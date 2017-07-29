package trabajo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class DBusuariosNuevo {
	private static String pathIn="./registro.in";
	private List<UsuarioNuevo> users;
	
	
	public void controlDeUsuarios() throws FileNotFoundException{
		leerArchivo();
	}

	private void leerArchivo() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(pathIn));
		users = new ArrayList<>();
	
		while (sc.hasNext()) {
			UsuarioNuevo aux=new UsuarioNuevo(sc.next(), sc.nextInt());
			users.add(aux);	
	
		}
		sc.close();
	}

	public static String getPathIn() {
		return pathIn;
	}

	public static void setPathIn(String pathIn) {
		DBusuariosNuevo.pathIn = pathIn;
	}

	public List<UsuarioNuevo> getUsers() {
		return users;
	}

	public void setUsers(List<UsuarioNuevo> users) {
		this.users = users;
	}
}
