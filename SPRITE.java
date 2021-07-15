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
    protected int hoehe;            // Höhe des Sprites
    protected int breite;           // Breite des Sprites

    protected String grafikDateinameOhneNummer;         // Enthält die Dateiname der Bitmap-Dateien ohne Nummer und ohne .png. Beispiel: Die Grafikdateien heißen monster1.png, monster2.png, monster3.png -> grafikDateinameOhneNummer muss monster heißen.

        
    /**
     * Konstruktor für den Sprite.
     */
    public SPRITE()
    {
        zeitZwischen2BildernInMS = 100;
        x = 300;
        y = 300;        
        defaultBilderLaden();
    }
    
    /**
     * Konstruktor für den Sprite.
     * @param dateiname Präfix der Bilddateinamen
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
        System.out.println("Fehler: Bild noch nicht geladen. SPRITE:gebeBreite() lässt sich noch nicht aufrufen. 100 als default-Wert zurückgegeben");
        return 100;
    }
    
    
    /**
     * Liefert die Höhe des Sprites
     * @return die Höhe des Sprites.
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
        System.out.println("Fehler: Bild noch nicht geladen. SPRITE:gebeHoehe() lässt sich noch nicht aufrufen. 100 als default-Wert zurückgegeben");
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
            aktuellesEinzelbild = (aktuellesEinzelbild + 1) % meineEinzelbilder.length;     // zum nächsten Einzelbild durchschalten.  
        }
        g.drawImage(meineEinzelbilder[aktuellesEinzelbild],(int)x,(int) y, null);

        // TODO: Evtl.
    }

    
    public void spielzugAusfuehren(long zeitSeitLetztemSpielzug)
    {
        zeitSeitLetztemBilderwechsel = zeitSeitLetztemBilderwechsel + zeitSeitLetztemSpielzug;        
        
        setRect(getX() + 1,  getY(), 100, 100); // TODO: Entfernen.

        // Hiervon erben und super.spielzugAusfuehren(zeitSeitLetztemSpielzug) in der ersten Zeile ausführen, um 
    }

    /**
     * Lädt die default-Bilder für einen Sprite.
     */
    protected void defaultBilderLaden()
    {
        grafikenLaden(DEFAULT_GRAFIK_DATEINAME);
    }

    /**
     * Lädt die Grafikdateien für das Sprite.
     * @param der Präfix der Grafikdateien des Sprites. Enthält die Dateiname der Bitmap-Dateien ohne Nummer und ohne .png. Beispiel: Die Grafikdateien heißen monster1.png, monster2.png, monster3.png -> grafikDateinameOhneNummer muss monster heißen.
     */
    protected void grafikenLaden(String grafikDateiPraefix)
    {
        // TODO: Prüfen: Grafiken laden asynchron? -> Würde zu einem Bug führen: meineEinzelBilder darf in der Methode zeichnen(Graphics g ) nicht mehr auf null geprüft werden. Stattdessen: boolean grafikenGeladen.

        // Anzahl der Einzelbilder ermitteln
        System.out.println("möchte laden: "+grafikDateiPraefix);
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
                
                        // (Nerdalarm!!!) Ultimativer Perfomance-Tweak! Könnte auch diverse Darstellungsprobleme lösen.
                        // TODO: Perfomance-Gewinn messen.
                            int breiteBild = meineEinzelbilder[i].getWidth();
                            int hoeheBild = meineEinzelbilder[i].getHeight();
                
                            // TODO: VolatileImage testen.
                            BufferedImage besseresBufferedImage = gc.createCompatibleImage(breiteBild, hoeheBild, Transparency.TRANSLUCENT);            // Achtung: Es sind noch weitere createCompatibleImage - Varianten verfügbar, z.B. solche mit Transparenzeffekten.
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
        // TODO: Prüfen: Grafiken laden asynchron? -> Würde zu einem Bug führen: meineEinzelBilder darf in der Methode zeichnen(Graphics g ) nicht mehr auf null geprüft werden. Stattdessen: boolean grafikenGeladen.

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
                
                        // (Nerdalarm!!!) Ultimativer Perfomance-Tweak! Könnte auch diverse Darstellungsprobleme lösen.
                        // TODO: Perfomance-Gewinn messen.
                            int breiteBild = meineEinzelbilder[i].getWidth();
                            int hoeheBild = meineEinzelbilder[i].getHeight();
                
                            // TODO: VolatileImage testen.
                            BufferedImage besseresBufferedImage = gc.createCompatibleImage(breiteBild * 3, hoeheBild * 3, Transparency.TRANSLUCENT);            // Achtung: Es sind noch weitere createCompatibleImage - Varianten verfügbar, z.B. solche mit Transparenzeffekten.
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
