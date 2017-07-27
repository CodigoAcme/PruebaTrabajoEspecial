package prueba.login.pass.con.hash;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import registros.DBusuarios;
import registros.Usuario;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class LoginNuevo extends JFrame {
	public static String pathIP="ip.in";
	public static String ipServer;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private DBusuariosNuevo users;
	private UsuarioNuevo usuarioIngresado;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginNuevo frame = new LoginNuevo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginNuevo() {
		Scanner sc = null;
		try {
			sc = new Scanner(new File(pathIP));
			ipServer=sc.next();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		finally {
			sc.close();
		}
		
		
		if (ipServer.isEmpty()||ipServer.equals(null)) {
			JOptionPane.showMessageDialog(null, "Ingresar la IP del servidor en el archivo "+pathIP);
			System.exit(ABORT);
		}
		
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("INICIAR SESION");
		DBusuariosNuevo users=new DBusuariosNuevo();
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					if (!textField.getText().isEmpty()&&!passwordField.getText().isEmpty()) {
						
						
						
						Socket enviaServidor=new Socket(ipServer, 9999);
						UsuarioNuevo paqueteEnvio=new UsuarioNuevo(textField.getText(), passwordField.getText().hashCode(),UsuarioNuevo.LOGGEO);
						ObjectOutputStream flujoSalida=new ObjectOutputStream(enviaServidor.getOutputStream());
						
						flujoSalida.writeObject(paqueteEnvio);
						
						//DataInputStream mensajeServidor=new DataInputStream(enviaServidor.getInputStream());
						ObjectInputStream mensajeServidor=new ObjectInputStream(enviaServidor.getInputStream());
						UsuarioNuevo respuesta=(UsuarioNuevo) mensajeServidor.readObject();
						//String respuesta=mensajeServidor.readUTF();
						JOptionPane.showMessageDialog(null, respuesta.getMensaje());
					
						enviaServidor.close();
						
						if (respuesta.getMensaje().equals("Loggeo OK!")) {
							PantallaEditora pantalla=new PantallaEditora(textField.getText(), new UsuarioNuevo(textField.getText(), passwordField.getText().hashCode(),respuesta.getListaArchivos()));
							LoginNuevo.this.dispose();
							pantalla.getFrm().setVisible(true);
						}
						
					}
					else{
						JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios!");
					}
					
					
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(73, 228, 171, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("REGISTRARSE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					if (!textField.getText().isEmpty()&&!passwordField.getText().isEmpty()) {
						
						Socket enviaServidor=new Socket(ipServer, 9999);
						UsuarioNuevo paqueteEnvio=new UsuarioNuevo(textField.getText(), passwordField.getText().hashCode(),UsuarioNuevo.REGISTRAR);
						ObjectOutputStream flujoSalida=new ObjectOutputStream(enviaServidor.getOutputStream());
						
						flujoSalida.writeObject(paqueteEnvio);
						
						DataInputStream mensajeServidor=new DataInputStream(enviaServidor.getInputStream());
						JOptionPane.showMessageDialog(null, mensajeServidor.readUTF());
					
						enviaServidor.close();
						
						new File("./usuarios_registrados/"+textField.getText()).mkdirs();
						
					}
					else{
						JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios!");
					}
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
		btnNewButton_1.setBounds(256, 228, 126, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblUsuario = new JLabel("USUARIO");
		lblUsuario.setBounds(28, 78, 66, 14);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("CONTRASE\u00D1A");
		lblContrasea.setBounds(28, 142, 117, 14);
		contentPane.add(lblContrasea);
		
		textField = new JTextField();
		textField.setBounds(174, 76, 171, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(174, 140, 171, 20);
		contentPane.add(passwordField);
		
		users = new DBusuariosNuevo();
		try {
			users.controlDeUsuarios();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}
	

}
