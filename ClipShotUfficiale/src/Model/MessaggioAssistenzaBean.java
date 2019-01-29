package Model;

import java.sql.Date;
import java.sql.Time;

public class MessaggioAssistenzaBean {
	private String idOperatore;
	private String idUtente;
	private Date dataMessaggio;
	private Time ora;
	private String oggetto;
	private String tipo;
	private String corpo;
	
	
	public MessaggioAssistenzaBean() {
	}
	
	public MessaggioAssistenzaBean(String idDestinatario, String idMittente, Date data, Time ora, String corpo) {
		
		this.idOperatore = idDestinatario;
		this.idUtente = idMittente;
		this.dataMessaggio = data;
		this.ora = ora;
		this.corpo = corpo;
	}
	
	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(String idOperatore) {
		this.idOperatore = idOperatore;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public Date getDataMessaggio() {
		return dataMessaggio;
	}

	public void setDataMessaggio(Date data) {
		this.dataMessaggio = data;
	}

	public Time getOra() {
		return ora;
	}

	public void setOra(Time ora) {
		this.ora = ora;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	
	
}
