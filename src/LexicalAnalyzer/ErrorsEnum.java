/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalyzer;

/**
 *
 * @author Arlem
 */
public enum ErrorsEnum {
    UNIDENTIFIED_ERROR(0,"Unidentified Lexical Error"),
    INVALID_CHARACTER(1, "Invalid character"),
    ESCAPEERROR(2, "Escape error on string"),
    LQUOTEERROR(3, "Missing left quote"),
    RQUOTEERROR(4,"Missing right quote"),
    INVALID_IDENTIFIER(5,"Invalid identifier");
    
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
