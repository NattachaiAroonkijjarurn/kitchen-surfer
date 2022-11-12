package kitchensurfer_MainSystem ;

//ALL IMPORT
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    
    private Game g ;

    public Config(Game g) {
        this.g = g;
    }
    
    public void saveConfig(int minute, int second) throws IOException {
        try (BufferedWriter save = new BufferedWriter(new FileWriter("./src/main/java/Save/config.txt"))) {
            //Write minute
            save.write(""+minute) ;
            //New line
            save.newLine() ;
            //Write second
            save.write(""+second);
        }
    }
    public void loadConfig() throws FileNotFoundException, IOException {
        try (BufferedReader load = new BufferedReader(new FileReader("./src/main/java/Save/config.txt"))) {
            //Read minute
            String time = load.readLine() ;
            g.bestMinute = Integer.parseInt(time) ;
            
            //Read second
            time = load.readLine() ;
            g.bestSecond = Integer.parseInt(time) ;
        }
    }
}
