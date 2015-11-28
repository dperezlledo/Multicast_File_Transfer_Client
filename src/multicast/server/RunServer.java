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
        
        try {
            InetAddress address = InetAddress.getByName(INET_ADDR);
            byte[] datos = new byte[1024];
            // Se abre el fichero donde se hará la copia
            fileOutput = new FileOutputStream("/home/dapelle/Escritorio/File1.zip");
            bufferedOutput = new BufferedOutputStream(fileOutput);
            System.out.println("Esperando recibir datos del cliente...");
            try (MulticastSocket clientSocket = new MulticastSocket(PORT)) {
                clientSocket.joinGroup(address);
                do {
                    msgPacket = new DatagramPacket(datos, datos.length);
                    clientSocket.receive(msgPacket);
                    System.out.println("Datos:" + msgPacket.getData().toString());
                    bufferedOutput.write(msgPacket.getData(), 0, msgPacket.getData().length);
                } while (msgPacket.getData().length>0);

            // Cierre de los ficheros
            bufferedOutput.close();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
   
}