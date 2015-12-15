package AntiTD;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Thom Renström
 */
public class Sounds {

    //sound
    Clip clip = null;
    long clipTime;
    private boolean playing=false;



    public void music(String gameSound, boolean looping)  {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(gameSound));
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            if(looping){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            //fungerar inte

            playing=true;
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
    public void pauseMusic(){
        clipTime = clip.getMicrosecondPosition();
        clip.stop();
        playing=false;
    }
    public void resumeMusic(boolean looping){
        clip.setMicrosecondPosition(clipTime);
        if(looping) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        playing=true;
        clip.start();
    }

    public boolean isPlaying(){
        return playing;
    }


}
