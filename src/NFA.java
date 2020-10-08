import java.util.LinkedList;

/**This class defines the NFA object
 * an NFA is comprised of a 5-tuple of values {Q,S,D,q0,F}
 * These values are as follows
 * Q: The set of all states in the NFA (represented as a linked list of states)
 * S: The language of the NFA (For our purposes, this will always be {a,b,c,d,e,E,} (E represents the empty string)
 *    Checking that inputs are within the language will be handled by the method passing parameters to this constructor
 * D: The transitions between states. These are maintained by the individual states
 * q0: The start state for the NFA
 * F: The set of accept states for the NFA (represented as a LinkedList)
 * @author JosephBoothby
 */
public class NFA {

    //Class variables for the NFA object
     LinkedList<State> Q = new LinkedList<State>();
     State q0;
     State F;

    /**Constructor method for an NFA. This constructor is the base case, and creates a single state
     * NFA when supplied a single character. This NFA has only two states. The start state has will be q0, and
     * there will be a single transition via the input char to the final state q1.
     * @param letter single character input letter
     * @author JosephBoothby
     */
    NFA( char letter){

        //If the lettering being passed is epsilon, then make the start and end state the same with no transitions.
        if( letter == 'E'){
            q0 = new State();
            Q.add(q0);
            F = q0;
            return;
        }

        //if the letter being passed is not epsilon, then create a start and final state with the appropriate transition
        q0 = new State();              //Set start state of NFA equal to a new State called q0
        State accept = new State();    //Create a second state to be the Final state
        Q.add(q0);                     //add both states to the state linked List that holds each state
        Q.add(accept);                 //add the new accept state to the total states
        F = accept;                     //make the accept state the new accept
        q0.addTransition(letter, accept);  //transition from start to finish via the letter

    }

    /**The union method creates an NFA that is the union of two inputs NFAs, and returns the result
     * It does this though adding an additional start state with epsilon transitions to the start of each input NFA
     * And an additional final state with epsilon transitions coming from each accept state of the input NFAs
     * @param n1 The first input NFA
     * @param n2 The second input NFA
     * @return nu The union of the first and second NFA (stands for NFA Union)
     */
    public static NFA union(NFA n1, NFA n2) {
        //create single state NFA
        NFA n3 = new NFA('E');

        //Initialize accept state (new start state is n3.q0);
        State newAccept = new State();

        //add transitions from new start to start states from each old NFA
        n3.q0.addTransition( 'E', n1.q0);
        n3.q0.addTransition( 'E', n2.q0);

        //add transition from the accept state of each input NFA to the accept state of the new NFA
        n1.F.addTransition('E', newAccept);
        n2.F.addTransition('E', newAccept);


        //add all the states from n1 and n2 to n3
        n3.Q.addAll(n1.Q);
        n3.Q.addAll(n2.Q);

        //add new accept state on the end
        n3.Q.add(newAccept);

        //add the correct accept state
        n3.F = newAccept;

        //return the now completed new NFA
        return( n3 );
    }

    /**The static cat method is for concatenating two NFAs together
     * It does this by creating a new NFA, whos start state the the start state of the
     * first parameter NFA, and whos end state is the accept state of the second parameter NFA
     * It then adds all of the states from both NFAs to the new NFA and returns it
     * @param n1 An object of type NFA
     * @param n2 An object of type NFA to be concatenating onto the END of n1
     * @return n3 The NFA created by concatenating n2 onto n1
     * @author JosephBoothby
     */
    public static NFA cat( NFA n1, NFA n2){
        //create a single state NFA
        NFA n3 = new NFA('E');

        //clear the states from n3
        n3.Q.clear();

        //make the start state the same as n1 start
        n3.q0 = n1.q0;
        //make the accept state the same as n2 accept
        n3.F = n2.F;

        //Add the transition between the accept and start states of n1 and n2
        n1.F.addTransition('E', n2.q0);

        //Add the states from n1 and n2 to n3 in order
        n3.Q.addAll(n1.Q);
        n3.Q.addAll(n2.Q);

        return n3;

    }

    /**The kStar method takes a single NFA and returns the NFA with the Kleene Start operator applied
     * It does this by adding a start state with an epsilon epsilon transition to start state of n1, and from
     * The accept state of n1 to the new start state
     * @param n1 The NFA to apply the Kleene Star operation to
     * @return nReturn The NFA resulting from applying the Kleene Star to the parameter NFA
     * @author JosephBoothby
     */
    public static NFA kStar( NFA n1){
        //create a new single state NFA. This NFA will have the correct accept state by default
        //because creating an NFA with E sets the start state to be the accept state
        NFA nReturn = new NFA( 'E');

        //add transition from the new start state to the start state of n1
        nReturn.q0.addTransition('E', n1.q0);
        //add transition from the accept state of n1 to the new start state
        n1.F.addTransition('E', nReturn.q0);
        //add the states from n1 to the new NFA
        nReturn.Q.addAll(n1.Q);

        return nReturn;
    }

    /**the print method updates the numbering of the states in an NFA and prints it
     * @author JosephBoothby
     */
    public void print(){

      int counter = 1; //init counter

        //iterate over the states and number them sequentially
       for( State temp : this.Q ){
           temp.name = counter;
           counter++;
       }

       //output each state using the State.toString() method
       for( State output : this.Q ){
           System.out.println(output);
       }

       System.out.println("S " + this.q0);
       System.out.println("F " + this.F);

    }
}
