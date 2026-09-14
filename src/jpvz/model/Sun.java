package jpvz.model;

import java.util.Random;

public class Sun {

    // ---------------------------------------------------------------
    // STATIC CONSTANTS
    // ---------------------------------------------------------------
    private static final int CELL_SIZE = 80;
    private static final int HALF_CELL_SIZE = 40;
    private static final int DEFAULT_TIMER = 150;
    private static final int DEFAULT_TARGET_Y = 430;
    private static final int DRIFT_STEP = 3;

    // ---------------------------------------------------------------
    // INSTANCE ATTRIBUTES
    // ---------------------------------------------------------------
    private double yPos;
    private double xPos;
    private int numSun;
    private int timer;
    private boolean collected;
    private int targetY = DEFAULT_TARGET_Y;
    private Random random;

    public Sun(int row, int col, int s){
        this.random = new Random();
        this.yPos = row * CELL_SIZE;
        this.xPos = col * CELL_SIZE;
        this.collected = false;
        this.numSun = s;
        this.timer = DEFAULT_TIMER;
    }

    // ---------------------------------------------------------------
    // GETTERS & SETTERS
    // ---------------------------------------------------------------

    public void setIsCollected(){
        this.collected=true;
    }

    public boolean isCollected(){
        return this.collected;
    }

    public int getNumSun(){
        return this.numSun;
    }

    public double getYPos(){
        return this.yPos;
    }

    public double getXPos(){
        return this.xPos;
    }

    public void setYPos(int r){
        this.yPos = r * CELL_SIZE + HALF_CELL_SIZE;
    }

    public void setXPos(int c){
        this.xPos = c * CELL_SIZE + HALF_CELL_SIZE;
    }

    // ---------------------------------------------------------------
    // LOGICA
    // ---------------------------------------------------------------

    public void update(){
        if(this.timer>0){
            this.timer--;
        }if(this.yPos<targetY){
            this.updateX();
            this.updateY();
        } 
    }

    public boolean isExpired(){
        if(this.timer<=0 || this.collected){
            return true;
        }
        return false;
    }

    public void updateX(){
        int x = random.nextInt(DRIFT_STEP);
        this.xPos += x;
    }

    public void updateY(){
        int y = random.nextInt(DRIFT_STEP);
        this.yPos += y;
    }
}
