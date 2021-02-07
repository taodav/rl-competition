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
public class helicopterResultTest {

    public helicopterResultTest() {
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
     * Test of getScoreAt method, of class helicopterResult.
     */
    @Test
    public void testGetScoreAt() {
        System.out.println("getScoreAt");

        double percent = 0.0;
        helicopterResult instance = new helicopterResult(new File("../../results/heli.results"));
        double expResult = 0.0;
        double result = instance.getScoreAt(percent);
        for(int i=0;i<=100;i+=10){
            System.out.println("i:" + i+ " reward: " + instance.getScoreAt(i));
        }
        
        // TODO review the generated test code and remove the default call to fail.
        

    }

}