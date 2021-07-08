import java.awt.event.*;

/**
 * Klasse SCHWERT.
 * 
 * @author M.Kraus
 * 
 * version 1.1
 */
public class SCHWERT extends ITEM implements KeyListener, MOVEABLE{
    /*---------------Attribute-----*/
    
    SPIELLOGIK meineSpielLogik;
    
    private boolean ePressed;

    /*---------------Konstruktor---*/
    public SCHWERT(SPIELLOGIK spielLogikNeu) 
    {
        super(spielLogikNeu);
        
        meineSpielLogik = spielLogikNeu;
        
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
    
    public void setY(int newY)
    {
        y = newY;
    }
    
    public void setX(int newX)
    {
        x = newX;
    }
}
