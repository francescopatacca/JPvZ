package jpvz.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URISyntaxException;

import java.util.Properties;

public class Config {

	private static boolean IS_DIST_VERSION = false;

	private static Config instance = null;

	private String baseDir = null;

	// ---------------------------------------------------------------
	// INSTANCE ATTRIBUTE
	// ---------------------------------------------------------------

	private Properties properties;

	private void setDefaultProperties() {
		this.properties.setProperty("startingSun", "150");
		this.properties.setProperty("tickDelay", "20");
		this.properties.setProperty("sunDelay", "10000");
		this.properties.setProperty("zombieSpawnDelay", "7000");
		this.properties.setProperty("zombieSpawnWaveDelay", "4000");
		this.properties.setProperty("playerName", "Giocatore");
		this.properties.setProperty("musicVolume", "50");
		this.properties.setProperty("selectedMap", "prato giorno");
		this.properties.setProperty("difficulty", "normale");
	}

	// ---------------------------------------------------------------
	// PRIVATE INSTANCE METHODS
	// ---------------------------------------------------------------

	private Config() {
		this.properties = new Properties();
		this.setDefaultProperties();
		try {
			String configFile = getConfigFile();
			if (configFile != null && new File(configFile).exists()) {
				BufferedReader buffRead = new BufferedReader(
						new InputStreamReader(new FileInputStream(configFile), "ISO-8859-1"));
				this.properties.load(buffRead);
			} else {
				System.err.println(
						"JPvZ warning: Configuration file 'conf/config.txt' not found. Using default settings.");
			}
		} catch (URISyntaxException urise) {
			System.err
					.println("JPvZ warning: URI syntax error resolving config file location. Using default settings.");
			urise.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			System.err.println("JPvZ warning: Configuration file not found. Using default settings.");
		} catch (IOException ioe) {
			System.err.println("JPvZ warning: IO error reading configuration file. Using default settings.");
			ioe.printStackTrace();
		}
	}

	private String getConfigFile() throws URISyntaxException {
		String relPath = File.separator + "conf" + File.separator + "config.txt";

		// Candidate 1: Standard dev version (relative to class location)
		try {
			String candidate1 = getHomeFolderForDevVersion() + relPath;
			if (new File(candidate1).exists()) {
				return candidate1;
			}
		} catch (Exception e) {

		}

		// Candidate 2: Current working directory (e.g. project root when run from IDE)
		String candidate2 = System.getProperty("user.dir") + relPath;
		if (new File(candidate2).exists()) {
			return candidate2;
		}

		// Candidate 3: Parent of current working directory (e.g. if we are inside bin/)
		String candidate3 = new File(System.getProperty("user.dir")).getParent() + relPath;
		if (new File(candidate3).exists()) {
			return candidate3;
		}

		// Fallback
		if (IS_DIST_VERSION)
			return getHomeFolderForDistVersion() + relPath;
		else
			return getHomeFolderForDevVersion() + relPath;
	}

	private String getHomeFolderForDistVersion() throws URISyntaxException {
		String homeDir = null;
		String jarPath = Config.class.getResource("Config.class").toURI().toString();
		int indexOfExclamationMark = jarPath.indexOf("!");
		String prefix = "jar:file:/";
		if (System.getProperty("os.name").startsWith("Linux")) {
			prefix = "jar:file:";
		}
		homeDir = jarPath.substring(prefix.length(), indexOfExclamationMark);
		int lastIndexOfSlash = homeDir.lastIndexOf("/");
		homeDir = homeDir.substring(0, lastIndexOfSlash);
		return homeDir;
	}

	private String getHomeFolderForDevVersion() throws URISyntaxException {
		File configFile = null;
		File byteCodeFileOfThisClass = new File(Config.class.getResource("Config.class").toURI());
		configFile = byteCodeFileOfThisClass.getParentFile().getParentFile().getParentFile().getParentFile();
		return configFile.toString();
	}

	private String getBaseDir() {
		if (this.baseDir == null) {
			try{
				File f=new File(getConfigFile());
				File confDir= f.getParentFile();
				if(confDir!=null && confDir.getParent()!=null){
					this.baseDir=confDir.getParent();
				}else{
					this.baseDir=System.getProperty("user.dir");
				}
			}catch(Exception e){
				this.baseDir=System.getProperty("user.dir");
			}
		}
		return  this.baseDir;
	}

	// ---------------------------------------------------------------
	// PUBLIC INSTANCE METHODS
	// ---------------------------------------------------------------

	public int getStartingSun() {
		return Integer.parseInt(this.properties.getProperty("startingSun", "150"));
	}

	public int getTickDelay() {
		return Integer.parseInt(this.properties.getProperty("tickDelay", "50"));
	}

	public int getSunDelay() {
		return Integer.parseInt(this.properties.getProperty("sunDelay", "10000"));
	}

	public int getZombieSpawnDelay() {
		return Integer.parseInt(this.properties.getProperty("zombieSpawnDelay", "7000"));
	}

	public int getZombieSpawnWaveDelay() {
		return Integer.parseInt(this.properties.getProperty("zombieSpawnWaveDelay", "4000"));
	}

	public String getPlayerName() {
		return this.properties.getProperty("playerName", "Giocatore");
	}

	public int getMusicVolume() {
		return Integer.parseInt(this.properties.getProperty("musicVolume", "50"));
	}

	public String getSelectedMap() {
		return this.properties.getProperty("selectedMap", "prato giorno");
	}

	public String getDifficulty() {
		return this.properties.getProperty("difficulty", "normale");
	}

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}

	public void setMusicVolume(int v) {
		this.properties.setProperty("musicVolume", String.valueOf(v));
	}

	public void setName(String name) {
		this.properties.setProperty("playerName", name);
	}

	public void setDifficulty(String d) {
		this.properties.setProperty("difficulty", d);
	}

	public void setMap(String m) {
		this.properties.setProperty("selectedMap", m);
	}

	public File getFile(String path){
		return new File(getBaseDir(), path);
	}

	// ---------------------------------------------------------------
	// STATIC SINGLETON METHODS
	// ---------------------------------------------------------------

	public static Config getInstance() {
		if (instance == null)
			instance = new Config();
		return instance;
	}

}
