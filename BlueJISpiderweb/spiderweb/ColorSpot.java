package spiderweb;


/**
 * Write a description of class ReturnerColorSpot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ColorSpot extends Spot{
    
    /**
     * Constructor for objects of class ReturnerColorSpot
     */
    public ColorSpot(int xPos, int yPos, int strand, String color) {
        super(xPos, yPos, strand, color);
        changeIdentificatorColor("purple");
    }
}
