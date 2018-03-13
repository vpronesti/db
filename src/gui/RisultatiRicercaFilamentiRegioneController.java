package gui;

import bean.BeanRispostaFilamenti;
import entity.Filamento;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RisultatiRicercaFilamentiRegioneController implements Initializable {
    @FXML
    Text titoloText;
    @FXML
    Pagination risultatiPagination;
    
    private final int rowsPerPage = 20;
    private final int itemsPerPage = 1;
    private BeanRispostaFilamenti beanRisposta;
    private List<Filamento> listaFilamenti;
    
    private final ObservableList<FilamentoGr> data;
    
    public ObservableList<FilamentoGr> getData() {
        List<FilamentoGr> lp = new ArrayList<>();
        Iterator<Filamento> i = listaFilamenti.iterator();
        while (i.hasNext()) {
            Filamento f = i.next();
            FilamentoGr p = new FilamentoGr(f.getIdFil(), f.getName(), 
                    f.getTotalFlux(), f.getMeanDens(), f.getMeanTemp(), 
                    f.getEllipticity(), f.getContrast(), 
                    f.getSatellite(), f.getInstrument());
            lp.add(p);
        }
        return FXCollections.observableArrayList(lp);
    }
    
        public VBox createPage(int pageIndex) {
        int lastIndex = 0;
        int displace = data.size() % rowsPerPage;
        if (displace > 0) {
            lastIndex = data.size() / rowsPerPage;
        } else {
            lastIndex = data.size() / rowsPerPage - 1;

        }

        VBox box = new VBox(5);
        int page = pageIndex * itemsPerPage;

        for (int i = page; i < page + itemsPerPage; i++) {
            TableView<FilamentoGr> table = new TableView<FilamentoGr>();
            
            TableColumn idFilCol = new TableColumn("Id");
            idFilCol.setCellValueFactory(
                    new PropertyValueFactory<FilamentoGr, Integer>("idFil"));
            idFilCol.setMinWidth(20);

            TableColumn nameCol = new TableColumn("Nome");
            nameCol.setCellValueFactory(
                    new PropertyValueFactory<FilamentoGr, String>("name"));
            nameCol.setMinWidth(160);

            TableColumn totalFluxCol = new TableColumn("Flusso");
            totalFluxCol.setCellValueFactory(
                    new PropertyValueFactory<FilamentoGr, Float>("total_flux"));
            totalFluxCol.setMinWidth(160);
            TableColumn meanDensCol = new TableColumn("Densita'");
            meanDensCol.setCellValueFactory(
                    new PropertyValueFactory<FilamentoGr, Float>("mean_dens"));
            meanDensCol.setMinWidth(160);
            TableColumn meanTempCol = new TableColumn("Temperatura");
            meanTempCol.setCellValueFactory(
                    new PropertyValueFactory<FilamentoGr, Float>("mean_temp"));
            meanTempCol.setMinWidth(160);
            TableColumn ellipticityCol = new TableColumn("Ellitticita'");
            ellipticityCol.setCellValueFactory(
                    new PropertyValueFactory<FilamentoGr, Float>("ellipticity"));
            ellipticityCol.setMinWidth(160);
            TableColumn contrastCol = new TableColumn("Contrasto");
            contrastCol.setCellValueFactory(
                    new PropertyValueFactory<FilamentoGr, Float>("contrast"));
            contrastCol.setMinWidth(160);
            
            TableColumn satelliteCol = new TableColumn("Satellite");
            satelliteCol.setCellValueFactory(
                    new PropertyValueFactory<FilamentoGr, String>("satellite"));
            satelliteCol.setMinWidth(160);
            TableColumn instrumentCol = new TableColumn("Strumento");
            instrumentCol.setCellValueFactory(
                    new PropertyValueFactory<FilamentoGr, String>("instrument"));
            instrumentCol.setMinWidth(160);

            table.getColumns().addAll(idFilCol, nameCol, totalFluxCol, 
                    meanDensCol, meanTempCol, ellipticityCol, contrastCol, 
                    satelliteCol, instrumentCol);
            if (lastIndex == pageIndex) {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage, pageIndex * rowsPerPage + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage, pageIndex * rowsPerPage + rowsPerPage)));
            }


            box.getChildren().add(table);
        }
        return box;
    }
    
    public RisultatiRicercaFilamentiRegioneController(BeanRispostaFilamenti beanRisposta) {
        this.beanRisposta = beanRisposta;
        this.listaFilamenti = beanRisposta.getFilamenti();
        this.data = getData();
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.RICERCAFILAMENTIREGIONE);
    }
    
    @FXML
    protected void menu(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
    
    @Override
    public void initialize() {
        int lastPage = ((beanRisposta.getFilamenti().size() % rowsPerPage > 0) ? 1 : 0);
        risultatiPagination.setPageCount(beanRisposta.getFilamenti().size() / rowsPerPage + lastPage);
        risultatiPagination.setPageFactory(this::createPage);        
    }
    
    public static class FilamentoGr {

        private SimpleIntegerProperty idFil;
        private SimpleStringProperty name;
        private SimpleFloatProperty total_flux;
        private SimpleFloatProperty mean_dens;
        private SimpleFloatProperty mean_temp;
        private SimpleFloatProperty ellipticity;
        private SimpleFloatProperty contrast;
        private SimpleStringProperty satellite;
        private SimpleStringProperty instrument;

        private FilamentoGr(int idFil, String name, float total_flux, 
                float mean_dens, float mean_temp, float ellipticity, 
                float contrast, String satellite, String instrument) {
            this.idFil = new SimpleIntegerProperty(idFil);
            this.name = new SimpleStringProperty(name);
            this.total_flux = new SimpleFloatProperty(total_flux);
            this.mean_dens = new SimpleFloatProperty(mean_dens);
            this.mean_temp = new SimpleFloatProperty(mean_temp);
            this.ellipticity = new SimpleFloatProperty(ellipticity);
            this.contrast = new SimpleFloatProperty(contrast);
            this.satellite = new SimpleStringProperty(satellite);
            this.instrument = new SimpleStringProperty(instrument);
        }

        public int getIdFil() {
            return idFil.get();
        }

        public void setIdFil(SimpleIntegerProperty idFil) {
            this.idFil = idFil;
        }

        public String getName() {
            return name.get();
        }

        public void setName(SimpleStringProperty name) {
            this.name = name;
        }

        public Float getTotal_flux() {
            return total_flux.get();
        }

        public void setTotal_flux(SimpleFloatProperty total_flux) {
            this.total_flux = total_flux;
        }

        public Float getMean_dens() {
            return mean_dens.get();
        }

        public void setMean_dens(SimpleFloatProperty mean_dens) {
            this.mean_dens = mean_dens;
        }

        public Float getMean_temp() {
            return mean_temp.get();
        }

        public void setMean_temp(SimpleFloatProperty mean_temp) {
            this.mean_temp = mean_temp;
        }

        public Float getEllipticity() {
            return ellipticity.get();
        }

        public void setEllipticity(SimpleFloatProperty ellipticity) {
            this.ellipticity = ellipticity;
        }

        public Float getContrast() {
            return contrast.get();
        }

        public void setContrast(SimpleFloatProperty contrast) {
            this.contrast = contrast;
        }

        public String getSatellite() {
            return satellite.get();
        }

        public void setSatellite(SimpleStringProperty satellite) {
            this.satellite = satellite;
        }

        public String getInstrument() {
            return instrument.get();
        }

        public void setInstrument(SimpleStringProperty instrument) {
            this.instrument = instrument;
        }
    }
}