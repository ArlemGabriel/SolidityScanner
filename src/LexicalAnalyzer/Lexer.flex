//User code: Place to put package declarations and import statements
package LexicalAnalyzer
%%
//Options and Declarations

%class Scanner
%line
%column

%%
//Lexical Rules

CONSSTRING \"[^"\\\n]*((?:\\((xNN)|(uNNNN))[^"\\\n]*)|(?:\\[n"][^"\\]*)|(?:\\\\[^"\\\n]*))*\"|\'[^"\\\n]*((?:\\((xNN)|(uNNNN))[^"\\\n]*)|(?:\\[n"][^"\\]*)|(?:\\\\[^"\\\n]*))*\'
CONSINTEGERHEX  hex("[0-9|A-F|a-f]+"|'[0-9|A-F|a-f]+')
