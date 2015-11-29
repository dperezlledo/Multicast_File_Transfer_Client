/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.cliente;

import java.util.LinkedList;

public class Contenedor {

    // Atributos.
    public LinkedList<String> datoAConsumir;
    private boolean sw = false;    

    public Contenedor() {
        datoAConsumir = new LinkedList();
    }
    
    // Métodos.
    public synchronized void escribir(String param) {

        try {
            if (sw) {
                wait();
            }
        } catch (InterruptedException ex) {  }        
        datoAConsumir.add(param);        
        sw = true;
        notify();

    }

    public synchronized void borrar() {
        try {
            if (!sw) {
                wait();
            }
        } catch (InterruptedException ex) { }       
        datoAConsumir.removeLast();
        sw = false;                
        notify();

    }

    public LinkedList<String> getDatoAConsumir() {
        return datoAConsumir;
    }

  

}
