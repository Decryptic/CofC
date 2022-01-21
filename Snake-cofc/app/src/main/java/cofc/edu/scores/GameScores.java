package cofc.edu.scores;

public class GameScores
{
    private int _id;
    private int _scores;
    private String _name;

    public GameScores(){}

    public GameScores(String name, int scores)
    {
        this._name = name;
        this._scores = scores;
    }

    public int getID()
    {
        return _id;
    }

    public void setId(int _id)
    {
        this._id = _id;
    }

    public int getScores()
    {
        return _scores;
    }

    public void setScores(int _scores)
    {
        this._scores = _scores;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String _name)
    {
        this._name = _name;
    }

    @Override
    public String toString()
    {
        return "Name: " + getName() + "    " +
                "   " + " Scores: " + getScores();
    }
}
