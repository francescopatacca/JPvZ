package jpvz.model.plants;

import jpvz.model.Plant;

public class Pugile extends Plant {

    public Pugile() {
        this.plantName = "Pugile";
        this.cost = 150;
        this.hp = 400;
        this.maxHP = 400;
        this.damage = 50;
        this.shootIntervalTicks = 15;
        this.sunIntervalTicks = 0;
        this.sunProduced = 0;
        this.identifier = 11;
        this.speed = 5.0;
        this.state = "BASE";
        this.attackFrames = 10;
        this.shootFrame = 5;
    }
}
