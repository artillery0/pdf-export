import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestExec implements Runnable
{
    private static String lumiraDesktoPath = "C:\\Program Files\\SAP Lumira\\Desktop\\SAPLumira.exe";
    private String storyName;

    public TestExec(String storyName)
    {
        this.storyName = storyName;
    }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        Runtime.getRuntime().exec("taskkill /F /IM SAPLumira.exe");
        Thread.sleep(500);
        new ProcessBuilder(lumiraDesktoPath).start();

        Thread t1 = new Thread(new TestExec("Report Test"));
        Thread t2 = new Thread(new TestExec("BarAndColumnCharts_AllStories"));
        Thread t3 = new Thread(new TestExec("InputControl_DashboardReports"));
        // Thread t3 = new Thread(new TestExec());
        // Thread t4 = new Thread(new TestExec());
        // Thread t5 = new Thread(new TestExec());

        t1.start();
        t2.start();
        t3.start();
        // t3.start();
        // t4.start();
        // t5.start();

        // testPdfx();
    }

    private void testPdfx(String storyName) throws InterruptedException
    {
        System.out.println("a thread started ...");
        // System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", null);

        ChromeOptions chromeOptions = new ChromeOptions();
        HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        chromeOptions.addArguments("--test-type");
        chromeOptions.addArguments("--start-maximized");

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

        // this wait is for lumira desktop to load
        Thread.sleep(8000);
        // WebDriver webDriver = new ChromeDriver(cap);
        WebDriver webDriver = null;
        try
        {
            webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
            System.out.println("driver started flag !");
        }
        catch (MalformedURLException e)
        {
            System.out.println("URL!!!!!!!!!!!!!!!!!!!!!");
        }



        // long timeOutInSeconds = 10;
        // WebDriverWait driverWait = new WebDriverWait(webDriver, timeOutInSeconds);
        webDriver.get("http://localhost:8081/h5v2/index.html");

        // webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // this wait is for localhost to show up in browser
        Thread.sleep(30000);
        WebElement storyButton = webDriver.findElement(By.cssSelector("div[title=\"Stories\"]"));

        // driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[title=\"Stories\"]")));
        storyButton.click();

        Thread.sleep(8000);
        // webDriver.manage().timeouts().pageLoadTimeout(timeOutInSeconds, TimeUnit.SECONDS);
        // String storyName = "Report Test";
        WebElement story1 = webDriver.findElement(By.cssSelector("span[title=\"" + storyName + "\"]"));
        // driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span[title=\"" + storyName + "\"]")));
        Actions action = new Actions(webDriver);
        action.doubleClick(story1).perform();
        // Thread.sleep(2000);

        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(4000);
        // Thread.sleep(10000);
        WebElement composeRoom = webDriver.findElement(By.cssSelector("a[aria-posinset=\"3\"]"));
        // driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Compose')]")));
        Thread.sleep(5000);
        // driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[aria-posinset=\"3\"]")));
        composeRoom.click();
        Thread.sleep(4000);
        webDriver.findElement(By.xpath("//body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "x"));
        Thread.sleep(5000);
        webDriver.findElement(By.xpath("//body")).sendKeys(Keys.chord(Keys.CONTROL, "o"));

        System.out.println(">>>>>>>>>>>>>>>>>>>>>> " + this.storyName + " thread has ended successfully!");
    }

    @Override
    public void run()
    {
        try
        {
            testPdfx(this.storyName);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }



}