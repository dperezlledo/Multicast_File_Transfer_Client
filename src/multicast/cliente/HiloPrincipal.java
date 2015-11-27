/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.cliente;

import java.util.LinkedList;

/**
 *
 * @author PROF_VESPERTINO
 */
public class HiloPrincipal extends Thread{
    private Thread hiloCompresor;
    private Thread hiloEnvio;    
    
    @Override
    public void run() {
        LinkedList<String> listaSelecionados = new LinkedList();
        LinkedList<String> listaComprimidos  = new LinkedList();
        
        listaSelecionados.add("C:\\tmp\\dir1");
        listaSelecionados.add("C:\\tmp\\dir2");
        
        // Arrancamos hilos
        hiloCompresor = new HiloCompresor(listaSelecionados, listaComprimidos);        
        hiloEnvio = new HiloEnvio(listaComprimidos);
        hiloEnvio.start();
        hiloCompresor.start();
        
        
    }
    
}
