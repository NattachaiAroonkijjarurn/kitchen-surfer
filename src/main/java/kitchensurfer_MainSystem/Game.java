package kitchensurfer_MainSystem ;

//ALL IMPORT
import kitchensurfer_Entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;;
import kitchensurfer_Object.OBJ_Mouse;
import javax.swing.JPanel;
import kitchensurfer_Object.MainObject;
import kitchensurfer_Object.OBJ_Table;
import kitchensurfer_tiles.TileManager;

public class Game extends JPanel implements Runnable {
    
    //Screen Setting
    private final int originalTileSize = 32 ;
    private final int scale = 3 ;
    public final int tileSize = originalTileSize * scale ;
    public final int maxScreenCol = 7 ;
    public final int maxScreenRow = 8 ;
    public final int screenWidth = tileSize * maxScreenCol ;
    public final int screenHeight = tileSize * maxScreenRow ;
    
    //World Settings
    public final int maxWorldCol = 7 ;
    public final int maxWorldRow = 390 ;
    public final int maxWorldEndlessRow = 2000 ;
    public final int worldWidth = tileSize * maxWorldCol ;
    public final int worldHeight = tileSize * maxWorldRow ;
    
    //Constructor for set up the game
    public Game() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight)) ;
        //This method will be draw anything offscreen and copy it onto the screen
        //then this method will be use cache to draw when you need to draw it again
        this.setDoubleBuffered(true) ;
        //Add KeyListener for this game to listen the keyboard input from user
        this.addKeyListener(key) ; 
        //When you press any key that will effect to this game
        this.setFocusable(true) ;
    }
    
    //FPS
    public final int FPS = 60 ;
    
    //System
    //Tile
    public TileManager tile = new TileManager(this) ;
    //Keyboard input
    public KeyInput key = new KeyInput(this) ;
    //Any UI of this game
    public UI ui = new UI(this) ;
    //Music and Sound Effect
    public Sound music ;
    public Sound se ;
    //Collision Check
    public CollisionChecker cChecker = new CollisionChecker(this) ;
    //Create the thread to run the game
    private Thread gameThread ;
    
    //Player and Object
    public Player player = new Player(this,key) ;
    public ArrayList<MainObject> object = new ArrayList<MainObject>() ;
    //Count for spawn an entity
    private int spawnCounter = 0 ;
    public int changeNum = 1 ;
    public int changeCount = 0 ;
    
    //Time
    public int bestMinute, bestSecond ;
    //Save and Load
    public Config config = new Config(this) ;
    
    //Game State
    public int gameState ;
    public final int titleState = 0 ;
    public final int modeState = 1 ;
    public final int playState = 2 ;
    public final int pauseState = 3 ;
    public final int gameOverState = 4 ;
    public final int winState = 5 ;
    
    //Game Mode
    public int gameMode ;
    public final int normalMode = 0 ;
    public final int endlessMode = 1 ;
   
    
    //setup the game before gamestart
    public void setupGame() throws IOException, UnsupportedAudioFileException {
        music = new Sound() ;
        se = new Sound() ;
        gameState = titleState ;
        gameMode = normalMode ;
        try {
            playMusic(4) ;
        } catch (UnsupportedAudioFileException | LineUnavailableException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        config.loadConfig() ;
    }
    
    //Start Thred to run game
    public void startGameThread() {
        gameThread = new Thread(this) ;
        gameThread.start() ;
        player.EventThread.start() ;
        player.EventThread.suspend() ;
    }
    
    public void setGameMode(int choosedGameMode) {
        this.gameMode = choosedGameMode ;
        if(choosedGameMode == normalMode) {
            ui.second = 0 ;
            ui.minute = 1 ;
            ui.timeCount = 0 ;
        }
        if(choosedGameMode == endlessMode) {
            ui.second = 0 ;
            ui.minute = 0 ;
            ui.timeCount = 0 ;
        }
    }
    
    //Therd run all things will loop in here until game close
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS ;
        double nextDrawTime = System.nanoTime() + drawInterval ;
        while(gameThread != null) {
          
            try {
            //1 Update information such as character position
            //any thing that need to update player, ui, sound, any object
                update() ;
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            //2 Draw the screen with the update information
            repaint() ;
            
            //Sleep Time for next draw and next update
            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000 ;
                if(remainingTime < 0) {
                    remainingTime = 0 ;
                }
                Thread.sleep((long) remainingTime) ;
                nextDrawTime += drawInterval ;
            } catch (InterruptedException e) {
                e.printStackTrace() ;
            }
            
        }
    }
    
    //Update anything that happen in this game
    public void update() throws InterruptedException, IOException {
        //TITLE STATE
        if(gameState == titleState) {
            ui.update() ; 
        }
        //MODE STATE
        if(gameState == modeState) {
            ui.update() ; 
        }
        //PLAY STATE
        if(gameState == playState) {
            //CHOOSE GAME MODE
            //NORMAL MODE
            if(gameMode == normalMode) {
                player.update() ;
                ui.update() ;
                //When the position of player less than this condition game will be end
                if(player.worldY < 620) {
                    player.finished = true ;
                }
                //When the position of player greater than this condition
                //game will be random spawn object
                if(player.worldY > 2560) {
                    if(spawnCounter > 30) {
                        Random random = new Random() ;
                        MainObject newObject ;
                        int randomPosition = random.nextInt(3) ;
                        int randomObject = random.nextInt(2) ;
                        int objX = 0, objY ;
                        if(randomPosition == 0) {
                            objX = 108 ;
                        }
                        else if(randomPosition == 1) {
                            objX = 278 ;
                        }
                        else if(randomPosition == 2) {
                            objX = 458 ;
                        }
                        objY = player.worldY-tileSize*7 ;
                        if(randomObject == 0) {
                            newObject = new OBJ_Table(this, objX, objY) ;
                            object.add(newObject) ;
                        }
                        else if(randomObject == 1) {
                            newObject = new OBJ_Mouse(this, objX, objY) ;
                            object.add(newObject) ;
                        }
                        spawnCounter = 0 ;
                    }
                    spawnCounter++ ;
                }
                //remove the object that player has passed to save the memory
                for(int i = 0; i < object.size(); i++) {
                    if(object.get(i).worldY-768 > player.worldY) {
                        object.remove(i) ;
                    }
                }
            }
            //ENDLESS MODE
            if(gameMode == endlessMode) {
                player.update() ;
                ui.update() ;
                //Random spawn object
                if(spawnCounter > 30) {
                    Random random = new Random() ;
                    MainObject newObject ;
                    int randomPosition = random.nextInt(3) ;
                    int randomObject = random.nextInt(2) ;
                    int objX = 0, objY ;
                    if(randomPosition == 0) {
                        objX = 108 ;
                    }
                    else if(randomPosition == 1) {
                        objX = 278 ;
                    }
                    else if(randomPosition == 2) {
                        objX = 458 ;
                    }
                    objY = player.worldY-tileSize*7 ;
                    if(randomObject == 0) {
                        newObject = new OBJ_Table(this, objX, objY) ;
                        object.add(newObject) ;
                    }
                    else if(randomObject == 1) {
                        newObject = new OBJ_Mouse(this, objX, objY) ;
                        object.add(newObject) ;
                    }
                    spawnCounter = 0 ;
                }
                spawnCounter++ ;
                //remove the object that player has passed to save the memory
                for(int i = 0; i < object.size(); i++) {
                    if(object.get(i).worldY-768 > player.worldY) {
                        object.remove(i) ;
                    }
                }
            }
        }
        //PAUSE STATE
        if(gameState == pauseState) {
            ui.update() ;
        }
        //GAMEOVER STATE
        if(gameState == gameOverState) {
            //remove the object to prepare for the new game
            for(int i = 0; i < object.size(); i++) {
                object.remove(i) ;
            }
            ui.update() ;
        }
        //WIN STATE
        if(gameState == winState) {
            //remove the object to prepare for the new game
            for(int i = 0; i < object.size(); i++) {
                object.remove(i) ;
            }
            ui.update() ;
        }
        
        changeCount++ ;
        if(changeCount > 4) {
            if(changeNum == 1) {
                changeNum = 2 ;
            }
            else if(changeNum == 2) {
                changeNum = 1 ;
            }
            changeCount = 0 ;
        }
    }
    
    //Method for Drawing
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g) ;
        
        Graphics2D g2 = (Graphics2D)g ;
        
        //Title
        if(gameState == titleState) {
            try {
                ui.draw(g2) ;
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2.dispose() ;
        }
        
        //Mode
        if(gameState == modeState) {
            try {
                ui.draw(g2) ;
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2.dispose() ;
        }
        
        //Play
        if(gameState == playState) {
            tile.draw(g2) ;
            
            for(int i = 0; i < object.size(); i++) {
                object.get(i).draw(g2, this);
            }
            
            player.draw(g2) ;

            try {
                ui.draw(g2) ;
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2.dispose() ;
        }
        
        //Pause
        if(gameState == pauseState) {
            tile.draw(g2) ;
            
            for(int i = 0; i < object.size(); i++) {
                object.get(i).draw(g2, this);
            }
            
            player.draw(g2) ;

            try {
                ui.draw(g2) ;
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2.dispose() ;
        }
        
        //GameOver
        if(gameState == gameOverState) {
            try {
                ui.draw(g2) ;
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2.dispose() ;
        }
        
        //Win
        if(gameState == winState) {
            try {
                ui.draw(g2) ;
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            g2.dispose() ;
        }
    }
    
    //Method for Music and Sound Effect
    public void playMusic(int i) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        music = new Sound() ;
        try {
            music.setFile(i) ;
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        music.play() ;
        music.loop() ;
    }
    
    public void stopMusic() {
        music.stop() ;
    }
    
    public void playSE(int i) throws UnsupportedAudioFileException, IOException  {
        se = new Sound() ;
        try {
            se.setFile(i) ;
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        se.play() ;
    }
}