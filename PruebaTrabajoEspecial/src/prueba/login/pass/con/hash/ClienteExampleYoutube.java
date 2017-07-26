package prueba.login.pass.con.hash;

import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;

import javax.swing.*;

import java.net.*;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClienteExampleYoutube {
	public static JLabel nickSalvador;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}

 
class MarcoCliente extends JFrame{//ahora este cliente, debe estar a la escucha de
	//los paquetes que le manden. O sea, el cliente tiene que hacer 2 TAREAS
	public MarcoCliente(){//1 escrbir el input de los mensajes y
						 //2do estar a la escucha permanentemente de los paquetes que le llegan
		setBounds(600,300,240,350);//Y para abrir un puerto/socket por donde recibirá ese paquete
									//Osea, cliente debe tener un SERVERSOCKET para ello
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		//Esta linea de baajo anda bien
		//addWindowListener(new EnvioOnline());
		
		addWindowListener(new EnvioOnline(milamina.dameElFukingNick()));
		//EnviaDesconeccion desconect=new EnviaDesconeccion();
		
		//addWindowListener(desconect);
		
		setVisible(true);
		
		
		}	
	
}
class EnviaDesconeccion implements WindowListener{

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
	
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//MI DESASTRE
		//Todo este bloque se ejecutará ni bien se cierrela App
		try {
			Socket miSocket=new Socket("192.168.0.14", 9999);
			JOptionPane.showMessageDialog(null, "DESCONECCION");
			PaqueteEnvio datos=new PaqueteEnvio();
			
			datos.setMensaje(" desconeccion");//de esta forma se que me conecte por 1era vez
			
			ObjectOutputStream paqueteDatos=new ObjectOutputStream(miSocket.getOutputStream());
			
			paqueteDatos.writeObject(datos);
			
			miSocket.close();
			
			
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	//FIN DESASTRE
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
//EVENTO DE VENTANA. AL ABRIR SE DISPARA
	//////////////////////////////ENVIO SEÑAL ONLINE//////////////
	class EnvioOnline extends WindowAdapter{
		private JLabel etiqueta;
		public EnvioOnline(JLabel benditoLabel) {
		this.etiqueta=benditoLabel;
		}
		//Es una clase adaptadora. Implementa todos los metodos pertenecientees a una interfaz
		//en este caso WIndowListener para no tener que implementarlos todos a la
		//hora de gestionar los eventos de ventana
		//O sea, si ENvioOnline implemente WindowListener me vería obligado
		//a sobreEscribir TODOS los métodos de esa interfaz
		//La clase adaptadora evita esto
		
		//La flecha de arriba de Eclipse me indica que estoy sobreEscribiendo un metodo
		public void windowOpened(WindowEvent e){
			//Todo este bloque se ejecutará ni bien se abre la App
			try {
				Socket miSocket=new Socket("192.168.0.14", 9999);
				//JOptionPane.showMessageDialog(null, "Entro al WIndowOpened");
				PaqueteEnvio datos=new PaqueteEnvio();
				
				datos.setMensaje(" online");//de esta forma se que me conecte por 1era vez
				datos.setNick(etiqueta.getText());
				ObjectOutputStream paqueteDatos=new ObjectOutputStream(miSocket.getOutputStream());
				
				paqueteDatos.writeObject(datos);
				
				miSocket.close();
				
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	//EL objeto que se va a mandar por la red tiene que ser serializable
			class PaqueteEnvio implements Serializable{
				
				private String ip;
				
				private String nick;
				
				private String mensaje;
				
				private ArrayList<String> ips=new ArrayList<>();
				
				private ArrayList<HashMap<String, String>> listaMapasIpUsuario=new ArrayList<>();
				
				
				public ArrayList<HashMap<String, String>> getListaMapasIpUsuario() {
					return listaMapasIpUsuario;
				}
				public void setListaMapasIpUsuario(ArrayList<HashMap<String, String>> listaMapasIpUsuario) {
					this.listaMapasIpUsuario = listaMapasIpUsuario;
				}
				public ArrayList<String> getIps() {
					return ips;
				}
				public void setIps(ArrayList<String> ips) {
					this.ips = ips;
				}
				public String getIp() {
					return ip;
				}
				public void setIp(String ip) {
					this.ip = ip;
				}
				public String getNick() {
					return nick;
				}
				public void setNick(String nick) {
					this.nick = nick;
				}
				public String getMensaje() {
					return mensaje;
				}
				public void setMensaje(String mensaje) {
					this.mensaje = mensaje;
				}
				
				
			}
class LaminaMarcoCliente extends JPanel implements Runnable{//La clase que tiene los componentes en juego, es la que debe estar a la escucha
	//debe ejecutar un hilo que este en 2do plano
	public LaminaMarcoCliente(){
		
		JLabel nNick=new JLabel("Nick: ");
		
		add(nNick);
		
		String usuario=JOptionPane.showInputDialog(null, "Ingrese nick: ", "Poné tu nick CABEZÓN!", JOptionPane.WARNING_MESSAGE);
		
		this.nick=new JLabel(usuario);
		
		
		
		
		add(nick);
		
		JLabel texto=new JLabel("Conectados: ");
		
		add(texto);
		
		this.ip=new JComboBox();
		
		/*
		this.ip.addItem("192.168.0.14");
		
		this.ip.addItem("192.168.0.19");
		*/
		add(ip);
		
		this.campoChat=new JTextArea(12,20);
		
		add(campoChat);
	
		campo1=new JTextField(20);
	
		add(campo1);		
	
		miboton=new JButton("Enviar");
		
		EnviaTexto miEvento=new EnviaTexto();//Clase interna
		
		//EnviaTecla tecla=new EnviaTecla();
		
		//campo1.addKeyListener(tecla);
		
		miboton.addActionListener(miEvento);//Pongo el boton a la escucha de la instancia de mi evento
		
		add(miboton);	
		//El hilo lo pongo en el constructor de la lámina
		Thread miHilo=new Thread(this);//la clase que tiene el hilo, el metodo run()
		miHilo.start();
		
	}
	
	private class EnviaTecla implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
				try{
						Socket botonPResinado=new Socket("localhost", 9999);
						DataOutputStream salida=new DataOutputStream(botonPResinado.getOutputStream());
						salida.writeUTF(e.getKeyChar()+"");
						salida.close();
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class EnviaTexto implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//System.out.println(campo1.getText());
			
			//String insertado=JOptionPane.showInputDialog("Ingresa la IP DEL SERVIDOR","localhost");
			
			campoChat.append(campo1.getText()+"\n");
			try {
				
				Socket miSocket=new Socket("192.168.0.14", 9999);
				
				//Voy a enviar estos datos a un cliente por medio del Servidor como intermediario 
				PaqueteEnvio datos=new PaqueteEnvio();
				datos.setIp(ip.getSelectedItem().toString());
				datos.setNick(nick.getText());
				datos.setMensaje(campo1.getText());
				
				ObjectOutputStream paqueteDatos=new ObjectOutputStream(miSocket.getOutputStream());
				paqueteDatos.writeObject(datos);
				paqueteDatos.close();
				//DataOutputStream flujoSalida=new DataOutputStream(miSocket.getOutputStream());
				//flujoSalida.writeUTF(campo1.getText());
				//flujoSalida.close();//cierra el FLUJO
				
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// Puede pasar que la ip este mal dado, que este ocupado, que haya
				///congestion en la red x el trafico, etc.
				System.out.println(e1.getMessage());
				JOptionPane.showMessageDialog(null, "Servidor sin respuesta");
			}
		}
		
	}
	
		
		
		
	private JTextField campo1;
	
	private JComboBox ip;
	
	private JLabel nick;
	
	public JLabel dameElFukingNick(){
		return this.nick;
	}
	
	private JButton miboton;
	
	private JTextArea campoChat;
	
	
	@Override
	public void run() {
		try {
			ServerSocket servidorCliente=new ServerSocket(9090);
			
			
			Socket cliente;
			PaqueteEnvio paqueteRecibido;//Es para que almacene la dirIP del mensaje
			//a enviar para el otro cliente
			while (true) {
				
				cliente=servidorCliente.accept();
				//Tomo los mensajes recibidos
				
				ObjectInputStream flujoEntrada=new ObjectInputStream(cliente.getInputStream());
				
				paqueteRecibido=(PaqueteEnvio) flujoEntrada.readObject();
				/*
				//DESASTRE
				if (paqueteRecibido.getMensaje().equals(" desconeccion")) {
					ArrayList<String> ipsReceived=new ArrayList<>();
					//Para que aparezcan los nicks de los conectados
					//Uso un Mapa con clave:ip valor:nick
					ipsReceived=paqueteRecibido.getIps();
					ip.removeAllItems();
					for (String ipsRecibidas : ipsReceived) {
						ip.addItem(ipsRecibidas);
					}
				}
				*/
				//FIN DESASTRE
				if (!paqueteRecibido.getMensaje().equals(" online")) {
					//Ya estamos chateando
					campoChat.append(paqueteRecibido.getNick()+": "
					+paqueteRecibido.getMensaje()+"\n");
				} else {//Sino, es porque se contecto otro cliente
					
					//campoChat.append("\n"+paqueteRecibido.getIps());
					ArrayList<String> ipsReceived=new ArrayList<>();
					
					
					//Para que aparezcan los nicks de los conectados
					//Uso un Mapa con clave:ip valor:nick
					ipsReceived=paqueteRecibido.getIps();
					
					ArrayList<HashMap<String, String>> listaMapaAux=new ArrayList<>();
					//HashMap<String, String> usuariosConectados=new HashMap<>();
					listaMapaAux=paqueteRecibido.getListaMapasIpUsuario();//Es la lista recibida actualizada
					
					
					ip.removeAllItems();
					
					for (HashMap<String, String> ipUsuario : listaMapaAux) {
						for(Map.Entry<String, String> entry: ipUsuario.entrySet()){
							JOptionPane.showMessageDialog(null, "MAPA: "+entry.getKey()+" "+entry.getValue());
							
							ip.addItem(entry.getValue());
						}
					}
					/*
					for (String ipsRecibidas : ipsReceived) {
						ip.addItem(ipsRecibidas);
					}*/
					
				}
				
				
				
				
			}
			
			
		} catch (IOException | ClassNotFoundException e) {
			
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
}