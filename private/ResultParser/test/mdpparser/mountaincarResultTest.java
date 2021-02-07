/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mdpparser;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mradkie
 */
public class mountaincarResultTest {

    public mountaincarResultTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getScoreAt method, of class mountaincarResult.
     */
    @Test
    public void testGetScoreAt() {
        System.out.println("getScoreAt");
        double percent = 25.0;
        mountaincarResult instance = new mountaincarResult(new File("../../results/mc.results"));
        double expResult = 250.0;
        double result = instance.getScoreAt(25);
        System.out.println("Percent: "+25+" Score: "+result);
        assertEquals(expResult, result);
        expResult = 500;
        percent = 50.0;
        result = instance.getScoreAt(50);
        System.out.println("Percent: "+percent+" Score: "+result);
        assertEquals(expResult, result);
        expResult = 750;
        percent = 75.0;
        result = instance.getScoreAt(75);
        System.out.println("Percent: "+percent+" Score: "+result);
        assertEquals(expResult, result);  
        expResult = 1000;
        percent = 100.0;
        result = instance.getScoreAt(100);
        System.out.println("Percent: "+percent+" Score: "+result);
        assertEquals(expResult, result);
        //try some harder ones
        expResult = 15;
        percent = 1.5;
        result = instance.getScoreAt(1.5);
        System.out.println("Percent: "+percent+" Score: "+result);
        assertEquals(expResult, result);
        expResult = 234;
        percent =23.4;
        result = instance.getScoreAt(23.4);
        System.out.println("Percent: "+percent+" Score: "+result);
        assertEquals(expResult, result);
        
        expResult = 234.5;
        percent = 23.45;
        result = instance.getScoreAt(23.45);
        System.out.println("Percent: "+percent+" Score: "+result);
        assertEquals(expResult, result);
        expResult = 23.45;
        percent = 2.345;
        result = instance.getScoreAt(2.345);
        System.out.println("Percent: "+percent+" Score: "+result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

}