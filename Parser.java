    /*
    EBNF languge
    FROM THE SLIDES:

    {} mean 0 or more
    [] optional are placed in brackets
    () multiple choice placed in parenthesis

   * <program> -> program <statements> end_program

   * <statements> -> {<statement>}
   * <statement> -> (<assignment> | <condition> | <loop>)

   * <assignment> -> <var> = <expression> ; 

   * <expression> -> <term> { ( + | - ) <term> }
   * <term> -> <factor> { ( * | / | % ) <factor> }
   * <factor> -> <var> | INT_LIT | (LP <expression> RP)
   * <var> -> IDENTIFIER
   * 
   * 
   * 
   * <condition> -> if (LP <logical_expression> RP) <statements> end_if
   *  <logic_expression> -> <var> (==|!=|>|<|>=|=<) <var> |
   *       <logic_expression> ( && | || ) <logic_expression>
   * 
   *
   * 
       

   \

      <logic_expression> → <expression> ("=="|"!="|">"|"<"|">="|"<=") <expression>
                   | <logic_expression> ("&&" | "||") <logic_expression>

    <loop> -> loop (LP <var> =  <expression> :  <expression> RP) <statements> end_loop 
     */

     public class Parser {


        public static String errorMessage = null; 
        
        public static boolean hasError() {
            return errorMessage != null;
        }
        
        public static String getError() {
            return errorMessage;
        }
        
        public static void clearError() {
            errorMessage = null;
        }


        public static Lexer lexer; 
        public static Lexer.Token nextToken;

     public Parser(Lexer lexer){
        this.lexer = lexer;
        this.nextToken = lexer.lex();
        program();
     }

     private static void lex() {
        nextToken = lexer.lex();
        if (nextToken == null) {
            nextToken = new Lexer.Token("EOF", "EOF");
        }
    }

      public static void program(){
        if (hasError()) return;

        System.out.println("Enter <program>");
     

        if(nextToken.getType().equals("PROGRAM")){

            lex();

            statements();
            if (hasError()) return; 

            if(!hasError()){
            if(nextToken.getType().equals("END_PROGRAM")){
                lex();
            }else{
                 errorMessage = "ERROR: MISSING 'end_program'";
                return;
            }
        }

        }else{
            errorMessage = "ERROR: MISSING 'program' AT START";
            return;
        }
        System.out.println("Exit <program>");
      }

      public static void statements() {
        if (hasError()) return;
        System.out.println("Enter <statements>");
    
        if (nextToken.getType().equals("END_LOOP") || 
            nextToken.getType().equals("END_IF") ||
            nextToken.getType().equals("END_PROGRAM")) {
            System.out.println("Exit <stmts>");
            return;
        }
    
        // Kept getting semi colon errors so added this if it detects an Assignment. 
        boolean isAssignment = nextToken.getType().equals("IDENTIFIER");
        
        statement();
        if (hasError()) return; 
        
        if (isAssignment) {
            if (!nextToken.getType().equals("SEMI")) {
                errorMessage = "ERROR: MISSING ';' AFTER ASSIGNMENT";
                return;
            }
            lex(); 
        }
    
        statements(); 
        if (hasError()) return; 
    


    }

      public static void statement(){
        if (hasError()) return;
        System.out.println("Enter <statement>");

        if(nextToken.getType().equals("IDENTIFIER")){
            assignment();
         }else if(nextToken.getType().equals("IF")){
            Condition();
         }else if(nextToken.getType().equals("LOOP")){
            Loop();
         }else {

            errorMessage = "ERROR: EXPECTED 'IDENTIFIER', 'IF', 'LOOP' ";
            return;
           
        }
        System.out.println("Exit <statement>");
      }


      public static void assignment(){
        if (hasError()) return;
        System.out.println("Enter <Assignment>");

        if(nextToken.getType().equals("IDENTIFIER")){
            Var();
        }

        if(nextToken.getType().equals("ASSIGN")){
            lex();
        }else{
            errorMessage = "ERROR: TOKEN SHOULD BE '=' ";
            return;
        }
        expression();
        if (hasError()) return; 

        System.out.println("Exit <Assignment>");
      }

      public static void expression(){
        if (hasError()) return;
        System.out.println("Enter <Expression>");

        term();
        if (hasError()) return; 


        
        while (nextToken.getType().equals("PLUS") || nextToken.getType().equals("MINUS")){
            lex();
            term();
            if (hasError()) return; 
        }
            
            System.out.println("Exit <Expression>");
      }
    

      public static void Condition(){
        if (hasError()) return;
        System.out.println("Enter <Condition>");
      
        if(nextToken.getType().equals("IF")){
            lex();
        }else{
            errorMessage = "ERROR: TOKEN MUST = 'IF' ";
            
            return;
        }
         if(nextToken.getType().equals("LP")){
            lex();
           // logical_expression();
        }else{
            errorMessage = "ERROR: TOKEN MUST = '(' ";
           
            return;
        }

        logical_expression();
        if (hasError()) return;

        if(nextToken.getType().equals("RP")){
            lex();
           // statements();
        }else{
            errorMessage = "ERROR: TOKEN MUST = ')' for expression or needs &&, || ";
            return;
        }
        statements();
        if (hasError()) return;

        
        if(nextToken.getType().equals("END_IF")){
            lex();
        }else{
            errorMessage = "ERROR: IF STATEMENTS MUST END IN 'end_if' ";
            return;
        }
        System.out.println("Exit <Condition>");
      }

      public static void logical_expression(){
        if (hasError()) return;
        System.out.println("Enter <logical_expression>");

        expression();

        if(nextToken.getType().equals("EQ") ||
        nextToken.getType().equals("NEQ") ||
        nextToken.getType().equals("LT") ||
        nextToken.getType().equals("GT") ||
        nextToken.getType().equals("LTEQ") ||
        nextToken.getType().equals("GTEQ")){
            lex();
            expression();
            if (hasError()) return; 

        while(nextToken.getType().equals("AND")||
        nextToken.getType().equals("OR")){
            lex();
            logical_expression();
            if (hasError()) return; 
        }
        }else{
            
            errorMessage = "ERROR: TOKEN SHOULD BE COMPARISION OPERATOR ";
            
            return;    
        }
        System.out.println("Exit <logical_expression>");  
        }

        public static void Loop(){
            if (hasError()) return;
            System.out.println("Enter <loop>");

            if(nextToken.getType().equals("LOOP")){
                lex();
            }else{

                errorMessage = "ERROR: LOOPS MUST BEGIN WITH 'loop' ";
        
                return;  
              
            }

            if(nextToken.getType().equals("LP")){
                lex();
                Var();
                if (hasError()) return; 

            }else{
                errorMessage = "ERROR: TOKEN SHOULD BE '(' ";
                return;  
            }


            if(nextToken.getType().equals("ASSIGN")){
                lex();
                expression();
                if (hasError()) return; 
         
            }else{
                errorMessage = "ERROR: TOKEN SHOULD BE '=' ";
                return;  
            }
            if(nextToken.getType().equals("COLON")){
                lex();
                expression();
                if (hasError()) return; 
               
            }else{
                errorMessage = "ERROR: TOKEN SHOULD BE ':' ";
                return;  
            }
            if(nextToken.getType().equals("RP")){
                lex();
                statements();
                if (hasError()) return; 
            }else{
                errorMessage = "ERROR: TOKEN SHOULD BE ')' ";
                return;  
            }
            if(nextToken.getType().equals("END_LOOP")){
                lex();
            }else{
                errorMessage = "ERROR: LOOPS MUST END IN 'end_loop' ";
                return; 
            }
            System.out.println("Exit <loop>");
        }
      
      

      public static void term(){
        if (hasError()) return;
        System.out.println("Enter <Term>");

        Factor();

        while(nextToken.getType().equals("MULT") || nextToken.getType().equals("DIV") || nextToken.getType().equals("MOD")){
            lex();
            Factor();
            if (hasError()) return; 
        }

        System.out.println("Exit <Term>");

      }

      public static void Factor(){
        if (hasError()) return;
        System.out.println("Enter <Factor>");

        if(nextToken.getType().equals("INT_LIT")){
            lex();
        }else if (nextToken.getType().equals("LP")){
                lex();
                expression();
                if (hasError()) return; 

                if(nextToken.getType().equals("RP")){
                    lex();
                }else{
                    errorMessage = "ERROR: TOKEN MUST BE ')' OR ADD OP IN EXPRESSION ";
                   
                    return; 
                }
            }
            //I know its redundant but it helps follow the grammer. 
            else if(nextToken.getType().equals("IDENTIFIER")){
                Var();
                if (hasError()) return; 
            }
            else{
                errorMessage = "ERROR: EXPECTED VARIABLE, INTEGER, OR '(' ";
                    return; 
            }
            System.out.println("Exit <factor>");
        }

        public static void Var(){
            if (hasError()) return;
            System.out.println("Enter <var>");

            if(nextToken.getType().equals("IDENTIFIER")){
                lex();
            }else{
                errorMessage = "ERROR: TOKEN MUST BE IDENTIFIER ";
                
                    return; 
            }
            System.out.println("Exit <var>");
        }
        
      }