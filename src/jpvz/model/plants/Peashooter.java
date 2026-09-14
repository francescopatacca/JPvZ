package jpvz.model.plants;

import jpvz.model.Plant;

public class Peashooter extends Plant {

	public Peashooter() {
		this.plantName = "Peashooter";
		this.cost = 100;
		this.hp = 300;
		this.maxHP = 300;
		this.damage = 35;
		this.shootIntervalTicks = 35; 
		this.sunIntervalTicks = 0;
		this.sunProduced = 0;
		this.identifier = 9;
		this.speed = 5.0;
		this.state = "BASE";
		this.attackFrames = 10;
		this.shootFrame = 6;
	}

}
