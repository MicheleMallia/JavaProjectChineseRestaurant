import java.util.Vector;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;


public class Prenotazioni implements Serializable{
	
	static final long serialVersionUID = 1;
	
	//vettore che conterrà tutte le prenotazioni
	public Vector<Ordine> prenotazioni;
	
	//costruttore della classe, quando viene creato un oggetto Prenotazioni
	//non è altro che un vettore vuoto
	public Prenotazioni(){
		prenotazioni = new Vector<Ordine>();
	}
	
	
	//verifica la presenza di un ordine nella lista
	public boolean presente(Ordine o){
		return prenotazioni.contains(o);
	}
	
	//inserisce un oggetto di tipo Ordine nella lista
	public void inserisci(Ordine o){
		if(!presente(o)){
			prenotazioni.add(o);
		}
	}
	
	//rimuove il prossimo ordine da consegnare
	public void rimuoviNextOrdine(Ordine o){
		if(presente(o)){
			prenotazioni.remove(o);
		}
	}
	
	//unisce le quantita dei piatti e il piatto selezionato dall'utente
	public void setOrdine(Ordine o, int i){
		if(!presente(o)){
			prenotazioni.set(i, o);
		}
	}
	
	//restituisce la grandezza della lista delle prenotazioni
	public int getSize(){
		return prenotazioni.size();
	}
	
	//restituisce un oggetto Ordine dato il suo indice nella lista delle prenotazioni
	public Ordine getIndex(int i){
		return prenotazioni.get(i);
	}
	
	
	//overrides sul metodo toString()
	public String toString(){
		return prenotazioni.toString();
	}
	
	//metodo per ordinare la lista in base all'orario
	//restituisce la lista ordinata delle Prenotazioni
	public Prenotazioni ordinaLista(Prenotazioni p){
		
		Vector<Integer> n = new Vector<Integer>(); //vettore che contiene i valori numerici dell'ora (per chiarire: le 23 e 04 minuti diventano 2304 nel vettore numerico.
		Vector<String> s = new Vector<String>(); //vettore che contiene le stringhe nuove delle ore ordinate in maniera crescente
		Prenotazioni nuovaP = new Prenotazioni(); //nuovo vettore con le prenotazioni ordinate in base all'orario
		
		
		//le stringhe contententi l'ora vengono convertite in interi
		//vengono considerate soltanto le cifre senza il simbolo : in mezzo
		for(int i =0; i<p.getSize();i++){
		
			int orario=0;
			
			//si assicura che l'orario inserito sia valido
			//ovvero composto da cinque elementi nella stringa
			//due cifre più il simbolo : separatore
			if(p.getIndex(i).getOrario().length() == 5){
				orario = Integer.parseInt(p.getIndex(i).getOrario().substring(0,2)+p.getIndex(i).getOrario().substring(3, 5));
			}
			n.addElement(orario);
		}
		
		//Selection sort applicato alle vettore n
		//contenente i valori numeri delle ore
		for (int i = 0; i<n.size()-1;i++){
			int posmin = i;
			for(int j = i; j<n.size(); j++){
				if(n.get(j)<n.get(posmin))
					posmin=j;
			}
			
			int tmp = n.get(i);
			n.set(i, n.get(posmin));
			n.set(posmin, tmp);
		}
		
		//vengono ricostituite le stringhe con gli orari ordinati
		for(int i = 0; i<n.size();i++){
			
			String neu = n.get(i).toString();
			String orario ="";
			
			if(neu.length()==4){orario = neu.substring(0,2)+":"+neu.substring(2,4);}
			s.addElement(orario);
		}
		
		//viene riempita la nuova lista seguendo l'ordine degli orari inseriti nel
		//vettore s, prendendo gli ordini dalla lista p
		for(int i =0; i<p.getSize();i++){
			for(int j = 0; j<p.getSize(); j++){
				if(p.getIndex(j).getOrario().equals(s.elementAt(i))){
					nuovaP.inserisci(p.getIndex(j));
				}
			}
		}
		
		//viene restituita la lista con gli orari in ordine
		return nuovaP;
	}
	
	
	//metodo per visualizzare gli ordini in base al nome del cliente
	public void visualizzaPerNome(Prenotazioni nuovaP){
		
		System.out.println("Ricerca ordini per nome cliente:");
		Scanner input = new Scanner(System.in);
		boolean nomeOK=false;
		int cont = 0;
		do{
			//l'utente può effettuare la ricerca tramite il nome esatto e parte del nome
			System.out.println();
			int scelta;
			System.out.println("Inserire 1 per ricercare gli ordini in base al nome; inserire 2 per ricercare gli ordini in base al nome parziale:");
			System.out.println();
			try{
				scelta = input.nextInt();
			}catch(InputMismatchException e){
				input.nextLine();
				System.out.println("Inserisci un valore corretto es: Michele");
				scelta = -1;
			}
			//in base a quale scelta viene effettuata vengono effettuate due stesse ricerche
			//ma con criteri diversi: per la prima scelta viene usato il metodo matches()
			//per la seconda scelta viene usato il metodo lookingAt();
			if(scelta==1){
				
				String nomeC ="";
				System.out.println("Inserire il nome:");
				System.out.println();
			
				nomeC = input.next();
				
				//per evitare che vengano inseriti nomi
				//in minuscolo o con numeri
				String regex = "([A-Z])([a-z])+";
				
				Pattern r = Pattern.compile(regex);				
				
				Matcher m = r.matcher(nomeC);
				
				if(m.matches()){
					
					Pattern pattern;
					Matcher matcher;
					
					for(int i =0; i<nuovaP.getSize();i++){
						
						pattern = Pattern.compile(nomeC.toLowerCase());
						matcher = pattern.matcher(nuovaP.getIndex(i).getNomeCliente());
						if(matcher.matches()){
							System.out.println("	"+nuovaP.getIndex(i));
							cont++;
							
						}
					}
					
					nomeOK = true;
				}
				
				else{
					System.out.println("Inserire un nome corretto");
					nomeOK = false;
				}
				
			}
			if(scelta==2){
				
				String nomeC ="";
				System.out.println("Inserire parte del nome:");
				System.out.println();
			
				nomeC = input.next();
				
				//lo stesso tipo di matching viene
				//fatto qui
				String regex = "[A-Z][a-z]+";
				
				Pattern r = Pattern.compile(regex);				
				
				Matcher m = r.matcher(nomeC);
				
				if(m.matches()){
					
					Pattern pattern;
					Matcher matcher;
					
					for(int i =0; i<nuovaP.getSize();i++){
						
						pattern = Pattern.compile(nomeC.toLowerCase());
						matcher = pattern.matcher(nuovaP.getIndex(i).getNomeCliente());
						if(matcher.lookingAt()){
							System.out.println("	"+nuovaP.getIndex(i));
							cont++;
							
						}
					}
					
					nomeOK = true;
				}
				
				else{
					System.out.println("Inserire un nome corretto");
					nomeOK = false;
				}
				
			}
			
			if(cont == 0){
				System.out.println("Ordine non trovato!");
			}
			
			
		}while(!nomeOK);
	}
	
	
	//metodo per visualizzare gli ordini in base alla fascia oraria
	public void visualizzaPerFascia(Prenotazioni nuovaP){
		
		
		System.out.println("Ricerca ordini per fascia oraria:");
		Scanner input = new Scanner(System.in);
		boolean oreOK=false, ore2OK= false;
		do{
			//viene chiesto all'utente di inserire due ore diverse per costituire il
			//range di ricerca con un'ora di partenza e un'ora finale
			System.out.println();
			int ore, ore2;
			System.out.println("Inserisci un'ora (a partire dalle 12):");
			System.out.println();
			
			//qualora dovesse essere inserito erroneamente un valore diverso da un numero
			try{
				ore = input.nextInt();
			}
			catch(InputMismatchException e){
				input.nextLine();
				System.out.println();
				System.out.println("Errore!");
				ore = -1;
			}
			if(ore>=12 && ore <24){
				System.out.println();
				System.out.println("Inserisci ancora un'ora (fino alle 24):");
				System.out.println();
				try{
				ore2 = input.nextInt();
				}
				catch(InputMismatchException e){
					input.nextLine();
					System.out.println();
					System.out.println("Errore!");
					ore2 = -1;
				}
				if(ore2>=12 && ore2 <24 && ore!=ore2){
					ore2OK = true;		
					System.out.println();
					System.out.println("Ordini che vanno dalle ore "+ore+" alle ore "+ore2+":");
					System.out.println();
					for(int i = 0; i<nuovaP.getSize();i++){
						if(Integer.parseInt(nuovaP.getIndex(i).getOrario().substring(0,2)) >= ore &&  Integer.parseInt(nuovaP.getIndex(i).getOrario().substring(0,2)) <ore2){
							System.out.println("	"+nuovaP.getIndex(i));
						}
					}	
				}
				else if(ore==ore2){
					System.out.println("Non puoi inserire gli stessi orari");
					ore2OK = false;
				}
				else{
					System.out.println("Inserisci correttamente l'ora.");
					ore2OK = false;
				}	
			}
			else{
				System.out.println("Inserisci correttamente l'ora.");
			}
			
		}while(!ore2OK);
	}
	
	
	//metodo per visualizzare o tutti gli ordini o solo quelli a domicilio
	public void visualizzaOrdini(Prenotazioni nuovaP){
		
		
		Scanner input = new Scanner(System.in);
		int scelta;
		boolean trovato = false;
		do{
			System.out.println();
			System.out.println("Premi 1 per vedere tutte le ordinazioni o 2 per vedere quelle a domicilio:");
			System.out.println();
			
			try{
			scelta = input.nextInt();
			}
			catch(InputMismatchException e){
				input.nextLine();
				System.out.println("Errore!");
				scelta = -1;
			}
			System.out.println();
			
			//scelta 1 corrisponde a tutti gli ordini, quindi considera o gli ordini normali
			//o gli ordini a domicilio (quindi tutti gli ordini).
			if(scelta == 1){
				System.out.println();
				for(int i = 0; i<nuovaP.getSize();i++){
					if(nuovaP.getIndex(i) instanceof Ordine || nuovaP.getIndex(i) instanceof OrdineADomicilio){
						System.out.println("	"+nuovaP.getIndex(i));
						trovato = true;
					}
				}
			}
			
			//stampa soltanto gli ordini a domicilio, verifica se gli ordini corrispondono
			//a ordini a domicilio
			else if(scelta == 2){
				System.out.println();
				for(int i = 0; i<nuovaP.getSize();i++){
					if(nuovaP.getIndex(i) instanceof OrdineADomicilio){
						System.out.println("	"+nuovaP.getIndex(i));
						trovato = true;
					}
				}
			}
			
			else
				System.out.println("Inserisci un valore valido (1 o 2)");
			
			if(trovato == false){
				System.out.println("Nessun ordine nella lista delle prenotazioni!");
			}
			
		
		}while(scelta!=1&&scelta!=2);
		
	
	}
	
	
	//visualizza il prossimo ordine da consegnare
	public void visualizzaProssimoOrdine(Prenotazioni nuovaP){
		System.out.println();
		System.out.println("Prossimo ordine:");
		System.out.println();
		
		
		System.out.println("	"+nuovaP.getIndex(0));
	
	}
	
	//elimina il prossimo ordine da consegnare
	public Prenotazioni eliminaProssimoOrdine(Prenotazioni nuovaP){
		System.out.println();
		System.out.println("Ordine consegnato:");
		System.out.println();
		
		nuovaP.rimuoviNextOrdine(nuovaP.getIndex(0));
		
		return nuovaP;
	}
	
	
	//qua sotto rimangono gli ultimi due metodi per serializzare le liste contenenti
	//gli ordini compilati dall'utente
	public void serializzaOutput(Prenotazioni nuovaP){
		
		//verifica se è presente un file per riversare i dati serializzati
		try{
			ObjectOutputStream out = 
					new ObjectOutputStream( new BufferedOutputStream (
							new FileOutputStream ("serializza.dat" )));
			out.writeObject(nuovaP);
			out.close();
		}catch ( IOException e) {
			System . out . println (" ERRORE di I/O");//qualora ci dovessero essere problemi di lettura
			System . out . println (e );
		}
		
		System.out.println ("Lista salvata!");
		
	}
	
	public Prenotazioni serializzaInput(Prenotazioni nuovaP){
		
		//crea una nuova lista
		Prenotazioni p2 = null;
		
		//verifica che sia presente il file per caricare i dati serializzati
		try {
			ObjectInputStream in =
					new ObjectInputStream ( new BufferedInputStream (
							new FileInputStream ("serializza.dat" )));
			p2 = ( Prenotazioni ) in . readObject ();
			in . close ();
			} catch ( ClassNotFoundException e) {
			System . out . println (" PROBLEMA ( manca oggetto nel file )");//qualora il file non dovesse essere presente
			System . out . println (e );
			} catch ( IOException e) {
			System . out . println (" ERRORE di I/O");
			System . out . println (e );
			}

		System.out.println ("Lista caricata!");
		
		return p2;
	}
	 
}
