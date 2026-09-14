package jpvz.model.plants;

import jpvz.model.Plant;

public class TriplePeashooter extends Plant {

    public TriplePeashooter() {
        this.plantName = "Triple Peashooter";
        this.cost = 275;
        this.hp = 300;
        this.maxHP = 300;
        this.damage = 35;
        this.shootIntervalTicks = 35;
        this.sunIntervalTicks = 0;
        this.sunProduced = 0;
        this.identifier = 12;
        this.speed = 5.0;
        this.state = "BASE";
        this.attackFrames = 14;
        this.shootFrame = 8;
    }
}
