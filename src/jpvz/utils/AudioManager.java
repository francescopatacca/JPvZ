package jpvz.utils;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager {

    // ---------------------------------------------------------------
    // STATIC CONSTANTS
    // ---------------------------------------------------------------

    private static final String[] AUDIO_PATHS = {
            "resources/audio/audio_jpvz.wav",
            "resources/audio/mangiare.wav",
            "resources/audio/mine.wav",
            "resources/audio/pianta_mangiata.wav",
            "resources/audio/piantare.wav",
            "resources/audio/proiettile.wav",
            "resources/audio/sole.wav",
            "resources/audio/tagliaerba.wav",
            "resources/audio/zombie.wav"
    };

    private static final String[] AUDIO_KEY = {
            "jpvz",
            "mangiare",
            "mina",
            "mangiata",
            "piantare",
            "proiettile",
            "sole",
            "tagliaerba",
            "zombie"
    };

    private static AudioManager instance = null;

    // ---------------------------------------------------------------
    // INSTANCE ATTRIBUTES
    // ---------------------------------------------------------------

    private Map<String, Clip> audio;

    private AudioManager() throws UnsupportedAudioFileException, IOException {
        this.audio = new HashMap<>();
        for (int i = 0; i < AUDIO_PATHS.length; i++) {
            File audioFile = Config.getInstance().getFile(AUDIO_PATHS[i]);
            String key = AUDIO_KEY[i];
            AudioFileFormat aff = AudioSystem.getAudioFileFormat(audioFile);
            AudioFormat af = aff.getFormat();
            AudioInputStream ais = null;

            try {
                ais = AudioSystem.getAudioInputStream(audioFile);
                int bufferSize = (int) ais.getFrameLength() * af.getFrameSize();
                DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, ais.getFormat(), bufferSize);

                if (!AudioSystem.isLineSupported(dataLineInfo))
                    throw new IOException("Error");
                try {
                    Clip clip = (Clip) AudioSystem.getLine(dataLineInfo);
                    this.audio.put(key, clip);
                    clip.open(ais);
                } catch (LineUnavailableException lue) {
                    throw new IOException("Error");
                }
            } catch (UnsupportedAudioFileException uafe) {
                uafe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                if (ais != null)
                    ais.close();
            }
        }

    }

    // ---------------------------------------------------------------
    // INSTANCE METHODS
    // ---------------------------------------------------------------

    public void play(String key) {
        Clip clip = this.audio.get(key);
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void close(String key) {
        Clip clip = this.audio.get(key);
        if (clip != null) {
            clip.close();
        }
    }

    public void stop(String key) {
        Clip clip = this.audio.get(key);
        if (clip != null) {
            clip.stop();
        }
    }

    public void loop(String key) {
        Clip clip = this.audio.get(key);
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void closeAll() {
        for (int i = 0; i < AUDIO_PATHS.length; i++) {
            Clip clip = this.audio.get(AUDIO_KEY[i]);
            if (clip != null && !AUDIO_KEY[i].equals("jpvz")) {
                clip.close();
            }
        }
    }

    public void stopAll() {
        for (int i = 0; i < AUDIO_PATHS.length; i++) {
            Clip clip = this.audio.get(AUDIO_KEY[i]);
            if (clip != null && !AUDIO_KEY[i].equals("jpvz")) {
                clip.stop();
                clip.setFramePosition(0);
            }
        }
    }

    public void setVolume() {
        int volume = Config.getInstance().getMusicVolume();
        for (int i = 0; i < AUDIO_PATHS.length; i++) {
            Clip clip = this.audio.get(AUDIO_KEY[i]);
            if (clip != null) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                if (volume == 0) {
                    gainControl.setValue(gainControl.getMinimum());
                } else {
                    float db = (float) (Math.log10(volume / 100.0) * 20.0);

                    if ("jpvz".equals(AUDIO_KEY[i])) {
                        db -= 25f;
                    }
                    db = Math.max(gainControl.getMinimum(), Math.min(db, gainControl.getMaximum()));
                    gainControl.setValue(db);
                }
            }
        }
    }

    // ---------------------------------------------------------------
    // STATIC SINGLETON METHODS
    // ---------------------------------------------------------------

    public static AudioManager getInstance() throws UnsupportedAudioFileException, IOException {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

}
