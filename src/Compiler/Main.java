/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import jflex.exceptions.SilentExit;

/**
 *
 * @author Arlem
 */
public class Main {
    
    static String scannerroute, scannerrouteCup, parserroute;
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

        //Scanner        
        readTokensFile();
        getTokens();
        countTokens();//Scanner
        countTotalTokensOccurrences();//Scanner
        generateTable();//Scanner
        printErrors();
        
        //Parser
        /*getParserRoute();
        generateParser();
        readTokensFile();
        executeParser();*/
    }
    /*Objective: This method was created to assign the route 
    where the scanner is going to be located*/
    public static void getScannerRoute(){
        scannerroute = "../SolidityScanner/src/Compiler/Lexer.flex";
        scannerrouteCup = "../SolidityScanner/src/Compiler/LexerCup.flex";
    }
    public static void getParserRoute(){
        parserroute = "../SolidityScanner/src/Compiler/Parser.cup";
    }
    /*Objective: This method was created to generate the scanner 
    from the entered route in the function getScannerRoute  */
    public static void generateScanner(){
        String[] file = new String[] {scannerroute};
        String[] file2 = new String[] {scannerrouteCup};
        try {
            jflex.Main.generate(file);
            jflex.Main.generate(file2);
        } catch (SilentExit ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void generateParser(){
        String[] file = {"-nowarn","-progress", "-parser","Parser",parserroute};
        try {
            //Generate file Parser.java and sym.java
            java_cup.Main.main(file);
            
            //Check if the generated files already exists
            Path parserRoute = Paths.get("../SolidityScanner/src/Compiler/Parser.java");
            if(Files.exists(parserRoute))
                Files.delete(parserRoute); //If file exist delete it
            Path parserSymRoute = Paths.get("../SolidityScanner/src/Compiler/sym.java");
            if(Files.exists(parserSymRoute))
                Files.delete(parserSymRoute); //If file exist delete it
            
            //Move files generated to the right package
            Files.move(Paths.get("../SolidityScanner/Parser.java"),
                        Paths.get("../SolidityScanner/src/Compiler/Parser.java"));
            Files.move(Paths.get("../SolidityScanner/sym.java"),
                        Paths.get("../SolidityScanner/src/Compiler/sym.java"));
        } catch (SilentExit ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*Objective: This method was created to specify the route
    where the tokens file to scan is located*/
    public static void getTokensFileRoute(){
        String tokensfileroute = "../SolidityScanner/codigo_solidity.txt";
        newtokensfile = new FilesReader(tokensfileroute);
    }
    /*Objective: This method was created to open the tokens
    file and store it in a buffer*/
    public static void readTokensFile(){
        newtokensfile.readFile();
        tokensbuffer = newtokensfile.getBufferReader();
    }
    /*Objective: This method was created to do the lexical
    analysis by reading token by token from the buffer*/
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
                    //Funcionality: If the token readed from buffer is not an error
                    //then create an object of type Token and store it
                    if(!readedtoken.equals(TokensEnum.ESCAPEERROR)&&!readedtoken.equals(TokensEnum.INVALID_CHARACTER)&&
                       !readedtoken.equals(TokensEnum.INVALID_IDENTIFIER)&&
                       !readedtoken.equals(TokensEnum.QUOTEERROR)&&!readedtoken.equals(TokensEnum.UNIDENTIFIED_ERROR)&&
                       !readedtoken.equals(TokensEnum.CONSSTRINGERROR) && !readedtoken.equals(TokensEnum.MULTICOMMENTERROR)){
                        Token token = new Token();
                        token.setType(readedtoken.toString());
                        token.setValue(scanner.lexeme);
                        token.setLinenumber(scanner.line());
                        token.setColumnnumber(scanner.column());
                        structure.insertToken(token);
                    }else{
                    //Funcionality: If the token readed from buffer is an error
                    //then store the type of error in a list
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
                        if(readedtoken.equals(TokensEnum.QUOTEERROR)){
                            errorsfound.add("ERROR: "+ErrorsEnum.QUOTEERROR.getDescription()+" at line "+scanner.line()+ " column "+scanner.column());
                        }
                        if(readedtoken.equals(TokensEnum.CONSSTRINGERROR)){
                            errorsfound.add("ERROR: "+ErrorsEnum.CONSSTRINGERROR.getDescription()+" at line "+scanner.line()+ " column "+scanner.column());
                        }
                        if(readedtoken.equals(TokensEnum.MULTICOMMENTERROR)){
                            errorsfound.add("ERROR: "+ErrorsEnum.MULTICOMMENTERROR.getDescription()+" at line "+scanner.line()+ " column "+scanner.column());
                        }
                    }
                }
            }catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }
    /*Objective: This method compares take the total tokens analyzed
    and passes them one by one to the countTokensOccurrencesByLine function
    to count the number of occurrences*/
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
    /*Objective: This method was created to count the occurrences
    of the tokens by line and saves it in a new structure*/
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
    /*Objective: This method was created to return true if a token
    was already analized and all their occurrences were counted by line */
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
    /*Objective: This method was created to count the total occurrences
    of the tokens with their respective line number and the number of occurrences by line*/
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
    public static void executeParser(){
        Parser parser = new Parser(new ScannerCup(tokensbuffer));
        try {
            parser.parse();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            Symbol sym = parser.getS();
            System.out.println("DEBUG: Sintax Error, Line: " + (sym.right + 1) + ", Column: " + (sym.left + 1) + ", Token: " + sym.value);
        }
    }
    
    /*Objective: This method was created to generate the table of
    the lexical analyzer for all the tokens*/
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
        newtable.printTable(6);
        System.out.println("-----------------------------------------------------------------------------");
    }
    /*Objective: This method was created to print all the errors
    encounted on execution of the lexer*/
    private static void printErrors(){
        int size = errorsfound.size();
        System.out.println(size+" ERROR(S) FOUND \n");
        for(int i=0;i<size;i++){
            System.out.println(errorsfound.get(i));
        }
    }
}
