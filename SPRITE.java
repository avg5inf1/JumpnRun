import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 * Eine Klasse, welche einen Sprite (eine evtl. bewegte) Grafik implementiert.
 * Sie wird zur grafischen Darstellung von Autos, Monstern, Helden, etc. verwendet.
 * 
 * @author Michael Buchner
 * @version 1.1
 */
public class SPRITE extends Rectangle2D.Double implements DRAWABLE, MOVEABLE
{
    protected static final String DEFAULT_GRAFIK_DATEINAME = "DefaultSprite";

    protected long zeitZwischen2BildernInMS;
    protected long zeitSeitLetztemBilderwechsel;

    protected BufferedImage[] meineEinzelbilder;
    protected int aktuellesEinzelbild;    
    protected int hoehe;            // H�he des Sprites
    protected int breite;           // Breite des Sprites

    protected String grafikDateinameOhneNummer;         // Enth�lt die Dateiname der Bitmap-Dateien ohne Nummer und ohne .png. Beispiel: Die Grafikdateien hei�en monster1.png, monster2.png, monster3.png -> grafikDateinameOhneNummer muss monster hei�en.

        
    /**
     * Konstruktor f�r den Sprite.
     */
    public SPRITE()
    {
        zeitZwischen2BildernInMS = 100;
        x = 300;
        y = 300;        
        defaultBilderLaden();
    }
    
    /**
     * Konstruktor f�r den Sprite.
     * @param dateiname Pr�fix der Bilddateinamen
     */
    public SPRITE(String dateiname)
    {
        zeitZwischen2BildernInMS = 100;
        x = 300;
        y = 300;        
        grafikenLaden(dateiname);
    }
    
    /**
     * Liefert die Breite des Sprites
     * @return die Breite des Sprites.
     */
    protected int gebeBreiteOriginal()
    {
        if(meineEinzelbilder != null)
        {
            if(meineEinzelbilder[0] != null)
            {
                return meineEinzelbilder[0].getWidth();
            }            
        }
        System.out.println("Fehler: Bild noch nicht geladen. SPRITE:gebeBreite() l�sst sich noch nicht aufrufen. 100 als default-Wert zur�ckgegeben");
        return 100;
    }
    
    
    /**
     * Liefert die H�he des Sprites
     * @return die H�he des Sprites.
     */
     protected int gebeHoeheOriginal()
    {
        if(meineEinzelbilder != null)
        {
            if(meineEinzelbilder[0] != null)
            {
                return meineEinzelbilder[0].getHeight();
            }            
        }
        System.out.println("Fehler: Bild noch nicht geladen. SPRITE:gebeHoehe() l�sst sich noch nicht aufrufen. 100 als default-Wert zur�ckgegeben");
        return 100;
    }

    public void zeichnen(Graphics g)
    {
        if(meineEinzelbilder == null)
        {
            System.out.println("Einzelbilder konnten nicht erfolgreich geladen werden. Lade die default-Bilder");
            defaultBilderLaden();
        }
        if(meineEinzelbilder[0] == null)
        {
            System.out.println("Einzelbild 0 konnten nicht erfolgreich geladen werden. Lade die default-Bilder");
            defaultBilderLaden();
        }

        if(zeitSeitLetztemBilderwechsel > zeitZwischen2BildernInMS)
        {
            zeitSeitLetztemBilderwechsel = 0;
            aktuellesEinzelbild = (aktuellesEinzelbild + 1) % meineEinzelbilder.length;     // zum n�chsten Einzelbild durchschalten.  
        }
        g.drawImage(meineEinzelbilder[aktuellesEinzelbild],(int)x,(int) y, null);

        // TODO: Evtl.
    }

    
    public void spielzugAusfuehren(long zeitSeitLetztemSpielzug)
    {
        zeitSeitLetztemBilderwechsel = zeitSeitLetztemBilderwechsel + zeitSeitLetztemSpielzug;        
        
        setRect(getX() + 1,  getY(), 100, 100); // TODO: Entfernen.

        // Hiervon erben und super.spielzugAusfuehren(zeitSeitLetztemSpielzug) in der ersten Zeile ausf�hren, um 
    }

    /**
     * L�dt die default-Bilder f�r einen Sprite.
     */
    protected void defaultBilderLaden()
    {
        grafikenLaden(DEFAULT_GRAFIK_DATEINAME);
    }

    /**
     * L�dt die Grafikdateien f�r das Sprite.
     * @param der Pr�fix der Grafikdateien des Sprites. Enth�lt die Dateiname der Bitmap-Dateien ohne Nummer und ohne .png. Beispiel: Die Grafikdateien hei�en monster1.png, monster2.png, monster3.png -> grafikDateinameOhneNummer muss monster hei�en.
     */
    protected void grafikenLaden(String grafikDateiPraefix)
    {
        // TODO: Pr�fen: Grafiken laden asynchron? -> W�rde zu einem Bug f�hren: meineEinzelBilder darf in der Methode zeichnen(Graphics g ) nicht mehr auf null gepr�ft werden. Stattdessen: boolean grafikenGeladen.

        // Anzahl der Einzelbilder ermitteln
        System.out.println("m�chte laden: "+grafikDateiPraefix);
        GraphicsConfiguration gc = HAUPTFENSTER.gebeGraphicsConfiguration();
        
        
        int anzahlEinzelbilder = 0;
        boolean fertig = false;
        while(! fertig)
        {
            File f = new File("Bilder\\" + grafikDateiPraefix + (anzahlEinzelbilder + 1) + ".png");
            if(!f.exists())
            {
                fertig = true;
            }
            else
            {
                anzahlEinzelbilder++;             
            }

        }
        
        if(anzahlEinzelbilder == 0)
        {
            System.out.println("Es sind 0 Einzelbilder mit dem gegebenen Dateinamen vorhanden! Erinnerung: Das Format ist z.B. 'monster1.png' - auch, wenn das Sprite 'monster' nur aus einem Einzelbild besteht.");
        }

        // Einzelbilder laden
        meineEinzelbilder = new BufferedImage[anzahlEinzelbilder];
        for(int i = 0; i < anzahlEinzelbilder; i++)
        {
            try
            {
                meineEinzelbilder[i] = ImageIO.read(new File("Bilder\\" + grafikDateiPraefix + (i+1) + ".png"));
                
                        // (Nerdalarm!!!) Ultimativer Perfomance-Tweak! K�nnte auch diverse Darstellungsprobleme l�sen.
                        // TODO: Perfomance-Gewinn messen.
                            int breiteBild = meineEinzelbilder[i].getWidth();
                            int hoeheBild = meineEinzelbilder[i].getHeight();
                
                            // TODO: VolatileImage testen.
                            BufferedImage besseresBufferedImage = gc.createCompatibleImage(breiteBild, hoeheBild, Transparency.TRANSLUCENT);            // Achtung: Es sind noch weitere createCompatibleImage - Varianten verf�gbar, z.B. solche mit Transparenzeffekten.
                            besseresBufferedImage.getGraphics().drawImage(meineEinzelbilder[i], 0, 0, null);
                            meineEinzelbilder[i] = besseresBufferedImage;
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        }
    }
    
    protected void grafikLaden(String grafikDateiPraefix)
    {
        // TODO: Pr�fen: Grafiken laden asynchron? -> W�rde zu einem Bug f�hren: meineEinzelBilder darf in der Methode zeichnen(Graphics g ) nicht mehr auf null gepr�ft werden. Stattdessen: boolean grafikenGeladen.

        // Anzahl der Einzelbilder ermitteln
        
        GraphicsConfiguration gc = HAUPTFENSTER.gebeGraphicsConfiguration();
        
        int anzahlEinzelbilder = 0;
        boolean fertig = false;
        while(! fertig)
        {
            File f = new File("Bilder\\" + grafikDateiPraefix + (anzahlEinzelbilder + 1) + ".png");
            if(!f.exists())
            {
                fertig = true;
            }
            else
            {
                anzahlEinzelbilder++;             
            }

        }
        
        if(anzahlEinzelbilder == 0)
        {
            System.out.println("Es sind 0 Einzelbilder mit dem gegebenen Dateinamen vorhanden! Erinnerung: Das Format ist z.B. 'monster1.png' - auch, wenn das Sprite 'monster' nur aus einem Einzelbild besteht.");
        }

        // Einzelbilder laden
        meineEinzelbilder = new BufferedImage[anzahlEinzelbilder];
        for(int i = 0; i < anzahlEinzelbilder; i++)
        {
            try
            {
                meineEinzelbilder[i] = ImageIO.read(new File("Bilder\\" + grafikDateiPraefix + (i+1) + ".png"));
                
                        // (Nerdalarm!!!) Ultimativer Perfomance-Tweak! K�nnte auch diverse Darstellungsprobleme l�sen.
                        // TODO: Perfomance-Gewinn messen.
                            int breiteBild = meineEinzelbilder[i].getWidth();
                            int hoeheBild = meineEinzelbilder[i].getHeight();
                
                            // TODO: VolatileImage testen.
                            BufferedImage besseresBufferedImage = gc.createCompatibleImage(breiteBild * 3, hoeheBild * 3, Transparency.TRANSLUCENT);            // Achtung: Es sind noch weitere createCompatibleImage - Varianten verf�gbar, z.B. solche mit Transparenzeffekten.
                            besseresBufferedImage.getGraphics().drawImage(meineEinzelbilder[i], 0, 0, null);
                            meineEinzelbilder[i] = besseresBufferedImage;
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        }
    }
}
