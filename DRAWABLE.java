import java.awt.*;

/**
 * Ein Interface, welches von zeichenbaren Objekten Implementiert werden soll.
 * 
 * @author Michael Buchner 
 * @version 1.1
 */

public interface DRAWABLE
{
    /**
     * Zeichnet das Objekt auf den Bildschirm.
     * @param g der zu verwendende Zeichenkontext (welcher auf den Bildschirm zeichnet)
     */
    public void zeichnen(Graphics g);
}
