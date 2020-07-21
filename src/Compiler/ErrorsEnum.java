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
    MULTICOMMENTERROR(6,"The commentary is never closed");
    
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
