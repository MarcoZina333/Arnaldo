package it.unibs.progettoArnaldo.planetarium;

/**
 * 
 * @author Gabriel,Giulia,Marco;
 * 
 *
 */

public class Main {
	
	private static final String CORNICE = "************************************************";
	private static final String MSG_INPUT_CORPO = "Inserisci il codice del corpo: ";
	final static private String INTESTAZIONE_SISTEMA_VUOTO = "Inserisci i dati identificativi della Stella attorno cui orbita il nuovo sistema stellare";
	final static private String[] SCELTA_SISTEMA_STELLARE= {"Sistema stellare vuoto","Sistema solare"};
	final static private String[] OPZIONI_MENU={"Aggiungi","Rimuovi","Cerca","Visualizza informazioni","Centro di massa", "Ricerca rotta per codice", "Scelta rotta"};
	

	final static private String COD_SOLE = "SOLE";
	final static private String COD_TERRA="TERRA";
	final static private String COD_LUNA="LUNA";
	final static private String COD_MARTE="MARTE";
	final static private String COD_PHOBOS="PHOBOS";
	final static private String COD_DEIMOS = "DEIMOS";
	final static private String COD_SATURNO="SATURNO";
	final static private String COD_TITANO="TITANO";

	
	
	
	
	public static void main(String[] args) {
		
		Stella stella = null;
		MioMenu.salutoIniziale();
		MioMenu menuApertura = new MioMenu(SCELTA_SISTEMA_STELLARE);
		MioMenu menu = new MioMenu(OPZIONI_MENU) ;
		
		//Menu iniziale che consente all'utente di scegliere tra un sistema stellare vuoto e uno già predisposto
		switch(menuApertura.scegli()) {
		case 1:
			stella = InputDati.leggiStella(INTESTAZIONE_SISTEMA_VUOTO);
			stella.riempiPianeti();
			break;
		case 2:
			stella = inizializzaSistSolare();
			break;
		case 0:
			return;
		}
		//Menu principale
		int scelta = 0;
		System.out.println();
		do {
			System.out.println(CORNICE);
			System.out.println();
			System.out.println("\t\tMENU PRINCIPALE");		
			switch(scelta=menu.scegli()) {
							
				//Aggiungi				
				case 1:
					stella.aggiungiCorpo();
					break;
				
				//Rimuovi	
				case 2:
					stella.rimuoviCorpoAScelta();
					break;
					
					//Cerca un corpo celeste di un tipo qualsiasi (stella, luna o pianeta) all'interno del sistema
					//e ne restituisce informazioni identificative (massa,codice,posizione)	
				case 3:
					System.out.println(stella.trovaInfoCorpoCeleste(InputDati.leggiStringa(MSG_INPUT_CORPO)));
					System.out.println();
					break;
					
				//Visualizza informazioni di un corpo a scelta		
				case 4:
					stella.visualizzaInfoCorpoAScelta();
					break;
					
				//Centro di massa del sistema		
				case 5:
					stella.printCentroMassa();
					System.out.println();
					break;
				
				//Ricerca rotta per codice
				case 6:
					System.out.println(stella.ricercaAvanzataRotta());
					System.out.println();
					break;
				//Ricerca rotta con scelta	
				case 7:
					System.out.println(stella.scegliRotta());
					System.out.println();
					break;
			    //Uscita dal programma
				case 0:
					return;
			}
		}while(scelta!=0);
	}
	
	/**
	 * Metodo che inizializza il nostro sistema solare 
	 * @return Ritorna la stella
	 */
	public static Stella inizializzaSistSolare() {
		Stella sole = new Stella (1989,new Posizione(0,0),COD_SOLE);
		Pianeta terra = new Pianeta (597,new Posizione(2,3),COD_TERRA,sole);
		Luna luna = new Luna (7,new Posizione(3,4),COD_LUNA,terra);
		terra.aggiungiLuna(luna);
		sole.aggiungiPianeta(terra);
		Pianeta marte = new Pianeta (64,new Posizione(5,6),COD_MARTE,sole);
		Luna fobos = new Luna (3,new Posizione(6,6),COD_PHOBOS,marte);
		Luna deimos = new Luna (0.3,new Posizione(5,5), COD_DEIMOS,marte);
		marte.aggiungiLuna(fobos);
		marte.aggiungiLuna(deimos);
		sole.aggiungiPianeta(marte);
		Pianeta saturno = new Pianeta (875,new Posizione (15,21),COD_SATURNO,sole);
		Luna titano = new Luna (13,new Posizione (16,22),COD_TITANO,saturno);
		saturno.aggiungiLuna(titano);
		sole.aggiungiPianeta(saturno);
		return sole;
	}

	
	
	
}

		
		
		

		
	
	

	
	

