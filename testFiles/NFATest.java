import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class NFATest {

    private NFA nfa1;
    private ArrayList<NFA.Transition> tList1;

    @Before
    public void init() {
        nfa1 = new NFA('a');
        tList1 = nfa1.getTList();
    }

    @Test
    public void testSingleSymbolStructure() {
        assertEquals(1,tList1.size());
        assertEquals(1,nfa1.getStartState());
        assertEquals(2,nfa1.getAcceptState());
        assertEquals(1,tList1.get(0).getFromState());
        assertEquals('a',tList1.get(0).getSymbol());
        assertEquals(2,tList1.get(0).getToState());
    }

    @Test
    public void testCopyConstructor() {
        NFA nfa2 = new NFA(nfa1);
        ArrayList<NFA.Transition> tList2 = nfa2.getTList();

        assertTrue(nfa1 != nfa2);
        assertEquals(nfa1.getStartState(),nfa2.getStartState());
        assertEquals(nfa1.getAcceptState(),nfa2.getAcceptState());
        assertEquals(tList1.size(),tList2.size());
        assertEquals(tList1.get(0).getFromState(),tList2.get(0).getFromState());
        assertEquals(tList1.get(0).getSymbol(),tList2.get(0).getSymbol());
        assertEquals(tList1.get(0).getToState(),tList2.get(0).getToState());
    }

    @Test
    public void testKleeneStarStructure() {
        nfa1.kleeneStar();
        tList1 = nfa1.getTList();

        // nfa info correct
        assertEquals(3,nfa1.getStartState());
        assertEquals(3,nfa1.getAcceptState());
        assertEquals(4,nfa1.getNextStateID());

        assertEquals(3,tList1.size());
        // original transition from
        assertEquals(1,tList1.get(0).getFromState());
        assertEquals('a',tList1.get(0).getSymbol());
        assertEquals(2,tList1.get(0).getToState());
        // Epsilon transition from the new start/accept state to the old start
        assertEquals(3,tList1.get(1).getFromState());
        assertEquals('E',tList1.get(1).getSymbol());
        assertEquals(1,tList1.get(1).getToState());
        // Epsilon transition from the old accept to the new start/accept state
        assertEquals(2,tList1.get(2).getFromState());
        assertEquals('E',tList1.get(2).getSymbol());
        assertEquals(3,tList1.get(2).getToState());
    }

    @Test
    public void testConcatenationStructure() {
        NFA nfa2 = new NFA('b');
        NFA nfa3 = nfa1.concatenate(nfa2);
        ArrayList<NFA.Transition> tList3 = nfa3.getTList();

        // nfa info correct
        assertEquals(1,nfa3.getStartState());
        assertEquals(4,nfa3.getAcceptState());
        assertEquals(5,nfa3.getNextStateID());

        assertEquals(3,tList3.size());
        // original transitions from nfa1
        assertEquals(1,tList3.get(0).getFromState());
        assertEquals('a',tList3.get(0).getSymbol());
        assertEquals(2,tList3.get(0).getToState());
        // Epsilon transitions from nfa1's accept state to nfa2's start state
        assertEquals(2,tList3.get(1).getFromState());
        assertEquals('E',tList3.get(1).getSymbol());
        assertEquals(3,tList3.get(1).getToState());
        // original transitions from nfa2
        assertEquals(3,tList3.get(2).getFromState());
        assertEquals('b',tList3.get(2).getSymbol());
        assertEquals(4,tList3.get(2).getToState());
    }

    @Test
    public void testUnionStructure() {
        NFA nfa2 = new NFA('b');
        NFA nfa3 = nfa1.union(nfa2);
        ArrayList<NFA.Transition> tList3 = nfa3.getTList();

        // nfa info correct
        assertEquals(5,nfa3.getStartState());
        assertEquals(6,nfa3.getAcceptState());
        assertEquals(7,nfa3.getNextStateID());

        assertEquals(6,tList3.size());
        // Epsilon transitions from the new start state to the old start state
        assertEquals(5,tList3.get(0).getFromState());
        assertEquals('E',tList3.get(0).getSymbol());
        assertEquals(1,tList3.get(0).getToState());
        assertEquals(5,tList3.get(1).getFromState());
        assertEquals('E',tList3.get(1).getSymbol());
        assertEquals(3,tList3.get(1).getToState());
        // nfa1's transition(s)
        assertEquals(1,tList3.get(2).getFromState());
        assertEquals('a',tList3.get(2).getSymbol());
        assertEquals(2,tList3.get(2).getToState());
        // nfa2's transition(s)
        assertEquals(3,tList3.get(3).getFromState());
        assertEquals('b',tList3.get(3).getSymbol());
        assertEquals(4,tList3.get(3).getToState());
        // Epsilon transitions from the old accept states to the new accept state
        assertEquals(2,tList3.get(4).getFromState());
        assertEquals('E',tList3.get(4).getSymbol());
        assertEquals(6,tList3.get(4).getToState());
        assertEquals(4,tList3.get(5).getFromState());
        assertEquals('E',tList3.get(5).getSymbol());
        assertEquals(6,tList3.get(5).getToState());
    }
}