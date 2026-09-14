package jpvz.utils;

public class Score {
    private String name;
    private int score;

    public Score(String n, int s){
        this.name=n;
        this.score=s;
    }

    // ---------------------------------------------------------------
    // GETTERS & SETTERS
    // ---------------------------------------------------------------

    public String getName(){
        return this.name;
    }

    public void setName(String n){
        this.name=n;
    }

    public int getScore(){
        return this.score;
    }

    public void setScore(int s){
        this.score=s;
    }

    public String toString(){
        return this.name +" ----------------------- "+this.score+" pt";
    }
}
