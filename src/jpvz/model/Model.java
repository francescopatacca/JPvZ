package jpvz.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jpvz.model.plants.*;
import jpvz.model.zombies.*;

public class Model implements IModel {

	// ---------------------------------------------------------------
	// STATIC CONSTANTS
	// ---------------------------------------------------------------
	private static final int DEFAULT_NUM_ROWS = 5;
	private static final int DEFAULT_NUM_COLUMNS = 9;
	private static final int NUM_SLOT_PLANTS = 6;
	private static final int DEFAULT_NUM_SUN = 150;
	private static final int INCREMENT_SUN = 50;
	private static final int[] DEFAULT_PLANTS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	private static final int HITBOX = 15;
	private static final int ZOMBIE_BEFORE_ORDER = 10;
	private static final int MAX_WAVES = 3;
	private static final int CELL_SIZE = 80;
	private static final int HALF_CELL_SIZE = 40;
	private static final int PROJECTILE_OFFSET_X = 30;
	private static final int MELEE_RANGE = 50;
	private static final int PUGILE_MAX_RANGE = 60;
	private static final int POTATO_MINE_ID = 10;
	private static final int PLANT_PUGILE_ID = 11;
	private static final int TRIPLE_PEASHOOTER_ID = 12;
	private static final int WAVE_MULTIPLIER_DIFFICULTY=3;

	// ---------------------------------------------------------------
	// STATIC FIELDS
	// ---------------------------------------------------------------
	private static Model instance = null;

	// ---------------------------------------------------------------
	// INSTANCE ATTRIBUTES
	// ---------------------------------------------------------------
	private int score;
	private String playerName;
	private Plant[][] playGrid;
	private int[] plantsBar;
	private int numSun;
	private boolean isGameOver;
	private boolean isVictory;
	private boolean isPaused;
	private List<Zombie> activeZombies;
	private List<Projectile> activeProjectiles;
	private List<LawnMower> activeLawnMowers;
	private List<Sun> activeSun;
	private List<String> activeSound;
	private boolean isEating;
	private Random random;
	private int currentWave;
	private int zombieSpawnedInWave;
	private int zombieSpawned;
	private boolean isWaveActive;
	private String selectedMap;
	private String selectedDifficulty;

	// ---------------------------------------------------------------
	// CONSTRUCTOR & INITIALIZATION
	// ---------------------------------------------------------------
	private Model(int[] selectedPlants) {
		this.initGame(selectedPlants);
		this.selectedMap = "prato giorno";
		this.selectedDifficulty = "normale";
	}

	private void initGame(int[] selectedPlants) {
		this.playGrid = new Plant[DEFAULT_NUM_ROWS][DEFAULT_NUM_COLUMNS];
		this.numSun = 150;
		this.currentWave=1;
		this.activeZombies = new ArrayList<>();
		this.activeProjectiles = new ArrayList<>();
		this.activeSun = new ArrayList<>();
		this.activeSound=new ArrayList<>();
		this.random = new Random();
		this.score = 0;
		this.isEating=false;
		this.initGrid();
		this.initLawnMovers();
		this.initPlantsBar(selectedPlants);
	}

	private void initLawnMovers() {
		this.activeLawnMowers = new ArrayList<>();
		for (int i = 0; i < DEFAULT_NUM_ROWS; i++) {
			LawnMower lm = new LawnMower(i);
			activeLawnMowers.add(lm);
		}
	}

	private void initGrid() {
		for (int i = 0; i < DEFAULT_NUM_ROWS; i++) {
			for (int j = 0; j < DEFAULT_NUM_COLUMNS; j++) {
				this.playGrid[i][j] = null;
			}
		}
	}

	public void initPlantsBar(int[] selectedPlants) {
		this.plantsBar = new int[NUM_SLOT_PLANTS];
		if (selectedPlants != null && selectedPlants.length == NUM_SLOT_PLANTS) {
			System.arraycopy(selectedPlants, 0, this.plantsBar, 0, NUM_SLOT_PLANTS);
		} else {
			for (int i = 0; i < NUM_SLOT_PLANTS; i++) {
				this.plantsBar[i] = i + 1;
			}
		}
	}

	// ---------------------------------------------------------------
	// SINGLETON INSTANCE METHOD
	// ---------------------------------------------------------------

	public static IModel getInstance() {
		if (instance == null)
			instance = new Model(DEFAULT_PLANTS);
		return instance;
	}

	// ---------------------------------------------------------------
	// PLAYER METHODS
	// ---------------------------------------------------------------
	public int getScore() {
		return score;
	}

	private void incrementScore(int score) {
		this.score += score;
	}

	public String getPlayerName() {
		if (this.playerName != null && !this.playerName.trim().isEmpty()) {
			return this.playerName;
		}
		return "Giocatore";
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	// ---------------------------------------------------------------
	// GAME STATUS METHODS
	// ---------------------------------------------------------------

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public boolean isVictory() {
		return isVictory;
	}

	public void setVictory(boolean isVictory) {
		this.isVictory = isVictory;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public boolean isInWave() {
		return this.isWaveActive;
	}

	public String getDifficulty() {
		return this.selectedDifficulty;
	}

	public void setDifficulty(String diff) {
		this.selectedDifficulty = diff;
	}

	public int getIntDifficulty() {
		String diff = this.selectedDifficulty;
		if (diff == null)
			return 2;
		switch (diff.toLowerCase()) {
			case "facile":
				return 1;
			case "normale":
				return 2;
			case "difficile":
				return 3;
			case "impossibile":
				return 4;
			default:
				return 2;
		}
	}

	public String getMap() {
		return this.selectedMap;
	}

	public void setMap(String map) {
		this.selectedMap = map;
	}

	private void checkGameOver() {
		for (int i = activeZombies.size() - 1; i >= 0; i--) {
			Zombie z = activeZombies.get(i);
			if (z.getXPos() <= 0) {
				boolean mowerProtects = false;
				for (int j = activeLawnMowers.size() - 1; j >= 0; j--) {
					LawnMower lm = activeLawnMowers.get(j);
					if (lm.getRow() == z.getRow() && !lm.isUsed()) {
						mowerProtects = true;
						break;
					}
				}
				if (!mowerProtects && z.getXPos() <= -10) {
					this.isGameOver = true;
					break;
				}
			}
		}
	}

	private void checkVictory() {
		if (currentWave > MAX_WAVES && activeZombies.isEmpty() && !isGameOver) {
			this.isVictory = true;
		}
	}

	public void updateGame() {
		if (isPaused || isGameOver || isVictory)
			return;

		updatePlants();
		updateLawnMowers();
		updateProjectile();
		updateZombie();
		updateSun();

		checkCollisionPlantZombie();
		checkCollisionProjectileZombie();
		checkCollisionLawnMowerZombie();
		shoot();
		checkGameOver();
		generateSunFromPlants();
		checkVictory();
	}

	private void updateProjectile() {
		for (int i = activeProjectiles.size() - 1; i >= 0; i--) {
			Projectile p=activeProjectiles.get(i);
			p.move();
			if(p.getId()==PLANT_PUGILE_ID && p.getDistanceTravelled()>PUGILE_MAX_RANGE){
				removeProjectile(p);
			}else if(p.isOffScreen()){
				removeProjectile(p);
			}
		}
	}

	private void updateZombie() {
		for (int i = activeZombies.size() - 1; i >= 0; i--) {
			activeZombies.get(i).move();
			int num = 200 + random.nextInt(700);
			int sound = random.nextInt(100);
			Zombie z = activeZombies.get(i);
			if (z.getXPos() < num && z.getXPos() > 200 && z.getSound() && sound == 55) {
				this.activeSound.add("zombie");
				z.sound();
			}
		}

		for (int i = activeZombies.size() - 1; i >= 0; i--) {
			activeZombies.get(i).update();
			if (activeZombies.get(i).canRemoveZombie())
				removeZombie(activeZombies.get(i));
		}
	}

	private void updatePlants() {
		for (int r = 0; r < DEFAULT_NUM_ROWS; r++) {
			for (int c = 0; c < DEFAULT_NUM_COLUMNS; c++) {
				if (playGrid[r][c] != null) {
					playGrid[r][c].update();
					if (playGrid[r][c].getIdentifier() == POTATO_MINE_ID) {
						PotatoMine mine = (PotatoMine) playGrid[r][c];
						if (mine.isExplosionFinished())
							playGrid[r][c] = null;
					}
				}
			}
		}
	}

	private void updateSun() {
		for (int i = activeSun.size() - 1; i >= 0; i--) {
			Sun s = activeSun.get(i);
			s.update();
			if (s.isCollected()) {
				incrementNumSun(activeSun.get(i).getNumSun());
				this.activeSound.add("sole");
				activeSun.remove(s);
			} else if (s.isExpired()) {
				activeSun.remove(s);
			}
		}
	}

	private void updateLawnMowers() {
		for (int j = activeLawnMowers.size() - 1; j >= 0; j--) {
			LawnMower lm = activeLawnMowers.get(j);
			lm.update();
		}
	}

	public void pauseGame() {
		this.isPaused = !this.isPaused;
	}

	public void resetGame() {
		this.isGameOver = false;
		this.isVictory = false;
		this.isPaused = false;
		this.initGrid();
		this.initLawnMovers();
		this.score = 0;
		this.numSun = DEFAULT_NUM_SUN;
		this.currentWave = 1;
		this.zombieSpawned = 0;
		this.zombieSpawnedInWave = 0;
		this.isWaveActive = false;
		this.isEating=false;
		this.activeZombies.clear();
		this.activeProjectiles.clear();
		this.activeSun.clear();
		this.activeSound.clear();
	}

	// ---------------------------------------------------------------
	// SUN MANAGEMENT METHODS
	// ---------------------------------------------------------------
	public int getNumSun() {
		return numSun;
	}

	public void setNumSun(int sun){
		this.numSun=sun;
	}

	public List<Sun> getActiveSun() {
		return this.activeSun;
	}

	public void incrementNumSun() {
		this.numSun += INCREMENT_SUN;
	}

	public void incrementNumSun(int s) {
		this.numSun += s;
	}

	public void spawnSunTick() {
		if (!isPaused && !isGameOver) {
			int c = random.nextInt(DEFAULT_NUM_COLUMNS);
			this.activeSun.add(new Sun(0, c, 50));
		}
	}

	private void generateSunFromPlants() {
		for (int i = 0; i < DEFAULT_NUM_ROWS; i++) {
			for (int j = 0; j < DEFAULT_NUM_COLUMNS; j++) {
				Plant plant = playGrid[i][j];
				if (plant != null && plant.getSunProduced() > 0) {
					if (plant.canProduceSun())
						plant.startAttack();
					if (plant.shoot()) {
						Sun s = new Sun(i, j, plant.getSunProduced());
						activeSun.add(s);
					}
				}

			}
		}
	}

	// ---------------------------------------------------------------
	// FACTORY METHODS
	// ---------------------------------------------------------------
	public Plant getPlant(int id) {
		switch (id) {
			case 1:
				return new Cactus();
			case 2:
				return new CatapultaMais();
			case 3:
				return new CatapultaPeperoni();
			case 4:
				return new CoccoCannon();
			case 5:
				return new Fungo();
			case 6:
				return new FungoSolare();
			case 7:
				return new Girasole();
			case 8:
				return new MuroDiNoci();
			case 9:
				return new Peashooter();
			case 10:
				return new PotatoMine();
			case 11:
				return new Pugile();
			case 12:
				return new TriplePeashooter();
			default:
				return null;
		}
	}

	public Zombie getZombie(int id) {
		switch (id) {
			case 1:
				return new ZombieCaveman();
			case 2:
				return new ZombieClown();
			case 3:
				return new ZombieConElmetto();
			case 4:
				return new ZombieEgiziano();
			case 5:
				return new ZombieFarWest();
			case 6:
				return new ZombieFuturo();
			case 7:
				return new ZombieNano();
			case 8:
				return new ZombieNormale();
			case 9:
				return new ZombieRugbysta();
			default:
				return null;
		}
	}

	// ---------------------------------------------------------------
	// PLANT & GRID METHODS
	// ---------------------------------------------------------------

	public Plant[][] getPlayGrid() {
		return playGrid;
	}

	public void setPlayGrid(Plant[][] playGrid) {
		this.playGrid = playGrid;
	}

	public int[] getPlantsBar() {
		return this.plantsBar;
	}

	public void setPlantsBar(int[] plants) {
		this.plantsBar = plants;
	}

	public boolean placePlant(int row, int col, int id) {
		if (this.playGrid[row][col] == null) {
			Plant newPlant = getPlant(id);
			if (newPlant != null && this.numSun >= newPlant.getCost()) {
				this.numSun -= newPlant.getCost();
				this.activeSound.add("piantare");
				this.playGrid[row][col] = newPlant;
				return true;
			}
		}
		return false;
	}

	public void removePlant(int rows, int colums) {
		this.playGrid[rows][colums] = null;
	}

	// ---------------------------------------------------------------
	// PROJECTILE MANAGEMENT METHODS
	// ---------------------------------------------------------------

	public List<Projectile> getActiveProjectiles() {
		return this.activeProjectiles;
	}

	private void spawnProjectile(String name, int row, double xPos, int damage, double speed, int id,double targetY) {
		Projectile pro = new Projectile(name, row, xPos, damage, speed, id,targetY);
		activeProjectiles.add(pro);
	}

	private void removeProjectile(Projectile p) {
		activeProjectiles.remove(p);
	}

	private void shoot() {
		for (int i = 0; i < DEFAULT_NUM_ROWS; i++) {
			for (int j = 0; j < DEFAULT_NUM_COLUMNS; j++) {
				Plant plant = playGrid[i][j];
				if (plant != null && plant.getSpeed() > 0) {
					double zombieX=720;
					int id = plant.getIdentifier();
					boolean zombieFront = false;
					boolean zombieAhead = false;
					boolean zombieInRange = false;
					for (int k = activeZombies.size() - 1; k >= 0; k--) {
						boolean[] check = checkShootCondition(k, i, j, id);
						if (check[0]){
							zombieFront = true;
							Zombie z=activeZombies.get(k);
							double dist=(z.getXPos()+30)-(j*CELL_SIZE);
							double time=dist/(plant.getSpeed()+z.getSpeed());
							zombieX=(z.getXPos()+30)-(z.getSpeed()*time);
						}						
						if (check[1]){
							zombieAhead = true;
							zombieX=activeZombies.get(k).getXPos()+30;
						}						
						if (check[2]){
							zombieInRange = true;
							zombieX=activeZombies.get(k).getXPos()+30;
						}						
					}
					plantShooting(plant, id, i, j, zombieAhead, zombieInRange, zombieFront, zombieX);
				}
			}
		}
	}

	private boolean[] checkShootCondition(int k, int i, int j, int id) {
		boolean[] condition = new boolean[3];
		// PIANTE NORMALI
		if (activeZombies.get(k).getRow() == i && activeZombies.get(k).getXPos() >= j * CELL_SIZE && activeZombies.get(k).getHp() > 0) {
			condition[0] = true;
		}
		// PIANTA PUGILE
		if (activeZombies.get(k).getRow() == i && activeZombies.get(k).getHp() > 0) {
			double dist = activeZombies.get(k).getXPos() - (j * CELL_SIZE);
			if ((dist >= 0 && dist <= MELEE_RANGE) && id == PLANT_PUGILE_ID) {
				condition[1] = true;
			}
		}
		// TRIPLO PEASHOOTER

		if (id==TRIPLE_PEASHOOTER_ID && activeZombies.get(k).getHp() > 0) {
			boolean centralRow=(activeZombies.get(k).getRow() == i && activeZombies.get(k).getXPos() >= j * CELL_SIZE);
			boolean topRow=((i - 1) >= 0 && activeZombies.get(k).getRow() == i - 1 && activeZombies.get(k).getXPos() >= j * CELL_SIZE);
			boolean bottomRow =((i + 1) < DEFAULT_NUM_ROWS && activeZombies.get(k).getRow() == i + 1 && activeZombies.get(k).getXPos() >= j * CELL_SIZE);	
			if (centralRow || topRow || bottomRow) {
				condition[2] = true;
			}
		}
		return condition;
	}

	private void plantShooting(Plant plant, int id, int i, int j, boolean zombieAhead, boolean zombieInRange, boolean zombieFront,double zombieX) {
		if (id == PLANT_PUGILE_ID) {
			if (zombieAhead && plant.canShoot()) {
				plant.startAttack();
			}
			if (playGrid[i][j].shoot())
				spawnProjectile(plant.getPlantName(), i, j * CELL_SIZE, plant.getDamage(), plant.getSpeed(), id,zombieX);
		}

		else if (id == TRIPLE_PEASHOOTER_ID) {
			if (zombieInRange && plant.canShoot()) {
				plant.startAttack();
			}
			if (playGrid[i][j].shoot()) {
				spawnProjectile(plant.getPlantName(), i, j * CELL_SIZE + PROJECTILE_OFFSET_X, plant.getDamage(), plant.getSpeed(), id,zombieX);
				if (i - 1 >= 0)
					spawnProjectile(plant.getPlantName(), i - 1, j * CELL_SIZE + PROJECTILE_OFFSET_X, plant.getDamage(), plant.getSpeed(), id,zombieX);
				if (i + 1 < DEFAULT_NUM_ROWS)
					spawnProjectile(plant.getPlantName(), i + 1, j * CELL_SIZE + PROJECTILE_OFFSET_X, plant.getDamage(), plant.getSpeed(), id,zombieX);
			}

		} else {
			if (zombieFront && plant.canShoot()) {
				plant.startAttack();
			}
			if (playGrid[i][j].shoot())
				spawnProjectile(plant.getPlantName(), i, j * CELL_SIZE + PROJECTILE_OFFSET_X, plant.getDamage(), plant.getSpeed(), id,zombieX);
		}
	}

	private void potatoMineSplashDamage(int r, int c, int dmg) {
		for (int i = activeZombies.size() - 1; i >= 0; i--) {
			Zombie z = activeZombies.get(i);
			int zRow = z.getRow();
			int xPlant = (c * CELL_SIZE) + HALF_CELL_SIZE;
			int rangeMax = xPlant + CELL_SIZE;
			int rangeMin = xPlant - CELL_SIZE;
			double diff = Math.abs(z.getXPos() - xPlant);
			boolean condition=((r == zRow) || (r - 1 >= 0 && r - 1 == zRow) || (r + 1 < DEFAULT_NUM_ROWS && r + 1 == zRow)) && (diff < xPlant - rangeMin || diff < rangeMax - xPlant);
			if (condition) {
				activeZombies.get(i).takeDamage(dmg);
			}
		}
	}

	// ---------------------------------------------------------------
	// LAWNMOWER MANAGEMENT METHODS
	// ---------------------------------------------------------------

	public List<LawnMower> getActiveLawnMowers() {
		return this.activeLawnMowers;
	}

	// ---------------------------------------------------------------
	// ZOMBIE MANAGEMENT METHODS
	// ---------------------------------------------------------------

	public List<Zombie> getActiveZombies() {
		return this.activeZombies;
	}

	public int getCurrentWave() {
		return Math.min(this.currentWave, MAX_WAVES);
	}

	private void spawnZombie(int zombieId, int row) {
		Zombie zombie = getZombie(zombieId);
		if (zombie != null) {
			zombie.setRow(row);
			zombie.setXPos(DEFAULT_NUM_COLUMNS * CELL_SIZE);
			this.activeZombies.add(zombie);
		}
	}

	private void removeZombie(Zombie z) {
		if (activeZombies.remove(z)) {
			incrementScore(z.getScore());
		}
	}

	public void updateZombie(Zombie z, double change) {
		if (z != null) {
			z.updateXPos(change);
		}
	}

	public void spawnZombieTick() {
		if (isGameOver || isPaused || isVictory || currentWave>MAX_WAVES)
			return;

		if (!isWaveActive) {
			int randomRow = random.nextInt(DEFAULT_NUM_ROWS);
			int randomZombie = random.nextInt(9) + 1;
			spawnZombie(randomZombie, randomRow);
			zombieSpawned++;
			if (zombieSpawned >= ZOMBIE_BEFORE_ORDER) {
				isWaveActive = true;
				zombieSpawned = 0;
				zombieSpawnedInWave = 0;
			}
		} else {
			int numberofZombieInWave = getIntDifficulty() * WAVE_MULTIPLIER_DIFFICULTY + 5;
			int numberOfZombieInWaveForTick = 1 + getIntDifficulty();
			for (int i = 0; i < numberOfZombieInWaveForTick; i++) {
				int randomRow = random.nextInt(DEFAULT_NUM_ROWS);
				int randomZombie = random.nextInt(9) + 1;
				spawnZombie(randomZombie, randomRow);
				zombieSpawnedInWave++;
				if (zombieSpawnedInWave >= numberofZombieInWave) {
					isWaveActive = false;
					zombieSpawnedInWave = 0;
					currentWave++;
				}
			}
		}
	}

	// ---------------------------------------------------------------
	// AUDIO MANAGEMENT METHODS
	// ---------------------------------------------------------------

	public List<String> getActiveSound(){
		return this.activeSound;
	}

	public void removeSound(int index){
		this.activeSound.remove(index);
	}

	public boolean isEating(){
		return this.isEating;
	}

	// ---------------------------------------------------------------
	// COLLISION MANAGEMENT METHODS
	// ---------------------------------------------------------------

	private void checkCollisionProjectileZombie() {
		for (int i = activeProjectiles.size() - 1; i >= 0; i--) {
			for (int j = activeZombies.size() - 1; j >= 0; j--) {
				if (activeZombies.get(j).getHp() > 0 && activeProjectiles.get(i).getRow() == activeZombies.get(j).getRow()) {
					int dmg = activeProjectiles.get(i).getDamage();
					double xPosProj = activeProjectiles.get(i).getXPos();
					double xPosZombie = activeZombies.get(j).getXPos();
					double diff = Math.abs(xPosProj - (xPosZombie + 30));

					if (diff <= HITBOX) {
						this.activeSound.add("proiettile");
						activeZombies.get(j).takeDamage(dmg);
						removeProjectile(activeProjectiles.get(i));
						break;
					}

				}
			}
		}
	}

	private void checkCollisionPlantZombie() {

		for (int i = activeZombies.size() - 1; i >= 0; i--) {
			activeZombies.get(i).setEating(false);
		}
		boolean anyEating = false;

		for (int i = 0; i < DEFAULT_NUM_ROWS; i++) {
			for (int j = 0; j < DEFAULT_NUM_COLUMNS; j++) {
				if (playGrid[i][j] == null)
					continue;
				if(zombieEat(i, j))
					anyEating=true;
			}
		}
		if (anyEating) {
			this.isEating=true;
		} else {
			this.isEating=false;
		}

	}

	private boolean zombieEat(int i, int j) {
		boolean anyEating=false;
		for (int l = activeZombies.size() - 1; l >= 0; l--) {
			if(playGrid[i][j]==null)
				break;
			if (activeZombies.get(l).getHp() <= 0)
				continue;
			if (activeZombies.get(l).getRow() == i) {
				double xPosZombie = activeZombies.get(l).getXPos();
				double xPosPlant = j * CELL_SIZE;
				double diff = Math.abs(xPosZombie - xPosPlant);
				double dmg = activeZombies.get(l).getEatDamage();
				if (diff <= HITBOX) {
					if(zombieMinaInteraction(i, j, l)){
						anyEating=true;
						continue;
					}
					activeZombies.get(l).setEating(true);
					anyEating = true;
					playGrid[i][j].takeDamage(dmg);
					if (playGrid[i][j].getHp() <= 0) {
						this.activeSound.add("mangiata");
						playGrid[i][j] = null;
						activeZombies.get(l).setEating(false);
					}
				}
			}
		}
		return anyEating;
	}

	private boolean zombieMinaInteraction(int i,int j,int l) {
		if (playGrid[i][j].getIdentifier() == POTATO_MINE_ID) {
			PotatoMine mine = (PotatoMine) playGrid[i][j];
			if (mine.isGrown()) {
				mine.explode();
				activeZombies.get(l).setEating(true);
				return true;
			}
			if (mine.shouldDoDamage()) {
				this.activeSound.add("mina");
				potatoMineSplashDamage(i, j, mine.getDamage());
				return true;
			}
			if (mine.getState().equals("ESPLOSIONE")) {
				activeZombies.get(l).setEating(true);
				return true;
			}
		}
		return false;
	}

	private void checkCollisionLawnMowerZombie() {
		for (int i = activeLawnMowers.size() - 1; i >= 0; i--) {
			LawnMower lm = activeLawnMowers.get(i);
			if (lm.isUsed())
				continue;

			for (int j = activeZombies.size() - 1; j >= 0; j--) {
				Zombie z = activeZombies.get(j);
				if (z.getRow() == lm.getRow() && z.getHp() > 0) {

					if (!lm.isActivated() && z.getXPos() <= 2) {
						lm.activate();
					}

					if (lm.isActivated()) {
						if (lm.getXPos() >= z.getXPos()) {
							this.activeSound.add("tagliaerba");
							activeZombies.get(j).takeDamage(lm.getDamage());
						}
					}
				}
			}
		}
	}
}
