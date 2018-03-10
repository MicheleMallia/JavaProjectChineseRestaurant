import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;

public class MenuInterattivo implements Serializable{
	
	//costante statica che serve a distinguere tra diverse versioni della stessa classe
	//quando viene effettuata la serializzazione della stessa classe
	static final long serialVersionUID = 1; 
	
	//vengono dichiaarate le variabili per l'input dell'utente
	//e la nuova lista delle prenotazioni per inserire gli ordini inseriti dall'utente
	Scanner input = new Scanner(System.in); 
	Prenotazioni nuovaP = new Prenotazioni();
	
	
	//utenti abilitati a usare questo menu interattivo 
	private Utente[] utenti; 
	
	// costruttore della classe.
	public MenuInterattivo(Utente[] ut){
		utenti = ut;
	};
	
	// metodo che consente di accedere e usare il menu interattivo
	public void login() {
		
		// chiede credenziali di accesso
		System.out.print("username: ");
		String username = input.nextLine();
		System.out.print("password: ");
		String password = input.nextLine();
		
		// verifica le credenziali inserite (cerca tra gli utenti)
		// invocando il metodo opportuno della classe Utente
		int i = 0; 
		boolean loginOK=false;
		while (i<utenti.length && !loginOK) {
			loginOK = utenti[i].controlla(username, password);
			if (!loginOK) i++;
		}
		
		// se le credenziali sono corrette, avvia il menu' con le operazioni
		if (loginOK) {
			// saluta l'utente
			System.out.println();
			System.out.println("Buongiorno, " + utenti[i].ottieniNome());
			avviaMenu();
			
		} else {
			System.out.println("Username o password errati!");
		}
			
	}

	// metodo ausiliaro che gestisce il menu delle operazioni
	private void avviaMenu(){
		
		//Qua ci sono tutte le opzioni che l'utente può scegliere
		//per la gestione degli ordini
		int scelta;
		do{
			
			System.out.println();//riga bianca
			System.out.println("Ristorante cinese Mao Tse Tung:");
			System.out.println();
			System.out.println("[1] Aggiungi una nuova ordinazione");
			System.out.println("[2] Visualizza la prossima ordinazione da consegnare");
			System.out.println("[3] Elimina ordinazione appena consegnata");
			System.out.println("[4] Visualizza ordinazioni per ordine di orario");
			System.out.println("[5] Visualizza ordinazioni per fascia oraria");
			System.out.println("[6] Visualizza ordinazioni per nome utente");
			System.out.println("[7] Carica lista ordinazioni");
			System.out.println("[8] Salva lista ordinazioni");
			System.out.println("[9] Esci");
			System.out.println();
			System.out.print("Scelta: ");
			
			//viene monitorata questa parte del programma
			//affinché l'esecuzione possa continuare anche
			//per un input errato inserito dall'utente
			try{
				scelta = input.nextInt();
			}
			
			//se l'utente inserisce un valore che non sia un intero
			//gli viene intimato di selezionare un valore corretto
			catch(InputMismatchException e){
				System.out.println();
				System.out.println("Inserire un valore intero.");
				input.nextLine();
				scelta = 0;
			}
			
			//in base alla scelta fatta dall'utente il programma
			//avvia diverse funzionalità che operano sulla lista
			//della prenotazioni
			switch(scelta){
				case 1: nuovaP = addPiatto(nuovaP);break;
				case 2: visualizzaProssimoOrdine(nuovaP);break;
				case 3: nuovaP = rimuoviOrdineConsegnato(nuovaP);break;
				case 4: visualizzaOrdini(nuovaP);break;
				case 5: visualizzaFasciaOraria(nuovaP);break;
				case 6: visualizzaPerNome(nuovaP);break;
				case 7: nuovaP = serializzaInput(nuovaP);break;
				case 8:	serializzaOutput(nuovaP);break;
				case 9: break;//comando che permette di uscire dal programma
							  //e di ritornare alla pagina principale del programma
							  //presente nel main
			}
		}while(scelta!=9);
	}
	
	//metodo per aggiungere un piatto all'ordinazione
	//da qui verranno compiute tutte le operazioni per costruire
	//l'ordine che verrà inserito nella lista delle prenotazioni
	private Prenotazioni addPiatto(Prenotazioni nuovaP){
		
		//vengono inizializzate tutte le variabili
		//che serviranno a "costruire" l'ordinazione
		//da inserire nella lista delle prenotazioni
		//viene dichiarata una variabile di tipo int tramite la quale
		//si specifica se l'ordinazione è di tipo "a domicilio"
		//viene dichiarato anche un vettore che conterrà soltanto
		//i nomi dei piatti inseriti
		//viene dichiarato un vettore che conterrò le quantità dei piatti
		//selezionati dall'utente, in modo da rendere chiaro quanti piatti
		//devono essere preparati dal cuoco
		int scelta;
		String orario = "";
		String nome = "";
		String indirizzo = "";
		int domicilio = 0;
		ListaPiatti lista = new ListaPiatti();
		Vector<Integer> vettoreQ = new Vector<Integer>();
		
		//variabile con la quale verifico se un ordine
		//è stato confermato o meno 
		boolean confermaO = false;
		
		
		//Lista dei piatti presenti nel menu
		do{
			System.out.println();
			System.out.println("Piatti da aggiungere:");
			System.out.println();
			System.out.println("[1]Involtini");
			System.out.println("[2]Nuvolette");
			System.out.println("[3]Alghe");
			System.out.println("[4]Ravioli");
			System.out.println("[5]Spaghetti");
			System.out.println("[6]Riso");
			System.out.println("[7]Pollo");
			System.out.println("[8]Insalata");
			System.out.println();
			System.out.println("[00] Ritorna al menu precedente");
			System.out.println("[10] Conferma lista");
			System.out.println();
			
			//se la lista non è vuota
			//viene visualizzata la lista con i piatti
			//selezionati dall'utente nella fase della scelta dei piatti
			//per tenere a mente cosa ha ordinato ed, eventualmente, togliere
			//o aggiungere altri piatti
			if(!lista.isVuota()){
				visualizzaPiatti(vettoreQ, lista);
			}
			System.out.println();
			System.out.print("Scelta: ");
			
			//per evitare che l'utente, inserendo un carattere, blocchi il programma
			try{
				scelta = input.nextInt();
			}
			catch(InputMismatchException e){
				input.nextLine();
				System.out.println();
				System.out.println("Inserire un numero.");
				scelta = 11;
			}
			
			//ad ogni piatto selezionato viene invocato un metodo
			//con il quale viene inserita la quantità e il piatto viene inserito nel vettore "lista"
			//se l'utente seleziona 10 prosegue con la compilazione del nome, dell'ora, dell'indirizzo
			//qualora si tratti di un ordine a domicilio
			switch(scelta){
			case 1: addQuantita(scelta, "Involtini", lista, vettoreQ);break;
			case 2:	addQuantita(scelta, "Nuvolette", lista, vettoreQ);break;
			case 3:	addQuantita(scelta, "Alghe", lista, vettoreQ);break;
			case 4:	addQuantita(scelta, "Ravioli", lista, vettoreQ);break;
			case 5:	addQuantita(scelta, "Spaghetti", lista, vettoreQ);break;
			case 6:	addQuantita(scelta, "Riso", lista, vettoreQ);break;
			case 7:	addQuantita(scelta, "Pollo", lista, vettoreQ);break;
			case 8:	addQuantita(scelta, "Insalata", lista, vettoreQ);break;
			case 10: 
				
				//si può proseguire con l'ordinazione soltanto
				//se l'utente ha selezionato almeno un piatto
				//se la lista non è vuota viene inizializzato un percorso
				//che l'utente deve fare per poter portare a compimento
				//l'ordine
				if(!lista.isVuota()){
					confermaQuantita(lista, vettoreQ); //viene prodotta una lista contenente i valori delle quantità e i nomi dei piatti che l'utente ha selezionato
					orario = addOrario(); //viene impostata l'ora dell'ordinazione
					nome = addNome(); //viene impostato il nome del cliente
					domicilio = DomicilioSiNo(); //si chiede all'utente se si tratta di un ordine a domicilio oppure no
					if(domicilio==1){
						indirizzo = addIndirizzo(); //se è un ordine a domicilio, viene chiesto all'utente di inserire un indirizzo
					}
					confermaO = true; //viene conferamta l'ordinazione
					break;
				}
				
				//se la lista è vuota non si può proseguire con l'ordinazione
				//non si effettuano ordinazioni senza aver selezionato un piatto!
				else if(lista.isVuota()){
					System.out.println("Lista vuota! Non si può  proseguire con l'ordine!");
					break;
				}
				break;
			case 00: confermaO = false; break; //si esce dalla metodo e si ritorna al menu principale
			default: System.out.println();System.out.println("Inserire una scelta valida");//qualora non venga inserito un valore valido
			}
		}while(scelta!=0 && scelta!=10);
		
		if(confermaO && domicilio == 0){
			System.out.println("Ordine registrato!");
			Ordine o = new Ordine(orario, lista, nome);
			nuovaP.inserisci(o);
			nuovaP = nuovaP.ordinaLista(nuovaP);
		}
		else if(confermaO && domicilio == 1){
			System.out.println();
			System.out.println("Ordine a domicilio registrato!");
			OrdineADomicilio o = new OrdineADomicilio(orario, lista, nome, indirizzo);
			nuovaP.inserisci(o);
			nuovaP = nuovaP.ordinaLista(nuovaP);
		}
		else
			System.out.println("Ordine non registrato");
		
		return nuovaP;
	}
	
	//oltre ad aggiungere la quantità dei piatti al vettore dei numeri vettoreQ
	//si effettuano alcuni calcoli sulla base della quantità dei piatti selezionati
	//(es: quanti piatti si possono ancora ordinare)
	private void addQuantita(int scelta, String nome, ListaPiatti l, Vector<Integer>vettoreQ){
		int quantita;

		boolean flag = true;
		do{
			System.out.println();
			System.out.print("Inserire la quantità (massimo: 10 porzioni, inserire negativo per rimuovere o diminuire le quantità): ");
			try{
				quantita = input.nextInt();
			}
			catch(InputMismatchException e){
				input.nextLine();
				System.out.println();
				System.out.println("Inserire un numero.");
				quantita = 0;
			}
			if(quantita>=-10 && quantita<=10 && quantita!=0){
				Piatto p = new Piatto(nome);
				if(!l.presente(p) && quantita >0){
					
					l.inserisci(p);
					vettoreQ.add(quantita);
					flag = false;
				}

				else if(!l.presente(p) && quantita <0){
						
						System.out.println();
						System.out.println("Non si può sottrarre portate a piatti che non esistono.");
						flag = false;
					}
				
				else if(l.presente(p) && quantita >0){
					
					int indicePiattoPresente = l.indexOf(p);
					int quantitaVecchia = vettoreQ.get(indicePiattoPresente);
					int quantitaAggiornataPiu = quantitaVecchia + quantita;
					if(quantitaAggiornataPiu>10){
						System.out.println();
						System.out.println("Non si possono scegliere più di 10 porzioni dello stesso piatto. Ancora "+(10-quantitaVecchia)+" porzioni di "+p.nomePiatto+" disponibili.");
						flag = false;
					}
					else if(quantitaAggiornataPiu<=10){
						vettoreQ.remove(indicePiattoPresente);
						vettoreQ.add(indicePiattoPresente, quantitaAggiornataPiu);
						flag = false;
					}
				}
				else if(l.presente(p)&&quantita<0){
				
					int indicePiattoPresente = l.indexOf(p);
					int quantitaVecchia = vettoreQ.get(indicePiattoPresente);
					int quantitaAggiornataMeno = quantitaVecchia + quantita;
					
					if(quantitaAggiornataMeno<0 || quantitaAggiornataMeno ==0){
						System.out.println();
						System.out.println("Desideri rimuovere il piatto "+p.nomePiatto+"?");
						char sino;
						sino = input.next().charAt(0);
						switch(sino){
						case 'n': System.out.println();System.out.println("Non hai eliminato il piatto"); flag = false;break;
						case 's': System.out.println();System.out.println("Hai eliminato il piatto"); l.rimuovi(p); vettoreQ.remove(indicePiattoPresente); flag = false;break;
						default: flag =false;
						}
					}
					else if(quantitaAggiornataMeno>0){
						vettoreQ.remove(indicePiattoPresente);
						vettoreQ.add(indicePiattoPresente, quantitaAggiornataMeno);
						flag = false;
					}
					
				}
			}
			else if(quantita<-10 || quantita >10){
				System.out.println();
				System.out.println("Non si può inserire un intero minore di -10 o maggiore di 10.");
				flag = false;
			}
			else{
				System.out.println();
				System.out.println("Riprova");
			}
			
		}while(flag);
		
		System.out.println();
		
	}
	
	//metodo che modifica la lista dei piatti inserendo le quantità
	//memorizzare nel vettore numerico v
	//in modo da rendere esplicite le informazioni relative alla quantità dei piatti
	//che andranno poi inserite nell'ordinazione
	private void confermaQuantita(ListaPiatti l, Vector<Integer> v){
		
		int quantita;
		Piatto p = new Piatto(null);
		
		for(int i=0; i<v.size();i++){
			quantita = v.get(i);
			p = l.getPiatto(i);
			p.setQuantita(quantita);
			l.setPiattoQuantita(i, p);
		}
				
	}
	
	//metodo per aggiungere l'orario all'ordinazione
	private String addOrario(){
		
		String o="", ore ="", minuti= "";
		boolean flag;
		do{
			System.out.println();
			System.out.println("Inserire un orario, prima ore e poi minuti [formato 24h]");
			System.out.println("Orario di apertura 12:00 -- orario di chiusura alle 24");
			System.out.println();
			System.out.print("ore: ");
			ore = input.next();
			System.out.print("minuti: ");
			minuti = input.next();
			try{
			if(Integer.parseInt(ore)>=12 && Integer.parseInt(ore)<24 && Integer.parseInt(minuti) >=0 && Integer.parseInt(minuti) <60 && ore.length()>1&& minuti.length()>1){
				o = ore+":"+minuti;
				flag = false;
			}
			else {
				System.out.println("Inserire un orario corretto (es: prima 12 e poi 08)");
				flag = true;
			}
			}catch(NumberFormatException e){
				input.nextLine();
				System.out.println();
				System.out.println("Errore!");
				flag = true;
			}
		}while(flag);
		
		return o;
				
	}
	
	//metodo per aggiungere il nome del cliente dell'ordinazione
	//può essere aggiunto soltanto il nome del cliente (non il cognome)
	//per ragioni di semplicità
	private String addNome(){
		String n;
		boolean flag;
		System.out.println();
		System.out.println("Inserire solo il nome del cliente (non il cognome):");
		do{
			n = input.nextLine();
			
			String regex = "[A-Z][a-z]{2,}+";
			
			Pattern r = Pattern.compile(regex);
			
			Matcher m = r.matcher(n);
			
			if(n.length()<30 && m.matches()){
				flag = false;
			}
			else {
				System.out.println();
				System.out.println("Non sono ammessi né simboli né numeri.");
				flag = true;
			}
		}
		while(flag);
			
		return n.toLowerCase();
	}
	
	//metodo che permette all'utente di impostare se un ordine è a domicilio o no
	private int DomicilioSiNo(){
		
		char scelta;
		int choice = 0;
		do{
			System.out.println();
			System.out.println("È un ordine a Domicilio?[s/n]");
			System.out.println();
			scelta = input.next().toLowerCase().charAt(0);
			switch(scelta){
			case 'n': choice = 0;break;
			case 's': choice = 1;break;
			default: System.out.println("Premere 'n' o 's'.");
			}
		}while(scelta!='n' && scelta!='s');
		
		return choice;
	}
	
	
	//metodo che permette all'utente di aggiungere l'indirizzo a un ordine
	//qualora l'ordine sia a domicilio
	//ci si assicura che venga inserito un indirizzo corretto
	//e un civico formato soltanto da cifre
	private String addIndirizzo(){
		String n;
		int civico=0;
		boolean flag = true;
		System.out.println();
		System.out.println("Inserire solo il nome dell'indirizzo:");
		
		do{
			System.out.println();
			n = input.nextLine();
			
			String regex = "(via|piazza|viale|piazzale|largo|vicolo).*";
			
			Pattern r = Pattern.compile(regex);
			
			Matcher m = r.matcher(n.toLowerCase());
			
			if(!m.matches()){
				System.out.println("Es: viale della vittoria. Il numero civico va inserito al passo successivo.");
				flag = true;
			}
			else if(m.matches() && n.length()<=40){
				System.out.println();
				System.out.println("Inserire numero civico");
				
				try{
				civico = input.nextInt();
				}
				catch(InputMismatchException e){
					input.nextLine();
					System.out.println("Errore!");
				}
				
				if(civico>0 && civico<10000)
					flag = false;
			}
			
			else{
				flag = true;
			}
		}
		while(flag);
			
		return n.toLowerCase()+" "+Integer.toString(civico);
	}
	
	
	//metodo che consente all'utente di visualizzare i piatti
	//che ha ordinato nella fase della compilazione del menu personale
	private void visualizzaPiatti(Vector<Integer> v, ListaPiatti l){
		Piatto p = new Piatto(null);
		int quantita = 0;
		System.out.println("Piatti già ordinati:");
		if(!l.isVuota()){
			for(int i=0; i<v.size();i++){
				quantita = v.get(i);
				p = l.getPiatto(i);
				System.out.println(quantita+" "+p);
			}
		}
	}
	
	
	//Qui sotto sono presenti tutti i metodi che consentono di eseguire alcune
	//funzionalità sulla lista delle prenotazioni 
	//(visualizzare, a scelta dell'utente, tutti gli ordini
	//o solo quelli a domicilio, rimuovere un ordina già consegnato,
	//visualizzare gli ordini per fascia oraria,
	//visualizzare il prossimo ordine,
	//visualizzare gli ordini per nome,
	//serializzazioni in input e output)
	
	//Tutti i metodi invocano medesimi metodi presenti nella classe Prenotazioni
	private void visualizzaOrdini(Prenotazioni nuovaP){
		if(nuovaP.getSize()==0)
			System.out.println("Non ci sono ancora ordini registrati!");
		else
			nuovaP.visualizzaOrdini(nuovaP);
	}
	
	private void visualizzaPerNome(Prenotazioni nuovaP){
		if(nuovaP.getSize()==0)
			System.out.println("Non ci sono ancora ordini registrati!");
		else
			nuovaP.visualizzaPerNome(nuovaP);
	}
	
	private void visualizzaFasciaOraria(Prenotazioni nuovaP){
		if(nuovaP.getSize()==0)
			System.out.println("Non ci sono ancora ordini registrati!");
		else
			nuovaP.visualizzaPerFascia(nuovaP);
	}
	
	
	private void visualizzaProssimoOrdine(Prenotazioni nuovaP){
		Prenotazioni nuovaL = new Prenotazioni();
		if(nuovaP.getSize()==0)
			System.out.println("Non ci sono ancora ordini registrati!");
		else
			nuovaP.visualizzaProssimoOrdine(nuovaP);
		
	}
	
	private Prenotazioni rimuoviOrdineConsegnato(Prenotazioni nuovaP){
		Prenotazioni nuovaL = new Prenotazioni();
		if(nuovaP.getSize()==0)
			System.out.println("Non ci sono ancora ordini registrati!");
		else{
			nuovaL = nuovaP.eliminaProssimoOrdine(nuovaP);
		}
		return nuovaL;
	}
	
	 private Prenotazioni serializzaInput(Prenotazioni nuovaP){
		 Prenotazioni nuovaL = new Prenotazioni();
		 nuovaL =nuovaP.serializzaInput(nuovaP);
		 return nuovaL;
	}
	
	private void serializzaOutput(Prenotazioni nuovaP){
			nuovaP.serializzaOutput(nuovaP);
	}
	
}
