import java.util.*;

/**
 * this class draws the initial web without bridges
 * @author Cardenas-Cardona
 * @version 1.0
 */
public class Thread{
    private boolean isVisible;
    private Linescoordinates lista;
    private ArrayList<Linea> listaHilos;   //Lista de las lineas 
    private List<Pair<Double, Double>> lis;
    private double angulo;
    /**
     * Constructor for objects of class Thread
     */
    public Thread(int strands, int radio){
        this.lista = new Linescoordinates(strands, radio);
        this.angulo = lista.getAngle();
        this.lis = lista.getlista();
        this.listaHilos = new ArrayList<Linea>();
        setCordenateStrands();
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
            listaHilos.add(temp);
        }
    }
    
    /**
     * make red visible
     */
    public void makeVisible(){
        if(!isVisible){
            for(Linea hilo: listaHilos){
                hilo.makeVisible();
            }
            isVisible = true;
        }
    }
    
    /**
     * make red invisible
     */
    public void makeInvisible(){
        if(isVisible){
            for(Linea hilo: listaHilos){
                hilo.makeInvisible();
            }
            isVisible = false;
        }
    }
    
    /**
     * get all threads into a list
     * @return a arraylist with all threads
     */
    public ArrayList<Linea> getThreads(){
        return listaHilos;
    }
    
    /**
     * get the angle between each thread
     * @return a the angle
     */
    public double getAngle(){
        return angulo;
    }
}
