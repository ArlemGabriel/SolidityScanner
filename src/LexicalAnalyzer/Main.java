/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
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
    static ArrayList<TokenOccurrence> tokensoccurrences = new ArrayList<>();
    public static void main(String[] args) {
        getScannerRoute();
        generateScanner();
        getTokensFileRoute();
        readTokensFile();
        getTokens();
        countTokens();
        printOccurrencesObjects();
        countTotalTokensOccurrences();
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
            TokensEnum readedtoken;
            try {
                readedtoken = scanner.yylex();
                if(readedtoken == null){
                    EOF = true;
                    
                }else{
                    Token token = new Token();
                    token.setType(readedtoken.toString());
                    token.setValue(scanner.lexeme);
                    token.setLinenumber(scanner.line());
                    token.setColumnnumber(scanner.column());
                    structure.insertToken(token);
                }
            }catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        //structure.printTokens();
    }
    public static void countTokens(){
        if(!structure.getTokensList().isEmpty()){
            String actualtype;
            String actualvalue;
            int actualline;
            Boolean query;
            for(Token actualtoken:structure.getTokensList()){
                actualtype = actualtoken.getType();
                actualvalue = actualtoken.getValue();
                actualline = actualtoken.getLinenumber();
                if(!tokensoccurrences.isEmpty()){
                    query = tokenExists(actualvalue,actualline);
                    if(query==false){
                        countTokensOccurrencesByLine(actualtype,actualvalue,actualline);
                    }
                    
                }else{
                    countTokensOccurrencesByLine(actualtype,actualvalue,actualline);
                }
            }
        }
    }
    public static void countTokensOccurrencesByLine(String type,String actualvalue,int actualline){
        int occurrence =0;
        for(Token tokentovisit : structure.getTokensList()){
            if(tokentovisit.getValue().equals(actualvalue) && tokentovisit.getLinenumber()==actualline){
                occurrence = occurrence+1;
            }
        }
        TokenOccurrence newtoken = new TokenOccurrence();
        newtoken.setType(type);
        newtoken.setValue(actualvalue);
        newtoken.setLinenumber(actualline);
        newtoken.setOccurrences(occurrence);
        tokensoccurrences.add(newtoken);
    }
    public static Boolean tokenExists(String actualvalue, int actualline){
        Boolean query = false;
        for(Token token : tokensoccurrences){
            if(token.getValue().equals(actualvalue) && token.getLinenumber()==actualline){
                query = true;
                break;
            }
        }
        return query;
    }
    public static void countTotalTokensOccurrences(){
        ArrayList<TokenOccurrence> tokenstoremove = new ArrayList<>();
        ArrayList<TokenOccurrence> totaloccurrences = new ArrayList<>();
        TokenOccurrence temptoken;
        
        while(!tokensoccurrences.isEmpty()){
            temptoken = tokensoccurrences.get(0);
            int size = tokensoccurrences.size();
            for(int i=0;i<size;i++){
                
                if(temptoken.getValue().equals(tokensoccurrences.get(i).getValue())){
                    System.out.print("\nVALUE: "+tokensoccurrences.get(i).getValue()+"\n");
                    System.out.print("LINENUMBER: "+tokensoccurrences.get(i).getLinenumber()+"\n");
                    System.out.print("OCCURRENCES: "+tokensoccurrences.get(i).getOccurrences()+"\n");
                    System.out.print("-------------------------------------------------------------");
                    temptoken.insertOccurrence(tokensoccurrences.get(i).getLinenumber(), tokensoccurrences.get(i).getOccurrences());
                    tokenstoremove.add(tokensoccurrences.get(i));
                }
            }
            totaloccurrences.add(temptoken);
            System.out.print("\nSALI DEL FOR\n");
            for(TokenOccurrence tokentoremove : tokenstoremove){
                tokensoccurrences.remove(tokentoremove);
            }
            tokenstoremove.clear();
        }
        System.out.print("TOTAL NUMBER OF TOKENS: "+totaloccurrences.size()+"\n");
        for(TokenOccurrence total: totaloccurrences){
            System.out.print("VALOR: "+total.getValue()+"\n");
            int size = total.getTotalOccurrences().size();
            for(int i =0;i<size;i++){
                System.out.print(total.getOccurrence(i));
            }
            
        }
        //System.out.print(totaloccurrences.get(0).getTotalOccurrences().size());
        //System.out.print(totaloccurrences.get(0).getTotalOccurrences().size());
    }
    public static void printOccurrencesObjects(){
        for (TokenOccurrence token : tokensoccurrences) {
            System.out.println("Type: "+token.getType()+" Value: "+token.getValue()+" Line: "+token.getLinenumber()+" Occurrences: "+token.getOccurrences()+ "\n");
        }
    }
}
