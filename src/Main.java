//import IO for file manipulation tools
import java.io.*;
import java.util.Scanner;
import java.util.Stack;

/**Regex2NFA is the driver class for the REGEX Engine
 * It will open a file a text file that contains postfix order Regular Expressions
 * separated one per line, and then create an NFA for each of these REGEX
 * Main will use the first command-line parameter as the name of the file to open.
 * If no file-name is supplied, it will default to regex_postfix.txt which is supplied in this package.
 * @author JosephBoothby
 */
public class Main {

    public static void main(String[] args) {

        //init a stack to hold NFAs during processing
        Stack<NFA> nfaStack = new Stack<NFA>();
        //initialize the scanner outside of the try/catch block for scope purposes
        Scanner in;
        //initialize a file. This will default to regex_postfix.txt if there is no filename supplied by the args parameter
        File inputFile;
        if( args.length > 0){
            System.out.println("Command line argument detected. Using file name \"" + args[0] + "\"" );
            inputFile = new File(args[0]);
        }
        else{            System.out.println("Command Line argument not detected. Using default file name \"regex_postfix.txt\"");
            inputFile = new File("regex_postfix.txt");

        }


        //Attempt to open the file and create a scanner for it
        try {
            in = new Scanner(inputFile);
            System.out.println("File opened properly");

            //iterate through file line by line
            while (in.hasNextLine()) {

                //flag for checking for bad input
                boolean badInputFlag = false;

                //make the current line into a char array to iterate by character
                char[] thisLine = in.nextLine().toCharArray();

                //clear the nfaStack in case there is bad input left in from previous iteration
                nfaStack = stackClear(nfaStack);

                System.out.println("The NFA for " + String.valueOf(thisLine) + " is below!");
                System.out.println("---------------------------------------");

                //iterate through each char in the line
                for( char temp : thisLine) {
                    //only execute the loop if no bad input has been detected
                    if (!badInputFlag) {
                        //init two nfas to be used for push/pop in this scope
                        NFA nfa1;
                        NFA nfa2;
                        //enter a switch statement that determines the action based on the given character
                        switch (temp) {
                            //for characters a,b,c,d,e,E just create an NFA and push onto the stack
                            case 'a' -> nfaStack.push(new NFA('a'));
                            case 'b' -> nfaStack.push(new NFA('b'));
                            case 'c' -> nfaStack.push(new NFA('c'));
                            case 'd' -> nfaStack.push(new NFA('d'));
                            case 'e' -> nfaStack.push(new NFA('e'));
                            case 'E' -> nfaStack.push(new NFA('E'));
                            //for character |, perform the union operation if there is enough items on the stack to do it
                            case '|' -> {
                                //only execute the operation if the stack contains more than 1 value
                                if( nfaStack.size() > 1) {
                                    nfa2 = nfaStack.pop();
                                    nfa1 = nfaStack.pop();
                                    nfaStack.push(NFA.union(nfa1, nfa2));
                                }
                                else{
                                    System.out.println("The RegEx is improperly formed for use of the | operator");
                                    badInputFlag = true;
                                    stackClear(nfaStack);
                                }
                            }
                            //for character &, perform the concatenation operation if the stack contains enough items to do it
                            case '&' -> {
                                //only execute the operation if the stack contains more than 1 value
                                if( nfaStack.size() > 1) {
                                    nfa2 = nfaStack.pop();
                                    nfa1 = nfaStack.pop();
                                    nfaStack.push(NFA.cat(nfa1, nfa2));
                                }
                                else{
                                    System.out.println("The RegEx is improperly formed for the use of the & operator");
                                    badInputFlag = true;
                                    stackClear(nfaStack);
                                }
                            }
                            //For the * operator, perform the * operation if the stack has an item. Push this back onto the stack.
                            case '*' -> {
                                if( nfaStack.size() > 0) {
                                    nfa1 = nfaStack.pop();
                                    nfaStack.push(NFA.kStar(nfa1));
                                }
                                else{
                                    System.out.println("The RegEx is improperly formed for the use of the * operator");
                                    badInputFlag = true;
                                    stackClear(nfaStack);
                                }
                            }
                            //if the character is not in the alphabet (sigma) then the input is bad. Output error message, and set flag
                            default -> {
                                System.out.println("The character " + temp + " is not part of the alphabet for this language");
                                badInputFlag = true;
                                stackClear(nfaStack);
                            }
                        }
                    }
                    else break; //breaks loop if bad input is detected to avoid unnecessary processing
                }
                //if the NFA has a single item on the stack, then the operation was successful. output the final NFA
                //because each bad operation clears the stack, this should only run if input was correct format
                if( nfaStack.size() == 1 ) {
                    NFA output = nfaStack.pop(); //print the NFA on the top of the stack
                    output.print();
                    System.out.println();
                }
                //if the stack has too many, or too few items on the stack, the operation was not successful
                else{
                    System.out.println("The RegEx was in an improper format, or it was lacking an operator.\n");
                }
            }
        //catch exception from the file not being opened
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found!");
            fileNotFoundException.printStackTrace();
        }
    }

    /**this stackClear method is just used to empty the NFA stack if there is a bad input from a line
     * @param s stack to be cleared
     * @return s empty stack
     * @author JosephBoothby
     */
    public static Stack<NFA> stackClear(Stack<NFA> s){
        while( !s.isEmpty()){
            s.pop();
        }
        return s;
    }
}


