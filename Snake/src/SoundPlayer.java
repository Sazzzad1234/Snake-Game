import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class SoundPlayer {
   static Clip clip;
 public static void backmusic(String filepath){
    try{
    AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filepath));
    clip =AudioSystem.getClip();
    clip.open(audioIn);
    clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){
        e.printStackTrace();
    }
 }
 public static void stopMusic(){

 if(clip != null && clip.isRunning()){
    clip.stop();
 }
    }
    public static void playOnce(String filepath) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filepath));
            Clip onceClip = AudioSystem.getClip();
            onceClip.open(audioIn);
            onceClip.start(); // Play one time
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



