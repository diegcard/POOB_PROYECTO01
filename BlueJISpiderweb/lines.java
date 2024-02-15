import java.util.List;
import java.util.ArrayList;

/**
 * Write a description of class lines here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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

public class lines{
    private List<Pair<Double, Double>> lista;
    public lines(int cantidad, int radio){
        this.lista = new ArrayList<>();
        final double anguloTotal = 360;
        double ange = 0;
        double cant = anguloTotal / cantidad;
        while (ange < anguloTotal){
            lista.add(new Pair<>(getPosXPlane(radio, ange), getPosYPlane(radio, ange)));
            ange+=cant;
        }
    }
    
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
