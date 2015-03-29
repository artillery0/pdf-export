public class Setting
{
    public static Platform getPlatform()
    {
        if (Panel_Platform.getDesktopStatus())
        {
            return Platform.DESKTOP;
        }
        else
        {
            return Platform.SERVER;
        }
    }

    public static String getURL()
    {
        return Panel_Logon.getURLString();
    }

    public static String getUsername()
    {
        return Panel_Logon.getUsernameString();
    }

    public static String getPassword()
    {
        return Panel_Logon.getPasswordString();
    }

    public static Browser getBrowser()
    {
        if (Panel_Browser.getChromeStatus())
        {
            return Browser.CHROME;
        }
        else
        {
            return Browser.IE;
        }
    }

    public static boolean getAppendixOption()
    {
       
        return Panel_Option.getAppendixStatus();
    }

    public static boolean getMtOption()
    {
        return Panel_Option.getMtStatus();
    }
    
    public static int getThreadNumbers()
    {
        return Panel_Option.getNumberOfThreads();
    }

    public static String getTestAssetFolderPath()
    {
        return Panel_SourceFolder.getSrcFolderPath();
    }

    public static String getPdfExportFolderPath()
    {
       return Panel_PDF_Folder.getPdfFolderPath();
    }

}
