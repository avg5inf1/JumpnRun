
/**
 * Ein Interface, welches von beweglichen Objekten implementiert werden soll.
 * 
 * @author Michael Buchner
 * @version 1.1
 */
public interface MOVEABLE
{       
    /**
     * Lässt ein Objekt seinen Spielzug ausführen.
     * @param zeitSeitLetzterAktion die Zeit, die seit dem letzten Spielzug vergangen ist.
     */
    public void spielzugAusfuehren(long zeitSeitLetztemSpielzug);
    
    
    // TODO: Evtl. um eine Methode spielzugAufuehren(Spiellogik l) erweitern, damit die beweglichen Objekte Kollisionen erkennen können.
}
