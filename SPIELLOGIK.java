import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * Diese Klasse enthält die Spiellogik des Spiels * 
 * @author M. Buchner
 * @version 1.1
 */
public class SPIELLOGIK extends Thread implements KeyListener
{   
    private HAUPTFENSTER meinHauptfenster;

    private long zeitVorSpielzug;
    private long zeitFuerLetztenSpielzug;
    private int fps;

    private HELD meinHeld;
    public SPRITE beispielSprite;
    private SCHWERT schwert;
    private LEBEN leben;
    

    public ITEM[] items;

    private boolean onePressed;
    private boolean twoPressed;
    private boolean threePressed;

    
    /**
     * Konstruktor. Startet zugleich das Spiel
     */
    public SPIELLOGIK()
    {
        meinHauptfenster = HAUPTFENSTER.gebeHauptfenster(this);
        hauptfensterGeben().addKeyListener(this);
        beispielSprite = new SPRITE();
        beispielSprite.grafikenLaden("Schwert");

        meinHeld = new HELD(this);
        meinHeld.grafikLaden("Held");

        schwert = new SCHWERT(this);
        schwert.grafikenLaden("Schwert");

        leben = new LEBEN(this);
        leben.setX(400);
        leben.setY(400);
        leben.grafikenLaden("Leben");

        items = new ITEM[3];

        this.start();
    }

    /**
     * Gibt das Hauptfenster zurück
     * @return das Hauptfenster
     */
    public HAUPTFENSTER hauptfensterGeben()
    {
        return meinHauptfenster;
    }

    /**
     * Gibt den Helden zurück
     * @return der Held
     */
    public HELD gebeHeld()
    {
        return meinHeld;
    }

    public LEBEN gebeLeben()
    {
        return leben;
    }

    /**
     * Gibt die FPS (Frames Per Second) zurück.
     * @return fps
     */
    public int gebeFPS()
    {
        return fps;
    }

    /**
     * Entspricht der Spieleschleife.
     */
    public void run()
    {       
        while(true)
        {
            zeitVorSpielzug = System.currentTimeMillis();

            // Spielzug berechnen
            // TODO: Alle Moveables sollen einen Spielzug machen         

            beispielSprite.spielzugAusfuehren(zeitFuerLetztenSpielzug);
            meinHeld.spielzugAusfuehren(zeitFuerLetztenSpielzug);
            itemAufheben(leben);

            // TODO: Kollisionserkennung und sonstige Logik
            
            // Lasse die anderen Threads auch mal zum Zug kommen!
            try
            {
                Thread.sleep(10);
            }
            catch(InterruptedException e){};

            meinHauptfenster.neuZeichnen();                 // Grafik zeichnen

            zeitFuerLetztenSpielzug = System.currentTimeMillis() - zeitVorSpielzug;
            if(zeitFuerLetztenSpielzug != 0)
            {
                fps = (int)((long)1000 / zeitFuerLetztenSpielzug);
            }
            else
            {
                fps = -1;
            }            

        }
    }

    public void lebenAdd()
    {
        System.out.println("Leben wird hinzugefügt");
        meinHeld.lebenAdd();

    }

    public void itemAufheben(ITEM neuesItem)
    {
        
        if(meinHeld.getX() >= neuesItem.getX() && meinHeld.getY() >= neuesItem.getY() && meinHeld.getX() <= neuesItem.getX() + neuesItem.gebeBreiteOriginal() && meinHeld.getY() <= neuesItem.getY() + neuesItem.gebeHoeheOriginal())
        {
            System.out.println(meinHeld.getX() + " " + meinHeld.getY());
            if(items[0] != null)
            {
                if(items[1] != null)
                {
                    items[2] = neuesItem;
                    neuesItem.setX(4000);
                    neuesItem.setY(4000);
                    meinHauptfenster.neuZeichnen();
                }
                else
                {
                    items[1] = neuesItem;
                    neuesItem.setX(4000);
                    neuesItem.setY(4000);
                    meinHauptfenster.neuZeichnen();
                }
            }
            else
            {
                items[0] = neuesItem;
                neuesItem.setX(4000);
                neuesItem.setY(4000);
                meinHauptfenster.neuZeichnen();
            }
        }
    }
public void keyPressed(KeyEvent e){


}
    public void keyPressed(KeyEvent e, ITEM usedItem)
    {

        if(e.getKeyCode() == KeyEvent.VK_1)
        {
            onePressed = true;
            usedItem = items[0];
            items[0] = items[1];
            items[1] = items[2];
            items[2] = null;
            usedItem.useItem();

        }

        if(e.getKeyCode() == KeyEvent.VK_2)
        {
            onePressed = true;
            usedItem = items[1];
            items[1] = items[2];
            items[2] = null;
            usedItem.useItem();

        }

        if(e.getKeyCode() == KeyEvent.VK_3)
        {
            onePressed = true;
            usedItem = items[2];
            items[2] = null;
            usedItem.useItem();

        }
    }

    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_1)
        {
            onePressed = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_2)
        {
            twoPressed = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_3)
        {
            threePressed = false;
        }
    }

    public void keyTyped(KeyEvent e)
    {} 
}


