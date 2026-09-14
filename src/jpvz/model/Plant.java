package jpvz.model;

public class Plant {

    private static final int TIMES_USAGE_OF_FRAME=4;

    protected String plantName;
    protected int cost;
    protected double hp;
    protected double maxHP;
    protected int damage;             
    protected int shootIntervalTicks; 
    protected int sunIntervalTicks;    
    protected int sunProduced; 
    protected int identifier;     
    protected int canShoot;   
    protected double speed;
    protected int canProduceSun=0;
    protected String state = "BASE";
    protected int attackFrames = 10;
    protected int shootFrame = 5;
    protected int attackTimer = 0;

    public Plant() {

    }

    // ---------------------------------------------------------------
    // GETTERS & SETTERS
    // ---------------------------------------------------------------
    public int getAttackTick(){
        return (this.attackFrames*TIMES_USAGE_OF_FRAME)-this.attackTimer;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(double maxHP) {
        this.maxHP = maxHP;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getShootIntervalTicks() {
        return shootIntervalTicks;
    }

    public void setShootIntervalTicks(int shootIntervalTicks) {
        this.shootIntervalTicks = shootIntervalTicks;
    }

    public int getSunIntervalTicks() {
        return sunIntervalTicks;
    }

    public void setSunIntervalTicks(int sunIntervalTicks) {
        this.sunIntervalTicks = sunIntervalTicks;
    }

    public int getSunProduced() {
        return sunProduced;
    }

    public void setSunProduced(int sunProduced) {
        this.sunProduced = sunProduced;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getAttackFrames() {
        return attackFrames;
    }

    public void setAttackFrames(int attackFrames) {
        this.attackFrames = attackFrames;
    }

    public int getShootFrame() {
        return shootFrame;
    }

    public void setShootFrame(int shootFrame) {
        this.shootFrame = shootFrame;
    }

    public String getState(){
        if("ATTACCO".equals(state)){
            return state;
        }
        return "BASE";
    }

    public void setState(String state){
        this.state=state;
    }  

    public int getPlantedTick(){
        return 0;
    }

    public int getGrowTick(){
        return 0;
    }

    public int getExplosionTick(){
        return 0;
    }
    
    // ---------------------------------------------------------------
    // LOGICA
    // ---------------------------------------------------------------

    public void startAttack() {
        if(this.attackTimer==0){
            this.attackTimer = this.attackFrames * TIMES_USAGE_OF_FRAME;
        }
        this.state = "ATTACCO";
    }

    public void update() {
        if (this.attackTimer > 0) {
            this.attackTimer--;
            if (this.attackTimer == 0) {
                this.state = "BASE";
            }
        }
    }

    public boolean shoot(){
        if(this.attackTimer==((this.attackFrames*TIMES_USAGE_OF_FRAME)-(this.shootFrame*TIMES_USAGE_OF_FRAME)))
            return true;
        return false;
    }

    public void takeDamage(double dmg) {
        this.hp -= dmg;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }


    public boolean canShoot() {
        if (this.shootIntervalTicks <= 0 || this.speed <= 0) {
            return false;
        }
        this.canShoot++;
        if (this.canShoot >= this.shootIntervalTicks) {
            this.canShoot = 0;
            return true;
        }
        return false;
    }

    public boolean canProduceSun(){
        if(this.sunIntervalTicks<=0 || this.sunProduced<=0){
            return false;
        }
        this.canProduceSun++;
        if(this.canProduceSun>=this.sunIntervalTicks){
            this.canProduceSun=0;
            return true;
        }

        return false;
    }


}


