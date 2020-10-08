import java.util.HashMap;
import java.util.LinkedList;

/**This class defines an Object of type State. A state is a single state in an NFA
 * The state contains A string that represents the name of the state (q0,q1,q2...), and this
 * name can be changed as necessary to update the associated number.
 * The state also contains a Hashmap that represents the transitions from this state to other states.
 * @author JosephBoothby
 */
public class State {
    //initialize instance variables
    int name;    //name of this tate
    HashMap< Character, LinkedList<State>> transition= new HashMap<Character, LinkedList<State>>(); //hashmap that contains transitions
    char[] Sigma = new char[]{'a','b','c','d','e','E'};     //char array that contains valid state transition chars

    /**The constructor for state does nothing at all
     * @author JosephBoothby
     */
    State(){}

    /**The add transition method is used to add a transition from this state
     * @param c character to transition on
     * @param s State object to be reached by applying transition Character c
     * @author JosephBoothby
     */
    public void addTransition( Character c, State s) {

        LinkedList<State> temp;
        //If the state doesn't have a transition for this character create an empty linked list
        if (!transition.containsKey(c)) {
            temp = new LinkedList<State>();
        }
        //if the state already had a transition on this character, use that linked list
        else{
            temp = transition.get(c);
        }
        //add the new state to the linked list
        temp.add(s);
        //put the linked list into the map for the transition
        transition.remove(c);
        transition.put(c, temp);
    }

    /**This method overrides the default toString and outputs the name of the state and the contents
     * of the hashmap transitions. This will be a list of transitions.
     * @return s String representation of the state
     */
    public String toString() {

        String s = new String();
        if( transition.isEmpty()){
            return ("(q" + this.name + ")");
        }

        //iterate over all the characters contained in Sigma (the alphabet for this language)
        for (char value : Sigma) {
            //if the key exists in the hashmap, then there is a valid transition on that character
            if (transition.containsKey(value)) {
                //append the name of the state, the character that causes the transition to s
                s += ("(q" + this.name + "," + value + ") --> ");
                //iterate over the States being transitioned to, and append them to s
                for( State temp : transition.get(value)){
                    s += ("q" + temp.name + " ");
                }
            }
        }
        return s;
    }
}
