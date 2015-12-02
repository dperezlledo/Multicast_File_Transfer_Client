/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multicast.lib;

//Import all needed packages
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private List<String> fileList;
    private String output_zip_file;
    private String source_folder;
    private Log jtxtLog;

    public ZipUtils(String output_zip_file, Log jtxtLog) {
        fileList = new ArrayList();
        this.output_zip_file = output_zip_file;
        this.jtxtLog = jtxtLog;
    }

    public String getOutput_zip_file() {
        return output_zip_file;
    }

    public void setOutput_zip_file(String output_zip_file) {
        this.output_zip_file = output_zip_file;
    }

    public String getSource_folder() {
        return source_folder;
    }

    public void setSource_folder(String source_folder) {
        this.source_folder = source_folder;
    }

    
//    public static void main(String[] args) {
//        ZipUtils appZip = new ZipUtils("Folder.zip", "D:\\Reports");
//        appZip.generateFileList(new File(source_folder));
//        appZip.zipIt(OUTPUT_ZIP_FILE);
//    }

    public void zipIt(String zipFile) {
        byte[] buffer = new byte[1024];
        String source = "";
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            try {
                source = source_folder.substring(source_folder.lastIndexOf("\\") + 1, source_folder.length());
            } catch (Exception e) {
                source = source_folder;
            }
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            //System.out.println("Guardando en fichero Zip : " + zipFile);
            FileInputStream in = null;

            for (String file : this.fileList) {
                //jtxtLog.añadir("Comprimiendo fichero ");
                
                ZipEntry ze = new ZipEntry(source + File.separator + file);                
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(source_folder + File.separator + file);
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }

            zos.closeEntry();
            jtxtLog.añadir(source + " comprimido correctamente");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateFileList(File node) {
       
        // add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));

        } else if (node.isDirectory())  {
            String[] subNote = node.list();
            for (String filename : subNote) {
                generateFileList(new File(node, filename));
            }
        }
    }

    private String generateZipEntry(String file) {
        String aux;
        try {
            aux = file.substring(source_folder.length() + 1, file.length());
            return aux;
        } catch (Exception e) {
            jtxtLog.añadir("Error: " + e.getMessage());
            return null;
        }
        
    }
}
