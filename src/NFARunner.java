import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class NFARunner {
    public static void main(String[] args) {
        for (String filename : args) {
            // ignore first 2 command line arguments
            if ((filename == "java") || (filename == "NFARunner")) {
                continue;
            }
            FileReader input;
            try {
                input = new FileReader(filename);
            } catch (FileNotFoundException e) {
                System.out.println("Error: File \"" + filename + "\" not found");
                continue;
            }
            System.out.println("===== NFAs from \"" + filename + "\" =====");
            Stack<NFA> nfaStack = new Stack<>();
            int c;
            int nfaNum = 1;
            try {
                while ((c = input.read()) != -1) {
                    if (c == '&') {
                        NFA nfa2 = nfaStack.pop();
                        NFA nfa1 = nfaStack.pop();
                        nfaStack.push(nfa1.concatenate(nfa2));
                    } else if (c == '|') {
                        NFA nfa2 = nfaStack.pop();
                        NFA nfa1 = nfaStack.pop();
                        nfaStack.push(nfa1.union(nfa2));
                    } else if (c == '*') {
                        nfaStack.peek().kleeneStar();
                    } else if (c == '\n') {
                        System.out.println("=== NFA " + nfaNum++ + " ===");
                        printNFA(nfaStack.pop());
                        nfaStack.clear();
                    } else {
                        nfaStack.push(new NFA((char) c));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading character in file \"" + filename + "\"");
                System.exit(1);
            }
        }
    }

    private static void printNFA(NFA nfa) {
        System.out.println("Start: q" + nfa.getStartState() +
                "\nAccept: q" + nfa.getAcceptState()
        );
        ArrayList<NFA.Transition> tList = nfa.getTList();
        for (NFA.Transition t : tList) {
            System.out.println("(q" +
                    t.getFromState() +
                    ", " +
                    t.getSymbol() +
                    ") -> q" +
                    t.getToState()
            );
        }
    }
}
