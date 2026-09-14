package jpvz.model.zombies;

import jpvz.model.Zombie;

public class ZombieFarWest extends Zombie {

    public ZombieFarWest() {
        super();
        this.zombieName = "Zombie Far West";
        this.hp = 240;
        this.maxHP = 240;
        this.speed = 0.7;
        this.eatDamage = 0.7;
        this.identifier = 5;
        this.deathFrame=14;
        this.score=100;
    }
}
