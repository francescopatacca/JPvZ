package jpvz.utils;

import java.awt.Image;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class SpriteManager {

	// ---------------------------------------------------------------
	// STATIC CONSTANTS
	// ---------------------------------------------------------------

    private static final String[] PLANT_FOLDERS = {
            "resources/images/plants/cactus", 
            "resources/images/plants/catapulta_mais", 
            "resources/images/plants/catapulta_peperoni", 
            "resources/images/plants/cocco_cannon", 
            "resources/images/plants/fungo", 
            "resources/images/plants/fungo_solare", 
            "resources/images/plants/girasole", 
            "resources/images/plants/muro_di_noci", 
            "resources/images/plants/peeshooter", 
            "resources/images/plants/potato_mine", 
            "resources/images/plants/pugile", 
            "resources/images/plants/triple_peeshooter" 
    };

    private static final String[] ZOMBIE_FOLDERS = {
            "resources/images/zombies/caveman", 
            "resources/images/zombies/clown", 
            "resources/images/zombies/con_elmetto", 
            "resources/images/zombies/egiziano", 
            "resources/images/zombies/zombie_far_west", 
            "resources/images/zombies/zombie_futuro", 
            "resources/images/zombies/nano", 
            "resources/images/zombies/normale", 
            "resources/images/zombies/rugbysta" 
    };

    public static final String[] ICON_PATHS = {
            "resources/images/icons/home_jpvz.png",
            "resources/images/icons/lose_icon.png",
            "resources/images/icons/pala_icon.png",
            "resources/images/icons/sun_icon.png",
            "resources/images/icons/tagliaerba_icon.png",
            "resources/images/icons/win_icon.png",
            "resources/images/icons/cactus_icon.png", // ID 1
            "resources/images/icons/mais_icon.png", 
            "resources/images/icons/peperoni_icon.png", 
            "resources/images/icons/cocco_icon.png", 
            "resources/images/icons/fungo_icon.png", 
            "resources/images/icons/fungo_solare_icon.png", 
            "resources/images/icons/girasole_icon.png", 
            "resources/images/icons/noci_icon.png", 
            "resources/images/icons/peashooter_icon.png", 
            "resources/images/icons/mina_icon.png", 
            "resources/images/icons/pugile_icon.png", 
            "resources/images/icons/triplo_peashooter_icon.png" 
    };

    public static final String[] PROJECTILE_ICON_PATHS = {
            "resources/images/plants/cactus/PROIETTILE/T_cactus_projectile_52x35.png", 
            "resources/images/plants/catapulta_mais/PROIETTILE/animation0001.png",
            "resources/images/plants/catapulta_peperoni/PROIETTILE/animation0001.png", 
            "resources/images/plants/cocco_cannon/PROIETTILE/coconut_projectile0001.png", 
            "resources/images/plants/fungo/PROIETTILE/animation0001.png", 
            null, 
            null, 
            null, 
            "resources/images/plants/peeshooter/PROIETTILE/peashooter_33x35.png", 
            null, 
            null, 
            "resources/images/plants/triple_peeshooter/PROIETTILE/peashooter_33x35.png" 
    };

    public static final String[] BACKGROUND_PATHS = {
            "resources/images/backgrounds/cortile.png",
            "resources/images/backgrounds/cortile_notte.png",
            "resources/images/backgrounds/antico_egitto.png",
            "resources/images/backgrounds/far_west.png",
            "resources/images/backgrounds/futuro.png"
    };

    private static final Map<String, Animation> sprite = new HashMap<>();
    private static final Image[] bg = new Image[BACKGROUND_PATHS.length];
    private static final Image[] proj = new Image[PROJECTILE_ICON_PATHS.length];
    private static final Image[] icon = new Image[ICON_PATHS.length];
    private static final int DELAY=4;

	// ---------------------------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------------------------

    private SpriteManager(){
        
    }

	// ---------------------------------------------------------------
	// STATIC ANIMATION & IMAGE METHODS 
	// ---------------------------------------------------------------

    public static Animation getAnimation(String path, int delay) {
        if (!sprite.containsKey(path)) {
            sprite.put(path, new Animation(path, delay));
        }
        return sprite.get(path);
    }

    public static Animation getPlantAnimation(int id, String state) {
        if (id < 1 || id > PLANT_FOLDERS.length) {
            return null;
        }
        String path = PLANT_FOLDERS[id - 1] + "/" + state;
        return getAnimation(path, DELAY);
    }

    public static Animation getZombieAnimation(int id, String state) {
        if (id < 1 || id > ZOMBIE_FOLDERS.length) {
            return null;
        }
        String path = ZOMBIE_FOLDERS[id - 1] + "/" + state;
        return getAnimation(path, DELAY);
    }

    public static Image getCurrentBackGroundImage(){
		String map=Config.getInstance().getSelectedMap();
		if(map==null)
			return bg[0];

        switch (map.toLowerCase()) {
        case "prato notte":   
            return bg[1];
        case "antico egitto": 
            return bg[2];
        case "far west":      
            return bg[3];
        case "futuro":        
            return bg[4];
        case "prato giorno":
            return bg[0];
        default:              
            return bg[0];
        }
	}

    public static Image getProjectileImage(int i) {
        return proj[i];
    }

    public static Image getIconImage(int i) {
        return icon[i];
    }

	// ---------------------------------------------------------------
	// STATIC LOAD ALL IMAGES METHODS
	// ---------------------------------------------------------------

    public static void preLoadAll() {
        for (int id = 1; id <= PLANT_FOLDERS.length; id++) {
            if (id == 8) {
                getPlantAnimation(id, "BASE1");
                getPlantAnimation(id, "BASE2");
                getPlantAnimation(id, "BASE3");
                getPlantAnimation(id, "BASE4");
            } else if (id == 10) {
                getPlantAnimation(id, "BASE");
                getPlantAnimation(id, "ESPLOSIONE");
                getPlantAnimation(id, "PIANTATA");
                getPlantAnimation(id, "CRESCIUTA");
            } else {
                getPlantAnimation(id, "BASE");
                getPlantAnimation(id, "ATTACCO");
            }
        }

        for (int id = 1; id <= ZOMBIE_FOLDERS.length; id++) {
            getZombieAnimation(id, "CAMMINA");
            getZombieAnimation(id, "MANGIA");
            getZombieAnimation(id, "MUORE");
        }

        for (int i = 0; i < ICON_PATHS.length; i++) {
            if (ICON_PATHS[i] != null) {
                try {
                    Image img = ImageIO.read(Config.getInstance().getFile(ICON_PATHS[i]));
                    icon[i] = img;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else {
                icon[i] = null;
            }
        }

        for (int i = 0; i < BACKGROUND_PATHS.length; i++) {
            if (BACKGROUND_PATHS[i] != null) {
                try {
                    Image img = ImageIO.read(Config.getInstance().getFile(BACKGROUND_PATHS[i]));
                    bg[i] = img;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else {
                bg[i] = null;
            }
        }

        for (int i = 0; i < PROJECTILE_ICON_PATHS.length; i++) {
            if (PROJECTILE_ICON_PATHS[i] != null) {
                try {
                    Image img = ImageIO.read(Config.getInstance().getFile(PROJECTILE_ICON_PATHS[i]));
                    proj[i] = img;
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else {
                proj[i] = null;
            }
        }
    }

}
