package test;

import spiderweb.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * The test class SpiderWebIntegrationTest.
 *
 * @author Sebastian Cardona
 * @author Diego Cardenas
 * @version 1.0
 * @since 14/04/2024
 */
public class SpiderWebIntegrationTest {

    @Test
    public void IntegrationTest(){
        // Creacion del Spiderweb con unas telearañas iniciales de 7 y un radio de 500
        //En este caso se van a hacer unas pruebas paso a paso
        //Para verificar la funcionalidad de todos los metodos realizados
        //Probando que cada uno se ejecute en su totalidad y que no haya ningun fallo
        //Para dar por finalizado el test de integracion y tener un codigo optimo
        Spiderweb spiderweb = new Spiderweb(7, 500);
        assertTrue(spiderweb.ok());
        // Se añade 1 puentes normal
        spiderweb.addBridge("normal","green", 100, 1);
        assertTrue(spiderweb.ok());
        // Se añade 1 puente fijo
        spiderweb.addBridge("fixed","purple", 150, 2);
        assertTrue(spiderweb.ok());
        // Se añade 1 puente mobile
        spiderweb.addBridge("mobile","red", 200, 3);
        assertTrue(spiderweb.ok());
        // Se añade 1 puente transformer
        spiderweb.addBridge("transformer","blue", 250, 4);
        assertTrue(spiderweb.ok());
        //se añaade un puente weak
        spiderweb.addBridge("weak","yellow", 300, 5);
        assertTrue(spiderweb.ok());
        //se añade un spot normal
        spiderweb.addSpot("normal","green", 1);
        assertTrue(spiderweb.ok());
        //se añade un spot bouncy
        spiderweb.addSpot("bouncy","blue", 2);
        assertTrue(spiderweb.ok());
        //se añade un spot color
        spiderweb.addSpot("color","red", 3);
        assertTrue(spiderweb.ok());
        //se añade un spot killer
        spiderweb.addSpot("killer","yellow", 4);
        //se sienta la añana en 1
        spiderweb.spiderSit(1);
        //se pone la añana a caminar y tiene que terminar en 5 pero como tiene un puente mobile en 3
        //crea otro puente y al tener otro punete ahi se devulve y queda en 4
        spiderweb.spiderWalk(true);
        assertEquals(4, spiderweb.getCurrentStrand());
        //despues se devuelve para poder seguir y debe volver al centro y la volvemo a hcaer caminar
        // y como el bouncy se elimino, este mismo termina en el spot 4 en el cual hay un killer y la mata
        //depues la volvemos a sentar para reaparecer ña araña y que se devuelve a su posicion inicial
        spiderweb.spiderSit(1);
        spiderweb.spiderWalk(false);
        // al tener un spot killer en 4 la araña muere y se hace nula
        //eliminamos un puente, normal
        spiderweb.delBridge("green");
        assertTrue(spiderweb.ok());
        //Ahora vamos a ir por el puente 3 y como hay un puente en 2 el cual es bouncy la araña termina en 3 y cambia de color
        spiderweb.spiderSit(3);
        spiderweb.spiderWalk(true);
        assertTrue(spiderweb.ok());
        //devolvemos la araña al comienzo
        spiderweb.spiderWalk(false);
        //vamos a verificar la cantidad de puentes que hay
        assertEquals(4, spiderweb.bridges().length);
        //verificamos que en total hay 4 puentes lo concordado con los analisis anteriores
        //Verificamos los puentes que no se han usado
        String[] br = {"yellow"};
        assertArrayEquals(spiderweb.unusedBridges(), br);
        //vemos que el unico puente que no se ha usado es el yellow
        //vamos a añadir un strand
        spiderweb.addStrand();
        assertEquals(8,spiderweb.getStrands());
        //se añande un nuevo hilo y no nos proporciona ningun fallo
        //Ahora vamos a enlargar los hilos un 10% mas de lo que estan
        spiderweb.enlarge(10);
        assertEquals(550, spiderweb.getRadio());
        //vamos a eliminar el spot killer
        spiderweb.delSpot("yellow");
        //verificamos la cantidad de spots
        String[] brr = {"red", "green", "blue"};
        assertArrayEquals(spiderweb.spots(), brr);
        //Vemos que se elimino correctamente
        //vamos a buscar si existe el spot red
        assertEquals(3, spiderweb.spot("red"));
        //verificamos los spots que no se han usado
        String[] sp = {"red"};
        assertArrayEquals(spiderweb.reachablesSpots(), sp);
        //Vamos a reubicar un puente yellow
        spiderweb.relocateBridge("yellow", 7);
        //verificamos que el puente yellow se reubico correctamente
        assertTrue(spiderweb.ok());
        //verificamos el ultimo camino de la araña
        int[] lastPath = {2, 3, 4 ,5, 1};
        assertArrayEquals(lastPath, spiderweb.spiderLastPath());
        //verificamos ahora la cantidad de puentes existentes
        String[] bridges = {"red", "blue", "yellow", "purple"};
        assertArrayEquals(bridges, spiderweb.bridges());
        //Al final vemos que todo el spiderweb esta corrento probando todos los metodos haciendo que la araña camine y ejecute las acciones
        //Correspondientes a cada uno de los spots y los puentes

    }
}
