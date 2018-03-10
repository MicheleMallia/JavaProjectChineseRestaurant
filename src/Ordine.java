import java.io.*;
public class Ordine implements Serializable{
	
	static final long serialVersionUID = 1;
	
	//componenti della classe
	public String orario, nomeCliente;
	public ListaPiatti lista;
	
	//costruttore
	public Ordine(String o, ListaPiatti l, String n) {
		this.orario = o;
		this.lista = l;	
		this.nomeCliente = n;
	}

	//stampa l'oggetto
	public String toString () {
		return orario+" "+lista+" "+nomeCliente;
	}
	
	//metodo per visualizzare la lista
	public void getLista(){
		lista.visualizza();
	}
	
	//metodo per ottenere l'orario
	public String getOrario(){
		return orario;
	}
	
	//metodo per modificare l'orario
	public String setOrario(String o){
		orario = o;
		return orario;
	}
	
	//metodo per ottenere il nome del cliente
	public String getNomeCliente(){
		return nomeCliente;
	}
	
	//metodo per settare il nome del cliente
	public String setNomeCliente(String n){
		nomeCliente = n;
		return nomeCliente;
	}
	
	//verifica se l'oggetto passato come parametro si configura come un Ordine
	public boolean equals ( Object obj) {
		if (obj instanceof Ordine ) {
		Ordine o = ( Ordine ) obj;
		return ( nomeCliente . equals (o. nomeCliente ) && lista . equals (o. lista ) && orario.equals(o.orario));
		}
		else return false ;
	}
}
