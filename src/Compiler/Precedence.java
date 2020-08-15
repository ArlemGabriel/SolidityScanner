/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package precedencia;

/**
 *
 * @author Arlem Gabriel
 */
public class Precedence {

    private AritmeticElementsStack operatorStack;
    private AritmeticElementsStack valueStack;
    private boolean error;

    public Precedence() {
        operatorStack = new AritmeticElementsStack();
        valueStack = new AritmeticElementsStack();
        error = false;
    }

    private void processOperator(AritmeticElement t) {
        AritmeticElement A = null;
        AritmeticElement B = null;
        if (valueStack.isEmpty()) {
            System.out.println("Expression error.");
            error = true;
            return;
        } else {
            B = valueStack.top();
            valueStack.pop();
        }
        if (valueStack.isEmpty()) {
            System.out.println("Expression error.");
            error = true;
            return;
        } else {
            A = valueStack.top();
            valueStack.pop();
        }
        AritmeticElement R = t.operate(A.getValue(), B.getValue());
        valueStack.push(R);
    }

    public void processExpression(String[] parts) {
        AritmeticElement[] aritmeticElements = new AritmeticElement[parts.length];
        for (int n = 0; n < parts.length; n++) {
            aritmeticElements[n] = new AritmeticElement(parts[n]);
        }
        // Main loop - process all input tokens
        for (int n = 0; n < aritmeticElements.length; n++) {
            AritmeticElement nextAritmeticElement = aritmeticElements[n];
            if (nextAritmeticElement.getType() == AritmeticElement.NUMBER) {
                valueStack.push(nextAritmeticElement);
            } else if (nextAritmeticElement.getType() == AritmeticElement.OPERATOR) {
                if (operatorStack.isEmpty() || nextAritmeticElement.getPrecedence() > operatorStack.top().getPrecedence()) {
                    operatorStack.push(nextAritmeticElement);
                } else {
                    while (!operatorStack.isEmpty() && nextAritmeticElement.getPrecedence() <= operatorStack.top().getPrecedence()) {
                        AritmeticElement toProcess = operatorStack.top();
                        operatorStack.pop();
                        processOperator(toProcess);
                    }
                    operatorStack.push(nextAritmeticElement);
                }
            } else if (nextAritmeticElement.getType() == AritmeticElement.LEFT_PARENTHESIS) {
                operatorStack.push(nextAritmeticElement);
            } else if (nextAritmeticElement.getType() == AritmeticElement.RIGHT_PARENTHESIS) {
                while (!operatorStack.isEmpty() && operatorStack.top().getType() == AritmeticElement.OPERATOR) {
                    AritmeticElement toProcess = operatorStack.top();
                    operatorStack.pop();
                    processOperator(toProcess);
                }
                if (!operatorStack.isEmpty() && operatorStack.top().getType() == AritmeticElement.LEFT_PARENTHESIS) {
                    operatorStack.pop();
                } else {
                    System.out.println("Error: unbalanced parenthesis.");
                    error = true;
                }
            }

        }
        // Empty out the operator stack at the end of the input
        while (!operatorStack.isEmpty() && operatorStack.top().getType() == AritmeticElement.OPERATOR) {
            AritmeticElement toProcess = operatorStack.top();
            operatorStack.pop();
            processOperator(toProcess);
        }
        // Print the result if no error has been seen.
        if(error == false) {
            AritmeticElement result = valueStack.top();
            valueStack.pop();
            if (!operatorStack.isEmpty() || !valueStack.isEmpty()) {
                System.out.println("Expression error.");
            } else {
                System.out.println("The result is " + result.getValue());
            }
        }
    }
}
