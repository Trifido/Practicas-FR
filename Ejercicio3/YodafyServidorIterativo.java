import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo extends Thread{
    // Puerto de escucha
    int port=8989;
    ServerSocket serverSocket;
    Socket socketServicio=null;
    
    public void run(){
        try {
            //Inicializamos el serverSocket con el puerto de escucha.
            serverSocket= new ServerSocket(port);
            do {
                //El serverSocket se mantiene en escucha
                socketServicio= serverSocket.accept();
                
                ProcesadorYodafy procesador=new ProcesadorYodafy(socketServicio);
                procesador.procesa();
            } while (true);
            
        } catch (IOException e) {
            System.err.println("Error al escuchar en el puerto "+port);
        }
    }
}
