package ventanas.only;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class Cliente extends Thread{

	private static JFrame frm;
	private static JTextField textField;
	private static JTextArea textArea;
	private static JScrollPane scrollPane;
	private static Socket s=null;
	private static Thread client;
	private static int PUERTO=9000;
	private static DataInputStream f=null;
	private static DataOutputStream dataOut = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cargarInterfaz();
					client = new Thread(new Cliente());
					
					client.start();
					
					//Cliente window = new Cliente();
					//window.frm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public static void cargarInterfaz(){
		//Cliente window = new Cliente();
		//window.frm.setVisible(true);
		//window.run();
		frm = new JFrame();
		frm.setTitle("CLIENTE @Gutiérrez-Montenegro");
		frm.setBounds(100, 100, 450, 300);
		frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frm.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 76, 327, 119);
		frm.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String textoAmandar=arg0.getKeyChar()+"";
				textField.setText(textoAmandar);
				try {
					dataOut.writeUTF(textoAmandar);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(44, 225, 327, 19);
		frm.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		JButton btnOpen = new JButton("Abrir");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Scanner sc;
				try {
					JFileChooser fc=new JFileChooser();
					fc.setCurrentDirectory(new java.io.File(""));
					fc.setDialogTitle("Elige tu archivo a abrir");
					fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					if (fc.showOpenDialog(fc)==JFileChooser.APPROVE_OPTION) {
						sc = new Scanner(new File(fc.getSelectedFile().getAbsolutePath()));
						
						while (sc.hasNextLine()) {
							textArea.append(sc.nextLine()+"\n");
					}
					textField.setText("");
					File f=new File(fc.getSelectedFile().getPath());
					textField.setText("Se abrió el archivo: "+f.getName());
					sc.close();
					}else{
						textField.setText("No seleccionó ningún archivo para abrir!");
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnOpen.setIcon(new ImageIcon(Cliente.class.getResource("/images/folder.png")));
		btnOpen.setBounds(44, 12, 141, 52);
		frm.getContentPane().add(btnOpen);
		
		JButton btnSave = new JButton("Guardar");
		btnSave.setIcon(new ImageIcon(Cliente.class.getResource("/images/save.jpg")));
		btnSave.setBounds(220, 12, 151, 52);
		frm.getContentPane().add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textArea.getLineCount()<=1) {
					
					int dialogResult =JOptionPane.showConfirmDialog(null, "¿Querés guardar un nuevo archivo?", "ADVERTENCIA", JOptionPane.YES_NO_CANCEL_OPTION);
					if (dialogResult==JOptionPane.YES_OPTION) {
						JFileChooser saveFile = new JFileChooser();
	                    int saveOption = saveFile.showSaveDialog(frm);
	                    if(saveOption == JFileChooser.APPROVE_OPTION){

	                        try{
	                           PrintWriter pw = new PrintWriter(new File(saveFile.getSelectedFile().getPath()));
	                           pw.write(textArea.getText());
	                           pw.close();
	                        }catch(Exception ex){

	                        }
	                    }
					}
				} else {
					JFileChooser saveFile = new JFileChooser();
                    int saveOption = saveFile.showSaveDialog(frm);
                    if(saveOption == JFileChooser.APPROVE_OPTION){
						PrintWriter pw = null;
						try {
							pw = new PrintWriter(new File(saveFile.getSelectedFile().getAbsolutePath()));
							pw.print(textArea.getText());
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally {
							
							pw.close();
							File f2=new File(saveFile.getSelectedFile().getAbsolutePath());
							textField.setText("Se guardo el archivo: "+f2.getName());
						}
                    }
				}
			}
		});
		frm.setVisible(true);
		
	}
	public Cliente() {
		try {
			s = new Socket("localhost",PUERTO);
			textField.setText("Me conecté al SERVIDOR!");
			f = new DataInputStream(s.getInputStream());
			dataOut = new DataOutputStream(s.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run(){
		try{
				
				textArea.append(f.readUTF());
				dataOut.writeUTF(textArea.getText());
		}catch(Exception e){
			}
	}
}