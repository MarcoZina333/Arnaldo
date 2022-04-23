package it.unibs.progettoArnaldo.planetarium;

import java.util.ArrayList;


/**
 * 
 *Stella del sisema solare con annessi pianeti che la orbitano
 */
public class Stella extends CorpoCeleste{

	private static final String SCELTA_CORPO_PARTENZA = "Scegliere il corpo di partenza";
	private static final String SCELTA_CORPO_ARRIVO = "Scegliere il corpo di arrivo";
	private static final String ERRORE_RICERCA_CORPO = "Corpo non trovato, codice non valido";
	private static final String MSG_INFORMAZIONI_CORPO = "\nDi che tipo di corpo ti interessano le informazioni?"	+
					   									 "\n(stella: stampa di tutti i pianeti che la orbitano"		+
					   									 "\n pianeta: stampa di tutte le lune che la orbitano"		+
					   									 "\n luna: percorso per raggiungerla partendo dalla stella)";
	private static final String MSG_DISTANZA_PERCORSO = "\nDistanza totale da percorrere: ";
	private static final String MSG_AGGIUNTA_PIANETA_O_LUNA = "\nVuoi aggiungere un pianeta o una luna al sistema?";
	private static final String MSG_CENTRO_MASSA = "\nCentro di massa del sistema stellare: ";
	private static final String MSG_RIMUOVI_LUNA_O_PIANETA = "\nVuoi rimuovere un pianeta o una luna dal sistema?";
	private static final String[]PIANETA_OPPURE_LUNA={"Pianeta","Luna"};
	private static final String[]STELLA_PIANETA_LUNA={"Stella","Pianeta","Luna"};
	public static final String MSG_PERCORSO = "Percorso da seguire: ";
	private static final String ERRORE_PARTENZA_UGUALE_ARRIVO = "\nAttenzione, partenza e arrivo coincidono";
	private static final String ERRORE_INTERNO_PERCORSO = "\nErrore durante la ricerca del percorso";
	private static final String ERRORE_RICERCA_PERCORSO = "\nAttenzione, dati inseriti non validi";
	public static final String ERRORE_DESTINAZIONE_NON_VALIDA = "Corpo celeste non presente nel sistema";
	private static final String MSG_CONTINUA_INPUT_PIANETI = "\nVuoi aggiungere pianeti al sistema?";
	private static final String MSG_INPUT_PIANETA = "Inserire dati del pianeta da aggiungere al sistema";
	private static final String MSG_RIMOZIONE = "Pianeta rimosso correttamente";
	private static final String ERRORE_RIMOZIONE = "Pianeta non trovato";
	private static final String MSG_AGGIUNTA = "Pianeta aggiunto correttamente";
	private static final String ERRORE_POSIZIONE = "Posizione non valida: posizione occupata da un'altro pianeta";
	private static final String ERRORE_CODICE = "Codice non valido: esiste un pianeta con questo codice";
	private static final String ERRORE_OVERFLOW_PIANETI = "Impossibile aggiungere pianeti al sistema, limite fisico raggiunto";
	
	private static final int MAX_PIANETI = 26000;
	
	private ArrayList<Pianeta> pianeti = new ArrayList<Pianeta>();

	public Stella(double massa, Posizione posizione, String codice) {
		super(massa, posizione, codice);
	}
	public Stella(CorpoCeleste c) {
		super(c.getMassa(), c.getPosizione(), c.getCodice());
	}
	
	public ArrayList<Pianeta> getPianeti() {
		return pianeti;
	}
	
	/**
	 * Metodo che aggiunge, se possibile, un pianeta all'arraylist della stella, contenente i pianeti che la orbitano;
	 * se la posizione del pianeta passato come argomento è occupata o il codice è utilizzato o si è raggiunto
	 * il limite fisico di pianeti attorno alla stella, viene restituito un messaggio di errore
	 * @param pianeta che si vuole aggiungere
	 * @return messaggio che indica se l'operazione di aggiunta è avvenuta con successo, altrimenti stampa messaggio di errore
	 */
	public String aggiungiPianeta (Pianeta newPianeta){
		if (pianeti.size() >= MAX_PIANETI)
			return ERRORE_OVERFLOW_PIANETI;
		boolean codicePresente = this.getCodice().equals(newPianeta.getCodice());
		boolean posizioneOccupata = this.getPosizione().equals(newPianeta.getPosizione());
		if (codicePresente) {
			return ERRORE_CODICE;
		}
		else if (posizioneOccupata) {
			return ERRORE_POSIZIONE;
		}
		for (Pianeta p : pianeti) {
			codicePresente = codicePresente || p.getCodice() == newPianeta.getCodice();
			posizioneOccupata = posizioneOccupata || p.getPosizione().equals(newPianeta.getPosizione());
			for (Luna l: p.getLune()) {
				codicePresente = codicePresente || l.getCodice().equals(newPianeta.getCodice());
				posizioneOccupata = posizioneOccupata || l.getPosizione().equals(newPianeta.getPosizione());
			}
		}
		if (!codicePresente && !posizioneOccupata) {
		pianeti.add(newPianeta);
		return MSG_AGGIUNTA;
		}
		else if (codicePresente) {
			return ERRORE_CODICE;
		}
		else {			
			return ERRORE_POSIZIONE;
		}
	}
	
	/**
	 * Metodo che fa scegliere all'utente se aggiungere una luna a un pianeta che orbita la stella, oppure un pianeta che orbita attorno alla stella
	 * facendoglielo inserire da tastiera e aggiungendolo (se possibile) in entrambi i casi al sistema
	 */
	public void aggiungiCorpo() {
		MioMenu pianetaOppureLuna=new MioMenu(PIANETA_OPPURE_LUNA);
		System.out.println(MSG_AGGIUNTA_PIANETA_O_LUNA);
		switch(pianetaOppureLuna.scegli()) {
		
		// Aggiungi un pianeta
			case 1:       
				Pianeta p = InputDati.leggiPianeta("Inserisci il pianeta che ruota attorno a " + this.getCodice(), this);
				System.out.println(this.aggiungiPianeta(p));
				System.out.println();
				break;
				
			//Aggiungi una Luna
			case 2:       
				System.out.println("\nAttorno a quale pianeta orbita?");
			    Pianeta pianetaOrbite = scegliPianeta();
			    if(pianetaOrbite != null) {
			    	Luna l = InputDati.leggiLuna("Inserisci la luna che ruota attorno a "+ pianetaOrbite.getCodice(), pianetaOrbite);
			    	System.out.println(pianetaOrbite.aggiungiLuna(l)); 
			    }
			case 0:
				System.out.println();
				break;
		}
	}
	
	
	
	/**
	 * Rimuove un pianeta con il codice passato come argomento, se esiste, dall'arraylist di pianeti
	 * @param codice del pianeta che si vuole rimuovere
	 * @return true se la rimozione è avvenuta, false altrimenti
	 */
	public boolean rimuoviPianeta (String codice){
		for (Pianeta p: pianeti) {
			if(p.getCodice().equals(codice)) {
				pianeti.remove(p);
				System.out.println(MSG_RIMOZIONE);
				return true;
			}
		}
		System.out.println(ERRORE_RIMOZIONE);
		return false;
	}
	
	/**
	 * Il metodo rimuove un CorpoCeleste (o luna o pianeta) a scelta dal sistema della stella,
	 * ossia rimuoverlo o dall'arraylist di pianeti della stella (se pianeta) o dall'arraylist
	 * di lune del pianeta a cui orbita attorno (a sua volta presente nell'arraylist di pianeti
	 * di questa stella)
	 */
	public void rimuoviCorpoAScelta() {
		MioMenu pianetaOppureLuna=new MioMenu(PIANETA_OPPURE_LUNA);
		System.out.println(MSG_RIMUOVI_LUNA_O_PIANETA);
		switch(pianetaOppureLuna.scegli()) {
		
		// Rimuovi un pianeta
			case 1:
				Pianeta p1=this.scegliPianeta();
				if (p1 != null) {
					this.rimuoviPianeta(p1.getCodice());
				} 
				System.out.println();
				break;
				
			//Rimuovi una Luna
			case 2:
				Luna lunaScelta=this.scegliTraTutteLeLune();
				if (lunaScelta != null) {
					lunaScelta.getPianetaOrbite().rimuoviLuna(lunaScelta.getCodice());
				}
			case 0:
				System.out.println();
				break;
		}
	}
	
	
	/**
	 * Metodo che fa scegliere un corpo celeste del sistema e ne stampa le informazioni:
	 * se si sceglie la stella, verranno stampati tutti i pianeti che gli orbitano attorno;
	 * se si sceglie un pianeta, verranno stampate le lune che ci orbitano attorno;
	 * se si sceglie una luna si stampera il percorso per arrivarci partendo da questa stella, con annessa distanza.
	 */
	public void visualizzaInfoCorpoAScelta() {
		System.out.println(MSG_INFORMAZIONI_CORPO);
		MioMenu pianetaLunaStella=new MioMenu(STELLA_PIANETA_LUNA);
		switch(pianetaLunaStella.scegli()) {
			case 1: //Visualizza lista di pianeti che orbitano attorno alla stella
				System.out.println("Pianeti che orbitano attorno a "+this.getCodice());
				if (this.pianeti.size() == 0)
					System.out.println("Attorno a " + getCodice() + " non orbita nessun pianeta");
				else this.stampaPianeti();
				System.out.println();
				break;
			case 2: //Visualizza lista di lune che orbitano attorno al pianeta scelto
				Pianeta p = this.scegliPianeta();
				//Se attorno al pianeta non orbita nessuna luna, restituisco stringa di errore
				if (p != null) {
					if(p.getLune().size()==0)
						System.out.println("Attorno a " + p.getCodice() + " non orbita nessuna luna");
					
					else
					{
						System.out.println("Lune che orbitano attorno a " + p.getCodice() + ":");
						p.stampaLune();
					}
				}
				System.out.println();
				break;
			case 3: //Dopo aver scelto quale luna, se ne visualizza il percorso necessario per raggiungerla
					//partendo dalla stella
				Luna lunaScelta=this.scegliTraTutteLeLune();
				if (lunaScelta != null)
					System.out.println(this.getPercorsoTo(lunaScelta));
			case 0:
				System.out.println();
		}
	}

	/**Cerca tra i pianeti del sistema una luna con il codice passato come argomento
	 * @param codice del pianeta cercato
	 * @return il pianeta del sistema, se esiste, con codice uguale alla stringa passata come argomento, se non esiste restituisce null
	 */
	public Pianeta trovaPianeta(String codice) {
		codice = codice.toUpperCase();
		for (Pianeta p: pianeti) {
			if(p.getCodice().equals(codice)) {
				return p;
			}
		}
		return null;
	}

	/**Cerca tra le lune del sistema una luna con il codice passato come argomento
	 * @param codice della luna cercato
	 * @return la luna del sistema, se esiste, con codice uguale alla stringa passata come argomento, se non esiste restituisce null
	 */
	public Luna trovaLuna(String codice) {
		codice = codice.toUpperCase();
		Luna luna;
		for(Pianeta p: pianeti) {
			luna = p.trovaLuna(codice);
			if(luna!=null)
				return luna;
		}
		return null;
	}

	/**Cerca tra i corpi celesti del sistema un corpo celeste con il codice passato come argomento
	 * @param codice del corpo celeste
	 * @return il corpo celeste del sistema, se esiste, con codice uguale alla stringa passata come argomento, se non esiste restituisce null
	 */
	public CorpoCeleste trovaCorpoCeleste(String codice) {
		codice = codice.toUpperCase();
		if (getCodice().equals(codice))
			return this;
		Luna luna;
		for(Pianeta p: pianeti) {
			if (p.getCodice().equals(codice))
				return p;
			luna = p.trovaLuna(codice);
			if(luna!=null) {
				return luna;
			}
		}
		return null;
	}
	
	/**Cerca tra i corpi celesti del sistema un corpo celeste con il codice passato come argomento e ne resttituisce le caratteristiche princpali
	 * @param codice del corpo celeste cercato
	 * @return le info sul corpo celeste del sistema, se esiste, con codice uguale alla stringa passata come argomento, se non esiste restituisce una stringa di errore
	 */
	public String trovaInfoCorpoCeleste(String codice) {
		codice = codice.toUpperCase();
		if (getCodice().equals(codice))
			return this.toString();
		Luna luna;
		for(Pianeta p: pianeti) {
			if (p.getCodice().equals(codice))
				return p.toString();
			luna = p.trovaLuna(codice);
			if(luna!=null)
				return luna.toString()+"\nLa luna "+ luna.getCodice() +" orbita attorno a "+luna.getPianetaOrbite().getCodice();
		}
		return ERRORE_RICERCA_CORPO;
	}

	/**
	 * @return la posizione del centro di massa del sistema della stella
	 */
	public Posizione getCentroMassa() {
		double x = getPosizione().getX();
		double y = getPosizione().getY();
		double totaleMasse = getMassa();
		for (Pianeta p: pianeti) {
			totaleMasse+=p.getMassa();
			x += p.getPosizione().getX()*p.getMassa();
			y += p.getPosizione().getY()*p.getMassa();
			for(Luna l: p.getLune()) {
				totaleMasse+=l.getMassa();
				x += l.getPosizione().getX()*l.getMassa();
				y += l.getPosizione().getY()*l.getMassa();
			}
		}
		return new Posizione(x/totaleMasse,y/totaleMasse);
	}
	
	/**
	 * Stampa il centro di massa del sistema
	 */
	public void printCentroMassa() {
		System.out.println(MSG_CENTRO_MASSA + getCentroMassa().toString() + "*10^6 Km");
	}
	
	/**
	 * Metodo che permette all'utente di aggiungere pianeti (finché digita 'n')
	 */
	public void riempiPianeti() {
		while(InputDati.yesOrNo(MSG_CONTINUA_INPUT_PIANETI)){
			if (aggiungiPianeta(InputDati.leggiPianeta(MSG_INPUT_PIANETA, this)) == MSG_AGGIUNTA)
				pianeti.get(pianeti.size()-1).riempiLune();
		};
		
	}

	/**
	 * Metodo che restituisce il percorso (con distanza) da seguire, partendo dalla stella su cui si invoca
	 * il metodo, per arrivare al pianeta mandato come parametro  
	 * @param pianeta da raggiungere
	 * @return percorso in formato stringa dalla stella al pianeta passato come parametro
	 */
	public String getPercorsoTo(Pianeta pianeta) {
		StringBuffer percorso = new StringBuffer(MSG_PERCORSO);
		if (pianeti.contains(pianeta)) {
			percorso.append(pianeta.getPercorsoDaStella());
			percorso.append(MSG_DISTANZA_PERCORSO);
			percorso.append(String.format("% 11.3f", pianeta.distanzaDaStella()));
			percorso.append("*10^6 Km");
			return percorso.toString();
		}
		return ERRORE_DESTINAZIONE_NON_VALIDA;
	}

	/**
	 * Metodo che restituisce il percorso (con distanza) da seguire, partendo dalla stella su cui si invoca
	 * il metodo, per arrivare alla luna mandata come parametro  
	 * @param luna che si vuole raggiungere
	 * @return percorso in formato stringa per raggiungere la luna partendo dalla stella
	 */
	public String getPercorsoTo(Luna luna) {
		StringBuffer percorso = new StringBuffer(MSG_PERCORSO);
		if (pianeti.contains(luna.getPianetaOrbite()))
			if (pianeti.get(pianeti.indexOf(luna.getPianetaOrbite())).getLune().contains(luna)) {
				percorso.append(luna.getPercorsoDaStella());
				percorso.append(MSG_DISTANZA_PERCORSO);
				percorso.append(String.format("% 11.3f", luna.getPianetaOrbite().distanzaDaStella()+luna.distanzaDaPianeta()));
				percorso.append("*10^6 Km");
				return percorso.toString();
			}
		return ERRORE_DESTINAZIONE_NON_VALIDA;
	}
	
	/**
	 * Metodo che fa scegliere all'utente un pianeta che ruota attorno alla stella
	 * @return pianeta del sistema scelto dal menu, se si seleziona '0' restituisce un null
	 */
	public Pianeta scegliPianeta() {
		String[] voci = new String[this.pianeti.size()];
		for (int i = 0; i < this.pianeti.size();i++)
			voci[i] = this.pianeti.get(i).getCodice();
		MioMenu menu = new MioMenu(voci);
		int scelta = menu.scegli()-1;
		if (scelta == -1)
			return null;
		return this.pianeti.get(scelta);
	}

	/**
	 * Metodo che stampa tutti i pianeti (con relative caratteristiche) che orbitano
	 * attorno a questa stella
	 */
	public void stampaPianeti() {
		for (Pianeta p: pianeti) {
			System.out.println(p.toString());
		}
	}
	
	
	/**
	 * 
	 * @return il numero di tutte le lune del sistema stellare
	 */
	public int contaTutteLeLune() {
		int conta = 0;
		for(Pianeta p:pianeti) {
			conta += p.getLune().size();
		}
		return conta;
	}
	
	
	/**
	 * Metodo che consente di scegliere una luna senza passare dal riferimento al pianeta attorno cui orbita
	 * @return luna all'indice scelto
	 */	
	public Luna scegliTraTutteLeLune() {
		String[] voci = new String[contaTutteLeLune()];
		int k=0;
		Luna tutteLune[]=new Luna[contaTutteLeLune()];
		for (int i = 0; i < this.pianeti.size();i++)
			for(int j=0;j<this.pianeti.get(i).getLune().size();j++) {
				voci[k]=this.pianeti.get(i).getLune().get(j).getCodice();
				tutteLune[k]=this.pianeti.get(i).getLune().get(j);
				k++;
			}
		MioMenu menu = new MioMenu(voci);
		int scelta = menu.scegli()-1;
		if (scelta == -1)
			return null;
		return tutteLune[scelta];
	}
	
	/**
	 * Metodo che consente di inserire il codice del corpo celeste di partenza e di arrivo
	 * e che, se possibile, stampa il percorso da seguire dal corpo di partenza a quello
	 * di arrivo, e la distanza totale del viaggio 
	 */	
	public  String ricercaAvanzataRotta() {
		String codiceCorpoCelesteP = InputDati.leggiStringaNonVuota("Inserire il codice del corpo celeste di partenza ").toUpperCase();
		Luna lunaPartenza = null;
		Pianeta pianetaPartenza = null;
		if (!codiceCorpoCelesteP.equals(getCodice())) {
			pianetaPartenza = trovaPianeta(codiceCorpoCelesteP);
			if (pianetaPartenza == null) {
				lunaPartenza = trovaLuna(codiceCorpoCelesteP);
				if (lunaPartenza == null) {
					return ERRORE_RICERCA_PERCORSO;
				}
			}
		}	
		String codiceCorpoCelesteA = InputDati.leggiStringaNonVuota("Inserire il codice del corpo celeste di arrivo ").toUpperCase();
		if (codiceCorpoCelesteA.equals(codiceCorpoCelesteP)) {
			return ERRORE_PARTENZA_UGUALE_ARRIVO;
		}
		Luna lunaArrivo = null;
		Pianeta pianetaArrivo = null;
		if (!codiceCorpoCelesteA.equals(getCodice())) {
			pianetaArrivo = trovaPianeta(codiceCorpoCelesteA);
			if (pianetaArrivo == null) {
				lunaArrivo = trovaLuna(codiceCorpoCelesteA);
				if (lunaArrivo == null) {
					return ERRORE_RICERCA_PERCORSO;
				}
			}
		}
			
		if (codiceCorpoCelesteP.equals(getCodice()) && pianetaArrivo != null && lunaArrivo == null) {
			return getPercorsoTo(pianetaArrivo);
		}
		else if (codiceCorpoCelesteP.equals(getCodice()) && pianetaArrivo == null && lunaArrivo != null) {
			return getPercorsoTo(lunaArrivo);
		}
		
		else if (codiceCorpoCelesteA.equals(getCodice()) && pianetaPartenza != null && lunaPartenza == null) {
			return pianetaPartenza.getPercorsoToStella();
		}
		else if (codiceCorpoCelesteA.equals(getCodice()) && pianetaPartenza == null && lunaPartenza != null) {
			return lunaPartenza.getPercorsoToStella();
		}
		
		else if (pianetaPartenza == null && lunaPartenza != null && pianetaArrivo != null && lunaArrivo == null) {
			return lunaPartenza.getPercorsoTo(pianetaArrivo);
		}
		else if (pianetaPartenza != null && lunaPartenza == null && pianetaArrivo != null && lunaArrivo == null) {
			return pianetaPartenza.getPercorsoTo(pianetaArrivo);
		}
		
		else if (pianetaPartenza == null && lunaPartenza != null && pianetaArrivo == null && lunaArrivo != null) {
			return lunaPartenza.getPercorsoTo(lunaArrivo);
		}
		else if (pianetaPartenza != null && lunaPartenza == null && pianetaArrivo == null && lunaArrivo != null) {
			return pianetaPartenza.getPercorsoTo(lunaArrivo);
		}
		else return ERRORE_INTERNO_PERCORSO;
		
	}
	
	/**
	 * Metodo che consente di scegliere del corpo celeste di partenza e di arrivo
	 * e che, se possibile, stampa il percorso da seguire dal corpo di partenza a quello
	 * di arrivo, e la distanza totale del viaggio 
	 */	
	public String scegliRotta() {
		System.out.println(SCELTA_CORPO_PARTENZA);
		Luna lunaPartenza = null;
		Pianeta pianetaPartenza = null;
		Luna lunaArrivo = null;
		Pianeta pianetaArrivo = null;
		MioMenu pianetaLunaStella = new MioMenu(STELLA_PIANETA_LUNA);
		int sceltaPartenza = pianetaLunaStella.scegli(); 
		switch(sceltaPartenza) {
			case 2:
				pianetaPartenza = scegliPianeta();
				break;
			case 3:
				lunaPartenza = scegliTraTutteLeLune();
			case 0:
			case 1:
				System.out.println();			
		}
		if (sceltaPartenza == 0 || (sceltaPartenza != 1 && pianetaPartenza == null && lunaPartenza == null) )
			return "";
		System.out.println(SCELTA_CORPO_ARRIVO);
		int sceltaArrivo = pianetaLunaStella.scegli(); 
		switch(sceltaArrivo) {
			case 2:
				pianetaArrivo = scegliPianeta();
				break;
			case 3:
				lunaArrivo = scegliTraTutteLeLune();
			case 0:
			case 1:
				System.out.println();			
		}
		if (sceltaArrivo == 0 || (sceltaArrivo != 1 && pianetaArrivo == null && lunaArrivo == null) )
			return "";
		if (sceltaPartenza == 1 && sceltaArrivo == 1)
			return ERRORE_PARTENZA_UGUALE_ARRIVO;
		else if (sceltaPartenza == 1 && sceltaArrivo == 2) {
			return getPercorsoTo(pianetaArrivo);
		}
		else if (sceltaPartenza == 1 && sceltaArrivo == 3) {
			return getPercorsoTo(lunaArrivo);
		}
		
		else if (sceltaArrivo == 1 && sceltaPartenza == 2) {
			return pianetaPartenza.getPercorsoToStella();
		}
		else if (sceltaArrivo == 1 && sceltaPartenza == 3) {
			return lunaPartenza.getPercorsoToStella();
		}

		else if (sceltaPartenza == 2 && sceltaArrivo == 2) {
			return pianetaPartenza.getPercorsoTo(pianetaArrivo);
		}
		else if (sceltaPartenza == 3 && sceltaArrivo == 2) {
			return lunaPartenza.getPercorsoTo(pianetaArrivo);
		}
		
		else if (sceltaPartenza == 3 && sceltaArrivo == 3) {
			return lunaPartenza.getPercorsoTo(lunaArrivo);
		}
		else if (sceltaPartenza == 2 && sceltaArrivo == 3) {
			return pianetaPartenza.getPercorsoTo(lunaArrivo);
		}
		else return ERRORE_INTERNO_PERCORSO;
		
	}
	
	
	
	
}
