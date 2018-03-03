package bean;

import entity.Filamento;
import java.util.List;

public class BeanRispostaFilamenti {
//    private List<Integer> filamenti;
//    
//    public BeanRispostaFilamenti(List<Integer> filamenti) {
//        this.filamenti = filamenti;
//    }
//
//    public List<Integer> getFilamenti() {
//        return filamenti;
//    }
//
//    public void setFilamenti(List<Integer> filamenti) {
//        this.filamenti = filamenti;
//    }
    
    private List<Filamento> filamenti;
    
    public BeanRispostaFilamenti(List<Filamento> filamenti) {
        this.filamenti = filamenti;
    }

    public List<Filamento> getFilamenti() {
        return filamenti;
    }

    public void setFilamenti(List<Filamento> filamenti) {
        this.filamenti = filamenti;
    }
}
