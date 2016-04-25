/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.lib;

import java.io.*;
import javax.swing.JTextArea;

/**
 *
 * @author dapelle
 */
public class Log {
    private JTextArea gui;
    private File fichero;
    private StringBuilder sb;
    
    public Log(JTextArea gui) {
        this.gui = gui;
        sb = new StringBuilder();
        gui.setColumns(100);
    }
    
    public void añadir (String msg) {
        String aux;
        
        aux = gui.getText();
        gui.setText(msg + "\n" + aux);
    }
    
       
}
