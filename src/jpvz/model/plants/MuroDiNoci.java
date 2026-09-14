package jpvz.model.plants;

import jpvz.model.Plant;

public class MuroDiNoci extends Plant {

    public MuroDiNoci() {
        this.plantName = "Muro di Noci";
        this.cost = 50;
        this.hp = 4000;
        this.maxHP = 4000;
        this.damage = 0;
        this.shootIntervalTicks = 0;
        this.sunIntervalTicks = 0;
        this.sunProduced = 0;
        this.identifier = 8;
        this.speed = 0.0;
        this.state = "BASE1";
        this.shootFrame = 0;
    }

    @Override
    public String getState() {
        double ratio = (double) this.hp / this.maxHP;
        if (ratio > 0.75) {
            return "BASE1";
        } else if (ratio > 0.50) {
            return "BASE2";
        } else if (ratio > 0.25) {
            return "BASE3";
        } else {
            return "BASE4";
        }
    }
}
