package jpvz.model.plants;

import jpvz.model.Plant;

public class PotatoMine extends Plant {

    private int timeToGrow=500;
    private boolean isGrown=false;
    private boolean isExploding=false;
    private int explosionTimer;
    private int explosionFrames;
    private int growFrames;
    private int growTick;
    private int plantedFrames;
    private int plantedTick;

    public PotatoMine() {
        this.plantName = "Potato Mine";
        this.cost = 25;
        this.hp = 300;
        this.maxHP = 300;
        this.damage = 1800;
        this.shootIntervalTicks = 0;
        this.sunIntervalTicks = 0;
        this.sunProduced = 0;
        this.identifier = 10;
        this.speed = 0.0;
        this.state = "PIANTATA";
        this.explosionFrames=22;
        this.growFrames=15;
        this.shootFrame = 0;
        this.growTick=this.growFrames*4;
        this.plantedFrames=17;
        this.plantedTick=this.plantedFrames*4;
    }

    // ---------------------------------------------------------------
    // GETTERS & SETTERS
    // ---------------------------------------------------------------

    @Override
    public String getState(){
        if(isExploding)
            return "ESPLOSIONE";
        if(isGrown){
            if(this.growTick>0){
               return "CRESCIUTA"; 
            } else{
            return "BASE";
            }
        }
            
        return "PIANTATA";
    }

    public boolean isGrown(){
        return isGrown && !this.isExploding && this.growTick==0;
    }

    public boolean isExplosionFinished() {
        return isExploding && this.explosionTimer <= 0;
    }
    public int getGrowTick() {
        return (this.growFrames * 4) - this.growTick;
    }
    public int getExplosionTick() {
        return (this.explosionFrames * 4) - this.explosionTimer;
    }

    public boolean shouldDoDamage(){
        return this.isExploding && this.explosionTimer== (11*4);
    }

    public int getPlantedTick(){
        return (this.plantedFrames*4)-this.plantedTick;
    }
    // ---------------------------------------------------------------
    // LOGICA
    // ---------------------------------------------------------------

    @Override
    public void update(){
        if(plantedTick>0)
            plantedTick--;
        if(!isGrown ){
            timeToGrow--;
            if(timeToGrow<=0){
                this.isGrown=true;
                this.state="CRESCIUTA";
            }
        }
        if(isGrown && this.growTick>0){
            this.growTick--;
        }
        if(isExploding && explosionTimer>0){
            explosionTimer--;
        }
    }

    public void explode(){
        if(isGrown && this.growTick==0 && !isExploding){
            this.isExploding=true;
            this.explosionTimer=this.explosionFrames*4;
            this.state="ESPLOSIONE";
        }
    }

}
