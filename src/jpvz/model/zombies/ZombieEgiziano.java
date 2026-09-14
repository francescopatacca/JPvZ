package jpvz.model.zombies;

import jpvz.model.Zombie;

public class ZombieEgiziano extends Zombie {

    public ZombieEgiziano() {
        super();
        this.zombieName = "Zombie Egiziano";
        this.hp = 220;
        this.maxHP = 220;
        this.speed = 0.7;
        this.eatDamage = 0.5;
        this.identifier = 4;
        this.deathFrame=16;
        this.score=100;
    }
}
