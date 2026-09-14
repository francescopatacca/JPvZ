package jpvz.model.zombies;

import jpvz.model.Zombie;

public class ZombieClown extends Zombie {

    public ZombieClown() {
        super();
        this.zombieName = "Zombie Clown";
        this.hp = 180;
        this.maxHP = 180;
        this.speed = 0.7;
        this.eatDamage = 0.5;
        this.identifier = 2;
        this.deathFrame=18;    
        this.score=100;    
    }
}
