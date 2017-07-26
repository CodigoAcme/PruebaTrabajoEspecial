package prueba.login.pass.con.hash;



import javax.swing.*;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server  {

	public static void main(String[] args) {
		// El servidor tiene que hacer 2 tareas
		//1 recibe en textArea texto
		//En 2do plano tiene que permanecer a la escucha ctemente y tener el puerto 9999 abierto
		
		Marco mimarco=new Marco();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
			
	}	
}

class Marco extends JFrame implements Runnable{ //clase que contruye el marco
	
	public Marco(){
		
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
		
			
				
				//Aca  no es necesario el while(1){}. No es necesario volver a poner
				//a la escucha porque esta sentencia YA LA HACE EL HILO
				ServerSocket servidor = null;
				try {
					servidor = new ServerSocket(9999);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Sí es importante que acepte de nuevo las conexiones
				
		
				DBusuariosNuevo users=new DBusuariosNuevo();
				
			while (true) {	
				Socket miSocket;
				try {
					miSocket = servidor.accept();
					ObjectInputStream paqueteDatos=new ObjectInputStream(miSocket.getInputStream());
					
					UsuarioNuevo aux=(UsuarioNuevo) paqueteDatos.readObject();
					
					int opcion=aux.getClave();
					
					
					if (opcion==UsuarioNuevo.REGISTRAR) {
						try {
							users.controlDeUsuarios();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String usuario=aux.getUser();
						int pass=aux.getPass();
						areatexto.append("IP "+miSocket.getInetAddress().getHostAddress()+" "+aux.getUser()+" "+aux.getPass()+"\n");
						
						List<UsuarioNuevo> usuarios=users.getUsers();
						
						boolean marca = false;
						UsuarioNuevo usuarioLoggeado = null;
						
						
							for (int i = 0; i < usuarios.size(); i++) {
								if(usuario.equals(usuarios.get(i).getUser())){
									marca = true;
									break;
								}
							
							}
						
							if(marca){//Hay un usario ya registrado con ese nombreDeUsuario
								
								DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());
								flujoSalida.writeUTF("Usuario no disponible");
								miSocket.close();
								break;
									
							}
							else{//Registrarlo
								PrintWriter pw;
								try {
									pw = new PrintWriter(new FileOutputStream(
										    new File("registro.in"), true));
									pw.println(usuario+ " "+pass);
									pw.close();
									
									
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());
								flujoSalida.writeUTF("Registro exitoso!");
								miSocket.close();
								
									
							}
					}
					
					
					
					if (opcion==UsuarioNuevo.LOGGEO) {
						
						try {
							users.controlDeUsuarios();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String usuario=aux.getUser();
						int pass=aux.getPass();
						
						areatexto.append("IP "+miSocket.getInetAddress().getHostAddress()+" "+aux.getUser()+" "+aux.getPass()+"\n");
						
						List<UsuarioNuevo> usuarios=users.getUsers();
						
						boolean marca = false;
						UsuarioNuevo usuarioLoggeado = null;
						
						
							for (int i = 0; i < usuarios.size(); i++) {
								if(usuario.equals(usuarios.get(i).getUser()) && 
										pass==(usuarios.get(i).getPass())){
									marca = true;
									usuarioLoggeado=usuarios.get(i);
									usuarioLoggeado.setDirectorio("./usuarios_registrados/"+usuarioLoggeado.getUser());
									break;
								}
							
							}
						
						if(marca){
							
							DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());
							flujoSalida.writeUTF("Loggeo OK!");
							miSocket.close();
								
						}
						else{
							DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());
							flujoSalida.writeUTF("Loggeo INCORRECTO!");
							miSocket.close();
								
						}
			}
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//
			
	}
	}//run
}

