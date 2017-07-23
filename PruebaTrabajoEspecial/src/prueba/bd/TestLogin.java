package prueba.bd;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import registros.DBusuarios;
import registros.Usuario;
import ventanas.only.Login;
import ventanas.only.PantallaEditora;

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

public class TestLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestLogin frame = new TestLogin();
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
	public TestLogin() {
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("INICIAR SESION");
		
		btnNewButton.setBounds(73, 228, 171, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
	boolean marca = false;
				
				if(!textField.getText().isEmpty() && !passwordField.getText().isEmpty()){
							marca = true;	
						}
				if(marca){
					Prueba coneccion=new Prueba();
					boolean resultado=coneccion.existeUsuario(textField.getText(), passwordField.getText().hashCode());
					coneccion.desconectar();
					if (resultado==true) {
						//JOptionPane.showMessageDialog(null, "Success!");
						PantallaEditora pantalla = new PantallaEditora();
						pantalla.getFrm().setTitle("Usuario: "+textField.getText());
						pantalla.getFrm().setVisible(true);
						TestLogin.this.dispose();
					}
					else{
						JOptionPane.showMessageDialog(null, "NO ESTA REGISTRADO! Intentalo nuevamente");
						textField.setText("");
						passwordField.setText("");
						System.exit(ABORT);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Los campos no pueden quedar vacios!");
				}	
			}
		});

		
		
		
		
		
		
		
		
		
		JButton btnNewButton_1 = new JButton("REGISTRARSE");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
boolean marca = false;
				
				if(!textField.getText().isEmpty() && !passwordField.getText().isEmpty()){
							marca = true;	
						}
				if(marca){
					
						Prueba coneccion2=new Prueba();
						coneccion2.agregarUsuario(textField.getText(), passwordField.getText().hashCode());
						coneccion2.desconectar();
						PantallaEditora pantalla2 = new PantallaEditora();
						pantalla2.getFrm().setTitle("Usuario: "+textField.getText());
						pantalla2.getFrm().setVisible(true);
						TestLogin.this.dispose();
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Los campos no pueden quedar vacios!");
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
		
		
		
	}
	

}
