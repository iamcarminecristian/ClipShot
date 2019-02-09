package Controller.GestioneAbbonamento;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Manager.AbbonamentoDAO;
import Model.AbbonamentoBean;

@WebServlet("/SottoscrizioneAbbonamento")
public class SottoscrizioneAbbonamento extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SottoscrizioneAbbonamento() {
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
				AbbonamentoBean temp1 = new AbbonamentoBean();
				try {
					temp1 = abbonamentoDAO.doRetrieveByKey(idUtente);
					if(temp1.getStato().equals("ATTIVO")) { //gi� artista
						RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); 
						requestDispatcher.forward(request, response);
					} else { //sospeso, dispatch ad AttivaAbbonamento
						RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); 
						requestDispatcher.forward(request, response);
					}
				} catch (Exception e2) { //abbonamento dell'utente non trovato
					e2.printStackTrace();
					AbbonamentoBean abbonamentoBean = new AbbonamentoBean();
					abbonamentoBean.setIdUtente(idUtente);
					GregorianCalendar dataScadenza = new GregorianCalendar();
					dataScadenza.add(Calendar.MONTH, 1); //l'abbonamento dura un mese
					abbonamentoBean.setDataScadenza(dataScadenza);
					AbbonamentoBean temp = new AbbonamentoBean();
					try {
						temp = abbonamentoDAO.doRetrieveByCond(idUtente); //conterr� solo la carta relativa all'utente in questione
						abbonamentoBean.setNumeroCarta(temp.getNumeroCarta());
					} catch (Exception e1) { //nessuna carta associata all'utente
						e1.printStackTrace();
						request.setAttribute("from", "from"); //dispatch ad aggiungiCarta ed invia l'attributo from per far visualizzare il tag di tipo hidden che servir� per ritornare qui
						RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); 
						requestDispatcher.forward(request, response);
					}		
					abbonamentoBean.setStato("1"); //attivo
					try {
						abbonamentoDAO.doSave(abbonamentoBean);
					} catch (Exception e) { //errore di salvataggio
						e.printStackTrace();
						RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); 
						requestDispatcher.forward(request, response);
					}
				}				
			} else { //idUtente==null
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); 
				requestDispatcher.forward(request, response);
			}
		} else { //ssn==null
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(""); 
			requestDispatcher.forward(request, response);
		}
	}
}