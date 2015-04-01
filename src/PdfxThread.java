import java.io.IOException;


public class PdfxThread implements Runnable
{
    // private static int numberOfThread = Setting.getThreadNumbers();
    // private static int numberOfTotalStories = 0;
    private static String[] storiesArr = ThreadManager.getStoryArr();
    private static int storiesPerThread = ThreadManager.getStoriesPerThread();
    private int threadId;


    public PdfxThread(int threadId)
    {
        // numberOfThread = Setting.getThreadNumbers();
        this.threadId = threadId;
        storiesArr = ThreadManager.getStoryArr();
        storiesPerThread = ThreadManager.getStoriesPerThread();
    }


    @Override
    public void run()
    {

        for (int i = threadId * storiesPerThread; i < storiesArr.length && i < (threadId + 1) * storiesPerThread; i++)
        {
            System.out.println("thread " + this.threadId + " ---> exporting...   " + storiesArr[i]);

            try
            {
                new Export().excutePdfExport(storiesArr[i]);
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }

        }
    }


}
