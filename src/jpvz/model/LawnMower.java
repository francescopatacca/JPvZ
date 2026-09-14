package jpvz.model;

public class LawnMower {
    
    private static final double INITIAL_X_POS=0.1;
    private static final double SPEED=5.0;
    private static final int DAMAGE=100000;
    private static final int OFF_SCREEN_X=750; 

    private int row;
    private double xPos;
    private double speed;
    private int damage;
    private boolean isActivated;
    private boolean isUsed;

    public LawnMower(int r){
        this.row=r;
        this.xPos=INITIAL_X_POS;
        this.speed=SPEED;
        this.isActivated=false;
        this.isUsed=false;
        this.damage=DAMAGE;
    }



    // ---------------------------------------------------------------
    // GETTERS & SETTERS
    // ---------------------------------------------------------------
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public double getXPos() {
        return xPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean isActivated) {
        this.isActivated = isActivated;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    // ---------------------------------------------------------------
    // LOGICA
    // ---------------------------------------------------------------    

    public void activate(){
        this.isActivated=true;
    }

    public void update(){
        if(isActivated && !isUsed){
            xPos+=speed;
            if(xPos>OFF_SCREEN_X)
                isUsed=true;
        }
    }
}
