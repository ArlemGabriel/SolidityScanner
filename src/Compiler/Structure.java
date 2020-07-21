/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;
import java.util.ArrayList;
/*Objective: This class was created to store objects of
type token thinking on the next stage of making the parser*/
public class Structure {
    private final ArrayList<Token> tokens;
    
    Structure(){
        tokens= new ArrayList<>();
    }
    public void insertToken(Token pToken){
        tokens.add(pToken);
    }
    public ArrayList<Token> getTokensList(){
        return tokens;
    }
    public void printTokens(){        
        for (Token token : tokens) {
            System.out.println("Type: "+token.getType()+" Value: "+token.getValue()+" Line: "+token.getLinenumber()+" Column: "+token.getColumnnumber()+ "\n");
        }
    }
}
