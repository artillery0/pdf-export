import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Panel_PDF_Folder extends JPanel
{
    private static JLabel pdfOutPathLabel;
    private static JButton changeFilePathBtn;
    private static String downloadPdfPath;
    private static String curPath;

    public Panel_PDF_Folder()
    {
        curPath = "Path: ";
        setLayout(new GridLayout(2, 0));
        pdfOutPathLabel = new JLabel(curPath + System.getProperty("user.home") + "\\Desktop\\");
        changeFilePathBtn = new JButton("Select destination folder");

        changeFilePathBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "/Desktop"));
                fileChooser.setDialogTitle("select pdf output folder");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                // fileChooser.setAcceptAllFileFilterUsed(false);

                if (fileChooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION)
                {
                    // System.out.println("getCurrentDirectory(): " +
                    // fileChooser.getCurrentDirectory());
                    // System.out.println("getSelectedFile() : " +
                    // fileChooser.getSelectedFile());
                    downloadPdfPath = fileChooser.getSelectedFile().toString();
                    pdfOutPathLabel.setText(curPath + downloadPdfPath + "\\");
                }

            }
        });



        add(pdfOutPathLabel);
        add(changeFilePathBtn);
    }
}
