package servidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ventanas.only.EscuchaCliente;
import ventanas.only.Server;

/**
 * Servidor de chat.
 * Acepta conexiones de clientes, crea un hilo para atenderlos, y espera la
 * siguiente conexion.
 * 
 *
 */
public class ServidorChat
{
    /** Lista en la que se guaradara toda la conversacion */
    private DefaultListModel<String> charla = new DefaultListModel<String>();
    private ServerSocket socketServidor;
    /**
     * Instancia esta clase.
     * @param args
     */
    public static void main(String[] args)
    {
        new ServidorChat();
    }

    /**
     * Se mete en un bucle infinito para ateder clientes, lanzando un hilo
     * para cada uno de ellos.
     */
    public ServidorChat()
    {
        try
        {	
            socketServidor = new ServerSocket(5557);
            while (true)
            {
                Socket cliente = socketServidor.accept();
                Runnable nuevoCliente = new HiloDeCliente(charla, cliente);
                Thread hilo = new Thread(nuevoCliente);
                hilo.start();
                
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
        	try {
				socketServidor.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
}
