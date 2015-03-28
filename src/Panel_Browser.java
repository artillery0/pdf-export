import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.apache.commons.collections.list.SetUniqueList;


public class Panel_Browser extends JPanel
{
    private static JRadioButton chromeBtn;
    private static JRadioButton ieBtn;

    private static ButtonGroup btnGroup;

    private static int imageSize = 60;
    private static URL chromeImageURL;
    private static URL ieImageURL;
    private static ImageIcon chromeIcon;
    private static ImageIcon ieIcon;
    private static JLabel labelChrome;
    private static JLabel labelIE;
    
    private static boolean chromeBrowserSelected;

    public Panel_Browser() throws IOException
    {
        setLayout(new GridLayout(0, 4));

        chromeBtn = new JRadioButton("Chrome");
        ieBtn = new JRadioButton("IE");

        chromeBtn.setSelected(true);
        chromeBrowserSelected = true;

        btnGroup = new ButtonGroup();
        btnGroup.add(chromeBtn);
        btnGroup.add(ieBtn);

        chromeImageURL = this.getClass().getResource("chrome.png");

        if (chromeImageURL == null)
        {
            chromeIcon = new ImageIcon("img/chrome.png");
        }
        else
        {
            chromeIcon = new ImageIcon(chromeImageURL);
        }

        chromeIcon = new ImageIcon(chromeIcon.getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH));

        ieImageURL = this.getClass().getResource("ie.png");

        if (chromeImageURL == null)
        {
            ieIcon = new ImageIcon("img/ie.png");
        }
        else
        {
            ieIcon = new ImageIcon(ieImageURL);
        }

        ieIcon = new ImageIcon(ieIcon.getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH));

        labelChrome = new JLabel(chromeIcon, JLabel.CENTER);
        labelIE = new JLabel(ieIcon, JLabel.CENTER);

        // labelChrome.setEnabled(false);
        // chromeBtn.setEnabled(false);

        chromeBtn.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                chromeBrowserSelected = true;
            }
        });
        
        ieBtn.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                chromeBrowserSelected = false;
            }
        });
        

        add(labelChrome);
        add(chromeBtn);
        add(labelIE);
        add(ieBtn);

        labelIE.setEnabled(false);
        ieBtn.setEnabled(false);

        // if (Panel_Platform.getDesktopStatus() == true)
        // {
        // labelIE.setEnabled(false);
        // ieBtn.setEnabled(false);
        // }
        // else
        // {
        // labelIE.setEnabled(true);
        // ieBtn.setEnabled(true);
        // }

    }

    public static void setIeAccess(boolean status)
    {
        labelIE.setEnabled(!status);
        ieBtn.setEnabled(!status);
    }

    public static void selectChromeBrowser()
    {
        chromeBtn.setSelected(true);
    }
    
    public static boolean getChromeBrowserStatus()
    {
        return chromeBrowserSelected;
    }
    
    public static void setChromeBrowserStatus(boolean status)
    {
        chromeBrowserSelected = status;
    }
}
