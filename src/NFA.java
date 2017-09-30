import java.util.ArrayList;

public class NFA {
    // instance variables
    private int startState;
    private int endState;
    private ArrayList<Transition> tList;
    private int nextStateID;

    /**
     * This creates a completely blank NFA
     */
    private NFA() {
        startState = 0;
        endState = 0;
        tList = new ArrayList<Transition>();
        nextStateID = 0;
    }

    /**
     * This creates a new NFA that accepts a single symbol
     * @param symbol the symbol the NFA will accept
     */
    public NFA(char symbol) {
        startState = 1;
        endState = 2;
        nextStateID = 3;
        tList = new ArrayList<Transition>();
        tList.add(new Transition(startState,symbol,endState));
    }

    /**
     * This creates a copy of an already existing nfa
     * @param nfa
     */
    public NFA(NFA nfa) {
        startState = nfa.startState;
        endState = nfa.endState;
        nextStateID = nfa.nextStateID;
        tList = nfa.getTransitions();
    }

    public int getStartState() {
        return startState;
    }

    public int getEndState() {
        return endState;
    }

    public ArrayList<Transition> getTransitions() {
        return new ArrayList<Transition>(tList);
    }

    public int getNextStateID() {
        return nextStateID;
    }

    public void kleeneStar() {
        int newState = nextStateID++;
        Transition t1 = new Transition(newState,'E',startState);
        tList.add(t1);
        Transition t2 = new Transition(endState,'E',newState);
        tList.add(t2);
        startState = endState = newState;
    }

    /**
     * Concatenates 2 NFAs, this will not change the original NFAs
     * @param nfa2 the NFA to concatenate to "this"
     * @return a new NFA that represents the connected NFAs
     */
    public NFA concatenate(NFA nfa2) {
        NFA nfa3 = new NFA();
        int inc = nextStateID - 1;
        // relabel the states from nfa2
        ArrayList<Transition> tList2 = nfa2.getTransitions();
        for (Transition t : tList2) {
            t.fromState += inc;
            t.toState += inc;
        }
        // set up nfa3
        nfa3.startState = startState;
        nfa3.endState = nfa2.endState + inc;
        nfa3.nextStateID = nfa2.nextStateID + inc;
        nfa3.tList.addAll(tList);
        nfa3.tList.add(new Transition(endState,'E',nfa2.startState+inc));
        nfa3.tList.addAll(tList2);
        return nfa3;
    }

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
