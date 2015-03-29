import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


@SuppressWarnings("serial")
public class Panel_Logon extends JPanel
{
    private SpringLayout springLayout;

    private static JLabel label_url;
    private static JLabel label_username;
    private static JLabel label_password;

    private static JTextField textfield_url;
    private static JTextField textfield_username;
    private static JTextField textfield_password;

    // private static String str_url;
    // private static String str_username;
    // private static String str_password;

    public Panel_Logon()
    {
        springLayout = new SpringLayout();
        setLayout(springLayout);

        label_url = new JLabel("URL:");
        label_username = new JLabel("Username:");
        label_password = new JLabel("Password:");

        textfield_url = new JTextField("http://localhost:8081/h5v2/index.html", 20);
        textfield_username = new JTextField(20);
        textfield_password = new JPasswordField(20);
        
        // str_url = textfield_url.getText();
        // str_username = textfield_username.getText();
        // str_password = textfield_password.getText();

        // label_url.setEnabled(false);
        label_username.setEnabled(false);
        label_password.setEnabled(false);

        // textfield_url.setEnabled(false);
        textfield_username.setEnabled(false);
        textfield_password.setEnabled(false);

        add(label_url);
        add(textfield_url);
        add(label_username);
        add(textfield_username);
        add(label_password);
        add(textfield_password);

        FormLayoutHelper.makeCompactGrid(this, 3, 2, 6, 6, 6, 6);
    }

//    public static void setLogonAccess(boolean status)
//    {
//        label_url.setEnabled(status);
//        label_username.setEnabled(status);
//        label_password.setEnabled(status);
//
//        textfield_url.setEnabled(status);
//        textfield_username.setEnabled(status);
//        textfield_password.setEnabled(status);
//    }

    public static void clearURLField()
    {
        textfield_url.setText("");
    }
    
    public static void clearUsernameField()
    {
        textfield_username.setText("");
    }
    
    public static void clearPasswordField()
    {
        textfield_password.setText("");
    }
    
    public static void disableUrlField()
    {
        label_url.setEnabled(false);
        textfield_url.setEnabled(false);
    }

    public static void enableUrlField()
    {
        label_url.setEnabled(true);
        textfield_url.setEnabled(true);
    }

    public static void disableUsernameField()
    {
        label_username.setEnabled(false);
        textfield_username.setEnabled(false);
    }

    public static void enableUsernameField()
    {
        label_username.setEnabled(true);
        textfield_username.setEnabled(true);
    }

    public static void disablePasswordField()
    {
        label_password.setEnabled(false);
        textfield_password.setEnabled(false);
    }

    public static void enablePasswordField()
    {
        label_password.setEnabled(true);
        textfield_password.setEnabled(true);
    }

    public static String getURLString()
    {
        return textfield_url.getText();
    }

    public static String getUsernameString()
    {
        return textfield_username.getText();
    }

    public static String getPasswordString()
    {
        return textfield_password.getText();
    }

    public static void setURLTextfield(String str)
    {
        textfield_url.setText(str);
        // str_url = textfield_url.getText();
    }

    public static void setUsernameTextfield(String str)
    {
        textfield_username.setText(str);
        // str_username = textfield_username.getText();
    }

    public static void setPasswordTextfield(String str)
    {
        textfield_password.setText(str);
        // str_password = textfield_password.getText();
    }

}
