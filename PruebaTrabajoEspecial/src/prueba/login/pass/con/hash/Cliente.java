package prueba.login.pass.con.hash;

import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.*;

import java.net.*;

public class Cliente {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MarcoCliente mimarco=new MarcoCliente();
		/*
		mimarco.getContentPane().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
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
		});
		*/
		mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}


class MarcoCliente extends JFrame{
	
	public MarcoCliente(){
		
		setBounds(600,300,240,350);
				
		LaminaMarcoCliente milamina=new LaminaMarcoCliente();
		
		add(milamina);
		
		setVisible(true);
		}	
	
}

class LaminaMarcoCliente extends JPanel{
	
	public LaminaMarcoCliente(){
		
		this.nick=new JTextField(5);
		
		add(nick);
		
		JLabel texto=new JLabel("Chat");
		
		add(texto);
		
		this.ip=new JTextField(8);
		
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
			//String insertado=JOptionPane.showInputDialog("Ingresa la IP DEL SERVIDOR");
			String insertado=JOptionPane.showInputDialog("Ingresa la IP DEL SERVIDOR","localhost");
			//System.out.println(insertado);
			try {
				
				Socket miSocket=new Socket(insertado, 9999);
				
				//Voy a enviar estos datos a un cliente por medio del Servidor como intermediario 
				PaqueteEnvio datos=new PaqueteEnvio();
				datos.setIp(ip.getText());
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
	
		
		
		
	private JTextField campo1, nick, ip;
	
	private JButton miboton;
	
	private JTextArea campoChat;
	
	class PaqueteEnvio implements Serializable{
		
		private String ip;
		
		private String nick;
		
		private String mensaje;
		
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
}