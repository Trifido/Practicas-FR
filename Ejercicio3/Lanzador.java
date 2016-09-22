/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vicente
 */
public class Lanzador {
    public static void main(String[] args){
        YodafyServidorIterativo servidor= new YodafyServidorIterativo();
        YodafyClienteTCP cliente1= new YodafyClienteTCP();
        YodafyClienteTCP cliente2= new YodafyClienteTCP();
        YodafyClienteTCP cliente3= new YodafyClienteTCP();
        servidor.start();
        cliente1.start();
        cliente2.start();
        cliente3.start();
    }
}
