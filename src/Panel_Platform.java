import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class Panel_Platform extends JPanel
{
    private static JRadioButton desktopBtn;
    private static JRadioButton serverBtn;
    private static boolean desktopSelected = true;

    private static URL lumiraImageURL;
    private static ImageIcon lumiraImageIcon;
    private static URL serverImageURL;
    private static ImageIcon serverImageIcon;

    private static JLabel lumiraLabel;
    private static JLabel serverLabel;

    private static int imageSize = 60;
    private static ButtonGroup btnGroup;

    public Panel_Platform()
    {
        setLayout(new GridLayout(0, 4));



        desktopBtn = new JRadioButton("Desktop");
        serverBtn = new JRadioButton("Server");

        lumiraImageURL = this.getClass().getResource("lumira.png");

        if (lumiraImageURL == null)
        {
            lumiraImageIcon = new ImageIcon("img/lumira.png");
        }
        else
        {
            lumiraImageIcon = new ImageIcon(lumiraImageURL);
        }

        lumiraImageIcon = new ImageIcon(lumiraImageIcon.getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH));

        serverImageURL = this.getClass().getResource("server.png");

        if (serverImageURL == null)
        {
            serverImageIcon = new ImageIcon("server/ie.png");
        }
        else
        {
            serverImageIcon = new ImageIcon(serverImageURL);
        }

        serverImageIcon = new ImageIcon(serverImageIcon.getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH));

        lumiraLabel = new JLabel(lumiraImageIcon, JLabel.CENTER);
        serverLabel = new JLabel(serverImageIcon, JLabel.CENTER);



        add(lumiraLabel);
        add(desktopBtn);
        add(serverLabel);
        add(serverBtn);
        desktopBtn.setSelected(desktopSelected);


        desktopBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                desktopSelected = true;
                Panel_Browser.selectChromeBrowser();
                Panel_Browser.setChromeBrowserStatus(true);
                Panel_Browser.setIeAccess(desktopSelected);
                Panel_Logon.setLogonAccess(desktopSelected);

            }
        });

        serverBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                desktopSelected = false;
                Panel_Browser.setIeAccess(desktopSelected);
                Panel_Logon.setLogonAccess(desktopSelected);
            }
        });


        btnGroup = new ButtonGroup();
        btnGroup.add(desktopBtn);
        btnGroup.add(serverBtn);
    }

    public static boolean getDesktopStatus()
    {
        return desktopSelected;
    }

}
