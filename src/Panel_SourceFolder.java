import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Panel_SourceFolder extends JPanel
{
    private static JLabel srcFolderLabel;
    private static JButton btnChangeSourceFolder;
    private static String srcFolderPath;
    private static String curPath;

    public Panel_SourceFolder()
    {
        setLayout(new GridLayout(2, 0));

        curPath = "Path: ";
        
        srcFolderPath = System.getProperty("user.home") + "\\Desktop";
        srcFolderLabel = new JLabel(curPath + srcFolderPath);
        btnChangeSourceFolder = new JButton("Select test asset folder");

        btnChangeSourceFolder.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();

                fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "/Desktop"));
                fileChooser.setDialogTitle("Select your test asset folder");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                if (fileChooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION)
                {
                    // System.out.println("getCurrentDirectory(): " +
                    // fileChooser.getCurrentDirectory());
                    // System.out.println("getSelectedFile() : " +
                    // fileChooser.getSelectedFile());
                    srcFolderPath = fileChooser.getSelectedFile().toString();
                    srcFolderLabel.setText(curPath + srcFolderPath);
                }

            }
        });


        add(srcFolderLabel);
        add(btnChangeSourceFolder);
    }

    public static String getSrcFolderPath()
    {
        return srcFolderPath;
    }

}
