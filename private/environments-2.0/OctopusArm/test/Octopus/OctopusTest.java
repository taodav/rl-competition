/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Octopus;

import Octopus.config.EnvSpec;
import Octopus.environment.Arm;
import Octopus.environment.Food;
import Octopus.environment.Mouth;
import Octopus.environment.Target;
import Octopus.protocol.TaskSpec;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import rlglue.types.Action;
import rlglue.types.Observation;
import rlglue.types.Random_seed_key;
import rlglue.types.Reward_observation;
import rlglue.types.State_key;

/**
 *
 * @author btanner
 */
public class OctopusTest {

    public OctopusTest() {
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
     * Test of main method, of class Octopus.
     */
    @Test
    public void main() {
        System.out.println("main");
        String[] args = null;
        Octopus.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initFromSpec method, of class Octopus.
     */
    @Test
    public void initFromSpec() {
        System.out.println("initFromSpec");
        EnvSpec spec = null;
        Octopus instance = new Octopus();
        instance.initFromSpec(spec);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of env_init method, of class Octopus.
     */
    @Test
    public void env_init() {
        System.out.println("env_init");
        Octopus instance = new Octopus();
        String expResult = "";
        String result = instance.env_init();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTaskSpec method, of class Octopus.
     */
    @Test
    public void getTaskSpec() {
        System.out.println("getTaskSpec");
        Octopus instance = new Octopus();
        TaskSpec expResult = null;
        TaskSpec result = instance.getTaskSpec();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of env_start method, of class Octopus.
     */
    @Test
    public void env_start() {
        System.out.println("env_start");
        Octopus instance = new Octopus();
        Observation expResult = null;
        Observation result = instance.env_start();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of env_step method, of class Octopus.
     */
    @Test
    public void env_step() {
        System.out.println("env_step");
        Action theAction = null;
        Octopus instance = new Octopus();
        Reward_observation expResult = null;
        Reward_observation result = instance.env_step(theAction);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getArm method, of class Octopus.
     */
    @Test
    public void getArm() {
        System.out.println("getArm");
        Octopus instance = new Octopus();
        Arm expResult = null;
        Arm result = instance.getArm();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFood method, of class Octopus.
     */
    @Test
    public void getFood() {
        System.out.println("getFood");
        Octopus instance = new Octopus();
        Set<Food> expResult = null;
        Set<Food> result = instance.getFood();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMouth method, of class Octopus.
     */
    @Test
    public void getMouth() {
        System.out.println("getMouth");
        Octopus instance = new Octopus();
        Mouth expResult = null;
        Mouth result = instance.getMouth();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTargets method, of class Octopus.
     */
    @Test
    public void getTargets() {
        System.out.println("getTargets");
        Octopus instance = new Octopus();
        Set<Target> expResult = null;
        Set<Target> result = instance.getTargets();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addObserver method, of class Octopus.
     */
    @Test
    public void addObserver() {
        System.out.println("addObserver");
        EnvironmentObserver o = null;
        Octopus instance = new Octopus();
        instance.addObserver(o);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of makeObservation method, of class Octopus.
     */
    @Test
    public void makeObservation() {
        System.out.println("makeObservation");
        Octopus instance = new Octopus();
        Observation expResult = null;
        Observation result = instance.makeObservation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of env_cleanup method, of class Octopus.
     */
    @Test
    public void env_cleanup() {
        System.out.println("env_cleanup");
        Octopus instance = new Octopus();
        instance.env_cleanup();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of env_set_state method, of class Octopus.
     */
    @Test
    public void env_set_state() {
        System.out.println("env_set_state");
        State_key arg0 = null;
        Octopus instance = new Octopus();
        instance.env_set_state(arg0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of env_set_random_seed method, of class Octopus.
     */
    @Test
    public void env_set_random_seed() {
        System.out.println("env_set_random_seed");
        Random_seed_key arg0 = null;
        Octopus instance = new Octopus();
        instance.env_set_random_seed(arg0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of env_get_state method, of class Octopus.
     */
    @Test
    public void env_get_state() {
        System.out.println("env_get_state");
        Octopus instance = new Octopus();
        State_key expResult = null;
        State_key result = instance.env_get_state();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of env_get_random_seed method, of class Octopus.
     */
    @Test
    public void env_get_random_seed() {
        System.out.println("env_get_random_seed");
        Octopus instance = new Octopus();
        Random_seed_key expResult = null;
        Random_seed_key result = instance.env_get_random_seed();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of env_message method, of class Octopus.
     */
    @Test
    public void env_message() {
        System.out.println("env_message");
        String arg0 = "";
        Octopus instance = new Octopus();
        String expResult = "";
        String result = instance.env_message(arg0);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}