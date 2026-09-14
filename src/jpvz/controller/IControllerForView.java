package jpvz.controller;

import java.util.List;

public interface IControllerForView {

    // ---------------------------------------------------------------
    // VIEW METHODS
    // ---------------------------------------------------------------
    void openMainGui();
    void showPanel(String panelId);
    void showError(String error);

    // ---------------------------------------------------------------
    // TIMER INITIALIZATION METHODS
    // ---------------------------------------------------------------
    void startTimer();
    void stopTimer();
    void pauseTimer();

    // ---------------------------------------------------------------
    // GAME STATUS METHODS
    // ---------------------------------------------------------------
    boolean isGameOver();
    boolean isVictory();
    void resetGame();
    void pauseGame();
    boolean isPaused();
    void setPlayerName(String name);
    int getWave();
    void updateGame();

	// ---------------------------------------------------------------
	// GAME SETTING METHODS
	// ---------------------------------------------------------------
    void saveSetting(String name, int vol, String map,String diff);
    String getPlayerName();
    int getMusicVolume();
    String getDifficulty();
    String getMap();
    int getZombieSpawnWaveDelay();
    int getZombieSpawnDelay();
    int getSunDelay();
    int getTickDelay();

    // ---------------------------------------------------------------
    // LAWNMOWERS METHODS
    // ---------------------------------------------------------------
    int getNumLawnMowers();
    int getLawnMowersRow(int index);
    double getLawnMowersXPos(int index);
    boolean isLawnMowerUsed(int index);

    // ---------------------------------------------------------------
    // SUN METHODS
    // ---------------------------------------------------------------
    int getNumActiveSun();
    boolean isSunExpired(int index);
    double getSunXPos(int index);
    double getSunYPos(int index);
    void setSunIsCollected(int index);
    int getNumSun();
    void spawnSunTick();

    // ---------------------------------------------------------------
    // ZOMBIE METHODS
    // ---------------------------------------------------------------
    int getNumActiveZombie();
    int getZombieId(int index);
    String getZombieState(int index);
    int getZombieRow(int index);
    double getZombieXPos(int index);
    int getZombieDeathTick(int index);
    void spawnZombieTick();
    boolean isInWave();

    // ---------------------------------------------------------------
    // PROJECTILE METHODS
    // ---------------------------------------------------------------
    int getNumActiveProjectile();
    int getProjectileId(int index);
    int getProjectileRow(int index);
    double getProjectileXPos(int index);
    double getProjectileYPos(int index);

    // ---------------------------------------------------------------
    // PLANTS METHODS
    // ---------------------------------------------------------------
    int getPlantId(int r, int c);
    String getPlantState(int r, int c);
    boolean isPlayGridNonNull();
    boolean isPlantNonNull(int r, int c);
    int getPlantAttackTick(int r, int c);
    int getPlantPlantedTick(int r, int c);
    int getPlantGrowTick(int r, int c);
    int getPlantExplosionTick(int r, int c);
    int getPlantCost(int id);
    void setSelectedPlant(int[] plant);
    int[] getPlantsBar();
    boolean placePlant(int row, int col, int id);
    void removePlant(int r, int c);

    // ---------------------------------------------------------------
    // SCORE METHODS
    // ---------------------------------------------------------------
    List<String> getScoreList();
    void saveCurrentScore();
    int getScore();

    // ---------------------------------------------------------------
	// AUDIO METHODS
	// ---------------------------------------------------------------
    void playSound();
    void stopAll();
}
