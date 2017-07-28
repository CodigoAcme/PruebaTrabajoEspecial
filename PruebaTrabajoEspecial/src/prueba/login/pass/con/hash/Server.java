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
import java.util.Scanner;

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
		setTitle("Soy yo!");
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
				UsuarioNuevo aux;
				ArrayList<HashMap<String, String>> listaMapas=new ArrayList<>();
			while (true) {	
				Socket miSocket;
				try {
					miSocket = servidor.accept();
					ObjectInputStream paqueteDatos=new ObjectInputStream(miSocket.getInputStream());
					
					aux=(UsuarioNuevo) paqueteDatos.readObject();
					
					int opcion=aux.getClave();
					if (opcion==UsuarioNuevo.GUARDAR_ARCHIVO) {
						//areatexto.append(aux.getCampoDeArchivo().getText());
						PrintWriter pw=new PrintWriter(new File("./usuarios_registrados/"+aux.getUser()+"/"+aux.getNombreArchivoAabrir()));
						pw.print(aux.getCampoDeArchivo().getText());;
						pw.close();
						//Localmente se guada bien
						//Ahora hay que listar los archivos para mandarle la lista acutalizada
						File archivosDelUsuario= new File("./usuarios_registrados/"+aux.getUser());
						File[] listOfFiles = archivosDelUsuario.listFiles();
						ArrayList<File> listaArchivos=new ArrayList<>();
						    for (int i = 0; i < listOfFiles.length; i++) {
						      if (listOfFiles[i].isFile()) {
						    	  listaArchivos.add(listOfFiles[i]);
						      }
						    }
						    
						
						ObjectOutputStream salida=new ObjectOutputStream(miSocket.getOutputStream());
						UsuarioNuevo usuarioConMensajeYsusArchivos=new UsuarioNuevo();
						usuarioConMensajeYsusArchivos.setListaArchivos(listaArchivos);
						
						usuarioConMensajeYsusArchivos.setMensaje("Lista de archivos actualizada y enviada!");
						salida.writeObject(usuarioConMensajeYsusArchivos);
						miSocket.close();
						
					}
					if (opcion==UsuarioNuevo.ONLINE) {
						InetAddress localizacion=miSocket.getInetAddress();
						//Tengo la direccion IP del socket que se conectó
						String ipRemota=localizacion.getHostAddress();
						HashMap<String, String> mapaAux=new HashMap<>();
						String usuario=aux.getUser();
						mapaAux.put(ipRemota, usuario);
						
						listaMapas.add(mapaAux);
						aux.setListaMapas(listaMapas);
						aux.setMensaje("online");
						for (HashMap<String, String> mapaDentroLista : listaMapas) {
							for(Map.Entry<String, String> entry: mapaDentroLista.entrySet()){
								//areatexto.append(entry.getValue()+" "+entry.getKey()+"\n");
								
								Socket enviaDestinatarioDelMapa=new Socket(entry.getKey(), 9090);
								ObjectOutputStream paqueteReEnvioDeMapa=new ObjectOutputStream(enviaDestinatarioDelMapa.getOutputStream());
								paqueteReEnvioDeMapa.writeObject(aux);
								paqueteReEnvioDeMapa.close();//CIERRO EL FLUJO DE DATOS
								enviaDestinatarioDelMapa.close();//CIERRO EL SOCKET DEL CLIENTE REENVIADO
								
								miSocket.close();//CIERRO EL SOCKET DEL CLIENTE QUE LLEGO
								
							}
						}/*
						//TEST
						ObjectOutputStream paqueteReEnvioDeMapa=new ObjectOutputStream(miSocket.getOutputStream());
						paqueteReEnvioDeMapa.writeObject(aux);
						paqueteReEnvioDeMapa.close();//CIERRO EL FLUJO DE DATOS
						miSocket.close();
						*/
					}
					
					if (opcion==UsuarioNuevo.TRAER_ARCHIVO) {
						File archivo = null;
						File archivosDelUsuario= new File("./usuarios_registrados/"+aux.getUser());
						File[] listOfFiles = archivosDelUsuario.listFiles();
						ArrayList<File> listaArchivos=new ArrayList<>();
						    for (int i = 0; i < listOfFiles.length; i++) {
						      if (listOfFiles[i].getName().equals(aux.getNombreArchivoAabrir())) {
						    	  archivo=new File("./usuarios_registrados/"+aux.getUser()+"/"+listOfFiles[i].getName());  
						      }
						    }
						   Scanner sc=new Scanner(archivo);
						   String lineasAmandar="";
						   JTextArea textArea = new JTextArea();
						   while (sc.hasNextLine()) {
							   textArea.append(sc.nextLine()+"\n");   
							   
						}
						   sc.close();
						   ObjectOutputStream envia =new ObjectOutputStream(miSocket.getOutputStream());
						   envia.writeObject(textArea);
						   miSocket.close();
					}
					
					if (opcion==UsuarioNuevo.REGISTRAR) {
						try {
							users.controlDeUsuarios();
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String usuario=aux.getUser();
						int pass=aux.getPass();
						
						
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
								new File("./usuarios_registrados/"+aux.getUser()).mkdirs();
								DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());
								//areatexto.append("IP "+miSocket.getInetAddress().getHostAddress()+" "+aux.getUser()+" "+aux.getPass()+"\n");
								//areatexto.append("Registro exitoso!"+"\n");
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
						
						
						
						List<UsuarioNuevo> usuarios=users.getUsers();
						
						boolean marca = false;
						UsuarioNuevo usuarioLoggeado = null;
						
						
							for (int i = 0; i < usuarios.size(); i++) {
								if(usuario.equals(usuarios.get(i).getUser()) && 
										pass==(usuarios.get(i).getPass())){
									marca = true;
									usuarioLoggeado=usuarios.get(i);
									//usuarioLoggeado.setDirectorio("./usuarios_registrados/"+usuarioLoggeado.getUser());
									break;
								}
							
							}
						
						if(marca){
							
							File archivosDelUsuario= new File("./usuarios_registrados/"+usuarioLoggeado.getUser());
							File[] listOfFiles = archivosDelUsuario.listFiles();
							ArrayList<File> listaArchivos=new ArrayList<>();
							    for (int i = 0; i < listOfFiles.length; i++) {
							      if (listOfFiles[i].isFile()) {
							    	  listaArchivos.add(listOfFiles[i]);
							      }
							    }
							    
							
							ObjectOutputStream salida=new ObjectOutputStream(miSocket.getOutputStream());
							UsuarioNuevo usuarioConMensajeYsusArchivos=new UsuarioNuevo();
							usuarioConMensajeYsusArchivos.setListaArchivos(listaArchivos);
							
							usuarioConMensajeYsusArchivos.setMensaje("Loggeo OK!");
							salida.writeObject(usuarioConMensajeYsusArchivos);
							//areatexto.append("IP "+miSocket.getInetAddress().getHostAddress()+" "+aux.getUser()+" "+aux.getPass()+"\n");
							/*
							DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());
							flujoSalida.writeUTF("Loggeo OK!");
							*/
							
							miSocket.close();
								
						}
						else{
							
							ObjectOutputStream salida=new ObjectOutputStream(miSocket.getOutputStream());
							UsuarioNuevo usuarioFallido=new UsuarioNuevo();
							usuarioFallido.setMensaje("Loggeo INCORRECTO!");
							salida.writeObject(usuarioFallido);
							/*
							DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());
							flujoSalida.writeUTF("Loggeo INCORRECTO!");
							*/
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

