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

/**
 * REQ-FN-3
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
    
    public boolean importContorni(File file) 
            throws FormatoFileNonSupportatoException {
        boolean contornoInseribile = true;
        ContornoDao contornoDao = ContornoDao.getInstance();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        while (rigaInizioLettura < totaleRighe) {
            List<Contorno> listaContorni = csvReader.leggiContornoFilamenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe);
            if (listaContorni == null)
                break;
            rigaInizioLettura += listaContorni.size();

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
            }
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
        
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        while (rigaInizioLettura < totaleRighe) {
            List<Filamento> listaFilamenti = csvReader.leggiCatalogoFilamenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe);
            if (listaFilamenti == null)
                break;
            rigaInizioLettura += listaFilamenti.size();
            
            Iterator<Filamento> i = listaFilamenti.iterator();
            
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

//System.out.println("write time: " + (System.currentTimeMillis() - start));
        }
        DBAccess.getInstance().closeConnection(conn);
        return filamentoInseribile;
    }
    
    public boolean importSegmenti(File file) 
            throws FormatoFileNonSupportatoException {
        SegmentoDao segmentoDao = SegmentoDao.getInstance();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        Connection conn = DBAccess.getInstance().getConnection();
        boolean segmentoInseribile = true;
        
        int rigaInizioLettura = 0;
        int totaleRighe = csvReader.numeroRighe(file);
        while (rigaInizioLettura < totaleRighe) {
            List<Segmento> listaSegmenti = csvReader.leggiPosizioniSegmenti(file, MAXRIGHE, rigaInizioLettura, totaleRighe);
            if (listaSegmenti == null)
                break;
            rigaInizioLettura += listaSegmenti.size();
            
            Iterator<Segmento> i = listaSegmenti.iterator();


            List<Integer> filamentiOk = new ArrayList<>();

            while (i.hasNext()) {
                Segmento seg = i.next();
                if (!filamentiOk.contains(seg.getIdFil())) {
                    if (!filamentoDao.queryEsistenzaFilamento(conn, seg.getIdFil())) {
                        System.out.println("seg: " + seg);
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

System.out.println("inserting, rigaInizioLettura" + rigaInizioLettura);
            stellaDao.inserisciStellaBatch(conn, listaStelle);  
        }
        DBAccess.getInstance().closeConnection(conn);
    }
    
    public boolean importCsv(BeanRichiestaImport beanRichiesta) 
            throws FormatoFileNonSupportatoException {
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
