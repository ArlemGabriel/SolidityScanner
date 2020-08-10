/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import java.util.ArrayList;
import java_cup.runtime.Symbol;

/**
 *
 * @author Arlem Gabriel
 */
class Semantic {
    private static Semantic instance = null;
    private SemanticErrors semanticErrors;
    private SemanticStack semanticStack;
    private SymbolTable symbolTable;
    
    private Semantic(){
        symbolTable = new SymbolTable();
        semanticStack = new SemanticStack();
        semanticErrors = new SemanticErrors();
    }
    
    public static Semantic getInstance(){
        if(instance == null){
            instance = new Semantic();
        }
        return instance;
    }
    
    public void analyzeToken(Symbol tokenValue, String tokenType){
        //type identifier;
        //type identifier;
    }
   
    
    
}
