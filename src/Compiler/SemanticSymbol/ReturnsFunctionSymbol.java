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
public class ReturnsFunctionSymbol extends SemanticSymbol{
    String value;
    
    public ReturnsFunctionSymbol(int line, int scope, String name, String type, String value) {
        this.line = line;
        this.scope = scope;
        this.name = name;
        this.type = type;
        this.value = value;
    }
    public String getValue() {
        return value;
    } 
}
