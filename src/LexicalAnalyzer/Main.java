/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jflex.exceptions.SilentExit;

/**
 *
 * @author Arlem
 */
public class Main {
    static String scannerroute;
    static FilesReader newtokensfile;
    static BufferedReader tokensbuffer;
    public static void main(String[] args) {
        getScannerRoute();
        generateScanner();
        getTokensFileRoute();
        readTokensFile();
        getTokens();
        /*FilesReader newfile = new FilesReader("C:\\Archivo.txt");
        BufferedReader br;
        String route = "C:/Users/Arlem/Desktop/Git/SolidityScanner/src/LexicalAnalyzer/Lexer.flex";
        try {
            generateScanner(route);
            getTokens(br);
            
        } catch (SilentExit ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    public static void getScannerRoute(){
        scannerroute = "C:/Users/Arlem/Desktop/Git/SolidityScanner/src/LexicalAnalyzer/Lexer.flex";
    }
    public static void generateScanner(){
        String[] file = new String[] {scannerroute};
        try {
            jflex.Main.generate(file);
        } catch (SilentExit ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void getTokensFileRoute(){
        String tokensfileroute = "C:\\Archivo.txt";
        newtokensfile = new FilesReader(tokensfileroute);
    }
    public static void readTokensFile(){
        newtokensfile.readFile();
        tokensbuffer = newtokensfile.getBufferReader();
    }
    
    public static void getTokens(){
        Scanner scanner = new Scanner(tokensbuffer);
         Boolean EOF = false;
         while(EOF == false){
             Tokens token;
            try {
                token = scanner.yylex();
                System.out.print(token);
             if(token == null){
                 EOF = true;
             }else{
                 switch(token){
                     case ERROR:
                         //System.out.print("ERROR ENCONTRADO");
                     case ID:
                        // System.out.print("ID: "+scanner.yytext());
                     default:
                         //System.out.print("DEFAULT");
                 }
             }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
         }
    }
}
