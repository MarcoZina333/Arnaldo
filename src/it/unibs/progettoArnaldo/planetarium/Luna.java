package it.unibs.progettoArnaldo.planetarium;


/**
 * 
 * Sottoclasse di CorpoCeleste definta dagli attributi di CorpoCelsete e dal pianeta attorno a cui orbita
 *
 */

public class Luna extends CorpoCeleste{
	
	private static final String ERRORE_STESSA_LUNA = "Attenzione, destinazione uguale alla luna di partenza";
	/**
	 * Pianeta attorno al quale orbita la luna
	 */
	private Pianeta pianetaOrbite;
	/**
	 * Costruttori
	 * @param massa
	 * @param posizione
	 * @param codice
	 * @param pianetaOrbite Pianeta attorno al quale orbita la luna
	 */
	public Luna(double massa, Posizione posizione, String codice, Pianeta pianetaOrbite) {
		super(massa, posizione, codice);
		this.pianetaOrbite = pianetaOrbite;
	}
	
	public Luna(CorpoCeleste c, Pianeta pianetaOrbite) {
		super(c.getMassa(), c.getPosizione(), c.getCodice());
		this.pianetaOrbite = pianetaOrbite;
	}
	
	/**
	 * 
	 * @return il pianeta attorno a cui orbita questa luna
	 */
	public Pianeta getPianetaOrbite() {
		return pianetaOrbite;
	}
	
	/**
	 * Metodo che serve a ottenere il percorso per arrivare a questa luna dalla stella attorno alla quale orbita
	 * @return in formato stringa il percorso partendo dalla stella per arrivare alla luna su cui si invoca questo metodo 
	 */
	public String getPercorsoDaStella() {
		return  this.pianetaOrbite.getStellaOrbite().getCodice() + "-->" +
				this.pianetaOrbite.getCodice() + "-->" + getCodice();
	}
	
	
	/**
	 * Metodo che serve a ottenere il percorso per arrivare alla stella attorno alla quale orbita pianetaOrbite, a questa luna
	 * @return in formato stringa il percorso partendo dalla luna per arrivare alla stella 
	 */
	public String getPercorsoToStella() {
		StringBuffer percorso = new StringBuffer(Stella.MSG_PERCORSO);
		percorso.append(getCodice() + "-->" + this.pianetaOrbite.getCodice()+ "-->" +
						this.pianetaOrbite.getStellaOrbite().getCodice() );
		percorso.append("\nDistanza totale da percorrere: ");
		percorso.append(String.format("% 11.3f", distanzaDaPianeta()+this.pianetaOrbite.distanzaDaStella()));
		percorso.append("*10^6 Km");
		return percorso.toString();
	}
	
	/**
	 * Metodo che serve a ottenere il percorso per arrivare al pianeta passato come parametro, partendo da questa luna 
	 * @param pianeta di destinazione
	 * @return stringa contenente il percorso dalla luna su cui si invoca il metodo al pianeta passato come argomento
	 */
	public String getPercorsoTo(Pianeta pianeta) {
		StringBuffer percorso = new StringBuffer();
		if (pianeta.equals(pianetaOrbite)) {
			percorso.append(getCodice() + "-->" + this.pianetaOrbite.getCodice());
			percorso.append("\nDistanza totale da percorrere: ");
			percorso.append(String.format("% 11.3f", distanzaDaPianeta()));
			percorso.append("*10^6 Km");
			percorso.insert(0, Stella.MSG_PERCORSO);
			return percorso.toString();
		}
		percorso.append(this.pianetaOrbite.getStellaOrbite().getPercorsoTo(pianeta));
		if (!percorso.toString().equals(Stella.ERRORE_DESTINAZIONE_NON_VALIDA)) {
			percorso = new StringBuffer(percorso.substring(0, percorso.length()-19));
			percorso.insert(Stella.MSG_PERCORSO.length(), getCodice() + "-->" + this.pianetaOrbite.getCodice() + "-->");
			percorso.append(String.format("% 11.3f", distanzaDaPianeta()+getPianetaOrbite().distanzaDaStella()+pianeta.distanzaDaStella() ));
			percorso.append("*10^6 Km");
		}
		return percorso.toString();
	}
	
	/**
	 * Metodo che serve a ottenere il percorso per arrivare alla luna passato come parametro, partendo da questa luna 
	 * @param luna di destinazione
	 * @return stringa contenente il percorso dalla luna su cui si invoca il metodo alla luna passata come argomento, se queste coincidono viene restituito un messaggio di errore
	 */
	public String getPercorsoTo(Luna luna) {
		if (luna.equals(this))
			return ERRORE_STESSA_LUNA;
		StringBuffer percorso = new StringBuffer();
		percorso.append(this.pianetaOrbite.getStellaOrbite().getPercorsoTo(luna));
		if (!percorso.toString().equals(Stella.ERRORE_DESTINAZIONE_NON_VALIDA)) {
			percorso = new StringBuffer(percorso.substring(0, percorso.length()-19));
			percorso.insert(Stella.MSG_PERCORSO.length(), getCodice() + "-->" + this.pianetaOrbite.getCodice() + "-->");
			percorso.append(String.format("% 11.3f", distanzaDaPianeta()+getPianetaOrbite().distanzaDaStella()+luna.distanzaDaPianeta()+luna.getPianetaOrbite().distanzaDaStella()));
			percorso.append("*10^6 Km");
		}
		return percorso.toString();
	}

	/**
	 * 
	 * @return distanza (double) tra la luna su cui si invoca il metodo e il pianeta attorno cui orbita
	 */
	public double distanzaDaPianeta() {
		return Math.sqrt( Math.pow(getPianetaOrbite().getPosizione().getX() - getPosizione().getX(), 2) + Math.pow(getPianetaOrbite().getPosizione().getY() - getPosizione().getY(), 2));
	}
	
}
