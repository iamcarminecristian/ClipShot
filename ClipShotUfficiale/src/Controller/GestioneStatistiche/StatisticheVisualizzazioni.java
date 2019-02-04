package Controller.GestioneStatistiche;

import java.io.IOException;
import java.util.GregorianCalendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Manager.AbbonamentoDAO;
import Manager.StatisticheDAO;
import Model.AbbonamentoBean;
import Model.StatisticheBean;

@WebServlet("/StatisticheVisualizzazioni")
public class StatisticheVisualizzazioni extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public StatisticheVisualizzazioni() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ssn = request.getSession();
		if(ssn != null) {
			String idUtente = (String) ssn.getAttribute("idUtente");
			if(idUtente != null) {
				AbbonamentoDAO abbonamentoDAO = new AbbonamentoDAO();
				AbbonamentoBean abbonamentoBean = new AbbonamentoBean();
				try {
					abbonamentoBean = abbonamentoDAO.doRetrieveByKey(idUtente);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if(abbonamentoBean.getStato().equals("ATTIVO") && new GregorianCalendar().before(abbonamentoBean.getDataScadenza())) {
					StatisticheDAO statisticheDAO = new StatisticheDAO();
					StatisticheBean statisticheBean = new StatisticheBean();
					try {
						statisticheBean = statisticheDAO.doRetrieveByKey(idUtente);
						request.setAttribute("numeroVisualizzazioni", statisticheBean.getNumeroVisualizzazioni());
						RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); 
						requestDispatcher.forward(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else { // abbonamento SOSPESO opp. SCADUTO
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); 
					requestDispatcher.forward(request, response);
				}
			} else { //idUtente == null
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); 
				requestDispatcher.forward(request, response);
			}
		} else { //ssn == null
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); 
			requestDispatcher.forward(request, response);
		}
	}
}
