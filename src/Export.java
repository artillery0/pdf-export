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
    private WebDriver chromeDriver;
    private WebDriver webDriver;
    private static boolean flag = true;
    private static boolean flag2 = true;
    private static DesiredCapabilities chromeCapability;
    private static DesiredCapabilities ieCapability;

    private static String storyBtn = "div[title='Stories']";
    private static String composeRoom = "a[aria-posinset='3']";
    private static String pdfDialog = "div[class='sapBiCommonWidgetsStyledDialog sapLumiraExportDialog sapUiDlg sapUiDlgContentBorderDesignNone sapUiDlgFlexHeight sapUiDlgFlexWidth sapUiDlgModal sapUiShd']";
    // private static String exportBtn = "button[class='sapUiBtn sapUiBtnAccept sapUiBtnNorm sapUiBtnS sapUiBtnStd']";
    private static String exportBtn = "//button[contains(.,'Export')]";
    private static String appendixBtn = "span[title='Display in appendix']";
    private static String sep = System.getProperty("file.separator");

    private static WebElement usernameField;
    private static WebElement passwordField;
    private static WebElement loginButton;

    private final static String username = "SYSTEM";
    private final static String password = "BOsap123";

    private static int timeout = 60;


    public void excutePdfExport(String storyName) throws IOException, InterruptedException
    {
        if (Setting.getPlatform().equals(Platform.DESKTOP))
        {
            excuteDesktopExport(storyName);
        }
        else if (Setting.getPlatform().equals(Platform.SERVER))
        {
            excuteServerExport(storyName);
        }
    }

    private void excuteDesktopExport(String storyNameStr) throws InterruptedException, IOException
    {
        if (flag)
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

            flag = false;
        }

        // // chromeDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
        // System.out.println(this.getClass().getResource("chromedriver.exe").toString());
        // System.out.println(this.getClass().getResource("chromedriver.exe").toString().substring(5).replace("%20", " "));
        //
        // if (this.getClass().getResource("chromedriver.exe").toString().contains("%20"))
        // {
        // System.setProperty("webdriver.chrome.driver", this.getClass().getResource("chromedriver.exe").toString().substring(5).replace("%20", " "));
        // }
        // else
        // {
        // URL url = this.getClass().getResource("chromedriver.exe");
        // File dest = new File(System.getProperty("user.home") + sep + "chromedriver.exe");
        // FileUtils.copyURLToFile(url, dest);
        // System.setProperty("webdriver.chrome.driver", System.getProperty("user.home") + sep + "chromedriver.exe");
        // }

        chromeDriver = new ChromeDriver(chromeCapability);

        chromeDriver.get(Setting.getURL());
        // chromeDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        WebDriverWait driverWait = new WebDriverWait(chromeDriver, timeout);

        // story btn on panel
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(storyBtn)));
        WebElement storyButton = chromeDriver.findElement(By.cssSelector(storyBtn));
        storyButton.click();

        // story item in the story list
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[title=\"" + storyNameStr + "\"]")));
        // chromeDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        System.out.println(" --- > testing " + storyNameStr + " ... ");
        WebElement story = chromeDriver.findElement(By.cssSelector("span[title=\"" + storyNameStr + "\"]"));


        // driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[title=\"" + storyName + "\"]")));
        new Actions(chromeDriver).doubleClick(story).perform();
        // Thread.sleep(2000);

        // chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Thread.sleep(10000);

        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(composeRoom)));
        WebElement composeRoomElement = chromeDriver.findElement(By.cssSelector(composeRoom));
        composeRoomElement.click();

        // wait for the central panel to load up
        // Thread.sleep(500);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='sapLumiraStoryComposerCenterArea sapUiBorderLayoutCenter']")));
        // 'div[class="sapLumiraStoryComposerCenterArea sapUiBorderLayoutCenter"]'
        System.out.println(storyNameStr + ": export key sent ");
        chromeDriver.findElement(By.xpath("//body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "x"));


        // export dialog -- focus on it
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(pdfDialog)));
        WebElement exportDialog = chromeDriver.findElement(By.cssSelector(pdfDialog));
        new Actions(chromeDriver).moveToElement(exportDialog);
        exportDialog.click();

        // appendix
        if (Setting.getAppendixOption())
        {
            WebElement appendixRadioButton = chromeDriver.findElement(By.cssSelector(appendixBtn));
            appendixRadioButton.click();
        }

        // export button -- click it
        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(exportBtn)));
        WebElement exportTextButton = chromeDriver.findElement(By.xpath(exportBtn));
        exportTextButton.click();
        System.out.println(storyNameStr + " : Export Button clicked");

        // This is the wait time for pdf export
        // TODO there should be a better way to do this
        // driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(exportBtn)));
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(pdfDialog)));

        // TODO this will be used later
        // chromeDriver.navigate().refresh();

        // chromeDriver.close();
        chromeDriver.quit();



        System.out.println(" ----> " + storyNameStr + " THREAD has ended successfully!");
    }



    private void excuteServerExport(String storyName) throws IOException, InterruptedException
    {
        System.out.println(Setting.getBrowser());
        if (Setting.getBrowser().equals(Browser.CHROME))
        {
            if (flag2 == true)
            {
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.default_directory", Setting.getPdfExportFolderPath());

                ChromeOptions chromeOptions = new ChromeOptions();
                HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.addArguments("--test-type");
                chromeOptions.addArguments("--start-maximized");

                chromeCapability = DesiredCapabilities.chrome();
                chromeCapability.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
                chromeCapability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                chromeCapability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

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

                flag2 = false;
            }


            webDriver = new ChromeDriver(chromeCapability);
            webDriver.get(Setting.getURL());
        }
        else if (Setting.getBrowser().equals(Browser.IE))
        {
            HashMap<String, Object> iePrefs = new HashMap<String, Object>();
            iePrefs.put("profile.default_content_settings.popups", 0);
            iePrefs.put("download.default_directory", Setting.getPdfExportFolderPath());

            ChromeOptions ieOptions = new ChromeOptions();
            HashMap<String, Object> ieOptionsMap = new HashMap<String, Object>();
            ieOptions.setExperimentalOption("prefs", iePrefs);
            ieOptions.addArguments("--test-type");
            ieOptions.addArguments("--start-maximized");

            DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
            cap.setCapability(ChromeOptions.CAPABILITY, ieOptionsMap);
            cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            cap.setCapability(ChromeOptions.CAPABILITY, ieOptions);
            // webDriver = new InternetExplorerDriver(cap);

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

            webDriver = new InternetExplorerDriver();
            webDriver.get(Setting.getURL());

        }

        WebDriverWait driverWait = new WebDriverWait(webDriver, timeout);
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id='username']")));

        // Thread.sleep(2000);
        usernameField = webDriver.findElement(By.cssSelector("input[id='username']"));
        passwordField = webDriver.findElement(By.cssSelector("input[id='password']"));
        loginButton = webDriver.findElement(By.cssSelector("button[type='button']"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();
        Thread.sleep(12000);

        System.out.println("span[title='" + storyName + "']");
        // driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[title='" + storyName + "']")));
        WebElement storyItem = webDriver.findElement(By.cssSelector("span[title='" + storyName + "']"));
        storyItem.click();
        // Thread.sleep(5000);

        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#explorerHeaderExportBtn")));
        WebElement exportIconButton = webDriver.findElement(By.cssSelector("#explorerHeaderExportBtn"));
        exportIconButton.click();
        // Thread.sleep(3000);

        // the export dialog
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='sapUiDlgCont']")));
        WebElement exportDialog = webDriver.findElement(By.cssSelector("div[class='sapUiDlgCont']"));
        new Actions(webDriver).moveToElement(exportDialog);
        exportDialog.click();
        Thread.sleep(2000);

        if (Setting.getAppendixOption())
        {
            driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[title='Display in appendix']")));
            WebElement appendixRadioButton = webDriver.findElement(By.cssSelector("span[title='Display in appendix']"));
            appendixRadioButton.click();
            Thread.sleep(1000);
        }

        driverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(exportBtn)));
        WebElement exportTextButton = webDriver.findElement(By.xpath(exportBtn));
        exportTextButton.click();
        Thread.sleep(4000);

        WebElement backButton = webDriver.findElement(By.cssSelector("#explorerHeaderBackBtn"));
        backButton.click();
        Thread.sleep(2000);


    }

    public static void setFlag(boolean status)
    {
        flag = status;
    }

    public static boolean getFlagStatus()
    {
        return flag;
    }



}
