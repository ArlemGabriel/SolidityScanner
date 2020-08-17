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
public class IfSymbol extends SemanticSymbol{
    String value;
    public IfSymbol(int line, int scope ,String name,String value) {
        this.line = line;
        this.scope = scope;
        this.name = name;
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public int getLine() {
        return line;
    }

    public int getScope() {
        return scope;
    }
}
