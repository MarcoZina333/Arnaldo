package it.unibs.progettoArnaldo.planetarium;

import java.util.ArrayList;

/**
 * Pianeta: sottoclasse di CorpoCeleste; 
 * è definito dalla stella attorno a cui orbita e dalla lista di lune che gli orbitano attorno
*/
public class Pianeta extends CorpoCeleste{


	private static final String ERRORE_OVERFLOW_LUNE = "Impossibile aggiungere lune al pianeta, limite fisico raggiunto";
	private static final String MSG_CONTINUA_RIEMPI_LUNE = "Vuoi inserire lune intorno al pianeta ";
	private static final String ERRORE_STESSO_PIANETA = "Attenzione, destinazione uguale al pianeta di partenza";
	private static final String MSG_RIMOZIONE = "Luna rimossa correttamente";
	private static final String ERRORE_RIMOZIONE = "Luna non trovata";
	private static final String MSG_AGGIUNTA = "Luna aggiunta correttamente";
	private static final String ERRORE_POSIZIONE = "Posizione non valida: posizione occupata da un'altro corpo celeste";
	private static final String ERRORE_CODICE = "Codice non valido: esiste un corpo celeste con questo codice";
	
	private static final int MAX_LUNE = 5000;
	/**
	 * Stella attorno alla quale orbita il pianeta
	 */
	private Stella stellaOrbite;
	/**
	 * ArrayList delle lune che orbitano attorno al pianeta
	 */
	private ArrayList<Luna> lune = new ArrayList<Luna>();
	
	/**
	 * Metodi costruttori 
	 * @param massa
	 * @param posizione
	 * @param codice
	 * @param stellaOrbite stella attorno alla quale orbita il pianeta
	 */
	public Pianeta(double massa, Posizione posizione, String codice, Stella stellaOrbite) {
		super(massa, posizione, codice);
		this.stellaOrbite = stellaOrbite;
	}
	/**
	 * 
	 * @param c oggetto corpo celeste da cui ereditera gli attributi
	 * @param stellaOrbite
	 */
	public Pianeta(CorpoCeleste c, Stella stellaOrbite) {
		super(c.getMassa(), c.getPosizione(), c.getCodice());
		this.stellaOrbite = stellaOrbite;
	}
	
	/**
	 * Metodo get per estrarre stella attorno cui orbita il pianeta
	 * @return la stella attorno a cui orbita il pianeta
	 */
	public Stella getStellaOrbite() {
		return stellaOrbite;
	}

	/**
	 * Metodo per aggiungere una luna all'arraylist lune
	 * @param luna che si vuole aggiungere
	 * @return un messaggio per indicare se l'operazione di aggiunta è stata svolta correttamente
	 */
	public String aggiungiLuna (Luna newLuna){
		//Controllo del limite fisico massimo di lune che orbitano il pianeta
		if (lune.size() >= MAX_LUNE)
			return ERRORE_OVERFLOW_LUNE;
		//Se è già presente oppure occupa la stessa posizione di un altro corpo celeste, restituisce un messaggio di errore
		boolean codicePresente = false || this.stellaOrbite.getCodice().equals(newLuna.getCodice());
		boolean posizioneOccupata = false || this.stellaOrbite.getPosizione().equals(newLuna.getPosizione());
		if (codicePresente) {
			return ERRORE_CODICE;
		}
		else if (posizioneOccupata) {
			return ERRORE_POSIZIONE;
		}
		for (Luna l : lune) {
			codicePresente = codicePresente || l.getCodice().equals(newLuna.getCodice());
			posizioneOccupata = posizioneOccupata || l.getPosizione().equals(newLuna.getPosizione());
		}
		for (Pianeta p : stellaOrbite.getPianeti()) {
			codicePresente = codicePresente || p.getCodice().equals(newLuna.getCodice());
			posizioneOccupata = posizioneOccupata || p.getPosizione().equals(newLuna.getPosizione());
		}
		if (!codicePresente && !posizioneOccupata) {
		lune.add(newLuna);
		return MSG_AGGIUNTA;
		}
		else if (codicePresente) {
			return ERRORE_CODICE;
		}
		else {
			return ERRORE_POSIZIONE;
		}
	}
	
	/**Metodo per rimuovere luna (se non presente ritorna un messaggio di errore)
	 * 
	 * @param codice della luna da rimuovere
	 * @return true se la rimozione è avvenuta, false altrimenti
	 */
	public boolean rimuoviLuna (String codice){
		for (Luna l : lune) {
			if (l.getCodice().equals(codice)) {
				lune.remove(l);
				System.out.println(MSG_RIMOZIONE);
				return true;
			}
		}
		System.out.println(ERRORE_RIMOZIONE);
		return false;
	}
	
	/**
	 * Stampa tutte le lune orbitanti il pianeta
	 */
	public void stampaLune() {
		int count = 0;
		for (Luna l: lune) {
			System.out.println(l.toString());
			count++;
		}
		if(count==0)
			System.out.println("Attorno a "+getCodice()+" non ruota nessuna luna");
	}
	
	/**Metodo per cercare luna a partire dal codice
	 * 
	 * @param codice della luna da cercare tra quelle che orbitano attorno al pianeta
	 * @return l'oggetto Luna con codice uguale a quello passato come argomento; se non esiste restituisce null
	 */
	public Luna trovaLuna(String codice) {
		codice = codice.toUpperCase();
		for(Luna l: lune) {
			if (l.getCodice().equals(codice)) {
				return l;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return arraylist delle lune orbitanti il pianeta
	 */
	public ArrayList<Luna> getLune(){
		return lune;
	}
	
	/**Metodo per chiedere all'utente di inserire nuove lune (fino a quando la risposta dell'utente è 'n', cioe no)
	 * 
	 */
	public void riempiLune() {
		while(InputDati.yesOrNo(MSG_CONTINUA_RIEMPI_LUNE + this.getCodice() +" ?")){
			aggiungiLuna(InputDati.leggiLuna("Inserire dati della luna di "+ this.getCodice() +" da aggiungere", this));
		};
	}

	/**
	 * Metodo per ottenere la distanza, in formato double, tra il pianeta e la stella attorno a cui orbita
	 * @return distanza del pianeta su cui è invocato il metodo dalla stella
	 */
	public double distanzaDaStella() {
		return Math.sqrt( Math.pow(getStellaOrbite().getPosizione().getX() - getPosizione().getX(), 2) + Math.pow(getStellaOrbite().getPosizione().getY() - getPosizione().getY(), 2));
	}
	
	/**
	 * Metodo che serve a ottenere il percorso per arrivare a questo pianeta dalla stella attorno alla quale orbita
	 * @return percorso in formato stringa partendo dalla stella per arrivare al pianeta
	 */
	public String getPercorsoDaStella() {
		return  this.stellaOrbite.getCodice() + "-->" + getCodice();
		
	}
	
	/**
	 * Metodo che serve a ottenere il percorso da questo pianeta alla stella attorno a cui orbita
	 * @return percorso in formato stringa partendo dal pianeta per arrivare alla stella con annessa distanza totale
	 */
	public String getPercorsoToStella() {
		StringBuffer percorso = new StringBuffer(Stella.MSG_PERCORSO);
		percorso.append(getCodice() + "-->" + getStellaOrbite().getCodice() );
		percorso.append("\nDistanza totale da percorrere: ");
		percorso.append(String.format("% 11.3f", distanzaDaStella() ) );
		percorso.append("*10^6 Km");
		return percorso.toString();
	}
	
	/**
	 * Metodo che serve a ottenere il percorso da questo pianeta a un'altro pianeta del suo sistema
	 * @return percorso in formato stringa partendo dal pianeta per arrivare al pianeta passato come parametro
	 * @param pianeta che si vuole raggiungere
	 */
	public String getPercorsoTo(Pianeta pianeta) {
		if (pianeta.equals(this))
			return ERRORE_STESSO_PIANETA;
		StringBuffer percorso = new StringBuffer();
		percorso.append(this.getStellaOrbite().getPercorsoTo(pianeta));
		if (!percorso.toString().equals(Stella.ERRORE_DESTINAZIONE_NON_VALIDA)) {
			percorso = new StringBuffer(percorso.substring(Stella.MSG_PERCORSO.length(), percorso.length()-19));
			percorso.insert(0, getCodice() + "-->");
			percorso.append(String.format("% 11.3f", distanzaDaStella()+pianeta.distanzaDaStella()));
			percorso.append("*10^6 Km");
		}
		percorso.insert(0, Stella.MSG_PERCORSO);
		return percorso.toString();
	}
	
	/**
	 * Metodo che serve a ottenere il percorso da questo pianeta a una luna del suo sistema
	 * @return percorso in formato stringa partendo dal pianeta per arrivare alla luna passata come parametro
	 * @param luna che si vuole raggiungere
	 */
	public String getPercorsoTo(Luna luna) {
		StringBuffer percorso = new StringBuffer();
		if (this.getLune().contains(luna)) {
			percorso.append(getCodice() + "-->" + luna.getCodice() );
			percorso.append("\nDistanza totale da percorrere: ");
			percorso.append(String.format("% 11.3f", luna.distanzaDaPianeta()));
			percorso.append("*10^6 Km");
			percorso.insert(0, Stella.MSG_PERCORSO);
			return percorso.toString();
		}
		percorso.append(this.getStellaOrbite().getPercorsoTo(luna));
		if (!percorso.toString().equals(Stella.ERRORE_DESTINAZIONE_NON_VALIDA)) {
			percorso = new StringBuffer(percorso.substring(Stella.MSG_PERCORSO.length(), percorso.length()-19));
			percorso.insert(0, getCodice() + "-->");
			percorso.append(String.format("% 11.3f", distanzaDaStella()+luna.distanzaDaPianeta()+luna.getPianetaOrbite().distanzaDaStella()));
			percorso.append("*10^6 Km");
		}
		percorso.insert(0, Stella.MSG_PERCORSO);
		return percorso.toString();
	}
	
	/**
	 * 
	 * @return luna che si trova all'indice scelto 
	 */
	public Luna scegliLuna() {
		String[] voci = new String[this.lune.size()];
		for (int i = 0; i < this.lune.size();i++)
			voci[i] = this.lune.get(i).getCodice();
		MioMenu menu = new MioMenu(voci);
		int scelta = menu.scegli()-1;
		if (scelta == -1)
			return null;
		return this.lune.get(scelta);
	}
	

	
	
}



