//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class YodafyClienteTCP {
    public static void main(String[] args) {
        String buferEnvio;
        String buferRecepcion;
        int bytesLeidos=0;
        // Nombre del host donde se ejecuta el servidor:
        String host="localhost";
        // Puerto en el que espera el servidor:
        int port=8989;

        // Socket para la conexión TCP
        Socket socketServicio=null;
        //Usamos los siguientes flujos para pasar objetos de tipo String
        PrintWriter outPrinter;
        BufferedReader inReader;

        try {
            // Creamos un socket que se conecte a "host" y "port":
            socketServicio= new Socket(host,port);
            //Con outPrinter enviamos texto.
            outPrinter= new PrintWriter(socketServicio.getOutputStream(), true);
            //Usamos inReader para leer el texto del socket.
            inReader= new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
            //Texto a enviar.
            buferEnvio= "Al monte del volcán debes ir sin demora";

            //Enviamos el texto.
            outPrinter.println(buferEnvio);
            outPrinter.flush();

            //Con el metodo readLine() leemos todo una línea de caracteres.
            buferRecepcion= inReader.readLine();

            //Mostremos la cadena de caracteres recibidos:
            System.out.println("Recibido: ");
            System.out.println(buferRecepcion);

            //Una vez terminado el servicio, cerramos el socket 
            socketServicio.close();

        // Excepciones:
        } catch (UnknownHostException e) {
                System.err.println("Error: Nombre de host no encontrado.");
        } catch (IOException e) {
                System.err.println("Error de entrada/salida al abrir el socket.");
        }
    }
}
