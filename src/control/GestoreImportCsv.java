package control;

import bean.BeanRichiestaImport;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import util.CSVReader;
import util.DBAccess;

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
     * per ciascuno dei blocchi letti si prova a effettuare l'inserimento nel DB 
     * 
     * se una riga fa riferimento ad un filamento non presente nel DB oppure 
     * uno dei punti dei contorni che si vogliono inserire si sovrappone 
     * ai punti dei segmenti del filmento, allora l'import viene rifiutato
     * 
     * se i vincoli sono rispettati prima di fare il commit si aggiorna 
     * la tabella delle relazioni tra stelle e filamenti 
     * 
     * @param file
     * @param satellite
     * @return
     * @throws FormatoFileNonSupportatoException
     * @throws ImpossibileAprireFileException 
     */
    public boolean importContorni(File file, String satellite) 
            throws FormatoFileNonSupportatoException, 
            FileNotFoundException {
        boolean contornoInseribile = true;
        ContornoDao contornoDao = ContornoDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        
        List<Contorno> listaContorni;
        rigaInizioLettura = 0;
        while (rigaInizioLettura < totaleRighe) {
            try {
                listaContorni = csvReader.leggiContorni(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
            } catch (FileNotFoundException e) {
                // se il file non esiste si abortisce la transazione, si chiude la connessione e si rilancia l'eccezione 
                // il file esiste altrimenti l'eccezione verrebbe lanciata dal metodo per la lettura del numero di righe
                DBAccess.getInstance().rollback(conn);
                DBAccess.getInstance().closeConnection(conn);
                throw new FileNotFoundException(e.getMessage());
            } catch (FormatoFileNonSupportatoException e) {
                // se il formato del file non va bene si abortisce la transazione, si chiude la connessione e e si rilancia l'eccezione 
                DBAccess.getInstance().rollback(conn);
                DBAccess.getInstance().closeConnection(conn);
                throw new FormatoFileNonSupportatoException(e.getMessage());
            }
            if (listaContorni == null)
                break;
            rigaInizioLettura += listaContorni.size();
            if (!contornoDao.inserisciContornoBatch(conn, listaContorni)) {
                contornoInseribile = false;
                break;
            }
        }
        /**
         * controlla che i contorni inseriti non creino sovrapposizioni 
         * con i segmenti nel DB
         */
        if (contornoInseribile)
            contornoInseribile = !contornoDao.controlloSovrapposizioneContornoSegmento(conn);
        /**
         * quando si modificano i contorni dei filamenti bisogna 
         * aggiornare la tabella stella_filamento perche' qualche 
         * stella potrebbe entrare o uscire da un filamento
         */
        if (contornoInseribile)
            ContornoDao.getInstance().aggiornamentoStellaFilamento(conn);
        
        if (contornoInseribile)
            DBAccess.getInstance().commit(conn);
        else
            DBAccess.getInstance().rollback(conn);
        DBAccess.getInstance().closeConnection(conn);
        return contornoInseribile;
    }

    /**
     * la lettura del file avviene in blocchi di dimensione MAXRIGHE 
     * per ciascuno dei blocchi letti si prova a effettuare l'inserimento nel DB 
     * 
     * se una riga fa riferimento ad una coppia strumento-satellite 
     * non presente nel DB, allora l'import viene rifiutato
     * 
     * @param file
     * @return
     * @throws FormatoFileNonSupportatoException
     * @throws ImpossibileAprireFileException 
     */
    public boolean importFilamenti(File file) 
            throws FormatoFileNonSupportatoException, 
            FileNotFoundException {
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        
        boolean filamentoInseribile = true;
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        List<Filamento> listaFilamenti = new ArrayList<>();
        
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        while (rigaInizioLettura < totaleRighe) {
            try {
                listaFilamenti = csvReader.leggiFilamenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe);
            } catch (FileNotFoundException e) {
                // se il file non esiste si abortisce la transazione, si chiude la connessione e si rilancia l'eccezione 
                // il file esiste altrimenti l'eccezione verrebbe lanciata dal metodo per la lettura del numero di righe
                DBAccess.getInstance().rollback(conn);
                DBAccess.getInstance().closeConnection(conn);
                throw new FileNotFoundException(e.getMessage());
            } catch (FormatoFileNonSupportatoException e) {
                // se il formato del file non va bene si abortisce la transazione, si chiude la connessione e e si rilancia l'eccezione 
                DBAccess.getInstance().rollback(conn);
                DBAccess.getInstance().closeConnection(conn);
                throw new FormatoFileNonSupportatoException(e.getMessage());
            }
            if (listaFilamenti == null)
                break;
            rigaInizioLettura += listaFilamenti.size();
            if (!filamentoDao.inserisciFilamentoBatch(conn, listaFilamenti)) {
                filamentoInseribile = false;
                break;
            }
        }
        if (filamentoInseribile)
            DBAccess.getInstance().commit(conn);
        else
            DBAccess.getInstance().rollback(conn);
        DBAccess.getInstance().closeConnection(conn);
        return filamentoInseribile;
    }
    
    /**
     * la lettura del file avviene in blocchi di dimensione MAXRIGHE 
     * per ciascuno dei blocchi letti si prova a effettuare l'inserimento nel DB 
     * 
     * se una riga fa riferimento ad un filamento non presente nel DB oppure 
     * uno dei punti dei segmenti che si vogliono inserire si sovrappone 
     * ai punti dei contorni del filmento, allora l'import viene rifiutato
     * 
     * @param file
     * @param satellite
     * @return
     * @throws FormatoFileNonSupportatoException
     * @throws FileNotFoundException 
     */
    public boolean importSegmenti(File file, String satellite) 
            throws FormatoFileNonSupportatoException, 
            FileNotFoundException {
        SegmentoDao segmentoDao = SegmentoDao.getInstance();
        ContornoDao contornoDao = ContornoDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        boolean segmentoInseribile = true;
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        
        rigaInizioLettura = 0;
        List<Segmento> listaSegmenti;
        while (rigaInizioLettura < totaleRighe) {
            try {
                listaSegmenti = csvReader.leggiSegmenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
            } catch (FileNotFoundException e) {
                // se il file non esiste si abortisce la transazione, si chiude la connessione e si rilancia l'eccezione 
                // il file esiste altrimenti l'eccezione verrebbe lanciata dal metodo per la lettura del numero di righe
                DBAccess.getInstance().rollback(conn);
                DBAccess.getInstance().closeConnection(conn);
                throw new FileNotFoundException(e.getMessage());
            } catch (FormatoFileNonSupportatoException e) {
                // se il formato del file non va bene si abortisce la transazione, si chiude la connessione e e si rilancia l'eccezione 
                DBAccess.getInstance().rollback(conn);
                DBAccess.getInstance().closeConnection(conn);
                throw new FormatoFileNonSupportatoException(e.getMessage());
            }
            if (listaSegmenti == null)
                break;
            rigaInizioLettura += listaSegmenti.size();
            if (!segmentoDao.inserisciSegmentoBatch(conn, listaSegmenti)) {
                segmentoInseribile = false;
                break;
            }
        }
        /**
         * controlla che i segmenti inseriti non creino sovrapposizioni 
         * con i contorni nel DB
         */
        if (segmentoInseribile)
            segmentoInseribile = !contornoDao.controlloSovrapposizioneContornoSegmento(conn);
        
        if (segmentoInseribile)
            DBAccess.getInstance().commit(conn);
        else
            DBAccess.getInstance().rollback(conn);
        DBAccess.getInstance().closeConnection(conn);
        return segmentoInseribile;
    }
    

    /**
     * la lettura del file avviene in blocchi di dimensione MAXRIGHE 
     * per ciascuno dei blocchi letti si prova a effettuare l'inserimento nel DB 
     * 
     * se una riga fa riferimento ad un satellite non presente nel DB 
     * allora l'import viene rifiutato
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
    public boolean importStelle(File file, String satellite) 
            throws FormatoFileNonSupportatoException, 
            FileNotFoundException {
        boolean stellaInseribile = true;
        StellaDao stellaDao = StellaDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        List<Stella> listaStelle;
        while (rigaInizioLettura < totaleRighe) {
            try {
                listaStelle = csvReader.leggiStelle(file, MAXRIGHE, rigaInizioLettura, totaleRighe, satellite);
            } catch (FileNotFoundException e) {
                // se il file non esiste si abortisce la transazione, si chiude la connessione e si rilancia l'eccezione 
                // il file esiste altrimenti l'eccezione verrebbe lanciata dal metodo per la lettura del numero di righe
                DBAccess.getInstance().rollback(conn);
                DBAccess.getInstance().closeConnection(conn);
                throw new FileNotFoundException(e.getMessage());
            } catch (FormatoFileNonSupportatoException e) {
                // se il formato del file non va bene si abortisce la transazione, si chiude la connessione e e si rilancia l'eccezione 
                DBAccess.getInstance().rollback(conn);
                DBAccess.getInstance().closeConnection(conn);
                throw new FormatoFileNonSupportatoException(e.getMessage());
            }
            if (listaStelle == null)
                break;
            rigaInizioLettura += listaStelle.size();
            if (!stellaDao.inserisciStellaBatch(conn, listaStelle)) {
                stellaInseribile = false;
                break;
            }
        }
        ContornoDao contornoDao = ContornoDao.getInstance();
        /**
         * si aggiorna la tabella delle relazioni tra stelle e filamenti per 
         * vedere se tra le stelle inserite qualcuna e' interna ad un filamento
         */
        if (stellaInseribile) {
            contornoDao.aggiornamentoStellaFilamento(conn);
        }
        if (stellaInseribile) 
            DBAccess.getInstance().commit(conn);
        else
            DBAccess.getInstance().rollback(conn);
        DBAccess.getInstance().closeConnection(conn);
        return stellaInseribile;
    }
    
    public boolean importCsv(BeanRichiestaImport beanRichiesta) 
            throws FormatoFileNonSupportatoException, 
            FileNotFoundException {
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
}
