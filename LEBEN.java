import java.awt.event.*;

/**
 * Klasse LEBEN.
 * 
 * @author K. Maik 
 */
public class LEBEN extends ITEM implements KeyListener, MOVEABLE{
    /*---------------Attribute-----*/

    SPIELLOGIK meineSpielLogik;

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
        meineSpielLogik.lebenAdd();
    }

    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_E)
        {
            ePressed = true;
            System.out.println("E Pressed");
            meineSpielLogik.itemAufheben(this);
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
