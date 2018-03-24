package control;

import bean.BeanIdFilamento;
import bean.BeanRichiestaImport;
import bean.BeanSatellite;
import boundary.InterfacciaImportCsv;
import dao.ContornoDao;
import dao.FilamentoDao;
import dao.SatelliteDao;
import dao.SegmentoDao;
import dao.StellaDao;
import entity.Contorno;
import entity.Filamento;
import entity.Segmento;
import entity.Stella;
import entity.TipoFileCsv;
import exception.FormatoFileNonSupportatoException;
import exception.ImpossibileAprireFileException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.CSVReader;
import util.DBAccess;
import static util.DBAccess.FOREIGN_KEY_VIOLATION;

/**
 * REQ-FN-3.1
 * REQ-FN-4
 */
public class GestoreImportCsv {
    private InterfacciaImportCsv boundary;
    private CSVReader csvReader;
    private final int MAXRIGHE = 100000;
    
    public GestoreImportCsv(InterfacciaImportCsv boundary) {
        this.boundary = boundary;
        this.csvReader = new CSVReader();
    }
    
    /**
     * la lettura del file avviene in blocchi di dimensione MAXRIGHE 
     * per ciascuna delle righe lette si controlla che i vincoli del 
     * DB siano rispettati 
     * 
     * se una riga fa riferimento ad un filamento non presente nel DB oppure 
     * uno dei punti dei contorni che si vogliono inserire si sovrappone 
     * ai punti dei segmenti del filmento
     * allora l'import viene rifiutato
     * 
     * se i vincoli sono rispettati da tutte le righe si esegue 
     * nuovamente una lettura a blocchi e le righe 
     * vengono inserite in modalita' batch 
     * 
     * prima di fare il commit si aggiorna la tabella delle 
     * relazioni tra stelle e filamenti 
     * 
     * @param file
     * @param satellite
     * @return
     * @throws FormatoFileNonSupportatoException
     * @throws ImpossibileAprireFileException 
     */
    public boolean importContorni(File file, String satellite) 
            throws FormatoFileNonSupportatoException, 
            ImpossibileAprireFileException {
        boolean contornoInseribile = true;
        ContornoDao contornoDao = ContornoDao.getInstance();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        while (rigaInizioLettura < totaleRighe) {
            List<Contorno> listaContorni = csvReader.leggiContorni(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
            if (listaContorni == null)
                break;
            rigaInizioLettura += listaContorni.size();

            List<BeanIdFilamento> filamentiOk = new ArrayList<>();

//            List<Segmento> listaPuntiUltimoSegmento = new ArrayList<>();
            Iterator<Contorno> i = listaContorni.iterator();
            while (i.hasNext()) {
                Contorno con = i.next();
                BeanIdFilamento idFil = new BeanIdFilamento(con.getIdFil(), con.getSatellite());
//                if (!filamentiOk.contains(idFil)) {
//                    if (!filamentoDao.queryEsistenzaFilamento(conn, idFil)) {
//                        contornoInseribile = false;
//                        System.err.println("Nel file dei contorni esiste un filamento non presente nel db, importare prima il file dei filamenti");
//                        break;
//                    } else {
//                        filamentiOk.add(idFil);
//                    }
//                }
                /**
                 * si interroga il DB per cercare i punti del segmento con cui 
                 * fare i confronti per trovare sovrapposizioni (segmento-contorno) solo se si 
                 * tratta di un segmento mai letto prima
                 */
                SegmentoDao segmentoDao = SegmentoDao.getInstance();
                List<Segmento> listaSegmenti = segmentoDao.queryPuntiSegmento(conn, idFil);
//                if (listaPuntiUltimoSegmento.size() == 0 || !(new BeanIdFilamento(listaPuntiUltimoSegmento.get(0).getIdFil(), listaPuntiUltimoSegmento.get(0).getSatellite()).equals(idFil))) {
//                    listaPuntiUltimoSegmento = segmentoDao.queryPuntiSegmento(conn, idFil);
//                }
                Iterator<Segmento> j = listaSegmenti.iterator();
                while (j.hasNext()) {
                    Segmento s = j.next();
                    // il punto di un contorno non puo' sovrapporsi ai punti 
                    // dei segmenti del filamento a cui appartiene
                    if (s.getgLonSe() == con.getgLonCont() && 
                            s.getgLatSe() == con.getgLatCont()) {
                        contornoInseribile = false;
                        break;
                    }
                }
            }

        }

        if (contornoInseribile) {
            rigaInizioLettura = 0;
            while (rigaInizioLettura < totaleRighe) {
                List<Contorno> listaContorni = csvReader.leggiContorni(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
                if (listaContorni == null)
                    break;
                rigaInizioLettura += listaContorni.size();
                if (!contornoDao.inserisciContornoBatch(conn, listaContorni)) {
                    contornoInseribile = false;
                    break;
                }
            }
        }
        /**
             * quando si modificano i contorni dei filamenti bisogna 
             * aggiornare la tabella stella_filamento perche' qualche 
             * stella potrebbe entrare o uscire da un filamento
             */
//        if (contornoInseribile)
//            ContornoDao.getInstance().aggiornamentoStellaFilamento(conn);
        
        DBAccess.getInstance().commit(conn);
        DBAccess.getInstance().closeConnection(conn);
        return contornoInseribile;
    }

    /**
     * la lettura del file avviene in blocchi di dimensione MAXRIGHE 
     * per ciascuna delle righe lette si controlla che i vincoli del 
     * DB siano rispettati 
     * 
     * se una riga non rispetta le foreign key verso satellite-strumento 
     * allora l'import viene rifiutato
     * 
     * se i vincoli sono rispettati da tutte le righe si esegue 
     * nuovamente una lettura a blocchi e le righe 
     * vengono inserite in modalita' batch 
     * 
     * @param file
     * @return
     * @throws FormatoFileNonSupportatoException
     * @throws ImpossibileAprireFileException 
     */
    public boolean importFilamenti(File file) 
            throws FormatoFileNonSupportatoException, 
            ImpossibileAprireFileException {
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        
        boolean filamentoInseribile = true;
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        List<Filamento> listaFilamenti = new ArrayList<>();
        
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        while (rigaInizioLettura < totaleRighe) {
            listaFilamenti = csvReader.leggiFilamenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe);
            if (listaFilamenti == null)
                break;
            rigaInizioLettura += listaFilamenti.size();
            if (!filamentoDao.inserisciFilamentoBatch(conn, listaFilamenti)) {
                filamentoInseribile = false;
                break;
            }
            /** dopo l'inserimento di nuovi filamenti si dovrebbe 
             * controllare se nel db sono presenti stelle interne a 
             * qualcuno dei filamenti appena inseriti ma dal momento che 
             * l'import del file dei filamenti non obbliga ad 
             * importare anche il file dei contorni (in realta' e' il file 
             * dei contorni a richiedere la presenza dei filamenti 
             * prima dell'import), il controllo viene posticipato 
             * al momento dell'import dei contorni
             */
        }
        DBAccess.getInstance().commit(conn);
        DBAccess.getInstance().closeConnection(conn);
        return filamentoInseribile;
    }
    
    /**
     * 
     * @param file
     * @param satellite
     * @return
     * @throws FormatoFileNonSupportatoException
     * @throws ImpossibileAprireFileException 
     */
    public boolean importSegmenti(File file, String satellite) 
            throws FormatoFileNonSupportatoException, 
            ImpossibileAprireFileException {
        SegmentoDao segmentoDao = SegmentoDao.getInstance();
        ContornoDao contornoDao = ContornoDao.getInstance();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        boolean segmentoInseribile = true;
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        while (rigaInizioLettura < totaleRighe) {
            List<Segmento> listaSegmenti = csvReader.leggiSegmenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
            if (listaSegmenti == null)
                break;
            rigaInizioLettura += listaSegmenti.size();

            Iterator<Segmento> i = listaSegmenti.iterator();


            List<BeanIdFilamento> filamentiOk = new ArrayList<>();

            while (i.hasNext()) {
                Segmento seg = i.next();
                BeanIdFilamento idFil = new BeanIdFilamento(seg.getIdFil(), seg.getSatellite());
//                if (!filamentiOk.contains(idFil)) {
//                    if (!filamentoDao.queryEsistenzaFilamento(conn, idFil)) {
//                        System.out.println("seg: " + seg);
//                        System.err.println("Nel file dei segmenti esiste un filamento non presente nel db, importare prima il file con i filamenti");
//                        segmentoInseribile = false;
//                        break;
//                    } else {
//                        filamentiOk.add(idFil);
//                    }
//                }
                /**
                 * controllare che il segmento che si vuole definire 
                 * non si sovrapponga ad un segmento gia esistente
                 */
                if (segmentoDao.querySegmentoAppartenteAltroSegmento(conn, seg)) {
                    segmentoInseribile = false;
                    break;
                }
                /**
                 * controllare che il segmento che si vuole definire 
                 * non si sovrapponga al perimetro del segmento
                 */
                List<Contorno> puntiContorno = contornoDao.queryPuntiContornoFilamento(conn, idFil);
                if (puntiContorno.contains(new Contorno(seg.getIdFil(), seg.getSatellite(), seg.getgLonSe(), seg.getgLatSe()))) {
                    segmentoInseribile = false;
                    break;
                }
            }

        }
        if (segmentoInseribile) {
            rigaInizioLettura = 0;
            while (rigaInizioLettura < totaleRighe) {
                List<Segmento> listaSegmenti = csvReader.leggiSegmenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
                if (listaSegmenti == null)
                    break;
                rigaInizioLettura += listaSegmenti.size();
                if (!segmentoDao.inserisciSegmentoBatch(conn, listaSegmenti)) {
                    segmentoInseribile = false;
                    break;
                }
            }
        }
        DBAccess.getInstance().commit(conn);
        DBAccess.getInstance().closeConnection(conn);
        return segmentoInseribile;
    }
    

    /**
     * la lettura del file avviene in blocchi di dimensione MAXRIGHE 
     * per ciascuna delle righe lette si controlla che i vincoli del 
     * DB siano rispettati 
     * 
     * se una riga fa riferimento ad un satellite non presente nel DB 
     * allora l'import viene rifiutato
     * 
     * se il vincolo e' rispettato da tutte le righe si esegue 
     * nuovamente una lettura a blocchi e le righe 
     * vengono inserite in modalita' batch 
     * 
     * prima di fare il commit si aggiorna la tabella delle 
     * relazioni tra stelle e filamenti 
     * 
     * 
     * @param file
     * @param satellite
     * @return
     * @throws FormatoFileNonSupportatoException
     * @throws ImpossibileAprireFileException 
     */
    public boolean importStelle(File file, String satellite) 
            throws FormatoFileNonSupportatoException, 
            ImpossibileAprireFileException {
        boolean stellaInseribile = true;
        StellaDao stellaDao = StellaDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
//        if (SatelliteDao.getInstance().queryEsistenzaSatellite(conn, new BeanSatellite(satellite))) {
            while (rigaInizioLettura < totaleRighe) {
                List<Stella> listaStelle = csvReader.leggiStelle(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
                if (listaStelle == null)
                    break;
                rigaInizioLettura += listaStelle.size();
                if (!stellaDao.inserisciStellaBatch(conn, listaStelle)) {
                    stellaInseribile = false;
                    break;
                }
            }
            ContornoDao contornoDao = ContornoDao.getInstance();
            if (stellaInseribile) {
                contornoDao.aggiornamentoStellaFilamento(conn);
            }
//        } else {
//            stellaInseribile = false;
//        }
        DBAccess.getInstance().commit(conn);
        DBAccess.getInstance().closeConnection(conn);
        return stellaInseribile;
    }
    
    public boolean importCsv(BeanRichiestaImport beanRichiesta) 
            throws FormatoFileNonSupportatoException, 
            ImpossibileAprireFileException {
        TipoFileCsv tipoFile = beanRichiesta.getTipo();
        String satellite = beanRichiesta.getSatellite();
        boolean res;
        if (tipoFile == TipoFileCsv.CONTORNO) {
            res = this.importContorni(beanRichiesta.getFileSelezionato(), satellite);
        } else if (tipoFile == TipoFileCsv.FILAMENTO) {
            res = this.importFilamenti(beanRichiesta.getFileSelezionato());
        } else if (tipoFile == TipoFileCsv.SEGMENTO) {
            res = this.importSegmenti(beanRichiesta.getFileSelezionato(), satellite);    
        } else if (tipoFile == TipoFileCsv.STELLA) {
            res = this.importStelle(beanRichiesta.getFileSelezionato(), satellite); 
        } else {
            res = false;
        }
        return res; 
    }
    
    /**
     * serve a rappresentare la coppia satellite-strumento relativa ad un 
     * filamento perche' quando si importa il file dei filamenti occorre 
     * verificare che le coppie satellite-strumento esistano nel DB 
     * 
     * questa classe serve per mantenere una lista delle coppie gia' 
     * lette dal DB ed evitare di fare sempre la stella lettura
     */
    public static class SatelliteStrumento {
        private String satellite;
        private String strumento;

        public SatelliteStrumento(String satellite, String strumento) {
            this.satellite = satellite;
            this.strumento = strumento;
        }

        public String getSatellite() {
            return satellite;
        }

        public void setSatellite(String satellite) {
            this.satellite = satellite;
        }

        public String getStrumento() {
            return strumento;
        }

        public void setStrumento(String strumento) {
            this.strumento = strumento;
        }
        
        @Override
        public boolean equals(Object v) {
            boolean retVal = false;

            if (v instanceof SatelliteStrumento){
                SatelliteStrumento ptr = (SatelliteStrumento) v;
                retVal = ptr.getSatellite().equals(this.satellite) && ptr.getStrumento().equals(this.strumento);
            }

            return retVal;
        }
    }
}
