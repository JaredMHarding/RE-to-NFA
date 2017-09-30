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
        tList1 = nfa1.getTransitions();
    }

    @Test
    public void testSingleSymbolStructure() {
        assertEquals(1,tList1.size());
        assertEquals(1,nfa1.getStartState());
        assertEquals(2,nfa1.getEndState());
        assertEquals(1,tList1.get(0).getFromState());
        assertEquals('a',tList1.get(0).getSymbol());
        assertEquals(2,tList1.get(0).getToState());
    }

    @Test
    public void testCopyConstructor() {
        NFA nfa2 = new NFA(nfa1);
        ArrayList<NFA.Transition> tList2 = nfa2.getTransitions();

        assertTrue(nfa1 != nfa2);
        assertEquals(nfa1.getStartState(),nfa2.getStartState());
        assertEquals(nfa1.getEndState(),nfa2.getEndState());
        assertEquals(tList1.size(),tList2.size());
        assertEquals(tList1.get(0).getFromState(),tList2.get(0).getFromState());
        assertEquals(tList1.get(0).getSymbol(),tList2.get(0).getSymbol());
        assertEquals(tList1.get(0).getToState(),tList2.get(0).getToState());
    }

    @Test
    public void testKleeneStarStructure() {
        nfa1.kleeneStar();
        tList1 = nfa1.getTransitions();

        assertEquals(3,nfa1.getStartState());
        assertEquals(3,nfa1.getEndState());
        assertEquals(4,nfa1.getNextStateID());
        assertEquals(3,tList1.size());
        assertEquals(1,tList1.get(0).getFromState());
        assertEquals('a',tList1.get(0).getSymbol());
        assertEquals(2,tList1.get(0).getToState());
        assertEquals(3,tList1.get(1).getFromState());
        assertEquals('E',tList1.get(1).getSymbol());
        assertEquals(1,tList1.get(1).getToState());
        assertEquals(2,tList1.get(2).getFromState());
        assertEquals('E',tList1.get(2).getSymbol());
        assertEquals(3,tList1.get(2).getToState());
    }

    @Test
    public void testConcatenationStructure() {
        NFA nfa2 = new NFA('b');
        NFA nfa3 = nfa1.concatenate(nfa2);
        ArrayList<NFA.Transition> tList3 = nfa3.getTransitions();

        assertEquals(1,nfa3.getStartState());
        assertEquals(4,nfa3.getEndState());
        assertEquals(5,nfa3.getNextStateID());
        assertEquals(3,tList3.size());
        assertEquals(1,tList3.get(0).getFromState());
        assertEquals('a',tList3.get(0).getSymbol());
        assertEquals(2,tList3.get(0).getToState());
        assertEquals(2,tList3.get(1).getFromState());
        assertEquals('E',tList3.get(1).getSymbol());
        assertEquals(3,tList3.get(1).getToState());
        assertEquals(3,tList3.get(2).getFromState());
        assertEquals('b',tList3.get(2).getSymbol());
        assertEquals(4,tList3.get(2).getToState());
    }
}