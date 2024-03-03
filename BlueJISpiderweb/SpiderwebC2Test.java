import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * The test class SpiderwebC2Test.
 *
 * @author  Diego Cardenas - Sebastian Cardona
 * @version 1.0 - 02/03/2024
 */
public class SpiderwebC2Test {
    //Las pruebas se van a hacer para un Spiderweb de 7 hilos y un radio de 500
    private Spiderweb spiderweb = new Spiderweb(7, 500);

    @BeforeEach
    public void before() {
        //Añadir Puntos
        spiderweb.addSpot("red", 1);
        spiderweb.addSpot("blue", 5);
        //Añadir Puentes
        spiderweb.addBridge("green", 100, 1);
        spiderweb.addBridge("blue", 300, 4);
    }

    @Test
    public void accordingCCShouldAddSpot() {
        spiderweb.addSpot("green", 3);
        assertTrue(spiderweb.ok());
        String[] valorDevolver = {"red", "green", "blue"};
        assertArrayEquals(spiderweb.spots(), valorDevolver);
    }

    @Test
    public void accordingCCShouldNotAddSpot() {
        spiderweb.addSpot("red", 1);
        spiderweb.addSpot("red", 1);
        assertFalse(spiderweb.ok());
    }

    @Test
    public void accordingCCShouldAddStrand() {
        spiderweb.addStrand();
        assertEquals(spiderweb.getStrands(), 8);
    }

    @Test
    public void accordingCCShouldNotAddStrand() {
        spiderweb.addStrand();
        assertNotEquals(spiderweb.getStrands(), 9);
    }

    @Test
    public void accordingCCShouldEnlarge(){
        spiderweb.enlarge(10);
        assertEquals(spiderweb.getRadio(), 550);
    }

    @Test
    public void accordingCCShouldNotEnlarge(){
        spiderweb.enlarge(10);
        assertNotEquals(spiderweb.getRadio(), 605);
    }

    @Test
    public void accordingCCShouldaddBridge(){
        spiderweb.addBridge("red", 100, 1);
        assertTrue(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulNotdaddBridge(){
        spiderweb.addBridge("red", 200, 1);
        spiderweb.addBridge("red", 200, 1);
        assertFalse(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulRelocateBride(){
        spiderweb.addBridge("red", 200, 5);
        spiderweb.relocateBridge("red", 200);
        assertTrue(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulNotRelocateBride(){
        spiderweb.addBridge("red", 200, 1);
        spiderweb.relocateBridge("blue", 700);
        assertFalse(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulDelBride(){
        spiderweb.addBridge("red", 200, 1);
        spiderweb.delBridge("red");
        assertTrue(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulNotDelBride(){
        spiderweb.addBridge("red", 200, 1);
        spiderweb.delBridge("red");
        spiderweb.delBridge("red");
        assertFalse(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulDelSpot(){
        spiderweb.delSpot("red");
        assertTrue(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulNotDelSpot(){
        spiderweb.delSpot("green");
        assertFalse(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulSpiderSit(){
        spiderweb.spiderSit(1);
        assertTrue(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulNotSpiderSit(){
        spiderweb.spiderSit(8);
        assertFalse(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulSpiderWalk(){
        spiderweb.spiderSit(1);
        spiderweb.spiderWalk(true);
        assertTrue(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulNotSpiderWalk(){
        spiderweb.spiderWalk(true);
        assertFalse(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulSpiderLastPath(){
        spiderweb.addBridge("red", 200, 2);
        spiderweb.spiderSit(1);
        spiderweb.spiderWalk(true);
        int[]   valorDevolver = {2, 3, 1};
        assertArrayEquals(spiderweb.spiderLastPath(), valorDevolver);
    }

    @Test
    public void accordingCCShoulNotSpiderLastPath(){
        //Realizar
    }

    @Test
    public void accordingCCShoulBridges(){
        String[] valorDevolver = {"green", "blue"};
        assertArrayEquals(spiderweb.bridges(), valorDevolver);
        spiderweb.addBridge("red", 200, 1);
        String[] valorDevolver2 = {"red", "green", "blue"};
        assertArrayEquals(spiderweb.bridges(), valorDevolver2);
    }

    @Test
    public void accordingCCShoulNotBridges(){
        String[] valorDevolver = {"green", "blue"};
        assertArrayEquals(spiderweb.bridges(), valorDevolver);
        spiderweb.addBridge("red", 200, 1);
        String[] valorDevolver2 = {"green", "blue", "red"};
        assertNotEquals(Arrays.asList(spiderweb.bridges()), Arrays.asList(valorDevolver2));
    }

    @Test
    public void accordingCCShoulBridge(){
        int[] valorDevolver = {1, 2};
        spiderweb.addBridge("red", 200, 1);
        assertArrayEquals(spiderweb.bridge("red"), valorDevolver);
        int[] valorDevolver2 = {4, 5};
        assertArrayEquals(spiderweb.bridge("blue"), valorDevolver2);
    }

    @Test
    public void accordingCCShoulNotBridge(){
        int[] valorDevolver = {1, 2};
        spiderweb.addBridge("red", 2, 1);
        assertArrayEquals(spiderweb.bridge("red"), valorDevolver);
        int[] valorDevolver2 = {4, 5};
        assertArrayEquals(spiderweb.bridge("blue"), valorDevolver2);
    }

    @Test
    public void accordingCCShoulSpots(){
        String[] valorDevolver = {"red", "blue"};
        assertArrayEquals(spiderweb.spots(), valorDevolver);
        spiderweb.addSpot("green", 1);
        String[] valorDevolver2 = {"red", "green", "blue"};
        assertArrayEquals(spiderweb.spots(), valorDevolver2);
    }

    @Test
    public void accordingCCShoulNotSpots(){
        String[] valorDevolver = {"red"};
        assertNotEquals(Arrays.asList(spiderweb.spots()), Arrays.asList(valorDevolver));
    }

    @Test
    public void accordingCCShoulSpot(){
        int valorDevolver = 2;
        spiderweb.addSpot("green", 2);
        assertEquals(spiderweb.spot("green"), valorDevolver);
        int valorDevolver2 = 1;
        assertEquals(spiderweb.spot("red"), valorDevolver2);
    }

    @Test
    public void accordingCCShoulNotSpot(){
        int valorDevolver = 3;
        spiderweb.addSpot("green", 2);
        assertNotEquals(spiderweb.spot("green"), valorDevolver);
        int valorDevolver2 = 8;
        assertNotEquals(spiderweb.spot("red"), valorDevolver2);
    }

    @Test
    public void accordingCCShoulUnusedBridge(){
        spiderweb.addBridge("red", 150, 1);
        spiderweb.spiderSit(1);
        spiderweb.spiderWalk(true);
        String[] valorDevolver = {"red", "blue"};
        assertArrayEquals(spiderweb.unusedBrisges(), valorDevolver);
    }

    @Test
    public void accordingCCShoulNotUnusedBridge(){
        spiderweb.addBridge("red", 150, 1);
        spiderweb.spiderSit(1);
        spiderweb.spiderWalk(true);
        String[] valorDevolver = {"red", "green", "blue"};
        assertNotEquals(Arrays.asList(spiderweb.unusedBrisges()), Arrays.asList(valorDevolver));
    }

    @Test
    public void accordingCCShoulMakeVisible(){
        spiderweb.makeVisible();
        assertTrue(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulNotMakeVisible(){
        spiderweb.makeVisible();
        spiderweb.makeVisible();
        assertFalse(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulMakeInvisible(){
        spiderweb.makeVisible();
        spiderweb.makeInvisible();
        assertTrue(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulNotMakeInvisible(){
        spiderweb.makeInvisible();
        spiderweb.makeInvisible();
        assertFalse(spiderweb.ok());
    }

    @Test
    public void accordingCCShoulFinish(){
        spiderweb.finish();
        assertTrue(spiderweb.ok());
    }


}