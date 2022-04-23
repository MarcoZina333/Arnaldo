package it.unibs.progettoArnaldo.planetarium;

/**
 * 
 *Classe di utilita per gestire le scelte dell'utente
 */
public class MioMenu {
	
	final private static String VOCE_USCITA = "0\tEsci";
	  final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata > ";
	  final private static String CORNICE = "\t***********************\n"
										  + "\t*                     *\n"
										  + "\t*     PLANETARIUM     *\n"
										  + "\t*                     *\n"
										  + "\t***********************\n";
	/**
	 * Voci del menu
	 */
	  private String [] voci;

	  public MioMenu(String[] voci) {
		this.voci = voci;
	}
	/**
	 * Metodo che fa sceglire all'utente tra le voci dell'istanza, con un opzione per uscire (sempre 0)
	 * @return l'intero corrispondente all'indice dell voce scelta+1 (con la scelta 0 si esce sempre)
	 */
	public int scegli ()
	  {
		stampaMenu();
		return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);	 
	  }
	  /**
	   * Stampa le voci del menu	
	   */
	  public void stampaMenu ()
	  {
		System.out.println();
	    for (int i=0; i<voci.length; i++)
		 {
		  System.out.println( (i+1) + "\t" + voci[i]);
		 }
	    System.out.println();
		System.out.println(VOCE_USCITA);
	    System.out.println();
	  }
	  /**
	   * Stampa il saluto iniziale
	   */
	  public static void salutoIniziale() {
		  System.out.println(CORNICE);
	  }
}
