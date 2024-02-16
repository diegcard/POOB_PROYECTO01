import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 * Aqui se hará el simulador del Spiderweb
 * 
 * @author Diego Cardenas y Sebastian Cardona 
 * @version 1.0
 */
public class Spiderweb{
    private int strands;
    private int radio;
    private Linescoordinates lista;
    private ArrayList<Linea> listaLineas;
    private List<Pair<Double, Double>> lis;
    private Spider arañita;
    private double angulo;
    private ArrayList<Linea> bridges;
    
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
        this.bridges = new ArrayList<Linea>();
        setCordenateStrands();
        this.arañita = new Spider();
        arañita.moveTo(365,365);
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
     * Hace visible cada una de las líneas en la lista de líneas y también hace visible la arañita.
     * Si la lista de líneas está vacía, no se realiza ninguna acción.
     */
    public void makeVisible(){
        for(Linea lineasValor: listaLineas){
            lineasValor.makeVisible();
            arañita.makeVisible();
        }
    }
    
    /**
     * Hace invisible cada una de las líneas en la lista de líneas.
     * Si la lista de líneas está vacía, no se realiza ninguna acción.
     */
    public void makeInvisible(){
        for(Linea lineasValor: listaLineas){
            lineasValor.makeInvisible();
        }
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
        if(firstStrand > radio){
            JOptionPane.showMessageDialog(null, "No se puede poder un puente a una distancia mayor a la de las telerañas");
        }else{
            double angleFirstStrand = (firstStrand-1)*angulo;
            double angleSecondStrand = (firstStrand)*angulo;
            double posx1 = Math.round((distance * Math.cos(Math.toRadians(angleFirstStrand))));
            double posy1 = Math.round((distance * Math.sin(Math.toRadians(angleFirstStrand))));
            double posx2 = Math.round((distance * Math.cos(Math.toRadians(angleSecondStrand))) * 100.0) / 100.0;
            double posy2 = Math.round((distance * Math.sin(Math.toRadians(angleSecondStrand))) * 100.0) / 100.0;
            Linea bridge = new Linea(400+(float)posx1,400-(float)posy1,400+(float)posx2,400-(float)posy2);
            bridge.changeColor(color);
            bridge.makeVisible();
            bridges.add(bridge);
        }
    }
    
    /**
     * 
     */
    private void relocateBridge(String color, int distance){
    
    }
    
    /**
     * Elimina un puente de la telaraña basado en su color y código de puente.
     *
     * @param color El color del puente que se desea eliminar.
     * @param cod_Bridge El código del puente que se desea eliminar.
     */
    private void delBridge(String color, int cod_Bridge){
        if(cod_Bridge > bridges.size()){
            JOptionPane.showMessageDialog(null, "No se puede eliminar un puente que no existe");
        }else{
            Linea bridge = bridges.get(cod_Bridge);
            bridge.makeInvisible();
            bridges.remove(cod_Bridge);
        }
    }
    
    /**
     * 
     */
    private void addSpot(String color, int strand){
    
    }
    
    /**
     * 
     */
    private void delSpot(String color){
    
    }
    
    /**
     * 
     */
    private void spiderSit(int strand){
    
    }
    
    /**
     * 
     */
    private void spiderWalk(boolean advance){
    
    }
}
