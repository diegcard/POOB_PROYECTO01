import java.util.List;
import java.util.ArrayList;

/**
 * Clase que representa las coordenadas de líneas en un plano.
 * Las coordenadas se generan en función de un radio y una cantidad de líneas.
 * Cada línea está definida por un par de coordenadas (x, y) en formato Double.
 * 
 * @author Cardenas Cardona
 * @version 1.0
 */
class Pair<A, B> {
    public final A first;
    public final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
    
    public double getA(){
        return (double)first;
    }
    
    public double getB(){
        return (double)second;
    }
}

public class Linescoordinates{
    private List<Pair<Double, Double>> lista;
    private double cant;
    
    /**
     * Constructor de la clase Linescoordinates.
     * Genera las coordenadas de las líneas en función de la cantidad y el radio especificados.
     * 
     * @param cantidad La cantidad de líneas a generar.
     * @param radio El radio del plano en el que se generan las líneas.
     */
    public Linescoordinates(int cantidad, int radio){
        this.lista = new ArrayList<>();
        final double anguloTotal = 360;
        double ange = 0;
        this.cant = anguloTotal / cantidad;
        while (ange < anguloTotal){
            lista.add(new Pair<>(getPosXPlane(radio, ange), getPosYPlane(radio, ange)));
            ange+=cant;
        }
    }
    
    /**
     * Obtiene el incremento angular entre cada línea generada.
     * 
     * @return El incremento angular entre líneas.
     */
    public double getCant(){
        return cant;
    }
    
    /**
     * Obtiene la lista de coordenadas de las líneas generadas.
     * 
     * @return La lista de coordenadas de las líneas.
     */ 
    public List<Pair<Double, Double>> getlista(){
        return lista;
    }
    
    private double getPosXPlane(double r, double angle){
        return Math.round((r * Math.cos(Math.toRadians(angle))) * 100.0) / 100.0;
    }
    
    private double getPosYPlane(double r, double angle){
        return Math.round((r * Math.sin(Math.toRadians(angle))) * 100.0) / 100.0;
    }
}
