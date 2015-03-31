import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ThreadManager
{
    private static int numberOfTotalStories = 0;
    private static int numberOfThread = Setting.getThreadNumbers();
    private static int storiesPerThread;
    private static String[] storyArr;
    private static List<String> storyList;



    public static void run()
    {
        initialize();

        System.out.println("---------------------");

        System.out.println("total number of stories = " + storyList.size());

        System.out.println("stories per thread = " + storiesPerThread);

        System.out.println("number of threads = " + numberOfThread);

        System.out.println("---------------------");


        Thread[] threads = new Thread[numberOfThread];

        // create threads
        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(new PdfxThread(i));
        }

        // start executing the threads
        for (int i = 0; i < threads.length; i++)
        {
            threads[i].start();
        }


        // ---------------------------------------------------
    }

    private static void initialize()
    {
        numberOfThread = Setting.getThreadNumbers();
        storyList = new ArrayList<String>();
        populateStoryList();
        numberOfTotalStories = storyList.size();

        // calculate number of stories per thread
        storiesPerThread = (int) Math.ceil((float) numberOfTotalStories / numberOfThread);

        storyArr = new String[numberOfTotalStories];
        storyArr = storyList.toArray(storyArr);
    }

    private static void populateStoryList()
    {
        File folder = new File(Setting.getTestAssetFolderPath());
        // add raw story names with .lums ending to list
        for (File fileEntry : folder.listFiles())
        {
            if (!fileEntry.isDirectory() && fileEntry.getName().endsWith(".lums"))
            {
                storyList.add(fileEntry.getName());
            }
        }

        // parse story name to keep the essential part for story name
        for (int i = 0; i < storyList.size(); i++)
        {
            if (storyList.get(i).contains("_v"))
            {
                int index = storyList.get(i).indexOf("_v"); // TODO parsing might be different based on a different naming convention of lums file
                storyList.set(i, storyList.get(i).substring(0, index));
            }
            else
            {
                int index = storyList.get(i).indexOf(".lums");
                storyList.set(i, storyList.get(i).substring(0, index));
            }
        }
    }

    public static int getStoriesPerThread()
    {
        initialize();
        return storiesPerThread;
    }

    public static String[] getStoryArr()
    {
        initialize();
        return storyArr;
    }


}
