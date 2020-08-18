/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler.Registry;

/**
 *
 * @author Fabian
 */
public class SR_Operator extends SemanticRegistry{
    String value;
    String type;

    public SR_Operator(String type, String value, int line) {
        this.value = value;
        this.type = type;
        this.line = line;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
    
    public void setValue(String value) {
       this.value = value;
    }
}
