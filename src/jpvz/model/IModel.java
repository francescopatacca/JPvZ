package jpvz.model;

import java.util.List;

public interface IModel {

    // ---------------------------------------------------------------
    // PLAYER METHODS
    // ---------------------------------------------------------------
    int getScore();
    String getPlayerName();
    void setPlayerName(String playerName);

    // ---------------------------------------------------------------
    // GAME STATUS METHODS
    // ---------------------------------------------------------------
    boolean isGameOver();
    void setGameOver(boolean isGameOver);
    boolean isVictory();
    void setVictory(boolean isVictory);
    boolean isPaused();
    void setPaused(boolean isPaused);
    boolean isInWave();
    String getDifficulty();
    void setDifficulty(String diff);
    int getIntDifficulty();
    String getMap();
    void setMap(String map);
    void updateGame();
    void pauseGame();
    void resetGame();

    // ---------------------------------------------------------------
    // SUN MANAGEMENT METHODS
    // ---------------------------------------------------------------
    int getNumSun();
    void setNumSun(int sun);
    List<Sun> getActiveSun();
    void incrementNumSun();
    void incrementNumSun(int s);
    void spawnSunTick();

    // ---------------------------------------------------------------
    // FACTORY METHODS 
    // ---------------------------------------------------------------
    Plant getPlant(int id);
    Zombie getZombie(int id);

    // ---------------------------------------------------------------
    // PLANT & GRID METHODS 
    // ---------------------------------------------------------------
    void initPlantsBar(int[] selectedPlants);
    Plant[][] getPlayGrid();
    void setPlayGrid(Plant[][] playGrid);
    int[] getPlantsBar();
    void setPlantsBar(int[] plants);
    boolean placePlant(int rows, int colums, int identifier);
    void removePlant(int rows, int colums);

    // ---------------------------------------------------------------
    // PROJECTILE MANAGEMENT METHODS 
    // ---------------------------------------------------------------
    List<Projectile> getActiveProjectiles();

    // ---------------------------------------------------------------
    // LAWNMOWER MANAGEMENT METHODS 
    // ---------------------------------------------------------------
    List<LawnMower> getActiveLawnMowers();

    // ---------------------------------------------------------------
    // ZOMBIE MANAGEMENT METHODS 
    // ---------------------------------------------------------------
    List<Zombie> getActiveZombies();
    int getCurrentWave();
    void updateZombie(Zombie z, double change);
    void spawnZombieTick();

	// ---------------------------------------------------------------
	// AUDIO MANAGEMENT METHODS
	// ---------------------------------------------------------------
    List<String> getActiveSound();
    void removeSound(int index);
    boolean isEating();

}
