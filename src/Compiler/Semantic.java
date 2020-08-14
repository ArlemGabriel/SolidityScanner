/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import Compiler.Registry.SR_Id;
import Compiler.Registry.SR_Type;
import Compiler.Registry.SemanticRegistry;
import Compiler.SemanticSymbol.SemanticSymbol;
import Compiler.SemanticSymbol.VariablesSymbol;
import java.io.PrintStream;
import java.util.ArrayList;
import java_cup.runtime.Symbol;

/**
 *
 * @author Arlem Gabriel
 */
class Semantic {
    private static Semantic instance = null;
    private SemanticErrors semanticErrors;
    private SemanticStack semanticStack;
    private SymbolTable symbolTable;
    private int actualScope;
    
    public Semantic(){
        symbolTable = new SymbolTable();
        semanticStack = new SemanticStack();
        semanticErrors = new SemanticErrors();
        actualScope =0;
    }
    
    public static Semantic getInstance(){
        if(instance == null){
            instance = new Semantic();
        }
        return instance;
    }
    public void actualScopeSum() {
        actualScope = actualScope+1;
    }
    public void actualScopeSubtract(){
        actualScope = actualScope-1;
    }
    
    public void rememberId(String name, int line){
        SR_Id registryId= new SR_Id(name,line);
        semanticStack.push(registryId);
    }
    
    public void rememberType(String type, int line){
        SR_Type registryType= new SR_Type(type,line);
        semanticStack.push(registryType);
    }
    //int a,b,c;
    //int a=5,b=7,c=2;
    //int a=b, c=g;
    //int a=1+5,b=a+5;
    //int a=5,b;
    
    public void insertDeclaration(){
        String type = "";
        for(SemanticRegistry sr: semanticStack.getStack()){
            if(sr instanceof SR_Type){
                type = ((SR_Type) sr).getType();
            }
        }
        while(!(semanticStack.getLastElement() instanceof SR_Type)){
            SR_Id idRegistry = (SR_Id)semanticStack.pop();
            String name = idRegistry.getName();
            int line = idRegistry.getLine();
            if(!symbolTable.isRepeated(name, type, actualScope)){
                SemanticSymbol newSymbol = new VariablesSymbol(line, actualScope, name, type, "0");
                symbolTable.addSymbol(newSymbol);
            }else{
                semanticErrors.addSemanticError("ERROR: "+ErrorsEnum.REPEATED_VARIABLE.getDescription()+" '"+name+"' "+"AT LINE: "+line);
            }
            
            
        }
        // Remove type from semantic stack
        semanticStack.pop();

    }
    public void insertVariableDefinition(){
        //TODO
        
    }
    public void print(){
        for(SemanticRegistry sr: semanticStack.getStack()){
            if(sr instanceof SR_Type){
                System.out.println("TYPE: "+((SR_Type) sr).getType()+ "\tLINE: "+((SR_Type) sr).getLine());
            }else{
                System.out.println("TYPE: "+((SR_Id) sr).getName()+ "\tLINE: "+((SR_Id) sr).getLine());
            }
        }
        
        for(SemanticSymbol ss: symbolTable.getSymbolTable()){
            System.out.println("TYPE: "+((VariablesSymbol) ss).getType()+"\tID: "+((VariablesSymbol) ss).getName()+ "\tVALUE: "+((VariablesSymbol) ss).getValue()+ "\tLINE: "+((VariablesSymbol) ss).getLine() + "\tSCOPE: "+((VariablesSymbol) ss).getScope());
        }
        for(String se: semanticErrors.getSemanticErrors()){
            System.out.println(se);
        }
        System.out.println("STACK: "+semanticStack.getStack().size());
    }


    
    
   
    
    
}
