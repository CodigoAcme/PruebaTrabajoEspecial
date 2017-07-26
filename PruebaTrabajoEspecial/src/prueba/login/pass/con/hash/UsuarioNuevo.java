package prueba.login.pass.con.hash;

import java.io.Serializable;

public class UsuarioNuevo implements Serializable{
	private String user;
	private int pass;
	private String directorio;//relativo donde guardará sus archivos
	private int clave;
	public static int LOGGEO=0;
	public static int REGISTRAR=1;
	public UsuarioNuevo(String user, int pass) {
		this.user = user;
		this.pass = pass;
	
	}
	public UsuarioNuevo(String user, int pass, int clave) {
		this.user = user;
		this.pass = pass;
		this.clave=clave;
	}
	public UsuarioNuevo(String user, int pass, String path) {
		this.user = user;
		this.pass = pass;
		this.directorio=path;
	}

	public int getClave() {
		return clave;
	}
	public void setClave(int clave) {
		this.clave = clave;
	}
	public void setPass(int pass) {
		this.pass = pass;
	}
	public String getDirectorio() {
		return directorio;
	}
	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getPass() {
		return pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

}
