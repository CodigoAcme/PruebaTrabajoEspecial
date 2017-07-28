package prueba.login.pass.con.hash;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

public class UsuarioNuevo implements Serializable{
	private String user;
	private int pass;
	private String nombreArchivoAabrir;//relativo donde guardará sus archivos
	private int clave;
	private ArrayList<File> listaArchivos;
	private String mensaje;
	private JTextArea campoDeArchivo;
	private ArrayList<HashMap<String, String>> listaMapas=new ArrayList<>();
	public static int LOGGEO=0;
	public static int REGISTRAR=1;
	public static int TRAER_ARCHIVO=2;
	public static int GUARDAR_ARCHIVO=3;
	public static int ONLINE=4;
	public static int DESCONECCION=5;
	
	
	
	public ArrayList<HashMap<String, String>> getListaMapas() {
		return listaMapas;
	}
	public void setListaMapas(ArrayList<HashMap<String, String>> listaMapas) {
		this.listaMapas = listaMapas;
	}
	public JTextArea getCampoDeArchivo() {
		return campoDeArchivo;
	}
	public void setCampoDeArchivo(JTextArea campoDeArchivo) {
		this.campoDeArchivo = campoDeArchivo;
	}
	public ArrayList<File> getListaArchivos() {
		return listaArchivos;
	}
	public void setListaArchivos(ArrayList<File> listaArchivos) {
		this.listaArchivos = listaArchivos;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public UsuarioNuevo() {
	}
	public UsuarioNuevo(String user, int pass, ArrayList<File> listaArchivos) {
		this.user = user;
		this.pass = pass;
		this.listaArchivos=listaArchivos;
	}
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
		this.nombreArchivoAabrir=path;
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
	public String getNombreArchivoAabrir() {
		return nombreArchivoAabrir;
	}
	public void setNombreArchivoAabrir(String directorio) {
		this.nombreArchivoAabrir = directorio;
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
