import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.HashMap;
import java.util.Map;

/**
 * Aqui se hará el simulador del Spiderweb
 * 
 * @author Diego Cardenas y Sebastian Cardona 
 * @version 1.0
 */
public class Spiderweb{
    private boolean isVisible;  //Es visible la figura
    private int strands;        //Cantidad de telarañas
    private int strand;         //A la Telaraña que se debe mover
    private int radio;          //Radio des las telarañas
    private Thread hilos;       // Objeto telaraña sin puentes
    private ArrayList<Linea> listaThreads;   //Lista de las lineas 
    private Spider spider;//Araña
    private double angulo;//Angulo 
    private HashMap<String,ArrayList<Bridge>> bridges;
    private static final int centroImagenX = 640;
    private static final int centroImagenY = 360;
    private boolean SpiderSit;
    private HashMap<String,Circle> spots;
    private boolean isOk = true;
    private HashMap<Integer,ArrayList<Bridge>> puentesPorLineas;
    private int[] spidertLastPath;
    private ArrayList<Linea> spiderLastRoute;
    
    
    /**
     * Constructor de la clase Spiderweb.
     * Crea una telaraña con un número específico de hilos y un radio dado.
     * La telaraña se inicializa con los puntos de inicio de los hilos y la arañita.
     *
     * @param strands El número de hilos que formarán la telaraña.
     * @param radio El radio de la telaraña desde el centro.
     */
    public Spiderweb(int strands, int radio){
        this.hilos = new Thread(strands, radio);
        this.angulo = hilos.getAngle();
        this.listaThreads = hilos.getThreads();   
        this.bridges = new HashMap<String,ArrayList<Bridge>>();
        this.spots = new HashMap<String,Circle>();
        this.radio = radio;
        this.strands = strands;
        this.spider = new Spider();
        spider.moveTo(Spiderweb.getPoscenterImage()[0]-spider.getPosx(),Spiderweb.getPoscenterImage()[1]-spider.getPosy());
        this.SpiderSit = false;
        this.puentesPorLineas = new HashMap<Integer,ArrayList<Bridge>>();
        spidertLastPath = new int[2];
        this.spiderLastRoute = new ArrayList<Linea>();
    }
    
    /**
     * make visible all the simulator
     */
    public void makeVisible(){
        if(!isVisible){
            hilos.makeVisible();
            spider.makeVisible();
            for(String color: bridges.keySet()){
                for(Bridge bridge : bridges.get(color)){
                    bridge.makeVisible();
                }
            }  
            for(String color: spots.keySet()){
                spots.get(color).makeVisible();
            }  
            isVisible = true;
            visibleRoute();
        }
    }
    
    /**
     * make invisible all the simulator
     */
    public void makeInvisible(){
        if(isVisible){
            hilos.makeInvisible();
            spider.makeInvisible();
            for(String color: bridges.keySet()){
                for(Bridge bridge : bridges.get(color)){
                    bridge.makeInvisible();
                }
            }   
            for(String color: spots.keySet()){
                spots.get(color).makeInvisible();
            } 
            isVisible = false;
            invisibleRoute();
        }
    }
    
    // Crea un puente entre dos hebras.
    private Bridge createBridge(int firstStrand, int distance, String color){
        double angleFirstStrand = (firstStrand-1)*angulo;
        double angleSecondStrand = (firstStrand)*angulo;
        float posx1 = Math.round(distance * Math.cos(Math.toRadians(angleFirstStrand)));
        float posy1 = Math.round(distance * Math.sin(Math.toRadians(angleFirstStrand)));
        float posx2 = Math.round(distance * Math.cos(Math.toRadians(angleSecondStrand)));
        float posy2 = Math.round(distance * Math.sin(Math.toRadians(angleSecondStrand)));
        Bridge bridge = new Bridge(centroImagenX+posx1,centroImagenY-posy1,centroImagenX+posx2,centroImagenY-posy2);
        bridge.changeColor(color);
        bridge.makeVisible();
        bridge.setInitStrand(firstStrand);
        return bridge;
    }
    
    
     // Crea un puente entre dos hebras.
    private Bridge createBridge2(int firstStrand, int distance, String color){
        double angleFirstStrand = (firstStrand-1)*angulo;
        double angleSecondStrand = (firstStrand)*angulo;
        float posx1 = Math.round(distance * Math.cos(Math.toRadians(angleFirstStrand)));
        float posy1 = Math.round(distance * Math.sin(Math.toRadians(angleFirstStrand)));
        float posx2 = Math.round(distance * Math.cos(Math.toRadians(angleSecondStrand)));
        float posy2 = Math.round(distance * Math.sin(Math.toRadians(angleSecondStrand)));
        Bridge bridge = new Bridge(centroImagenX + posx2, centroImagenY - posy2, centroImagenX + posx1, centroImagenY - posy1);
        bridge.setInitStrand(firstStrand);
        bridge.changeColor(color);
        return bridge;
    }     
    
    /**
     * Adds a bridge between two points on the screen, given a color, a distance, and a starting coordinate.
     * @param color the color of bridge
     * @param distance The distance from the center point where the bridge will be located.
     * @param firstStrand initial thread where the bridge will be located
     **/
    public void addBridge(String color, int distance, int firstStrand){
        if(distance > radio){
            if(isVisible){JOptionPane.showMessageDialog(null, "No se puede poder un puente a una distancia mayor del limite de las arañas");}isOk = false;
        }else if(firstStrand > strands){
            if(isVisible){JOptionPane.showMessageDialog(null, "No se puede poder un puente en un hilo que no existe");}isOk = false;
        }else if(bridges.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "Ya existe un puente de dicho color");}isOk = false;
        }else{
            Bridge bridge = createBridge(firstStrand, distance, color);
            Bridge bridge2 = createBridge2(firstStrand, distance, color);
            addBridgeInStrand(bridge,firstStrand-1);
            bridge.setDirection("izq");
            addBridgeInStrand(bridge2,firstStrand);
            bridge2.setDirection("der");
            ArrayList<Bridge> puente = new ArrayList<Bridge>();
            puente.add(bridge);
            puente.add(bridge2);
            bridges.put(color,puente);
            isOk = true;
        } 
    }
    
    //Añade los puentes a el hilo correpondiente en un hashMap
    private void addBridgeInStrand(Bridge puente, int strandi){
        if(strandi == strands){
            strandi = 0;
        }
        if(puentesPorLineas.containsKey(strandi)){
            puentesPorLineas.get(strandi).add(puente);
        }else{
            ArrayList<Bridge> puent = new ArrayList<Bridge>();
            puent.add(puente);
            puentesPorLineas.put(strandi,puent);
        }
    }
    
    public static int[] getPoscenterImage(){
        int[] listaPoscionesCentroimagen = {centroImagenX, centroImagenY};
        return listaPoscionesCentroimagen;
    }
    
    /**
     * Relocate bridges of a specific color a given distance from the center of the web.
     *
     * @param color The color of the bridge to move.
     * @param distance The new distance from the center of the circle to the bridge attachment point.
    */
    public void relocateBridge(String color, int distance){
        if(distance > radio){
            if(isVisible){JOptionPane.showMessageDialog(null, "No se puede reubicar los puentes a una distancia mayor del limite de las arañas");
            }isOk = false;
        }else if(!bridges.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "No hay ningun puente con ese color");
            }isOk = false;
        }else{
            for(Bridge bridge : bridges.get(color)){
                double angleFirstStrand = (bridge.getInitStrand()-1)*angulo;
                double angleSecondStrand = (bridge.getInitStrand())*angulo;
                float posx1 = Math.round(distance * Math.cos(Math.toRadians(angleFirstStrand)));
                float posy1 = Math.round(distance * Math.sin(Math.toRadians(angleFirstStrand)));
                float posx2 = Math.round(distance * Math.cos(Math.toRadians(angleSecondStrand)));
                float posy2 = Math.round(distance * Math.sin(Math.toRadians(angleSecondStrand)));
                if(bridge.getDirection().equals("izq")){
                    bridge.newPoints(centroImagenX+posx1,centroImagenY-posy1,centroImagenX+posx2,centroImagenY-posy2);
                }else{
                    bridge.newPoints(centroImagenX+posx2,centroImagenY-posy2,centroImagenX+posx1,centroImagenY-posy1);
                }
                
            }isOk = true;
        }
    }
    
    /**
     * Eliminate spider web bridges based on their color.
     *
     * @param color The color of the bridge to remove.
     */
    public void delBridge(String color){
        if(!bridges.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "No existe ningun puente con dicho color");
            }isOk = false;
        }else{
            for(Bridge bridge : bridges.get(color)){
                bridge.makeInvisible();
                int intStrand;
                if(bridge.getDirection().equals("izq")){
                    intStrand = bridge.getInitStrand()-1;
                }else{
                    intStrand = bridge.getInitStrand();
                    if(intStrand == strands){intStrand = 0;}
                } 
                ArrayList<Bridge> hilo= puentesPorLineas.get(intStrand);
                hilo.remove(bridge);
            }
            bridges.remove(color);
            isOk = true;
        }
    }
    
    /**
     * adds a favorite place for the spider to sleep, represented by a circle
     * @param color the spot color
     * @param Strand where the spot will be
    */
    public void addSpot(String color, int strand){
        if(strand > strands){
            if(isVisible){JOptionPane.showMessageDialog(null, "No puedes poner un spot en un hilo inexistente");
            }isOk = false;
        }else if(spots.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "Este spot ya esxite");
            }isOk = false;
        }else{
            Linea hilo = listaThreads.get(strand-1);
            Circle spot = new Circle((int)hilo.getX2(),(int)hilo.getY2());
            spot.changeColor(color);
            spot.makeVisible();
            spot.setStrand(strand);
            spots.put(color,spot);
            isOk = true;
        }
        
    }
    
    /**
     * eliminates the spider's favorite sleeping places, classified by color
     * @param color the color spot to eliminate
     */
    public void delSpot( String color){
        if(!spots.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "No existen spots de ese color");
            }isOk = false;
        }else{
            spots.get(color).makeInvisible();
            spots.remove(color);
            isOk = true;
        }
    }
    
    /**
     * changes the state of the spider from standing to sitting and from sitting to standing
     */
    public void spiderSit(){
        if(!SpiderSit){
            this.SpiderSit = true;
            spider.headChangeColor("blue");
        }else{
            this.SpiderSit = false;
            spider.headChangeColor("red");
        }isOk = true;
    }
    
    //Haya la distancia entre dos puntos
    private double getDistance(float x1, float y1, float x2, float y2){
        double distance = Math.sqrt(Math.pow((double)x2-x1,2)+Math.pow((double)y2-y1,2));
        return distance;
    }
    
    //Retorna la distancia entre la araña y el centro
    private double getDistanceCenterSpider(){
        return getDistance((float)centroImagenX,(float)centroImagenY,(float)spider.getPosx(),(float)spider.getPosy());
    }
    
    // retorna la distancia entre el centro y un puente
    private double getDistanceCenterBridge(Bridge bridge){
        return getDistance((float)centroImagenX,(float)centroImagenY,bridge.getX1(),bridge.getY1());
    }
    
    // retorna la distancia entre la Spider y un puente
    private double getDistanceSpiderBridge(Bridge bridge){
        return getDistance((float)spider.getPosx(),(float)spider.getPosy(),bridge.getX1(),bridge.getY1());
    }
    
    /**
     * Specify which thread you want the spider to start its journey on.
     */
    public void spiderStart(int strand){
        if((int)getDistanceCenterSpider() != 0){
            if(isVisible){JOptionPane.showMessageDialog(null, "No puedes especificar en que hilo quieres sentar la araña si ella no está en el centro");
            }isOk = false;
        }else if(strand > strands){
            if(isVisible){JOptionPane.showMessageDialog(null, "No puedes sentar la araña en un hilo inexistente");
            }isOk = false;
        }else{
            this.strand = strand;
            isOk = true;
        }
    }
    
    /**
     * Allows you to move the spider forward (true) or backward (false) automatically across its bridges
     * @param advance a boolean that sets whether the spider moves forward or backwards
     */
    public void siperWalk(boolean advance){
        if(advance){
            spiderWalk();    
        }else{
            spiderRetroceder();
        }
    }
    
    //puente más cercano a la araña en un hilo
    private Bridge findCLoserBridge(int hiloActual){
        Bridge puenteCercano = null;
        if(!puentesPorLineas.containsKey(hiloActual)){
            return puenteCercano;
        }else{
            double distanceMin = Double.POSITIVE_INFINITY;
            for(Bridge hilo : puentesPorLineas.get(hiloActual)){
                if(getDistanceCenterBridge(hilo)>getDistanceCenterSpider() && getDistanceSpiderBridge(hilo) < distanceMin){
                    distanceMin = getDistanceSpiderBridge(hilo);
                    puenteCercano = hilo;
                }
            }
            return puenteCercano;
        }
    }

    // añade a la lista de ultima ruta una linea y la colorea de azul    
    private void addLastRoute(float x1, float y1, float x2, float y2){
        Linea ruta = new Linea(x1,y1,x2,y2);
        ruta.changeColor("red");
        spiderLastRoute.add(ruta);
    }
    
    // hace visible toda la ruta
    private void visibleRoute(){
        for(Linea ruta: spiderLastRoute){
            ruta.makeVisible();
        }
    }
    
    //hacer la ultima ruta invisible
    private void invisibleRoute(){
        for(Linea ruta: spiderLastRoute){
            ruta.makeInvisible();
        }
    }
    
    //borrar la ultima ruta
    private void delRoute(){
        invisibleRoute();
        spiderLastRoute.clear();
    }
    
    //mover la araña hasta una esquina
    private void moveEsquina(int hiloActual){
        float X  =  listaThreads.get(hiloActual-1).getX2() - spider.getPosx();
        float Y  = listaThreads.get(hiloActual-1).getY2() - spider.getPosy();
        addLastRoute(spider.getPosx(),spider.getPosy(),listaThreads.get(hiloActual-1).getX2(),listaThreads.get(hiloActual-1).getY2());
        spider.moveTo((int)X, (int)Y);
        visibleRoute();
    }
    
    //mover y cruzar el puente
    private void MoverYpasarPuente(Bridge puenteCercano){
        float X  =  puenteCercano.getX1() - spider.getPosx();
        float Y  = puenteCercano.getY1() - spider.getPosy();
        addLastRoute(spider.getPosx(),spider.getPosy(),puenteCercano.getX1(),puenteCercano.getY1());
        spider.moveTo((int)X, (int)Y);
        float X2  =  puenteCercano.getX2() - spider.getPosx();
        float Y2  = puenteCercano.getY2() - spider.getPosy();
        addLastRoute(spider.getPosx(),spider.getPosy(),puenteCercano.getX2(),puenteCercano.getY2());
        spider.moveTo((int)X2, (int)Y2);
        visibleRoute();
    }
    
    //reubicar hilo actual
    private int strandActual(Bridge puenteCercano, int hiloActual){
        if(puenteCercano.getDirection() == "der"){
            if(hiloActual == 1){
                hiloActual = strands;
            }else{
                hiloActual -=1;
            }
        }else if(puenteCercano.getDirection() == "izq"){
            if(hiloActual == strands){
                hiloActual = 1;
            }else{
                hiloActual +=1;
            }
        }
        return hiloActual;
    }
    
    //mueve la araña automáticamente por los puentes
    private void spiderWalk(){
        if(SpiderSit){
            if(isVisible){JOptionPane.showMessageDialog(null, "la araña está sentada, levantala!");
            }isOk = false;
        }else{
            delRoute();
            int hiloActual = strand;
            while(!compararConMargenError(getDistanceCenterSpider(),radio,5)){
                //puente más proximo a la araña
                Bridge puenteCercano =findCLoserBridge(hiloActual-1);
                if(puenteCercano == null){
                    moveEsquina(hiloActual);
                }else{
                    MoverYpasarPuente(puenteCercano);
                    hiloActual = strandActual(puenteCercano,hiloActual);
                }
            }
            this.strand =hiloActual;
        } isOk = true;
    }
    
    //puente más cercano a la araña en un hilo para retroceder
    private Bridge findCLoserBridgeRet(int hiloActual){
        Bridge puenteCercano = null;
        if(!puentesPorLineas.containsKey(hiloActual)){
            return puenteCercano;
        }else{
            double distanceMin = Double.POSITIVE_INFINITY;
            for(Bridge hilo : puentesPorLineas.get(hiloActual)){
                if(getDistanceCenterBridge(hilo)<getDistanceCenterSpider() && getDistanceSpiderBridge(hilo) < distanceMin){
                    distanceMin = getDistanceSpiderBridge(hilo);
                    puenteCercano = hilo;
                }
            }
            return puenteCercano;
        }
    }
    
    //mover la araña hasta el centro
    private void moveCentro(){
        float X  =  centroImagenX - spider.getPosx();
        float Y  = centroImagenY - spider.getPosy();
        addLastRoute(spider.getPosx(),spider.getPosy(),centroImagenX,centroImagenY);
        spider.moveTo((int)X, (int)Y);
        visibleRoute();
    }
    
    
    //Hace retroceder la araña hasta el centro
    private void spiderRetroceder(){
        if(SpiderSit){
            if(isVisible){JOptionPane.showMessageDialog(null, "la araña está sentada, levantala!");
            }isOk = false;
        }else{
            delRoute();
            int hiloActual = strand;
            while(!compararConMargenError(getDistanceCenterSpider(),0,5)){
                //puente más proximo a la araña
                Bridge puenteCercano =findCLoserBridgeRet(hiloActual-1);
                if(puenteCercano == null){
                    moveCentro();
                }else{
                    MoverYpasarPuente(puenteCercano);
                    hiloActual = strandActual(puenteCercano,hiloActual);
                }
            }
        } isOk = true;
    }
    
    /**
     * If the spider is sit
     * @return SpiderSit a boolean
     */
    public boolean isSpiderSit(){
        isOk = true;
        return SpiderSit;
    }
    
    /**
     * return the last position of the spider
     * @return a Array with the x and y coordenates
     */
    private int[] spiderLastPath(){
        return spidertLastPath;
    }
    
    /**
     * gives all bridges colors
     * @return un arreglo de strings con los colores de los puentes
     */
    public String[] bridges(){
        String[] puentes = new String[bridges.size()];
        int i = 0;
        for(String puente : bridges.keySet()){
            puentes[i] = puente;
            i++;
        }
        isOk = true;
        return puentes;
    }
    
    /**
     * gives the strands that connect the bridge
     * @param color the color of the bridge to consult
     * @return the strands that connect the bridge
     */
    public int[] bridge(String color){
        if(!bridges.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "No existen puentes de ese color");} 
            int[] vacio = {};
            isOk = false;
            return vacio;
        }else{
            ArrayList<Bridge> puente = bridges.get(color);
            Bridge actual = puente.get(0);
            int init = actual.getInitStrand();
            int fin;
            if(init == strands){
                fin = 1;
            }else{
                fin = init +1;
            }
            int[] numStrands = {init,fin};
            isOk = true;
            return numStrands;
        }
    }
    
    /**
     * gives all color spots
     * @return un arreglo de strings con los colores de los spots
     */
    public String[] spots(){
        String[] lugares = new String[spots.size()];
        int i = 0;
        for(String spot : spots.keySet()){
            lugares[i] = spot;
            i++;
        }
        isOk = true;
        return lugares;
    }
    
    /**
     * gives the strand where the spot is
     * @param color the color of the spot to consult
     * @return the strand where the spot is
     */
    public int spot(String color){
        if(!spots.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "No existen spots de ese color");} 
            int vacio = -1;
            isOk = false;
            return vacio;
        }else{
            Circle lugar = spots.get(color);
            int numStrands = lugar.getStrand();
            isOk = true;
            return numStrands;
        }
    }
    
    /**
     * finish the simulator
     */
    public void finish(){
         this.makeInvisible();
         this.listaThreads = null;
         this.hilos = null;
         this.spider = null;
         this.bridges = null;
         this.spots = null;
         isOk = true;
    }
    
    /**
     * return if the last move was ok
     * @return isOk a boolean
     */
    public boolean ok(){
        return isOk;
    }
    
    //prueba si dos numeros son iguales con margen de error
    private boolean compararConMargenError(double numero1, double numero2, double margenError) {
        // Calcula la diferencia absoluta entre los dos números
        double diferencia = Math.abs(numero1 - numero2);
        
        // Si la diferencia es menor o igual al margen de error, consideramos que los números son iguales
        return diferencia <= margenError;
    }
}



