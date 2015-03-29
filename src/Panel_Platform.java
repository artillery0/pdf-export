import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class Panel_Platform extends JPanel
{
    private static JLabel lumiraLabel;
    private static JLabel serverLabel;
    private static JRadioButton desktopBtn;
    private static JRadioButton serverBtn;
    private static ImageIcon lumiraImageIcon;
    private static ImageIcon serverImageIcon;
    private static int imageSize = 60;
    private static ButtonGroup btnGroup;

    private static boolean desktopSelected;
    private static boolean serverSelected;

    public Panel_Platform()
    {
        setLayout(new GridLayout(0, 4));

        desktopBtn = new JRadioButton("Desktop");
        serverBtn = new JRadioButton("Server");

        lumiraImageIcon = new ImageIcon(this.getClass().getResource("lumira.png"));
        lumiraImageIcon = new ImageIcon(lumiraImageIcon.getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH));
        serverImageIcon = new ImageIcon(this.getClass().getResource("server.png"));
        serverImageIcon = new ImageIcon(serverImageIcon.getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH));

        lumiraLabel = new JLabel(lumiraImageIcon, JLabel.CENTER);
        serverLabel = new JLabel(serverImageIcon, JLabel.CENTER);

        btnGroup = new ButtonGroup();
        btnGroup.add(desktopBtn);
        btnGroup.add(serverBtn);

        desktopBtn.setSelected(true);
        desktopSelected = true;
        serverSelected = false;

        desktopBtn.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent paramItemEvent)
            {
                if (desktopBtn.isSelected())
                {
                    desktopSelected = true;
                    serverSelected = false;
                    Panel_Browser.selectChromeBrowser();
                    Panel_Browser.setIeAccess(false);
                    Panel_Logon.setURLTextfield("http://localhost:8081/h5v2/index.html");
                    Panel_Logon.clearUsernameField();
                    Panel_Logon.clearPasswordField();
                    Panel_Logon.disableUsernameField();
                    Panel_Logon.disablePasswordField();
                    System.out.println("DSK -- desktop = " + desktopSelected + ", server = " + serverSelected);
                }
                else // when server is selected
                {
                    desktopSelected = false;
                    serverSelected = true;
                    Panel_Browser.setIeAccess(true);
                    Panel_Logon.enableUsernameField();
                    Panel_Logon.enablePasswordField();
                    Panel_Logon.setURLTextfield("http://hana:8000/sap/bi/launchpad/");
                    Panel_Logon.setUsernameTextfield("testuser1");
                    Panel_Logon.setPasswordTextfield("BOsap123");
                    System.out.println("DSK -- desktop = " + desktopSelected + ", server = " + serverSelected);
                }

            }
        });

//        serverBtn.addItemListener(new ItemListener()
//        {
//            @Override
//            public void itemStateChanged(ItemEvent paramItemEvent)
//            {
//                if (serverBtn.isSelected())
//                {
//                    serverSelected = true;
//                    desktopSelected = false;
//                    Panel_Browser.setIeAccess(false);
//                    Panel_Logon.enableUsernameField();
//                    Panel_Logon.enablePasswordField();
//                    Panel_Logon.setURLTextfield("http://hana:8000/sap/bi/launchpad/");
//                    Panel_Logon.setUsernameTextfield("testuser1");
//                    Panel_Logon.setPasswordTextfield("BOsap123");
//                    System.out.println("SER -- desktop = " + desktopSelected + ", server = " + serverSelected);
//                }
//                else
//                {
//                    serverSelected = false;
//                    desktopSelected = true;
//                    Panel_Logon.setURLTextfield("http://localhost:8081/h5v2/index.html");
//                    Panel_Logon.setUsernameTextfield("testuser1");
//                    Panel_Logon.setPasswordTextfield("BOsap123");
//                    System.out.println("SER -- desktop = " + desktopSelected + ", server = " + serverSelected);
//                }
//                Panel_Browser.setIeAccess(true);
//            }
//        });

        add(lumiraLabel);
        add(desktopBtn);
        add(serverLabel);
        add(serverBtn);


    }

    public static boolean getDesktopStatus()
    {
        return desktopSelected;
    }
    
    public static boolean getServerStatus()
    {
        return serverSelected;
    }

}
