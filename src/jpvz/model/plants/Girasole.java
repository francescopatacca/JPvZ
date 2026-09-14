package jpvz.model.plants;

import jpvz.model.Plant;

public class Girasole extends Plant {

    public Girasole() {
        this.plantName = "Girasole";
        this.cost = 50;
        this.hp = 300;
        this.maxHP = 300;
        this.damage = 0;
        this.shootIntervalTicks = 0;
        this.sunIntervalTicks = 700;
        this.sunProduced = 50;
        this.identifier = 7;
        this.speed = 0.0;
        this.state = "BASE";
        this.attackFrames = 16;
        this.shootFrame = 8;
    }
}
