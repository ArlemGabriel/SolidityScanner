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
public class ElseSymbol extends SemanticSymbol{
    public ElseSymbol(int line, int scope ,String name) {
        this.line = line;
        this.scope = scope;
        this.name = name;
    }

    public int getLine() {
        return line;
    }

    public int getScope() {
        return scope;
    }
}
