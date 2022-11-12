package kitchensurfer_MainSystem ;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private Clip clip ;
    private AudioInputStream ais[] = new AudioInputStream[8] ;
    
    public Sound() throws UnsupportedAudioFileException, IOException {
        ais[0] = AudioSystem.getAudioInputStream(new File("./src/main/java/Sound/happySound.wav")) ;
        ais[1] = AudioSystem.getAudioInputStream(new File("./src/main/java/Sound/hitSound.wav")) ;
        ais[2] = AudioSystem.getAudioInputStream(new File("./src/main/java/Sound/loseSound.wav")) ;
        ais[3] = AudioSystem.getAudioInputStream(new File("./src/main/java/Sound/selectSound.wav")) ;
        ais[4] = AudioSystem.getAudioInputStream(new File("./src/main/java/Sound/titleSound.wav")) ;
        ais[5] = AudioSystem.getAudioInputStream(new File("./src/main/java/Sound/whileRunning.wav")) ;
        ais[6] = AudioSystem.getAudioInputStream(new File("./src/main/java/Sound/winSound.wav")) ;
        ais[7] = AudioSystem.getAudioInputStream(new File("./src/main/java/Sound/ratSound.wav")) ;
    }
    
    public void setFile(int i) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        clip = AudioSystem.getClip() ;
        clip.open(ais[i]) ;
    }
    
    public void play() {
        clip.start() ;
    }
    
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY) ;
    }
    
    public void stop() {
        clip.stop() ;
    }
}