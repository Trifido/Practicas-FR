//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class YodafyClienteTCP {
    public static void main(String[] args) {
        int puerto=888;
        InetAddress direccion;
        DatagramPacket paquete;
        byte[] buffer= new byte[256];
        buffer= "Al monte del volcán debes ir sin demora".getBytes();
        DatagramSocket socket;

        try{
            
            //Creamos el socket con el que nos comunicamos con el servidor.
            socket= new DatagramSocket();
            direccion= InetAddress.getByName("localhost");
            paquete= new DatagramPacket(buffer, buffer.length, direccion, puerto);
            //Enviamos el paquete al servidor
            socket.send(paquete);

            //Creamos el paquete donde vamos a guarda los datos recibidos
            byte[] datos= new byte[256];
            paquete= new DatagramPacket(datos, datos.length);
            
            //Esperamos al DatagramPacket
            socket.receive(paquete);
            paquete.getData();
            paquete.getPort();
            paquete.getAddress();

            //Imprimimos el texto modificado
            System.out.println("Recibido: ");
            for(int i=0; i<datos.length; i++){
                System.out.print((char)datos[i]);
            }
            //Cerramos el socket
            socket.close();

        }catch(IOException E){
            System.out.println("Error: No se pudo atender en el puerto: "+puerto);
        }
    }
}
