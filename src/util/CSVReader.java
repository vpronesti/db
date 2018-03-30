package util;

import entity.Contorno;
import entity.Filamento;
import entity.Segmento;
import entity.Stella;
import exception.FormatoFileNonSupportatoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    
    public List<Filamento> leggiFilamenti(File file, 
            int maxRighe, int posInizio, int totaleRighe) 
            throws FormatoFileNonSupportatoException, 
            FileNotFoundException {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        List<Filamento> listaFilamenti = new ArrayList<>();
        if (file.exists() && !file.isDirectory()) {
            try {
                br = new BufferedReader(new FileReader(file));
                line = br.readLine(); // linea con nomi
    //            IDFIL,NAME,TOTAL_FLUX,MEAN_DENS,MEAN_TEMP,ELLIPTICITY,CONTRAST,SATELLITE,INSTRUMENT
                String[] header = line.split(cvsSplitBy);
                if (!header[0].equals("IDFIL") || 
                        !header[1].equals("NAME") || 
                        !header[2].equals("TOTAL_FLUX") || 
                        !header[3].equals("MEAN_DENS") || 
                        !header[4].equals("MEAN_TEMP") || 
                        !header[5].equals("ELLIPTICITY") || 
                        !header[6].equals("CONTRAST") || 
                        !header[7].equals("SATELLITE") || 
                        !header[8].equals("INSTRUMENT")) {
                    throw new FormatoFileNonSupportatoException("Errore nell'header del file");
                }

                int i = 0;
                // in caso di file con piu di maxRighe si 
                // saltano quelle gia lette alla chiamata precedente
                String l = null;
                while (i < posInizio && (l = br.readLine()) != null) {
                    i++;
                }

                int numRigheLette = 0;
                while ((line = br.readLine()) != null && numRigheLette < maxRighe && (numRigheLette + posInizio) < totaleRighe) {

                    String[] values = line.split(cvsSplitBy);
                    int idFil = Integer.parseInt(values[0]);
                    String name = values[1];
                    double totalFlux = Double.parseDouble(values[2]);
                    double meanDens = Double.parseDouble(values[3]);
                    double meanTemp = Double.parseDouble(values[4]);
                    double ellipticity = Double.parseDouble(values[5]);
                    double contrast = Double.parseDouble(values[6]);
                    String satellite = values[7];
                    String instrument = values[8];
                    Filamento fil = new Filamento(idFil, name, totalFlux, 
                            meanDens, meanTemp, ellipticity, contrast, satellite, 
                            instrument);
                    listaFilamenti.add(fil);
                    numRigheLette++;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
               throw new FormatoFileNonSupportatoException("Errore durante la lettura del file");
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new FileNotFoundException("Il file specificato non esiste");
        }
        return listaFilamenti;
    }
    
    public List<Contorno> leggiContorni(File file, 
            int maxRighe, int posInizio, int totaleRighe, String satellite) 
            throws FormatoFileNonSupportatoException, 
            FileNotFoundException {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
        List<Contorno> listaContorni = new ArrayList<>();
        
        if (file.exists() && !file.isDirectory()) {
            try {
                br = new BufferedReader(new FileReader(file));
                line = br.readLine(); // linea con nomi
                // IDFIL,GLON_CONT,GLAT_CONT
                String[] header = line.split(cvsSplitBy);
                if (!header[0].equals("IDFIL") || 
                        !header[1].equals("GLON_CONT") || 
                        !header[2].equals("GLAT_CONT")) {
                    throw new FormatoFileNonSupportatoException("Errore nell'header del file");
                }
                int i = 0;
                String l = null;
                while (i < posInizio && (l = br.readLine()) != null) {
                        i++;
                }
                int numRigheLette = 0;
                while ((line = br.readLine()) != null && numRigheLette < maxRighe && (numRigheLette + posInizio) < totaleRighe) {

                    String[] values = line.split(cvsSplitBy);
                    int idFil = Integer.parseInt(values[0]);
                    double glon_cont = Double.parseDouble(values[1]);
                    double glat_cont = Double.parseDouble(values[2]);

                    Contorno con = new Contorno(idFil, satellite, 
                            glon_cont, glat_cont);
                    listaContorni.add(con);
                    numRigheLette++;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
               throw new FormatoFileNonSupportatoException("Errore durante la lettura del file");
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new FileNotFoundException("Il file specificato non esiste");
        }
        return listaContorni;
    }
    
    public List<Segmento> leggiSegmenti(File file, 
            int maxRighe, int posInizio, int totaleRighe, String satellite) 
            throws FormatoFileNonSupportatoException, 
            FileNotFoundException {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        List<Segmento> listaSegmenti = new ArrayList<>();
        
//        Segmento seg;s
        
        if (file.exists() && !file.isDirectory()) {
            try {
                br = new BufferedReader(new FileReader(file));
                line = br.readLine(); // linea con nomi
                // IDFIL,IDBRANCH,TYPE,GLON_BR,GLAT_BR,N,FLUX
                String[] header = line.split(cvsSplitBy);
                if (!header[0].equals("IDFIL") || 
                        !header[1].equals("IDBRANCH") || 
                        !header[2].equals("TYPE") || 
                        !header[3].equals("GLON_BR") || 
                        !header[4].equals("GLAT_BR") || 
                        !header[5].equals("N") || 
                        !header[6].equals("FLUX")) {
                    throw new FormatoFileNonSupportatoException("Errore nell'header del file");
                }

                int i = 0;
                // in caso di file con piu di maxRighe si 
                // saltano quelle gia lette alla chiamata precedente
                String l = null;
                while (i < posInizio && (l = br.readLine()) != null) {
                    i++;
                }

                int numRigheLette = 0;
                while ((line = br.readLine()) != null && numRigheLette < maxRighe && (numRigheLette + posInizio) < totaleRighe) {

                    String[] values = line.split(cvsSplitBy);
                    int idFil = Integer.parseInt(values[0]);
                    int idBranch = Integer.parseInt(values[1]);
                    String type = values[2];
                    double glon_br = Double.parseDouble(values[3]);
                    double glat_br = Double.parseDouble(values[4]);
                    int n = Integer.parseInt(values[5]);
                    double flux = Double.parseDouble(values[6]);

                    Segmento seg = new Segmento(idFil, satellite, idBranch, type, 
                            glon_br, glat_br, n, flux);
                    listaSegmenti.add(seg);
                    numRigheLette++;
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
               throw new FormatoFileNonSupportatoException("Errore durante la lettura del file");
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            throw new FileNotFoundException("Il file specificato non esiste");
        }
        return listaSegmenti;
    }

    public List<Stella> leggiStelle(File file, 
            int maxRighe, int posInizio, int totaleRighe, String satellite) 
            throws FormatoFileNonSupportatoException, 
            FileNotFoundException {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        List<Stella> listaStelle = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(file));
            line = br.readLine(); // linea con nomi
            // IDSTAR,NAMESTAR,GLON_ST,GLAT_ST,FLUX_ST,TYPE_ST
            String[] header = line.split(cvsSplitBy);
            if (!header[0].equals("IDSTAR") || 
                    !header[1].equals("NAMESTAR") || 
                    !header[2].equals("GLON_ST") || 
                    !header[3].equals("GLAT_ST") || 
                    !header[4].equals("FLUX_ST") || 
                    !header[5].equals("TYPE_ST")) { 
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                throw new FormatoFileNonSupportatoException("Errore nell'header del file");
            }
            int i = 0;
            // in caso di file con piu di maxRighe si 
            // saltano quelle gia lette alla chiamata precedente
            String l = null;
            while (i < posInizio && (l = br.readLine()) != null) {
                i++;
            }

            int numRigheLette = 0;
            while ((line = br.readLine()) != null && numRigheLette < maxRighe && (numRigheLette + posInizio) < totaleRighe) {

                String[] values = line.split(cvsSplitBy);
                int idStar = Integer.parseInt(values[0]);
                String nameStar = values[1];
                double glon_st = Double.parseDouble(values[2]);
                double glat_st = Double.parseDouble(values[3]);
                double flux_st = Double.parseDouble(values[4]);
                String type_st = values[5];

                Stella st = new Stella(idStar, satellite, nameStar, glon_st, 
                        glat_st, flux_st, type_st);
                listaStelle.add(st);

                numRigheLette++;
            }

        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            throw new FileNotFoundException("Il file specificato non esiste");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
           throw new FormatoFileNonSupportatoException("Errore durante la lettura del file");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return listaStelle;
    }

    public int numeroRighe(File file) 
            throws FileNotFoundException {
        int lines = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.toString()));
            while (reader.readLine() != null) lines++;
            reader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Il file specificato non esiste");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines - 1; // si toglie la linea di intestazione
    }
    
//    public static void main(String[] args) throws URISyntaxException, FormatoFileNonSupportatoException {
//        CSVReader csvr = new CSVReader();
//
//        csvr.leggiCatalogoFilamenti("filamenti_Spitzer.csv");
//        csvr.leggiContornoFilamenti(new File("contorni_filamenti_Herschel.csv"), 20, 0, 10);
//        csvr.leggiPosizioniSegmenti("scheletro_filamenti_Herschel.csv");
//        csvr.leggiPosizioniStelle("stelle_Herschel.csv");
//    }
}