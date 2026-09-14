package jpvz.model.plants;

import jpvz.model.Plant;

public class CatapultaMais extends Plant {

    public CatapultaMais() {
        this.plantName = "Catapulta Mais";
        this.cost = 100;
        this.hp = 300;
        this.maxHP = 300;
        this.damage = 30;
        this.shootIntervalTicks = 40;
        this.sunIntervalTicks = 0;
        this.sunProduced = 0;
        this.identifier = 2;
        this.speed = 4.0;
        this.state = "BASE";
        this.attackFrames = 26;
        this.shootFrame = 11;
    }
}
