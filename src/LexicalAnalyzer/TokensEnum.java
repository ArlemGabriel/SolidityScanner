/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LexicalAnalyzer;

/*Objective: This class enum was created to get the matches
of the Lexer.flex and return it*/
public enum TokensEnum {
    //Tokens for comments
    MULTICOMMENT,SINGLECOMMENT,
    //Tokens for reserved words
    INTWITHSIGN,INTWITHOUTSIGN,ID,ADDRESS,AS,BOOL,BREAK,
    CONSTRUCTOR,CONTINUE,CONTRACT,DELETE,DO,
    ELSE,ENUM,FALSE,FOR,FROM,FUNCTION,HEX,IF,IMPORT,INT,
    INTERNAL,MAPPING,MODIFIER,PAYABLE,PRAGMA,PRIVATE,
    PUBLIC,RETURN,RETURNS,SOLIDITY,STRING,STRUCT,THIS,
    TRUE,UFIXED,UINT,VAR,VIEW,WHILE,BYTE,BYTES,
    //Tokens for reserved words of type TRANSAC
    BALANCE,CALL,CALLCODE,DELEGATECALL,SEND,TRANSFER,
    //Tokens for reserved words of type UNITS
    DAYS,ETHER,FINNEY,HOURS,MINUTES,SECONDS,SZABO,WEEKS,WEI,YEARS,
    //Tokens for literals
    CONSINTEGER,CONSREAL,CONSSCIENT,CONSSTRING,CONSINTEGERHEX,
    //Tokens for operators
    //    !           &&         ^              ==           !=        ||           <=                               
    LOGICNEGATION,LOGICAND,ARITMETICEXOR,ARITMETICEQUAL,LOGICDISTINCT,LOGICOR,ARITMETICLESSEQUALTHAN,
    //    <                     >=                        >               &              |  
    ARITMETICLESSTHAN, ARITMETICGREATEREQUALTHAN,ARITMETICGREATERTHAN,ARITMETICAND,ARITMETICOR,
    //    ~            +       -                *        /       %         **            >>        <<        =     ,      ;      .
    ARITMETICNEGATION,SUM,SUBSTRACTION,MULTIPLICATION,DIVISION,MODULE,EXPONENTIATION,SHIFTRIGHT,SHIFTLEFT,EQUAL,COMMA,SEMICOLON,DOT,
    // {         }      (        )     [      ]        ?        : 
    LBRACKET,RBRACKET,LPAREN,RPAREN,LBRACE,RBRACE,QUESTIONSIGN,COLON,
    //+=        -=         *=        /=
    SUMEQUAL,SUBEQUAL,MULTIEQUAL,DIVEQUAL,
    //Tokens para errores
    UNIDENTIFIED_ERROR,INVALID_CHARACTER,ESCAPEERROR,QUOTEERROR,INVALID_IDENTIFIER,CONSSTRINGERROR
}
