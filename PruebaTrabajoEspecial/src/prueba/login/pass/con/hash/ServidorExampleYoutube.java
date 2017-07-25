package prueba.login.pass.con.hash;



import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServidorExampleYoutube  {

	public static void main(String[] args) {
		// El servidor tiene que hacer 2 tareas
		//1 recibe en textArea texto
		//En 2do plano tiene que permanecer a la escucha ctemente y tener el puerto 9999 abierto
		
		MarcoServidor mimarco=new MarcoServidor();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
			
	}	
}

class MarcoServidor extends JFrame implements Runnable{ //clase que contruye el marco
	
	public MarcoServidor(){
		
		setBounds(1200,300,280,350);				
			
		JPanel milamina= new JPanel();
		
		milamina.setLayout(new BorderLayout());
		
		areatexto=new JTextArea();
		
		milamina.add(areatexto,BorderLayout.CENTER);
		
		add(milamina);
		
		setVisible(true);
		
		Thread miHilo=new Thread(this);
		miHilo.start();
		
		}
	
	private	JTextArea areatexto;

	@Override
	public void run() {
		//El codigo que estará ala escucha
		//System.out.println("EStoy a la escucha");
		try {
			
				
				//Aca  no es necesario el while(1){}. No es necesario volver a poner
				//a la escucha porque esta sentencia YA LA HACE EL HILO
				ServerSocket servidor=new ServerSocket(9999);
				//Sí es importante que acepte de nuevo las conexiones
				
				String nick, ip, mensaje;
				PaqueteEnvio paqueteRecibido;
				ArrayList<String> listaIp=new ArrayList<>();
			while (true) {	
				Socket miSocket=servidor.accept();//
				
				ObjectInputStream paqueteDatos=new ObjectInputStream(miSocket.getInputStream());
				
				paqueteRecibido=(PaqueteEnvio) paqueteDatos.readObject();
				//////////ATENCION AL PAQUETE/////////
				ip=paqueteRecibido.getIp();
				nick=paqueteRecibido.getNick();
				mensaje=paqueteRecibido.getMensaje();
				if (!mensaje.equals(" online")) {//se conecto 1 cliente
					
					areatexto.append(nick+": "+mensaje+" para "+ip+"\n");
					//Ahora, aca necesito crear un socet para mandar la info que recibí
					//al cliente en cuestión
					
					Socket enviaDestinatario=new Socket(ip, 9090);
					
					//Como voy a mandar un objeto, necesito un ObjectOutputStream
					
					ObjectOutputStream paqueteReEnvio=new ObjectOutputStream(enviaDestinatario.getOutputStream());
					
					paqueteReEnvio.writeObject(paqueteRecibido);
					paqueteReEnvio.close();//CIERRO EL FLUJO DE DATOS
					enviaDestinatario.close();//CIERRO EL SOCKET DEL CLIENTE REENVIADO
					
					miSocket.close();//CIERRO EL SOCKET DEL CLIENTE QUE LLEGO
				}else{//Si es la primera vez que se conecta
				/////DETECTA ONLINE/////////////
					InetAddress localizacion=miSocket.getInetAddress();
					//Tengo la direccion IP del socket que se conectó
					String ipRemota=localizacion.getHostAddress();
					System.out.println("Online "+ipRemota);
					listaIp.add(ipRemota);
					paqueteRecibido.setIps(listaIp);//Mando la lista de conectados
					for (String z : listaIp) {
						System.out.println("Array:"+z);
						Socket enviaDestinatario=new Socket(z, 9090);
						
						//Como voy a mandar un objeto, necesito un ObjectOutputStream
						
						ObjectOutputStream paqueteReEnvio=new ObjectOutputStream(enviaDestinatario.getOutputStream());
						
						paqueteReEnvio.writeObject(paqueteRecibido);
						paqueteReEnvio.close();//CIERRO EL FLUJO DE DATOS
						enviaDestinatario.close();//CIERRO EL SOCKET DEL CLIENTE REENVIADO
						
						miSocket.close();//CIERRO EL SOCKET DEL CLIENTE QUE LLEGO1111111111
					}
				}
				//DataInputStream flujoEntrada=new DataInputStream(miSocket.getInputStream());//Creo un flujo de entrada
				
				//String mensajeTexto=flujoEntrada.readUTF();
				
				//areatexto.append(mensajeTexto+"\n");
			}
			/*
			 Recordar que todo este bloque esta dentro de un HILO EJECUTANDO EN
			 2DO PLANO. Lo que nos permite manejar la app en 1ER PLANO, por ej,
			 si la App tuviera menus (U OTRAS COSAS QUE HACER) las podemos trabajar
			 EN 1ER PLANO xq en 
			 1ER PLANO esta ejecutando una cosa y en 2DO PLANO OTRA
			 */
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
