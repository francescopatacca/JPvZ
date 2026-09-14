package jpvz.utils;

import javax.imageio.ImageIO;

import java.awt.Image;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;

public class Animation {

	// ---------------------------------------------------------------
	// INSTANCE ATTRIBUTES
	// ---------------------------------------------------------------

    private Image[] frames;
    private int delay;

    public Animation(String path, int delay){
        this.delay=delay;
        File folder=Config.getInstance().getFile(path);
        File[] files=folder.listFiles((dir,name)->name.toLowerCase().endsWith(".png"));

        if(files!=null && files.length>0){
            Arrays.sort(files);
            this.frames=new Image[files.length];
            for(int i=0;i<files.length;i++){
                try{
                    this.frames[i]=ImageIO.read(files[i]);
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }

	// ---------------------------------------------------------------
	// INSTANCE METHODS
	// ---------------------------------------------------------------

    public Image getFrame(long tick){
        if(this.frames!=null && this.frames.length>0){
            int index=(int)((tick/this.delay)%frames.length);
            return frames[index];
        }
        return null;
    }

}
