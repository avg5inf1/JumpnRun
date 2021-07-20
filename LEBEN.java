import java.awt.event.*;

/**
 * Klasse LEBEN.
 * 
 * @author K. Maik 
 * 
 * version 1.1
 */
public class LEBEN extends ITEM implements KeyListener, MOVEABLE{
    /*---------------Attribute-----*/

    

    private boolean ePressed;

    /*---------------Konstruktor---*/
    public LEBEN(SPIELLOGIK neueSpielLogik)
    {
        super(neueSpielLogik);
        //meineSpielLogik.hauptfensterGeben().addKeyListener(this);
        
        breite = gebeBreiteOriginal();
        hoehe = gebeHoeheOriginal();
        x = HAUPTFENSTER.gebeHoehe() - hoehe;
        y = HAUPTFENSTER.gebeBreite() /2;
    }

    /*---------------Methoden------*/    

    
    public void useItem(){
        System.out.println("Hi");
        meineSpielLogik.lebenAdd();
    }

    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_E)
        {
            useItem();
            ePressed = true;
        }

    }

    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_E)
        {
            ePressed = false;
        }
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
