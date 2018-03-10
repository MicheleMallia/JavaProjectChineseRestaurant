import java.io.*;

public class Piatto implements Serializable{
	
	static final long serialVersionUID = 1;
	
	//nome del piatto
	public String nomePiatto;
	
	//costruttore
	public Piatto(String n){
		this.nomePiatto = n;
	}
	
	//visualizza il piatto
	public String toString () {
		return nomePiatto;
	}
	
	//verifica se si tratta di un oggetto Piatto
	public boolean equals ( Object o) {
		if (o instanceof Piatto ) {
		Piatto p = ( Piatto ) o;
		return ( nomePiatto . equals (p. nomePiatto ));
		}
		else return false ;
	}
	
	//combina la quantità con il nome del piatto
	public String setQuantita(int quantita){
		nomePiatto = Integer.toString(quantita)+" "+nomePiatto;
		return nomePiatto;
	}
	
}
