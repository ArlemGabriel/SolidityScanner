/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import Compiler.Registry.SemanticRegistry;
import java.util.ArrayList;

/**
 *
 * @author Arlem Gabriel
 */
public class SemanticStack {
    private ArrayList<SemanticRegistry> stack = new ArrayList<>();
    
    public void push(SemanticRegistry semanticRegistry){
        stack.add(semanticRegistry);
    }
    
    public SemanticRegistry pop(){
        int position = stack.size()-1;
        if(position>-1)
            return stack.remove(position);
        else
            return null;
    }

    public ArrayList<SemanticRegistry> getStack() {
        return stack;
    }
    public SemanticRegistry getLastElement(){
        return stack.get(stack.size()-1);
    }
    public SemanticRegistry getElement(int index){
        return stack.get(index);
    }
}
