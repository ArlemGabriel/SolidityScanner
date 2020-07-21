
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
    BYTES = (bytes)[1,2,3,4,5,6,7,8]
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
    BLANKSPACE = [ ,\t,\r,\n]+
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
    {ADDRESS} {return new Symbol (sym.ADDRESS,  (int) yychar,yyline, yytext());}
    {AS} {return new Symbol (sym.AS,  (int) yychar,yyline, yytext());}
    {BOOL} {return new Symbol (sym.BOOL,  (int) yychar,yyline, yytext());}
    {BREAK} {return new Symbol (sym.BREAK,  (int) yychar,yyline, yytext());}
    {BYTE} {return new Symbol (sym.BYTE,  (int) yychar,yyline, yytext());}
    {BYTES} {return new Symbol (sym.BYTES,  (int) yychar,yyline, yytext());}
    {CONSTRUCTOR} {return new Symbol (sym.CONSTRUCTOR,  (int) yychar,yyline, yytext());}
    {CONTINUE} {return new Symbol (sym.CONTINUE,  (int) yychar,yyline, yytext());}
    {CONTRACT} {return new Symbol (sym.CONTRACT,  (int) yychar,yyline, yytext());}
    {DELETE} {return new Symbol (sym.DELETE,  (int) yychar,yyline, yytext());}
    {DO} {return new Symbol (sym.DO,  (int) yychar,yyline, yytext());}
    {ELSE} {return new Symbol (sym.ELSE,  (int) yychar,yyline, yytext());}
    {ENUM} {return new Symbol (sym.ENUM,  (int) yychar,yyline, yytext());}
    {FALSE} {return new Symbol (sym.FALSE,  (int) yychar,yyline, yytext());}
    {FOR} {return new Symbol (sym.FOR,  (int) yychar,yyline, yytext());}
    {FROM} {return new Symbol (sym.FROM,  (int) yychar,yyline, yytext());}
    {FUNCTION} {return new Symbol (sym.FUNCTION,  (int) yychar,yyline, yytext());}
    
    {CONSINTEGERHEX} {return new Symbol (sym.CONSINTEGERHEX,  (int) yychar,yyline, yytext());}

    {HEX} {return new Symbol (sym.HEX,  (int) yychar,yyline, yytext());}
    {IF} {return new Symbol (sym.IF,  (int) yychar,yyline, yytext());}
    {IMPORT} {return new Symbol (sym.IMPORT,  (int) yychar,yyline, yytext());}
    {INTERNAL} {return new Symbol (sym.INTERNAL,  (int) yychar,yyline, yytext());}
    {MAPPING} {return new Symbol (sym.MAPPING,  (int) yychar,yyline, yytext());}
    {MODIFIER} {return new Symbol (sym.MODIFIER,  (int) yychar,yyline, yytext());}
    {PAYABLE} {return new Symbol (sym.PAYABLE,  (int) yychar,yyline, yytext());}
    {PRAGMA} {return new Symbol (sym.PRAGMA,  (int) yychar,yyline, yytext());}
    {PRIVATE} {return new Symbol (sym.PRIVATE,  (int) yychar,yyline, yytext());}
    {PUBLIC} {return new Symbol (sym.PUBLIC,  (int) yychar,yyline, yytext());}
    {RETURN} {return new Symbol (sym.RETURN,  (int) yychar,yyline, yytext());}
    {RETURNS} {return new Symbol (sym.RETURNS,  (int) yychar,yyline, yytext());}
    {SOLIDITY} {return new Symbol (sym.SOLIDITY,  (int) yychar,yyline, yytext());}
    {STRING} {return new Symbol (sym.STRING,  (int) yychar,yyline, yytext());}
    {STRUCT} {return new Symbol (sym.STRUCT,  (int) yychar,yyline, yytext());}
    {THIS} {return new Symbol (sym.THIS,  (int) yychar,yyline, yytext());}
    {TRUE} {return new Symbol (sym.TRUE,  (int) yychar,yyline, yytext());}
    {UFIXED} {return new Symbol (sym.UFIXED,  (int) yychar,yyline, yytext());}
    {VAR} {return new Symbol (sym.VAR,  (int) yychar,yyline, yytext());}
    {VIEW} {return new Symbol (sym.VIEW,  (int) yychar,yyline, yytext());}
    {WHILE} {return new Symbol (sym.WHILE,  (int) yychar,yyline, yytext());}
    {BALANCE} {return new Symbol (sym.BALANCE,  (int) yychar,yyline, yytext());}
    {CALL} {return new Symbol (sym.CALL,  (int) yychar,yyline, yytext());}
    {CALLCODE} {return new Symbol (sym.CALLCODE,  (int) yychar,yyline, yytext());}
    {DELEGATECALL} {return new Symbol (sym.DELEGATECALL,  (int) yychar,yyline, yytext());}
    {SEND} {return new Symbol (sym.SEND,  (int) yychar,yyline, yytext());}
    {TRANSFER} {return new Symbol (sym.TRANSFER,  (int) yychar,yyline, yytext());}
    {DAYS} {return new Symbol (sym.DAYS,  (int) yychar,yyline, yytext());}
    {ETHER} {return new Symbol (sym.ETHER,  (int) yychar,yyline, yytext());}
    {FINNEY} {return new Symbol (sym.FINNEY,  (int) yychar,yyline, yytext());}
    {HOURS} {return new Symbol (sym.HOURS,  (int) yychar,yyline, yytext());}
    {MINUTES} {return new Symbol (sym.MINUTES,  (int) yychar,yyline, yytext());}
    {SECONDS} {return new Symbol (sym.SECONDS,  (int) yychar,yyline, yytext());}
    {SZABO} {return new Symbol (sym.SZABO,  (int) yychar,yyline, yytext());}
    {WEEKS} {return new Symbol (sym.WEEKS,  (int) yychar,yyline, yytext());}
    {WEI} {return new Symbol (sym.WEI,  (int) yychar,yyline, yytext());}
    {YEARS} {return new Symbol (sym.YEARS,  (int) yychar,yyline, yytext());}

    {LOGICNEGATION} {return new Symbol (sym.LOGICNEGATION,  (int) yychar,yyline, yytext());}
    {LOGICAND} {return new Symbol (sym.LOGICAND,  (int) yychar,yyline, yytext());}
    {ARITMETICEXOR} {return new Symbol (sym.ARITMETICEXOR,  (int) yychar,yyline, yytext());}
    {ARITMETICEQUAL} {return new Symbol (sym.ARITMETICEQUAL,  (int) yychar,yyline, yytext());}
    {LOGICDISTINCT} {return new Symbol (sym.LOGICDISTINCT,  (int) yychar,yyline, yytext());}
    {LOGICOR} {return new Symbol (sym.LOGICOR,  (int) yychar,yyline, yytext());}
    {ARITMETICLESSEQUALTHAN} {return new Symbol (sym.ARITMETICLESSEQUALTHAN,  (int) yychar,yyline, yytext());}
    {ARITMETICLESSTHAN} {return new Symbol (sym.ARITMETICLESSTHAN,  (int) yychar,yyline, yytext());}
    {ARITMETICGREATEREQUALTHAN} {return new Symbol (sym.ARITMETICGREATEREQUALTHAN,  (int) yychar,yyline, yytext());}
    {ARITMETICGREATERTHAN} {return new Symbol (sym.ARITMETICGREATERTHAN,  (int) yychar,yyline, yytext());}
    {ARITMETICAND} {return new Symbol (sym.ARITMETICAND,  (int) yychar,yyline, yytext());}
    {ARITMETICOR} {return new Symbol (sym.ARITMETICOR,  (int) yychar,yyline, yytext());}
    {ARITMETICNEGATION} {return new Symbol (sym.ARITMETICNEGATION,  (int) yychar,yyline, yytext());}
    {SUM} {return new Symbol (sym.SUM,  (int) yychar,yyline, yytext());}
    {SUBSTRACTION} {return new Symbol (sym.SUBSTRACTION,  (int) yychar,yyline, yytext());}
    {MULTIPLICATION} {return new Symbol (sym.MULTIPLICATION,  (int) yychar,yyline, yytext());}
    {SINGLECOMMENT} {/*IGNORE*/}
    {DIVISION} {return new Symbol (sym.DIVISION,  (int) yychar,yyline, yytext());}
    {MODULE} {return new Symbol (sym.MODULE,  (int) yychar,yyline, yytext());}
    {EXPONENTIATION} {return new Symbol (sym.EXPONENTIATION,  (int) yychar,yyline, yytext());}
    {SHIFTRIGHT} {return new Symbol (sym.SHIFTRIGHT,  (int) yychar,yyline, yytext());}
    {SHIFTLEFT} {return new Symbol (sym.SHIFTLEFT,  (int) yychar,yyline, yytext());}
    {EQUAL} {return new Symbol (sym.EQUAL,  (int) yychar,yyline, yytext());}
    {COMMA} {return new Symbol (sym.COMMA,  (int) yychar,yyline, yytext());}
    {SEMICOLON} {return new Symbol (sym.SEMICOLON,  (int) yychar,yyline, yytext());}
    {DOT} {return new Symbol (sym.DOT,  (int) yychar,yyline, yytext());}
    {LBRACKET} {return new Symbol (sym.LBRACKET,  (int) yychar,yyline, yytext());}
    {RBRACKET} {return new Symbol (sym.RBRACKET,  (int) yychar,yyline, yytext());}
    {LPAREN} {return new Symbol (sym.LPAREN,  (int) yychar,yyline, yytext());}
    {RPAREN} {return new Symbol (sym.RPAREN,  (int) yychar,yyline, yytext());}
    {LBRACE} {return new Symbol (sym.LBRACE,  (int) yychar,yyline, yytext());}
    {RBRACE} {return new Symbol (sym.RBRACE,  (int) yychar,yyline, yytext());}
    {QUESTIONSIGN} {return new Symbol (sym.QUESTIONSIGN,  (int) yychar,yyline, yytext());}
    {COLON} {return new Symbol (sym.COLON,  (int) yychar,yyline, yytext());}
    {SUMEQUAL} {return new Symbol (sym.SUMEQUAL,  (int) yychar,yyline, yytext());}
    {SUBEQUAL} {return new Symbol (sym.SUBEQUAL,  (int) yychar,yyline, yytext());}
    {MULTIEQUAL} {return new Symbol (sym.MULTIEQUAL,  (int) yychar,yyline, yytext());}
    {DIVEQUAL} {return new Symbol (sym.DIVEQUAL,  (int) yychar,yyline, yytext());}
    
    {CONSSCIENT} {return new Symbol (sym.CONSSCIENT,  (int) yychar,yyline, yytext());}
    {INVALIDID} {/*return INVALID_IDENTIFIER;*/}
    
    {MULTICOMMENT} {/*IGNORE*/}
    {MULTICOMMENTERROR} {return new Symbol (sym.MULTICOMMENTERROR,  (int) yychar,yyline, yytext());}
    {INTWITHSIGN} {return new Symbol (sym.INTWITHSIGN,  (int) yychar,yyline, yytext());}
    {INTWITHOUTSIGN} {return new Symbol (sym.INTWITHOUTSIGN,  (int) yychar,yyline, yytext());}
    {CONSSTRING} {return new Symbol (sym.CONSSTRING,  (int) yychar,yyline, yytext());}
    {ESCAPEERROR} {return new Symbol (sym.ESCAPEERROR,  (int) yychar,yyline, yytext());}
    {CONSSTRINGERROR} {return new Symbol (sym.CONSSTRINGERROR,  (int) yychar,yyline, yytext());}
    {ID} {return new Symbol (sym.ID, (int) yychar,yyline, yytext());}
    {CONSINTEGER} {return new Symbol (sym.CONSINTEGER,  (int) yychar,yyline, yytext());}
    {CONSREAL} {return new Symbol (sym.CONSREAL,  (int) yychar,yyline, yytext());}
    
    {ADDRESS} {return new Symbol (sym.ADDRESS,  (int) yychar,yyline, yytext());}
    {QUOTEERROR} {/*return QUOTEERROR;*/}
    {BLANKSPACE} {/*IGNORE*/}
    {INVALIDCHAR} {/*return INVALID_CHARACTER;*/}

    . {/*return UNIDENTIFIED_ERROR;*/}


