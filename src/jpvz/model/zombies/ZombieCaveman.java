package jpvz.model.zombies;

import jpvz.model.Zombie;

public class ZombieCaveman extends Zombie {

    public ZombieCaveman() {
        super();
        this.zombieName = "Zombie Caveman";
        this.hp = 280;
        this.maxHP = 280;
        this.speed = 0.5;
        this.eatDamage = 1;
        this.identifier = 1;
        this.deathFrame=20;
        this.score=150;
    }
}
