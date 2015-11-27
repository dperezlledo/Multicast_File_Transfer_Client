/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.cliente;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PROF_VESPERTINO
 */
public class HiloEnvio extends Thread {

    private LinkedList<String> listaComprimidos;

    public HiloEnvio(LinkedList<String> listaComprimidos) {
        this.listaComprimidos = listaComprimidos; // Ficheros compridos
    }

    @Override
    public void run() {
        String next=null;
        int indice = 1;        
        boolean repetir = true;
        Envio e = new Envio();
        
        while (repetir) {
            try {                
                next = listaComprimidos.get(indice);
                e.sendFile(next);        // Enviar
            } catch(IndexOutOfBoundsException error) {
                try {
                    sleep(1000);
                    System.out.print(".");
                } catch (InterruptedException ex) {
                    Logger.getLogger(HiloEnvio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
               
            
        }
        
    }
}
