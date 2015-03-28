public class Story
{
    private String name;
    private boolean isTested;


    public Story(String name)
    {
        this.name = name;
        this.isTested = false;
    }

    public String getName()
    {
        return name;
    }

    public boolean getTestStatus()
    {
        return isTested;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setTestStatus(boolean isTested)
    {
        this.isTested = isTested;
    }

}
