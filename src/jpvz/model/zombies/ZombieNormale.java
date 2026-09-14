package jpvz.model.zombies;

import jpvz.model.Zombie;

public class ZombieNormale extends Zombie {

    public ZombieNormale() {
        super();
        this.zombieName = "Zombie Normale";
        this.hp = 200;
        this.maxHP = 200;
        this.speed = 0.7;
        this.eatDamage = 0.5;
        this.identifier = 8;
        this.deathFrame=16;
        this.score=100;
    }
}
