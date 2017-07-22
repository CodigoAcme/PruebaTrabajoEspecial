package ventanas.only;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import registros.DBusuarios;
import registros.Usuario;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private DBusuarios users;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
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
				
				List<Usuario> usuarios = users.getUsers();
				boolean marca = false;
				
				if(!textField.getText().isEmpty() && !passwordField.getText().isEmpty()){
					for (int i = 0; i < usuarios.size(); i++) {
						if(textField.getText().equals(usuarios.get(i).getUser()) && 
								passwordField.getText().equals(usuarios.get(i).getPass())){
							marca = true;
							break;
						}
					
					}
				}
				if(marca){
					PantallaEditora pantalla = new PantallaEditora();
					pantalla.getFrm().setVisible(true);
					Login.this.dispose();
				}
				else {
					System.out.println("USUARIO NO ENCONTRADO");
					ErrorDeSesion error = new ErrorDeSesion("USUARIO NO ENCONTRADO");
					error.setLocationRelativeTo(null);
					error.setVisible(true);
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
				
				List<Usuario> usuarios = users.getUsers();
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
						usuarios.add(new Usuario(textField.getText(), passwordField.getText()));
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
						
						PantallaEditora pantalla = new PantallaEditora();
						pantalla.getFrm().setVisible(true);
						Login.this.dispose();
						
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
		
		users = new DBusuarios();
		try {
			users.controlDeUsuarios();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}
	

}
