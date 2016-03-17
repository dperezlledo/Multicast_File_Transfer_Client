/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.cliente;

import java.io.*;
import java.util.*;
import java.util.logging.*;
import multicast.cliente.gui.JFrameMain;
import multicast.lib.*;
import org.xeustechnologies.jtar.*;

/**
 *
 * @author PROF_VESPERTINO
 */
public class HiloPrincipal extends Thread {

    private Thread hiloEnvio;
    private LinkedList<String> listaSelecionados;
    private JFrameMain ventana;
    private Log log;
    String tarPath;

    public HiloPrincipal(LinkedList<String> listaSelecionados, JFrameMain ventana) {
        this.listaSelecionados = listaSelecionados;
        log = new Log(ventana.getJTextAreaLog());
    }

    @Override
    public void run() {
        log.añadir("Preparando paquete de envio...");
        empaquetar_directorios();
        log.añadir("Paquete preparado!!!");
        log.añadir("Enviando paquete a los clientes...");
        Thread he = new HiloEnvio(tarPath, log);   
        he.start();
        

    }

    private void empaquetar_directorios() {
        FileOutputStream fOut = null;
        TarOutputStream out = null;
        int i = 0;
        String next;

        try {
            // Output file stream
            tarPath = "PAQUETE.tar";
            fOut = new FileOutputStream(new File(tarPath));
            // Create a TarOutputStream
            out = new TarOutputStream(new BufferedOutputStream(fOut));
            // Files to tar            
            for (Iterator<String> iterator = listaSelecionados.iterator(); iterator.hasNext();) {
                String dirPath = iterator.next();

                System.out.println(new File(".").getAbsolutePath());
                addFileToTar(out, dirPath, "");

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HiloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
                fOut.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void addFileToTar(TarOutputStream tOut, String path, String base) {
        File f = new File(path);
        String entryName = base + f.getName();

        try {
            TarEntry tarEntry = new TarEntry(f, entryName);
            tOut.putNextEntry(tarEntry);

            if (f.isFile()) {
                copiarFicheros(new FileInputStream(f), tOut);                

            } else {                
                File[] children = f.listFiles();
                if (children != null) {
                    for (File child : children) {
                        log.añadir(entryName + " añadido al paquete de envio");
                        addFileToTar(tOut, child.getAbsolutePath(), entryName + "/");
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HiloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HiloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void copiarFicheros(FileInputStream fileInputStream, TarOutputStream tOut) {
        BufferedInputStream origin = null;
        try {
            origin = new BufferedInputStream(fileInputStream);

            int count;
            byte data[] = new byte[2048];
            while ((count = origin.read(data)) != -1) {
                tOut.write(data, 0, count);
            }

            tOut.flush();
            origin.close();
        } catch (IOException ex) {
            Logger.getLogger(HiloPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void enviarPaquete() {
        System.out.println("Enviar paquete no implementado aun");
    }

}
