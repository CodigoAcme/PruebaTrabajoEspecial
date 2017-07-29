package trabajo;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cliente.ClienteEditorParalelo;
import servidor.ServidorEditorParalelo;

import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.event.ActionEvent;

import javax.print.attribute.standard.JobPriority;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JList;
import javax.swing.JLabel;

public class PantallaEditora implements Runnable{

	private JFrame frm;
	private JTextField textField;
	private JTextField nombreArchivo;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_2;
	private JList list;
	private JList list_1;
	private JScrollPane scrollPane_1;
	private UsuarioNuevo usuario;
	private String archivoSeleccionado;
	private JFileChooser fc;//DE ABRIR
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
	public PantallaEditora(String titulo, UsuarioNuevo in) {
		
		frm = new JFrame();
		
		frm.setTitle(titulo);
		usuario=in;
		frm.setBounds(100, 100, 700, 600);
		frm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int rta=JOptionPane.showConfirmDialog(null, "¿Querés salir "+getFrm().getTitle()+" ?", "DANGER", JOptionPane.YES_NO_CANCEL_OPTION);
				if (rta==JOptionPane.YES_OPTION) {
					//eliminar de la lista de conectados
					
					try {
						Socket envia= new Socket(LoginNuevo.ipServer, 9999);
						ObjectOutputStream flujoSalida=new ObjectOutputStream(envia.getOutputStream());
						UsuarioNuevo aux=new UsuarioNuevo();
						aux.setClave(UsuarioNuevo.DESCONECCION);
						aux.setUser(titulo);
						flujoSalida.writeObject(aux);
						envia.close();
						System.exit(1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		frm.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		
		scrollPane.setBounds(44, 135, 327, 341);
		frm.getContentPane().add(scrollPane);
		
		JLabel lblArchivoAbierto = new JLabel("Archivo abierto: ");
		lblArchivoAbierto.setBounds(42, 90, 143, 33);
		frm.getContentPane().add(lblArchivoAbierto);
		
		nombreArchivo = new JTextField();
		nombreArchivo.setEditable(false);
		nombreArchivo.setBounds(166, 97, 205, 19);
		frm.getContentPane().add(nombreArchivo);
		nombreArchivo.setColumns(10);
		
		textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (!textArea.getText().isEmpty()) {
					textField.setText("Hay cambios en el archivo");
				}	
			}
		});
		scrollPane.setViewportView(textArea);
		
		
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(44, 515, 529, 19);
		frm.getContentPane().add(textField);
		textField.setColumns(10);
		

		
		
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
			
				//1 = NO
				
				int rta=JOptionPane.showConfirmDialog(null, "Invitar a "+(String) list.getSelectedValue()+"?","Invitación",JOptionPane.YES_NO_CANCEL_OPTION);
				if (rta==JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "Invitacion Enviada","Info",JOptionPane.INFORMATION_MESSAGE);
					
					try {
						Socket invitacion= new Socket(LoginNuevo.ipServer, 9999);
						ObjectOutputStream flujoSalida=new ObjectOutputStream(invitacion.getOutputStream());
						UsuarioNuevo senial=new UsuarioNuevo();
						JTextArea areaAux=textArea;
						senial.setClave(UsuarioNuevo.INVITACION);
						senial.setUser(titulo);
						senial.setCampoDeArchivo(areaAux);
						senial.setInvitado((String) list.getSelectedValue());
						flujoSalida.writeObject(senial);
						
						ObjectInputStream flujoEntrada=new ObjectInputStream(invitacion.getInputStream());
						senial=(UsuarioNuevo) flujoEntrada.readObject();
						if (senial.getRespuestaJpane()==JOptionPane.YES_OPTION) {
							usuario.setClave(UsuarioNuevo.COLABORANDO);//VER
							usuario.setColaborador(senial.getColaborador());
							//Aca se liberaria el listerner de teclado, kesyPress
							invitacion.close();
							JOptionPane.showMessageDialog(null, (String) list.getSelectedValue()+" acepto la invitacion!");
						}
						
						
						invitacion.close();
						
						
					} catch (IOException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				
			}
		});
		
		DefaultListModel modelo = new DefaultListModel();
		
		ArrayList<File> listaArchivosAux=new ArrayList<>();
		listaArchivosAux=usuario.getListaArchivos();
		for (File file : listaArchivosAux) {
			modelo.addElement(file.getName());
		}
	
		
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(416, 329, 157, 144);
		frm.getContentPane().add(scrollPane_2);
		
		list_1 = new JList();
		scrollPane_2.setViewportView(list_1);
		list_1.setModel(modelo);
		
		list_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int rta=JOptionPane.showConfirmDialog(null, "Abrir el archivo "+(String) list_1.getSelectedValue()+"?","Abrir",JOptionPane.YES_NO_CANCEL_OPTION);
				if (rta==JOptionPane.YES_OPTION) {
					
					textField.setText("Archivo "+list_1.getSelectedValue().toString()+" abierto!");
					
					textArea.append(list_1.getSelectedValue().toString());
					
					try {
						Socket arbirArchivo=new Socket(LoginNuevo.ipServer, 9999);
						UsuarioNuevo aux=new UsuarioNuevo();
						aux.setNombreArchivoAabrir(list_1.getSelectedValue().toString());//nombre del archivoSeleccionado
						aux.setClave(UsuarioNuevo.TRAER_ARCHIVO);
						aux.setUser(titulo);
						ObjectOutputStream envia=new ObjectOutputStream(arbirArchivo.getOutputStream());
						envia.writeObject(aux);
						
						
						ObjectInputStream recibido=new ObjectInputStream(arbirArchivo.getInputStream());
						JTextArea aux2=(JTextArea) recibido.readObject();
						textArea.setText(aux2.getText());
						nombreArchivo.setText(list_1.getSelectedValue().toString());
						arbirArchivo.close();
						
						
					} catch (IOException | ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		
		JLabel lblUsuariosConectados = new JLabel("Usuarios conectados:");
		lblUsuariosConectados.setBounds(413, 12, 160, 52);
		frm.getContentPane().add(lblUsuariosConectados);
		
		JLabel lblMisArchivos = new JLabel("Mis archivos");
		lblMisArchivos.setBounds(413, 285, 160, 52);
		frm.getContentPane().add(lblMisArchivos);
		
		btnSave.setEnabled(true);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textArea.getText().isEmpty()&&nombreArchivo.getText().isEmpty()||
						!textArea.getText().isEmpty()&&nombreArchivo.getText().isEmpty()) {
					int dialogResult =JOptionPane.showConfirmDialog(null, "¿Querés guardar un nuevo archivo?", "ADVERTENCIA", JOptionPane.YES_NO_CANCEL_OPTION);
					if (dialogResult==JOptionPane.YES_OPTION) {
						String fileName=JOptionPane.showInputDialog(null, "Ingresa el nombre de tu archivo");
						Socket envia;
						try {
							envia = new Socket(LoginNuevo.ipServer, 9999);
							ObjectOutputStream flujoSalida=new ObjectOutputStream(envia.getOutputStream());
							UsuarioNuevo aux=new UsuarioNuevo();
							aux.setClave(UsuarioNuevo.GUARDAR_ARCHIVO);
							
							JTextArea aux2=new JTextArea();
							aux2.setText(textArea.getText());//Estaria vacio
							
							aux.setUser(titulo);
							aux.setCampoDeArchivo(aux2);
							aux.setNombreArchivoAabrir(fileName);//Nombre del archivo a guardar
							flujoSalida.writeObject(aux);
							//Recibo la lista de los archivos actualizado
							ObjectInputStream flujoEntrada=new ObjectInputStream(envia.getInputStream());
							aux=(UsuarioNuevo) flujoEntrada.readObject();
							
							DefaultListModel modelo = new DefaultListModel();
							
							ArrayList<File> listaArchivosAux=new ArrayList<>();
							listaArchivosAux=aux.getListaArchivos();
							//Vacio la jList
							list_1.removeAll();
							for (File file : listaArchivosAux) {
								modelo.addElement(file.getName());
							}
							list_1.setModel(modelo);
							
							JOptionPane.showMessageDialog(null, "Archivo "+fileName+" guardado con exito!");
							textField.setText("Archivo "+fileName+" guardado con exito!");
							nombreArchivo.setText(fileName);
							envia.close();
						} catch (IOException | ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						}
					}
				else{//GUARDAR UN ARCHIVO YA ABIERTO
					try {
						////Creo que estas dos lineas estan de mas xq nunca las uso =\
						JTextArea mandar=new JTextArea();
						mandar.setText(textArea.getText());
						// Creo que estas dos lineas estan de mas xq nunca las uso =\
						Socket envia= new Socket(LoginNuevo.ipServer, 9999);
						ObjectOutputStream flujoSalida=new ObjectOutputStream(envia.getOutputStream());
						UsuarioNuevo aux=new UsuarioNuevo();
						aux.setClave(UsuarioNuevo.GUARDAR_ARCHIVO);
						
						JTextArea aux2=new JTextArea();
						aux2.setText(textArea.getText());
						
						aux.setUser(titulo);
						aux.setCampoDeArchivo(aux2);
						//aux.setNombreArchivoAabrir(list_1.getSelectedValue().toString());//Nombre del archivo a guardar
						aux.setNombreArchivoAabrir(nombreArchivo.getText());//Nombre del archivo a guardar
						flujoSalida.writeObject(aux);
						//Recibo la lista de los archivos actualizado
						ObjectInputStream flujoEntrada=new ObjectInputStream(envia.getInputStream());
						aux=(UsuarioNuevo) flujoEntrada.readObject();
						
						DefaultListModel modelo = new DefaultListModel();
						
						ArrayList<File> listaArchivosAux=new ArrayList<>();
						listaArchivosAux=aux.getListaArchivos();
						//Vacio la jList
						list_1.removeAll();
						for (File file : listaArchivosAux) {
							modelo.addElement(file.getName());
						}
						list_1.setModel(modelo);
						
						JOptionPane.showMessageDialog(null, "Archivo "+nombreArchivo.getText()+" guardado con exito!");
						textField.setText("Archivo "+nombreArchivo.getText()+" guardado con exito!");
						envia.close();
						
						
					} catch (IOException | ClassNotFoundException e) {
						
						e.printStackTrace();
					}
				}//fin else
			}//FIN ACTION PERFORMED
		});
		
		frm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
					Socket envia=new Socket(LoginNuevo.ipServer, 9999);
					UsuarioNuevo aux=new UsuarioNuevo();
					aux.setClave(UsuarioNuevo.ONLINE);;
					aux.setUser(titulo);
					ObjectOutputStream salida=new ObjectOutputStream(envia.getOutputStream());
					salida.writeObject(aux);
					
					envia.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
			}
		});
		Thread miHilo=new Thread(this);
		miHilo.start();
	}

	public JFrame getFrm(){
		return this.frm;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	public void ponerEnTextArea(String loquevenga){
		textArea.setText(loquevenga);
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//NO SE USA
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	private void initialize() {
	
	
		frm = new JFrame();
		frm.setBounds(700, 700, 700, 600);
		frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frm.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		
		scrollPane.setBounds(44, 76, 327, 400);
		frm.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		
	
		scrollPane.setRowHeaderView(textArea);
		
		textField = new JTextField();
		textField.setEditable(false);
		
		textField.setBounds(44, 515, 529, 19);
		frm.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSave = new JButton("Guardar");
		btnSave.setIcon(new ImageIcon(PantallaEditora.class.getResource("/images/save.jpg")));
		btnSave.setBounds(220, 12, 151, 52);
		frm.getContentPane().add(btnSave);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(413, 76, 160, 147);
		frm.getContentPane().add(scrollPane_1);
		
		list = new JList();
		list.setVisible(false);
		scrollPane_1.setViewportView(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int rta=JOptionPane.showConfirmDialog(null, "Invitar a "+(String) list.getSelectedValue()+"?");
				if (rta==JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "Invitacion Enviada");
					
				}
			
				
			}
		});
		
		DefaultListModel modelo = new DefaultListModel();
		
		modelo.addElement("");
	
		
		list.setModel(modelo);
		
		
		JLabel lblUsuariosConectados = new JLabel("Usuarios conectados:");
		lblUsuariosConectados.setBounds(413, 12, 160, 52);
		frm.getContentPane().add(lblUsuariosConectados);
		btnSave.setEnabled(false);
		
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "guardaría");
			}
		});
	}

@Override
public void run() {
	
	
	ServerSocket escuchaClientesConectados = null;
	try {
		escuchaClientesConectados = new ServerSocket(9090);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	Socket cliente;
	UsuarioNuevo usuarioOnline;
	while (true) {
		try {
			
			cliente=escuchaClientesConectados.accept();
			ObjectInputStream entrada=new ObjectInputStream(cliente.getInputStream());
			
			usuarioOnline=(UsuarioNuevo) entrada.readObject();
			if (usuarioOnline.getMensaje().equals("invitacion")) {
				int rta=JOptionPane.showConfirmDialog(null, usuarioOnline.getUser()+" te invi"
						+ "to a colaborar con su documento. Aceptás?", "INVITACIÓN", JOptionPane.YES_NO_CANCEL_OPTION);
				UsuarioNuevo senial=new UsuarioNuevo();
				senial.setRespuestaJpane(rta);
				if (rta==JOptionPane.YES_OPTION) {
					
					JOptionPane.showMessageDialog(null, "Comienza la edicion en paralelo del documento..");
					
					senial.setClave(UsuarioNuevo.COLABORANDO);
					
					
					//PantallaEditora paralelo=new PantallaEditora();
					//paralelo.ponerEnTextArea(usuarioOnline.getCampoDeArchivo().getText());
					//paralelo.getFrm().setVisible(true);
					//
					
				}
			}
			if (usuarioOnline.getMensaje().equals("desconeccion")) {
				ArrayList<HashMap<String, String>> mapa=usuarioOnline.getListaMapas();
				
				DefaultListModel modelo = new DefaultListModel();
				list.removeAll();
				for (HashMap<String, String> mapaDentroLista : mapa) {
					for(Map.Entry<String, String> entry: mapaDentroLista.entrySet()){
						modelo.addElement(entry.getValue());
					}
				}
				
				list.setModel(modelo);
			}
			if (usuarioOnline.getMensaje().equals("online")) {
				ArrayList<HashMap<String, String>> mapa=usuarioOnline.getListaMapas();
				
				DefaultListModel modelo = new DefaultListModel();
				list.removeAll();
				for (HashMap<String, String> mapaDentroLista : mapa) {
					for(Map.Entry<String, String> entry: mapaDentroLista.entrySet()){
						
							modelo.addElement(entry.getValue());
						
						
					}
				}
				
				list.setModel(modelo);
			}
			if (usuarioOnline.getMensaje().equals("servidorCaido")) {
				JOptionPane.showMessageDialog(null, "Hubo problemas con el servidor.", "Servidor caido", JOptionPane.WARNING_MESSAGE);
				System.exit(1);
			}
			
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	}
}