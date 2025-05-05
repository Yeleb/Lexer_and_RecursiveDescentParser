
import java.util.*;
public class Lexer {
    private String input;
    private ArrayList<Object> lexemeArrayList;
    private ArrayList<Token> finalToken;
    private int currentPosition;
    public static String errorMessage = null;

  
    public Lexer(String input){
      this.input = input;
      this.lexemeArrayList = new ArrayList<>();
      this.finalToken = new ArrayList<>();
      this.currentPosition = 0;
    }
    public static boolean hasAnError() {
      return errorMessage != null;
  }
  
  public static String getError() {
      return errorMessage;
  }
  
  public static void clearError() {
      errorMessage = null;
  }


    public ArrayList<Token> tokenize(){
    //operators that will be sepereated. ==nfirst so it doesnt read it as = =.
    String[] seperate = {"==", "&&", "||", "!=", ">=", "<=", "+", "-", "/", "*", "%", "=", ">", "<", ";", "(", ")"};       

    //Went with using a tokenizer. takes the user input and the delimeter is set for all spaces and new lines
    StringTokenizer tInput = new StringTokenizer(input, " \n");

      /* loops as long as input still has more "tokens"
       * creates a string of the tokens. 
       * and sets has op to false so then if it finds one we late set to true
       */
        while(tInput.hasMoreTokens()){
          String token = tInput.nextToken();
          boolean hasOperator = false;

          //loops through the seperator array to see if current token contains the op.
          for(int i = 0; i < seperate.length; i++){
            String op = seperate[i];
            if (token.contains(op)) {
              hasOperator = true;
              break;
          }
        }


        if (hasOperator){
          int start = 0;
          int pos = 0;

          //loops for the token. Everything but if operator is in last spot.
          while (pos < token.length()){
            boolean foundOp = false;
            
          /*loops through sperate array to see if the token
          contains an operator that the position of pos. 
          */
            for (int i=0; i< seperate.length; i++){
              String op = seperate[i];

              /*
               * if token starts with "==" at 0.
               * if no then it moves to next one
               * if token starts with "&&" at 0.
               */
              if(token.startsWith(op, pos)){
                //this pretty much says its going to cut the words before op
                if(pos > start){
                  String before = token.substring(start, pos);
                  lexemeArrayList.add(before); 
                  //System.out.println(before);
                }
                lexemeArrayList.add(op);
                //System.out.println(op);
                pos += op.length();
                start = pos;
                foundOp = true;
                break;
                
              }
            }

              if (!foundOp){
                pos++;
              }
        }

          //after it gets rid of op this should print everything after. 
         if (start < token.length()){
          String remaining = token.substring(start);
          lexemeArrayList.add(remaining);
          //System.out.println(remaining);
        }
          
      }
      
      else{
        lexemeArrayList.add(token);
          //System.out.println(token);

        }
      }

        boolean invalidToken = false;
      
        for(int i = 0; i < lexemeArrayList.size(); i++){
         
         String convert = lexemeArrayList.get(i).toString();
         String tokenType;

          if(convert.equals("end_program")){
            tokenType = "END_PROGRAM";
           }
           else if(convert.equals("program")){
            tokenType = "PROGRAM";
           }
           else if(convert.equals("if")){
            tokenType = "IF";
           }
           else if(convert.equals("end_if")){
            tokenType = "END_IF";
           }
           else if(convert.equals("loop")){
            tokenType = "LOOP";
           }
           else if(convert.equals("end_loop")){
            tokenType = "END_LOOP";
         }
         
         else if(Character.isLetter(convert.charAt(0))){
          tokenType = "IDENTIFIER";
         }
         else if(convert.matches("\\d+")){
          tokenType = "INT_LIT";
         }
        // String[] seperate = {"==", "&&", "||", "!=", "+", "-", "/", "*", "%", "=", ">", "<", ";", "(", ")"}; 
       
        //sorry for what you have to witness
       /*
       OPERATORS:
        PLUS, MINUS, MULT, DIV, MOD, ASSIGN, 
        EQ, NEQ, GT, LT, AND, OR, 

        PUNCTUATION:
        SEMI, LPAREN, RPAREN, COLON,
       
       */ 
        else if(convert.equals("+")){
          tokenType = "PLUS";
        }
        else if(convert.equals("-")){
          tokenType = "MINUS";
        }
        else if(convert.equals("*")){
          tokenType = "MULT";
        }
        else if(convert.equals("/")){
          tokenType = "DIV";
        }
        else if(convert.equals("%")){
          tokenType = "MOD";
        }
        else if(convert.equals("=")){
          tokenType = "ASSIGN";
        }
        else if(convert.equals("==")){
          tokenType = "EQ";
        }
        else if(convert.equals("!=")){
          tokenType = "NEQ";
        }
        else if(convert.equals(">")){
          tokenType = "GT";
        }
        else if(convert.equals("<")){
          tokenType = "LT";
        }
        else if(convert.equals("&&")){
          tokenType = "AND";
        }
        else if(convert.equals("||")){
          tokenType = "OR";
        }
        else if(convert.equals(";")){
          tokenType = "SEMI";
        }
        else if(convert.equals("(")){
          tokenType = "LP";
        }
        else if(convert.equals(")")){
          tokenType = "RP";
        }
        else if(convert.equals(":")){
          tokenType = "COLON";
        }
        else if(convert.equals("<=")){
          tokenType = "LTEQ";
        }
        else if(convert.equals(">=")){
          tokenType = "GTEQ";
        }
        else{
          invalidToken = true;
          if(invalidToken){
            errorMessage = "ERROR: INVALID TOKEN: "+convert;
            return null;
          }
          break;
        }

          finalToken.add(new Token(tokenType, convert));
      }
    
      
       if(!lexemeArrayList.get(0).toString().equals("program")){
        errorMessage = "ERROR: INPUT MUST START WITH 'program' ";
        return null;
       // System.out.println("\nERROR: INPUT MUST START WITH 'program' ");
      }
      else if(!lexemeArrayList.get(lexemeArrayList.size() - 1).toString().equals("end_program")) {
        errorMessage = "ERROR: INPUT MUST END IN 'end_program' ";
        return null;
       // System.out.println("\nERROR: INPUT MUST END IN 'end_program' ");
      }
          
     /*   if(invalidToken){
        errorMessage = "ERROR: INVALID TOKEN: "+convert+ "'";
        return null;
      }
        */
        return finalToken;
      }

      public static class Token {
        //PPP rule Keep private use getters and setters
        private String type;
        private String lexeme;
        
        public Token(String type, String lexeme){
          this.type = type;
          this.lexeme = lexeme;
        }

        public String getType() { 
          return type; 
        }
        public String getLexeme() { 
          return lexeme; 
        }

    public String toString() {
      
        return "\n(TOKEN: "+type+ " | LEXEMES: "+lexeme+")";
    }
  }
  //as long as theres more tokens, will go to next token
  // if it reaches end it returns eof (last token) if lex() is called
  public Token lex() {
    if (currentPosition < finalToken.size()) {
        return finalToken.get(currentPosition++);
    }
    return new Token("EOF", "EOF");
}
  
}


  

