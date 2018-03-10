public class Utente {
	
	//le stringhe rappresentano il nome reale dell'utente
	//l'username e la password d'accesso al menu interattivo
	private String realname;
	private String username;
	private String password;
	
	
	//costruttore
	public Utente(String nome, String user, String pwd) {
		realname = nome;
		username = user;
		password = pwd;
	}
	
	//metodo per la verifica dell'utente al momento del login
	public boolean controlla(String user, String pwd) {
		return (username.equals(user) && password.equals(pwd));
	}
	
	//metodo per ottenere il nome dell'utente
	public String ottieniNome() {
		return realname;
	}

}
