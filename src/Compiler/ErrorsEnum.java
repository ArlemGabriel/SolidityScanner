/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compiler;

/*Objective: This class enum was created to differentiate the
errors that the lexical analyzer could throw and to have
a description to print it to the user*/
public enum ErrorsEnum {
    UNIDENTIFIED_ERROR(0,"Unidentified Lexical Error"),
    INVALID_CHARACTER(1, "Invalid character"),
    ESCAPEERROR(2, "Escape error on string"),
    QUOTEERROR(3, "Missing quote"),
    INVALID_IDENTIFIER(4,"Invalid identifier"),
    CONSSTRINGERROR(5,"String doesnt allow multiple lines"),
    MULTICOMMENTERROR(6,"The commentary is never closed"),
    REPEATED_VARIABLE(7,"Variable already defined"),
    WRONG_OPERATOR(8,"Operation not allowed for this type of variables"),
    WRONG_TYPES_ASSIGNATION(9,"Incompatible types on assignation"),
    WRONG_TYPES_EXPRESSION(10,"Incompatible types on expression"),
    VARIABLE_NOT_DEFINED(11,"Variable not defined"),
    MISSING_RETURNS(12,"No returns declaration but this function is trying to return a value"),
    MISSING_RETURN(13,"Missing return statement, function declared with returns"),
    BREAK_ON_INVALID_SCOPE(14,"Break statement on invalid scope"),
    CONTINUE_ON_INVALID_SCOPE(15, "Continue statement on invalid scope"),
    INVALID_OPERATOR(16,"Invalid expression boolean expected"),
    ERROR_PROFE(17,"Ericka Marin Shumman, consideramos solamente operaciones binarias :( Posdata: Puntos extra por mensaje personalizado");
    
  private final int code;
  private final String description;

  private ErrorsEnum(int code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getDescription() {
     return description;
  }

  public int getCode() {
     return code;
  }
  @Override
  public String toString() {
    return code + ": " + description;
  }
}
