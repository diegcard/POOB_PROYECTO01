import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.JOptionPane;
/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */

public class Linea{
    private String color;
    private boolean isVisible;
    private float x1;
    private float x2;
    private float y1;
    private float y2;
    /**
     * Create a new Line with a static start position
     */
    public Linea(float x2, float y2){
        color = "black";
        isVisible = false;
        this.x1 = 400; //initial value
        this.y1 = 400; //initial value
        this.x2 = x1+x2;
        this.y2 = y1-y2;
    }
    
    /**
     * Create a Line
     */
    public Linea(float x1, float y1,float x2, float y2){
        color = "black";
        isVisible = false;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    /**
     * news points
     */
    public void newPoints(float newx1, float newy1,float newx2, float newy2){
        erase();
        this.x1 = newx1;
        this.y1 = newy1;
        this.x2 = newx2;
        this.y2 = newy2;
        draw();
    }
    public void changePositionInitial(float x1, float y1){
        if(this.x1 == x1 || this.y1 == y1){
            JOptionPane.showMessageDialog(null, "No se puede ingresar la misma coordenada de x y y ");
        }else{
            this.x1 = x1;
            this.y1 = y1;
        }
    }
    
    /**
     * Devuelve el valor de la coordenada x1.
     * 
     * @return el valor de la coordenada x1
     */
    public float getX1(){
        return x1;
    }
    
    /**
     * Devuelve el valor de la coordenada x2.
     * 
     * @return el valor de la coordenada x2
     */
    public float getX2(){
        return x2;
    }
    
    /**
     * Devuelve el valor de la coordenada y1.
     * 
     * @return el valor de la coordenada y1
     */
    public float getY1(){
        return y1;
    }
    
    /**
     * Devuelve el valor de la coordenada y2.
     * 
     * @return el valor de la coordenada y2
     */
    public float getY2(){
        return y2;
    }
    
    /**
     * Make this triangle visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this triangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    public void changeColor(String newcolor){
        color = newcolor;
    }
    
    public void moveHorizontal( int distancia){
        erase();
        x1 += distancia;
        x2 += distancia;
        draw();
    }
    
    public void moveVertical( int distancia){
        erase();
        y1 += distancia;
        y2 += distancia;
        draw();
    }
    
    /*
     * Draw the line with current specifications on screen.
     */
    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            Line2D line = new Line2D.Float(x1, y1, x2, y2);
            canvas.draw(this, color, line);
            canvas.wait(10);
        }
    }
    
    /*
     * Erase the line on screen.
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

}
