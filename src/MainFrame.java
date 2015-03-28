import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class MainFrame extends JFrame
{
    private static Panel_Platform panel1;
    private static Panel_Logon panel2;
    private static Panel_Browser panel3;
    private static Panel_Option panel4;
    private static Panel_SourceFolder panel5;
    private static Panel_PDF_Folder panel6;
    private static Panel_Actions panel7;

    private URL imageURL;
    private ImageIcon titleIcon;

    public MainFrame() throws IOException
    {
        setWindowsLook();

        setTitleImage();

        setTitle("VDC 17 - PDF export UI automation tool");

        setLayout(new GridLayout(7, 0));

        panel1 = new Panel_Platform();
        panel2 = new Panel_Logon();
        panel3 = new Panel_Browser();
        panel4 = new Panel_Option();
        panel5 = new Panel_SourceFolder();
        panel6 = new Panel_PDF_Folder();
        panel7 = new Panel_Actions();

        panel1.setBorder(BorderFactory.createTitledBorder("Platform"));
        panel2.setBorder(BorderFactory.createTitledBorder("Logon"));
        panel3.setBorder(BorderFactory.createTitledBorder("Browser"));
        panel4.setBorder(BorderFactory.createTitledBorder("Export options"));
        panel5.setBorder(BorderFactory.createTitledBorder("Test assset folder"));
        panel6.setBorder(BorderFactory.createTitledBorder("Pdf export folder"));
        panel7.setBorder(BorderFactory.createTitledBorder("Actions"));

        ((javax.swing.border.TitledBorder) panel1.getBorder()).setTitleFont(new Font(Font.SERIF, Font.PLAIN, 16));
        ((javax.swing.border.TitledBorder) panel2.getBorder()).setTitleFont(new Font(Font.SERIF, Font.PLAIN, 16));
        ((javax.swing.border.TitledBorder) panel3.getBorder()).setTitleFont(new Font(Font.SERIF, Font.PLAIN, 16));
        ((javax.swing.border.TitledBorder) panel4.getBorder()).setTitleFont(new Font(Font.SERIF, Font.PLAIN, 16));
        ((javax.swing.border.TitledBorder) panel5.getBorder()).setTitleFont(new Font(Font.SERIF, Font.PLAIN, 16));
        ((javax.swing.border.TitledBorder) panel6.getBorder()).setTitleFont(new Font(Font.SERIF, Font.PLAIN, 16));
        ((javax.swing.border.TitledBorder) panel7.getBorder()).setTitleFont(new Font(Font.SERIF, Font.PLAIN, 16));

        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);
        add(panel5);
        add(panel6);
        add(panel7);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(400, 800);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void setTitleImage()
    {
        imageURL = this.getClass().getResource("mm.png");

        if (imageURL == null)
        {
            titleIcon = new ImageIcon("img/mm.png");
        }
        else
        {
            titleIcon = new ImageIcon(imageURL);
        }

        setIconImage(titleIcon.getImage());
    }

    private void setWindowsLook()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }
    }

}
