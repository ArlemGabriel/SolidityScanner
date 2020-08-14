/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import Compiler.Registry.SR_DO;
import Compiler.Registry.SR_Id;
import Compiler.Registry.SR_Operator;
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
    
    
    // -------------------------- Semantic actions --------------------------
    public void rememberId(String name, int line){
        SR_Id registryId= new SR_Id(name,line);
        semanticStack.push(registryId);
    }
    
    public void rememberType(String type, int line){
        SR_Type registryType= new SR_Type(type,line);
        semanticStack.push(registryType);
    }
    
    public void rememberDO(String type, String constantType, String value, int line){
        SR_DO registryDO = new SR_DO(type, constantType, value, line);
        semanticStack.push(registryDO);
    }
    
    public void rememberOperator(String type, String value, int line){
        SR_Operator registryOperator = new SR_Operator(type, value, line);
        semanticStack.push(registryOperator);
    }
    //int a,b,c;
    //int a=5,b=7,c=2;
    //int a=b, c=g;
    //int a=1+5,b=a+5;
    //int a=5,b;
    
    // -------------------------- Validate semantic stack --------------------------
    // ------------------------------ And generate code ----------------------------
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
        boolean endExpression = false;
        ArrayList<SemanticRegistry> tmpExpression = new ArrayList<>();
        while(!endExpression){
            SemanticRegistry lastElement = semanticStack.pop();
            
            
            tmpExpression.add(lastElement);
            
            if(tmpExpression.size()!= 0 && tmpExpression.size()%3 == 0){
                int lastIndex = tmpExpression.size() - 1;
                SR_DO lastDO1 = (SR_DO)tmpExpression.get(lastIndex);
                SR_Operator lastOperator = (SR_Operator)tmpExpression.get(lastIndex-1);
                SR_DO lastDO2 = (SR_DO)tmpExpression.get(lastIndex-2);
                
                if(validateExpression(lastDO1, lastOperator, lastDO2)){
                    //TODO
                }
                else{
                    //TODO clean semantic stack until 'ID ='
                }
                /*
                 String name = idRegistry.getName();
                int line = idRegistry.getLine();
                if(!symbolTable.isRepeated(name, type, actualScope)){
                    SemanticSymbol newSymbol = new VariablesSymbol(line, actualScope, name, type, "0");
                    symbolTable.addSymbol(newSymbol);
                }else{
                    semanticErrors.addSemanticError("ERROR: "+ErrorsEnum.REPEATED_VARIABLE.getDescription()+" '"+name+"' "+"AT LINE: "+line);
                }*/
            }
            
            //TODO validate = to exit while
        }
        
    }
    
    // -------------------------- Auxiliar functions --------------------------
    public boolean validateExpression(SR_DO lastDO1, SR_Operator lastOperator, SR_DO lastDO2){
        if(lastDO1.getConstantType() == null){
            //TODO search variable type on symbol table
            //TODO set constantType
        }
        if(lastDO2.getConstantType() == null){
            //TODO search variable type on symbol table
            //TODO set constantType
        }
        
        if(lastDO1.getConstantType() == lastDO2.getConstantType()){
            if(lastDO1.getType() == lastDO2.getType()){
                //TODO do operation (Constant Folding)
                //Find a way to return result where the function were called xD
            }
        }
        else{
            //TODO error de tipo
            return false;
        }
        
        return true;
    }
    
    //PRINT
    public void print(){
        for(SemanticRegistry sr: semanticStack.getStack()){
            if(sr instanceof SR_Type){
                System.out.println("TYPE: "+((SR_Type) sr).getType()+ "\tLINE: "+((SR_Type) sr).getLine());
            }else if(sr instanceof SR_Id){
                System.out.println("TYPE: "+((SR_Id) sr).getName()+ "\tLINE: "+((SR_Id) sr).getLine());
            }else if(sr instanceof SR_DO){
                System.out.println("TYPE: "+((SR_DO) sr).getType()+"\tVALUE: "+((SR_DO) sr).getValue()+ "\tLINE: "+((SR_DO) sr).getLine()+ "\tCONSTANT: "+((SR_DO) sr).getConstantType()+ "\tLINE: "+((SR_DO) sr).getLine());
            }else if(sr instanceof SR_Operator){
                System.out.println("VALUE: "+((SR_Operator) sr).getValue()+ "\tLINE: "+((SR_Operator) sr).getLine());
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
