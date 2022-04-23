package it.unibs.progettoArnaldo.planetarium;
/**
 * Classe utilizzata per gestire la posizione di un corpo sul piano del sistema stellare;
 * l'origine del piano non è specificata e non influisce sul funzionamento del programma
 * (la posizione della stella non deve per forza essere l'origine del piano)
 *
 */
public class Posizione {
		
	
	private double x;
	private double y;
	private static final double EPSILON = 0.001;
	
	
	public Posizione(double x, double y) {
		this.x = x;
		this.y = y;
	}
	

	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	/**
	 * Metodo che serve a confrontare due posizioni con tolleranza EPSILON
	 * @param posizione da confrontare con la posizione su cui viene invocato il metodo
	 * @return true se la posizione coincide, altrimenti false
	 */
	public boolean equals(Posizione p) {
        return  this.x >= (p.x - EPSILON) && this.x <= (p.x + EPSILON) &&
                this.y >= (p.y - EPSILON) && this.y <= (p.y + EPSILON);
    }
	
	/**
	 * Restituisce una stringa contenente le coordinate della posizione
	 */
	public String toString() {
		return String.format("(%4.2f ; %4.2f)", x, y);
	}
	

}
