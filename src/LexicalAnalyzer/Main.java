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
    static ArrayList<TokenOccurrence> totaloccurrences = new ArrayList<>();
    static ArrayList<String> errorsfound = new ArrayList<>();
    
    
    public static void main(String[] args) {
        getScannerRoute();
        generateScanner();
        getTokensFileRoute();
        readTokensFile();
        getTokens();
        countTokens();
        //printOccurrencesObjects();
        countTotalTokensOccurrences();
        generateTable();
        printErrors();
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
                    
                    if(!readedtoken.equals(TokensEnum.ESCAPEERROR)&&!readedtoken.equals(TokensEnum.INVALID_CHARACTER)&&
                       !readedtoken.equals(TokensEnum.INVALID_IDENTIFIER)&& !readedtoken.equals(TokensEnum.LQUOTEERROR)&&
                       !readedtoken.equals(TokensEnum.RQUOTEERROR)&&!readedtoken.equals(TokensEnum.UNIDENTIFIED_ERROR)){
                        Token token = new Token();
                        token.setType(readedtoken.toString());
                        token.setValue(scanner.lexeme);
                        token.setLinenumber(scanner.line());
                        token.setColumnnumber(scanner.column());
                        structure.insertToken(token);
                    }else{
                        if(readedtoken.equals(TokensEnum.INVALID_IDENTIFIER)){
                            errorsfound.add("ERROR: "+ErrorsEnum.INVALID_IDENTIFIER.getDescription()+" at line "+scanner.line()+ " column "+scanner.column());
                        }
                        if(readedtoken.equals(TokensEnum.INVALID_CHARACTER)){
                            errorsfound.add("ERROR: "+ErrorsEnum.INVALID_CHARACTER.getDescription()+" at line "+scanner.line()+ " column "+scanner.column());
                        }
                        if(readedtoken.equals(TokensEnum.UNIDENTIFIED_ERROR)){
                            errorsfound.add("ERROR: "+ErrorsEnum.UNIDENTIFIED_ERROR.getDescription()+" at line "+scanner.line()+ " column "+scanner.column());
                        }
                        if(readedtoken.equals(TokensEnum.ESCAPEERROR)){
                            errorsfound.add("ERROR: "+ErrorsEnum.ESCAPEERROR.getDescription()+" at line "+scanner.line()+ " column "+scanner.column());
                        }
                        if(readedtoken.equals(TokensEnum.LQUOTEERROR)){
                            errorsfound.add("ERROR: "+ErrorsEnum.LQUOTEERROR.getDescription()+" at line "+scanner.line()+ " column "+scanner.column());
                        }
                        if(readedtoken.equals(TokensEnum.RQUOTEERROR)){
                            errorsfound.add("ERROR: "+ErrorsEnum.RQUOTEERROR.getDescription()+" at line "+scanner.line()+ " column "+scanner.column());
                        }
                        
                    }
                    /*switch(readedtoken){
                        
                        case UNIDENTIFIED_ERROR:
                            errorsfound.add(ErrorsEnum.UNIDENTIFIED_ERROR);
                            System.out.print(readedtoken.toString()+" "+scanner.yytext()+"\n");
                        case INVALID_CHARACTER:
                            errorsfound.add(ErrorsEnum.INVALID_CHARACTER);
                            System.out.print(readedtoken.toString()+" "+scanner.yytext()+"\n");
                        case INVALID_IDENTIFIER:
                            errorsfound.add(ErrorsEnum.INVALID_IDENTIFIER);
                            System.out.print(readedtoken.toString()+" "+scanner.yytext()+"\n");
                        case ESCAPEERROR:
                            errorsfound.add(ErrorsEnum.ESCAPEERROR);
                        case LQUOTEERROR:
                            errorsfound.add(ErrorsEnum.LQUOTEERROR);
                        case RQUOTEERROR:
                            errorsfound.add(ErrorsEnum.RQUOTEERROR);
                        case INVALID_IDENTIFIER:
                            errorsfound.add(ErrorsEnum.INVALID_IDENTIFIER);
                        default:
                            Token token = new Token();
                            token.setType(readedtoken.toString());
                            token.setValue(scanner.lexeme);
                            token.setLinenumber(scanner.line());
                            token.setColumnnumber(scanner.column());
                            structure.insertToken(token);
                    }*/
                }
            }catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
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
        TokenOccurrence temptoken;
        
        while(!tokensoccurrences.isEmpty()){
            temptoken = tokensoccurrences.get(0);
            int size = tokensoccurrences.size();
            for(int i=0;i<size;i++){
                
                if(temptoken.getValue().equals(tokensoccurrences.get(i).getValue())){
                    temptoken.insertOccurrence(tokensoccurrences.get(i).getLinenumber(), tokensoccurrences.get(i).getOccurrences());
                    tokenstoremove.add(tokensoccurrences.get(i));
                }
            }
            totaloccurrences.add(temptoken);
            for(TokenOccurrence tokentoremove : tokenstoremove){
                tokensoccurrences.remove(tokentoremove);
            }
            tokenstoremove.clear();
        }
    }
    public static void generateTable(){
        TableGenerator newtable = new TableGenerator();
        ArrayList<String> values = new ArrayList<String>();
        ArrayList<String> types = new ArrayList<String>();
        ArrayList<String> occurrences = new ArrayList<String>();
        System.out.println("\n-----------------------------------------------------------------------------");
        values.add("VALUE");
        types.add("TYPE");
        occurrences.add("OCCURRENCES");
        
        for(TokenOccurrence total: totaloccurrences){
            types.add(total.getType());
            values.add(total.getValue());
            int size = total.getTotalOccurrences().size();
            String stroccurrences = "";
            for(int i =0;i<size;i++){
                stroccurrences += total.getOccurrence(i).get(0)+" ("+total.getOccurrence(i).get(1)+")";
                if(i<size-1){
                    stroccurrences+= ",";
                }
            }
            occurrences.add(stroccurrences);
        }
        newtable.initializeTable(types);
        newtable.initializeTable(values);
        newtable.initializeTable(occurrences);
        newtable.printTable(4);
        System.out.println("-----------------------------------------------------------------------------");
    }
    private static void printErrors(){
        int size = errorsfound.size();
        System.out.println(size+" ERROR(S) FOUND \n");
        for(int i=0;i<size;i++){
            System.out.println(errorsfound.get(i));
        }
    }
    public static void printOccurrencesObjects(){
        for (TokenOccurrence token : tokensoccurrences) {
            System.out.println("Type: "+token.getType()+" Value: "+token.getValue()+" Line: "+token.getLinenumber()+" Occurrences: "+token.getOccurrences()+ "\n");
        }
    }
}
