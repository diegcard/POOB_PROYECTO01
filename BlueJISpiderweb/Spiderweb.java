import java.util.List;
import java.util.ArrayList;
/**
 * Aqui se hará el simulador del Spiderweb
 * 
 * @author Diego Cardenas y Sebastian Cardona 
 * @version 1.0
 */
public class Spiderweb{
    private int strands;
    private int radio;
    private lines lista;
    private ArrayList<Linea> listaLineas;
    private List<Pair<Double, Double>> lis;
    private Spider arañita;
    private double angulo;
    private ArrayList<Linea> bridges;
    
    /**
     * 
     */
    public Spiderweb(int strands, int radio){
        this.lista = new lines(strands, radio);
        this.angulo = lista.getCant();
        this.lis = lista.getlista();
        this.listaLineas = new ArrayList<Linea>();   
        this.bridges = new ArrayList<Linea>();
        setCordenateStrands();
        this.arañita = new Spider();
        arañita.moveTo(365,365);
    }
    
    private void setCordenateStrands(){
        for(int i = 0; i < lis.size(); i++){
            float x = (float)lis.get(i).getA();
            float y = (float)lis.get(i).getB();
            Linea temp = new Linea(x,y);
            listaLineas.add(temp);
        }
    }
    
    public void makeVisible(){
        for(Linea lineasValor: listaLineas){
            lineasValor.makeVisible();
            arañita.makeVisible();
        }
    }
    
    public void makeInvisible(){
        for(Linea lineasValor: listaLineas){
            lineasValor.makeInvisible();
        }
    }
    
    /**
     * 
     */
    public void addBridge(String color, int distance, int firstStrand){
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
    
    /**
     * 
     */
    private void relocateBridge(String color, int distance){
    
    }
    
    /**
     * 
     */
    private void delBridge(String color){
    
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
    private      void spiderWalk(boolean advance){
    
    }
}
