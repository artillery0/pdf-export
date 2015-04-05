import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Export
{
    private static String lumiraDesktoPath = "C:\\Program Files\\SAP Lumira\\Desktop\\SAPLumira.exe";
    private WebDriver chromeDriver_Desktop;
    private WebDriver chromeDriver_Server;
    private WebDriver ieDriver_Server;

    private static boolean flag_Desktop = true;
    private static boolean flag_Server_Chrome = true;
    private static boolean flag_Server_IE = true;
    private static DesiredCapabilities chromeCapability;
    private static DesiredCapabilities ieCapability;

    private static String storyBtn = "div[title='Stories']";
    private static String composeRoom = "a[aria-posinset='3']";
    private static String pdfDialog = "div[class='sapBiCommonWidgetsStyledDialog sapLumiraExportDialog sapUiDlg sapUiDlgContentBorderDesignNone sapUiDlgFlexHeight sapUiDlgFlexWidth sapUiDlgModal sapUiShd']";
    private static String exportBtn = "//button[contains(.,'Export')]";
    private static String appendixBtn = "span[title='Display in appendix']";
    private static String sep = System.getProperty("file.separator");

    private WebElement usernameField;
    private WebElement passwordField;
    private WebElement loginButton;

    private final static String username = "SYSTEM";
    private final static String password = "BOsap123";

    private static int timeout = 90;


    public void excutePdfExport(String storyName) throws IOException, InterruptedException
    {
        if (Setting.getPlatform().equals(Platform.DESKTOP))
        {
            excute_Desktop_Export(storyName);
        }
        else
        {
            if (Setting.getBrowser().equals(Browser.CHROME))
            {
                excute_ServerExport_Chrome(storyName);
            }
            else
            {
                excute_ServerExport_Ie(storyName);
            }
        }
    }

    private void excute_Desktop_Export(String storyNameStr) throws InterruptedException, IOException
    {
        if (flag_Desktop)
        {
            Runtime.getRuntime().exec("taskkill /F /IM SAPLumira.exe");
            Thread.sleep(500);
            new ProcessBuilder(lumiraDesktoPath).start();

            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(Setting.getPdfExportFolderPath());
            chromePrefs.put("download.default_directory", Setting.getPdfExportFolderPath());
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

            ChromeOptions chromeOptions = new ChromeOptions();
            HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
            chromeOptions.setExperimentalOption("prefs", chromePrefs);
            chromeOptions.addArguments("--test-type");
            // chromeOptions.addArguments("--start-maximized");

            chromeCapability = DesiredCapabilities.chrome();
            chromeCapability.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
            chromeCapability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            chromeCapability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);


            // chromeDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
            System.out.println(this.getClass().getResource("chromedriver.exe").toString());
            System.out.println(this.getClass().getResource("chromedriver.exe").toString().substring(5).replace("%20", " "));

            if (this.getClass().getResource("chromedriver.exe").toString().contains("%20"))
            {
                System.setProperty("webdriver.chrome.driver", this.getClass().getResource("chromedriver.exe").toString().substring(5).replace("%20", " "));
            }
            else
            {
                URL url = this.getClass().getResource("chromedriver.exe");
                File dest = new File(System.getProperty("user.home") + sep + "chromedriver.exe");
                FileUtils.copyURLToFile(url, dest);
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.home") + sep + "chromedriver.exe");
            }

            flag_Desktop = false;
        }

        chromeDriver_Desktop = new ChromeDriver(chromeCapability);

        chromeDriver_Desktop.get(Setting.getURL());
        // chromeDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        WebDriverWait driverWait = new WebDriverWait(chromeDriver_Desktop, timeout);

        // story btn on panel
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(storyBtn)));
        WebElement storyButton = chromeDriver_Desktop.findElement(By.cssSelector(storyBtn));
        storyButton.click();

        // story item in the story list
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[title=\"" + storyNameStr + "\"]")));
        // chromeDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        System.out.println(" --- > testing " + storyNameStr + " ... ");
        WebElement story = chromeDriver_Desktop.findElement(By.cssSelector("span[title=\"" + storyNameStr + "\"]"));


        // driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[title=\"" + storyName + "\"]")));
        new Actions(chromeDriver_Desktop).doubleClick(story).perform();
        // Thread.(2000);

        // chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Thread.(10000);

        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(composeRoom)));
        WebElement composeRoomElement = chromeDriver_Desktop.findElement(By.cssSelector(composeRoom));
        composeRoomElement.click();

        // wait for the central panel to load up
        // Thread.(500);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='sapLumiraStoryComposerCenterArea sapUiBorderLayoutCenter']")));
        // 'div[class="sapLumiraStoryComposerCenterArea sapUiBorderLayoutCenter"]'
        System.out.println(storyNameStr + ": export key sent ");
        chromeDriver_Desktop.findElement(By.xpath("//body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "x"));


        // export dialog -- focus on it
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(pdfDialog)));
        WebElement exportDialog = chromeDriver_Desktop.findElement(By.cssSelector(pdfDialog));
        new Actions(chromeDriver_Desktop).moveToElement(exportDialog);
        exportDialog.click();

        // appendix
        if (Setting.getAppendixOption())
        {
            WebElement appendixRadioButton = chromeDriver_Desktop.findElement(By.cssSelector(appendixBtn));
            appendixRadioButton.click();
        }

        // export button -- click it
        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(exportBtn)));
        WebElement exportTextButton = chromeDriver_Desktop.findElement(By.xpath(exportBtn));
        exportTextButton.click();
        System.out.println(storyNameStr + " : Export Button clicked");

        // wait for the pdf dialog disappear
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(pdfDialog)));

        // TODO this will be used later if we only start browser for just one time
        // chromeDriver.navigate().refresh();

        // chromeDriver.close();
        chromeDriver_Desktop.quit();

        System.out.println(" ----> " + storyNameStr + " THREAD has ended successfully!");
    }


    private void excute_ServerExport_Chrome(String storyName) throws InterruptedException, IOException
    {
        if (flag_Server_Chrome)
        {

            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(Setting.getPdfExportFolderPath());
            chromePrefs.put("download.default_directory", Setting.getPdfExportFolderPath());
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

            ChromeOptions chromeOptions = new ChromeOptions();
            HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
            chromeOptions.setExperimentalOption("prefs", chromePrefs);
            chromeOptions.addArguments("--test-type");
            chromeOptions.addArguments("--start-maximized");

            chromeCapability = DesiredCapabilities.chrome();
            chromeCapability.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
            chromeCapability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            chromeCapability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);


            // chromeDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
            System.out.println(this.getClass().getResource("chromedriver.exe").toString());
            System.out.println(this.getClass().getResource("chromedriver.exe").toString().substring(5).replace("%20", " "));

            if (this.getClass().getResource("chromedriver.exe").toString().contains("%20"))
            {
                System.setProperty("webdriver.chrome.driver", this.getClass().getResource("chromedriver.exe").toString().substring(5).replace("%20", " "));
            }
            else
            {
                URL url = this.getClass().getResource("chromedriver.exe");
                File dest = new File(System.getProperty("user.home") + sep + "chromedriver.exe");
                FileUtils.copyURLToFile(url, dest);
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.home") + sep + "chromedriver.exe");
            }

            flag_Server_Chrome = false;
        }


        chromeDriver_Server = new ChromeDriver(chromeCapability);

        /**
         * Start exporting here
         */

        chromeDriver_Server.get(Setting.getURL());
        // chromeDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        WebDriverWait driverWait = new WebDriverWait(chromeDriver_Server, timeout);
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='button']")));

        usernameField = chromeDriver_Server.findElement(By.cssSelector("input[id='username']"));
        passwordField = chromeDriver_Server.findElement(By.cssSelector("input[id='password']"));
        loginButton = chromeDriver_Server.findElement(By.cssSelector("button[type='button']"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        System.out.println("span[title='" + storyName + "']");
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[title='" + storyName + "']")));
        WebElement storyItem = chromeDriver_Server.findElement(By.cssSelector("span[title='" + storyName + "']"));
        storyItem.click();

        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Export']")));
        WebElement exportIconButton = chromeDriver_Server.findElement(By.cssSelector("button[title='Export']"));
        exportIconButton.click();

        // the export dialog
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='sapUiDlgCont']")));
        WebElement exportDialog = chromeDriver_Server.findElement(By.cssSelector("div[class='sapUiDlgCont']"));
        new Actions(chromeDriver_Server).moveToElement(exportDialog);
        exportDialog.click();
        // Thread.sleep(2000);

        if (Setting.getAppendixOption())
        {
            driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[title='Display in appendix']")));
            WebElement appendixRadioButton = chromeDriver_Server.findElement(By.cssSelector("span[title='Display in appendix']"));
            appendixRadioButton.click();
            // Thread.sleep(1000);
        }

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(exportBtn)));
        WebElement exportTextButton = chromeDriver_Server.findElement(By.xpath(exportBtn));
        exportTextButton.click();

        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(pdfDialog)));
        Thread.sleep(1000);
        chromeDriver_Server.quit();



        System.out.println(" ----> " + storyName + " THREAD has ended successfully!");
    }

    
    private void excute_ServerExport_Ie(String storyName) throws IOException, InterruptedException
    {
        if (flag_Server_IE)
        {

            HashMap<String, Object> iePrefs = new HashMap<String, Object>();
            iePrefs.put("profile.default_content_settings.popups", 0);

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(Setting.getPdfExportFolderPath());
            iePrefs.put("download.default_directory", Setting.getPdfExportFolderPath());
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

//            chromeCapability = DesiredCapabilities.internetExplorer();
//            chromeCapability.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
//            chromeCapability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//            chromeCapability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

            
            ieCapability = DesiredCapabilities.internetExplorer();
            ieCapability.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP,true );
            ieCapability.setCapability(InternetExplorerDriver.IE_USE_PRE_PROCESS_PROXY, true);
            //ieCapability.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS,true);
            //ieCapability.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);

            // chromeDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
            System.out.println(this.getClass().getResource("IEDriverServer.exe").toString());
            System.out.println(this.getClass().getResource("IEDriverServer.exe").toString().substring(5).replace("%20", " "));

            if (this.getClass().getResource("IEDriverServer.exe").toString().contains("%20"))
            {
                System.setProperty("webdriver.ie.driver", this.getClass().getResource("IEDriverServer.exe").toString().substring(5).replace("%20", " "));
            }
            else
            {
                URL url = this.getClass().getResource("IEDriverServer.exe");
                File dest = new File(System.getProperty("user.home") + sep + "IEDriverServer.exe");
                FileUtils.copyURLToFile(url, dest);
                System.setProperty("webdriver.ie.driver", System.getProperty("user.home") + sep + "IEDriverServer.exe");
            }

            flag_Server_IE = false;
        }


       ieDriver_Server = new InternetExplorerDriver(ieCapability);

        /**
         * Start exporting here
         */

        ieDriver_Server.get(Setting.getURL());
        // chromeDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        WebDriverWait driverWait = new WebDriverWait(ieDriver_Server, timeout);
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='button']")));

        usernameField = ieDriver_Server.findElement(By.cssSelector("input[id='username']"));
        passwordField = ieDriver_Server.findElement(By.cssSelector("input[id='password']"));
        loginButton = ieDriver_Server.findElement(By.cssSelector("button[type='button']"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        System.out.println("span[title='" + storyName + "']");
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[title='" + storyName + "']")));
        WebElement storyItem = ieDriver_Server.findElement(By.cssSelector("span[title='" + storyName + "']"));
        storyItem.click();

        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[title='Export']")));
        WebElement exportIconButton = ieDriver_Server.findElement(By.cssSelector("button[title='Export']"));
        exportIconButton.click();

        // the export dialog
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='sapUiDlgCont']")));
        WebElement exportDialog = ieDriver_Server.findElement(By.cssSelector("div[class='sapUiDlgCont']"));
        new Actions(ieDriver_Server).moveToElement(exportDialog);
        exportDialog.click();
        // Thread.sleep(2000);

        if (Setting.getAppendixOption())
        {
            driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[title='Display in appendix']")));
            WebElement appendixRadioButton = ieDriver_Server.findElement(By.cssSelector("span[title='Display in appendix']"));
            appendixRadioButton.click();
            // Thread.sleep(1000);
        }

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(exportBtn)));
        WebElement exportTextButton = ieDriver_Server.findElement(By.xpath(exportBtn));
        exportTextButton.click();

        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(pdfDialog)));
        Thread.sleep(1000);
        ieDriver_Server.quit();



        System.out.println(" ----> " + storyName + " THREAD has ended successfully!");
    }


    

    public static void setDesktopFlag(boolean status)
    {
        flag_Desktop = status;
    }

    public static boolean getDesktopFlagStatus()
    {
        return flag_Desktop;
    }



}
