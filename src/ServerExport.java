import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * 
 * prerequisite : 1.1) chrome driver 1.2) ie driver 2) selenium-java-2.45.0.jar 3) selenium-server-standalone-2.45.0.jar
 * 
 * @precondition Stories need to be on the server
 */

public class ServerExport
{
    private final static String chromeDriverType = "webdriver.chrome.driver";
    private final static String ieDriverType = "webdriver.ie.driver";
    private final static String chromeDriverPath = "C:\\chromedriver.exe";
    private final static String ieDriverPath = "C:\\IEDriverServer.exe";
    private final static String lumiraServerURL = "hana:8000/sap/bi/launchpad";
    private final static String lumiraServerURL_IE = "http://" + lumiraServerURL;
    private final static String maxmizeWindow = "--start-maximized";
    private final static String username = "SYSTEM";
    private final static String password = "BOsap123";

    private static WebElement usernameField;
    private static WebElement passwordField;
    private static WebElement loginButton;

    private static WebDriver webDriver;

    private static boolean chromeBrowser = true;
    private static boolean addAppendix = false;

    private static List<String> storyNames = new ArrayList<String>();

    private final static String CHROME_STRING = "CHROME";
    private final static String IE_STRING = "IE";
    private final static String APPENDIX_BUTTON = "Export appendixes";
    private final static String OUT_FOLDER_SELECTION = "Select pdf output folder";
    private final static String DEFAULT_DIR = "C:\\";
    private final static String SELECT_PDFX_FOLDER_TITLE = "Select pdf file output folder";
    private final static String GENERAL_TITLE = "PDF export automation";
    private final static String POPUP_ERR_MSG = "Something is wrong";

    private static String downloadFilepath = null;

    private static void createAndShowGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
    {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        // Create and set up the window.
        JFrame frame = new JFrame(GENERAL_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());

        // Image imageIcon= ResourceLoader.loadImage("img/mm.png");

        // ImageIcon imageIcon = new ImageIcon("img/mm.png");
        // frame.setIconImage(imageIcon);

        // Create the radio buttons.
        JRadioButton chromeButton = new JRadioButton(CHROME_STRING);
        chromeButton.setActionCommand(CHROME_STRING);
        chromeButton.setSelected(true);
        chromeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                chromeBrowser = true;
            }
        });

        JRadioButton ieButton = new JRadioButton(IE_STRING);
        ieButton.setActionCommand(IE_STRING);
        ieButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                chromeBrowser = false;
            }
        });

        // Create a checkbox
        JCheckBox appendixCheckBox = new JCheckBox(APPENDIX_BUTTON);
        appendixCheckBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addAppendix = !addAppendix;
            }
        });

        // Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(chromeButton);
        group.add(ieButton);

        // Select Folder Button
        JButton selectFolderButton = new JButton(OUT_FOLDER_SELECTION);

        selectFolderButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home") + "/Desktop"));
                fileChooser.setDialogTitle(SELECT_PDFX_FOLDER_TITLE);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //
                // disable the "All files" option.
                //
                fileChooser.setAcceptAllFileFilterUsed(false);

                if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION)
                {
                    // System.out.println("getCurrentDirectory(): " +
                    // fileChooser.getCurrentDirectory());
                    // System.out.println("getSelectedFile() : " +
                    // fileChooser.getSelectedFile());
                    downloadFilepath = fileChooser.getSelectedFile().toString();
                }
                else
                {
                    // hit cancel - do nothing
                }
            }
        });

        // Export Button
        final JButton exportButton = new JButton("Export PDF");
        exportButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    // Color orginalColor = exportButton.getBackground();
                    // exportButton.setText("Exporting ... ");
                    // exportButton.setBackground(Color.YELLOW);

                    doPdfExport(chromeBrowser);
                    // exportButton.setBackground(orginalColor);
                    // exportButton.setText("Export PDF");
                }
                catch (InterruptedException error)
                {
                    error.printStackTrace();
                }
            }
        });

        // Put the radio buttons in a column in a panel.
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(chromeButton);
        panel.add(ieButton);
        panel.add(appendixCheckBox);
        panel.add(selectFolderButton);
        panel.add(exportButton);

        frame.add(panel, BorderLayout.CENTER);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screen.width / 2 - frame.getSize().width / 2, screen.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws InterruptedException
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    createAndShowGUI();
                }
                catch (ClassNotFoundException error)
                {
                    error.printStackTrace();
                }
                catch (InstantiationException error)
                {
                    error.printStackTrace();
                }
                catch (IllegalAccessException error)
                {
                    error.printStackTrace();
                }
                catch (UnsupportedLookAndFeelException error)
                {
                    error.printStackTrace();
                }
            }
        });
    }

    private static void doPdfExport(boolean chromeBroswer) throws InterruptedException
    {
        if (chromeBroswer)
        {
            setupBrowserDriver(Browser.CHROME);
        }
        else
        {
            setupBrowserDriver(Browser.IE);
        }
        try
        {
            loginToLumiraServer();
            populateStoryList();

            // pdfExport(storyNames.get(0), addAppendix, 40000);
            pdfExport(storyNames.get(1), addAppendix, 10000);
            pdfExport(storyNames.get(2), addAppendix, 10000);
            pdfExport(storyNames.get(3), addAppendix, 10000);
        }
        catch (NoSuchElementException error)
        {
            JOptionPane.showMessageDialog(new JFrame(), POPUP_ERR_MSG, "ERROR!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch (WebDriverException error)
        {
            JOptionPane.showMessageDialog(new JFrame(), POPUP_ERR_MSG, "ERROR!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // for (String storyName : storyNames) {
        // pdfExport(storyName, true, 40000);
        // }

        webDriver.close();
        System.out.println(">>>>>>>>>>>>>>>>>> PDF export safely finished");
    }

    private static void setupBrowserDriver(Browser browser)
    {
        if (browser.equals(Browser.CHROME))
        {
            // System.setProperty(chromeDriverType, chromeDriverPath);
            // ChromeOptions options = new ChromeOptions();
            //
            // options.addArguments(maxmizeWindow);
            // webDriver = new ChromeDriver(options);
            // webDriver.navigate().to(lumiraServerURL);
            System.setProperty(chromeDriverType, chromeDriverPath);
            // downloadFilepath = "c:\\selenium_test";

            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downloadFilepath);

            ChromeOptions chromeOptions = new ChromeOptions();
            HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
            chromeOptions.setExperimentalOption("prefs", chromePrefs);
            chromeOptions.addArguments("--test-type");
            chromeOptions.addArguments(maxmizeWindow);

            DesiredCapabilities cap = DesiredCapabilities.chrome();
            cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
            cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            webDriver = new ChromeDriver(cap);

            webDriver.navigate().to(lumiraServerURL);
        }
        else if (browser.equals(Browser.IE))
        {
            System.setProperty(ieDriverType, ieDriverPath);
            // ChromeOptions options = new ChromeOptions();
            // options.addArguments(maxmizeWindow);
            webDriver = new InternetExplorerDriver();
            webDriver.navigate().to(lumiraServerURL_IE);
        }
    }

    private static void loginToLumiraServer() throws InterruptedException
    {
        usernameField = webDriver.findElement(By.cssSelector("input[id=\"username\"]"));
        passwordField = webDriver.findElement(By.cssSelector("input[id=\"password\"]"));
        loginButton = webDriver.findElement(By.cssSelector("button[type=\"button\"]"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
        Thread.sleep(3000);
    }

    private static void populateStoryList()
    {
        storyNames.add("BarAndColumnCharts_AllStories");
        storyNames.add("Infographic Test");
        storyNames.add("Report Test");
        storyNames.add("Board Test");
    }

    private static void pdfExport(final String storyName, boolean addAppendix, int estimatedTimeToPublish) throws InterruptedException
    {
        WebElement testcase;
        testcase = webDriver.findElement(By.cssSelector("span[title=\"" + storyName + "\"]"));
        testcase.click();
        Thread.sleep(5000);

        WebElement exportIconButton = webDriver.findElement(By.cssSelector("#explorerHeaderExportBtn"));
        exportIconButton.click();
        Thread.sleep(3000);

        // the export dialog
        WebElement exportDialog = webDriver.findElement(By.cssSelector("div[class=\"sapUiDlgCont\"]"));
        new Actions(webDriver).moveToElement(exportDialog);
        exportDialog.click();
        Thread.sleep(2000);

        if (addAppendix)
        {
            WebElement appendixRadioButton = webDriver.findElement(By.cssSelector("span[title=\"Display in appendix\"]"));
            appendixRadioButton.click();
            Thread.sleep(1000);
        }

        WebElement exportTextButton = webDriver.findElement(By.cssSelector("button[class=\"sapUiBtn sapUiBtnAccept sapUiBtnNorm sapUiBtnS sapUiBtnStd\"]"));
        exportTextButton.click();
        Thread.sleep(estimatedTimeToPublish);

        WebElement backButton = webDriver.findElement(By.cssSelector("#explorerHeaderBackBtn"));
        backButton.click();
        Thread.sleep(2000);
    }

}


enum Browser
{
    CHROME, IE
}
