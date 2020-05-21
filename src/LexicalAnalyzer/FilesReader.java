/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Arlem
 */
public class FilesReader {
    private File archivo;
    private java.io.FileReader fr;
    private BufferedReader br;
    private final String ruta;
    
    FilesReader(String pRuta){
        archivo = null;
        fr = null;
        br = null;
        ruta = pRuta;
    }
    
    public void readFile(){
      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File (ruta);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);     
      }
      catch(FileNotFoundException e){
          System.out.print("FILE NOT FOUND");
      }
    }
    public BufferedReader getBufferReader(){
        return br;
    }
    public void closeFile(){
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (IOException e2){
             System.out.print("FILE COULD NOT BE CLOSED");
         }
    }
}
