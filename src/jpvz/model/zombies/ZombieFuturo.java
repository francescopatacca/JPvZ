package jpvz.model.zombies;

import jpvz.model.Zombie;

public class ZombieFuturo extends Zombie {

    public ZombieFuturo() {
        super();
        this.zombieName = "Zombie Futuro";
        this.hp = 280;
        this.maxHP = 280;
        this.speed = 0.8;
        this.eatDamage = 0.4;
        this.identifier = 6;
        this.deathFrame=13;
        this.score=100;
    }
}
