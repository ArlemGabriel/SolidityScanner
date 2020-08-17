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
public class SR_Else extends SemanticRegistry{
    String name;
    
    public SR_Else(String name, int line) {
        this.name = name;
        this.line = line;
    }

    public String getName() {
        return name;
    }
}
