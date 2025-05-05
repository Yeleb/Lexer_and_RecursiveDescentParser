# LEXER AND RECURSIVE DESCENT PARSER FOR A CUSTOM LANGUAGE

A java implementation of a Lexer and a Recursive Descent Parser for a custom Language 

 --------------------------**HOW TO USE**---------------------------------  
1. Run the `FinalProject.java` file
2. Enter your code in the input text area
3. Click the "Run" button
4. View the tokenized output and parse results in the output area

 -----------------------**LEXICAL ANAYLSIS(Lexer.java)**--------------------------
- Tokenizes input code into 
- Handles comments (Single-Line with '//')
- Supports: '==', '&&', '||', '!=', '>=', '<=', '+', '-', '/', '*', '%', '=', '>', '<', ';', '(', ')'
- Error Detections for Invalid Token

 -----------------------**RECURSIVE DESCENT PARSER(Parser.java)**-----------------
- Recursive descent parser that validates program structure
- Implements grammar rules for:
  - Program structure (`program ... end_program`)
  - Statements (assignments, conditions, loops)
  - Expressions and arithmetic operations
  - Conditional statements (`if ... end_if`)
  - Loop constructs (`loop ... end_loop`)
    
 -----------------------**GUI Interface(FinaProject.java)**-----------------------
- Simple Swing-based interface for code input and output
- Displays tokenized output and parse results


 --------------------------**LANGUAGE SPECIFICS**---------------------------------  
- 1- Programs in the language start with the word program and end with end_program <program> -> program <statements> end_program 
- 2- The language is typeless 
- 3- The language accept liner comments in the format //this is a comment 
- 4- Statements end with a semicolon 
- 5- The language supports alphanumeric identifier names that do not start with a digit 
- 6- The language supports the operations =, +, -, *, /, %, and () 
- 7- The language supports if statements (no else) in the form: <condition> -> if (<logic expression>) <statements> end_if 
- 8- The language supports binary logical operations in the form <logic_expression> -> <var> (==|!=|>|<|>=|=<) <var> 
- 9- You can cascade multiple binary conditions using && and II 
- 10- The language supports loops that increment a value by 1 until an end value is reached, such that <loop>  loop(<var> = <var> : <var>)<statements> end_loop 

 --------------------------**LANGUAGE EXAMPLE**---------------------------------  
```
program  
value = 32; 
mod1 = 45; 
//Calculating performance 
z = mod1 / value * (value % 7) + mod1; 
loop (i = 0 : value) 
z = z + mod1; 
end_loop  
if (z >= 50 && value <= 60) 
newValue = 50 / mod1; 
x = mod1; 
if (y < 5) 
y = x + 5; 
end_if 
end_if 
end_program 
```
-------------**AUTHOR**------------
Caleb Zeringue @Yeleb https://github.com/Yeleb
