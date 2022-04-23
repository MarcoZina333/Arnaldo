package it.unibs.progettoArnaldo.planetarium;


/**
 * 
 * CorpoCeleste superclasse: caratteristiche comuni a tutti i corpi celesti.
 *
 */
public class CorpoCeleste {
	private double massa;
	private Posizione posizione;
	private String codice;
	
	
	/**
	 * Metodo costruttore
	 * @param massa
	 * @param posizione
	 * @param codice univoco del corpo celeste(non case sensitive, viene convertito automaticamente in "Upper case"
	 */
	public CorpoCeleste(double massa, Posizione posizione, String codice) {
		this.massa = massa;
		this.posizione = posizione;
		this.codice = codice.toUpperCase();
	}

	/**
	 * 
	 * @return la massa del corpo
	 */
	public double getMassa() {
		return massa;
	}

	/**
	 * 
	 * @return la posizione del corpo
	 */
	public Posizione getPosizione() {
		return posizione;
	}

	/**
	 * 
	 * @return il codice del corpo
	 */
	public String getCodice() {
		return codice;
	}

	
	/**
	 * Restituisce una stringa contenente tutte le caratteristiche di questo corpo
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("Codice univoco: ");
		str.append(this.codice);
		str.append("; Massa: ");
		str.append(String.format("%.3f*10^25 Kg", this.massa));
		str.append("; Posizione: ");
		str.append(this.posizione.toString());
		str.append("*10^6 Km");
		return str.toString();
	}

	
	

}
