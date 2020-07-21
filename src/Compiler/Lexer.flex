//*****************************************
//  IMPORTS AND PACKAGES
//*****************************************
    package Compiler;
    import static Compiler.TokensEnum.*;
%%
//*****************************************
//  OPTIONS AND DECLARATIONS
//*****************************************
%class Scanner
%type TokensEnum
%line
%column
%{
    public String lexeme;
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
    {ADDRESS} {lexeme = yytext();return ADDRESS;}
    {AS} {lexeme = yytext();return AS;}
    {BOOL} {lexeme = yytext();return BOOL;}
    {BREAK} {lexeme = yytext();return BREAK;}
    {BYTE} {lexeme = yytext();return BYTE;}
    {BYTES} {lexeme = yytext();return BYTES;}
    {CONSTRUCTOR} {lexeme = yytext();return CONSTRUCTOR;}
    {CONTINUE} {lexeme = yytext();return CONTINUE;}
    {CONTRACT} {lexeme = yytext();return CONTRACT;}
    {DELETE} {lexeme = yytext();return DELETE;}
    {DO} {lexeme = yytext();return DO;}
    {ELSE} {lexeme = yytext();return ELSE;}
    {ENUM} {lexeme = yytext();return ENUM;}
    {FALSE} {lexeme = yytext();return FALSE;}
    {FOR} {lexeme = yytext();return FOR;}
    {FROM} {lexeme = yytext();return FROM;}
    {FUNCTION} {lexeme = yytext();return FUNCTION;}
    
    {CONSINTEGERHEX} {lexeme = yytext();return CONSINTEGERHEX;}

    {HEX} {lexeme = yytext();return HEX;}
    {IF} {lexeme = yytext();return IF;}
    {IMPORT} {lexeme = yytext();return IMPORT;}
    {INTERNAL} {lexeme = yytext();return INTERNAL;}
    {MAPPING} {lexeme = yytext();return MAPPING;}
    {MODIFIER} {lexeme = yytext();return MODIFIER;}
    {PAYABLE} {lexeme = yytext();return PAYABLE;}
    {PRAGMA} {lexeme = yytext();return PRAGMA;}
    {PRIVATE} {lexeme = yytext();return PRIVATE;}
    {PUBLIC} {lexeme = yytext();return PUBLIC;}
    {RETURN} {lexeme = yytext();return RETURN;}
    {RETURNS} {lexeme = yytext();return RETURNS;}
    {SOLIDITY} {lexeme = yytext();return SOLIDITY;}
    {STRING} {lexeme = yytext();return STRING;}
    {STRUCT} {lexeme = yytext();return STRUCT;}
    {THIS} {lexeme = yytext();return THIS;}
    {TRUE} {lexeme = yytext();return TRUE;}
    {UFIXED} {lexeme = yytext();return UFIXED;}
    {VAR} {lexeme = yytext();return VAR;}
    {VIEW} {lexeme = yytext();return VIEW;}
    {WHILE} {lexeme = yytext();return WHILE;}
    {BALANCE} {lexeme = yytext();return BALANCE;}
    {CALL} {lexeme = yytext();return CALL;}
    {CALLCODE} {lexeme = yytext();return CALLCODE;}
    {DELEGATECALL} {lexeme = yytext();return DELEGATECALL;}
    {SEND} {lexeme = yytext();return SEND;}
    {TRANSFER} {lexeme = yytext();return TRANSFER;}
    {DAYS} {lexeme = yytext();return DAYS;}
    {ETHER} {lexeme = yytext();return ETHER;}
    {FINNEY} {lexeme = yytext();return FINNEY;}
    {HOURS} {lexeme = yytext();return HOURS;}
    {MINUTES} {lexeme = yytext();return MINUTES;}
    {SECONDS} {lexeme = yytext();return SECONDS;}
    {SZABO} {lexeme = yytext();return SZABO;}
    {WEEKS} {lexeme = yytext();return WEEKS;}
    {WEI} {lexeme = yytext();return WEI;}
    {YEARS} {lexeme = yytext();return YEARS;}

    {LOGICNEGATION} {lexeme = yytext();return LOGICNEGATION;}
    {LOGICAND} {lexeme = yytext();return LOGICAND;}
    {ARITMETICEXOR} {lexeme = yytext();return ARITMETICEXOR;}
    {ARITMETICEQUAL} {lexeme = yytext();return ARITMETICEQUAL;}
    {LOGICDISTINCT} {lexeme = yytext();return LOGICDISTINCT;}
    {LOGICOR} {lexeme = yytext();return LOGICOR;}
    {ARITMETICLESSEQUALTHAN} {lexeme = yytext();return ARITMETICLESSEQUALTHAN;}
    {ARITMETICLESSTHAN} {lexeme = yytext();return ARITMETICLESSTHAN;}
    {ARITMETICGREATEREQUALTHAN} {lexeme = yytext();return ARITMETICGREATEREQUALTHAN;}
    {ARITMETICGREATERTHAN} {lexeme = yytext();return ARITMETICGREATERTHAN;}
    {ARITMETICAND} {lexeme = yytext();return ARITMETICAND;}
    {ARITMETICOR} {lexeme = yytext();return ARITMETICOR;}
    {ARITMETICNEGATION} {lexeme = yytext();return ARITMETICNEGATION;}
    {SUM} {lexeme = yytext();return SUM;}
    {SUBSTRACTION} {lexeme = yytext();return SUBSTRACTION;}
    {MULTIPLICATION} {lexeme = yytext();return MULTIPLICATION;}
    {SINGLECOMMENT} {/*IGNORE*/}
    {DIVISION} {lexeme = yytext();return DIVISION;}
    {MODULE} {lexeme = yytext();return MODULE;}
    {EXPONENTIATION} {lexeme = yytext();return EXPONENTIATION;}
    {SHIFTRIGHT} {lexeme = yytext();return SHIFTRIGHT;}
    {SHIFTLEFT} {lexeme = yytext();return SHIFTLEFT;}
    {EQUAL} {lexeme = yytext();return EQUAL;}
    {COMMA} {lexeme = yytext();return COMMA;}
    {SEMICOLON} {lexeme = yytext();return SEMICOLON;}
    {DOT} {lexeme = yytext();return DOT;}
    {LBRACKET} {lexeme = yytext();return LBRACKET;}
    {RBRACKET} {lexeme = yytext();return RBRACKET;}
    {LPAREN} {lexeme = yytext();return LPAREN;}
    {RPAREN} {lexeme = yytext();return RPAREN;}
    {LBRACE} {lexeme = yytext();return LBRACE;}
    {RBRACE} {lexeme = yytext();return RBRACE;}
    {QUESTIONSIGN} {lexeme = yytext();return QUESTIONSIGN;}
    {COLON} {lexeme = yytext();return COLON;}
    {SUMEQUAL} {lexeme = yytext();return SUMEQUAL;}
    {SUBEQUAL} {lexeme = yytext();return SUBEQUAL;}
    {MULTIEQUAL} {lexeme = yytext();return MULTIEQUAL;}
    {DIVEQUAL} {lexeme = yytext();return DIVEQUAL;}
    
    {CONSSCIENT} {lexeme = yytext();return CONSSCIENT;}
    {INVALIDID} {return INVALID_IDENTIFIER;}
    
    {MULTICOMMENT} {/*IGNORE*/}
    {MULTICOMMENTERROR} {lexeme = yytext();return MULTICOMMENTERROR;}
    {INTWITHSIGN} {lexeme = yytext();return INTWITHSIGN;}
    {INTWITHOUTSIGN} {lexeme = yytext();return INTWITHOUTSIGN;}
    {CONSSTRING} {lexeme = yytext();return CONSSTRING;}
    {ESCAPEERROR} {lexeme = yytext();return ESCAPEERROR;}
    {CONSSTRINGERROR} {lexeme = yytext();return CONSSTRINGERROR;}
    {ID} {lexeme = yytext();return ID;}
    {CONSINTEGER} {lexeme = yytext();return CONSINTEGER;}
    {CONSREAL} {lexeme = yytext();return CONSREAL;}
    
    {ADDRESS} {lexeme = yytext();return ADDRESS;}
    {QUOTEERROR} {return QUOTEERROR;}
    {BLANKSPACE} {/*IGNORE*/}
    {INVALIDCHAR} {return INVALID_CHARACTER;}

    . {return UNIDENTIFIED_ERROR;}


