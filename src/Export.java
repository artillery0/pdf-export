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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Export
{
    private static String lumiraDesktoPath = "C:\\Program Files\\SAP Lumira\\Desktop\\SAPLumira.exe";
    private WebDriver chromeDriver;
    private static boolean flag = true;
    private static DesiredCapabilities capability;

    private static String storyBtn = "div[title='Stories']";
    private static String composeRoom = "a[aria-posinset='3']";
    private static String pdfDialog = "div[class='sapBiCommonWidgetsStyledDialog sapLumiraExportDialog sapUiDlg sapUiDlgContentBorderDesignNone sapUiDlgFlexHeight sapUiDlgFlexWidth sapUiDlgModal sapUiShd']";
    private static String exportBtn = "button[class='sapUiBtn sapUiBtnAccept sapUiBtnNorm sapUiBtnS sapUiBtnStd']";
    private static String appendixBtn = "span[title='Display in appendix']";
    private static String sep = System.getProperty("file.separator");

    private static int timeout = 30;


    public void excutePdfExport(String storyName) throws IOException, InterruptedException
    {
        if (Setting.getPlatform().equals(Platform.DESKTOP))
        {
            excuteDesktopExport(storyName);
        }
        else
        {
            // excuteServerExport(storyName);
        }
    }

    public void excuteDesktopExport(String storyNameStr) throws InterruptedException, IOException
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

            capability = DesiredCapabilities.chrome();
            capability.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
            capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

            flag = false;
        }

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

        chromeDriver = new ChromeDriver(capability);

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
        System.out.println(storyNameStr);
        WebElement story = chromeDriver.findElement(By.cssSelector("span[title=\"" + storyNameStr + "\"]"));


        // driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[title=\"" + storyName + "\"]")));
        new Actions(chromeDriver).doubleClick(story).perform();
        // Thread.sleep(2000);

        // chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Thread.sleep(10000);

        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(composeRoom)));
        WebElement composeRoomElement = chromeDriver.findElement(By.cssSelector(composeRoom));
        composeRoomElement.click();

        // Thread.sleep(5000); //--> wait for the central panel to load up
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='center-panel']")));
        System.out.println("KEY SENT");
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
            Thread.sleep(500);
        }

        // export button -- click it

        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(exportBtn)));
        Thread.sleep(500);
        driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(exportBtn)));
        Thread.sleep(500);
        WebElement exportTextButton = chromeDriver.findElement(By.cssSelector(exportBtn));

        // this wait time is important
        Thread.sleep(500);
        exportTextButton.click();

        // driverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#File-menuitem > span")));
        // WebElement fileBtn = chromeDriver.findElement(By.cssSelector("#File-menuitem > span"));
        // fileBtn.click();

        // Thread.sleep(1000);

        // System.out.println("performing F5");
        // chromeDriver.findElement(By.xpath("//body")).sendKeys(Keys.chord(Keys.F5));

        // webDriver.close();
        // chromeDriver.quit();



        System.out.println(" ----> " + storyNameStr + " thread has ended successfully!");
    }



    public static void excuteServerExport()
    {

    }

}
