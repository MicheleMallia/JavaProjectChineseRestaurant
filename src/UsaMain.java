import java.util.InputMismatchException; 
import java.util.Scanner; 

public class UsaMain {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in); //input dichiarato per permettere all'utente di effettuare una scelta
												
		//Lista degli utenti abilitati a usare
		//il menu interattivo per la gestione del ristorante
		Utente[] utenti = new Utente[3];
		utenti[0] = new Utente("Michele Mallia", "mallia", "26091992");
		utenti[1] = new Utente("Donata Gaglio", "donata", "22031958");
		utenti[2] = new Utente("Lillo Mallia", "lillo", "18121958");
		
		//Menu con cui gestisco gli ordini del ristorante
		MenuInterattivo m1 = new MenuInterattivo(utenti); 
		
		
		//Fase iniziale del programma che consente all'utente di effettuare
		//il login oppure di uscire dal programma.
		//All'interno del ciclo viene inserito un sistema per gestire gli errori
		//qualora venisse inserito un input diverso da un numero intero.
		//Se si inserisce 1 viene effettuato il login.
		//Se si inserisce 0 si esce dal programma
		int scelta;
		do {
			System.out.println("Inserisci 1 per effettuare il login oppure 0 per uscire");
			try{
				scelta=input.nextInt();
			}
			catch(InputMismatchException e){
				input.nextLine();
				System.out.println("Errore.");
				scelta = -1;
			}
			if (scelta<0 || scelta>1) System.out.println("Scelta errata");
			else if (scelta!=0){
				m1.login();
			}
		} while (scelta!=0);
	}
}