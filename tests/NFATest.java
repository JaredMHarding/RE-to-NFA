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
        assertEquals(1,tList1.get(0).getFrom());
        assertEquals('a',tList1.get(0).getSymbol());
        assertEquals(2,tList1.get(0).getTo());
    }

    @Test
    public void testCopyConstructor() {
        NFA nfa2 = new NFA(nfa1);
        assertTrue(nfa1 != nfa2);
    }

    @Test
    public void testKleeneStarStructure() {
        nfa1.kleeneStar();
        tList1 = nfa1.getTransitions();
        assertEquals(3,tList1.size());
        assertEquals(3,nfa1.getStartState());
        assertEquals(3,nfa1.getEndState());
        assertEquals(1,tList1.get(0).getFrom());
        assertEquals('a',tList1.get(0).getSymbol());
        assertEquals(2,tList1.get(0).getTo());
        assertEquals(3,tList1.get(1).getFrom());
        assertEquals('E',tList1.get(1).getSymbol());
        assertEquals(1,tList1.get(1).getTo());
        assertEquals(2,tList1.get(2).getFrom());
        assertEquals('E',tList1.get(2).getSymbol());
        assertEquals(3,tList1.get(2).getTo());
    }

    @Test
    public void testConcatenationStructure() {
        NFA nfa2 = new NFA('b');
        NFA nfa3 = nfa1.concatenate(nfa2);
        // TODO: Finish writing this test
    }
}