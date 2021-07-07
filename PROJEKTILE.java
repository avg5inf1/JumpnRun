import java.awt.event.*;

/**
 * Klasse PROJEKTILE.
 * 
 * @author M.Kraus
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
}
