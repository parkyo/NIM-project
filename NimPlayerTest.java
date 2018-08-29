

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class NimPlayerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class NimPlayerTest
{
    private NimStack nimStack1;
    private NimStack nimStack2;
    private NimStack nimStack3;
    private NimPlayer nimPlaye1;
    /**
     * Default constructor for test class NimPlayerTest
     */
    public NimPlayerTest()
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
        nimStack1 = new NimStack(5);
        nimStack2 = new NimStack(5);
        nimStack3 = new NimStack(5);
        nimPlaye1 = new NimPlayer(nimStack1, nimStack2, nimStack3);
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
    public void testGetTotalChips()
    {
        NimStack nimStack1 = new NimStack(5);
        NimStack nimStack2 = new NimStack(5);
        NimStack nimStack3 = new NimStack(5);
        NimPlayer nimPlaye1 = new NimPlayer(nimStack1, nimStack2, nimStack3);
        assertEquals(15, nimPlaye1.getTotalChips());
    }
    @Test
    public void testALargerBEqualC()
    {
        NimPlayer nimPlaye1 = new NimPlayer(nimStack1, nimStack2, nimStack3);
        nimStack2.removeChips(1);
        nimPlaye1.takeOneFromLargest();
        assertEquals(4, nimStack1.getNumChips());
        assertEquals(5, nimStack3.getNumChips());
        assertEquals(4, nimStack2.getNumChips());
    }

    @Test
    public void testALargest()
    {
        NimPlayer nimPlaye1 = new NimPlayer(nimStack1, nimStack2, nimStack3);
        nimStack2.removeChips(1);
        nimStack3.removeChips(1);
        nimPlaye1.takeOneFromLargest();
        assertEquals(4, nimStack1.getNumChips());
        assertEquals(4, nimStack2.getNumChips());
        assertEquals(4, nimStack3.getNumChips());
    }

   

    @Test
    public void testCLargerA()
    {
        NimPlayer nimPlaye1 = new NimPlayer(nimStack1, nimStack2, nimStack3);
        nimStack2.removeChips(1);
        nimStack2.removeChips(1);
        nimStack1.removeChips(1);
        nimPlaye1.takeOneFromLargest();
        assertEquals(4, nimStack3.getNumChips());
        assertEquals(3, nimStack2.getNumChips());
        assertEquals(4, nimStack1.getNumChips());
    }

    @Test
    public void testBLargerCEqualA()
    {
        nimStack3.removeChips(1);
        nimPlaye1.takeOneFromLargest();
        assertEquals(4, nimStack2.getNumChips());
        assertEquals(5, nimStack1.getNumChips());
        assertEquals(4, nimStack3.getNumChips());
    }

    @Test
    public void testBLargest()
    {
        nimStack3.removeChips(1);
        nimStack1.removeChips(1);
        nimPlaye1.takeOneFromLargest();
        assertEquals(4, nimStack1.getNumChips());
        assertEquals(4, nimStack2.getNumChips());
        assertEquals(4, nimStack3.getNumChips());
    }

    @Test
    public void testCLargest()
    {
        nimStack1.removeChips(1);
        nimStack2.removeChips(1);
        nimPlaye1.takeOneFromLargest();
        assertEquals(4, nimStack1.getNumChips());
        assertEquals(4, nimStack2.getNumChips());
        assertEquals(4, nimStack3.getNumChips());
    }

    @Test
    public void testAllEquals()
    {
        nimPlaye1.takeOneFromLargest();
        assertEquals(4, nimStack3.getNumChips());
        assertEquals(5, nimStack1.getNumChips());
        assertEquals(5, nimStack2.getNumChips());
    }

    @Test
    public void test2ALargerThan5()
    {
        nimStack2.removeChips(1);
        nimStack3.removeChips(1);
        nimPlaye1.takeFiveFromAny();
        assertEquals(0, nimStack1.getNumChips());
        assertEquals(4, nimStack2.getNumChips());
        assertEquals(4, nimStack3.getNumChips());
    }

    @Test
    public void test2BLargerThan5()
    {
        nimStack3.removeChips(1);
        nimStack1.removeChips(1);
        nimPlaye1.takeFiveFromAny();
        assertEquals(0, nimStack2.getNumChips());
        assertEquals(4, nimStack1.getNumChips());
        assertEquals(4, nimStack3.getNumChips());
    }

    @Test
    public void test2CLargerThan5()
    {
        nimStack2.removeChips(1);
        nimStack1.removeChips(1);
        nimPlaye1.takeFiveFromAny();
        assertEquals(4, nimStack1.getNumChips());
        assertEquals(0, nimStack3.getNumChips());
        assertEquals(4, nimStack2.getNumChips());
    }

    @Test
    public void test2ALargerThanBEqualC()
    {
        nimStack2.removeChips(2);
        nimStack3.removeChips(1);
        nimStack1.removeChips(1);
        nimPlaye1.takeFiveFromAny();
        assertEquals(3, nimStack2.getNumChips());
        assertEquals(4, nimStack3.getNumChips());
        assertEquals(3, nimStack1.getNumChips());
    }

    @Test
    public void test2ALargest()
    {
        nimStack2.removeChips(2);
        nimStack3.removeChips(2);
        nimStack1.removeChips(1);
        nimPlaye1.takeFiveFromAny();
        assertEquals(3, nimStack1.getNumChips());
        assertEquals(3, nimStack3.getNumChips());
        assertEquals(3, nimStack2.getNumChips());
    }

    @Test
    public void test2CLargest()
    {
        nimStack2.removeChips(2);
        nimStack3.removeChips(1);
        nimStack1.removeChips(2);
        nimPlaye1.takeFiveFromAny();
        assertEquals(3, nimStack2.getNumChips());
        assertEquals(3, nimStack3.getNumChips());
        assertEquals(3, nimStack1.getNumChips());
    }

    @Test
    public void test2BLargerThanCEqualA()
    {
        nimStack2.removeChips(1);
        nimStack3.removeChips(2);
        nimStack1.removeChips(1);
        nimPlaye1.takeFiveFromAny();
        assertEquals(3, nimStack3.getNumChips());
        assertEquals(4, nimStack1.getNumChips());
        assertEquals(3, nimStack2.getNumChips());
    }

    @Test
    public void test2BLargest()
    {
        nimStack2.removeChips(1);
        nimStack3.removeChips(2);
        nimStack1.removeChips(2);
        nimPlaye1.takeFiveFromAny();
        assertEquals(3, nimStack2.getNumChips());
        assertEquals(3, nimStack3.getNumChips());
        assertEquals(3, nimStack1.getNumChips());
    }

    @Test
    public void test2CLargerThanBandA()
    {
        nimStack2.removeChips(2);
        nimStack3.removeChips(1);
        nimStack1.removeChips(2);
        nimPlaye1.takeFiveFromAny();
        assertEquals(3, nimStack1.getNumChips());
        assertEquals(3, nimStack3.getNumChips());
        assertEquals(3, nimStack2.getNumChips());
    }

    @Test
    public void test2AllEqual()
    {
        nimStack2.removeChips(1);
        nimStack1.removeChips(1);
        nimStack3.removeChips(1);
        nimPlaye1.takeFiveFromAny();
        assertEquals(4, nimStack1.getNumChips());
        assertEquals(3, nimStack3.getNumChips());
        assertEquals(4, nimStack2.getNumChips());
    }

    @Test
    public void test3AEven()
    {
        nimStack1.removeChips(1);
        nimPlaye1.takeHalfFromAny();
        assertEquals(2, nimStack1.getNumChips());
        assertEquals(5, nimStack3.getNumChips());
        assertEquals(5, nimStack2.getNumChips());
    }

    @Test
    public void test3AOdd()
    {
        nimPlaye1.takeHalfFromAny();
        assertEquals(5, nimStack2.getNumChips());
        assertEquals(5, nimStack3.getNumChips());
        assertEquals(2, nimStack1.getNumChips());
    }

    @Test
    public void test3BEven()
    {
        nimStack1.removeChips(5);
        nimStack2.removeChips(1);
        nimPlaye1.takeHalfFromAny();
        assertEquals(2, nimStack2.getNumChips());
        assertEquals(5, nimStack3.getNumChips());
        assertEquals(0, nimStack1.getNumChips());
    }

    @Test
    public void test3BOdd()
    {
        nimStack1.removeChips(5);
        nimPlaye1.takeHalfFromAny();
        assertEquals(0, nimStack1.getNumChips());
        assertEquals(2, nimStack2.getNumChips());
        assertEquals(5, nimStack3.getNumChips());
    }

    @Test
    public void test3CEven()
    {
        nimStack2.removeChips(5);
        nimStack3.removeChips(1);
        nimStack1.removeChips(5);
        nimPlaye1.takeHalfFromAny();
        assertEquals(0, nimStack2.getNumChips());
        assertEquals(2, nimStack3.getNumChips());
        assertEquals(0, nimStack1.getNumChips());
    }

    @Test
    public void test3COdd()
    {
        nimStack2.removeChips(5);
        nimStack1.removeChips(5);
        nimPlaye1.takeHalfFromAny();
        assertEquals(0, nimStack2.getNumChips());
        assertEquals(2, nimStack3.getNumChips());
        assertEquals(0, nimStack1.getNumChips());
    }

    @Test
    public void test3AllEqual()
    {
        nimStack2.removeChips(5);
        nimStack3.removeChips(5);
        nimStack1.removeChips(5);
        nimPlaye1.takeHalfFromAny();
        assertEquals(0, nimStack2.getNumChips());
        assertEquals(0, nimStack3.getNumChips());
        assertEquals(0, nimStack1.getNumChips());
    }
}






