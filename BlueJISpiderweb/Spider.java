
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
    private Linea pata1;
    private Linea pata2;
    private Linea pata3;
    private Linea pata4;
    private Linea pata31;
    private Linea pata41;
    private Linea pata5;
    private Linea pata6;
    private Linea pata51;
    private Linea pata61;
    private Linea pata7;
    private Linea pata8;
    private Linea pata71;
    private Linea pata81;   

    
    /**
     * Constructor de la clase Spider.
     * Inicializa una nueva araña con cabeza y cuerpo, y posiciona sus componentes.
     */
    public Spider()
    {
        this.cabeza = new Circle();
        this.cuerpo = new Circle();
        initDrawSpider();
    }
    
    /**
     * Obtiene la posición horizontal del cuerpo.
     * 
     * @return La posición horizontal del cuerpo.
     */
    public int getPosx(){
        return cuerpo.getxPosition()+cuerpo.getDiameter()/2;
    }
    
    /**
     * Obtiene la posición vertical del cuerpo.
     * 
     * @return La posición vertical del cuerpo.
     */
    public int getPosy(){
        return cuerpo.getyPosition()+cuerpo.getDiameter()/2;
    }

    /**
     * Inicializa el dibujo de la araña, creando y posicionando los componentes.
     */
    private void initDrawSpider(){
        reorPatasDelanteras();
        reorPatasTraseras();
        cuerpo.changeColor("red");
        cabeza.changeColor("blue");
        reordenarCabeza();
    }
    
     // Métodos privados para reorganizar las patas delanteras y traseras
    
    // Método para mover las patas traseras de la araña
    private void reorPatasDelanteras(){
        this.pata1 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()-cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        this.pata2 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        
        this.pata3 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20);
        this.pata4 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()-cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20);
        
        this.pata31 = new Linea(cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20,
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/10,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        this.pata41 = new Linea(cuerpo.getxPosition()-cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20,
        cuerpo.getxPosition()-cabeza.getDiameter()/10,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
    }
    
    // Método para mover las patas delanteras de la araña
    private void reorPatasTraseras(){
        this.pata5 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8);
        this.pata6 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()-cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8);
        
        this.pata51 = new Linea(cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8,
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        this.pata61 = new Linea(cuerpo.getxPosition()-cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8,
        cuerpo.getxPosition()-cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        
        this.pata7 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4);
        this.pata8 = new Linea((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()-cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4);
        
        this.pata71 = new Linea(cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4,
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/3,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        this.pata81 = new Linea(cuerpo.getxPosition()-cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4,
        cuerpo.getxPosition()-cabeza.getDiameter()/3,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
    }
    // Otros métodos de la clase
    
    private void reordenarCabeza(){
        cabeza.changeSize(cuerpo.getDiameter()/3);
        cabeza.moveHorizontal(cuerpo.getxPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/2-cabeza.getxPosition());
        cabeza.moveVertical(cuerpo.getyPosition()+cuerpo.getDiameter()-cabeza.getDiameter()/2-cabeza.getyPosition());
    }
    
    private void movePatasTraseras(int x, int y){
        pata5.moveHorizontal(x);
        pata5.moveVertical(y);
        pata6.moveHorizontal(x);
        pata6.moveVertical(y);
        pata51.moveHorizontal(x);
        pata51.moveVertical(y);
        pata61.moveHorizontal(x);
        pata61.moveVertical(y);
        pata7.moveHorizontal(x);
        pata7.moveVertical(y);
        pata8.moveHorizontal(x);
        pata8.moveVertical(y);
        pata71.moveHorizontal(x);
        pata71.moveVertical(y);
        pata81.moveHorizontal(x);
        pata81.moveVertical(y);
    }
    
    private void movePatasDelanteras(int x, int y){
        pata1.moveHorizontal(x);
        pata1.moveVertical(y);
        pata2.moveHorizontal(x);
        pata2.moveVertical(y);
        pata3.moveHorizontal(x);
        pata3.moveVertical(y);
        pata4.moveHorizontal(x);
        pata4.moveVertical(y);
        pata31.moveHorizontal(x);
        pata31.moveVertical(y);
        pata41.moveHorizontal(x);
        pata41.moveVertical(y);
    }
    
    public void moveTo(int x, int y){
        movePatasDelanteras(x,y);
        movePatasTraseras(x,y);
        cabeza.moveHorizontal(x);
        cabeza.moveVertical(y);
        cuerpo.moveHorizontal(x);
        cuerpo.moveVertical(y);
    }
    
    /**
     * Hace visible el objeto.
     */
    public void makeVisible(){
        pata1.makeVisible();
        pata2.makeVisible();
        pata3.makeVisible();
        pata4.makeVisible();
        pata31.makeVisible();
        pata41.makeVisible();
        pata5.makeVisible();
        pata6.makeVisible();
        pata51.makeVisible();
        pata61.makeVisible();
        pata7.makeVisible();
        pata8.makeVisible();
        pata71.makeVisible();
        pata81.makeVisible();
        cuerpo.makeVisible();
        cabeza.makeVisible();
    }  
    
        /**
     * make spider invisible
     */
    public void makeInvisible(){
        pata1.makeInvisible();
        pata2.makeInvisible();
        pata3.makeInvisible();
        pata4.makeInvisible();
        pata31.makeInvisible();
        pata41.makeInvisible();
        pata5.makeInvisible();
        pata6.makeInvisible();
        pata51.makeInvisible();
        pata61.makeInvisible();
        pata7.makeInvisible();
        pata8.makeInvisible();
        pata71.makeInvisible();
        pata81.makeInvisible();
        cuerpo.makeInvisible();
        cabeza.makeInvisible();

    }  
    
    /**
     * Cambiar el tamaño de la araña
     * @param newSize el nuevo tamaño
     */
    public void changeSize(int newSize){
        cuerpo.changeSize((int)(cuerpo.getDiameter() + cuerpo.getDiameter()*((float)newSize/100)));
        cabeza.changeSize((int)(cabeza.getDiameter() + cabeza.getDiameter()*((float)newSize/100)));
        reorPatasDelanteras1();
        reorPatasTraseras1();
        reordenarCabeza();
        cuerpo.makeVisible();
    }
    
    // Otros métodos de la clase
    private void reorPatasDelanteras1(){
        pata1.newPoints((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()-cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        pata2.newPoints((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        
        pata3.newPoints((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20);
        pata4.newPoints((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()-cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20);
        
        pata31.newPoints(cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20,
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/10,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        pata41.newPoints(cuerpo.getxPosition()-cabeza.getDiameter()/6,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/20,
        cuerpo.getxPosition()-cabeza.getDiameter()/10,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
    }
    
    private void reorPatasTraseras1(){
        pata5.newPoints((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8);
        pata6.newPoints((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()-cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8);
        
        pata51.newPoints(cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8,
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        pata61.newPoints(cuerpo.getxPosition()-cabeza.getDiameter()/4,cuerpo.getyPosition()+cuerpo.getDiameter()/2-cabeza.getDiameter()/8,
        cuerpo.getxPosition()-cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        
        pata7.newPoints((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4);
        pata8.newPoints((cuerpo.getxPosition() + cuerpo.getDiameter()/2),(cuerpo.getyPosition() + cuerpo.getDiameter()/2),
        cuerpo.getxPosition()-cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4);
        
        pata71.newPoints(cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4,
        cuerpo.getxPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/3,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
        pata81.newPoints(cuerpo.getxPosition()-cabeza.getDiameter()/2,cuerpo.getyPosition()+cuerpo.getDiameter()/4,
        cuerpo.getxPosition()-cabeza.getDiameter()/3,cuerpo.getyPosition()+cuerpo.getDiameter()+cabeza.getDiameter()/4);
    }
    
}
