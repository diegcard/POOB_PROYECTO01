
/**
 * This class maintains the main characteristics of a spider web bridge
 * 
 * @author Cardenas-Cardona
 * @version 1.0
 */
public class Bridge{
    private Linea bridge;
    private boolean isVisible = false;
    private int initStrand;
    private String direction;

    /**
     * Constructor for objects of class Bridge
     */
    public Bridge(float x1, float y1,float x2, float y2){
        this.bridge = new Linea(x1,y1,x2,y2);
    }

    /**
     * Updates the coordinates of the bridge endpoints with the new specified coordinates
     *
     * @param newx1 The new x coordinate of the first end of the line.
     * @param newy1 The new y coordinate of the first end of the line.
     * @param newx2 The new x coordinate of the second end of the line.
     * @param newy2 The new y coordinate of the second end of the line.
     */
    public void newPoints(float newx1, float newy1,float newx2, float newy2){
        this.bridge.newPoints(newx1,newy1,newx2,newy2);
    }
    
    /**
     * get the x1 coordenate
     * 
     * @return the value of the x1 coordenate
     */
    public float getX1(){
        return bridge.getX1();
    }
    
    /**
     * get the x2 coordenate
     * 
     * @return the value of the x2 coordenate
     */
    public float getX2(){
        return bridge.getX2();
    }
    
    /**
     * get the y1 coordenate
     * 
     * @return the value of the y1 coordenate
     */
    public float getY1(){
        return bridge.getY1();
    }
    
    /**
     * get the y2 coordenate
     * 
     * @return the value of the y2 coordenate
     */
    public float getY2(){
        return bridge.getY2();
    }
    
    /**
     * Make this Line visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        if(!isVisible){
            bridge.makeVisible();
            isVisible = true;
        }
    }
    
    /**
     * Make this Line invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        if(isVisible){
            bridge.makeInvisible();
            isVisible = false;
        }
    }
    
    /**
     * Change the Bridge color
     */
    public void changeColor(String newcolor){
        bridge.changeColor(newcolor);
    }
    
    /**
     * gets the value of the thread where the bridge starts
     * 
     * @return the value of initStrand.
     */
    public int getInitStrand(){
        return initStrand;
    }
    
    /**
     * sets the value of the thread where the bridge starts
     * 
     * @param newInit
     */
    public void setInitStrand(int newInitStrand){
       this.initStrand = newInitStrand;
    }
    
    /**
     * Gets the value of the direction of where a bridge is heading.
     * (der o izq)
     * @return El valor de initStrand.
     */
    public String getDirection(){
        return direction;
    }
    
    /**
     * Sets the value of the direction of where a bridge is heading.
     * (der o izq)
     * @param newInit
     */
    public void setDirection(String newDirection){
       this.direction = newDirection;
    }
}