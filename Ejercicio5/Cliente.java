import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vicente
 */
public class Cliente extends Thread{
    private int numero, npregunta, acotInf, acotSup, respuesta, preguntaServer, respuestaClient;
    private Random numSecreto, numPregunta;
    boolean win= false, lose=false;
    String buferEnvio;
    String buferRecepcion;
    // Nombre del host donde se ejecuta el servidor:
    String host="localhost";
    // Puerto en el que espera el servidor:
    int port=8989;

    // Socket para la conexión TCP
    Socket socketServicio=null;
//-------------------------------------------
    public Cliente(){
        numSecreto = new Random();
        numero= numSecreto.nextInt(10);
        acotInf=0;
        acotSup=10;
        System.out.println("El cliente piensa en el numero: "+numero+"\n");
    }
    
    public void run(){
        try {
            //--------------Inicialización de socket y flujos-------------------------
            //Inicializamos el socket con la dirección y el puerto
            socketServicio= new Socket(host,port);

            //------------------- while ---------------------
            while(!win && !lose){

                //Número aleatorio a preguntar, acotado por la respuesta del server
                numPregunta = new Random();
                npregunta=numPregunta.nextInt((acotSup-acotInf)+1)+acotInf;
                System.out.println("El cliente pregunta por el numero: "+npregunta);
                
                //Convertimos numero en string y lo enviamos
                buferEnvio= Integer.toString(npregunta);
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                outPrinter.println(buferEnvio);
                outPrinter.flush();

                //Esperamos a recibir la respuesta y la transformamos en int
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                buferRecepcion= inReader.readLine();
                respuesta= Integer.parseInt(buferRecepcion);
                
                //Procesamos la respuesta
                procesarRespuesta(respuesta);
                
                //En el caso de que con nuestra pregunta no ganemos, le toca al servidor
                if(!win && !lose){
                    //Obtenemos la pregunta del servidor y la convertimos a int
                    BufferedReader inReader2 = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                    buferRecepcion= inReader2.readLine();
                    preguntaServer= Integer.parseInt(buferRecepcion);
                    //Procesamos la pregunta y enviamos la respuesta al servidor.
                    procesarPregunta(preguntaServer);
                    buferEnvio= Integer.toString(respuestaClient);
                    PrintWriter outPrinter2 = new PrintWriter(socketServicio.getOutputStream(),true);
                    outPrinter2.println(buferEnvio);
                    outPrinter2.flush();
                }
            }
            //Cerramos el socket
            socketServicio.close();
            // Excepciones:
        } catch (UnknownHostException e) {
                System.err.println("Error: Nombre de host no encontrado.");
        } catch (IOException e) {
                System.err.println("Error de entrada/salida al abrir el socket.");
        }
    }
    //----------------Metodos para controlar las preguntas y respuestas -----------------
    // 0 -> ganar(servidor pierde), 1 -> mayor, 2 -> menor
    private void procesarRespuesta(int respuesta){
        if(respuesta==0){
            win=true;
            System.out.println("El cliente ha ganado!!");
        }
        else if(respuesta==1){
            acotInf=npregunta+1;
            System.out.println("El numero es mayor");
        }
        else if(respuesta==2){
            acotSup=npregunta-1; 
            System.out.println("El numero es menor");
        }
    }
    // 0 -> ganar(cliente pierde), 1 -> menor, 2 -> mayor
    private void procesarPregunta(int pregunta){
        if(pregunta==numero){
            lose=true;
            respuestaClient=0;
            System.out.println("El cliente ha perdido!!");
        }
        else if(pregunta>numero){
            respuestaClient=2;
        }
        else if(pregunta<numero){
            respuestaClient=1;
        }
    }
}
