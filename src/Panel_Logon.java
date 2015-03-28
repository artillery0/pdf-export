import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


public class Panel_Logon extends JPanel
{
    private SpringLayout springLayout;

    private static JLabel label_url;
    private static JLabel label_username;
    private static JLabel label_password;

    private static JTextField textfield_url;
    private static JTextField textfield_username;
    private static JTextField textfield_password;

    private static String str_url;
    private static String str_username;
    private static String str_password;

    public Panel_Logon()
    {
        springLayout = new SpringLayout();
        setLayout(springLayout);

        label_url = new JLabel("URL:");
        label_username = new JLabel("Username:");
        label_password = new JLabel("Password:");

        textfield_url = new JTextField("http://hana:8000/sap/bi/launchpad/", 20);
        textfield_username = new JTextField("TESTUSER1", 20);
        textfield_password = new JPasswordField("BOsap123", 20);

        str_url = textfield_url.getText();
        str_username = textfield_username.getText();
        str_password = textfield_password.getText();

        add(label_url);
        add(textfield_url);
        add(label_username);
        add(textfield_username);
        add(label_password);
        add(textfield_password);

        FormLayoutHelper.makeCompactGrid(this, 3, 2, 6, 6, 6, 6);

        label_url.setEnabled(false);
        label_username.setEnabled(false);
        label_password.setEnabled(false);

        textfield_url.setEnabled(false);
        textfield_username.setEnabled(false);
        textfield_password.setEnabled(false);
    }

    public static void setLogonAccess(boolean status)
    {
        label_url.setEnabled(!status);
        label_username.setEnabled(!status);
        label_password.setEnabled(!status);

        textfield_url.setEnabled(!status);
        textfield_username.setEnabled(!status);
        textfield_password.setEnabled(!status);
    }

    public static String getURLString()
    {
        return str_url;
    }

    public static String getUserNameString()
    {
        return str_username;
    }

    public static String getPasswordString()
    {
        return str_password;
    }


}
