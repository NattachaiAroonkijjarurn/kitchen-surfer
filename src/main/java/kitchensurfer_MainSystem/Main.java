package kitchensurfer_MainSystem ;

//ALL IMPORT
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        //Create JFrame
        JFrame window = new JFrame() ;
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
        window.setTitle("Kitchen Surfer") ;
        
        //Add the Game to JFrame to show ur game when you run
        Game game = new Game() ;
        window.add(game) ;
        
        //TO make the screen size to fit ur content
        window.pack() ;
        
        //To set the window show at the center of the monitor
        window.setLocationRelativeTo(null) ;
        //To set user can't resizeable game window
        window.setResizable(false);
        //This method make the JFrame cam show on the monitor
        window.setVisible(true) ;
        
        //Set up the game
        game.setupGame() ;
        game.startGameThread() ;
    }
}
