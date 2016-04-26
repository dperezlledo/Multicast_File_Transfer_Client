/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.cliente;

import java.io.*;
import multicast.lib.Log;

/**
 *
 * @author PROF_VESPERTINO
 */
public class StreamsComando extends Thread {

    private InputStream is;
    private String type;
    private Log log;

    public StreamsComando(InputStream is, String type, Log log) {
        this.is = is;
        this.type = type;
        this.log = log;
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        try {
            
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) //System.out.println(type + ">" + line);    
            {
                System.out.println(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
