import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


@SuppressWarnings("serial")
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

    private static boolean chromeSelected;
    private static boolean ieSelected;

    public Panel_Browser() throws IOException
    {
        setLayout(new GridLayout(0, 4));

        chromeBtn = new JRadioButton("Chrome");
        ieBtn = new JRadioButton("IE");

        chromeBtn.setSelected(true);
        chromeSelected = true;
        ieSelected = false;

        btnGroup = new ButtonGroup();
        btnGroup.add(chromeBtn);
        btnGroup.add(ieBtn);

        chromeImageURL = this.getClass().getResource("chrome.png");
        chromeIcon = new ImageIcon(chromeImageURL);
        chromeIcon = new ImageIcon(chromeIcon.getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH));

        ieImageURL = this.getClass().getResource("ie.png");
        ieIcon = new ImageIcon(ieImageURL);
        ieIcon = new ImageIcon(ieIcon.getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH));

        labelChrome = new JLabel(chromeIcon, JLabel.CENTER);
        labelIE = new JLabel(ieIcon, JLabel.CENTER);

        // labelChrome.setEnabled(false);
        // chromeBtn.setEnabled(false);

        // chromeBtn.addActionListener(new ActionListener()
        // {
        //
        // @Override
        // public void actionPerformed(ActionEvent e)
        // {
        // chromeSelected = true;
        // }
        // });
        //
        // ieBtn.addActionListener(new ActionListener()
        // {
        //
        // @Override
        // public void actionPerformed(ActionEvent e)
        // {
        // chromeSelected = false;
        // }
        // });

        chromeBtn.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent paramItemEvent)
            {
                if (chromeBtn.isSelected())
                {
                    chromeSelected = true;
                    ieSelected = false;
                }
                else
                {
                    ieSelected = true;
                    chromeSelected = false;
                }
                
                System.out.print ("chrome = " + getChromeStatus() +", ");
                System.out.println("ie = " + getIeStatus());
            }
        });

//        ieBtn.addItemListener(new ItemListener()
//        {
//            @Override
//            public void itemStateChanged(ItemEvent paramItemEvent)
//            {
//                ieSelected = true;
//                chromeSelected = false;
//            }
//        });



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
        labelIE.setEnabled(status);
        ieBtn.setEnabled(status);
    }

    public static void selectChromeBrowser()
    {
        chromeBtn.setSelected(true);
        setChromeBrowserStatus(true);
    }

    public static boolean getChromeStatus()
    {
        return chromeSelected;
    }

    public static boolean getIeStatus()
    {
        return ieSelected;
    }

    public static void setChromeBrowserStatus(boolean status)
    {
        chromeSelected = status;
    }
}
