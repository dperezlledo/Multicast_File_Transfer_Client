/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.cliente;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import multicast.lib.Log;

/**
 *
 * @author PROF_VESPERTINO
 */
public class HiloEnvio extends Thread {

    private MulticastSocket enviador;
    public static final String INET_ADDR = "224.0.0.3";
    public static final int PORT = 8888;
    private InetAddress addr;
    private DatagramSocket serverSocket;
    private Log log;
    private String tarPath;

    public HiloEnvio(String tarPath, Log log) {
        try {
            this.tarPath = tarPath; // Ficheros compridos
            // Nos creamos la conexion
            addr = InetAddress.getByName(INET_ADDR);
            serverSocket = new DatagramSocket();
            this.log = log;
        } catch (UnknownHostException | SocketException ex) {
            Logger.getLogger(HiloEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        int leidos;
        byte[] datos = new byte[4096];
        DatagramPacket msgPacket;
        byte fin[] = new byte[]{-1};

        try {
            // Se abre el fichero original para lectura            
            FileInputStream fileInput = new FileInputStream(this.tarPath);
            BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);

            leidos = bufferedInput.read(datos);
            while (leidos > 0) {
                msgPacket = new DatagramPacket(datos, datos.length, addr, PORT);
                serverSocket.send(msgPacket);
                leidos = bufferedInput.read(datos);
            }
            //  Aviso de fin de fichero
            msgPacket = new DatagramPacket(fin, 1, addr, PORT);
            serverSocket.send(msgPacket);
            // Cierre de los ficheros
            bufferedInput.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(HiloEnvio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(HiloEnvio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HiloEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
