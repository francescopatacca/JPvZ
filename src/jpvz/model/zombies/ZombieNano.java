package jpvz.model.zombies;

import jpvz.model.Zombie;

public class ZombieNano extends Zombie {

    public ZombieNano() {
        super();
        this.zombieName = "Zombie Nano";
        this.hp = 140;
        this.maxHP = 140;
        this.speed = 1.5;
        this.eatDamage = 0.3;
        this.identifier = 7;
        this.deathFrame=11;
        this.score=75;
    }
}
