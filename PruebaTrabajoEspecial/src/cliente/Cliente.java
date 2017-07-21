package cliente;


import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.GridLayout;

public class Cliente extends JFrame implements Runnable {

	private JPanel contentPane;
	
	private JTextArea textArea;
	
	int indice_ultima_linea;
	
	 /** Para lectura de datos del socket */
    private DataInputStream dataInput;

    /** Para escritura de datos en el socket */
    private DataOutputStream dataOutput;


    /**
     * Contruye una instancia de esta clase, lanzando un hilo para atender al
     * socket.
     * @param socket El socket
	 * Create the frame.
	 */
	public Cliente(Socket socket) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		textArea = new JTextArea();
		contentPane.add(textArea);
		
	
	    /**
	     * Contruye una instancia de esta clase, lanzando un hilo para atender al
	     * socket.
	     * @param socket El socket
	     * @param panel El panel del usuario
	     */
	 
	        try
	        {
	            dataInput = new DataInputStream(socket.getInputStream());
	            dataOutput = new DataOutputStream(socket.getOutputStream());
	         
	            /**
	    	     * Recoge el texto del panel y lo env�a por el socket.
	    	     * El panel llamar� a este m�todo cuando el usuario escriba algo
	    	     */
	            textArea.addKeyListener(new KeyAdapter() {
	    			@Override
	    			public void keyReleased(KeyEvent arg0) {
	
	    				 try
	    			        {
	    					
	    			            dataOutput.writeUTF(textArea.getText());
	    			        } catch (Exception excepcion)
	    			        {
	    			            excepcion.printStackTrace();
	    			        }
	    				
	    			}
	    			
	    		});
	            Thread hilo = new Thread(this);
	            hilo.start();
	        } catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }

   

	    /**
	     * M�todo run para antender al socket. Todo lo que llega por el socket se
	     * escribe en el panel.
	     */
	    public void run() {
	        try
	        {
	            while (true)
	            {
	            	 indice_ultima_linea = textArea.getDocument().getLength(); //retorna el numero de lineas
					 textArea.setCaretPosition(indice_ultima_linea); //ubica el cursor al final
	                String texto = dataInput.readUTF();
	                textArea.setText(texto);
	            }
	        }catch (EOFException e2)
	        {
	            System.out.println(e2.getMessage());
	        	
	        } 
	        
	        catch (Exception e)
	        {
	        	System.out.println(e.getMessage());
	        	
	        }
	        
	    }
	
}
