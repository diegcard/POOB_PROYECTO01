package spiderweb;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.HashMap;
import shapes.*;

/**
 * La clase Spiderweb representa una telaraña con hilos, puentes y una arañita.
 * La telaraña se compone de un número específico de hilos, que se extienden desde el centro de la telaraña hasta el border.
 * Los hilos están espaciados regularmente alrededor del círculo, y cada hilo tiene un punto de inicio y un punto final.
 * La telaraña también incluye puentes que conectan los hilos, y puntos de inicio para la arañita.
 * La arañita puede moverse a lo largo de los hilos y puentes, y puede descansar en los puntos de inicio.
 * La clase incluye métodos para agregar y eliminar hilos, puentes y puntos de inicio, y para mover la arañita a lo largo de la telaraña.
 * La clase también incluye métodos para mostrar y ocultar la telaraña y sus componentes.
 * La clase se utiliza para simular una araña que se mueve a lo largo de una telaraña, cruzando puentes y descansando en puntos de inicio.
 *
 * @author Diego Cardenas
 * @author Sebastian Cardona
 * @version 1.2
 */
public class Spiderweb{
    private boolean isVisible;
    private int strands;
    private int strand;
    private double radio;
    private double angulo;
    private final ArrayList<Hilo> listaThreads;
    private Spider spider;
    private final HashMap<String, ArrayList<Bridge>> bridges;
    private static final int CENTRO_IMAGEN_X = 640;
    private static final int CENTRO_IMAGEN_Y = 360;
    private final HashMap<String, Spot> spots;
    private boolean isOk = true;
    private final HashMap<Integer, ArrayList<Bridge>> puentesPorLineas;
    private final ArrayList<Integer> spidertLastPath;
    private final ArrayList<Linea> spiderLastRoute;

    /**
     * Constructor de la clase Spiderweb.
     * Crea una telaraña con un número específico de hilos y un radio dado.
     * La telaraña se inicializa con los puntos de inicio de los hilos y la arañita.
     *
     * @param strands El número de hilos que formarán la telaraña.
     * @param radio   El radio de la telaraña desde el centro.
     */
    public Spiderweb(int strands, int radio) {
        this.strands = strands;
        this.angulo = (double) 360 / strands;
        this.radio = radio;
        this.listaThreads = new ArrayList<>();
        newWeb();
        this.bridges = new HashMap<>();
        this.spots = new HashMap<>();
        this.spider = new Spider();
        spider.moveTo(Spiderweb.getPosCenterImage()[0] - spider.getPosx(), Spiderweb.getPosCenterImage()[1] - spider.getPosy());
        this.puentesPorLineas = new HashMap<>();
        spidertLastPath = new ArrayList<>();
        this.spiderLastRoute = new ArrayList<>();
    }

    /**
     * Constructor de la clase Spiderweb.
     *
     * @param strands  El número de hilos que formarán la telaraña.
     * @param favorite El hilo favorito de la araña.
     * @param bridges  Los puentes que conectan los hilos.
     */
    public Spiderweb(int strands, int favorite, int[][] bridges) {
        this.strands = strands;
        this.angulo = (double) 360 / strands;
        this.radio = 350;
        this.listaThreads = new ArrayList<>();
        newWeb();
        this.bridges = new HashMap<>();
        this.spots = new HashMap<>();
        this.spider = new Spider();
        spider.changeSize(-50);
        spider.moveTo(Spiderweb.getPosCenterImage()[0] - spider.getPosx(), Spiderweb.getPosCenterImage()[1] - spider.getPosy());
        this.puentesPorLineas = new HashMap<>();
        spidertLastPath = new ArrayList<>();
        this.spiderLastRoute = new ArrayList<>();
        addSpot("blue", favorite);
        String[] colors = {"red", "blue", "green", "yellow", "black", "darkgray", "orange", "magenta",
                "cyan", "gray","#14936136","#18217172","#122202184","#15194115","#5522195","#117176131","#8420971",
                "#14121414","#24024029","#2509532","#1006253","#2558751","pink", "lightgray", "purple", "brown", "turquoise", "lilac", "salmon"};
        for (int i = 0; i < bridges.length; i++) {
            double position = (bridges[i][0] * radio) / 23;
            addBridge(colors[i], (int)position, bridges[i][1]);
        }
    }

    /**
     * This method is used to create a new web.
     */
    private void newWeb() {
        double intervalo = 0;
        while (intervalo < 360) {
            Hilo temp = new Hilo(Math.round(radio * Math.cos(Math.toRadians(intervalo))), Math.round(radio * Math.sin(Math.toRadians(intervalo))));
            listaThreads.add(temp);
            intervalo += angulo;
        }
    }

    /**
     * Adds a new thread to the web.
     * The method adds a new thread to the web by creating a new instance of the Thread class and adding it to the 'listaThreads' ArrayList.
     */
    public void addStrand() {
        if ((int) getDistanceCenterSpider() != 0) {
            spiderRetroceder();
        }
        double intervalo = 0;
        this.strands += 1;
        this.angulo = (double) 360 / strands;
        for (Hilo hilo : listaThreads) {
            hilo.newPoints(Math.round(radio * Math.cos(Math.toRadians(intervalo))), Math.round(radio * Math.sin(Math.toRadians(intervalo))));
            intervalo += angulo;
        }
        listaThreads.add(new Hilo(Math.round(radio * Math.cos(Math.toRadians(intervalo))), Math.round(radio * Math.sin(Math.toRadians(intervalo)))));
        for (String color : bridges.keySet()) {
            relocateBridge(color, bridges.get(color).get(0).getDistance());
        }
        if (isVisible) {
            makeInvisible();
            makeVisible();
        }
        relocateSpots();
        isOk = true;
    }

    /**
     * This method is used to find and return the colors of unused bridges in the web.
     *
     * @return An array of Strings representing the colors of the unused bridges.
     */
    public String[] unusedBridges() {
        ArrayList<String> noUsados = new ArrayList<>();
        for (String color : bridges.keySet()) {
            if (!(bridges.get(color).get(0).isUsed() || bridges.get(color).get(1).isUsed())) {
                noUsados.add(color);
            }
        }
        isOk = true;
        return noUsados.toArray(new String[0]);
    }

    /**
     * This method is used to return the spots where it is possible to reach using bridges and threads.
     *
     * @return An array of Strings representing the colors of the spots that can be reached.
     */
    public String[] reachablesSpots() {
        ArrayList<String> alcanzable = new ArrayList<>();
        if (strand == 0) {
            if (isVisible) JOptionPane.showMessageDialog(null, "Primero sienta la araña");
            isOk = false;
        } else if ((int) getDistanceCenterSpider() != 0) {
            return new String[]{};
        } else {
            spider.makeInvisible();
            spiderWalk(true);
            delRoute();
            int pos = strand;
            spiderWalk(false);
            delRoute();
            if (isVisible) spider.makeVisible();
            for (String color : spots.keySet()) {
                if (spots.get(color).getStrand() == pos) {
                    alcanzable.add(color);
                }
                break;
            }
            isOk = true;
        }
        return alcanzable.toArray(new String[0]);
    }

    /**
     * This method is used to lengthen the length of the threads
     *
     * @param percentage the percentage to increase the length of the threads
     */
    public void enlarge(int percentage) {
        if (percentage < 0) {
            if (isVisible) JOptionPane.showMessageDialog(null, "Solo puedes alargar la telaraña");
            isOk = false;
        } else {
            for (Hilo hilo : listaThreads) {
                hilo.changeSize(percentage);
            }
            this.radio = getDistance(CENTRO_IMAGEN_X, CENTRO_IMAGEN_Y, listaThreads.get(0).getX2(), listaThreads.get(0).getY2());
            relocateSpots();
            isOk = true;
        }
        if (isVisible) {
            makeInvisible();
            makeVisible();
        }
    }

    /**
     * Relocates spots when their radius is modified
     */
    private void relocateSpots() {
        for (Spot spot : spots.values()) {
            int strandi = spot.getStrand() - 1;
            Hilo hilo = listaThreads.get(strandi);
            spot.relocate((int) hilo.getX2(), (int) hilo.getY2());
        }
    }

    /**
     * Make visible all the simulator
     */
    public void makeVisible() {
        if (!isVisible) {
            listaThreads.forEach(Hilo::makeVisible);
            spider.makeVisible();
            for (String color : bridges.keySet()) {
                for (Bridge bridge : bridges.get(color)) {
                    bridge.makeVisible();
                }
            }
            for (String color : spots.keySet()) {
                spots.get(color).makeVisible();
            }
            isVisible = true;
            isOk = true;
            visibleRoute();
        } else {
            isOk = false;
        }
    }

    /**
     * Make invisible all the simulator
     */
    public void makeInvisible() {
        if (isVisible) {
            listaThreads.forEach(Hilo::makeInvisible);
            spider.makeInvisible();
            for (String color : bridges.keySet()) {
                for (Bridge bridge : bridges.get(color)) {
                    bridge.makeInvisible();
                }
            }
            for (String color : spots.keySet()) {
                spots.get(color).makeInvisible();
            }
            isVisible = false;
            invisibleRoute();
            isOk = true;
        } else {
            isOk = false;
        }
    }

    /**
     * Create a bridge between two threads with a specific type
     *
     * @param type        the type of bridge to create
     * @param firstStrand the first thread
     * @param distance    the distance from the center of the web to the bridge
     * @param color       the color of the bridge
     * @return the bridge created
     */
    private Bridge createBridge(String type,int firstStrand, int distance, String color) {
        float[] points =calculedPoints(firstStrand,distance);
        Bridge bridge = switch (type) {
            case "normal" ->
                    new NormalBridge(CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1], CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3], firstStrand, distance, "izq", color);
            case "fixed" ->
                    new FixedBridge(CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1], CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3], firstStrand, distance, "izq", color);
            case "mobile" ->
                    new MobileBridge(CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1], CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3], firstStrand, distance, "izq", color);
            case "transformer" ->
                    new TransformerBridge(CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1], CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3], firstStrand, distance, "izq", color);
            default ->
                    new WeakBridge(CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1], CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3], firstStrand, distance, "izq", color);
        };
        if (isVisible) bridge.makeVisible();
        return bridge;
    }


    /**
     * Create a bridge between two threads with a specific type
     *
     * @param type        the type of bridge to create
     * @param firstStrand the first thread
     * @param distance    the distance from the center of the web to the bridge
     * @param color       the color of the bridge
     * @return the bridge created
     */
    private Bridge createBridge2(String type, int firstStrand, int distance, String color) {
        float[] points =calculedPoints(firstStrand,distance);
        Bridge bridge = switch (type) {
            case "normal" ->
                    new NormalBridge(CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3], CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1], firstStrand, distance, "der", color);
            case "fixed" ->
                    new FixedBridge(CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3], CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1], firstStrand, distance, "der", color);
            case "mobile" ->
                    new MobileBridge(CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3], CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1], firstStrand, distance, "der", color);
            case "transformer" ->
                    new TransformerBridge(CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3], CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1], firstStrand, distance, "der", color);
            default ->
                    new WeakBridge(CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3], CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1], firstStrand, distance, "der", color);
        };
        if (isVisible) bridge.makeVisible();
        return bridge;
    }

    /**
     * Adds a bridge between two points on the screen, given a color, a distance, and a starting coordinate.
     *
     * @param color       the color of bridge
     * @param distance    The distance from the center point where the bridge will be located.
     * @param firstStrand initial thread where the bridge will be located
     **/
    public void addBridge(String color, int distance, int firstStrand) {
        isOk = false;
        if (distance > radio || firstStrand > strands || firstStrand <= 0 || adyacentBridges(distance,firstStrand)) {
            if (isVisible) JOptionPane.showMessageDialog(null, "Oops, algo salió mal, revisa las entradas");
        } else if (bridges.containsKey(color)) {
            if (isVisible) JOptionPane.showMessageDialog(null, "Ya existe un puente de dicho color");
        } else {
            Bridge bridge = createBridge("normal",firstStrand, distance, color);
            Bridge bridge2 = createBridge2("normal",firstStrand, distance, color);
            addBridgeInStrand(bridge, firstStrand - 1);
            addBridgeInStrand(bridge2, firstStrand);
            ArrayList<Bridge> puente = new ArrayList<>();
            puente.add(bridge);
            puente.add(bridge2);
            bridges.put(color, puente);
            isOk = true;
        }
    }

    /**
     * Adds a specific type of bridge between two threads in the web.
     * @param type the type of bridge to add
     * @param color the color of the bridge
     * @param distance the distance from the center of the web to the bridge
     * @param firstStrand the first thread where the bridge will be located
     */
    public void addBridge(String type,String color, int distance, int firstStrand) {
        isOk = false;
        if (distance > radio || firstStrand > strands || firstStrand <= 0 || adyacentBridges(distance,firstStrand) || !(type.equals("normal") || type.equals("fixed") || type.equals("mobile") || type.equals("transformer") || type.equals("weak"))){
            if (isVisible) JOptionPane.showMessageDialog(null, "Oops, algo salió mal, revisa las entradas");
        } else if (bridges.containsKey(color)) {
            if (isVisible) JOptionPane.showMessageDialog(null, "Ya existe un puente de dicho color");
        } else {
            Bridge bridge = createBridge(type,firstStrand, distance, color);
            Bridge bridge2 = createBridge2(type,firstStrand, distance, color);
            addBridgeInStrand(bridge, firstStrand - 1);
            addBridgeInStrand(bridge2, firstStrand);
            ArrayList<Bridge> puente = new ArrayList<>();
            puente.add(bridge);
            puente.add(bridge2);
            bridges.put(color, puente);
            isOk = true;
        }
    }

    /**
     * Add the bridge to the corresponding thread in a hashMap
     *
     * @param puente  the bridge to add
     * @param strandi the thread where the bridge will be added
     */
    private void addBridgeInStrand(Bridge puente, int strandi) {
        strandi = strandi % strands;
        if (puentesPorLineas.containsKey(strandi)) {
            puentesPorLineas.get(strandi).add(puente);
        } else {
            ArrayList<Bridge> puent = new ArrayList<>();
            puent.add(puente);
            puentesPorLineas.put(strandi, puent);
        }
    }

    /**
     * Get the center of the image
     *
     * @return an array with the x and y coordinates
     */
    public static int[] getPosCenterImage() {
        return new int[]{CENTRO_IMAGEN_X, CENTRO_IMAGEN_Y};
    }

    //this method returns an array with the calculated points
    private float[] calculedPoints(int firstStrand, int distance){
        double angleFirstStrand = (firstStrand - 1) * angulo;
        double angleSecondStrand = (firstStrand) * angulo;
        float posx1 = Math.round(distance * Math.cos(Math.toRadians(angleFirstStrand)));
        float posy1 = Math.round(distance * Math.sin(Math.toRadians(angleFirstStrand)));
        float posx2 = Math.round(distance * Math.cos(Math.toRadians(angleSecondStrand)));
        float posy2 = Math.round(distance * Math.sin(Math.toRadians(angleSecondStrand)));
        return new float[]{posx1,posy1,posx2,posy2};
    }

    /**
     * Relocate bridges of a specific color a given distance from the center of the web.
     *
     * @param color    The color of the bridge to move.
     * @param distance The new distance from the center of the circle to the bridge attachment point.
     */
    public void relocateBridge(String color, int distance) {
        if (distance > radio) {
            if (isVisible) JOptionPane.showMessageDialog(null, "No se puede reubicar los puentes a una distancia mayor del limite de la araña");
            isOk = false;
        } else if (!bridges.containsKey(color)) {
            if (isVisible) JOptionPane.showMessageDialog(null, "No hay ningun puente con ese color");
            isOk = false;
        } else {
            for (Bridge bridge : bridges.get(color)) {
                int firstStrand = bridge.getInitStrand();
                float[] points = calculedPoints(firstStrand, distance);
                if (bridge.getDirection().equals("izq")) {
                    bridge.newPoints(CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1], CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3]);
                } else {
                    bridge.newPoints(CENTRO_IMAGEN_X + points[2], CENTRO_IMAGEN_Y - points[3], CENTRO_IMAGEN_X + points[0], CENTRO_IMAGEN_Y - points[1]);
                }
            }
            isOk = true;
        }
    }

    /**
     * Eliminate spider web bridges based on their color.
     *
     * @param color The color of the bridge to remove.
     */
    public void delBridge(String color) {
        isOk = false;
        if (!bridges.containsKey(color)) {
            if (isVisible) JOptionPane.showMessageDialog(null, "No existe ningun puente con dicho color");
        }else if(bridges.get(color).get(0) instanceof FixedBridge) {
            if (isVisible) JOptionPane.showMessageDialog(null, "No puedes eliminar un puente fijo");
        }else {
            if(bridges.get(color).get(0) instanceof TransformerBridge){
                if(!(isAStrandWithASpot(bridges.get(color).get(0).getInitStrand()) || spots.containsKey(color))){
                    addSpot(color,bridges.get(color).get(0).getInitStrand());
                }
            }
            delBridgelogical(color);
            isOk = true;
        }
    }

    /**
     * look if a strand has a spot
     * @param strand the strand to look
     * @return true if the strand has a spot, false otherwise
     */
    private boolean isAStrandWithASpot(int strand){
        for (String color : spots.keySet()) {
            if (spots.get(color).getStrand() == strand) {
                return true;
            }
        }
        return false;
    }
    private void delBridgelogical(String color) {
        for (Bridge bridge : bridges.get(color)) {
            bridge.makeInvisible();
            int intStrand;
            if (bridge.getDirection().equals("izq")) {
                intStrand = bridge.getInitStrand() - 1;
            } else {
                intStrand = bridge.getInitStrand() % strands;
            }
            puentesPorLineas.get(intStrand).remove(bridge);
        }
        bridges.remove(color);
    }

    /**
     * Adds a favorite place for the spider to sleep, represented by a circle
     *
     * @param color the spot color
     * @param strand where the spot will be
     */
    public void addSpot(String color, int strand) {
        isOk = false;
        if (strand > strands || strand <= 0 || isAStrandWithASpot(strand)){
            if (isVisible) JOptionPane.showMessageDialog(null, "No puedes poner un spot en un hilo inexistente, o en un hilo que ya tiene spot");
        } else if (spots.containsKey(color)) {
            if (isVisible) JOptionPane.showMessageDialog(null, "Este spot ya existe");
        } else {
            Hilo hilo = listaThreads.get(strand - 1);
            Spot spot = new NormalSpot((int) hilo.getX2(), (int) hilo.getY2(), strand, color);
            if (isVisible) spot.makeVisible();
            spots.put(color, spot);
            isOk = true;
        }
    }

    /**
     * Adds a favorite place for the spider to sleep, represented by a circle with a specific type
     *
     * @param type  the type of spot to add
     * @param color the spot color
     * @param strand where the spot will be
     */
    public void addSpot(String type, String color, int strand){
        isOk = false;
        if (strand > strands || strand <= 0 || isAStrandWithASpot(strand) || !(type.equals("normal") || type.equals("bouncy") || type.equals("killer") || type.equals("color"))){
            if (isVisible) JOptionPane.showMessageDialog(null, "No puedes poner un spot en un hilo inexistente, o en un hilo que ya tiene spot, o con un tipo incorrecto");
        } else if (spots.containsKey(color)) {
            if (isVisible) JOptionPane.showMessageDialog(null, "Este spot ya existe");
        } else {
            Spot spot = getSpot(type, color, strand);
            spots.put(color, spot);
            isOk = true;
        }
    }

    private Spot getSpot(String type, String color, int strand) {
        Hilo hilo = listaThreads.get(strand - 1);
        Spot spot = switch (type) {
            case "normal" -> new NormalSpot((int) hilo.getX2(), (int) hilo.getY2(), strand, color);
            case "bouncy" -> new BouncySpot((int) hilo.getX2(), (int) hilo.getY2(), strand, color);
            case "color" -> new ColorSpot((int) hilo.getX2(), (int) hilo.getY2(), strand, color);
            default -> new KillerSpot((int) hilo.getX2(), (int) hilo.getY2(), strand, color);
        };
        if (isVisible) spot.makeVisible();
        return spot;
    }

    /**
     * Eliminates the spider's favorite sleeping places, classified by color
     *
     * @param color the color spot to eliminate
     */
    public void delSpot(String color) {
        if (!spots.containsKey(color)) {
            if (isVisible) JOptionPane.showMessageDialog(null, "No existen spots de ese color");
            isOk = false;
        } else {
            spots.get(color).makeInvisible();
            spots.remove(color);
            isOk = true;
        }
    }

    /**
     * Find the distance between two points
     *
     * @param x1 the x coordinate of the first point
     * @param y1 the y coordinate of the first point
     * @param x2 the x coordinate of the second point
     * @param y2 the y coordinate of the second point
     * @return the distance between two points
     */
    private double getDistance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow((double) x2 - x1, 2) + Math.pow((double) y2 - y1, 2));
    }

    /**
     * Find the distance between the center of the web and the spider
     *
     * @return the distance between the center of the web and the spider
     */
    private double getDistanceCenterSpider() {
        return getDistance((float) CENTRO_IMAGEN_X, (float) CENTRO_IMAGEN_Y, (float) spider.getPosx(), (float) spider.getPosy());
    }

    /**
     * Find the distance between the center of the web and a bridge
     *
     * @param bridge the bridge to consult
     * @return the distance between the center of the web and a bridge
     */
    private double getDistanceCenterBridge(Bridge bridge) {
        return getDistance((float) CENTRO_IMAGEN_X, (float) CENTRO_IMAGEN_Y, bridge.getX1(), bridge.getY1());
    }

    /**
     * Find the distance between the spider and a bridge
     *
     * @param bridge the bridge to consult
     * @return the distance between the spider and a bridge
     */
    private double getDistanceSpiderBridge(Bridge bridge) {
        return getDistance((float) spider.getPosx(), (float) spider.getPosy(), bridge.getX1(), bridge.getY1());
    }

    /**
     * Specify which thread you want the spider to start its journey on.
     *
     * @param strand the thread where the spider will start
     */
    public void spiderSit(int strand) {
        if (strand > strands && strand >= 0) {
            if (isVisible) JOptionPane.showMessageDialog(null, "No puedes sentar la araña en un hilo inexistente");
            isOk = false;
        } else {
            if(spider == null){
                spider = new Spider();
                spider.moveTo(Spiderweb.getPosCenterImage()[0] - spider.getPosx(), Spiderweb.getPosCenterImage()[1] - spider.getPosy());
                if(isVisible) spider.makeVisible();
            }
            if ((int) getDistanceCenterSpider() != 0) {
                spiderRetroceder();
            }
            spider.headChangeColor("blue");
            this.strand = strand;
            isOk = true;
        }
    }

    /**
     * Allows you to move the spider forward (true) or backward (false) automatically across its bridges
     *
     * @param advance a boolean that sets whether the spider moves forward or backwards
     */
    public void spiderWalk(boolean advance) {
        if (strand == 0) {
            if (isVisible) JOptionPane.showMessageDialog(null, "Primero debes sentar la araña en un hilo");
            isOk = false;
        } else if (advance) {
            spiderWalk();
            Spot spot = getSpot(strand);
            effectSpots(spot);
            isOk = true;
        } else {
            spiderRetroceder();
            isOk = true;
        }
    }

    /**
     * a recursive method that allows repetitive the bouncy effect
     */
    private void effectSpots(Spot spot){
        if(spot instanceof BouncySpot){
            Hilo hilo = listaThreads.get(strand % strands);
            spider.moveTo((int) hilo.getX2() - spider.getPosx(), (int) hilo.getY2() - spider.getPosy());
            this.strand = (strand % strands) +1;
            spot = getSpot(strand);
            effectSpots(spot);
        }else if(spot instanceof KillerSpot){
            spider.makeInvisible();
            spider = null;
        }else if(spot instanceof ColorSpot){
            spider.bodyChangeColor(spot.getColor());
        }
    }

    /**
     * if a strand have a spot, this metod will return it
     * @param strand the strand to look
     * @return the spot in the strand
     */
    private Spot getSpot(int strand) {
        for (String color : spots.keySet()) {
            if (spots.get(color).getStrand() == strand) {
                return spots.get(color);
            }
        }
        return null;
    }

    /**
     * Find the closest bridge to the spider in a thread
     *
     * @param hiloActual the thread where the spider is
     * @return Bridge the closest bridge to the spider
     */
    private Bridge findCLoserBridge(int hiloActual) {
        Bridge puenteCercano = null;
        if (!puentesPorLineas.containsKey(hiloActual)) {
            return null;
        } else {
            double distanceMin = Double.POSITIVE_INFINITY;
            for (Bridge hilo : puentesPorLineas.get(hiloActual)) {
                if (getDistanceCenterBridge(hilo) > getDistanceCenterSpider() && getDistanceSpiderBridge(hilo) < distanceMin) {
                    distanceMin = getDistanceSpiderBridge(hilo);
                    puenteCercano = hilo;
                }
            }
            return puenteCercano;
        }
    }

    /**
     * Add the last route to the list of routes and change the color to red
     *
     * @param x1 position x1
     * @param y1 position y1
     * @param x2 position x2
     * @param y2 position y2
     */
    private void addLastRoute(float x1, float y1, float x2, float y2) {
        Linea ruta = new Linea(x1, y1, x2, y2);
        ruta.changeColor("red");
        spiderLastRoute.add(ruta);
    }

    /**
     * Make the all route visible
     */
    private void visibleRoute() {
        spiderLastRoute.forEach(Linea::makeVisible);
    }

    /**
     * Make the last route invisible
     */
    private void invisibleRoute() {
        spiderLastRoute.forEach(Linea::makeInvisible);
    }

    /**
     * Delete the last route
     */
    private void delRoute() {
        invisibleRoute();
        spiderLastRoute.clear();
    }

    /**
     * Move the spider to a corner
     *
     * @param hiloActual the current thread
     */
    private void moveEsquina(int hiloActual) {
        addLastRoute(spider.getPosx(), spider.getPosy(), listaThreads.get(hiloActual - 1).getX2(), listaThreads.get(hiloActual - 1).getY2());
        spider.moveTo((int) listaThreads.get(hiloActual - 1).getX2() - spider.getPosx(), (int) listaThreads.get(hiloActual - 1).getY2() - spider.getPosy());
        if (isVisible) {
            visibleRoute();
        }
    }

    /**
     * Move and cross the bridge
     *
     * @param puenteCercano the closest bridge to the spider
     */
    private void MoverYpasarPuente(Bridge puenteCercano) {
        addLastRoute(spider.getPosx(), spider.getPosy(), puenteCercano.getX1(), puenteCercano.getY1());
        spider.moveTo((int) puenteCercano.getX1() - spider.getPosx(), (int) puenteCercano.getY1() - spider.getPosy());
        spider.moveTo((int) puenteCercano.getX2() - spider.getPosx(), (int) puenteCercano.getY2() - spider.getPosy());
        String colorPuenteCercano = puenteCercano.getColor();
        if(puenteCercano instanceof WeakBridge){
            delBridge(colorPuenteCercano);
        }else if(puenteCercano instanceof MobileBridge){
            int firstStrand = (puenteCercano.getInitStrand() % strands) +1;
            int distance = puenteCercano.getDistance()+((puenteCercano.getDistance()*20)/100);
            if(!(distance> radio || adyacentBridges(distance,firstStrand))){
                delBridge(colorPuenteCercano);
                addBridge("normal",colorPuenteCercano,distance,firstStrand);
            }else addLastRoute(puenteCercano.getX1(), puenteCercano.getY1(), puenteCercano.getX2(), puenteCercano.getY2());
        }else{
            addLastRoute(puenteCercano.getX1(), puenteCercano.getY1(), puenteCercano.getX2(), puenteCercano.getY2());
        }
        if (isVisible) visibleRoute();
        puenteCercano.use();
    }

    /**
     * look if a bridge I want to put is adyacent to another bridge or if it is in the same position
     *
     * @param strand   the strand where the bridge will be
     * @param distance the distance from the center of the web to the bridge
     * @return true if the bridge is in the strand in the distance, false otherwise
     */
    private boolean adyacentBridges(int distance, int strand) {
        int next = (strand % strands) + 1;
        boolean verificador = false;
        if (puentesPorLineas.get(strand - 1) != null) {
            for (Bridge bridges : puentesPorLineas.get(strand - 1)) {
                if (compararConMargenError(bridges.getDistance(), distance)) {
                    verificador = true;
                    break;
                }
            }
        }
        if (puentesPorLineas.get(next - 1) != null) {
            for (Bridge bridges : puentesPorLineas.get(next - 1)) {
                if (compararConMargenError(bridges.getDistance(), distance)) {
                    verificador = true;
                    break;
                }
            }
        }
        return verificador;
    }

    /**
     * Relocate current thread
     *
     * @param puenteCercano the closest bridge to the spider
     * @param hiloActual    the current thread
     * @return int the current thread
     */
    private int strandActual(Bridge puenteCercano, int hiloActual) {
        if (puenteCercano.getDirection().equals("der")) {
            if (hiloActual == 1) {
                hiloActual = strands;
            } else {
                hiloActual -= 1;
            }
        } else if (puenteCercano.getDirection().equals("izq")) {
            hiloActual = (hiloActual % strands) + 1;
        }
        return hiloActual;
    }

    /**
     * Move the spider automatically through the bridges
     */
    private void spiderWalk() {
        delRoute();
        int hiloActual = strand;
        while (!compararConMargenError(getDistanceCenterSpider(), radio)) {
            Bridge puenteCercano = findCLoserBridge(hiloActual - 1);
            if (puenteCercano == null) {
                moveEsquina(hiloActual);
                spidertLastPath.add(strand);
            } else {
                MoverYpasarPuente(puenteCercano);
                hiloActual = strandActual(puenteCercano, hiloActual);
                spidertLastPath.add(hiloActual);
            }
        }
        this.strand = hiloActual;
        isOk = true;
    }

    /**
     * Find the closest bridge to the spider in a thread
     *
     * @param hiloActual the thread where the spider is
     * @return Bridge the closest bridge to the spider
     */
    private Bridge findCLoserBridgeRet(int hiloActual) {
        if (!puentesPorLineas.containsKey(hiloActual)) {
            return null;
        }
        Bridge puenteCercano = null;
        double distanceMin = Double.POSITIVE_INFINITY;
        for (Bridge hilo : puentesPorLineas.get(hiloActual)) {
            if (getDistanceCenterBridge(hilo) < getDistanceCenterSpider() && getDistanceSpiderBridge(hilo) < distanceMin) {
                distanceMin = getDistanceSpiderBridge(hilo);
                puenteCercano = hilo;
            }
        }
        return puenteCercano;
    }

    /**
     * Move the spider to the center of the web
     */
    private void moveCentro() {
        addLastRoute(spider.getPosx(), spider.getPosy(), CENTRO_IMAGEN_X, CENTRO_IMAGEN_Y);
        spider.moveTo(CENTRO_IMAGEN_X - spider.getPosx(), CENTRO_IMAGEN_Y - spider.getPosy());
        if (isVisible) visibleRoute();
    }

    /**
     * Move the spider to the center of the web
     */
    private void spiderRetroceder() {
        delRoute();
        int hiloActual = strand;
        while (!compararConMargenError(getDistanceCenterSpider(), 0)) {
            Bridge puenteCercano = findCLoserBridgeRet(hiloActual - 1);
            if (puenteCercano == null) {
                moveCentro();
            } else {
                MoverYpasarPuente(puenteCercano);
                hiloActual = strandActual(puenteCercano, hiloActual);
            }
        }
        delRoute();
        isOk = true;
    }

    /**
     * search an int into a ArrayList
     */
    private boolean searchInt(ArrayList<Integer> arraylist, int entero) {
        for (int elemento : arraylist) {
            if (elemento == entero) {
                return true;
            }
        }
        return false;
    }

    /**
     * del repite elements in a Arraylist
     */
    private ArrayList<Integer> deletingRepetive(ArrayList<Integer> list) {
        ArrayList<Integer> nuevo = new ArrayList<>();
        for (int entero : list) {
            if (!searchInt(nuevo, entero)) {
                nuevo.add(entero);
            }
        }
        return nuevo;
    }

    /**
     * Return the last path of the spider
     * @return ArrayList with the x and y coordinates
     */
    public int[] spiderLastPath() {
        ArrayList<Integer> LastPath = deletingRepetive(spidertLastPath);
        int[] spidertLastPathfin = new int[LastPath.size()];
        for (int i = 0; i < LastPath.size(); i++) {
            spidertLastPathfin[i] = LastPath.get(i);
        }
        return spidertLastPathfin;
    }

    /**
     * Gives all bridges colors
     *
     * @return an Array of strings with the colors of the bridges
     */
    public String[] bridges() {
        String[] puentes = new String[bridges.size()];
        int i = 0;
        for (String puente : bridges.keySet()) {
            puentes[i] = puente;
            i++;
        }
        isOk = true;
        return puentes;
    }

    /**
     * Gives the strands that connect the bridge
     *
     * @param color the color of the bridge to consult
     * @return the strands that connect the bridge
     */
    public int[] bridge(String color) {
        if (!bridges.containsKey(color)) {
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "No existen puentes de ese color");
            }
            isOk = false;
            return new int[]{};
        } else {
            ArrayList<Bridge> puente = bridges.get(color);
            Bridge actual = puente.get(0);
            int init = actual.getInitStrand();
            int fin = (init % strands) + 1;
            int[] numStrands = {init, fin};
            isOk = true;
            return numStrands;
        }
    }

    /**
     * Gives all color spots
     *
     * @return an Array of strings with the colors of the spots
     */
    public String[] spots() {
        String[] lugares = new String[spots.size()];
        int i = 0;
        for (String spot : spots.keySet()) {
            lugares[i] = spot;
            i++;
        }
        isOk = true;
        return lugares;
    }

    /**
     * Gives the strand where the spot is
     *
     * @param color the color of the spot to consult
     * @return the strand where the spot is
     */
    public int spot(String color) {
        if (!spots.containsKey(color)) {
            if (isVisible) {
                JOptionPane.showMessageDialog(null, "No existen spots de ese color");
            }
            isOk = false;
            return -1;
        } else {
            Spot lugar = spots.get(color);
            int numStrands = lugar.getStrand();
            isOk = true;
            return numStrands;
        }
    }

    /**
     * finish the simulator
     */
    public void finish(){
        System.exit(0);
        isOk = true;
    }

    /**
     * return if the last move was ok
     *
     * @return isOk a boolean
     */
    public boolean ok() {
        return isOk;
    }

    /**
     * This method is used to compare two numbers with a margin of error.
     *
     * @param numero1 the first number to compare
     * @param numero2 the second number to compare
     * @return true if the numbers are equal within the margin of error, false otherwise
     */
    private boolean compararConMargenError(double numero1, double numero2) {
        return Math.abs(numero1 - numero2) <=  5;
    }

    /**
     * Get the number of strands
     *
     * @return the number of strands
     */
    public int getStrands() {
        return strands;
    }
    
    /**
     * Get the current Strand where The Spider is sat
     *
     * @return the number current strand
     */
    public int getCurrentStrand() {
        return strand;
    }

    /**
     * Get the radius of the web
     *
     * @return the radius of the web
     */
    public int getRadio() {
        return (int) radio;
    }

    public HashMap<Integer, ArrayList<Bridge>> getPuentesPorLineas(){
        return puentesPorLineas;
    }
    
    /**
     * gets spider's body color 
     */
    public String getSpiderColor() {
        return spider.getColor();
    }
    
    /**
     * gets spider 
     */
    public Spider getSpider() {
        return spider;
    }
}