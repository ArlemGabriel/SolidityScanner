/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler.Registry;

/**
 *
 * @author Arlem Gabriel
 */
public class SR_Type extends SemanticRegistry{
    String type;

    public SR_Type(String type,int line) {
        this.type = type;
        this.line = line;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
