import java.util.List;
import java.util.ArrayList;
/**
 * Write a description of class Spiderweb here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spiderweb{
    private int strands;
    private int radio;
    private lines lista;
    private ArrayList<Linea> listaLineas;
    private List<Pair<Double, Double>> lis;
    
    /**
     * 
     */
    public Spiderweb(int strands, int radio){
        this.lista = new lines(strands, radio);
        this.lis = lista.getlista();
        this.listaLineas = new ArrayList<Linea>();    
        setCordenateStrands();
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
    private void addBridge(String color, int distance, int firstStrand){
        if(radio == 4){
            System.out.println("Algo");
        }
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
