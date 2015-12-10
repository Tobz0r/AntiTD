package AntiTD;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by dv13trm on 2015-12-10.
 */
public class Sounds {

    //sound
    private String gameSound;
    Clip clip = null;
    long clipTime;


    public void nonLoopMusic(String gameSound)  {
        this.gameSound = gameSound;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(gameSound));
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    public void startMusic(String gameSound)  {
        this.gameSound = gameSound;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(gameSound));
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    public void pauseMusic(){
        clipTime = clip.getMicrosecondPosition();
        clip.stop();
    }
    public void resumeMusic(){
        clip.setMicrosecondPosition(clipTime);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }


}
