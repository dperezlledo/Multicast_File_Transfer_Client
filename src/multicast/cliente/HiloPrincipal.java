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
    private LinkedList<String> listaSelecionados;
    private LinkedList<String> listaComprimidos;

    public HiloPrincipal(LinkedList<String> listaSelecionados) {
        this.listaSelecionados = listaSelecionados;
        listaComprimidos = new LinkedList<String>();
    }
       
    
    @Override
    public void run() {
                
        // Arrancamos hilos
        hiloCompresor = new HiloCompresor(listaSelecionados, listaComprimidos);        
        hiloEnvio = new HiloEnvio(listaComprimidos);        
        hiloCompresor.start();
        hiloEnvio.start();
        
        
    }
    
}
