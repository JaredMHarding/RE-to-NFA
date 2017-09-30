import java.util.ArrayList;

public class NFA {
    // instance variables
    private int startState;
    private int endState;
    private ArrayList<Transition> tList;
    private int nextStateID;

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
        startState = nfa.getStartState();
        endState = nfa.getEndState();
        nextStateID = nfa.getNextStateID();
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

    public NFA concatenate(NFA nfa2) {
        NFA nfa3 = new NFA(this);
        return nfa3;
    }

    public class Transition {
        private int from;
        private char symbol;
        private int to;

        private Transition(int from, char symbol, int to) {
            this.from = from;
            this.symbol = symbol;
            this.to = to;
        }

        public int getFrom() {
            return from;
        }

        public char getSymbol() {
            return symbol;
        }

        public int getTo() {
            return to;
        }
    }
}
