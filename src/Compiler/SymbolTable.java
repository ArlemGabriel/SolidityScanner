/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import Compiler.SemanticSymbol.SemanticSymbol;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Arlem Gabriel
 */
public class SymbolTable {
    private ArrayList<SemanticSymbol> symbolTable = new ArrayList<>();

    public ArrayList<SemanticSymbol> getSymbolTable() {
        return symbolTable;
    }

    public void addSymbol(SemanticSymbol semanticSymbol){
        symbolTable.add(semanticSymbol);
    }
    public boolean isSymbolOnTable(String name, int scope){

        for(SemanticSymbol semanticSymbol: symbolTable){
            if(semanticSymbol.getName().equals(name) 
                    /*&& semanticSymbol.getType().equals(type)*/
                    && semanticSymbol.getScope()==scope){
                
                return true;
            }
        }
        return false;
    }
    
    public String getVariableType(String name, int scope){
        Collections.reverse(symbolTable);
        for(SemanticSymbol semanticSymbol: symbolTable){
            if(semanticSymbol.getName().equals(name) 
                    /*&& semanticSymbol.getScope()==scope*/){
                Collections.reverse(symbolTable);
                return semanticSymbol.getType();
            }
        }
        Collections.reverse(symbolTable);
        return null;
    }
    
     public int getVariableScope(String name){
        Collections.reverse(symbolTable);
        for(SemanticSymbol semanticSymbol: symbolTable){
            if(semanticSymbol.getName().equals(name)){
                Collections.reverse(symbolTable);
                return semanticSymbol.getScope();
            }
        }
        Collections.reverse(symbolTable);
        return -1;
    }
}
