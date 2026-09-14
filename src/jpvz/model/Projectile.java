package jpvz.model;

public class Projectile {

	private static final int OFF_SCREEN_X = 720;
	private static final int MAIS_ID=2;
	private static final int PEPERONI_ID=3;

	private int row;
	private double xPos;
	private int damage;
	private double speed;     
    private String name;
    private int identifier;
	private double xStartPos;
	private double yPos;
	private double targetX;


	public Projectile(String name,int row, double xPos, int damage, double speed,int id,double targetX) {
        this.name=name;
		this.row = row;
		this.xPos = xPos;
		this.yPos=this.row*80.0;
		this.xStartPos= xPos;
		this.damage = damage;
		this.speed = speed;
        this.identifier=id;
		this.targetX=targetX;
	}

    // ---------------------------------------------------------------
    // GETTERS & SETTERS
    // ---------------------------------------------------------------
	public int getRow() {
		return this.row;
	}

	public double getXPos() {
		return this.xPos;
	}

	public double getXStartPos() {
		return this.xStartPos;
	}

	public double getDistanceTravelled(){
		return this.xPos-this.xStartPos;
	}

	public int getDamage() {
		return this.damage;
	}

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name=name;
    }
	
    public int getId(){
        return this.identifier;
    }

    public void setId(int id){
        this.identifier=id;
    }	

	public void setSpeed(double s){
		this.speed=s;
	}

	public double getSpeed(){
		return this.speed;
	}

	public double getYPos(){
		return this.yPos;
	}

    // ---------------------------------------------------------------
    // LOGICA
    // ---------------------------------------------------------------

	public void move() {
		this.xPos+=this.speed;
		if(this.identifier==MAIS_ID ||this.identifier==PEPERONI_ID){
			double totalDistance=Math.max(this.speed, targetX-this.xStartPos);
			double distance=this.xPos-this.xStartPos;
			if(distance<totalDistance){
				double maxHeigth=Math.max(70.0, totalDistance/7.0);
				double progress=distance/totalDistance;
				double height=4.0*progress*maxHeigth*(1.0-progress);
				this.yPos=(this.row*80.0)-height;
			}else if (distance>totalDistance +this.speed){
				this.xPos=OFF_SCREEN_X+100;
			}else{
				this.yPos=this.row*80.0;
			}
		}
	}

	public boolean isOffScreen() {
		return this.xPos > OFF_SCREEN_X; 
	}




} 
