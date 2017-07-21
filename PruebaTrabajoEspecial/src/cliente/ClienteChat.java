package cliente;
/**
 * 
 * Cliente de un chat
 */
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Clase con el main de un cliente del chat.
 * Establece la conexion y crea la ventana y la clase de control.
 * 
 *
 */

public class ClienteChat
{
    /** Socket con el servidor del chat */
    private Socket socket;


    /**
     * Arranca el Cliente de chat.
     * @param args
     */
    public static void main(String[] args)
    {
        new ClienteChat();
    }

    /**
     * Crea la ventana, establece la conexi�n e instancia al controlador.
     */
    public ClienteChat()
    {
        try
        {
      
            socket = new Socket("localhost", 5557);
            Cliente control = new Cliente(socket);
            control.setVisible(true);
            control.setLocationRelativeTo(null);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
       
    }


}
