package ventanas.only;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EscuchaCliente extends Thread{
	
	private Socket s;
	private static int PUERTO=9000;
	private final DataInputStream entrada;
	private final DataOutputStream salida;
	
	
	public EscuchaCliente(Socket obj, DataInputStream in, DataOutputStream out) {
		this.s=obj;
		entrada=in;
		salida=out;
	}
	public void run(){
		try {
			if (!Server.listaClientesConectados.isEmpty()) {
				for (EscuchaCliente socket : Server.listaClientesConectados) {
					System.out.println("Esto llego: "+entrada.readUTF());
					if (!socket.equals(s)) {	
							if (!entrada.readUTF().isEmpty()) {
								salida.writeUTF(entrada.readUTF());
							}
						}
					}
				}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public Socket getS() {
		return s;
	}
	public void setS(Socket s) {
		this.s = s;
	}
	public static int getPUERTO() {
		return PUERTO;
	}
	public static void setPUERTO(int pUERTO) {
		PUERTO = pUERTO;
	}
	public DataInputStream getEntrada() {
		return entrada;
	}
	public DataOutputStream getSalida() {
		return salida;
	}

}
