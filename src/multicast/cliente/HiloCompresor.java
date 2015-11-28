/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.cliente;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import multicast.lib.ZipUtils;

/**
 *
 * @author PROF_VESPERTINO
 */
public class HiloCompresor extends Thread {

    private LinkedList<String> listaSelecionados;
    private Contenedor listaComprimidos;

    public HiloCompresor(LinkedList<String> listaSelecionados, Contenedor listaComprimidos) {
        this.listaSelecionados = listaSelecionados; // Ficheros seleccionados
        this.listaComprimidos = listaComprimidos; // Ficheros compridos
    }

    @Override
    public void run() {
        String next, nombreFicheroComprimido;
        int c =1;
        ZipUtils appZip = null;
        
        // Comprime archivos
        try {
            for (Iterator<String> iterator = listaSelecionados.iterator(); iterator.hasNext();) {
                next = iterator.next();                
                nombreFicheroComprimido = "File" + (c++) + ".zip";                
                listaComprimidos.escribir(nombreFicheroComprimido);                
                //System.out.println("Comprimiendo " + next + " en fichero " + nombreFicheroComprimido);                
                appZip = new ZipUtils(nombreFicheroComprimido);
                // Le pasamos los distintos directorios
                appZip.setSource_folder(next);
                appZip.generateFileList(new File(next)); 
                appZip.zipIt(appZip.getOutput_zip_file());
            }
        
            

            
        } catch (Exception ex) {
            Logger.getLogger(HiloCompresor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
