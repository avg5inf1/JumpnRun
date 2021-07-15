import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferStrategy;

/**
 * Ein einfaches Beispiel einer Vollbilddarstellung in Java.
 * 
 * @author M.Buchner
 * @version 1.1
 */
public class HAUPTFENSTER extends JFrame
{
    public static final  DisplayMode WUNSCH_DISPLAYMODE = new DisplayMode(1980, 1080, 32, 0);     // Default - Breite, Höhe, Farbtiefe. Diese Auflösung wird verwendet, falls sie vom Computer unterstützt wird. Anderenfalls wird die normal eingestellte Auflösung verwendet.
    private static final int NUMBER_OF_BUFFERS = 2;         // double Buffering
    private DisplayMode alterDisplayMode;
    private boolean wunschDisplayModeVerfuegbar;

    private static HAUPTFENSTER diesesHauptfenster;
    
    HINTERGRUND Hintergrund;

    private static GraphicsConfiguration gc; 

    private final SPIELLOGIK meineSpiellogik;

    /**
     * Gibt das Hauptfenster zurück, bzw. erstellt dieses.
     * Singleton - Softwaremuster!
     * @return das Hauptfenster
     */
    public static HAUPTFENSTER gebeHauptfenster(SPIELLOGIK meineSpiellogikNeu)
    {
        if(diesesHauptfenster == null)
        {
            diesesHauptfenster = new HAUPTFENSTER(meineSpiellogikNeu);
        }
        return diesesHauptfenster;
    }

    /**
     * Konstuktor
     */
    private HAUPTFENSTER(SPIELLOGIK meineSpiellogikNeu)
    {
        meineSpiellogik = meineSpiellogikNeu;  
        
        Hintergrund = new HINTERGRUND();
        Hintergrund.setHintergrundX(300);
        Hintergrund.setHintergrundY(300);
        Hintergrund.grafikenLaden("Background1");

        try 
        {
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice device = env.getDefaultScreenDevice();            
            alterDisplayMode = device.getDisplayMode();

            gc = device.getDefaultConfiguration();
            this.setUndecorated(true);
            this.setIgnoreRepaint(true);

            device.setFullScreenWindow(this);

            device.setDisplayMode(WUNSCH_DISPLAYMODE);

            Rectangle bounds = this.getBounds();    // dieses Rechteck gibt den Zeichenbereich an.
            this.createBufferStrategy(NUMBER_OF_BUFFERS);
            wunschDisplayModeVerfuegbar = true;
        }
        catch(Exception e)
        {}
        finally                                     // wird ausgeführt, wenn 
        {
            if(!wunschDisplayModeVerfuegbar)
            {
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setDisplayMode(alterDisplayMode);
                Rectangle bounds = this.getBounds();    // dieses Rechteck gibt den Zeichenbereich an.
                this.createBufferStrategy(NUMBER_OF_BUFFERS);
            }
        }
    }

    /**
     * Gibt die Höhe des Bildschirms in Pixeln zurück
     * @return die Höhe des Bildschirms
     */
    public static int gebeHoehe()
    {
        return diesesHauptfenster.getHeight();
    }

    /**
     * Gibt die Breite des Bildschirms in Pixeln zurück
     * @return die Breite des Bildschirms
     */
    public static int gebeBreite()
    {
        return diesesHauptfenster.getWidth();
    }

    /**
     * Liefert das BufferStrategy-Objekt des Vollbild-Fensters.
     * Das Objekt enthält den Grafikkontext, auf den gezeichnet werden darf und kümmert sich um das double-Buffering.
     * @return das BufferStrategy-Objekt, auf dessen Grafikkontext (aktiv) gezeichnet werden darf.
     */
    public BufferStrategy gebeBufferStrategy()
    {
        return this.getBufferStrategy();
    }

    /**
     * Gibt das Graphics Configuration - Objekt des Fensters zurück
     * @return das GraphicsConfiguration - Objekt.
     */
    public static GraphicsConfiguration gebeGraphicsConfiguration()
    {
        return gc;
    }

    // Grafische Darstellung
    /**
     * Zeichnet den Bildschirminhalt neu.
     * Diese Methode musst du NICHT verändern. Zeichne deinen Bildschirminhalt in der Methode void zeichnen(Graphics g)
     */
    public void neuZeichnen()
    {

        BufferStrategy meineBufferStrategy = this.gebeBufferStrategy();

        do
        {
            do
            {
                Graphics g = meineBufferStrategy.getDrawGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());    // alten Bildschirminhalt löschen.

                g.setColor(Color.GREEN);
                g.drawString("FPS: " + meineSpiellogik.gebeFPS(), 0, 20);

                zeichnen(g);                                            // Rufe die Methode zum Zeichnen auf.

            }  while(meineBufferStrategy.contentsRestored());
            meineBufferStrategy.show();                             // Zeichnet das (nun im Hintergrund, dem "back buffer") gezeichnete Bild auf den Bildschirm.  
        } while(meineBufferStrategy.contentsLost());
    }

    /**
     * Methode zum Zeichnen der Inhalte auf den Bildschirm
     * @param g der Graphikkontext, der zum Zeichnen auf den Bildschirm verwendet werden soll.
     */
    private void zeichnen(Graphics g)
    {
        meineSpiellogik.beispielSprite.zeichnen(g);
        meineSpiellogik.gebeLeben().zeichnen(g);
        meineSpiellogik.gebeHeld().zeichnen(g);
        
        g.setColor(Color.BLACK);
        g.fillRect(0,0,this.getWidth(), this.getHeight());
               
        g.setColor(Color.CYAN);
        g.fillRect(100, 200 , 200, 200);
        
        g.setColor(Color.CYAN);
        g.fillRect(300, 700, 500, 200);
        
        meineSpiellogik.gebeHeld().zeichnen(g);
        
        g.setColor(Color.YELLOW);
        g.drawLine(300,0,300,this.getHeight());
    }

}
