import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Panel_Actions extends JPanel
{
    private static JButton storyManifestBtn;
    private static JButton pdfExportBtn;

    private static String manifestPath;

    public Panel_Actions()
    {
        setLayout(new GridBagLayout());

        storyManifestBtn = new JButton("View story manifest");
        pdfExportBtn = new JButton("PDF export");

        manifestPath = System.getProperty("user.home") + "\\Desktop\\story_manifest.xml";

        storyManifestBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                File folder = new File(Panel_SourceFolder.getSrcFolderPath());
                PrintWriter fileWriter = null;
                try
                {
                    fileWriter = new PrintWriter(manifestPath, "UTF-8");
                }
                catch (FileNotFoundException error)
                {
                    error.printStackTrace();
                }
                catch (UnsupportedEncodingException error)
                {
                    error.printStackTrace();
                }


                int xmlIndex = 0;
                boolean flag = true;
                fileWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                for (File fileEntry : folder.listFiles())
                {
                    if (!fileEntry.isDirectory() && fileEntry.getName().endsWith(".lums"))
                    {
                        flag = false;
                        if (xmlIndex == 0)
                        {
                            fileWriter.println("<stories>");
                        }
                        int index = fileEntry.getName().toString().indexOf("_v"); // this needs to be changed if they change the naming convention
                        fileWriter.println("   <story id=\"" + (xmlIndex + 1) + "\">" + fileEntry.getName().toString().substring(0, index) + "</story>");

                        xmlIndex++;
                    }
                }

                if (!flag)
                {
                    fileWriter.println("</stories>");
                }
                else
                {
                    printEmptyMsg(fileWriter);
                }

                fileWriter.close();

                try
                {
                    new ProcessBuilder("Notepad.exe", System.getProperty("user.home") + "\\Desktop\\story_manifest.xml").start();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }

            private void printEmptyMsg(PrintWriter fileWriter)
            {
                fileWriter.println("<!--");
                fileWriter.println("##################################################################");
                fileWriter.println();
                fileWriter.println();
                fileWriter.println();
                fileWriter.println("\t\tNO LUMS FILE FOUND IN THIS PATH!");
                fileWriter.println("\t\tPATH = " + Panel_SourceFolder.getSrcFolderPath());
                fileWriter.println();
                fileWriter.println();
                fileWriter.println();
                fileWriter.println("##################################################################");
                fileWriter.println("-->");
            }
        });


        pdfExportBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // if ( Panel_Platform.getDesktopStatus())
                // {
                // System.out.print("Destop , ");
                // if (Panel_Browser.getChromeStatus())
                // {
                // System.out.println("Chrome");
                // }
                // else
                // {
                // System.out.println("IE");
                // }
                // }
                // else
                // {
                // System.out.print("Server , ");
                // if (Panel_Browser.getChromeStatus())
                // {
                // System.out.println("Chrome");
                // }
                // else
                // {
                // System.out.println("IE");
                // }
                // }
                System.out.println("==============================");
                
                System.out.println("platform = " + Setting.getPlatform());
                System.out.println("------------------------------");
                
                System.out.println("url = " + Setting.getURL());
                System.out.println("username = " + Setting.getUsername());
                System.out.println("password = " + Setting.getPassword());
                System.out.println("------------------------------");
                
                System.out.println("browser = " + Setting.getBrowser());
                System.out.println("------------------------------");
                
                System.out.println("appendix = " + Setting.getAppendixOption());
                System.out.println("mt option = " + Setting.getMtOption());
                System.out.println("number of threads = " + Setting.getThreadNumbers());
                System.out.println("------------------------------");
                
                System.out.println("pdf down path = " + Setting.getPdfExportFolderPath());
                System.out.println("------------------------------");
                
                System.out.println("lums folder = " + Setting.getTestAssetFolderPath());
                
                System.out.println("-------------------------------");

            }
        });


        add(storyManifestBtn);
        add(pdfExportBtn);
    }
}
