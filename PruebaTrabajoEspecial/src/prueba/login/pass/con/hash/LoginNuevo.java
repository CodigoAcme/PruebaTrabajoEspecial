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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.awt.event.ActionEvent;

public class LoginNuevo extends JFrame {

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
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("INICIAR SESION");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<UsuarioNuevo> usuarios = users.getUsers();
				boolean marca = false;
				UsuarioNuevo usuarioLoggeado = null;
				if(!textField.getText().isEmpty() && !passwordField.getText().isEmpty()){
					for (int i = 0; i < usuarios.size(); i++) {
						if(textField.getText().equals(usuarios.get(i).getUser()) && 
								passwordField.getText().hashCode()==(usuarios.get(i).getPass())){
							marca = true;
							usuarioLoggeado=usuarios.get(i);
							usuarioLoggeado.setDirectorio("./usuarios_registrados/"+usuarioLoggeado.getUser());
							break;
						}
					
					}
				}
				if(marca){
					
					PantallaEditora pantalla = new PantallaEditora(textField.getText(),usuarioLoggeado);
					JOptionPane.showMessageDialog(null, "Loggeo exitoso!","Informacion de Login",JOptionPane.INFORMATION_MESSAGE);
					pantalla.getFrm().setVisible(true);
					LoginNuevo.this.dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "USUARIO NO ENCONTRADO!","Problema de login",JOptionPane.ERROR_MESSAGE);
					//System.out.println("USUARIO NO ENCONTRADO");
					//ErrorDeSesion error = new ErrorDeSesion("USUARIO NO ENCONTRADO");
					//error.setLocationRelativeTo(null);
					//error.setVisible(true);
					textField.setText("");
					passwordField.setText("");
				}
				
			}
		});
		btnNewButton.setBounds(73, 228, 171, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("REGISTRARSE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				List<UsuarioNuevo> usuarios = users.getUsers();
				boolean marca = false;
				
				if(!textField.getText().isEmpty() && !passwordField.getText().isEmpty()){
					for (int i = 0; i < usuarios.size(); i++) {
						if(textField.getText().equals(usuarios.get(i).getUser())){
							marca = true;
							break;
						}
					
					}
				}
					else {
						if(textField.getText().isEmpty()){
							System.out.println("INGRESE USUARIO");
							ErrorDeSesion error = new ErrorDeSesion("INGRESE USUARIO");
							error.setLocationRelativeTo(null);
							error.setVisible(true);
							textField.setText("");
							passwordField.setText("");
						}
						else {
							System.out.println("INGRESE CONTRASE�A");
							ErrorDeSesion error = new ErrorDeSesion("INGRESE CONTRASE�A");
							error.setLocationRelativeTo(null);
							error.setVisible(true);
							textField.setText("");
							passwordField.setText("");
						}
					}
				
				if(marca){
					System.out.println("USUARIO NO DISPONIBLE");
					ErrorDeSesion error = new ErrorDeSesion("USUARIO NO DISPONIBLE");
					error.setLocationRelativeTo(null);
					error.setVisible(true);
					textField.setText("");
					passwordField.setText("");
				}
				else{
					if(!textField.getText().isEmpty() && !passwordField.getText().isEmpty()){
						usuarios.add(new UsuarioNuevo(textField.getText(), passwordField.getText().hashCode()));
						try {
							PrintWriter pw = new PrintWriter(new File(users.getPathIn()));
							for (int i = 0; i < usuarios.size(); i++) {
								pw.println(usuarios.get(i).getUser()+" "+usuarios.get(i).getPass());
							}
							pw.close();
							
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String crear="./usuarios_registrados/"+textField.getText();
						usuarioIngresado=new UsuarioNuevo(textField.getText(), passwordField.getText().hashCode(), crear);
						File carpeta=new File(crear);
						carpeta.mkdirs();
						JOptionPane.showMessageDialog(null, "REGISTRO EXITOSO!","Informacion de registro",JOptionPane.INFORMATION_MESSAGE);
						PantallaEditora pantalla = new PantallaEditora(textField.getText(),usuarioIngresado);
						pantalla.getFrm().setVisible(true);
						LoginNuevo.this.dispose();
						
					}
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
