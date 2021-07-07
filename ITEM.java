import java.awt.event.*;

/**
 * Abstrakte Klasse ITEM.
 * 
 * @author M. Kraus 
 */
public abstract class ITEM extends SPRITE implements KeyListener, MOVEABLE{
    /*---------------Attribute-----*/

    protected SPIELLOGIK meineSpielLogik;
    
    

    /*---------------Konstruktor---*/
    public ITEM(SPIELLOGIK spielLogikNeu) {
        meineSpielLogik = spielLogikNeu;
    }

    /*---------------Methoden------*/    

    
    public abstract void useItem();
    
}
