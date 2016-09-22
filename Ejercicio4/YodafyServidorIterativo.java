import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo {
    public static void main(String[] args) {
        DatagramSocket socketServidor;
        DatagramPacket paquete;
        Socket socketServicio=null;
        InetAddress direccion;
        byte []buffer=new byte[256];
        int puerto=888;

        try{
            //Iniciamos el socket del servidor
            socketServidor= new DatagramSocket(puerto);
            do{
                //DatagramPacket para guardar los datos recibidos por el socket
                paquete= new DatagramPacket(buffer, buffer.length);
                //EL servidor estará esperando a recibir el DatagramPacket
                socketServidor.receive(paquete);

                //Llamamos al objeto procesador para que modifique el texto y lo envíe al cliente.
                ProcesadorYodafy procesador=new ProcesadorYodafy(paquete);
                procesador.procesa();
            }while(true);
        }catch(IOException E){
            System.out.println("Error: No se pudo atender en el puerto: "+puerto);
        }
    }
}
