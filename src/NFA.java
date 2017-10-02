import java.util.ArrayList;

public class NFA {
    // instance variables
    private int startState;
    private int acceptState;
    private ArrayList<Transition> tList;
    private int nextStateID;

    /**
     * This creates a completely blank NFA
     * Private because this is only for internal class use
     */
    private NFA() {
        startState = 0;
        acceptState = 0;
        tList = new ArrayList<Transition>();
        nextStateID = 0;
    }

    /**
     * This creates a new NFA that accepts a single symbol
     * @param symbol the symbol the NFA will accept
     */
    public NFA(char symbol) {
        startState = 1;
        acceptState = 2;
        nextStateID = 3;
        tList = new ArrayList<Transition>();
        tList.add(new Transition(startState,symbol, acceptState));
    }

    /**
     * Creates a copy of an already existing nfa
     * @param nfa
     */
    public NFA(NFA nfa) {
        startState = nfa.startState;
        acceptState = nfa.acceptState;
        nextStateID = nfa.nextStateID;
        tList = nfa.getTList();
    }

    public int getStartState() {
        return startState;
    }

    public int getAcceptState() {
        return acceptState;
    }

    public ArrayList<Transition> getTList() {
        return new ArrayList<Transition>(tList);
    }

    public int getNextStateID() {
        return nextStateID;
    }

    /**
     * Changes the nfa to accept the kleene star of the nfa's language
     */
    public void kleeneStar() {
        int newState = nextStateID++;
        Transition t1 = new Transition(newState,'E',startState);
        tList.add(t1);
        Transition t2 = new Transition(acceptState,'E',newState);
        tList.add(t2);
        startState = acceptState = newState;
    }

    /**
     * Concatenates 2 NFAs, this will not change the original NFAs
     * @param nfa2 the NFA to concatenate to "this"
     * @return a new NFA that represents the connected NFAs
     */
    public NFA concatenate(NFA nfa2) {
        NFA nfa3 = new NFA();
        int inc = nextStateID - 1;
        ArrayList<Transition> tList2 = incrementStates(nfa2,inc);
        // set up nfa3
        nfa3.startState = startState;
        nfa3.acceptState = nfa2.acceptState + inc;
        nfa3.nextStateID = nfa2.nextStateID + inc;
        nfa3.tList.addAll(tList);
        nfa3.tList.add(new Transition(acceptState,'E',nfa2.startState+inc));
        nfa3.tList.addAll(tList2);
        return nfa3;
    }

    public NFA union(NFA nfa2) {
        NFA nfa3 = new NFA();
        int inc = nextStateID - 1;
        ArrayList<Transition> tList2 = incrementStates(nfa2,inc);
        // set up nfa3
        nfa3.startState = nfa2.nextStateID + inc;
        nfa3.acceptState = nfa3.startState + 1;
        nfa3.nextStateID = nfa3.acceptState + 1;
        // add the transitions
        nfa3.tList.add(new Transition(nfa3.startState,'E',startState));
        nfa3.tList.add(new Transition(nfa3.startState,'E',nfa2.startState+inc));
        nfa3.tList.addAll(tList);
        nfa3.tList.addAll(tList2);
        nfa3.tList.add(new Transition(acceptState,'E',nfa3.acceptState));
        nfa3.tList.add(new Transition(nfa2.acceptState+inc,'E',nfa3.acceptState));
        return nfa3;
    }

    /**
     * Increments all of the state numbers from an nfa's transition list
     * @param nfa the nfa from which to get the transition list of
     * @param amount the amount to increment by
     * @return a new ArrayList of Transitions with the states incremented
     */
    private ArrayList<Transition> incrementStates(NFA nfa, int amount) {
        ArrayList<Transition> incTList = nfa.getTList();
        for (Transition t : incTList) {
            t.fromState += amount;
            t.toState += amount;
        }
        return incTList;
    }

    /**
     * Nested Transition class, data structure which holds info about transitions
     */
    public class Transition {
        private int fromState;
        private char symbol;
        private int toState;

        private Transition(int fromState, char symbol, int to) {
            this.fromState = fromState;
            this.symbol = symbol;
            this.toState = to;
        }

        public int getFromState() {
            return fromState;
        }

        public char getSymbol() {
            return symbol;
        }

        public int getToState() {
            return toState;
        }
    }
}
