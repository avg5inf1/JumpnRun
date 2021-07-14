import java.awt.*;
import java.awt.event.*;

/**
 * Klasse GUI.
 * 
 * @author 10.Klasse 
 */
public class GUI extends Frame {
    /*---------------Attribute-----*/
    
    private Button button1;
    
    private Label text;

    /*---------------Konstruktor---*/
    public GUI(String title, String button1Text, String textLabel)
    {
        setTitle(title);
        setResizable(false);
        
        Panel contentPane = new Panel(null);
        contentPane.setPreferredSize(new Dimension(500,400));
        contentPane.setBackground(new Color(192,192,192));
        
        button1 = new Button();
        button1.setBounds(255,60,240,40);
        button1.setBackground(new Color(102,102,102));
        button1.setForeground(new Color(255,255,255));
        button1.setEnabled(true);
        button1.setFont(new Font("arial",0,20));
        button1.setLabel(button1Text);
        button1.setVisible(true);
        
        text = new Label();
        text.setBounds(5,20,490,30);
        text.setBackground(new Color(214,217,223));
        text.setForeground(new Color(0,0,0));
        text.setEnabled(true);
        text.setFont(new Font("arial",1,20));
        text.setText(textLabel);
        text.setVisible(true);
        
        contentPane.add(button1);
        contentPane.add(text);
        
        add(contentPane);
        addWindowListener(new TestWindowListener());
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    /*---------------Methoden------*/    

    class TestWindowListener extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            e.getWindow().dispose();                   // Fenster schlieﬂen
            System.exit(0);                            // Virtuelle Maschine schlieﬂen
        }           
    }
    
}
