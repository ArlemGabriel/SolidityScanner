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
public class SemanticErrors {
    private ArrayList<String> semanticErrors;

    public ArrayList<String> getSemanticErrors() {
        return semanticErrors;
    }
    
    public void addSemanticError(String semanticError){
        semanticErrors.add(semanticError);
    }
    
    public String toString(){
        String semanticErrorsStr = "";
        for(String str: semanticErrors){
            semanticErrorsStr = semanticErrorsStr+str;
        }
        return semanticErrorsStr;
    }
}
