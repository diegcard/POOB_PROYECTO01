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
    private Linescoordinates lista;// Lista de las coordenadas
    private ArrayList<Linea> listaLineas;   //Lista de las lineas 
    private List<Pair<Double, Double>> lis;//
    private Spider spider;//Araña
    private double angulo;//Angulo 
    private HashMap<String,ArrayList<Linea>> bridges;
    private static final int centroImagenX = 640;
    private static final int centroImagenY = 360;
    private boolean SpiderSit;
    private HashMap<String,ArrayList<Circle>> spots;
    private Triangle avisador = new Triangle(20,20);
    private boolean isOk = true;
    private HashMap<Integer,ArrayList<Linea>> puentesPorLineas;
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
        this.lista = new Linescoordinates(strands, radio);
        this.angulo = lista.getCant();
        this.lis = lista.getlista();
        this.listaLineas = new ArrayList<Linea>();   
        this.bridges = new HashMap<String,ArrayList<Linea>>();
        this.spots = new HashMap<String,ArrayList<Circle>>();
        this.radio = radio;
        this.strands = strands;
        setCordenateStrands();
        this.spider = new Spider();
        spider.moveTo(Spiderweb.getPoscenterImage()[0]-spider.getPosx(),Spiderweb.getPoscenterImage()[1]-spider.getPosy());
        this.SpiderSit = false;
        this.puentesPorLineas = new HashMap<Integer,ArrayList<Linea>>();
        spidertLastPath = new int[2];
        this.spiderLastRoute = new ArrayList<Linea>();
    }

    /**
     * Establece las coordenadas de los puntos de inicio de las líneas en la lista de líneas (listaLineas)
     * utilizando los valores de las coordenadas almacenadas en la lista 'lis'.
     * Cada par de coordenadas (x, y) en 'lis' se utiliza para crear una nueva línea con origen en esas coordenadas
     * y se agrega a la lista de líneas.
     */
    private void setCordenateStrands(){
        for(int i = 0; i < lis.size(); i++){
            float x = (float)lis.get(i).getA();
            float y = (float)lis.get(i).getB();
            Linea temp = new Linea(x,y);
            listaLineas.add(temp);
        }
    }
    
    /**
     * Hace visible todos los componentes del simulador.
     */
    public void makeVisible(){
        if(!isVisible){
            for(Linea lineasValor: listaLineas){
                lineasValor.makeVisible();
            }
            spider.makeVisible();
            for(String color: bridges.keySet()){
                for(Linea bridge : bridges.get(color)){
                    bridge.makeVisible();
                }
            }  
            for(String color: spots.keySet()){
                for(Circle spot : spots.get(color)){
                    spot.makeVisible();
                }
            }  
            if(SpiderSit){
                avisador.makeVisible();
            }
            isVisible = true;
            visibleRoute();
        }
    }
    
    /**
     * Hace invisible cada una de las líneas en la lista de líneas.
     * Si la lista de líneas está vacía, no se realiza ninguna acción.
     */
    public void makeInvisible(){
        if(isVisible){
            for(Linea lineasValor: listaLineas){
                lineasValor.makeInvisible();
            }
            spider.makeInvisible();
            for(String color: bridges.keySet()){
                for(Linea bridge : bridges.get(color)){
                    bridge.makeInvisible();
                }
            }   
            for(String color: spots.keySet()){
                for(Circle spot : spots.get(color)){
                    spot.makeInvisible();
                }
            } 
            avisador.makeInvisible();
            isVisible = false;
            invisibleRoute();
        }
    }
    
    /**
     * Crea un puente entre dos hebras.
     *
     * @param firstStrand El índice de la primera hebra.
     * @param distance La distancia desde el centro del círculo al punto de unión del puente.
     * @param color El color del puente.
     * @return Una instancia de la clase Linea que representa el puente creado.
    */
    private Linea createBridge(int firstStrand, int distance, String color){
        double angleFirstStrand = (firstStrand-1)*angulo;
        double angleSecondStrand = (firstStrand)*angulo;
        float posx1 = Math.round(distance * Math.cos(Math.toRadians(angleFirstStrand)));
        float posy1 = Math.round(distance * Math.sin(Math.toRadians(angleFirstStrand)));
        float posx2 = Math.round(distance * Math.cos(Math.toRadians(angleSecondStrand)));
        float posy2 = Math.round(distance * Math.sin(Math.toRadians(angleSecondStrand)));
        Linea bridge = new Linea(centroImagenX+posx1,centroImagenY-posy1,centroImagenX+posx2,centroImagenY-posy2);
        bridge.changeColor(color);
        bridge.makeVisible();
        bridge.setInitStrand(firstStrand);
        return bridge;
    }
    
    /**
     * Crea un puente entre dos hebras.
     *
     * @param firstStrand El índice de la primera hebra.
     * @param distance La distancia desde el centro del círculo al punto de unión del puente.
     * @param color El color del puente.
     * @return Una instancia de la clase Linea que representa el puente creado.
    */
    private Linea createBridge2(int firstStrand, int distance) {
        double angleFirstStrand = (firstStrand-1)*angulo;
        double angleSecondStrand = (firstStrand)*angulo;
        float posx1 = Math.round(distance * Math.cos(Math.toRadians(angleFirstStrand)));
        float posy1 = Math.round(distance * Math.sin(Math.toRadians(angleFirstStrand)));
        float posx2 = Math.round(distance * Math.cos(Math.toRadians(angleSecondStrand)));
        float posy2 = Math.round(distance * Math.sin(Math.toRadians(angleSecondStrand)));
        Linea bridge = new Linea(centroImagenX + posx2, centroImagenY - posy2, centroImagenX + posx1, centroImagenY - posy1);
        return bridge;
    }     
    
    /**
     * Agrega un puente entre dos puntos en la pantalla, dados un color, una distancia y una coordenada inicial.
     * El puente se crea entre la coordenada inicial y la coordenada siguiente en el círculo alrededor del punto central.
     * 
     * @param color El color del puente.
     * @param distance La distancia desde el punto central donde se ubicará el puente.
     * @param firstStrand La coordenada inicial del puente en grados (de 1 a 360) alrededor del punto central.
     **/
    public void addBridge(String color, int distance, int firstStrand){
        if(distance > radio){
            if(isVisible){JOptionPane.showMessageDialog(null, "No se puede poder un puente a una distancia mayor del limite de las arañas");}
            isOk = false;
        }else if(firstStrand > strands){
            if(isVisible){JOptionPane.showMessageDialog(null, "No se puede poder un puente en un hilo que no existe");
            }isOk = false;
        }else{
            Linea bridge = createBridge(firstStrand, distance, color);
            Linea bridge2 = createBridge2(firstStrand, distance);
            addBridgeInStrand(bridge,firstStrand-1);
            bridge.setDirection("izq");
            addBridgeInStrand(bridge2,firstStrand);
            bridge2.setDirection("der");
            if(bridges.containsKey(color)){
                bridges.get(color).add(bridge);
            }else{
                ArrayList<Linea> puente = new ArrayList<Linea>();
                puente.add(bridge);
                bridges.put(color,puente);
            }isOk = true;
        } 
    }
    
    //Añade los puentes a el hilo correpondiente en un hashMap
    private void addBridgeInStrand(Linea puente, int strandi){
        if(strandi == strands){
            strandi = 0;
        }
        if(puentesPorLineas.containsKey(strandi)){
            puentesPorLineas.get(strandi).add(puente);
        }else{
            ArrayList<Linea> puent = new ArrayList<Linea>();
            puent.add(puente);
            puentesPorLineas.put(strandi,puent);
        }
    }
    
    public static int[] getPoscenterImage(){
        int[] listaPoscionesCentroimagen = {centroImagenX, centroImagenY};
        return listaPoscionesCentroimagen;
    }
    
    /**
     * Reubica los puentes de un color específico a una distancia determinada desde el centro de la telaraña.
     *
     * @param color El color del puente que se va a reubicar.
     * @param distance La nueva distancia desde el centro del círculo al punto de unión del puente.
    */
    public void relocateBridge(String color, int distance){
        if(distance > radio){
            if(isVisible){JOptionPane.showMessageDialog(null, "No se puede reubicar los puentes a una distancia mayor del limite de las arañas");
            }isOk = false;
        }else if(!bridges.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "No hay ningun puente con ese color");
            }isOk = false;
        }else{
            for(Linea bridge : bridges.get(color)){
                double angleFirstStrand = (bridge.getInitStrand()-1)*angulo;
                double angleSecondStrand = (bridge.getInitStrand())*angulo;
                float posx1 = Math.round(distance * Math.cos(Math.toRadians(angleFirstStrand)));
                float posy1 = Math.round(distance * Math.sin(Math.toRadians(angleFirstStrand)));
                float posx2 = Math.round(distance * Math.cos(Math.toRadians(angleSecondStrand)));
                float posy2 = Math.round(distance * Math.sin(Math.toRadians(angleSecondStrand)));
                bridge.newPoints(centroImagenX+posx1,centroImagenY-posy1,centroImagenX+posx2,centroImagenY-posy2);
            }isOk = true;
        }
    }
    
    /**
     * Elimina los puentes de la telaraña basado en su color.
     *
     * @param color El color del puente que se desea eliminar.
     * @param cod_Bridge El código del puente que se desea eliminar.
     */
    public void delBridge(String color){
        if(!bridges.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "No existe ningun puente con dicho color");
            }isOk = false;
        }else{
            for(Linea bridge : bridges.get(color)){
                bridge.makeInvisible();   
            }
            bridges.get(color).clear();
            isOk = true;
        }
    }
    
    /**
     * añade un lugar favorito de la araña para dormir, representado por un circulo
     * @param color El color del circulo
     * @param Strand El hilo donde se pondrá el lugar
    */
    public void addSpot(String color, int strand){
        if(strand > strands){
            if(isVisible){JOptionPane.showMessageDialog(null, "No puedes poner un spot en un hilo inexistente");
            }isOk = false;
        }else{
            Linea hilo = listaLineas.get(strand-1);
            Circle spot = new Circle((int)hilo.getX2(),(int)hilo.getY2());
            spot.changeColor(color);
            spot.makeVisible();
            spot.setStrand(strand);
            if(spots.containsKey(color)){
                spots.get(color).add(spot);
            }else{
                ArrayList<Circle> lugar = new ArrayList<Circle>();
                lugar.add(spot);
                spots.put(color,lugar);
            }isOk = true;
        }
        
    }
    
    /**
     * elimina los lugares favoritos de la araña para dormir, clasificados por un color
     * @param color El color de los circulos a eliminar
     */
    public void delSpot( String color){
        if(!spots.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "No existen spots de ese color");
            }isOk = false;
        }else{
            for(Circle spot : spots.get(color)){
                spot.makeInvisible();
            }
            spots.get(color).clear();
            isOk = true;
        }
    }
    
    /**
     * cambia el estado de la araña de parada a sentada y visceversa
     */
    public void spiderSit(){
        if(!SpiderSit){
            this.SpiderSit = true;
            avisador.makeVisible();
        }else{
            this.SpiderSit = false;
            avisador.makeInvisible();
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
    private double getDistanceCenterBridge(Linea bridge){
        return getDistance((float)centroImagenX,(float)centroImagenY,bridge.getX1(),bridge.getY1());
    }
    
    // retorna la distancia entre la Spider y un puente
    private double getDistanceSpiderBridge(Linea bridge){
        return getDistance((float)spider.getPosx(),(float)spider.getPosy(),bridge.getX1(),bridge.getY1());
    }
    
    /**
     * Especifica en que hilo quieres que la araña empiece su recorrido
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
     * Permite mover la araña para adelante (true) o hacia atrás (false) automáticamente por sus puentes 
     * de manera automática
     * @param advance un booleano que establece si la araña avanza o retrocede
     */
    public void siperWalk(boolean advance){
        if(advance){
            spiderWalk();    
        }else{
            spiderRetroceder();
        }
    }
    
    //puente más cercano a la araña en un hilo
    private Linea findCLoserBridge(int hiloActual){
        Linea puenteCercano = null;
        if(!puentesPorLineas.containsKey(hiloActual)){
            return puenteCercano;
        }else{
            double distanceMin = Double.POSITIVE_INFINITY;
            for(Linea hilo : puentesPorLineas.get(hiloActual)){
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
        float X  =  listaLineas.get(hiloActual-1).getX2() - spider.getPosx();
        float Y  = listaLineas.get(hiloActual-1).getY2() - spider.getPosy();
        addLastRoute(spider.getPosx(),spider.getPosy(),listaLineas.get(hiloActual-1).getX2(),listaLineas.get(hiloActual-1).getY2());
        spider.moveTo((int)X, (int)Y);
        visibleRoute();
    }
    
    //mover y cruzar el puente
    private void MoverYpasarPuente(Linea puenteCercano){
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
    private int strandActual(Linea puenteCercano, int hiloActual){
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
                Linea puenteCercano =findCLoserBridge(hiloActual-1);
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
    private Linea findCLoserBridgeRet(int hiloActual){
        Linea puenteCercano = null;
        if(!puentesPorLineas.containsKey(hiloActual)){
            return puenteCercano;
        }else{
            double distanceMin = Double.POSITIVE_INFINITY;
            for(Linea hilo : puentesPorLineas.get(hiloActual)){
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
                Linea puenteCercano =findCLoserBridgeRet(hiloActual-1);
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
     * Dice si la araña está sentada o no
     * @return SpiderSit un booleano
     */
    public boolean isSpiderSit(){
        isOk = true;
        return SpiderSit;
    }
    
    /**
     * retorna la ultima posicion de la araña
     * @return un arreglo indicando el punto x,y anterior de la araña
     */
    private int[] spiderLastPath(){
        return spidertLastPath;
    }
    
    /**
     * da todos los colores de puentes que existen
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
     * da todos los puentes de un color dado que existen
     * @param color el color de los puentes a consultar
     * @return un arreglo de enteros con los puentes del color
     */
    public int[] bridge(String color){
        if(!bridges.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "No existen puentes de ese color");} 
            int[] vacio = {};
            isOk = false;
            return vacio;
        }else{
            ArrayList<Linea> puentes = bridges.get(color);
            int[] numStrands = new int[puentes.size()];
            int i = 0;
            for(Linea puente: puentes){
                numStrands[i] = puente.getInitStrand();
                i++;
            }
            isOk = true;
            return numStrands;
        }
    }
    
    /**
     * da todos los colores de spots que existen
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
     * da todos los spots de un color dado que existen
     * @param color el color de los lugares a consultar
     * @return un arreglo de enteros con los lugar del color
     */
    public int[] spot(String color){
        if(!spots.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "No existen spots de ese color");} 
            int[] vacio = {};
            isOk = false;
            return vacio;
        }else{
            ArrayList<Circle> lugares = spots.get(color);
            int[] numStrands = new int[lugares.size()];
            int i = 0;
            for(Circle lugar: lugares){
                numStrands[i] = lugar.getStrand();
                i++;
            }
            isOk = true;
            return numStrands;
        }
    }
    
    /**
     * termina el simulador
     */
    public void finish(){
         this.makeInvisible();
         this.listaLineas = null;
         this.lista = null;
         this.lis = null;
         this.spider = null;
         this.bridges = null;
         this.spots = null;
         this.avisador = null;
         isOk = true;
    }
    
    /**
     * retorna si el ultimo movimiento fue valido
     * @return isOk un booleano
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



