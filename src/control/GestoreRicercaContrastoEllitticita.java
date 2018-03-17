package control;

import bean.BeanRichiestaContrastoEllitticita;
import bean.BeanRispostaContrastoEllitticita;
import boundary.InterfacciaRicercaContrastoEllitticita;
import dao.FilamentoDao;
import entity.Filamento;
import java.sql.Connection;
import java.util.List;
import util.DBAccess;

public class GestoreRicercaContrastoEllitticita {
    private InterfacciaRicercaContrastoEllitticita amministratore;
    
    public GestoreRicercaContrastoEllitticita(InterfacciaRicercaContrastoEllitticita amministratore) {
        this.amministratore = amministratore;
    }
    
    public BeanRispostaContrastoEllitticita ricercaContrastoEllitticita(BeanRichiestaContrastoEllitticita beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        List<Filamento> filamenti = filamentoDao.queryFilamentoContrastoEllitticita(conn, beanRichiesta);
        int totaleFilamenti = filamentoDao.queryNumeroFilamenti(conn);
        double percentuale = (100 * filamenti.size()) / totaleFilamenti;
        BeanRispostaContrastoEllitticita beanRisposta = new BeanRispostaContrastoEllitticita(filamenti, percentuale, true, true);
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }
}
