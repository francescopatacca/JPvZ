package jpvz.model.zombies;

import jpvz.model.Zombie;

public class ZombieRugbysta extends Zombie {

    public ZombieRugbysta() {
        super();
        this.zombieName = "Zombie Rugbysta";
        this.hp = 450;
        this.maxHP = 450;
        this.speed = 1.5;
        this.eatDamage = 1;
        this.identifier = 9;
        this.deathFrame=14;
        this.score=250;
    }
}
