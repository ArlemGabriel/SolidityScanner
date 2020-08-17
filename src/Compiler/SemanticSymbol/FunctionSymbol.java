/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler.SemanticSymbol;

/**
 *
 * @author Arlem Gabriel
 */
public class FunctionSymbol extends SemanticSymbol{
    
    public FunctionSymbol(int line, int scope, String name, String type) {
        this.line = line;
        this.scope = scope;
        this.name = name;
        this.type = type;
    }

    public int getLine() {
        return line;
    }

    public int getScope() {
        return scope;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
}
