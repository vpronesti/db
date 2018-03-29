package control;

import bean.BeanRichiestaContrastoEllitticita;
import bean.BeanRispostaContrastoEllitticita;
import boundary.InterfacciaRicercaContrastoEllitticita;
import dao.FilamentoDao;
import entity.Filamento;
import java.sql.Connection;
import java.util.List;
import util.DBAccess;

/**
 * REQ-6
 */
public class GestoreRicercaContrastoEllitticita {
    private InterfacciaRicercaContrastoEllitticita utente;
    
    public GestoreRicercaContrastoEllitticita(InterfacciaRicercaContrastoEllitticita utente) {
        this.utente = utente;   
    }
    
    public BeanRispostaContrastoEllitticita ricercaContrastoEllitticita(BeanRichiestaContrastoEllitticita beanRichiesta) {
        Connection conn = DBAccess.getInstance().getConnection();
        DBAccess.getInstance().disableAutoCommit(conn);
        FilamentoDao filamentoDao = FilamentoDao.getInstance();
        List<Filamento> filamenti = filamentoDao.queryFilamentoContrastoEllitticita(conn, beanRichiesta);
        int totaleFilamenti = filamentoDao.queryNumeroFilamenti(conn);
        double percentuale = (100 * filamenti.size()) / totaleFilamenti;
        BeanRispostaContrastoEllitticita beanRisposta = new BeanRispostaContrastoEllitticita(filamenti, percentuale, true, true);
        DBAccess.getInstance().commit(conn);
        DBAccess.getInstance().closeConnection(conn);
        return beanRisposta;
    }
}
