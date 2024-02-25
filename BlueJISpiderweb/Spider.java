import java.util.*;

/**
 * Representa una araña con componentes específicos como cabeza, cuerpo y patas.
 * Permite mover, hacer visible e invisible la araña, así como cambiar su tamaño.
 * Además, inicializa y posiciona los componentes de la araña.
 * 
 * @author Diego Cárdenas y Sebastián Cardona
 * @version 1.0
 */
public class Spider
{
    private Circle cabeza;
    private Circle cuerpo;
    private ArrayList<Linea> patas;
    private boolean isVisible =false;

    /**
     * Class spider contructor
     * Init the new Spider with its all components
     */
    public Spider()
    {
        this.cabeza = new Circle();
        this.cuerpo = new Circle();
        this.patas = new ArrayList<Linea>();
        initDrawSpider();
    }
    
    /**
     * get x coordenate
     * 
     * @return x coordenate
     */
    public int getPosx(){
        return cuerpo.getxPosition()+cuerpo.getDiameter()/2;
    }
    
    /**
     * get y coordenate
     * 
     * @return y coordenate
     */
    public int getPosy(){
        return cuerpo.getyPosition()+cuerpo.getDiameter()/2;
    }

    // make the spider
    private void initDrawSpider(){
        reorPatas();
        cuerpo.changeColor("red");
        cabeza.changeColor("red");
        reordenarCabeza();
    }
    
     // Métodos privados para reorganizar las patas delanteras y traseras
    
    // Método para reordenar todas las patas juntas
    private void reorPatas(){
        patas.clear();
        reorPatasDelanteras();
        reorPatasTraseras();
    }
    
    // Método para mover las patas traseras de la araña
    private void reorPatasDelanteras(){
        Linea pata1 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()-cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        patas.add(pata1);
        Linea pata2 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        patas.add(pata2);
        Linea pata3 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20);
        patas.add(pata3);
        Linea pata4 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()-cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20);
        patas.add(pata4);
        Linea pata31 = new Linea(cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20,
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/10,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        patas.add(pata31);
        Linea pata41 = new Linea(cuerpo.getxPosition()-cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20,
        cuerpo.getxPosition()-cabeza.getDiameter()/10,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        patas.add(pata41);
    }
    
    // Método para mover las patas delanteras de la araña
    private void reorPatasTraseras(){
        Linea pata5 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8);
        Linea pata6 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()-cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8);
        patas.add(pata5);patas.add(pata6);
        Linea pata51 = new Linea(cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8,
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        Linea pata61 = new Linea(cuerpo.getxPosition()-cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8,
        cuerpo.getxPosition()-cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        patas.add(pata51);patas.add(pata61);
        Linea pata7 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4);
        Linea pata8 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()-cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4);
        patas.add(pata7);patas.add(pata8);
        Linea pata71 = new Linea(cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4,
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/3,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        Linea pata81 = new Linea(cuerpo.getxPosition()-cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4,
        cuerpo.getxPosition()-cabeza.getDiameter()/3,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        patas.add(pata71);patas.add(pata81);
    }
    // Otros métodos de la clase
    
    private void reordenarCabeza(){
        cabeza.changeSize(cuerpo.getDiameter()/3);
        cabeza.moveHorizontal(cuerpo.getxPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/2-cabeza.getxPosition());
        cabeza.moveVertical(cuerpo.getyPosition()+cuerpo.getDiameter()-cabeza.getDiameter()/2-cabeza.getyPosition());
    }
    
    private void movePatas(int x, int y){
        for(Linea pata: patas){
            pata.moveHorizontal(x);
            pata.moveVertical(y);
        }
    }
    
    /**
     * Move the Spider to new place
     * @param x coordenate x
     * @param y coordenate y
     */
    public void moveTo(int x, int y){
        movePatas(x,y);
        cuerpo.moveHorizontal(x);
        cuerpo.moveVertical(y);
        cabeza.moveHorizontal(x);
        cabeza.moveVertical(y);
    }
    
    /**
     * make spider visible
     */
    public void makeVisible(){
        if(!isVisible){
            for(Linea pata: patas){
                pata.makeVisible();
            }
            cuerpo.makeVisible();
            cabeza.makeVisible();
            isVisible = true;
        }
    }  
    
    /**
     * make spider invisible
     */
    public void makeInvisible(){
        if(isVisible){
            for(Linea pata: patas){
                pata.makeInvisible();
            }
            cuerpo.makeInvisible();
            cabeza.makeInvisible();
            isVisible = false;
        }
    }  
    
    /**
     * Change Spider Size
     * @param newSize new size
     */
    public void changeSize(int newSize){
        cuerpo.changeSize((int)(cuerpo.getDiameter() + cuerpo.getDiameter()*((float)newSize/100)));
        cabeza.changeSize((int)(cabeza.getDiameter() + cabeza.getDiameter()*((float)newSize/100)));
        for(Linea pata: patas){
            pata.makeInvisible();
        }
        reorPatas();
        if(isVisible){
            for(Linea pata: patas){
                pata.makeVisible();
            }
        }
        cuerpo.changeColor("red");
        reordenarCabeza();
    }
    
    /**
     * Change spider's head color to indicate when the spider is sit
     */
    public void headChangeColor(String newColor){
        cabeza.changeColor(newColor);
    }
}
