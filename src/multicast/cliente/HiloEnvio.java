/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.cliente;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PROF_VESPERTINO
 */
public class HiloEnvio extends Thread {

    private Contenedor listaComprimidos;
    private MulticastSocket enviador;
    public static final String INET_ADDR = "224.0.0.3";
    public static final int PORT = 8888;
    private InetAddress addr;
    private DatagramSocket serverSocket;
    
    public HiloEnvio(Contenedor listaComprimidos) {
        try {
            this.listaComprimidos = listaComprimidos; // Ficheros compridos
            // Nos creamos la conexion
            addr = InetAddress.getByName(INET_ADDR);         
            serverSocket = new DatagramSocket();
        } catch (UnknownHostException | SocketException ex) {
            Logger.getLogger(HiloEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String ficheroAEnviar;

        while (true) {
            try {
                ficheroAEnviar = listaComprimidos.datoAConsumir;
                listaComprimidos.borrar();
                System.out.println("Enviando " + ficheroAEnviar);
                enviarFichero(ficheroAEnviar);
            } catch (Exception ex) {
                Logger.getLogger(HiloEnvio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void enviarFichero(String ficheroAEnviar) {
        int leidos;
        byte[] datos = new byte[1024];
        
        try {            
            // Se abre el fichero original para lectura
            FileInputStream fileInput = new FileInputStream(ficheroAEnviar);
            BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
                                   
            leidos = bufferedInput.read(datos);
            while (leidos > 0) {
                DatagramPacket msgPacket = new DatagramPacket(datos,datos.length, addr, PORT);
                serverSocket.send(msgPacket);                
                leidos=bufferedInput.read(datos);
            }
 
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
