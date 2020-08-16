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
import java.util.Arrays;
import java.util.Collections;
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
    static ArrayList<String> intsTypes = new ArrayList<>(Arrays.asList("CONSINTEGER","int8","int16","int32","int64","int128","int256"
                                       ,"uint","uint8","uint16","uint32","uint64","uint128","uint256"));
    
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
        if(intsTypes.contains(type)){
            type = "int";
        }
        SR_Type registryType= new SR_Type(type,line);
        semanticStack.push(registryType);
    }
    
    public void rememberDO(String type, String constantType, String value, int line){
        if(intsTypes.contains(constantType)){
            constantType = "int";
        }
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
    public void insertDeclaration(){//TODO generate declaration code
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
            if(!symbolTable.isSymbolOnTable(name, actualScope)){
                SemanticSymbol newSymbol = new VariablesSymbol(line, actualScope, name, type, "0");
                symbolTable.addSymbol(newSymbol);
            }else{
                semanticErrors.addSemanticError("ERROR: "+ErrorsEnum.REPEATED_VARIABLE.getDescription()+" '"+name+"' "+"AT LINE: "+line);
            }
        }
        // Remove type from semantic stack
        semanticStack.pop();
    }
    public void insertVariableDefinition(){//TODO PARENTESISSISISIS
        boolean endExpression = false;
        boolean isExpressionValid = true;
        ArrayList<SemanticRegistry> tmpExpression = new ArrayList<>();
        ArrayList<SemanticRegistry> resExpression = new ArrayList<>();
        ArrayList<SemanticRegistry> tmpPrecedenceStack = new ArrayList<>();
        
        while(!endExpression){
            SemanticRegistry lastElement = semanticStack.pop();
            if(lastElement instanceof SR_Operator && ((SR_Operator)lastElement).getValue().equals("=")){
                endExpression = true;
            }
            
            if(!endExpression){
                tmpExpression.add(lastElement);
            }
        }
        
        Collections.reverse(tmpExpression);
        
        System.out.println("--------------- tmpExpression ---------------");
        System.out.println("--------------- "+String.valueOf(resExpression.size())+" ---------------");
        int cont =0;
        for(SemanticRegistry sr: tmpExpression){
            if(sr instanceof SR_DO){
                SR_DO aaaaaa = (SR_DO)sr;
                System.out.println("--------------- "+aaaaaa.getValue()+" "+cont+" ---------------");
            } else 
            {
                SR_Operator aaaaaa = (SR_Operator)sr;
                System.out.println("--------------- "+aaaaaa.getValue()+" "+cont+" ---------------");
            }
            cont++;
        }
        boolean lastHasParenthesis = false;
        while(tmpExpression.size() >= 2){
            int contRes = 0;
            for(SemanticRegistry sr: resExpression){
                if(sr instanceof SR_DO){
                    SR_DO aaaaaa = (SR_DO)sr;
                    System.out.println("-----RES"+contRes+"------- "+aaaaaa.getValue()+" ---------------");
                } else 
                {
                    SR_Operator aaaaaa = (SR_Operator)sr;
                    System.out.println("-----RES"+contRes+"------- "+aaaaaa.getValue()+" ---------------");
                }
                contRes++;
            }
            int lastIndex = tmpExpression.size() - 1;
            System.out.println("/////////////// "+String.valueOf(lastIndex)+" ///////////////");
            SemanticRegistry lastDO1;
            SR_Operator lastOperator;
            SemanticRegistry lastDO2;
            boolean isFirstOperator = false;
            boolean validateExpression = true;
            boolean hasParenthesisPrecedence = false;

            SemanticRegistry checkClosingP = tmpExpression.get(lastIndex);
            SR_Operator operator = null;
            
            if(checkClosingP instanceof SR_Operator)
                operator = (SR_Operator)checkClosingP;
            
            if(operator != null && operator.getValue().equals("(") && tmpPrecedenceStack.size() > 0){
                hasParenthesisPrecedence = true;
                lastHasParenthesis = true;
                tmpExpression.remove(lastIndex);
                int lastIndexPrecedence = tmpPrecedenceStack.size() - 1;                
                
                validateExpression = false;
                lastDO1 = null;
                lastDO2 = null;
                lastOperator = null;
                while(tmpPrecedenceStack.size()>0){
                    lastDO1 = (SR_DO)resExpression.remove(0);
                    lastDO2 = (SR_DO)tmpPrecedenceStack.remove(lastIndexPrecedence);
                    lastIndexPrecedence--;
                    lastOperator = (SR_Operator)tmpPrecedenceStack.remove(lastIndexPrecedence);
                    lastIndexPrecedence--;
                    

                    ArrayList<SemanticRegistry> expressionValidated = validateExpression((SR_DO)lastDO1, lastOperator, (SR_DO)lastDO2, hasParenthesisPrecedence);
                    if(expressionValidated != null){
                        resExpression.addAll(0,expressionValidated);
                    }
                    else{
                        tmpExpression = new ArrayList<>();
                        isExpressionValid = false;
                    }
                }
            }
            else{
                isFirstOperator = false;
                lastDO1 = tmpExpression.remove(lastIndex);
                while(lastDO1 instanceof SR_Operator){
                    SR_Operator tmpDO1 = (SR_Operator)lastDO1;

                    System.out.println("++++++++++++ "+tmpDO1.getValue()+" ++++++++++++");
                    lastIndex--;
                    if(tmpDO1.getType().equals("ARITHMETIC")){
                        isFirstOperator = true;
                        break;
                    }
                    else if(tmpDO1.getValue().equals("(")){
                        lastHasParenthesis=true;
                    }
                    lastDO1 = tmpExpression.remove(lastIndex);
                }

                if(isFirstOperator){
                    int lastIndexResExpression = resExpression.size() - 1;
                    System.out.println("@@@@@@@@@@@@@@@@ "+lastIndex+" @@@@@@@@@@@@@@@@");
                    System.out.println("@@@@@@@@@@@@@@@@ "+lastIndexResExpression+" @@@@@@@@@@@@@@@@");
                    
                    lastOperator = (SR_Operator)lastDO1;
                    lastDO1 = tmpExpression.remove(lastIndex);
                    while(lastDO1 instanceof SR_Operator){
                        lastIndex--;
                        lastDO1 = tmpExpression.remove(lastIndex);
                        validateExpression = false;
                    }
                    
                    if(validateExpression){
                        if(lastHasParenthesis){
                            hasParenthesisPrecedence = true;
                            lastHasParenthesis = false;
                        }
                        lastDO1 = (SR_DO)lastDO1;
                        lastDO2 = (SR_DO)resExpression.remove(lastIndexResExpression);
                    }
                    else{
                        tmpExpression.add(lastDO1);
                        resExpression.add(lastOperator);
                        lastDO2 = null;
                                                
                        while(resExpression.size()>0){
                            tmpPrecedenceStack.add(resExpression.remove(1));
                            tmpPrecedenceStack.add(resExpression.remove(0));
                        }
                        
                        System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        int a = 0;
                        for(SemanticRegistry sr: tmpPrecedenceStack){
                            if(sr instanceof SR_DO){
                                SR_DO aaaaaa = (SR_DO)sr;
                                System.out.println("-----PRE"+a+"------- "+aaaaaa.getValue()+" ---------------");
                            } else 
                            {
                                SR_Operator aaaaaa = (SR_Operator)sr;
                                System.out.println("-----PRE"+a+"------- "+aaaaaa.getValue()+" ---------------");
                            }
                            a++;
                        }
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
                        resExpression.clear();
                    }
                }
                else{
                    System.out.println("***");
                    lastOperator = (SR_Operator)tmpExpression.remove(lastIndex-1);

                    lastIndex -= 2;
                    lastDO2 = (SR_DO)lastDO1;
                    lastDO1 = tmpExpression.remove(lastIndex);
                    
                    while(lastDO1 instanceof SR_Operator){
                        validateExpression = false;
                        SR_Operator tmpDO1 = (SR_Operator)lastDO1;

                        System.out.println("%%%%%%%%%%%% "+tmpDO1.getValue()+" %%%%%%%%%%%%");

                        lastIndex--;
                        lastDO1 = tmpExpression.remove(lastIndex);
                    }
                    if(!validateExpression){
                        tmpExpression.add(lastDO1);
                        tmpPrecedenceStack.add(lastOperator);
                        tmpPrecedenceStack.add(lastDO2);
                    }
                    lastDO1 = (SR_DO)lastDO1;
                }
            }
            
            System.out.println("\n");

            if(validateExpression){
                ArrayList<SemanticRegistry> expressionValidated = validateExpression((SR_DO)lastDO1, lastOperator, (SR_DO)lastDO2, hasParenthesisPrecedence);
                if(expressionValidated != null){
                    if(!isFirstOperator){
                        resExpression.addAll(0,expressionValidated);
                    }
                    else{
                        resExpression.addAll(expressionValidated);
                    }
                }
                else{
                    tmpExpression = new ArrayList<>();
                    isExpressionValid = false;
                }
            }
        }
        
         if(tmpPrecedenceStack.size()>0){
            int lastIndexPrecedence = tmpPrecedenceStack.size()-1;
            while(tmpPrecedenceStack.size()>0){
                SR_DO lastDO1 = (SR_DO)resExpression.remove(0);
                SR_DO lastDO2 = (SR_DO)tmpPrecedenceStack.remove(lastIndexPrecedence);
                lastIndexPrecedence--;
                SR_Operator lastOperator = (SR_Operator)tmpPrecedenceStack.remove(lastIndexPrecedence);
                lastIndexPrecedence--;


                ArrayList<SemanticRegistry> expressionValidated = validateExpression((SR_DO)lastDO1, lastOperator, (SR_DO)lastDO2, true);
                if(expressionValidated != null){
                    resExpression.addAll(0,expressionValidated);
                }
                else{
                    isExpressionValid = false;
                }
            }
        }
        
        SR_DO variable = (SR_DO)semanticStack.pop(); //Varible to assign expression
        
        if(isExpressionValid){
            if(resExpression.size()==0)
                resExpression = tmpExpression;
            
            //Try to reduce with constant folding
            boolean expressionReduced = true;
            int lastResult = 0;
            String lastOperator = "+";
            Collections.reverse(resExpression);
            for(SemanticRegistry sr: resExpression){
                if(sr instanceof SR_DO){
                    SR_DO tmp = (SR_DO)sr;
                    if(tmp.getType()=="CONSTANT"){
                        if(lastOperator.equals("+"))
                            lastResult += Integer.parseInt(tmp.getValue());
                        else
                            lastResult -= Integer.parseInt(tmp.getValue());
                    }
                    else{
                        expressionReduced = false;
                        break;
                    }
                }
                else{
                    lastOperator = ((SR_Operator)sr).getValue();
                }
            }
            
            if(expressionReduced){
                SR_DO newExpression = new SR_DO("CONSTANT","int",String.valueOf(lastResult),resExpression.get(0).getLine());
                resExpression = new ArrayList<SemanticRegistry>(Arrays.asList(newExpression));
            }
            else
                Collections.reverse(resExpression);
            
            System.out.println("\n\n");
            for(SemanticRegistry sr: resExpression){
                if(sr instanceof SR_DO){
                    SR_DO aaaaaa = (SR_DO)sr;
                    System.out.println("--------------- "+aaaaaa.getValue()+" ---------------");
                } else 
                {
                    SR_Operator aaaaaa = (SR_Operator)sr;
                    System.out.println("--------------- "+aaaaaa.getValue()+" ---------------");
                }
            }
            
            variable.setConstantType(symbolTable.getVariableType(variable.getValue(),actualScope));
            SR_DO expressionSample = (SR_DO) resExpression.get(0);
            if(symbolTable.getVariableType(variable.getValue(),actualScope) == null){
                semanticErrors.addSemanticError("ERROR: "+ErrorsEnum.VARIABLE_NOT_DEFINED.getDescription()+ " AT LINE: "+String.valueOf(variable.getLine()));
            }
            else if(variable.getConstantType().equals(expressionSample.getConstantType())){
                //TODO generate assembler code to assign expression to variable
            }
            else{
                semanticErrors.addSemanticError("ERROR: "+ErrorsEnum.WRONG_TYPES_ASSIGNATION.getDescription()+ " AT LINE: "+String.valueOf(variable.getLine()));
            }
        }
    }
    
    // -------------------------- Auxiliar functions --------------------------
    public  ArrayList<SemanticRegistry> validateExpression(SR_DO lastDO1, SR_Operator lastOperator, SR_DO lastDO2, boolean hasParenthesisPrecedence){
        if(lastDO1.getConstantType() == null){
            lastDO1.setConstantType(symbolTable.getVariableType(lastDO1.getValue(),actualScope));
        }
        if(lastDO2.getConstantType() == null){
            lastDO2.setConstantType(symbolTable.getVariableType(lastDO2.getValue(),actualScope));
        }
        
        if(lastDO1.getConstantType() != null && lastDO2.getConstantType() !=null){
            if(lastDO1.getConstantType().equals(lastDO2.getConstantType())){
                if(lastDO1.getType().equals("CONSTANT") && lastDO1.getType().equals(lastDO2.getType())){
                    if(lastOperator.getType().equals("ARITHMETIC")){
                        //Constant folding
                        if(!hasParenthesisPrecedence){
                            int valueExpression1 = Integer.parseInt(lastDO1.getValue()); 
                            int valueExpression2 = Integer.parseInt(lastDO2.getValue()); 
                            int newExpression = 0;

                            if(lastOperator.getValue().equals("+")){
                                newExpression = valueExpression1 + valueExpression2;
                            }
                            else{
                                newExpression = valueExpression1 - valueExpression2;
                            }

                            SR_DO newExpressionDO = new SR_DO(lastDO1.getType(),lastDO1.getConstantType(),String.valueOf(newExpression),lastDO1.getLine());
                            return new ArrayList<SemanticRegistry>(Arrays.asList(newExpressionDO));
                        }
                    }
                    else{
                        semanticErrors.addSemanticError("ERROR: "+ErrorsEnum.WRONG_OPERATOR.getDescription()+ " AT LINE: "+String.valueOf(lastDO1.getLine()));
                        return null;
                    }
                }//Else: return expression as it is (return at the end of this function)
            }
            else{
                semanticErrors.addSemanticError("ERROR: "+ErrorsEnum.WRONG_TYPES_EXPRESSION.getDescription()+ " AT LINE: "+String.valueOf(lastDO1.getLine()));
                return null;
            }
        }
        else{
            semanticErrors.addSemanticError("ERROR: "+ErrorsEnum.VARIABLE_NOT_DEFINED.getDescription()+ " AT LINE: "+String.valueOf(lastDO1.getLine()));
            return null;
        }
        
        ArrayList<SemanticRegistry> result = new ArrayList<SemanticRegistry>(Arrays.asList(lastDO2, lastOperator,lastDO1));
        return result;
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
