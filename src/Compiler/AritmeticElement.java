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
public class AritmeticElement {
   public static final int UNKNOWN = -1;
   public static final int NUMBER = 0;
   public static final int OPERATOR = 1;
   public static final int LEFT_PARENTHESIS = 2;
   public static final int RIGHT_PARENTHESIS = 3;

   private int type;
   private double value;
   private char operator;
   private int precedence;

   public AritmeticElement() {
       type = UNKNOWN;
   }

   public AritmeticElement(String contents) {
       switch(contents) {
           case "+":
               type = OPERATOR;
               operator = contents.charAt(0);
               precedence = 1;
               break;
           case "-":
               type = OPERATOR;
               operator = contents.charAt(0);
               precedence = 1;
               break;
           case "*":
               type = OPERATOR;
               operator = contents.charAt(0);
               precedence = 2;
               break;
           case "/":
               type = OPERATOR;
               operator = contents.charAt(0);
               precedence = 2;
               break;
           case "(":
               type = LEFT_PARENTHESIS;
               break;
           case ")":
               type = RIGHT_PARENTHESIS;
               break;
           default:
               type = NUMBER;
               try {
                   value = Double.parseDouble(contents);
               } catch (Exception ex) {
                   type = UNKNOWN;
               }
       }
   }

   public AritmeticElement(double x) {
       type = NUMBER;
       value = x;
   }

   int getType() { return type; }
   double getValue() { return value; }
   int getPrecedence() { return precedence; }

   AritmeticElement operate(double a,double b) {
       double result = 0;
       switch(operator) {
           case '+':
               result = a + b;
               break;
           case '-':
               result = a - b;
               break;
           case '*':
               result = a * b;
               break;
           case '/':
               result = a / b;
               break;
       }
       return new AritmeticElement(result);
   }
}
