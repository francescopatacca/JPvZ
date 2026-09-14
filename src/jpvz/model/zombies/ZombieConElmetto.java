package jpvz.model.zombies;

import jpvz.model.Zombie;

public class ZombieConElmetto extends Zombie {

    public ZombieConElmetto() {
        super();
        this.zombieName = "Zombie Con Elmetto";
        this.hp = 600;
        this.maxHP = 600;
        this.speed = 0.5;
        this.eatDamage = 0.5;
        this.identifier = 3;
        this.deathFrame=17;
        this.score=200;
    }
}
