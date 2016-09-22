//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafy {
    private Random random;
    private DatagramPacket paquete;
    byte[] datos=null;
    byte[] dEnviar=null;

    // Constructor que tiene como parámetro una referencia al DatagramPacket 
    public ProcesadorYodafy(DatagramPacket paquete) {
        this.paquete=paquete;
        random=new Random();
    }

    // Aquí es donde se realiza el procesamiento realmente:
    void procesa(){
        //Obtenemos los bytes para formar la cadena de caracteres
        datos=paquete.getData();
        String fraseSinMod= new String(datos,0,paquete.getLength());

        //Modificamos la frase y la almacenamos en el datagram que enviamos al cliente.
        String fraseMod=yodaDo(fraseSinMod);
        dEnviar= fraseMod.getBytes();
        paquete= new DatagramPacket(dEnviar, dEnviar.length, paquete.getAddress(), paquete.getPort());

        try{
            //Enviamos el paquete al cliente
            DatagramSocket socket= new DatagramSocket();
            socket.send(paquete);
        }catch(IOException E){
            System.out.println("Error");
        }
    }

    // Yoda interpreta una frase y la devuelve en su "dialecto":
    private String yodaDo(String peticion) {
        // Desordenamos las palabras:
        String[] s = peticion.split(" ");
        String resultado;

        for(int i=0;i<s.length;i++){
            int j=random.nextInt(s.length);
            int k=random.nextInt(s.length);
            String tmp=s[j];

            s[j]=s[k];
            s[k]=tmp;
        }

        resultado=s[0];
        for(int i=1;i<s.length;i++){
            resultado+=" "+s[i];
        }

        return resultado;
    }
}
