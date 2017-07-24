package prueba.login.pass.con.hash;

public class UsuarioNuevo {
	private String user;
	private int pass;
	private String directorio;//relativo donde guardará sus archivos
	public UsuarioNuevo(String user, int pass) {
		this.user = user;
		this.pass = pass;
	}
	public UsuarioNuevo(String user, int pass, String path) {
		this.user = user;
		this.pass = pass;
		this.directorio=path;
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
