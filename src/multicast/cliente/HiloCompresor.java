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
public class HiloCompresor extends Thread {

    private LinkedList<String> listaSelecionados;
    private LinkedList<String> listaComprimidos;

    public HiloCompresor(LinkedList<String> listaSelecionados, LinkedList<String> listaComprimidos) {
        this.listaSelecionados = listaSelecionados; // Ficheros seleccionados
        this.listaComprimidos = listaComprimidos; // Ficheros compridos
    }

    @Override
    public void run() {
        String next;
        int c =1;
        
        // Comprime archivos
        try {
            for (Iterator<String> iterator = listaSelecionados.iterator(); iterator.hasNext();) {
                next = iterator.next();
                System.out.println("Comprimiendo " + next);
                sleep(15000);
                listaComprimidos.add("File" + (c++) + ".zip");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloCompresor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
