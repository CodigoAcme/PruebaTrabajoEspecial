package ventanas.only;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.event.ActionEvent;

import javax.print.attribute.standard.JobPriority;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JList;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PantallaEditora {

	private JFrame frm;
	private JTextField textField;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JList list;
	private JScrollPane scrollPane_1;
	private JList list_1;
	private JScrollPane scrollPane_2;
	private JTextField nombreArchivo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaEditora window = new PantallaEditora();
					window.frm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PantallaEditora() {
		initialize();
	}
	public PantallaEditora(String titulo) {
		frm = new JFrame();
		
		frm.setTitle(titulo);
		frm.setBounds(100, 100, 700, 600);
		//frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		frm.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		
		scrollPane.setBounds(44, 76, 327, 400);
		frm.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				//JOptionPane.showMessageDialog(null, "Se presionó la tecla: "+arg0.getKeyChar());
			}
		});
	
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(44, 515, 529, 19);
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
		btnOpen.setIcon(new ImageIcon(PantallaEditora.class.getResource("/images/folder.png")));
		btnOpen.setBounds(44, 12, 141, 52);
		frm.getContentPane().add(btnOpen);
		
		JButton btnSave = new JButton("Guardar");
		btnSave.setIcon(new ImageIcon(PantallaEditora.class.getResource("/images/save.jpg")));
		btnSave.setBounds(220, 12, 151, 52);
		frm.getContentPane().add(btnSave);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(413, 76, 160, 147);
		frm.getContentPane().add(scrollPane_1);
		
		list = new JList();
		scrollPane_1.setViewportView(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
			String nombre=(String) list.getSelectedValue();
			ventana=new VentanaChat(nombre);
			ventana.setVisible(true);
			*/
				//1 = NO
				int rta=JOptionPane.showConfirmDialog(null, "Invitar a "+(String) list.getSelectedValue()+"?");
				if (rta==JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "Invitacion Enviada");
					textField.setText("Cuando "+list.getSelectedValue()+ " se conecte, aca diría: "+list.getSelectedValue().toString()+" se conectó!");
				}
				if (rta==JOptionPane.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "Invitacion cancelada");
				}
				
			}
		});
		
		DefaultListModel modelo = new DefaultListModel();
		modelo.addElement("Cris");
		modelo.addElement("Ruben");
		modelo.addElement("Fer");
		modelo.addElement("Juancito");
		modelo.addElement("Micho");
		modelo.addElement("Calvo");
		modelo.addElement("Gimenez");
		modelo.addElement("Perla");
		modelo.addElement("Ceci");
		list.setModel(modelo);
		
		
		
		JLabel lblUsuariosConectados = new JLabel("Usuarios conectados:");
		lblUsuariosConectados.setBounds(413, 12, 160, 52);
		frm.getContentPane().add(lblUsuariosConectados);
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
	}
	public JFrame getFrm(){
		return this.frm;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frm = new JFrame();
		frm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int rta=JOptionPane.showConfirmDialog(null, "¿Querés salir PEBETE?", "DANGER", JOptionPane.YES_NO_CANCEL_OPTION);
				if (rta==JOptionPane.YES_OPTION) {
					
					System.exit(1);
				}
			}
		});
	
		frm.setTitle("Prototipo");
		frm.setBounds(100, 100, 700, 600);
		
		frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frm.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		
		scrollPane.setBounds(44, 135, 327, 341);
		frm.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}
		});
	
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(44, 515, 529, 19);
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
						textArea.setText("");
						while (sc.hasNextLine()) {
							textArea.append(sc.nextLine()+"\n");
					}
					textField.setText("");
					File f=new File(fc.getSelectedFile().getPath());
					textField.setText("Se abrió el archivo: "+f.getName());
					nombreArchivo.setText(f.getName());
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
		//btnOpen.setIcon(new ImageIcon(PantallaEditora.class.getResource("/images/folder.png")));
		btnOpen.setBounds(44, 12, 141, 52);
		frm.getContentPane().add(btnOpen);
		
		JButton btnSave = new JButton("Guardar");
		btnSave.setIcon(new ImageIcon(PantallaEditora.class.getResource("/images/save.jpg")));
		btnSave.setBounds(220, 12, 151, 52);
		frm.getContentPane().add(btnSave);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(413, 76, 160, 147);
		frm.getContentPane().add(scrollPane_1);
		
		list = new JList();
		scrollPane_1.setViewportView(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
			String nombre=(String) list.getSelectedValue();
			ventana=new VentanaChat(nombre);
			ventana.setVisible(true);
			*/
				//1 = NO
				int rta=JOptionPane.showConfirmDialog(null, "Invitar a "+(String) list.getSelectedValue()+"?");
				if (rta==JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "Invitacion Enviada");
					textField.setText("Cuando "+list.getSelectedValue()+ " se conecte, aca diría: "+list.getSelectedValue().toString()+" se conectó!");
				}
				if (rta==JOptionPane.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null, "Invitacion cancelada");
				}
				
			}
		});
		
		DefaultListModel modelo = new DefaultListModel();
		modelo.addElement("Cris");
		modelo.addElement("Ruben");
		modelo.addElement("Fer");
		modelo.addElement("Juancito");
		modelo.addElement("Micho");
		modelo.addElement("Calvo");
		modelo.addElement("Gimenez");
		modelo.addElement("Perla");
		modelo.addElement("Ceci");
		list.setModel(modelo);

		
		
		
		JLabel lblUsuariosConectados = new JLabel("Usuarios conectados:");
		lblUsuariosConectados.setBounds(413, 12, 160, 52);
		frm.getContentPane().add(lblUsuariosConectados);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(416, 329, 157, 144);
		frm.getContentPane().add(scrollPane_2);
		
		list_1 = new JList();
		scrollPane_2.setViewportView(list_1);
		list_1.setModel(modelo);
		
		JLabel lblArchivos = new JLabel("MisArchivos");
		lblArchivos.setBounds(413, 302, 117, 15);
		frm.getContentPane().add(lblArchivos);
		
		JLabel lblArchivoAbierto = new JLabel("Archivo abierto: ");
		lblArchivoAbierto.setBounds(42, 90, 143, 33);
		frm.getContentPane().add(lblArchivoAbierto);
		
		nombreArchivo = new JTextField();
		nombreArchivo.setEditable(false);
		nombreArchivo.setBounds(166, 97, 205, 19);
		frm.getContentPane().add(nombreArchivo);
		nombreArchivo.setColumns(10);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//if (textArea.getLineCount()<=1) {
				if (textArea.getText().isEmpty()&&nombreArchivo.getText().isEmpty()) {
					int dialogResult =JOptionPane.showConfirmDialog(null, "¿Querés guardar un nuevo archivo?", "ADVERTENCIA", JOptionPane.YES_NO_CANCEL_OPTION);
					if (dialogResult==JOptionPane.YES_OPTION) {
						JFileChooser saveFile = new JFileChooser();
	                    int saveOption = saveFile.showSaveDialog(frm);
	                    if(saveOption == JFileChooser.APPROVE_OPTION){

	                        try{
	                           PrintWriter pw = new PrintWriter(new File(saveFile.getSelectedFile().getPath()));
	                           pw.write(textArea.getText());
	                           pw.close();
	                           textField.setText("Se guardo el archivo: "+saveFile.getSelectedFile().getName());
								//JOptionPane.showMessageDialog(null, "Se guardo el archivo: "+saveFile.getSelectedFile().getName());
								nombreArchivo.setText(saveFile.getSelectedFile().getName());
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
							textField.setText("Se guardo el archivo "+saveFile.getSelectedFile().getName());
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally {
							
							pw.close();
							File f2=new File(saveFile.getSelectedFile().getAbsolutePath());
							textField.setText("Se guardo el archivo: "+f2.getName());
							//JOptionPane.showMessageDialog(null, "Se guardo el archivo: "+f2.getName());
						}
                    }
				}
			}
		});
	}
}