package gui;

import bean.BeanRispostaStellaFilamento;
import entity.Stella;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import util.DistanzaComparator;
import util.FlussoComparator;

public class RisultatiRicercaDistanzaStellaFilamentoController implements Initializable {
    @FXML
    Text titoloText;
    @FXML
    ComboBox<String> ordinamentoComboBox;
    @FXML
    Pagination risultatiPagination;
    @FXML
    Text text;
    
    private final int rowsPerPage = 20;
    private final int itemsPerPage = 1;
    private BeanRispostaStellaFilamento beanRisposta;
    private List<Stella> listaStelle;
    private List<Double> listaDistanze;
    private boolean ordinamentoDistanza; // se e' false si ordina per flusso
    
    private final ObservableList<StellaGr> data;
    
    public ObservableList<StellaGr> getData() {
        List<StellaGr> lp = new ArrayList<>();
        Iterator<Stella> i = listaStelle.iterator();
        int j = 0;
        while (i.hasNext()) {
            Stella s = i.next();
            StellaGr p = new StellaGr(s.getIdStella(), s.getNome(), 
                    s.getgLonSt(), s.getgLatSt(), s.getFlussoSt(), 
                    s.getTipo(), listaDistanze.get(j++));
            lp.add(p);
        }
        if (ordinamentoDistanza) {
            Collections.sort(lp, new DistanzaComparator());
        } else {
            Collections.sort(lp, new FlussoComparator());
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
            TableView<StellaGr> table = new TableView<StellaGr>();
            
            TableColumn idStarCol = new TableColumn("Id");
            idStarCol.setCellValueFactory(
                    new PropertyValueFactory<StellaGr, Integer>("idStar"));
            idStarCol.setMinWidth(20);

            TableColumn nameStarCol = new TableColumn("Nome");
            nameStarCol.setCellValueFactory(
                    new PropertyValueFactory<StellaGr, String>("nameStar"));
            nameStarCol.setMinWidth(160);

            TableColumn gLonCol = new TableColumn("Longitudine");
            gLonCol.setCellValueFactory(
                    new PropertyValueFactory<StellaGr, Double>("glon_st"));
            gLonCol.setMinWidth(160);
            TableColumn gLatCol = new TableColumn("Latitudine");
            gLatCol.setCellValueFactory(
                    new PropertyValueFactory<StellaGr, Double>("glat_st"));
            gLatCol.setMinWidth(160);
            TableColumn fluxCol = new TableColumn("Flusso");
            fluxCol.setCellValueFactory(
                    new PropertyValueFactory<StellaGr, Double>("flux_st"));
            fluxCol.setMinWidth(160);
            TableColumn typeCol = new TableColumn("Tipo");
            typeCol.setCellValueFactory(
                    new PropertyValueFactory<StellaGr, String>("type_st"));
            typeCol.setMinWidth(160);
            TableColumn distanzaCol = new TableColumn("Distanza");
            distanzaCol.setCellValueFactory(
                    new PropertyValueFactory<StellaGr, Double>("distanza"));
            distanzaCol.setMinWidth(160);

            table.getColumns().addAll(idStarCol, nameStarCol, gLonCol, 
                    gLatCol, fluxCol, typeCol, distanzaCol);
            if (lastIndex == pageIndex) {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage, pageIndex * rowsPerPage + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(data.subList(pageIndex * rowsPerPage, pageIndex * rowsPerPage + rowsPerPage)));
            }


            box.getChildren().add(table);
        }
        return box;
    }
    
    public RisultatiRicercaDistanzaStellaFilamentoController(BeanRispostaStellaFilamento beanRisposta, boolean ordinamentoDistanza) {
        this.beanRisposta = beanRisposta;
        this.listaStelle = beanRisposta.getListaStelle();
        this.listaDistanze = beanRisposta.getListaDistanze();
        this.ordinamentoDistanza = ordinamentoDistanza;
        this.data = getData();
    }
    
    @FXML
    protected void indietro(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.RICERCADISTANZASTELLAFILAMENTO);
    }
    
    @FXML
    protected void menu(ActionEvent event) throws Exception {
        ViewSwap.getInstance().swap(event, ViewSwap.MENU);
    }
    
    @FXML
    protected void ordina(ActionEvent event) throws Exception {
        String tipoOrd = ordinamentoComboBox.getValue();
        if (tipoOrd == null) {
            text.setText("Inserire il tipo di ordinamento");
            return;
        }
        if (tipoOrd.equals("Flusso")) {
            RisultatiRicercaDistanzaStellaFilamentoController risultatiController = new RisultatiRicercaDistanzaStellaFilamentoController(beanRisposta, false); 
            ViewSwap.getInstance().swap(event, ViewSwap.RISULTATIRICERCADISTANZASTELLAFILAMENTO, risultatiController);
        }
        if (tipoOrd.equals("Distanza")) {
            RisultatiRicercaDistanzaStellaFilamentoController risultatiController = new RisultatiRicercaDistanzaStellaFilamentoController(beanRisposta, true); 
            ViewSwap.getInstance().swap(event, ViewSwap.RISULTATIRICERCADISTANZASTELLAFILAMENTO, risultatiController);
        }
    }
    
    @Override
    public void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Flusso");
        items.add("Distanza");
        this.ordinamentoComboBox.setItems(items);
        int lastPage = ((beanRisposta.getListaStelle().size() % rowsPerPage > 0) ? 1 : 0);
        risultatiPagination.setPageCount(beanRisposta.getListaStelle().size() / rowsPerPage + lastPage);
        risultatiPagination.setPageFactory(this::createPage);        
    }
    
    public static class StellaGr {

        private SimpleIntegerProperty idStar;
        private SimpleStringProperty nameStar;
        private SimpleDoubleProperty glon_st;
        private SimpleDoubleProperty glat_st;
        private SimpleDoubleProperty flux_st;
        private SimpleStringProperty type_st;
        private SimpleDoubleProperty distanza;

        private StellaGr(int idStar, String nameStar, double glon_st, 
                double glat_st, double flux_st, String type_st, 
                double distanza) {
            this.idStar = new SimpleIntegerProperty(idStar);
            this.nameStar = new SimpleStringProperty(nameStar);
            this.glon_st = new SimpleDoubleProperty(glon_st);
            this.glat_st = new SimpleDoubleProperty(glat_st);
            this.flux_st = new SimpleDoubleProperty(flux_st);
            this.type_st = new SimpleStringProperty(type_st);
            this.distanza = new SimpleDoubleProperty(distanza);
        }

        public int getIdStar() {
            return idStar.get();
        }

        public void setIdStar(SimpleIntegerProperty idStar) {
            this.idStar = idStar;
        }

        public String getNameStar() {
            return nameStar.get();
        }

        public void setNameStar(SimpleStringProperty nameStar) {
            this.nameStar = nameStar;
        }

        public Double getGlon_st() {
            return glon_st.get();
        }

        public void setGlon_st(SimpleDoubleProperty glon_st) {
            this.glon_st = glon_st;
        }

        public Double getGlat_st() {
            return glat_st.get();
        }

        public void setGlat_st(SimpleDoubleProperty glat_st) {
            this.glat_st = glat_st;
        }

        public Double getFlux_st() {
            return flux_st.get();
        }

        public void setFlux_st(SimpleDoubleProperty flux_st) {
            this.flux_st = flux_st;
        }

        public String getType_st() {
            return type_st.get();
        }

        public void setType_st(SimpleStringProperty type_st) {
            this.type_st = type_st;
        }

        public Double getDistanza() {
            return distanza.get();
        }

        public void setDistanza(SimpleDoubleProperty distanza) {
            this.distanza = distanza;
        }

    }
}
