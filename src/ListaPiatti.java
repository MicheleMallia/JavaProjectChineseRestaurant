import java.util.*;
import java.io.*;

public class ListaPiatti implements Serializable {
	
	static final long serialVersionUID = 1;
	
	//vettore che contiene i piatti
	private Vector<Piatto> listaPiatti;
	
	//costruttore
	public ListaPiatti(){
		listaPiatti = new Vector<Piatto>();
	}
	
	//inserisce un piatto alla lista
	public void inserisci(Piatto p){
		if(!presente(p)){
			listaPiatti.add(p);
		}
	}
	
	//restituisce un piatto tramite l'indice della lista
	public Piatto getPiatto(int i){
		return listaPiatti.get(i);
	}
	
	//setta la quantità di un piatto
	public Piatto setPiattoQuantita(int i, Piatto p){
		return listaPiatti.set(i, p);
	}
	
	//rimuove un piatto
	public void rimuovi(Piatto p){
		if(presente(p)){
			listaPiatti.remove(p);
		}
	}
	
	//verifica la presenza di un piatto
	public boolean presente(Piatto p){
		return listaPiatti.contains(p);
	}
	
	
	//restituisce l'indice di un piatto 
	public int indexOf(Piatto p){
		return listaPiatti.indexOf(p);
	}
	
	//verifica se la lista dei Piatti è vuota
	public boolean isVuota(){
		return listaPiatti.isEmpty();
	}
	
	//visualizza la lista dei piatti
	public void visualizza(){
		System.out.println(listaPiatti);
	}
	
	//overrides sul metodo toString()
	public String toString(){
		return listaPiatti.toString();
	}
}
