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
    static Structure structure = new Structure();
    public static void main(String[] args) {
        getScannerRoute();
        generateScanner();
        getTokensFileRoute();
        readTokensFile();
        getTokens();
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
            Tokens readedtoken;
            try {
                readedtoken = scanner.yylex();
                if(readedtoken == null){
                    EOF = true;
                    
                }else{
                    Token token = new Token(readedtoken.toString(), scanner.line(),scanner.column(),scanner.lexeme);
                    structure.insertToken(token);
                }
            }catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        structure.printTokens();
    }
}
