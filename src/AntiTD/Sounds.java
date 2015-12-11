package AntiTD;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by dv13trm on 2015-12-10.
 */
public class Sounds {

    //sound
    Clip clip = null;
    long clipTime;


    public void music(String gameSound, boolean looping,boolean lowervolume)  {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(gameSound));
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            if(looping){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            //fungerar inte
            if(lowervolume){
             //sänk volymen
            }
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    public void pauseMusic(){
        clipTime = clip.getMicrosecondPosition();
        clip.stop();
    }
    public void resumeMusic(boolean looping){
        clip.setMicrosecondPosition(clipTime);
        if(looping) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        clip.start();
    }


}
