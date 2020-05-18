//*****************************************
//  IMPORTS AND PACKAGES
//*****************************************
    package LexicalAnalyzer;
    import static LexicalAnalyzer.Tokens.*;
%%
//*****************************************
//  OPTIONS AND DECLARATIONS
//*****************************************
    %class Scanner
    %type Tokens

    ADDRESS = address
    AS = as
    BOOL = bool
    BREAK = break
    BYTE = byte
    BYTES = bytes
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
    INT = int
    INTERNAL = internal
    MAPPING = mapping
    MODIFIER = modifier
    PAYABLE = payable
    PRAGMA = Pragma
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
    UINT = uint
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
    LOGICAND = \&&
    ARITMETICEXOR = \^
    ARITMETICEQUAL = \==
    LOGICDISTINCT = \!=
    LOGICOR = \||
    ARITMETICLESSEQUALTHAN = \<=
    ARITMETICLESSTHAN = \<
    ARITMETICGREATEREQUALTHAN = \>=
    ARITMETICGREATERTHAN = \>
    ARITMETICAND = \&
    ARITMETICOR = \|
    ARITMETICNEGATION = \~
    SUM = \+
    SUBSTRACTION = \-
    MULTIPLICATION = \*
    DIVISION = \/
    MODULE = \%
    EXPONENTIATION = \**
    SHIFTRIGHT = \>>
    SHIFTLEFT = \<<
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
    SUMEQUAL = \+=
    SUBEQUAL = \-=
    MULTIEQUAL = \*=
    DIVEQUAL = \/=

    SINGLECOMMENT = \\\\[^\n\\]+
    MULTICOMMENT = \/\*\*([^*]|(\*+[^*/]))*\*+\/
    ID = [a-zA-Z]{1}[a-zA-Z|0-9|_]{0,30}
    BLANKSPACE = [ ,\t,\r,\r,\n]+
    CONSINTEGER = [0-9]+
    CONSREAL = [0-9]*[.][0-9]+|[0-9]+[.][0-9]*
    CONSSCIENT = [-]?[0-9]+([.][0-9]+)?[e][-]?[0-9]+
%{
    public String lexeme;
%}

%%
//*****************************************
//  LEXICAL RULES
//*****************************************
int|
if|
else|
while {lexeme=yytext();return RESERVADAS;}
{BLANKSPACE} {/*Ignore*/}
"//".* {}
"=" {return EQUAL;}
. {return ERROR;}


