package ventanas.only;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Server extends Thread {
	public static List<EscuchaCliente> listaClientesConectados;
	private static ServerSocket serverSocket;
	private static Thread server;
	private static int PUERTO=9000;
	public static JTextArea textArea;
	//private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cargarInterfaz();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally {
					listaClientesConectados.clear();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public static void cargarInterfaz(){
		listaClientesConectados=new LinkedList<>();
		JFrame frame=new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				if (serverSocket != null) {
					try {
						server.stop();
						for (EscuchaCliente cliente : listaClientesConectados) {
							cliente.getS().close();
						}
						serverSocket.close();
					} catch (IOException e) {
						textArea.append("Fallo al intentar detener el servidor." + System.lineSeparator());
						e.printStackTrace();
						System.exit(1);
					}
				}
			}
		});
		frame.setTitle("Servidor - Editor Multi-Persona");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 600);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 23, 395, 467);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		//textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JButton btnConectar = new JButton("Conectar");
		JButton btnDesconectar = new JButton("Desconectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				///
				server = new Thread(new Server());
				server.start();
				btnConectar.setEnabled(false);
				btnDesconectar.setEnabled(true);
			}
		});
		btnConectar.setBounds(260, 517, 117, 25);
		contentPane.add(btnConectar);
		
		
		btnDesconectar.setBounds(66, 517, 144, 25);
		contentPane.add(btnDesconectar);
		
		JLabel lblLog = new JLabel("Log");
		lblLog.setBounds(180, 0, 70, 15);
		contentPane.add(lblLog);
		
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					server.stop();
					for (EscuchaCliente cliente : listaClientesConectados) {
						cliente.getS().close();
						textArea.append("Cliente: "+cliente.getS().getInetAddress().getHostName()+" desconectado"+System.lineSeparator());
					}
					serverSocket.close();
					textArea.append("Servidor desconectado."+System.lineSeparator());
				} catch (IOException e1) {
					textArea.append("Fallo al intentar detener el servidor." + System.lineSeparator());
					e1.printStackTrace();
				}
				
				btnDesconectar.setEnabled(false);
				btnConectar.setEnabled(true);
			}
		});
		
		
		
		frame.setVisible(true);
	}
	public void run(){
		try {
		textArea.append("Iniciando el servidor..." + System.lineSeparator());
		serverSocket = new ServerSocket(PUERTO);
		textArea.append("Servidor esperando conexiones..." + System.lineSeparator());
		while (true) {
			
			Socket cliente;
			cliente = serverSocket.accept();
			DataInputStream in=new DataInputStream(cliente.getInputStream());
			DataOutputStream out=new DataOutputStream(cliente.getOutputStream());
			String ip = cliente.getInetAddress().getHostAddress();
			textArea.append(ip + " se ha conectado" + System.lineSeparator());
			

			//Atender a los clientes
			EscuchaCliente client=new EscuchaCliente(cliente,in,out);
			client.start();
			listaClientesConectados.add(client);
			
			}	
		}	catch (IOException e) {
			e.printStackTrace();
	}	}
}