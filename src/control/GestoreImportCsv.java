package control;

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

public class GestoreImportCsv {
    private InterfacciaImportCsv boundary;
    private CSVReader csvReader;
    
    public GestoreImportCsv(InterfacciaImportCsv boundary) {
        this.boundary = boundary;
        this.csvReader = new CSVReader();
    }
    
    public boolean importContorni(File file) throws FormatoFileNonSupportatoException {
        List<Contorno> listaContorni = csvReader.leggiContornoFilamenti(file);
        ContornoDao contornoDao = ContornoDao.getInstance();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        boolean contornoInseribile = true;
        
        List<Integer> filamentiOk = new ArrayList<>();
        
        Iterator<Contorno> i = listaContorni.iterator();
        while (i.hasNext()) {
            Contorno con = i.next();
            if (!filamentiOk.contains(con.getIdFil())) {
                if (!filamentoDao.queryEsistenzaFilamento(conn, con.getIdFil())) {
                    contornoInseribile = false;
                    System.err.println("Nel file dei contorni esiste un filamento non presente nel db, importare prima il file dei filamenti");
                    break;
                } else {
                    filamentiOk.add(con.getIdFil());
                }
            }
        }
        if (contornoInseribile) {
            contornoDao.inserisciContornoBatch(conn, listaContorni);
//            i = listaContorni.iterator();
//            while (i.hasNext()) {
//                Contorno c = i.next();
//                contornoDao.inserisciContorno(conn, c);
//            }
        }
        DBAccess.getInstance().closeConnection(conn);
        return contornoInseribile;
    }

    public boolean importFilamenti(File file) throws FormatoFileNonSupportatoException {
        List<Filamento> listaFilamenti = csvReader.leggiCatalogoFilamenti(file);
        Iterator<Filamento> i = listaFilamenti.iterator();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        SatelliteDao satelliteDao = SatelliteDao.getInstance();
        // select distinct satellite non si puo fare perche e su file ma si 
        // puo aggiungere una lista ed evitare la lettura 
        // nel db la maggior parte delle volte
        StrumentoDao strumentoDao = StrumentoDao.getInstance();
        // select distinct instrument
        Connection conn = DBAccess.getInstance().getConnection();
        boolean filamentoInseribile = true;
        
//long start = System.currentTimeMillis();
        List<String> satellitiOk = new ArrayList<>();
        List<String> strumentiOk = new ArrayList<>();

        while (i.hasNext()) {
            Filamento fil = i.next();
            String satellite = fil.getSatellite();
            if (!satellitiOk.contains(satellite)) {
                if (!satelliteDao.queryEsistenzaSatellite(conn, new BeanSatellite(satellite))) {
                    System.err.println("Nel file dei filamenti esiste un satellite non presente nel db, definire i satelliti prima di importare il file");
                    filamentoInseribile = false;
                    break;
                } else {
                    satellitiOk.add(satellite);
                }
            }
            
            String strumento = fil.getInstrument();
            if (!strumentiOk.contains(strumento)) {
                if (!strumentoDao.queryEsistenzaStrumento(conn, strumento)) {
                    System.err.println("Nel file dei filamenti esiste uno strumento non presente nel db, definire gli strumenti prima di importare il file");
                    filamentoInseribile = false;
                    break;
                } else {
                    strumentiOk.add(strumento);
                }
            }
        }
//System.out.println("check time: " + (System.currentTimeMillis() - start));
        // si inseriscono i filamenti solo solo se 
        // i satelliti e gli strumenti sono nel db
//start = System.currentTimeMillis();

        if (filamentoInseribile) {
            filamentoDao.inserisciFilamentoBatch(conn, listaFilamenti);
        }

//        if (filamentoInseribile) {
//            i = listaFilamenti.iterator();
//            while (i.hasNext()) {
//                Filamento f = i.next();
//                filamentoDao.inserisciFilamento(conn, f);
//            }
//        }

//System.out.println("write time: " + (System.currentTimeMillis() - start));
        DBAccess.getInstance().closeConnection(conn);
        return filamentoInseribile;
    }
    
    public boolean importSegmenti(File file) throws FormatoFileNonSupportatoException {
        List<Segmento> listaSegmenti = csvReader.leggiPosizioniSegmenti(file);
        Iterator<Segmento> i = listaSegmenti.iterator();
        SegmentoDao segmentoDao = SegmentoDao.getInstance();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        boolean segmentoInseribile = true;
        
        List<Integer> filamentiOk = new ArrayList<>();
        
        while (i.hasNext()) {
            Segmento seg = i.next();
            if (!filamentiOk.contains(seg.getIdFil())) {
                if (!filamentoDao.queryEsistenzaFilamento(conn, seg.getIdFil())) {
                    System.err.println("Nel file dei segmenti esiste un filamento non presente nel db, importare prima il file con i filamenti");
                    segmentoInseribile = false;
                    break;
                } else {
                    filamentiOk.add(seg.getIdFil());
                }
            }
        }
        if (segmentoInseribile) {
            segmentoDao.inserisciSegmentoBatch(conn, listaSegmenti);
//            i = listaSegmenti.iterator();
//            while (i.hasNext()) {
//                Segmento s = i.next();
//                segmentoDao.inserisciSegmento(conn, s);
//            }
        }
        DBAccess.getInstance().closeConnection(conn);
        return segmentoInseribile;
    }
        
    public void importStelle(File file) throws FormatoFileNonSupportatoException {
        List<Stella> listaStelle = csvReader.leggiPosizioniStelle(file);
        Iterator<Stella> i = listaStelle.iterator();
        StellaDao stellaDao = StellaDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        
        stellaDao.inserisciStellaBatch(conn, listaStelle);  
        
//        while (i.hasNext()) {
//            Stella st = i.next();
//            stellaDao.inserisciStella(conn, st);
//        }
        DBAccess.getInstance().closeConnection(conn);
    }
    
    public boolean importCsv(BeanRichiestaImport beanRichiesta) throws FormatoFileNonSupportatoException {
        TipoFileCsv tipoFile = beanRichiesta.getTipo();
        boolean res = true;
        if (tipoFile == TipoFileCsv.CONTORNO) {
            res = this.importContorni(beanRichiesta.getFileSelezionato());
        } else if (tipoFile == TipoFileCsv.FILAMENTO) {
            res = this.importFilamenti(beanRichiesta.getFileSelezionato());
        } else if (tipoFile == TipoFileCsv.SEGMENTO) {
            res = this.importSegmenti(beanRichiesta.getFileSelezionato());    
        } else if (tipoFile == TipoFileCsv.STELLA) {
            this.importStelle(beanRichiesta.getFileSelezionato()); 
        } else {
            res = false;
        }
        return res; 
    }
}
