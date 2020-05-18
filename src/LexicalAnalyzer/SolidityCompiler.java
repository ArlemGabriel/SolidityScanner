/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalyzer;

import java.util.logging.Level;
import java.util.logging.Logger;
import jflex.exceptions.SilentExit;

/**
 *
 * @author Arlem
 */
public class SolidityCompiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String route = "C:/Users/Arlem/Desktop/Git/SolidityScanner/src/LexicalAnalyzer/Lexer.flex";
        try {
            generateScanner(route);
        } catch (SilentExit ex) {
            Logger.getLogger(SolidityCompiler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void generateScanner(String route) throws SilentExit{
        String[] file = new String[] {route};
        jflex.Main.generate(file);
    }
    
    
}
