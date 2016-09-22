//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class YodafyClienteTCP extends Thread{
    String buferEnvio;
    String buferRecepcion;
    // Nombre del host donde se ejecuta el servidor:
    String host="localhost";
    // Puerto en el que espera el servidor:
    int port=8989;

    // Socket para la conexión TCP
    Socket socketServicio=null;

    public void run(){
        try {
            //Inicializamos el socket con la dirección y el puerto
            socketServicio= new Socket(host,port);
            
            //Creamos e inicializamos los flujos de datos
            PrintWriter outPrinter= new PrintWriter(socketServicio.getOutputStream(), true);
            BufferedReader inReader= new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
            
            buferEnvio= "Al monte del volcán debes ir sin demora";
            
            //Enviamos la cadena de caracteres con el texto.
            outPrinter.println(buferEnvio);
            outPrinter.flush();
            
            //Esperamos a leer la respuesta del servidor
            buferRecepcion= inReader.readLine();
            
            //Imprimimos la frase modificada
            System.out.println("Recibido: ");
            System.out.println(buferRecepcion);

            //Cerramos el socket
            socketServicio.close();
            // Excepciones:
        } catch (UnknownHostException e) {
                System.err.println("Error: Nombre de host no encontrado.");
        } catch (IOException e) {
                System.err.println("Error de entrada/salida al abrir el socket.");
        }
    }
}
