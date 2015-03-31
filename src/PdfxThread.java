import java.io.IOException;


public class PdfxThread implements Runnable
{
    // number of thread is a given quantity by the user
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
            // Call DesktopExport
            System.out.println("thread " + this.threadId + " ---> exporting...   " + storiesArr[i]);
            // pdfExport( storyname,waitTime ) --- > desktopPdfExport( storyname )
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

//            try
//            {
//                Thread.sleep(500);
//            }
//            catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }

        }
    }


}
