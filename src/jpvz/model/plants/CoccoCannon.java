package jpvz.model.plants;

import jpvz.model.Plant;

public class CoccoCannon extends Plant {

    public CoccoCannon() {
        this.plantName = "Cocco Cannon";
        this.cost = 400;
        this.hp = 500;
        this.maxHP = 500;
        this.damage = 500;
        this.shootIntervalTicks = 100;
        this.sunIntervalTicks = 0;
        this.sunProduced = 0;
        this.identifier = 4;
        this.speed = 6.0;
        this.state = "BASE";
        this.attackFrames = 27;
        this.shootFrame = 10;
    }
}
