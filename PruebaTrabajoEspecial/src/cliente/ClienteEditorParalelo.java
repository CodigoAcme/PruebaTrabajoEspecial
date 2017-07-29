package cliente;

/**
 * 
 * Cliente de un chat
 */
import java.net.Socket;

/**
 * Clase con el main de un cliente del chat.
 * Establece la conexion y crea la ventana y la clase de control.
 * 
 *
 */

public class ClienteEditorParalelo
{
    /** Socket con el servidor del chat */
    private Socket socket;


    /**
     * Arranca el Cliente de chat.
     * @param args
     */
    public static void main(String[] args)
    {
        new ClienteEditorParalelo();
    }

    /**
     * Crea la ventana, establece la conexi�n e instancia al controlador.
     */
    public ClienteEditorParalelo()
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
    public ClienteEditorParalelo(String texto)
    {
        try
        {
      
            socket = new Socket("localhost", 5557);
            Cliente control = new Cliente(socket,texto);
            control.setVisible(true);
            control.setLocationRelativeTo(null);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
       
    }


}
