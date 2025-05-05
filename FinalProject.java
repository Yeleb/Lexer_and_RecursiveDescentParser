import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;
public class FinalProject {


  
  
    public static void main(String[] args) {
      
      JFrame frame = new JFrame("Final Program");  
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      frame.setSize(800, 600); 

      JTextArea jinput = new JTextArea(); 
      JTextArea joutput = new JTextArea(); 

      JButton run = new JButton("Run");


      //Fix Joutput area. 
      frame.setLayout(new BorderLayout()); 
      frame.add(new JScrollPane(jinput), BorderLayout.CENTER); 
      JPanel bottomPanel = new JPanel(new BorderLayout()); 
      bottomPanel.add(run, BorderLayout.NORTH); 
      bottomPanel.add(new JScrollPane(joutput), BorderLayout.CENTER);
      frame.add(bottomPanel, BorderLayout.SOUTH);



      run.addActionListener((ActionEvent e) -> { 
        Parser.clearError(); 

        String[] lines = jinput.getText().split("\n"); 
        StringBuilder inputBuilder = new StringBuilder();


        /*
         * For loop is checking to see if thers comments
         * right now input in array, after comment seperation appends to string using inputBuilder
         */
        
         for(int i = 0; i < lines.length; i++){
            String line = lines[i]; 
            String[] commentCheck = line.split("//",2); 
            String nonComment = commentCheck[0];
            inputBuilder.append(nonComment).append(" ");
            
        }
        String input = inputBuilder.toString();

        
        Lexer lexer = new Lexer(input); 
        ArrayList<Lexer.Token> tokens = lexer.tokenize();

        
        if (Lexer.hasAnError()) {
            joutput.setText(Lexer.getError());
            return;
        }


        /* 
         * This for loop just prints the tokens in the output section.
         * cant even see it though cause jFrame janky rn
         * 
         */
        StringBuilder output = new StringBuilder("OUTPUT:\n");
        for (Lexer.Token token : tokens) {
            output.append(token).append("\n");
        }
        

        
        Parser parser = new Parser(lexer);

        if(Parser.hasError()){
            output.append("PARSE FAILED! ");
            output.append(Parser.getError());
        }else{
        output.append("PARSE SUCCESSFUL");
        }
        joutput.setText(output.toString());
    });
    
    frame.setVisible(true); 
  }
}



/* 
    Scanner scan = new Scanner(System.in); 
    
    System.out.println("Enter Code: ");

    //this helps me read multiple lines 
    StringBuilder inputBuilder = new StringBuilder();

    //infinite input until end_program is typed or if user does control+D
    while (scan.hasNextLine()) {
      //connecting all my input lines together by a space
      String line = scan.nextLine();

      //will have to change this. 
      if (line.startsWith("//")) {  // This will skip commented line
        continue;
    }
      inputBuilder.append(line).append(" ");

      // ends the line if the input contains end_program
      if (line.contains("end_program")) {
          break;
      }
  }

  // ERROR if "end_program" is not in the input. This happens if the user doe Control+D pretty mch
  String input = inputBuilder.toString();
*/
