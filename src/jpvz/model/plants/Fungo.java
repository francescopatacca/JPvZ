package jpvz.model.plants;

import jpvz.model.Plant;

public class Fungo extends Plant {

    public Fungo() {
        this.plantName = "Fungo";
        this.cost = 25;
        this.hp = 150;
        this.maxHP = 150;
        this.damage = 25;
        this.shootIntervalTicks = 30;
        this.sunIntervalTicks = 0;
        this.sunProduced = 0;
        this.identifier = 5;
        this.speed = 4.5;
        this.state = "BASE";
        this.attackFrames = 14;
        this.shootFrame = 8;
    }
}
