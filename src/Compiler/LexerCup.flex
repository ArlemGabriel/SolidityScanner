
//*****************************************
//  IMPORTS AND PACKAGES
//*****************************************
    package Compiler;
    import java_cup.runtime.Symbol;
%%
//*****************************************
//  OPTIONS AND DECLARATIONS
//*****************************************
%class ScannerCup
%type java_cup.runtime.Symbol
%cup
%full
%line
%column
%char
%{
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline, yycolumn, value);
    }
    private Symbol symbol(int type){
        return new Symbol(type, yyline, yycolumn);
    }
    public int line(){
        return yyline;
    }
    public int column(){
        return yycolumn;
    }
%}
    ADDRESS = address
    AS = as
    BOOL = bool
    BREAK = break
    BYTE = byte
    BYTES = (bytes)[1,2,3,4,5,6,7,8]?
    CONSTRUCTOR = constructor
    CONTINUE = continue
    CONTRACT = contract
    DELETE = delete
    DO = do
    ELSE = else
    ENUM = enum
    FALSE = false
    FOR = for
    FROM = from
    FUNCTION = function
    HEX = hex
    IF = if
    IMPORT = import
    INTERNAL = internal
    MAPPING = mapping
    MODIFIER = modifier
    PAYABLE = payable
    PRAGMA = (Pragma | pragma)
    PRIVATE = private
    PUBLIC = public
    RETURN = return
    RETURNS = returns
    SOLIDITY = solidity
    STRING = string
    STRUCT = struct
    THIS = this
    TRUE = true
    UFIXED = ufixed
    VAR = var
    VIEW = view
    WHILE = while
    BALANCE = balance
    CALL = call
    CALLCODE = callcode
    DELEGATECALL = delegatecall
    SEND = send
    TRANSFER = transfer
    DAYS = days
    ETHER = ether
    FINNEY = finney
    HOURS = hours
    MINUTES = minutes
    SECONDS = seconds
    SZABO = szabo
    WEEKS = weeks
    WEI = wei
    YEARS = years

    LOGICNEGATION = \!
    LOGICAND = \&\&
    ARITMETICEXOR = \^
    ARITMETICEQUAL = \=\=
    LOGICDISTINCT = \!\=
    LOGICOR = \|\|
    ARITMETICLESSEQUALTHAN = \<\=
    ARITMETICLESSTHAN = \<
    ARITMETICGREATEREQUALTHAN = \>\=
    ARITMETICGREATERTHAN = \>
    ARITMETICAND = \&
    ARITMETICOR = \|
    ARITMETICNEGATION = \~
    SUM = \+
    SUBSTRACTION = \-
    MULTIPLICATION = \*
    DIVISION = \/
    MODULE = \%
    EXPONENTIATION = \*\*
    SHIFTRIGHT = \>\>
    SHIFTLEFT = \<\<
    EQUAL = \=
    COMMA = \,
    SEMICOLON = \;
    DOT = \.
    LBRACKET = \{
    RBRACKET = \}
    LPAREN = \(
    RPAREN = \)
    LBRACE = \[
    RBRACE = \]
    QUESTIONSIGN = \?
    COLON = \:
    SUMEQUAL = \+\=
    SUBEQUAL = \-\=
    MULTIEQUAL = \*\=
    DIVEQUAL = \/\=
    CONSINTEGERHEX = hex(\"[0-9|A-F|a-f]+\"|\'[0-9|A-F|a-f]+\')
    SINGLECOMMENT = (\/\/[^\n]+)|(\/\/)
    MULTICOMMENT = \/\*\*([^*]|(\*+[^*/]))*\*+\/
    MULTICOMMENTERROR = \/\*\*([^*]|(\*+[^*/]))*
    ID = [a-zA-Z]{1}[a-zA-Z|0-9|_]*
    BLANKSPACE = [ |\t|\r|\n]+
    CONSINTEGER = [0-9]+
    CONSREAL = [0-9]*[.][0-9]+|[0-9]+[.][0-9]*
    CONSSCIENT = (-)[0-9]+(e)[0-9]+|[0-9]+(e)[0-9]+|[0-9]+(e)(-)[0-9]+|(-)[0-9]+(e)(-)[0-9]+|[0-9]+(.)[0-9]+(e)[0-9]+|(-)[0-9]+(.)[0-9]+(e)[0-9]+|[0-9]+(.)[0-9]+(e)(-)[0-9]+|(-)[0-9]+(.)[0-9]+(e)(-)[0-9]+
    CONSSTRING = (\"[^\"\'\\\n]*((\\(n|xNN|uNNNN)[^\'\"\\\n]*)|(\\\\[^\'\"\\\n]*))*\")|(\'[^\"\'\\\n]*((\\(n|xNN|uNNNN)[^\'\"\\\n]*)|(\\\\[^\'\"\\\n]*))*\')
    INVALIDCHAR = ['|`|@|#|"|"|\\|:|_|$|¡|¿|´]{1}|[;]{2}
    INVALIDID = [0-9|_]+[a-zA-Z|_]+[a-zA-Z|_|0-9]*
    ESCAPEERROR = (\"[^\"\'\\\n]*((\\[^n|xNN|uNNNN)][\'\"\\\n]*)|(\\\\[^\'\"\\\n]*))*\")|(\'[^\"\'\\\n]*((\\[^n|xNN|uNNNN)][\'\"\\\n]*)|(\\\\[^\'\"\\\n]*))*\')
    CONSSTRINGERROR = (\"[^\"\'\\]*((\\(n|xNN|uNNNN)[^\'\"\\]*)|(\\\\[^\'\"\\\n]*))*\")
    QUOTEERROR = (\"[^\"\'\\\n]*((\\(n|xNN|uNNNN)[^\'\"\\\n]*)|(\\\\[^\'\"\\\n]*))*)|(\'[^\"\'\\\n]*((\\(n|xNN|uNNNN)[^\'\"\\\n]*)|(\\\\[^\'\"\\\n]*))*)
    INTWITHSIGN = (uint|int)(8|16|32|64|128|256)
    INTWITHOUTSIGN = (uint|int)
%%
//*****************************************
//  LEXICAL RULES
//*****************************************
    {ADDRESS} {return new Symbol (sym.ADDRESS,  yycolumn,yyline, yytext());}
    {AS} {return new Symbol (sym.AS,  yycolumn,yyline, yytext());}
    {BOOL} {return new Symbol (sym.BOOL,  yycolumn,yyline, yytext());}
    {BREAK} {return new Symbol (sym.BREAK,  yycolumn,yyline, yytext());}
    {BYTE} {return new Symbol (sym.BYTE,  yycolumn,yyline, yytext());}
    {BYTES} {return new Symbol (sym.BYTES,  yycolumn,yyline, yytext());}
    {CONSTRUCTOR} {return new Symbol (sym.CONSTRUCTOR,  yycolumn,yyline, yytext());}
    {CONTINUE} {return new Symbol (sym.CONTINUE,  yycolumn,yyline, yytext());}
    {CONTRACT} {return new Symbol (sym.CONTRACT,  yycolumn,yyline, yytext());}
    {DELETE} {return new Symbol (sym.DELETE,  yycolumn,yyline, yytext());}
    {DO} {return new Symbol (sym.DO,  yycolumn,yyline, yytext());}
    {ELSE} {return new Symbol (sym.ELSE,  yycolumn,yyline, yytext());}
    {ENUM} {return new Symbol (sym.ENUM,  yycolumn,yyline, yytext());}
    {FALSE} {return new Symbol (sym.FALSE,  yycolumn,yyline, yytext());}
    {FOR} {return new Symbol (sym.FOR,  yycolumn,yyline, yytext());}
    {FROM} {return new Symbol (sym.FROM,  yycolumn,yyline, yytext());}
    {FUNCTION} {return new Symbol (sym.FUNCTION,  yycolumn,yyline, yytext());}
    
    {CONSINTEGERHEX} {return new Symbol (sym.CONSINTEGERHEX,  yycolumn,yyline, yytext());}

    {HEX} {return new Symbol (sym.HEX,  yycolumn,yyline, yytext());}
    {IF} {return new Symbol (sym.IF,  yycolumn,yyline, yytext());}
    {IMPORT} {return new Symbol (sym.IMPORT,  yycolumn,yyline, yytext());}
    {INTERNAL} {return new Symbol (sym.INTERNAL,  yycolumn,yyline, yytext());}
    {MAPPING} {return new Symbol (sym.MAPPING,  yycolumn,yyline, yytext());}
    {MODIFIER} {return new Symbol (sym.MODIFIER,  yycolumn,yyline, yytext());}
    {PAYABLE} {return new Symbol (sym.PAYABLE,  yycolumn,yyline, yytext());}
    {PRAGMA} {return new Symbol (sym.PRAGMA,  yycolumn,yyline, yytext());}
    {PRIVATE} {return new Symbol (sym.PRIVATE,  yycolumn,yyline, yytext());}
    {PUBLIC} {return new Symbol (sym.PUBLIC,  yycolumn,yyline, yytext());}
    {RETURN} {return new Symbol (sym.RETURN,  yycolumn,yyline, yytext());}
    {RETURNS} {return new Symbol (sym.RETURNS,  yycolumn,yyline, yytext());}
    {SOLIDITY} {return new Symbol (sym.SOLIDITY,  yycolumn,yyline, yytext());}
    {STRING} {return new Symbol (sym.STRING,  yycolumn,yyline, yytext());}
    {STRUCT} {return new Symbol (sym.STRUCT,  yycolumn,yyline, yytext());}
    {THIS} {return new Symbol (sym.THIS,  yycolumn,yyline, yytext());}
    {TRUE} {return new Symbol (sym.TRUE,  yycolumn,yyline, yytext());}
    {UFIXED} {return new Symbol (sym.UFIXED,  yycolumn,yyline, yytext());}
    {VAR} {return new Symbol (sym.VAR,  yycolumn,yyline, yytext());}
    {VIEW} {return new Symbol (sym.VIEW,  yycolumn,yyline, yytext());}
    {WHILE} {return new Symbol (sym.WHILE,  yycolumn,yyline, yytext());}
    {BALANCE} {return new Symbol (sym.BALANCE,  yycolumn,yyline, yytext());}
    {CALL} {return new Symbol (sym.CALL,  yycolumn,yyline, yytext());}
    {CALLCODE} {return new Symbol (sym.CALLCODE,  yycolumn,yyline, yytext());}
    {DELEGATECALL} {return new Symbol (sym.DELEGATECALL,  yycolumn,yyline, yytext());}
    {SEND} {return new Symbol (sym.SEND,  yycolumn,yyline, yytext());}
    {TRANSFER} {return new Symbol (sym.TRANSFER,  yycolumn,yyline, yytext());}
    {DAYS} {return new Symbol (sym.DAYS,  yycolumn,yyline, yytext());}
    {ETHER} {return new Symbol (sym.ETHER,  yycolumn,yyline, yytext());}
    {FINNEY} {return new Symbol (sym.FINNEY,  yycolumn,yyline, yytext());}
    {HOURS} {return new Symbol (sym.HOURS,  yycolumn,yyline, yytext());}
    {MINUTES} {return new Symbol (sym.MINUTES,  yycolumn,yyline, yytext());}
    {SECONDS} {return new Symbol (sym.SECONDS,  yycolumn,yyline, yytext());}
    {SZABO} {return new Symbol (sym.SZABO,  yycolumn,yyline, yytext());}
    {WEEKS} {return new Symbol (sym.WEEKS,  yycolumn,yyline, yytext());}
    {WEI} {return new Symbol (sym.WEI,  yycolumn,yyline, yytext());}
    {YEARS} {return new Symbol (sym.YEARS,  yycolumn,yyline, yytext());}

    {LOGICNEGATION} {return new Symbol (sym.LOGICNEGATION,  yycolumn,yyline, yytext());}
    {LOGICAND} {return new Symbol (sym.LOGICAND,  yycolumn,yyline, yytext());}
    {ARITMETICEXOR} {return new Symbol (sym.ARITMETICEXOR,  yycolumn,yyline, yytext());}
    {ARITMETICEQUAL} {return new Symbol (sym.ARITMETICEQUAL,  yycolumn,yyline, yytext());}
    {LOGICDISTINCT} {return new Symbol (sym.LOGICDISTINCT,  yycolumn,yyline, yytext());}
    {LOGICOR} {return new Symbol (sym.LOGICOR,  yycolumn,yyline, yytext());}
    {ARITMETICLESSEQUALTHAN} {return new Symbol (sym.ARITMETICLESSEQUALTHAN,  yycolumn,yyline, yytext());}
    {ARITMETICLESSTHAN} {return new Symbol (sym.ARITMETICLESSTHAN,  yycolumn,yyline, yytext());}
    {ARITMETICGREATEREQUALTHAN} {return new Symbol (sym.ARITMETICGREATEREQUALTHAN,  yycolumn,yyline, yytext());}
    {ARITMETICGREATERTHAN} {return new Symbol (sym.ARITMETICGREATERTHAN,  yycolumn,yyline, yytext());}
    {ARITMETICAND} {return new Symbol (sym.ARITMETICAND,  yycolumn,yyline, yytext());}
    {ARITMETICOR} {return new Symbol (sym.ARITMETICOR,  yycolumn,yyline, yytext());}
    {ARITMETICNEGATION} {return new Symbol (sym.ARITMETICNEGATION,  yycolumn,yyline, yytext());}
    {SUM} {return new Symbol (sym.SUM,  yycolumn,yyline, yytext());}
    {SUBSTRACTION} {return new Symbol (sym.SUBSTRACTION,  yycolumn,yyline, yytext());}
    {MULTIPLICATION} {return new Symbol (sym.MULTIPLICATION,  yycolumn,yyline, yytext());}
    {SINGLECOMMENT} {/*IGNORE*/}
    {DIVISION} {return new Symbol (sym.DIVISION,  yycolumn,yyline, yytext());}
    {MODULE} {return new Symbol (sym.MODULE,  yycolumn,yyline, yytext());}
    {EXPONENTIATION} {return new Symbol (sym.EXPONENTIATION,  yycolumn,yyline, yytext());}
    {SHIFTRIGHT} {return new Symbol (sym.SHIFTRIGHT,  yycolumn,yyline, yytext());}
    {SHIFTLEFT} {return new Symbol (sym.SHIFTLEFT,  yycolumn,yyline, yytext());}
    {EQUAL} {return new Symbol (sym.EQUAL,  yycolumn,yyline, yytext());}
    {COMMA} {return new Symbol (sym.COMMA,  yycolumn,yyline, yytext());}
    {SEMICOLON} {return new Symbol (sym.SEMICOLON,  yycolumn,yyline, yytext());}
    {DOT} {return new Symbol (sym.DOT,  yycolumn,yyline, yytext());}
    {LBRACKET} {return new Symbol (sym.LBRACKET,  yycolumn,yyline, yytext());}
    {RBRACKET} {return new Symbol (sym.RBRACKET,  yycolumn,yyline, yytext());}
    {LPAREN} {return new Symbol (sym.LPAREN,  yycolumn,yyline, yytext());}
    {RPAREN} {return new Symbol (sym.RPAREN,  yycolumn,yyline, yytext());}
    {LBRACE} {return new Symbol (sym.LBRACE,  yycolumn,yyline, yytext());}
    {RBRACE} {return new Symbol (sym.RBRACE,  yycolumn,yyline, yytext());}
    {QUESTIONSIGN} {return new Symbol (sym.QUESTIONSIGN,  yycolumn,yyline, yytext());}
    {COLON} {return new Symbol (sym.COLON,  yycolumn,yyline, yytext());}
    {SUMEQUAL} {return new Symbol (sym.SUMEQUAL,  yycolumn,yyline, yytext());}
    {SUBEQUAL} {return new Symbol (sym.SUBEQUAL,  yycolumn,yyline, yytext());}
    {MULTIEQUAL} {return new Symbol (sym.MULTIEQUAL,  yycolumn,yyline, yytext());}
    {DIVEQUAL} {return new Symbol (sym.DIVEQUAL,  yycolumn,yyline, yytext());}
    
    {CONSSCIENT} {return new Symbol (sym.CONSSCIENT,  yycolumn,yyline, yytext());}
    {INVALIDID} {/*return INVALID_IDENTIFIER;*/}
    
    {MULTICOMMENT} {/*IGNORE*/}
    {MULTICOMMENTERROR} {return new Symbol (sym.MULTICOMMENTERROR,  yycolumn,yyline, yytext());}
    {INTWITHSIGN} {return new Symbol (sym.INTWITHSIGN,  yycolumn,yyline, yytext());}
    {INTWITHOUTSIGN} {return new Symbol (sym.INTWITHOUTSIGN,  yycolumn,yyline, yytext());}
    {CONSSTRING} {return new Symbol (sym.CONSSTRING,  yycolumn,yyline, yytext());}
    {ESCAPEERROR} {return new Symbol (sym.ESCAPEERROR,  yycolumn,yyline, yytext());}
    {CONSSTRINGERROR} {return new Symbol (sym.CONSSTRINGERROR,  yycolumn,yyline, yytext());}
    {ID} {return new Symbol (sym.ID, yycolumn,yyline, yytext());}
    {CONSINTEGER} {return new Symbol (sym.CONSINTEGER,  yycolumn,yyline, yytext());}
    {CONSREAL} {return new Symbol (sym.CONSREAL,  yycolumn,yyline, yytext());}
    
    {ADDRESS} {return new Symbol (sym.ADDRESS,  yycolumn,yyline, yytext());}
    {QUOTEERROR} {/*return QUOTEERROR;*/}
    {BLANKSPACE} {/*IGNORE*/}
    {INVALIDCHAR} {/*return INVALID_CHARACTER;*/}

    . {/*return UNIDENTIFIED_ERROR;*/}


