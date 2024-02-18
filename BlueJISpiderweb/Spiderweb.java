import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.HashMap;
/**
 * Aqui se hará el simulador del Spiderweb
 * 
 * @author Diego Cardenas y Sebastian Cardona 
 * @version 1.0
 */
public class Spiderweb{
    private boolean isVisible;
    private int strands;
    private int radio;
    private Linescoordinates lista;
    private ArrayList<Linea> listaLineas;
    private List<Pair<Double, Double>> lis;
    private Spider spider;
    private double angulo;
    private HashMap<String,ArrayList<Linea>> bridges;
    private static final int centroImagenX = 500;
    private static final int centroImagenY = 400;
    private boolean SpiderSit;
    private HashMap<String,ArrayList<Circle>> spots;
    private Triangle avisador = new Triangle(20,20);
    private boolean isOk = true;
    
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
        spider.moveTo(Spiderweb.getPoscenterImage()[0]-35,Spiderweb.getPoscenterImage()[1]-35);
        this.SpiderSit = false;
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
        }
    }
    
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
            if(bridges.containsKey(color)){
                bridges.get(color).add(bridge);
            }else{
                ArrayList<Linea> puente = new ArrayList<Linea>();
                puente.add(bridge);
                bridges.put(color,puente);
            }isOk = true;
        } 
    }
    
    public static int[] getPoscenterImage(){
        int[] listaPoscionesCentroimagen = {centroImagenX, centroImagenY};
        return listaPoscionesCentroimagen;
    }
    
    /**
     * 
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
     * Elimina un puente de la telaraña basado en su color y código de puente.
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
     * 
     */
    public void addSpot(String color, int strand){
        if(strand > radio){
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
     * 
     */
    public void delSpot( String color){
        if(!spots.containsKey(color)){
            if(isVisible){JOptionPane.showMessageDialog(null, "No existen spots de ese color");
            }isOk = true;
        }else{
            for(Circle spot : spots.get(color)){
                spot.makeInvisible();
            }
            spots.get(color).clear();
            isOk = true;
        }
    }
    
    /**
     * 
     */
    public void spiderSit(){
        if(SpiderSit == false){
            this.SpiderSit = true;
            avisador.makeVisible();
        }else{
            this.SpiderSit = false;
            avisador.makeInvisible();
        }isOk = true;
    }
    
    public boolean isSpiderSit(){
        isOk = true;
        return SpiderSit;
    }
    
    /**
     * 
     */
    private void spiderWalk(boolean advance){
    
    }
    
    /**
     * 
     */
    private int[] spiderLastPath(){
        return null;
    }
    
    /**
     * 
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
     * 
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
     * 
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
     * 
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
     * 
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
    
    public boolean ok(){
        return isOk;
    }
}
