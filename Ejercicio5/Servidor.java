
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vicente
 */
public class Servidor extends Thread{
    //----------------Variables-----------------
    private int preguntaClient, preguntaServer, respuestaClient, respuestaServer;
    private int acotInf, acotSup;
    private boolean win=false, lose=false;
    String buferEnvio;
    String buferRecepcion;
    private int numero;
    private Random numSecreto, numPregunta;
    int port=8989;
    ServerSocket serverSocket;
    Socket socketServicio=null;
    
    public Servidor(){
        numSecreto= new Random();
        numero= numSecreto.nextInt(10);
        acotInf=0;
        acotSup=10;
        System.out.println("El servidor piensa en el numero: "+numero+"\n");
    }
    
    public void run(){
        try {
            //Inicializamos el serverSocket con el puerto de escucha.
            serverSocket= new ServerSocket(port);
            //El serverSocket se mantiene en escucha
            socketServicio= serverSocket.accept();
            procesar();
        } catch (IOException e) {
            System.err.println("Error al escuchar en el puerto "+port);
        }
    }
    
    public void procesar(){
        try {
            while(!win && !lose){
                //Esperamos a recibir la pregunta del cliente y la procesamos
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                buferRecepcion= inReader.readLine();
                preguntaClient= Integer.parseInt(buferRecepcion);
                procesarPregunta(preguntaClient);
                
                //Enviamos la respuesta al cliente
                buferEnvio= Integer.toString(respuestaServer);
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                outPrinter.println(buferEnvio);
                outPrinter.flush();
                
                if(!win && !lose){
                    //Número aleatorio a pregunta
                    numPregunta = new Random();
                    preguntaServer= numPregunta.nextInt((acotSup-acotInf)+1)+acotInf;
                    System.out.println("El servidor pregunta por el numero: "+preguntaServer);
                    
                    //Convertimos numero en string y lo enviamos
                    buferEnvio= Integer.toString(preguntaServer);
                    PrintWriter outPrinter2 = new PrintWriter(socketServicio.getOutputStream(),true);
                    outPrinter2.println(buferEnvio);
                    outPrinter2.flush();
                    
                    //Esperamos a recibir la respuesta y la transformamos en int
                    BufferedReader inReader2 = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                    buferRecepcion= inReader2.readLine();
                    respuestaClient= Integer.parseInt(buferRecepcion);
                    //Procesamos la respuesta
                    procesarRespuesta(respuestaClient);
                } 
            }
        } catch (IOException ex) {
            Logger.getLogger("Error en Procesar del Servidor");
        } 
    }
    // 0 -> ganar(servidor pierde), 1 -> menor, 2 -> mayor
    public void procesarPregunta(int pregunta){
        if(pregunta==numero){
            lose=true;
            respuestaServer=0;
            System.out.println("El servidor pierde.");
        }
        else if(pregunta>numero){
            respuestaServer=2;
        }
        else if(pregunta<numero){
            respuestaServer=1;
        }
    }
    // 0 -> ganar(servidor pierde), 1 -> mayor, 2 -> menor
    public void procesarRespuesta(int respuesta){
        if(respuesta==0){
            win=true;
            System.out.println("El servidor ha ganado!!");
        }
        else if(respuesta==1){
            acotInf=preguntaServer+1;  
            System.out.println("El numero es mayor");
        }
        else if(respuesta==2){
            acotSup=preguntaServer-1;
            System.out.println("El numero es menor");
        }
    }
}
