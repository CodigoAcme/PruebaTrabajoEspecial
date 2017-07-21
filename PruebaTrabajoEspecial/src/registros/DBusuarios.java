package registros;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class DBusuarios {
	private static String pathIn="./registro.in";
	private List<Usuario> users;
	
	
	public void controlDeUsuarios() throws FileNotFoundException{
		leerArchivo();
	}

	private void leerArchivo() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(pathIn));
		users = new ArrayList<>();
	
		while (sc.hasNext()) {
			
			users.add(new Usuario(sc.next(), sc.next()));	
	
		}
		sc.close();
	}

	public static String getPathIn() {
		return pathIn;
	}

	public static void setPathIn(String pathIn) {
		DBusuarios.pathIn = pathIn;
	}

	public List<Usuario> getUsers() {
		return users;
	}

	public void setUsers(List<Usuario> users) {
		this.users = users;
	}
}
