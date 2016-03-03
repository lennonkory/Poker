package Controller;

import Controller.InputListener;
import java.util.Scanner;

/**
 * @author kory
 */
public class InputController {
    
    private InputListener il;
    
    public void getInput(){
        
        String command = "";
        Scanner input = new Scanner(System.in);

        do {
            
            System.out.print("Enter command: ");
            command = input.nextLine();
            int b= 0;
            
            if (command.equals("quit")) {
                break;
            }

            if (command.equals("bet")) {
                b = Integer.parseInt(input.nextLine());
                
            }

            il.input(command + " " + b);
            System.out.println();

        } while (!command.equals("quit"));
        
    }
    
    public void addInputListener(InputListener il){
        this.il = il;
    }
    
}
