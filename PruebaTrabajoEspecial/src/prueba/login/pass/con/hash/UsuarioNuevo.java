package prueba.login.pass.con.hash;

public class UsuarioNuevo {
	private String user;
	private int pass;

	public UsuarioNuevo(String user, int pass) {
		this.user = user;
		this.pass = pass;
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
