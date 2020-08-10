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
public class SemanticStack {
    private ArrayList<SemanticToken> stack;
    
    public void push(SemanticToken semanticToken){
        stack.add(semanticToken);
    }
    
    public SemanticToken pop(){
        int position = stack.size()-1;
        return stack.remove(position);
    }
}
