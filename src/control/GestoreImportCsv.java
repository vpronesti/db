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
import dao.StrumentoDao;
import entity.Contorno;
import entity.Filamento;
import entity.Segmento;
import entity.Stella;
import entity.TipoFileCsv;
import exception.FormatoFileNonSupportatoException;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import util.CSVReader;
import util.DBAccess;

/**
 * REQ-FN-3
 * REQ-FN-4
 */
public class GestoreImportCsv {
    private InterfacciaImportCsv boundary;
    private CSVReader csvReader;
    private final int MAXRIGHE = 100000;
    
    /**
     * probabilmente bisogna cambiare contains
     */
    
    public GestoreImportCsv(InterfacciaImportCsv boundary) {
        this.boundary = boundary;
        this.csvReader = new CSVReader();
    }
    
    public boolean importContorni(File file, String satellite) 
            throws FormatoFileNonSupportatoException {
        boolean contornoInseribile = true;
        ContornoDao contornoDao = ContornoDao.getInstance();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        while (rigaInizioLettura < totaleRighe) {
            List<Contorno> listaContorni = csvReader.leggiContornoFilamenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
            if (listaContorni == null)
                break;
            rigaInizioLettura += listaContorni.size();

            List<BeanIdFilamento> filamentiOk = new ArrayList<>();

            Iterator<Contorno> i = listaContorni.iterator();
            while (i.hasNext()) {
                Contorno con = i.next();
                BeanIdFilamento idFil = new BeanIdFilamento(con.getIdFil(), con.getSatellite());
                if (!filamentiOk.contains(idFil)) {
                    if (!filamentoDao.queryEsistenzaFilamento(conn, idFil)) {
                        contornoInseribile = false;
                        System.err.println("Nel file dei contorni esiste un filamento non presente nel db, importare prima il file dei filamenti");
                        break;
                    } else {
                        filamentiOk.add(idFil);
                    }
                }
            }

        }
        
        if (contornoInseribile) {
            rigaInizioLettura = 0;
            while (rigaInizioLettura < totaleRighe) {
                List<Contorno> listaContorni = csvReader.leggiContornoFilamenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
                if (listaContorni == null)
                    break;
                rigaInizioLettura += listaContorni.size();
                contornoDao.inserisciContornoBatch(conn, listaContorni);
            }
            /**
             * quando si modificano i contorni dei filamenti bisogna 
             * aggiornare la tabella stella_filamento perche' qualche 
             * stella potrebbe entrare o uscire da un filamento
             */
            
            
        }
        
        DBAccess.getInstance().closeConnection(conn);
        return contornoInseribile;
    }

    public boolean importFilamenti(File file) 
            throws FormatoFileNonSupportatoException {
        Connection conn = DBAccess.getInstance().getConnection();
        boolean filamentoInseribile = true;
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        StrumentoDao strumentoDao = StrumentoDao.getInstance();
        List<Filamento> listaFilamenti = new ArrayList<>();
        
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        while (rigaInizioLettura < totaleRighe) {
            listaFilamenti = csvReader.leggiCatalogoFilamenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe);
            if (listaFilamenti == null)
                break;
            rigaInizioLettura += listaFilamenti.size();
            
            Iterator<Filamento> i = listaFilamenti.iterator();
            
//long start = System.currentTimeMillis();
            List<SatelliteStrumento> listaSatStrOk = new ArrayList<>();
            List<String> satellitiOk = new ArrayList<>();
            List<String> strumentiOk = new ArrayList<>();

            while (i.hasNext()) {
                Filamento fil = i.next();
                
                String satellite = fil.getSatellite();
                String strumento = fil.getInstrument();
                SatelliteStrumento satStr = new SatelliteStrumento(satellite, strumento);
                if (!listaSatStrOk.contains(satStr)) {
                    if (!strumentoDao.queryEsistenzaSatelliteStrumento(conn, satellite, strumento)) {
                        System.err.println("Nel db non risulta che il satellite " + satellite + " sia dotato dello strumento " + strumento);
                        filamentoInseribile = false;
                        break;
                    } else {
                        listaSatStrOk.add(satStr);
                    }
                }
                
//                String satellite = fil.getSatellite();
//                if (!satellitiOk.contains(satellite)) {
//                    if (!satelliteDao.queryEsistenzaSatellite(conn, new BeanSatellite(satellite))) {
//                        System.err.println("Nel file dei filamenti esiste un satellite non presente nel db, definire i satelliti prima di importare il file");
//                        filamentoInseribile = false;
//                        break;
//                    } else {
//                        satellitiOk.add(satellite);
//                    }
//                }
//
//                String strumento = fil.getInstrument();
//                if (!strumentiOk.contains(strumento)) {
//                    if (!strumentoDao.queryEsistenzaStrumento(conn, strumento)) {
//                        System.err.println("Nel file dei filamenti esiste uno strumento non presente nel db, definire gli strumenti prima di importare il file");
//                        filamentoInseribile = false;
//                        break;
//                    } else {
//                        strumentiOk.add(strumento);
//                    }
//                }
            }
//System.out.println("check time: " + (System.currentTimeMillis() - start));
            // si inseriscono i filamenti solo solo se 
            // i satelliti e gli strumenti sono nel db
//start = System.currentTimeMillis();



//System.out.println("write time: " + (System.currentTimeMillis() - start));
        }
        
        if (filamentoInseribile) {
            rigaInizioLettura = 0;
            while (rigaInizioLettura < totaleRighe) {
                listaFilamenti = csvReader.leggiCatalogoFilamenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe);
                if (listaFilamenti == null)
                    break;
                rigaInizioLettura += listaFilamenti.size();
                filamentoDao.inserisciFilamentoBatch(conn, listaFilamenti);
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
        }
        
        DBAccess.getInstance().closeConnection(conn);
        return filamentoInseribile;
    }
    
    public boolean importSegmenti(File file, String satellite) 
            throws FormatoFileNonSupportatoException {
        SegmentoDao segmentoDao = SegmentoDao.getInstance();
        ContornoDao contornoDao = ContornoDao.getInstance();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        boolean segmentoInseribile = true;
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        while (rigaInizioLettura < totaleRighe) {
            List<Segmento> listaSegmenti = csvReader.leggiPosizioniSegmenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
            if (listaSegmenti == null)
                break;
            rigaInizioLettura += listaSegmenti.size();

            Iterator<Segmento> i = listaSegmenti.iterator();


            List<BeanIdFilamento> filamentiOk = new ArrayList<>();

            while (i.hasNext()) {
                Segmento seg = i.next();
                BeanIdFilamento idFil = new BeanIdFilamento(seg.getIdFil(), seg.getSatellite());
                if (!filamentiOk.contains(idFil)) {
                    if (!filamentoDao.queryEsistenzaFilamento(conn, idFil)) {
                        System.out.println("seg: " + seg);
                        System.err.println("Nel file dei segmenti esiste un filamento non presente nel db, importare prima il file con i filamenti");
                        segmentoInseribile = false;
                        break;
                    } else {
                        filamentiOk.add(idFil);
                    }
                    /**
                     * controllare che il segmento che si vuole definire 
                     * non si sovrapponga ad un segmento gia esistente
                     */
                    
                    /**
                     * controllare che il segmento che si vuole definire 
                     * non si sovrapponga al perimetro del segmento
                     */
                    List<Contorno> puntiContorno = contornoDao.queryPuntiContornoFilamento(conn, idFil);
                    if (puntiContorno.contains(new Contorno(seg.getIdFil(), seg.getSatellite(), seg.getgLonBr(), seg.getgLatBr()))) {
                        segmentoInseribile = false;
                        break;
                    }
                }
            }

        }
        if (segmentoInseribile) {
            rigaInizioLettura = 0;
            while (rigaInizioLettura < totaleRighe) {
                List<Segmento> listaSegmenti = csvReader.leggiPosizioniSegmenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
                if (listaSegmenti == null)
                    break;
                rigaInizioLettura += listaSegmenti.size();
                segmentoDao.inserisciSegmentoBatch(conn, listaSegmenti);
            }
        }
        DBAccess.getInstance().closeConnection(conn);
        return segmentoInseribile;
    }
        
    public void importStelle(File file) throws FormatoFileNonSupportatoException {
        StellaDao stellaDao = StellaDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        while (rigaInizioLettura < totaleRighe) {
            List<Stella> listaStelle = csvReader.leggiPosizioniStelle(file, MAXRIGHE, rigaInizioLettura, totaleRighe);
            if (listaStelle == null)
                break;
            rigaInizioLettura += listaStelle.size();
            Iterator<Stella> i = listaStelle.iterator();

//System.out.println("inserting, rigaInizioLettura" + rigaInizioLettura);
            stellaDao.inserisciStellaBatch(conn, listaStelle);  
        }
//        ContornoDao contornoDao = ContornoDao.getInstance();
//        contornoDao.aggiornamentoStellaFilamento(conn);

        stellaDao.aggiornaStelleFilamento(conn);
        
        DBAccess.getInstance().closeConnection(conn);
    }
    
    public boolean importCsv(BeanRichiestaImport beanRichiesta) 
            throws FormatoFileNonSupportatoException {
        TipoFileCsv tipoFile = beanRichiesta.getTipo();
        String satellite = beanRichiesta.getSatellite();
        boolean res = true;
        if (tipoFile == TipoFileCsv.CONTORNO) {
            res = this.importContorni(beanRichiesta.getFileSelezionato(), satellite);
        } else if (tipoFile == TipoFileCsv.FILAMENTO) {
            res = this.importFilamenti(beanRichiesta.getFileSelezionato());
        } else if (tipoFile == TipoFileCsv.SEGMENTO) {
            res = this.importSegmenti(beanRichiesta.getFileSelezionato(), satellite);    
        } else if (tipoFile == TipoFileCsv.STELLA) {
            this.importStelle(beanRichiesta.getFileSelezionato()); 
        } else {
            res = false;
        }
        return res; 
    }
    
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
