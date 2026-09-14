package jpvz.model.plants;

import jpvz.model.Plant;

public class CatapultaPeperoni extends Plant {

    public CatapultaPeperoni() {
        this.plantName = "Catapulta Peperoni";
        this.cost = 150;
        this.hp = 300;
        this.maxHP = 300;
        this.damage = 45;
        this.shootIntervalTicks = 40;
        this.sunIntervalTicks = 0;
        this.sunProduced = 0;
        this.identifier = 3;
        this.speed = 4.0;
        this.state = "BASE";
        this.attackFrames = 25;
        this.shootFrame = 7;
    }
}
