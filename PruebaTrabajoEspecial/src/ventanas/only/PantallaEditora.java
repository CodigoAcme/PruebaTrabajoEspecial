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
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PantallaEditora {

	private JFrame frm;
	private JTextField textField;
	private JTextArea textArea;
	private JScrollPane scrollPane;
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
	public JFrame getFrm(){
		return this.frm;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frm = new JFrame();
		frm.setTitle("Editor MultiPersona @Gutiérrez-Montenegro");
		frm.setBounds(100, 100, 450, 300);
		frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frm.getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 76, 327, 119);
		frm.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textArea.append(System.lineSeparator()+System.lineSeparator());
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
		btnOpen.setIcon(new ImageIcon(PantallaEditora.class.getResource("/images/folder.png")));
		btnOpen.setBounds(44, 12, 141, 52);
		frm.getContentPane().add(btnOpen);
		
		JButton btnSave = new JButton("Guardar");
		btnSave.setIcon(new ImageIcon(PantallaEditora.class.getResource("/images/save.jpg")));
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
	}
}