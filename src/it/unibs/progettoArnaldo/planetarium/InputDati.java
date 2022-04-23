package it.unibs.progettoArnaldo.planetarium;
import java.util.*;


/**
 * 
 * Classe per la lettura dei dati inseriti dall'utente.
 *
 */


public class InputDati 
{
	private static final String MSG_CODICE_PIANETA = "Codice del corpo celeste: ";
	private static final String MSG_POSIZIONE_PIANETA = "Posizione corpo celeste (UM: 10^6 Km)";
	private static final String MSG_MASSA = "Massa del corpo celeste (UM: 10^25 Kg): ";
	private static final String MSG_COORDINATA_Y = "Coordinata Y: ";
	private static final String MSG_COORDINATA_X = "Coordinata X: ";

	private static Scanner lettore = creaScanner();
	  
	  private final static String ERRORE_FORMATO = "Attenzione: il dato inserito non e' nel formato corretto";
	  private final static String ERRORE_MINIMO= "Attenzione: e' richiesto un valore maggiore o uguale a ";
	  private final static String ERRORE_STRINGA_VUOTA= "Attenzione: non hai inserito alcun carattere";
	  private final static String ERRORE_MASSIMO= "Attenzione: e' richiesto un valore minore o uguale a ";
	  private final static String MESSAGGIO_AMMISSIBILI= "Attenzione: i caratteri ammissibili sono: ";

	  private final static char RISPOSTA_SI='S';
	  private final static char RISPOSTA_NO='N';

	  
 
	  private static Scanner creaScanner ()
	  {
	   Scanner creato = new Scanner(System.in);
	   creato.useDelimiter(System.getProperty("line.separator"));
	   return creato;
	  }
	  
	  public static String leggiStringa (String messaggio)
	  {
		  System.out.print(messaggio);
		  return lettore.next();
	  }
	  
	  public static String leggiStringaNonVuota(String messaggio)
	  {
	   boolean finito=false;
	   String lettura = null;
	   do
	   {
		 lettura = leggiStringa(messaggio);
		 lettura = lettura.trim();
		 if (lettura.length() > 0)
		  finito=true;
		 else
		  System.out.println(ERRORE_STRINGA_VUOTA);
	   } while (!finito);
	   
	   return lettura;
	  }
	  
	  public static char leggiChar (String messaggio)
	  {
	   boolean finito = false;
	   char valoreLetto = '\0';
	   do
	    {
	     System.out.print(messaggio);
	     String lettura = lettore.next();
	     if (lettura.length() > 0)
	      {
	       valoreLetto = lettura.charAt(0);
	       finito = true;
	      }
	     else
	      {
	       System.out.println(ERRORE_STRINGA_VUOTA);
	      }
	    } while (!finito);
	   return valoreLetto;
	  }
	  
	  public static char leggiUpperChar (String messaggio, String ammissibili)
	  {
	   boolean finito = false;
	   char valoreLetto = '\0';
	   do
	   {
	    valoreLetto = leggiChar(messaggio);
	    valoreLetto = Character.toUpperCase(valoreLetto);
	    if (ammissibili.indexOf(valoreLetto) != -1)
		 finito  = true;
	    else
	     System.out.println(MESSAGGIO_AMMISSIBILI + ammissibili);
	   } while (!finito);
	   return valoreLetto;
	  }
	  
	  
	  public static int leggiIntero (String messaggio)
	  {
	   boolean finito = false;
	   int valoreLetto = 0;
	   do
	    {
	     System.out.print(messaggio);
	     try
	      {
	       valoreLetto = lettore.nextInt();
	       finito = true;
	      }
	     catch (InputMismatchException e)
	      {
	       System.out.println(ERRORE_FORMATO);
	       String daButtare = lettore.next();
	      }
	    } while (!finito);
	   return valoreLetto;
	  }

	  public static int leggiInteroPositivo(String messaggio)
	  {
		  return leggiInteroConMinimo(messaggio,1);
	  }
	  
	  public static int leggiInteroNonNegativo(String messaggio)
	  {
		  return leggiInteroConMinimo(messaggio,0);
	  }
	  
	  
	  public static int leggiInteroConMinimo(String messaggio, int minimo)
	  {
	   boolean finito = false;
	   int valoreLetto = 0;
	   do
	    {
	     valoreLetto = leggiIntero(messaggio);
	     if (valoreLetto >= minimo)
	      finito = true;
	     else
	      System.out.println(ERRORE_MINIMO + minimo);
	    } while (!finito);
	     
	   return valoreLetto;
	  }

	  public static int leggiIntero(String messaggio, int minimo, int massimo)
	  {
	   boolean finito = false;
	   int valoreLetto = 0;
	   do
	    {
	     valoreLetto = leggiIntero(messaggio);
	     if (valoreLetto >= minimo && valoreLetto<= massimo)
	      finito = true;
	     else
	      if (valoreLetto < minimo)
	         System.out.println(ERRORE_MINIMO + minimo);
	      else
	    	 System.out.println(ERRORE_MASSIMO + massimo); 
	    } while (!finito);
	     
	   return valoreLetto;
	  }

	  
	  public static double leggiDouble (String messaggio)
	  {
	   boolean finito = false;
	   double valoreLetto = 0;
	   do
	    {
	     System.out.print(messaggio);
	     try
	      {
	       valoreLetto = lettore.nextDouble();
	       finito = true;
	      }
	     catch (InputMismatchException e)
	      {
	       System.out.println(ERRORE_FORMATO);
	       String daButtare = lettore.next();
	      }
	    } while (!finito);
	   return valoreLetto;
	  }
	 
	  public static double leggiDoubleConMinimo (String messaggio, double minimo)
	  {
	   boolean finito = false;
	   double valoreLetto = 0;
	   do
	    {
	     valoreLetto = leggiDouble(messaggio);
	     if (valoreLetto >= minimo)
	      finito = true;
	     else
	      System.out.println(ERRORE_MINIMO + minimo);
	    } while (!finito);
	     
	   return valoreLetto;
	  }
	  
	  public static double leggiDouble (String messaggio, double min, double max)
	  {
	   boolean finito = false;
	   double valoreLetto = 0;
	   do
	    {
	     valoreLetto = leggiDouble(messaggio);
	     if (valoreLetto >= min && valoreLetto <= max)
	      finito = true;
	     else
		      if (valoreLetto < min)
		         System.out.println(ERRORE_MINIMO + min);
		      else
		    	 System.out.println(ERRORE_MASSIMO + max); 
	    } while (!finito);
	     
	   return valoreLetto;
	  }
	  
	  public static Posizione leggiPosizione(String messaggio) {
		  System.out.println(messaggio);
		  double x = leggiDouble(MSG_COORDINATA_X);
		  double y = leggiDouble(MSG_COORDINATA_Y);
		  return new Posizione(x, y);
	  }
	  
	  public static CorpoCeleste leggiCorpoCeleste(String messaggio) {
		  System.out.println(messaggio);
		  double massa = leggiDouble(MSG_MASSA, 0, 100000);
		  Posizione posizione = leggiPosizione(MSG_POSIZIONE_PIANETA);
		  String codice = leggiStringaNonVuota(MSG_CODICE_PIANETA).toUpperCase();
		  return new CorpoCeleste(massa, posizione, codice);
	  }
	  
	  public static Pianeta leggiPianeta(String messaggio, Stella stellaOrbita) {
		  System.out.println(messaggio);
		  double massa = leggiDouble(MSG_MASSA, 0, stellaOrbita.getMassa());
		  Posizione posizione = leggiPosizione(MSG_POSIZIONE_PIANETA);
		  String codice = leggiStringaNonVuota(MSG_CODICE_PIANETA).toUpperCase();
		  return new Pianeta(massa, posizione, codice, stellaOrbita);
	  }
	  
	  public static Luna leggiLuna(String messaggio, Pianeta pianetaOrbita) {
		  System.out.println(messaggio);
		  double massa = leggiDouble(MSG_MASSA, 0, pianetaOrbita.getMassa());
		  Posizione posizione = leggiPosizione(MSG_POSIZIONE_PIANETA);
		  String codice = leggiStringaNonVuota(MSG_CODICE_PIANETA).toUpperCase();
		  return new Luna(massa, posizione, codice, pianetaOrbita);
	  }
	  
	  public static Stella leggiStella(String messaggio) {
		  return new Stella(leggiCorpoCeleste(messaggio));
	  }

	  
	  public static boolean yesOrNo(String messaggio)
	  {
		  String mioMessaggio = messaggio + "("+RISPOSTA_SI+"/"+RISPOSTA_NO+")";
		  char valoreLetto = leggiUpperChar(mioMessaggio,String.valueOf(RISPOSTA_SI)+String.valueOf(RISPOSTA_NO));
		  
		  if (valoreLetto == RISPOSTA_SI)
			return true;
		  else
			return false;
	  }

}
