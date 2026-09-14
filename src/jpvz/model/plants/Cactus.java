package jpvz.model.plants;

import jpvz.model.Plant;

public class Cactus extends Plant {

    public Cactus() {
        this.plantName = "Cactus";
        this.cost = 125;
        this.hp = 300;
        this.maxHP = 300;
        this.damage = 50;
        this.shootIntervalTicks = 30;
        this.sunIntervalTicks = 0;
        this.sunProduced = 0;
        this.identifier = 1;
        this.speed = 5.0;
        this.state = "BASE";
        this.attackFrames = 30;
        this.shootFrame = 24;
    }
}
