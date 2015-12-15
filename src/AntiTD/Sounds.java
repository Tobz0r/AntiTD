package AntiTD;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
/**
 * @author Thom Renström
 * Sound class that is used for every sound that is made during a running session of the game
 *
 */

public class Sounds {

    //sound
    Clip clip = null;
    long clipTime;
    private boolean playing=false;


    /**
     * Takes a string of an imported music and creates a clip with sound from that string
     * @param gameSound string of what sound that should be playing
     * @param looping boolean that if true the sound will loop, if false it will not
     */
    public void music(String gameSound, boolean looping)  {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(gameSound));
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(audioInputStream);
            if(looping){
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            playing=true;
            clip.start();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pauses the clip on the class where this function is called
     */
    public void pauseMusic(){
        clipTime = clip.getMicrosecondPosition();
        clip.stop();
        playing=false;
    }

    /**
     * Resumes the clip on the class where this function is called
     * @param looping boolean that if true the sound will loop, if false it will not
     */
    public void resumeMusic(boolean looping){
        clip.setMicrosecondPosition(clipTime);
        if(looping) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        playing=true;
        clip.start();
    }
    /**
     * @return a boolean that check if music is playing
     */
    public boolean isPlaying(){
        return playing;
    }
}
