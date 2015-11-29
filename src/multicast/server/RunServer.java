/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.server;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dapelle
 */
public class RunServer {

    final static String INET_ADDR = "224.0.0.3";
    final static int PORT = 8888;

    public static void main(String[] args) throws UnknownHostException {

        FileOutputStream fileOutput = null;
        BufferedOutputStream bufferedOutput = null;
        DatagramPacket msgPacket=null;
        int indice=1;
        
        
        try {
            InetAddress address = InetAddress.getByName(INET_ADDR);
            byte[] datos = new byte[1024];
            // Se abre el fichero donde se hará la copia
            fileOutput = new FileOutputStream("/home/dapelle/Escritorio/File" + indice + ".zip");
            bufferedOutput = new BufferedOutputStream(fileOutput);
            System.out.println("Esperando recibir datos del cliente...\n");
            try (MulticastSocket clientSocket = new MulticastSocket(PORT)) {
                clientSocket.joinGroup(address);
                do {
                    msgPacket = new DatagramPacket(datos, datos.length);
                    clientSocket.receive(msgPacket);
                    if (bufferedOutput==null) { // Otro fichero
                        fileOutput = new FileOutputStream("/home/dapelle/Escritorio/File" + (++indice) + ".zip");
                        bufferedOutput = new BufferedOutputStream(fileOutput);
                    }
                    if (msgPacket.getData()[0]==-1) { // Fin del fichero
                        bufferedOutput.close();
                        fileOutput = null; bufferedOutput = null;
                        System.out.println("\nFile" + indice + ".zip Enviado");                        
                    } else {
                        System.out.print(".");
                        bufferedOutput.write(msgPacket.getData(), 0, msgPacket.getData().length);
                    }
                } while (true);       
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
   
}