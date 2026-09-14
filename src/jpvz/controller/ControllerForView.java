package jpvz.controller;

import java.util.ArrayList;
import java.util.List;

import jpvz.model.Model;
import jpvz.utils.AudioManager;
import jpvz.utils.Config;
import jpvz.utils.Score;
import jpvz.utils.ScoreManager;
import jpvz.utils.SpriteManager;

import jpvz.view.View;

public class ControllerForView implements IControllerForView {

    // ---------------------------------------------------------------
    // STATIC ATTRIBUTES
    // ---------------------------------------------------------------

    private static ControllerForView instance;

    // ---------------------------------------------------------------
    // CONSTRUCTOR
    // ---------------------------------------------------------------

    private ControllerForView() {
        Model.getInstance().setPlayerName(Config.getInstance().getPlayerName());
        Model.getInstance().setMap(Config.getInstance().getSelectedMap());
        Model.getInstance().setDifficulty(Config.getInstance().getDifficulty());
        Model.getInstance().setNumSun(Config.getInstance().getStartingSun());
    }

    // ---------------------------------------------------------------
    // SINGLETON INSTANCE METHOD
    // ---------------------------------------------------------------

    public static IControllerForView getInstance() {
        if (instance == null) {
            instance = new ControllerForView();
        }
        return instance;
    }

    // ---------------------------------------------------------------
    // VIEW METHODS
    // ---------------------------------------------------------------

    public void openMainGui() {
        try {
            AudioManager.getInstance().setVolume();
            AudioManager.getInstance().loop("jpvz");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SpriteManager.preLoadAll();
        View.getInstance().openMainGui();
    }

    public void showPanel(String panelId) {
        View.getInstance().showPanel(panelId);
    }

    public void showError(String error) {
        View.getInstance().showError(error);
    }

    // ---------------------------------------------------------------
    // TIMER INITIALIZATION METHODS
    // ---------------------------------------------------------------

    public void startTimer() {
        View.getInstance().startTimer();
    }

    public void stopTimer() {
        View.getInstance().stopTimer();
    }

    public void pauseTimer() {
        View.getInstance().pauseTimer();
    }

    // ---------------------------------------------------------------
    // GAME STATUS METHODS
    // ---------------------------------------------------------------

    public boolean isGameOver() {
        return Model.getInstance().isGameOver();
    }

    public boolean isVictory() {
        return Model.getInstance().isVictory();
    }

    public void resetGame() {
        Model.getInstance().resetGame();
    }

    public void pauseGame() {
        Model.getInstance().pauseGame();
        if (isPaused()) {
            stopAll();
        }
    }

    public boolean isPaused() {
        return Model.getInstance().isPaused();
    }

    public void setPlayerName(String name) {
        if (name == null || name.trim().isEmpty()) {
            name = "Giocatore";
        }
        Config.getInstance().setName(name);
        Model.getInstance().setPlayerName(name);
    }

    public int getWave() {
        return Model.getInstance().getCurrentWave();
    }

    public void updateGame() {
        Model.getInstance().updateGame();

        playSound();
    }

    // ---------------------------------------------------------------
    // GAME SETTING METHODS
    // ---------------------------------------------------------------

    public void saveSetting(String name, int volume, String map, String diff) {
        if (name != null && !name.trim().isEmpty()) {
            setPlayerName(name);
        }
        Config.getInstance().setMusicVolume(volume);
        Config.getInstance().setMap(map);
        Config.getInstance().setDifficulty(diff);

        Model.getInstance().setMap(map);
        Model.getInstance().setDifficulty(diff);

        try {
            AudioManager.getInstance().setVolume();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getPlayerName() {
        return Model.getInstance().getPlayerName();
    }

    public int getMusicVolume() {
        return Config.getInstance().getMusicVolume();
    }

    public String getDifficulty() {
        return Model.getInstance().getDifficulty();
    }

    public String getMap() {
        return Model.getInstance().getMap();
    }

    public int getTickDelay() {
        return Config.getInstance().getTickDelay();
    }

    public int getSunDelay() {
        return Config.getInstance().getSunDelay();
    }

    public int getZombieSpawnDelay() {
        return Config.getInstance().getZombieSpawnDelay();
    }

    public int getZombieSpawnWaveDelay() {
        return Config.getInstance().getZombieSpawnWaveDelay();
    }

    // ---------------------------------------------------------------
    // LAWNMOWERS METHODS
    // ---------------------------------------------------------------

    public int getNumLawnMowers() {
        return Model.getInstance().getActiveLawnMowers().size();
    }

    public int getLawnMowersRow(int index) {
        return Model.getInstance().getActiveLawnMowers().get(index).getRow();
    }

    public double getLawnMowersXPos(int index) {
        return Model.getInstance().getActiveLawnMowers().get(index).getXPos();
    }

    public boolean isLawnMowerUsed(int index) {
        return Model.getInstance().getActiveLawnMowers().get(index).isUsed();
    }

    // ---------------------------------------------------------------
    // SUN METHODS
    // ---------------------------------------------------------------

    public int getNumActiveSun() {
        return Model.getInstance().getActiveSun().size();
    }

    public boolean isSunExpired(int index) {
        return Model.getInstance().getActiveSun().get(index).isExpired();
    }

    public double getSunXPos(int index) {
        return Model.getInstance().getActiveSun().get(index).getXPos();
    }

    public double getSunYPos(int index) {
        return Model.getInstance().getActiveSun().get(index).getYPos();
    }

    public void setSunIsCollected(int index) {
        Model.getInstance().getActiveSun().get(index).setIsCollected();
    }

    public int getNumSun() {
        return Model.getInstance().getNumSun();
    }

    public void spawnSunTick() {
        Model.getInstance().spawnSunTick();
    }
    // ---------------------------------------------------------------
    // ZOMBIE METHODS
    // ---------------------------------------------------------------

    public int getNumActiveZombie() {
        return Model.getInstance().getActiveZombies().size();
    }

    public int getZombieId(int index) {
        return Model.getInstance().getActiveZombies().get(index).getIdentifier();
    }

    public String getZombieState(int index) {
        return Model.getInstance().getActiveZombies().get(index).getState();
    }

    public int getZombieRow(int index) {
        return Model.getInstance().getActiveZombies().get(index).getRow();
    }

    public double getZombieXPos(int index) {
        return Model.getInstance().getActiveZombies().get(index).getXPos();
    }

    public int getZombieDeathTick(int index) {
        return Model.getInstance().getActiveZombies().get(index).getDeathTick();
    }

    public void spawnZombieTick() {
        Model.getInstance().spawnZombieTick();
    }

    public boolean isInWave() {
        return Model.getInstance().isInWave();
    }
    // ---------------------------------------------------------------
    // PROJECTILE METHODS
    // ---------------------------------------------------------------

    public int getNumActiveProjectile() {
        return Model.getInstance().getActiveProjectiles().size();
    }

    public int getProjectileId(int index) {
        return Model.getInstance().getActiveProjectiles().get(index).getId();
    }

    public int getProjectileRow(int index) {
        return Model.getInstance().getActiveProjectiles().get(index).getRow();
    }

    public double getProjectileXPos(int index) {
        return Model.getInstance().getActiveProjectiles().get(index).getXPos();
    }

    public double getProjectileYPos(int index) {
        return Model.getInstance().getActiveProjectiles().get(index).getYPos();
    }

    // ---------------------------------------------------------------
    // PLANTS METHODS
    // ---------------------------------------------------------------

    public int getPlantId(int r, int c) {
        return Model.getInstance().getPlayGrid()[r][c].getIdentifier();
    }

    public String getPlantState(int r, int c) {
        return Model.getInstance().getPlayGrid()[r][c].getState();
    }

    public boolean isPlayGridNonNull() {
        return Model.getInstance().getPlayGrid() != null;
    }

    public boolean isPlantNonNull(int r, int c) {
        return Model.getInstance().getPlayGrid()[r][c] != null;
    }

    public int getPlantAttackTick(int r, int c) {
        return Model.getInstance().getPlayGrid()[r][c].getAttackTick();
    }

    public int getPlantPlantedTick(int r, int c) {
        return Model.getInstance().getPlayGrid()[r][c].getPlantedTick();
    }

    public int getPlantGrowTick(int r, int c) {
        return Model.getInstance().getPlayGrid()[r][c].getGrowTick();
    }

    public int getPlantExplosionTick(int r, int c) {
        return Model.getInstance().getPlayGrid()[r][c].getExplosionTick();
    }

    public int getPlantCost(int id) {
        return Model.getInstance().getPlant(id).getCost();
    }

    public void setSelectedPlant(int[] plant) {
        Model.getInstance().initPlantsBar(plant);
    }

    public int[] getPlantsBar() {
        return Model.getInstance().getPlantsBar();
    }

    public boolean placePlant(int row, int col, int id) {
        return Model.getInstance().placePlant(row, col, id);
    }

    public void removePlant(int r, int c) {
        Model.getInstance().removePlant(r, c);
    }

    // ---------------------------------------------------------------
    // SCORE METHODS
    // ---------------------------------------------------------------

    public List<String> getScoreList() {
        List<Score> score = ScoreManager.loadScore();
        List<String> scoreString = new ArrayList<>();
        for (int i = 0; i <score.size(); i++) {
            scoreString.add(score.get(i).toString());
        }

        return scoreString;
    }

    public void saveCurrentScore() {
        int score = Model.getInstance().getScore();
        if(score>0){
            String PlayerName = Model.getInstance().getPlayerName();
            ScoreManager.saveScore(PlayerName, score);
        }
    }

    public int getScore() {
        return Model.getInstance().getScore();
    }

    // ---------------------------------------------------------------
    // AUDIO METHODS
    // ---------------------------------------------------------------

    private void playSound(String key) {
        try {
            AudioManager.getInstance().play(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void playSoundInLoop(String key) {
        try {
            AudioManager.getInstance().loop(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void stopSound(String key) {
        try {
            AudioManager.getInstance().stop(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playSound() {
        if (Model.getInstance().isEating())
            playSoundInLoop("mangiare");
        else
            stopSound("mangiare");

        for (int i = Model.getInstance().getActiveSound().size() - 1; i >= 0; i--) {
            playSound(Model.getInstance().getActiveSound().get(i));
            Model.getInstance().removeSound(i);
        }
    }

    public void stopAll() {
        try {
            AudioManager.getInstance().stopAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
