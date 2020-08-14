package Compiler.Registry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fabian
 */
public class SR_DO extends SemanticRegistry{
    String type;
    String value;
    String constantType;

    public SR_DO(String type, String constantType, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.constantType = constantType;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getConstantType() {
        return constantType;
    }

    public void setConstantType(String constantType) {
        this.constantType = constantType;
    }
    
}