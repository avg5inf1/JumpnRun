import java.awt.event.*;

/**
 * Klasse PROJEKTILE.
 * 
 * @author M.Kraus
 * 
 * version 1.1
 */
public class PROJEKTILE extends ITEM implements KeyListener, MOVEABLE{
    /*---------------Attribute-----*/
    
    SPIELLOGIK meineSpielLogik;
    
    int x;
    int y;
    
    private boolean ePressed;
    
    /*---------------Konstruktor---*/
    public PROJEKTILE(SPIELLOGIK neueSpielLogik) {
        super(neueSpielLogik);
        meineSpielLogik = neueSpielLogik;
    }

    /*---------------Methoden------*/    

    public void useItem()
    {
        
    }
    
    public void keyPressed(KeyEvent e)
    {
        ePressed = true;
        meineSpielLogik.itemAufheben(this);
    }
    
    public void keyReleased(KeyEvent e)
    {
        ePressed = false;
    }
    
    public void keyTyped(KeyEvent e)
    {} 
    
    public void setX(int newX)
    {
        x = newX;
    }
    
    public void setY(int newY)
    {
        y = newY;
    }
}
