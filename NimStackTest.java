

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class NimStackTest.
 *
 * @author  (HaHoang)
 * @version (03/19/2018)
 */
public class NimStackTest
{
    /**
     * Default constructor for test class NimStackTest
     */
    public NimStackTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    @Test
    public void testSizeL10()
    {
        NimStack nimStack1 = new NimStack(11);
        assertEquals(10, nimStack1.getNumChips());
    }
    @Test
    public void testSizeST10LT0()
    {
        NimStack nimStack1 = new NimStack(5);
        assertEquals(5, nimStack1.getNumChips());
    }


    @Test
    public void testSizeLT0()
    {
        NimStack nimStack1 = new NimStack(-5);
        assertEquals(0, nimStack1.getNumChips());
    }

    @Test
    public void testGetSize()
    {
        NimStack nimStack1 = new NimStack(5);
        assertEquals(5, nimStack1.getNumChips());
    }

    @Test
    public void testRemoveSize0()
    {
        NimStack nimStack1 = new NimStack(0);
        nimStack1.removeChips(10);
        assertEquals(0, nimStack1.getNumChips());
    }

    @Test
    public void testRemoveNumST1()
    {
        NimStack nimStack1 = new NimStack(10);
        nimStack1.removeChips(0);
        assertEquals(9, nimStack1.getNumChips());
    }

    @Test
    public void testRemoveLTSize()
    {
        NimStack nimStack1 = new NimStack(5);
        nimStack1.removeChips(7);
        assertEquals(0, nimStack1.getNumChips());
    }

    @Test
    public void testRemoveNormal()
    {
        NimStack nimStack1 = new NimStack(10);
        nimStack1.removeChips(5);
        assertEquals(5, nimStack1.getNumChips());
    }
}











