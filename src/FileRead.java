import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileRead implements Runnable
{
    // number of thread is a given quantity by the user
    private static int numberOfThread = 19;
    private static int numberOfTotalStories = -1;
    private static String[] storiesArr;
    private int threadId;
    private static int maxPerThread;



    public FileRead(int threadId)
    {
        this.threadId = threadId;
    }


    public static void main(String[] args)
    {
        List<String> stories = new ArrayList<String>();
        final File folder = new File("C:\\Users\\alexlu\\Desktop\\archive");
        for (final File fileEntry : folder.listFiles())
        {
            if (!fileEntry.isDirectory() && fileEntry.getName().endsWith(".lums"))
            {
                stories.add(fileEntry.getName());
            }
        }

        for (int i = 0; i < stories.size(); i++)
        {
            int index = stories.get(i).indexOf("_v");
            stories.set(i, stories.get(i).substring(0, index));
        }

        numberOfTotalStories = stories.size();
        if (numberOfThread > numberOfTotalStories){
            try
            {
                throw new Exception("Thread more than stories");
            }
            catch (Exception e)
            {
                System.out.println("thread more than stoires");
                System.exit(1);
            }
        }
        maxPerThread = (int) Math.ceil((float) numberOfTotalStories / numberOfThread);

        storiesArr = new String[numberOfTotalStories];
        storiesArr = stories.toArray(storiesArr);



        System.out.println("---------------------");

        System.out.println("total number of stories = " + stories.size());


        int maxPerThread = (int) Math.ceil((float) numberOfTotalStories / numberOfThread);
        ;
        System.out.println("max story per thread = " + maxPerThread);
        System.out.println("number of threads = " + numberOfThread);

        System.out.println("---------------------");


        Thread[] threads = new Thread[numberOfThread];

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(new FileRead(i));
        }

        for (int i = 0; i < threads.length; i++)
        {
            threads[i].start();
        }

        // Thread t1 = new Thread(new TestArrayAccess(0));
        // Thread t2 = new Thread(new TestArrayAccess(1));
        // Thread t3 = new Thread(new TestArrayAccess(2));
        // Thread t4 = new Thread(new TestArrayAccess(3));
        // Thread t5 = new Thread(new TestArrayAccess(4));
        //
        // t1.start();
        // t2.start();
        // t3.start();
        // t4.start();
        // t5.start();
    }

    @Override
    public void run()
    {

        for (int i = threadId * maxPerThread; i < (threadId + 1) * maxPerThread; i++)
        {

            try
            {
                Thread.sleep((long) (Math.random() * 2000));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            

            if (i > storiesArr.length)
            {
                break;
            }
            // Call DesktopExport
            System.out.println("thread " + this.threadId + " ---> Exported   " + storiesArr[i]);


            try
            {
                Thread.sleep((long) (Math.random() * 30000));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }


        }
    }
}
