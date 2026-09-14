package jpvz.model.plants;

import jpvz.model.Plant;

public class FungoSolare extends Plant {

    public FungoSolare() {
        this.plantName = "Fungo Solare";
        this.cost = 25;
        this.hp = 300;
        this.maxHP = 300;
        this.damage = 0;
        this.shootIntervalTicks = 0;
        this.sunIntervalTicks = 700;
        this.sunProduced = 25;
        this.identifier = 6;
        this.speed = 0.0;
        this.state = "BASE";
        this.attackFrames = 17;
        this.shootFrame = 8;
    }
}
