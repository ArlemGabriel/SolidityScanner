/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalyzer;
import java.util.ArrayList;
/**
 *
 * @author Arlem
 */
public class Structure {
    private ArrayList<Token> tokens;
    
    Structure(){
        tokens= new ArrayList<>();
    }
    public void insertToken(Token pToken){
        tokens.add(pToken);
    }
}
