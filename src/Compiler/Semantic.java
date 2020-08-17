/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

import Compiler.Registry.SR_DO;
import Compiler.Registry.SR_Else;
import Compiler.Registry.SR_Id;
import Compiler.Registry.SR_If;
import Compiler.Registry.SR_Operator;
import Compiler.Registry.SR_Type;
import Compiler.Registry.SR_While;
import Compiler.Registry.SemanticRegistry;
import Compiler.SemanticSymbol.ElseSymbol;
import Compiler.SemanticSymbol.FunctionSymbol;
import Compiler.SemanticSymbol.IfSymbol;
import Compiler.SemanticSymbol.ReturnFunctionSymbol;
import Compiler.SemanticSymbol.ReturnsFunctionSymbol;
import Compiler.SemanticSymbol.SemanticSymbol;
import Compiler.SemanticSymbol.VariablesSymbol;
import Compiler.SemanticSymbol.WhileSymbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
    private ArrayList<Integer> actualScopeList;
    private int numWhiles;
    private int lastScope;
    private ArrayList<Integer> breakContinueScope;
    private ArrayList<Integer> jmpElseScope;
    static ArrayList<String> intsTypes;
    public static final String ANSI_RED = "\u001B[31m";
    private String assemblerCode;
    private String assemblerDataSegment;
    Map<String, String> possibleJumps;

    public Semantic() {
        symbolTable = new SymbolTable();
        semanticStack = new SemanticStack();
        semanticErrors = new SemanticErrors();
        actualScopeList = new ArrayList<>(Arrays.asList(0));
        intsTypes = new ArrayList<>(Arrays.asList("CONSINTEGER", "int8", "int16", "int32", "int64", "int128", "int256",
                 "uint", "uint8", "uint16", "uint32", "uint64", "uint128", "uint256"));
        assemblerCode = "";
        
        assemblerDataSegment =  "section .data\n"
                                + "segment .bss\n";
        lastScope = 0;
        numWhiles = 0;
        breakContinueScope = new ArrayList<>();
        jmpElseScope = new ArrayList<>();
        possibleJumps = new HashMap<String, String>();
        possibleJumps.put(">", "jg");
        possibleJumps.put("<", "jl");
        possibleJumps.put(">=", "jge");
        possibleJumps.put("<=", "jle");
        possibleJumps.put("==", "je");
        possibleJumps.put("!=", "jne");
    }

    public static Semantic getInstance() {
        if (instance == null) {
            instance = new Semantic();
        }
        return instance;
    }

    public void actualScopeSum() {
        lastScope = lastScope + 1;
        actualScopeList.add(lastScope);
    }

    public void actualScopeSubtract() {
        actualScopeList.remove(actualScopeList.size() - 1);
    }

    public void whilesSubtract() {
        numWhiles--;
        breakContinueScope.remove(breakContinueScope.size()-1);
        assemblerCode += "\texitwhile"+actualScopeList.get(actualScopeList.size()-1)+":\n";
    }
    
    public void createElseExitTag(){
        assemblerCode += "\texitelse"+actualScopeList.get(actualScopeList.size()-1)+":\n";
    }

    // -------------------------- Semantic actions --------------------------
    public void rememberId(String name, int line) {
        SR_Id registryId = new SR_Id(name, line);
        semanticStack.push(registryId);
    }

    public void rememberType(String type, int line) {
        if (intsTypes.contains(type)) {
            type = "int";
        }
        SR_Type registryType = new SR_Type(type, line);
        semanticStack.push(registryType);
    }

    public void rememberDO(String type, String constantType, String value, int line) {
        if (intsTypes.contains(constantType)) {
            constantType = "int";
        }
        SR_DO registryDO = new SR_DO(type, constantType, value, line);
        semanticStack.push(registryDO);
    }

    public void rememberOperator(String type, String value, int line) {
        SR_Operator registryOperator = new SR_Operator(type, value, line);
        semanticStack.push(registryOperator);
    }

    public void rememberWhile(String name, int line) {
        numWhiles++;
        SR_While registryWhile = new SR_While(name, line);
        semanticStack.push(registryWhile);
    }

    public void rememberIf(String name, int line) {
        SR_If registryIf = new SR_If(name, line);
        semanticStack.push(registryIf);
    }

    public void rememberElse(String name, int line) {
        SR_Else registryElse = new SR_Else(name, line);
        semanticStack.push(registryElse);
    }

    //int a,b,c;
    //int a=5,b=7,c=2;
    //int a=b, c=g;
    //int a=1+5,b=a+5;
    //int a=5,b;
    // -------------------------- Validate semantic stack --------------------------
    // ------------------------------ And generate code ----------------------------
    public void insertDeclaration() {
        String type = "";
        for (SemanticRegistry sr : semanticStack.getStack()) {
            if (sr instanceof SR_Type) {
                type = ((SR_Type) sr).getType();
            }
        }
        while (!(semanticStack.getLastElement() instanceof SR_Type)) {
            SR_Id idRegistry = (SR_Id) semanticStack.pop();
            String name = idRegistry.getName();
            int line = idRegistry.getLine();
            int scope = actualScopeList.get(actualScopeList.size() - 1);
            if (!symbolTable.isSymbolOnTable(name, scope)) {
                SemanticSymbol newSymbol = new VariablesSymbol(line, scope, name, type, "0");
                symbolTable.addSymbol(newSymbol);
                assemblerDataSegment += "\t"+name + scope + " resb 1\n";
            } else {
                semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.REPEATED_VARIABLE.getDescription() + " '" + name + "' " + "AT LINE: " + line);
            }
        }
        // Remove type from semantic stack
        semanticStack.pop();
    }
    public void insertFunction(String name, int line){
        SemanticSymbol newSymbol = new FunctionSymbol(line, actualScopeList.get(actualScopeList.size() - 1), name,"FUNCTION");
        symbolTable.addSymbol(newSymbol);
        assemblerCode += "\nglobal " + name + "\n";
    }
    public void insertFunctionReturns() {//TODO generate declaration code
        String type = "";
        for (SemanticRegistry sr : semanticStack.getStack()) {
            if (sr instanceof SR_Type) {
                type = ((SR_Type) sr).getType();
            }
        }
        while (!(semanticStack.getLastElement() instanceof SR_Type)) {
            SR_Id idRegistry = (SR_Id) semanticStack.pop();
            String name = idRegistry.getName();
            int line = idRegistry.getLine();
            //int line, int scope, String name, String type, String value
            SemanticSymbol newSymbol = new ReturnsFunctionSymbol(line, actualScopeList.get(actualScopeList.size() - 1), name, type, "RETURNS");
            symbolTable.addSymbol(newSymbol);
        }
        // Remove type from semantic stack
        semanticStack.pop();
    }

    public void insertFunctionReturn() {//TODO generate declaration code
        String name = "";
        String value = "";
        String type = "";
        SemanticRegistry idRegistry = semanticStack.pop();
        if (idRegistry instanceof SR_Id) {
            type = symbolTable.getVariableType(((SR_Id) idRegistry).getName(), actualScopeList.get(actualScopeList.size() - 1));
            value = ((SR_Id) idRegistry).getName();
        } else if (idRegistry instanceof SR_DO) {
            SR_DO newSR_DO = (SR_DO) idRegistry;
            type = newSR_DO.getConstantType();
            value = newSR_DO.getValue();
        }
        if (type != null) {
            int line = idRegistry.getLine();
            SemanticSymbol newSymbol = new ReturnFunctionSymbol(line, actualScopeList.get(actualScopeList.size() - 1), "RETURN", type, value);
            symbolTable.addSymbol(newSymbol);
        } else {
            semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.VARIABLE_NOT_DEFINED.getDescription() + " AT LINE: " + String.valueOf(idRegistry.getLine()));
        }
        semanticStack.pop();

    }

    public void insertWhile() {
        boolean response = validateBooleanExpression("while");
        int scope = actualScopeList.get(actualScopeList.size() - 1);
        breakContinueScope.add(scope);
        if (response) {
            while (!(semanticStack.getLastElement() instanceof SR_While)) {
                semanticStack.pop();
            }
            SR_While sr = (SR_While) semanticStack.pop();
            String name = sr.getName();
            int line = sr.getLine();
            SemanticSymbol newSymbol = new WhileSymbol(line, scope, name, "0");
            symbolTable.addSymbol(newSymbol);
        } else {
            while (!(semanticStack.getLastElement() instanceof SR_While)) {
                semanticStack.pop();
            }
            SR_While sr = (SR_While) semanticStack.pop();
        }

    }

    public void insertIf() {
        boolean response = validateBooleanExpression("if");
        if (response) {
            while (!(semanticStack.getLastElement() instanceof SR_If)) {
                //TODO Generate code of if
                semanticStack.pop();
            }
            SR_If sr = (SR_If) semanticStack.pop();
            String name = sr.getName();
            int line = sr.getLine();
            int scope = actualScopeList.get(actualScopeList.size() - 1);
            SemanticSymbol newSymbol = new IfSymbol(line, scope, name, "0");
            symbolTable.addSymbol(newSymbol);
            jmpElseScope.add(scope);
        } else {
            while (!(semanticStack.getLastElement() instanceof SR_If)) {
                //TODO Generate code of if
                semanticStack.pop();
            }
            SR_If sr = (SR_If) semanticStack.pop();
        }
    }

    public void insertElse() {
        SR_Else sr = (SR_Else) semanticStack.pop();
        String name = sr.getName();
        int line = sr.getLine();
        int scope = actualScopeList.get(actualScopeList.size() - 1);
        SemanticSymbol newSymbol = new ElseSymbol(line, scope, name);
        symbolTable.addSymbol(newSymbol);
        assemblerCode += "\tjmp exitelse"+scope+"\n"
                    + "\telse"+jmpElseScope.remove(jmpElseScope.size()-1)+":\n";
    }

    public void insertBreakContinue(String name, int line) {
        boolean isInScope = false;
        if (numWhiles > 0/*isInScope == true*/) {
            int scope = actualScopeList.get(actualScopeList.size() - 1);
            SemanticSymbol newSymbol = new SemanticSymbol(line, scope, name);
            symbolTable.addSymbol(newSymbol);
            int scope2 = breakContinueScope.get(breakContinueScope.size()-1);
            if (name.equals("break")) {
                assemblerCode += "\tjmp exitwhile"+scope2+"\n";
            }
            else{
                assemblerCode += "\tjmp while"+scope2+"\n";
            }
        } else {
            if (name.equals("break")) {
                semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.BREAK_ON_INVALID_SCOPE.getDescription() + " AT LINE: " + String.valueOf(line));
            } else {
                semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.CONTINUE_ON_INVALID_SCOPE.getDescription() + " AT LINE: " + String.valueOf(line));
            }

        }

    }

    public void validateFunctionReturn() {
        //First Case: Function with RETURNS but without RETURN, error
        //Second Case: Function without RETURNS but with RETURN, error
        //Third Case: Function without RETURNS and RETURN ,no error
        Boolean isReturns = false;
        Boolean isReturn = false;
        int lineReturns = 0;
        int lineReturn = 0;

        for (SemanticSymbol ss : symbolTable.getSymbolTable()) {
            if (ss instanceof ReturnFunctionSymbol) {
                isReturn = true;
                lineReturn = ss.getLine();
            } else if (ss instanceof ReturnsFunctionSymbol) {
                isReturns = true;
                lineReturns = ss.getLine();
            }
        }
        if (isReturns == true && isReturn == false) {
            semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.MISSING_RETURN.getDescription() + " AT LINE: " + String.valueOf(lineReturns));
        }
        if (isReturns == false && isReturn == true) {
            semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.MISSING_RETURNS.getDescription() + " AT LINE: " + String.valueOf(lineReturn));
        }

    }

    public void insertVariableDefinition() {//TODO PARENTESISSISISIS
        boolean endExpression = false;
        boolean isExpressionValid = true;
        ArrayList<SemanticRegistry> tmpExpression = new ArrayList<>();
        ArrayList<SemanticRegistry> resExpression = new ArrayList<>();
        ArrayList<SemanticRegistry> tmpPrecedenceStack = new ArrayList<>();

        while (!endExpression) {
            SemanticRegistry lastElement = semanticStack.pop();
            if (lastElement instanceof SR_Operator && ((SR_Operator) lastElement).getValue().equals("=")) {
                endExpression = true;
            }

            if (!endExpression) {
                tmpExpression.add(lastElement);
            }
        }

        Collections.reverse(tmpExpression);

        System.out.println("--------------- tmpExpression ---------------");
        System.out.println("--------------- " + String.valueOf(resExpression.size()) + " ---------------");
        int cont = 0;
        for (SemanticRegistry sr : tmpExpression) {
            if (sr instanceof SR_DO) {
                SR_DO aaaaaa = (SR_DO) sr;
                System.out.println("--------------- " + aaaaaa.getValue() + " " + cont + " ---------------");
            } else {
                SR_Operator aaaaaa = (SR_Operator) sr;
                System.out.println("--------------- " + aaaaaa.getValue() + " " + cont + " ---------------");
            }
            cont++;
        }
        boolean lastHasParenthesis = false;
        while (tmpExpression.size() >= 2) {
            int contRes = 0;
            for (SemanticRegistry sr : resExpression) {
                if (sr instanceof SR_DO) {
                    SR_DO aaaaaa = (SR_DO) sr;
                    System.out.println("-----RES" + contRes + "------- " + aaaaaa.getValue() + " ---------------");
                } else {
                    SR_Operator aaaaaa = (SR_Operator) sr;
                    System.out.println("-----RES" + contRes + "------- " + aaaaaa.getValue() + " ---------------");
                }
                contRes++;
            }
            int lastIndex = tmpExpression.size() - 1;
            System.out.println("/////////////// " + String.valueOf(lastIndex) + " ///////////////");
            SemanticRegistry lastDO1;
            SR_Operator lastOperator;
            SemanticRegistry lastDO2;
            boolean isFirstOperator = false;
            boolean validateExpression = true;
            boolean hasParenthesisPrecedence = false;

            SemanticRegistry checkClosingP = tmpExpression.get(lastIndex);
            SR_Operator operator = null;

            if (checkClosingP instanceof SR_Operator) {
                operator = (SR_Operator) checkClosingP;
            }

            if (operator != null && operator.getValue().equals("(") && tmpPrecedenceStack.size() > 0) {
                hasParenthesisPrecedence = true;
                lastHasParenthesis = true;
                tmpExpression.remove(lastIndex);
                int lastIndexPrecedence = tmpPrecedenceStack.size() - 1;

                validateExpression = false;
                lastDO1 = null;
                lastDO2 = null;
                lastOperator = null;
                while (tmpPrecedenceStack.size() > 0) {
                    lastDO1 = (SR_DO) resExpression.remove(0);
                    lastDO2 = (SR_DO) tmpPrecedenceStack.remove(lastIndexPrecedence);
                    lastIndexPrecedence--;
                    lastOperator = (SR_Operator) tmpPrecedenceStack.remove(lastIndexPrecedence);
                    lastIndexPrecedence--;

                    ArrayList<SemanticRegistry> expressionValidated = validateExpression((SR_DO) lastDO1, lastOperator, (SR_DO) lastDO2, hasParenthesisPrecedence);
                    if (expressionValidated != null) {
                        resExpression.addAll(0, expressionValidated);
                    } else {
                        tmpExpression = new ArrayList<>();
                        isExpressionValid = false;
                    }
                }
            } else {
                isFirstOperator = false;
                lastDO1 = tmpExpression.remove(lastIndex);
                while (lastDO1 instanceof SR_Operator) {
                    SR_Operator tmpDO1 = (SR_Operator) lastDO1;

                    System.out.println("++++++++++++ " + tmpDO1.getValue() + " ++++++++++++");
                    lastIndex--;
                    if (tmpDO1.getType().equals("ARITHMETIC")) {
                        isFirstOperator = true;
                        break;
                    } else if (tmpDO1.getValue().equals("(")) {
                        lastHasParenthesis = true;
                    }
                    lastDO1 = tmpExpression.remove(lastIndex);
                }

                if (isFirstOperator) {
                    int lastIndexResExpression = resExpression.size() - 1;
                    System.out.println("@@@@@@@@@@@@@@@@ " + lastIndex + " @@@@@@@@@@@@@@@@");
                    System.out.println("@@@@@@@@@@@@@@@@ " + lastIndexResExpression + " @@@@@@@@@@@@@@@@");

                    lastOperator = (SR_Operator) lastDO1;
                    lastDO1 = tmpExpression.remove(lastIndex);
                    while (lastDO1 instanceof SR_Operator) {
                        lastIndex--;
                        lastDO1 = tmpExpression.remove(lastIndex);
                        validateExpression = false;
                    }

                    if (validateExpression) {
                        if (lastHasParenthesis) {
                            hasParenthesisPrecedence = true;
                            lastHasParenthesis = false;
                        }
                        lastDO1 = (SR_DO) lastDO1;
                        lastDO2 = (SR_DO) resExpression.remove(lastIndexResExpression);
                    } else {
                        tmpExpression.add(lastDO1);
                        resExpression.add(lastOperator);
                        lastDO2 = null;

                        while (resExpression.size() > 0) {
                            tmpPrecedenceStack.add(resExpression.remove(1));
                            tmpPrecedenceStack.add(resExpression.remove(0));
                        }

                        System.out.println("\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        int a = 0;
                        for (SemanticRegistry sr : tmpPrecedenceStack) {
                            if (sr instanceof SR_DO) {
                                SR_DO aaaaaa = (SR_DO) sr;
                                System.out.println("-----PRE" + a + "------- " + aaaaaa.getValue() + " ---------------");
                            } else {
                                SR_Operator aaaaaa = (SR_Operator) sr;
                                System.out.println("-----PRE" + a + "------- " + aaaaaa.getValue() + " ---------------");
                            }
                            a++;
                        }
                        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
                        resExpression.clear();
                    }
                } else {
                    System.out.println("***");
                    lastOperator = (SR_Operator) tmpExpression.remove(lastIndex - 1);

                    lastIndex -= 2;
                    lastDO2 = (SR_DO) lastDO1;
                    lastDO1 = tmpExpression.remove(lastIndex);

                    while (lastDO1 instanceof SR_Operator) {
                        validateExpression = false;
                        SR_Operator tmpDO1 = (SR_Operator) lastDO1;

                        System.out.println("%%%%%%%%%%%% " + tmpDO1.getValue() + " %%%%%%%%%%%%");

                        lastIndex--;
                        lastDO1 = tmpExpression.remove(lastIndex);
                    }
                    if (!validateExpression) {
                        tmpExpression.add(lastDO1);
                        tmpPrecedenceStack.add(lastOperator);
                        tmpPrecedenceStack.add(lastDO2);
                    }
                    lastDO1 = (SR_DO) lastDO1;
                }
            }

            System.out.println("\n");

            if (validateExpression) {
                ArrayList<SemanticRegistry> expressionValidated = validateExpression((SR_DO) lastDO1, lastOperator, (SR_DO) lastDO2, hasParenthesisPrecedence);
                if (expressionValidated != null) {
                    if (!isFirstOperator) {
                        resExpression.addAll(0, expressionValidated);
                    } else {
                        resExpression.addAll(expressionValidated);
                    }
                } else {
                    tmpExpression = new ArrayList<>();
                    isExpressionValid = false;
                }
            }
        }

        if (tmpPrecedenceStack.size() > 0) {
            int lastIndexPrecedence = tmpPrecedenceStack.size() - 1;
            while (tmpPrecedenceStack.size() > 0) {
                SR_DO lastDO1 = (SR_DO) resExpression.remove(0);
                SR_DO lastDO2 = (SR_DO) tmpPrecedenceStack.remove(lastIndexPrecedence);
                lastIndexPrecedence--;
                SR_Operator lastOperator = (SR_Operator) tmpPrecedenceStack.remove(lastIndexPrecedence);
                lastIndexPrecedence--;

                ArrayList<SemanticRegistry> expressionValidated = validateExpression((SR_DO) lastDO1, lastOperator, (SR_DO) lastDO2, true);
                if (expressionValidated != null) {
                    resExpression.addAll(0, expressionValidated);
                } else {
                    isExpressionValid = false;
                }
            }
        }

        SR_DO variable = (SR_DO) semanticStack.pop(); //Varible to assign expression

        if (isExpressionValid) {
            if (resExpression.size() == 0) {
                resExpression = tmpExpression;
            }

            //Try to reduce with constant folding
            boolean expressionReduced = true;
            int lastResult = 0;
            String lastOperator = "+";
            Collections.reverse(resExpression);
            for (SemanticRegistry sr : resExpression) {
                if (sr instanceof SR_DO) {
                    SR_DO tmp = (SR_DO) sr;
                    if (tmp.getType() == "CONSTANT") {
                        if (lastOperator.equals("+")) {
                            lastResult += Integer.parseInt(tmp.getValue());
                        } else {
                            lastResult -= Integer.parseInt(tmp.getValue());
                        }
                    } else {
                        expressionReduced = false;
                        break;
                    }
                } else {
                    lastOperator = ((SR_Operator) sr).getValue();
                }
            }

            if (expressionReduced) {
                SR_DO newExpression = new SR_DO("CONSTANT", "int", String.valueOf(lastResult), resExpression.get(0).getLine());
                resExpression = new ArrayList<SemanticRegistry>(Arrays.asList(newExpression));
            }

            System.out.println("\n\n");
            for (SemanticRegistry sr : resExpression) {
                if (sr instanceof SR_DO) {
                    SR_DO aaaaaa = (SR_DO) sr;
                    System.out.println("--------------- " + aaaaaa.getValue() + " ---------------");
                } else {
                    SR_Operator aaaaaa = (SR_Operator) sr;
                    System.out.println("--------------- " + aaaaaa.getValue() + " ---------------");
                }
            }

            String variableName = variable.getValue();
            variable.setConstantType(symbolTable.getVariableType(variableName, actualScopeList.get(actualScopeList.size() - 1)));
            SR_DO expressionSample = (SR_DO) resExpression.get(0);
            if (symbolTable.getVariableType(variable.getValue(), actualScopeList.get(actualScopeList.size() - 1)) == null) {
                semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.VARIABLE_NOT_DEFINED.getDescription() + " AT LINE: " + String.valueOf(variable.getLine()));
            } else if (variable.getConstantType().equals(expressionSample.getConstantType())) {
                lastOperator = "+";
                assemblerCode += "\tMOV "+" EAX, 0\n";
                for (SemanticRegistry sr : resExpression) {
                    if (sr instanceof SR_DO) {
                        SR_DO tmp = (SR_DO) sr;
                        String tmpScope =  tmp.getType().equals("CONSTANT") ? "" : String.valueOf(symbolTable.getVariableScope(variableName));
                        
                        if (lastOperator.equals("+")) {
                            assemblerCode += "\tADD "+" EAX, "+ tmp.getValue()+ tmpScope+"\n";
                        } else {
                            assemblerCode += "\tSUB "+" EAX, "+ tmp.getValue() + tmpScope+"\n";
                        }
                    } else {
                        lastOperator = ((SR_Operator) sr).getValue();
                    }
                }
                
                int scopeVariable = symbolTable.getVariableScope(variableName);
                assemblerCode += "\tMOV "+ variableName + scopeVariable+", EAX\n";
            } else {
                semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.WRONG_TYPES_ASSIGNATION.getDescription() + " AT LINE: " + String.valueOf(variable.getLine()));
            }
        }
    }
    public void assemblerFileCreation(){
        if(semanticErrors.getSemanticErrors().isEmpty()){
            assemblerCode = assemblerDataSegment + assemblerCode;
            //TODO: Print file
            System.out.print(assemblerCode);
        }
        print();
    }
    // -------------------------- Auxiliar functions --------------------------
    public boolean validateBooleanExpression(String tag) {
        if (semanticStack.getStack().size() == 4) {
            SR_DO lastDO1 = (SR_DO) semanticStack.getElement(3);
            SR_Operator operator = (SR_Operator) semanticStack.getElement(2);
            SR_DO lastDO2 = (SR_DO) semanticStack.getElement(1);
            boolean isFirstDOVariable = false;
            boolean isSecondDOVariable = false;

            if (lastDO1.getConstantType() == null) {
                isFirstDOVariable = true;
                lastDO1.setConstantType(symbolTable.getVariableType(lastDO1.getValue(), actualScopeList.get(actualScopeList.size() - 1)));
            }
            if (lastDO2.getConstantType() == null) {
                isSecondDOVariable = true;
                lastDO2.setConstantType(symbolTable.getVariableType(lastDO2.getValue(), actualScopeList.get(actualScopeList.size() - 1)));
            }

            if (lastDO1.getConstantType() != null && lastDO2.getConstantType() != null) {
                if (operator.getType().equals("BOOLEAN")) {
                    if (lastDO1.getConstantType().equals(lastDO2.getConstantType())) {
                        int scope = actualScopeList.get(actualScopeList.size() - 1);
                        
                        String scope1 = isFirstDOVariable ? String.valueOf(symbolTable.getVariableScope(lastDO1.getValue())) : "";
                        String scope2 = isSecondDOVariable ? String.valueOf(symbolTable.getVariableScope(lastDO2.getValue())) : "";
                        
                        if(tag.equals("if")){
                            assemblerCode += "\tcmp "+lastDO1.getValue()+scope1+", "
                                        + lastDO2.getValue()+scope2 + "\n\t"
                                        + possibleJumps.get(operator.getValue()) +" else"+scope+"\n";
                        }
                        else{
                            assemblerCode += "\n\t"+tag +scope + ":\n"
                                        + "\tcmp "+lastDO1.getValue()+scope1+", "
                                        + lastDO2.getValue()+scope2 + "\n\t"
                                        + possibleJumps.get(operator.getValue()) +" exit"+tag+scope+"\n";
                        }
                        return true;
                    } else {
                        semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.INVALID_OPERATOR.getDescription() + " AT LINE: " + String.valueOf(lastDO1.getLine()));
                        return false;
                    }
                } else {
                    semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.INVALID_OPERATOR.getDescription() + " AT LINE: " + String.valueOf(lastDO1.getLine()));
                    return false;
                }
            } else {
                semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.VARIABLE_NOT_DEFINED.getDescription() + " AT LINE: " + String.valueOf(lastDO1.getLine()));
                return false;
            }
        } else {
            boolean isFirstDOVariable = false;
            if (semanticStack.getStack().size() == 2) {
                SR_DO lastDO1 = (SR_DO) semanticStack.getElement(1);
                if (lastDO1.getConstantType() == null) {
                    isFirstDOVariable = true;
                    lastDO1.setConstantType(symbolTable.getVariableType(lastDO1.getValue(), actualScopeList.get(actualScopeList.size() - 1)));
                }
                if (lastDO1.getConstantType() != null) {
                    if (lastDO1.getConstantType().equals("TRUE") || lastDO1.getConstantType().equals("FALSE") || lastDO1.getConstantType().equals("bool")) {
                        int scope = actualScopeList.get(actualScopeList.size() - 1);
                        
                        String scope1 = isFirstDOVariable ? String.valueOf(symbolTable.getVariableScope(lastDO1.getValue())) : "";
                        String value = lastDO1.getValue();
                        if(!isFirstDOVariable)
                            value = lastDO1.getValue().equals("true") ? "1" : "0";
                        
                        if(tag.equals("if")){
                            assemblerCode += "\n\tcmp "+"0, "
                                     + value+scope1 + "\n"
                                     + "\tje else"+scope+"\n";
                        }
                        else{
                            assemblerCode += "\n\t"+tag +scope + ":\n"
                                        + "\tcmp "+"0, "
                                        + value+scope1 + "\n"
                                        + "\tje exit"+tag+scope+"\n";
                        }
                        return true;
                    } else {
                        semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.INVALID_OPERATOR.getDescription() + " AT LINE: " + String.valueOf(lastDO1.getLine()));
                        return false;
                    }
                } else {
                    semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.VARIABLE_NOT_DEFINED.getDescription() + " AT LINE: " + String.valueOf(lastDO1.getLine()));
                    return false;
                }
            } else {
                semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.ERROR_PROFE.getDescription());
                return false;
            }
        }

    }

    public ArrayList<SemanticRegistry> validateExpression(SR_DO lastDO1, SR_Operator lastOperator, SR_DO lastDO2, boolean hasParenthesisPrecedence) {
        if (lastDO1.getConstantType() == null) {
            lastDO1.setConstantType(symbolTable.getVariableType(lastDO1.getValue(), actualScopeList.get(actualScopeList.size() - 1)));
        }
        if (lastDO2.getConstantType() == null) {
            lastDO2.setConstantType(symbolTable.getVariableType(lastDO2.getValue(), actualScopeList.get(actualScopeList.size() - 1)));
        }

        if (lastDO1.getConstantType() != null && lastDO2.getConstantType() != null) {
            if (lastDO1.getConstantType().equals(lastDO2.getConstantType())) {
                if (lastDO1.getType().equals("CONSTANT") && lastDO1.getType().equals(lastDO2.getType())) {
                    if (lastOperator.getType().equals("ARITHMETIC")) {
                        //Constant folding
                        if (!hasParenthesisPrecedence) {
                            int valueExpression1 = Integer.parseInt(lastDO1.getValue());
                            int valueExpression2 = Integer.parseInt(lastDO2.getValue());
                            int newExpression = 0;

                            if (lastOperator.getValue().equals("+")) {
                                newExpression = valueExpression1 + valueExpression2;
                            } else {
                                newExpression = valueExpression1 - valueExpression2;
                            }

                            SR_DO newExpressionDO = new SR_DO(lastDO1.getType(), lastDO1.getConstantType(), String.valueOf(newExpression), lastDO1.getLine());
                            return new ArrayList<SemanticRegistry>(Arrays.asList(newExpressionDO));
                        }
                    } else {
                        semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.WRONG_OPERATOR.getDescription() + " AT LINE: " + String.valueOf(lastDO1.getLine()));
                        return null;
                    }
                }//Else: return expression as it is (return at the end of this function)
            } else {
                semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.WRONG_TYPES_EXPRESSION.getDescription() + " AT LINE: " + String.valueOf(lastDO1.getLine()));
                return null;
            }
        } else {
            semanticErrors.addSemanticError("ERROR: " + ErrorsEnum.VARIABLE_NOT_DEFINED.getDescription() + " AT LINE: " + String.valueOf(lastDO1.getLine()));
            return null;
        }

        ArrayList<SemanticRegistry> result = new ArrayList<SemanticRegistry>(Arrays.asList(lastDO2, lastOperator, lastDO1));
        return result;
    }

    //PRINT
    public void print() {
        /*for (SemanticRegistry sr : semanticStack.getStack()) {
            if (sr instanceof SR_Type) {
                System.out.println("TYPE: " + ((SR_Type) sr).getType() + "\tLINE: " + ((SR_Type) sr).getLine());
            } else if (sr instanceof SR_Id) {
                System.out.println("TYPE: " + ((SR_Id) sr).getName() + "\tLINE: " + ((SR_Id) sr).getLine());
            } else if (sr instanceof SR_DO) {
                System.out.println("TYPE: " + ((SR_DO) sr).getType() + "\tVALUE: " + ((SR_DO) sr).getValue() + "\tLINE: " + ((SR_DO) sr).getLine() + "\tCONSTANT: " + ((SR_DO) sr).getConstantType() + "\tLINE: " + ((SR_DO) sr).getLine());
            } else if (sr instanceof SR_Operator) {
                System.out.println("VALUE: " + ((SR_Operator) sr).getValue() + "\tLINE: " + ((SR_Operator) sr).getLine());
            } else if (sr instanceof SR_While) {
                System.out.println("VALUE: " + ((SR_While) sr).getName() + "\tLINE: " + ((SR_While) sr).getLine());
            }
        }*/
        System.out.println("\n");
        for (SemanticSymbol ss : symbolTable.getSymbolTable()) {
            if (ss instanceof VariablesSymbol) {
                System.out.println("TYPE: " + ((VariablesSymbol) ss).getType() + "\tNAME: " + ((VariablesSymbol) ss).getName() + "\tVALUE: " + ((VariablesSymbol) ss).getValue() + "\tLINE: " + ((VariablesSymbol) ss).getLine() + "\tSCOPE: " + ((VariablesSymbol) ss).getScope());
            } else if (ss instanceof ReturnsFunctionSymbol) {
                System.out.println("TYPE: " + ((ReturnsFunctionSymbol) ss).getType() + "\tNAME: " + ((ReturnsFunctionSymbol) ss).getName() + "\tVALUE: " + ((ReturnsFunctionSymbol) ss).getValue() + "\tLINE: " + ((ReturnsFunctionSymbol) ss).getLine() + "\tSCOPE: " + ((ReturnsFunctionSymbol) ss).getScope());
            } else if (ss instanceof ReturnFunctionSymbol) {
                System.out.println("TYPE: " + ((ReturnFunctionSymbol) ss).getType() + "\tNAME: " + ((ReturnFunctionSymbol) ss).getName() + "\tVALUE: " + ((ReturnFunctionSymbol) ss).getValue() + "\tLINE: " + ((ReturnFunctionSymbol) ss).getLine() + "\tSCOPE: " + ((ReturnFunctionSymbol) ss).getScope());
            } else if (ss instanceof WhileSymbol) {
                System.out.println("TYPE: " + ((WhileSymbol) ss).getName() + "\tNAME: " + ((WhileSymbol) ss).getName() + "\tVALUE: " + ((WhileSymbol) ss).getValue() + "\tLINE: " + ((WhileSymbol) ss).getLine() + "\tSCOPE: " + ((WhileSymbol) ss).getScope());
            } else if (ss instanceof FunctionSymbol) {
                System.out.println("TYPE: " + ((FunctionSymbol) ss).getType() + "\tNAME: " + ((FunctionSymbol) ss).getName() + "\tVALUE: NONE"+ "\tLINE: " + ((FunctionSymbol) ss).getLine() + "\tSCOPE: " + ((FunctionSymbol) ss).getScope());
            } 
            else {
                System.out.println("TYPE: " + ss.getName() + "\tNAME: " + ss.getName() + "\tVALUE: NONE" + "\tLINE: " + ss.getLine() + "\tSCOPE: " + ss.getScope());
            }

        }
        for (String se : semanticErrors.getSemanticErrors()) {
            System.out.println(ANSI_RED+se);
        }
        //System.out.println("STACK: " + semanticStack.getStack().size());
    }
}
