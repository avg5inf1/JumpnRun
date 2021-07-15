import java.awt.event.*;

/**
 * @author M. Buchner
 * @version 1.1
 */
public class HELD extends SPRITE implements KeyListener, MOVEABLE
{
    private static final double DEFAULT_GESCHWINDIGKEIT = 10;
    private SPIELLOGIK meineSpielLogik;

    private boolean upPressed;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean spacePressed;
    private boolean controlPressed;
    
    private double geschwindigkeit; 
    private int leben = 3;
    private boolean jumping = false;

    /**
     * Konstruktor
     */
    public HELD(SPIELLOGIK spielLogikNeu)
    {
        super("Soldat");
        meineSpielLogik = spielLogikNeu;
        meineSpielLogik.hauptfensterGeben().addKeyListener(this);                  // Das Hauptfenster soll die Spiellogik über KeyEvents informieren.
        
        breite = gebeBreiteOriginal();
        hoehe = gebeHoeheOriginal();
        x = HAUPTFENSTER.gebeHoehe() - hoehe;
        y = HAUPTFENSTER.gebeBreite() /2;
        
        geschwindigkeit = DEFAULT_GESCHWINDIGKEIT;
    }

    
    
    // Methoden des Listeners.
    /**
     * Wird ausgelöst, wenn eine Taste gedrückt wird.
     * @param e ein KeyEvent-Objekt, welches Informationen zum Event (z.B. "Welche Taste wurde gedrückt?") enthält.
     */
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            leftPressed = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            rightPressed = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            upPressed = true;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            upPressed = true;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            if(jumping == false)
            {
                y = y - (int)Math.ceil(200);
                y = y - (int)Math.ceil(150);
            
                aktuellesEinzelbild = 4;
            }
        }
        
        if(e.getKeyCode() == KeyEvent.VK_CONTROL)
        {
            controlPressed = true;
        }
    }

    /**
     * Wird ausgelöst, wenn eine Taste losgelassen wird.
     * @param e ein KeyEvent-Objekt, welches Informationen zum Event (z.B. "Welche Taste wurde losgelassen?") enthält.
     */
    public void keyReleased(KeyEvent e)
    {              
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            leftPressed = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            rightPressed = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            upPressed = false;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_CONTROL)
        {
            controlPressed = false;
        }
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            spacePressed = false;
        }
    }

    public void keyTyped(KeyEvent e)
    {} 

    
    public void spielzugAusfuehren(long zeitSeitLetztemSpielzug)
    {        
        int deltaX = 0;             // Bewegung des Helden in x - Richtung.
        int deltaY = 0;             // Bewegung des Helden in y - Richtung.
        aktuellesEinzelbild = 4;    // keine Blickrichtung.
        
        //Kollisions Spielwelt
        
        if((y <= 700 -hoehe && x >= 250 && x<= 800) ||  (x < 250 || x > 800))
        {
            deltaY = 7;
            jumping = true;
            if(y >= 1080)
            {
                die();
            }
        }
        else
        {
            jumping = false;
        }
        
        
        
        if(y >= 709-hoehe && x >= 809-breite)
        {
            x = Math.max(x, 809);
        }
        
        if(y >= 709-hoehe && x <= 291+breite)
        {
            x = Math.min(x, 291-breite);
        }
        
        //Bewegungssteurerung
        
        if(upPressed)
        {
            deltaY = -(int)Math.ceil( geschwindigkeit * zeitSeitLetztemSpielzug *1.0 / 20);
            //y = y - 1;              // TODO: Zeit seit letztem Spielzug einbauen. Geschwindigkeit vorher festlegen.            
            aktuellesEinzelbild = 0;
        }
        
        if(leftPressed)
        {
            deltaX = -(int)Math.ceil( geschwindigkeit * zeitSeitLetztemSpielzug *1.0 / 20);
            //x = x - 1;              // TODO: Zeit seit letztem Spielzug einbauen. Geschwindigkeit vorher festlegen.
            
            aktuellesEinzelbild = 3;
            
            if(rightPressed && !upPressed)
            {
                aktuellesEinzelbild = 4;
            }
        }
        
        if(rightPressed)
        {
            deltaX = (int)Math.ceil( geschwindigkeit * zeitSeitLetztemSpielzug *1.0 / 20);
            //x = x + 1;              // TODO: Zeit seit letztem Spielzug einbauen. Geschwindigkeit vorher festlegen.
            
            aktuellesEinzelbild = 1;
        }
        
        //         if(controlPressed)      
        //         {
        //             hoehe = (1/100)*hoehe;
        //         }
        
        // TODO: weitere Einzelbilder für schräge Bewegung.
        
        x = x + deltaX;
        y = y + deltaY;
        
        // Der Held darf den Bildschrim nicht verlassen.
        
        y = Math.max(y, 0);
        x = Math.max(x, 0);        
        //         y = Math.min(y, HAUPTFENSTER.gebeHoehe() - hoehe);
        x = Math.min(x, HAUPTFENSTER.gebeBreite() - breite);
               
        this.setRect(x, y, hoehe, breite);
    }
    
    public void lebenAdd()
    {
        if(leben > 2){
            leben = 3;
        }
        else
        {
            leben = leben + 1;
        }
        
    }
    
    
    /**
     * Methode die ein Leben enfernt, wenn der Spieler den Bildschirm nach unten verlässt.
     * Bei Leben == 0 "Game Over"
     *
     */
    public void die()
    {
        leben = leben - 1;
        if(leben > 0)
        {
            x = 300;
            y = 400;
        }
        else
        {
            x = 4000;
            y = 4000;
            GUI gameover = new GUI("Game Over", "Quit", "Game Over");
        }
    }
    
    
}
