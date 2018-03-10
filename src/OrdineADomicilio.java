import java.io.*;
public class OrdineADomicilio extends Ordine implements Serializable{
	
	static final long serialVersionUID = 1;
	
	//variabile aggiuntiva per gli ordini a domicilio
	private String indirizzo;
	
	//mi ricollego alla super-classe con "super"
	public OrdineADomicilio(String o, ListaPiatti l, String n, String i) {
		super(o, l, n);
		this.indirizzo = i;
	}
	
	//override per stampare l'oggetto
	public String toString () {
		return orario+" "+lista+" "+nomeCliente+" "+" "+indirizzo;
	}
	
	//override sul metodo di Ordine
	public void getLista(){
		lista.visualizza();
	}
	
	//override sul metodo di Ordine
	public String getOrario(){
		return orario;
	}
	
	//override sul metodo di Ordine
	public String setOrario(String o){
		orario = o;
		return orario;
	}
	
	//override sul metodo di Ordine
	public String getNomeCliente(){
		return nomeCliente;
	}
	
	//override sul metodo di Ordine
	public String setNomeCliente(String n){
		nomeCliente = n;
		return nomeCliente;
	}

}
