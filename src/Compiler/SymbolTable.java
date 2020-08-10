/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import java.util.ArrayList;

/**
 *
 * @author Arlem Gabriel
 */
public class SymbolTable {
    private ArrayList<SemanticSymbol> symbolTable;

    public ArrayList<SemanticSymbol> getSymbolTable() {
        return symbolTable;
    }

    public void addSymbol(SemanticSymbol semanticSymbol){
        symbolTable.add(semanticSymbol);
    }    
}
