package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.DefaultListModel;

/**
 * Servidor de chat.
 * Acepta conexiones de clientes, crea un hilo para atenderlos, y espera la
 * siguiente conexion.
 * 
 *
 */
public class ServidorEditorParalelo
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
        new ServidorEditorParalelo();
    }

    /**
     * Se mete en un bucle infinito para ateder clientes, lanzando un hilo
     * para cada uno de ellos.
     */
    public ServidorEditorParalelo()
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
