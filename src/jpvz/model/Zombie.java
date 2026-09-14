package jpvz.model;

public class Zombie {

    private static final int SOUND_LIMIT=2;
    private static final int TIMES_USAGE_OF_FRAME=4;

	protected String zombieName;
	protected int row;
	protected double xPos;
	protected int hp;
	protected int maxHP;
	protected double speed;        
	protected double eatDamage;       
	protected boolean eating;
	protected int identifier;
    protected String state;
    protected int deathFrame;
    protected int deathTimer=-1;
    protected int tick=0;
    protected int sound;
    protected int score;

    public Zombie() {
        this.eating = false;
        this.state="BASE";
        this.sound=0;
        this.score=100;
    }

    // ---------------------------------------------------------------
    // GETTERS & SETTERS
    // ---------------------------------------------------------------

    public int getDeathTick(){
        if(this.deathTimer<0)
            return 0;
        return (this.deathFrame*4)-this.deathTimer;
    }

    public String getZombieName() {
        return zombieName;
    }

    public void setZombieName(String zombieName) {
        this.zombieName = zombieName;
    }

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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getEatDamage() {
        return eatDamage;
    }

    public void setEatDamage(double eatDamage) {
        this.eatDamage = eatDamage;
    }

    public boolean isEating() {
        return eating;
    }

    public void setEating(boolean eating) {
        this.eating = eating;
    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getState(){
        if(!this.eating && this.hp>0)
            return "CAMMINA";
        else if(this.eating && this.hp>0)
            return "MANGIA";
        else if(this.hp<=0)
            return "MUORE";
        return this.state;
    }

    public void setState(String state){
        this.state=state;
    }

    public boolean getSound(){
        return this.sound<SOUND_LIMIT;
    }    

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score=score;
    }

    // ---------------------------------------------------------------
    // LOGICA
    // ---------------------------------------------------------------

    public void death(){
        if(this.deathTimer==-1){
            this.deathTimer=this.deathFrame*TIMES_USAGE_OF_FRAME;
        }
        this.state="MUORE";
    }

    public void update(){
        if(this.deathTimer>0){
            this.deathTimer--;
        }
    }

    public boolean canRemoveZombie(){
        if(this.deathTimer==0)
            return true;
        return false;
    }

    public void takeDamage(int dmg) {
        this.hp -= dmg;
        if (this.hp <= 0) {
            this.hp = 0;
            death();
        }
    }

    public void move() {
        if (!this.eating && this.hp>0) {
            this.xPos -= this.speed;
        }
    }

    public void updateXPos(double change){
        this.xPos-=change;
    }

    public void sound(){
        this.sound++;
    }
}

