package kitchensurfer_Entity;

import kitchensurfer_MainSystem.Game;
import kitchensurfer_MainSystem.KeyInput;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;

interface playerAction {
    void bangWhenLose() ;
    void drawPlayerLose() ;
    void drawPlayerWin() ;
    void drawPlayerHappy() ;
}

public class Player extends Entity implements playerAction {
    
    public int screenX ;
    public int screenY ;
    public boolean finished ;
    
    private Game g ;
    private Graphics2D g2 ;
    private KeyInput key ;
    private BufferedImage stand, win, lose, bang1, bang2 ;
    private int bangCounter = 0 ;
    private int bangNum = 1 ;
    private int bangChefCounter = 0 ;
    private int happyNum = 1 ;
    private int happyCounter = 0 ;
    private int hitCount = 0 ;

    public Player(Game g, KeyInput key) {
        this.g = g ;
        this.key = key ;
        
        screenX = 258 ;
        screenY = 576 ;
        
        //Collision wall ;
        solidAreaWall = new Rectangle() ;
        solidAreaWall.x = 0 ;
        solidAreaWall.y = 0 ;
        solidAreaWall.width = 146 ;
        solidAreaWall.height = 146 ;
        //Collision object
        solidAreaObject = new Rectangle() ;
        solidAreaObject.x = 5 ;
        solidAreaObject.y = 5 ;
        solidAreaObject.width = 84 ;
        solidAreaObject.height = 84 ;
        
        setDefaultValues() ;
        getPlayerImage() ;
    }

    public void setDefaultValues() {
        worldX = 258 ;
        worldY = 37240 ;
        //worldY for test
        //worldY = 3840 ;
        speed = 10 ;
        direction = "up" ;
        lane = "mid" ;
        collisionOn = false ;
        finished = false ;
        g.setGameMode(g.gameMode) ;
        g.ui.showTime = true ;
        hitCount = 0 ;
    }
    public void setDefaultEndlessValues() {
        worldX = 258 ;
        worldY = 191700 ;
        speed = 10 ;
        direction = "up" ;
        lane = "mid" ;
        collisionOn = false ;
        finished = false ;
        g.setGameMode(g.gameMode) ;
        hitCount = 0 ;
    }
    
    public void getPlayerImage() {
        try {
            up = ImageIO.read(new File("./src/main/java/res/play.png")) ;
            down = ImageIO.read(new File("./src/main/java/res/player2.png")) ;
            left = ImageIO.read(new File("./src/main/java/res/play.png")) ;
            right = ImageIO.read(new File("./src/main/java/res/player2.png")) ;
            bang1 = ImageIO.read(new File("./src/main/java/res/bang1.png")) ;
            bang2 = ImageIO.read(new File("./src/main/java/res/bang2.png")) ;
        }catch(IOException e) {
            e.printStackTrace() ;
        }
    }
    
    public Thread EventThread = new Thread(new Runnable(){
            @Override
            public void run() {
		while(true){
                    double drawInterval = 1000000000/g.FPS ;
                    double nextDrawTime = System.nanoTime() + drawInterval ;
                    if(g.gameState == g.playState) {
                        updateEvent() ;
                    }    
                    try {
                    double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000 ;
                    if(remainingTime < 0) {
                        remainingTime = 0 ;   
                    }
                    Thread.sleep((long) remainingTime) ;
                    } catch (InterruptedException e) {
                        e.printStackTrace() ;
                    }
		}
            }
    });
    
    @Override
    public void update() {
        
        //Check Tile Collision
        //if Collision is FALSE, Player can move
        collisionOn = false ;
        g.cChecker.checkTile(this) ;
        
        int hitIndex = 0 ;
        try {
            hitIndex = g.cChecker.checkObject(this, g.object);
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        hitObject(hitIndex) ;

        try {
            if(g.cChecker.checkObject(this, g.object) == 999) {
                worldY -= speed ;
            }
            else {
                speed = 0 ;
                g.ui.lastMinute = g.ui.minute ;
                g.ui.lastSecond = g.ui.second ;
            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        if(key.leftPressed == true) {
            direction = "left" ;
            if(collisionOn == false) {
                worldX -= speed ;
            }
        }
        else if(key.rightPressed == true) {
            direction = "right" ;
            if(collisionOn == false) {
                worldX += speed ;
            }
        }
        
        spriteCounter++ ;
        if(spriteCounter > 8) {
            if(spriteNum == 1) {
                spriteNum = 2 ;
            }
            else if(spriteNum == 2) {
                spriteNum = 1 ;
            }
            spriteCounter = 0 ;
        }
    }
    
    public void updateEvent() {
        bangCounter++ ;
        if(bangCounter > 8) {
            if(bangNum == 1) {
                bangNum = 2 ;
            }
            else if(bangNum == 2) {
                bangNum = 1 ;
            }
            bangCounter = 0 ;
        }
        
        happyCounter++ ;
        if(happyCounter > 4) {
            if(happyNum == 1) {
                happyNum = 2 ;
            }
            else if(happyNum == 2) {
                happyNum = 1 ;
            }
            happyCounter = 0 ;
        }
    }
    
    @Override
    public void draw(Graphics2D g2) {
        this.g2 = g2 ;
        BufferedImage image = null ;
        
        if(spriteNum == 1) {
            image = up ;
        }
        if(spriteNum == 2) {
            image = down ;
        }
        if(g.gameMode == g.normalMode) {
            try {
                if(finished == true) {
                    drawPlayerWin() ;
                    EventThread.resume() ;
                    speed = 0 ;
                }
                else if(g.cChecker.checkObject(this, g.object) == 1) {   
                    EventThread.resume() ;
                    drawPlayerLose() ;
                }
                else {
                    if(worldY > 1056)
                        g2.drawImage(image, worldX, screenY, g.tileSize+50, g.tileSize+50, null) ;
                    else {
                        if(worldX == 258) {
                            g2.drawImage(image, worldX, worldY-480, g.tileSize+50, g.tileSize+50, null) ;
                        }

                        else if(worldX < 258) {
                            worldX += 5 ;
                            g2.drawImage(image, worldX, worldY-480, g.tileSize+50, g.tileSize+50, null) ;
                        }

                        else if(worldX > 258){
                            worldX -= 5 ;
                            g2.drawImage(image, worldX, worldY-480, g.tileSize+50, g.tileSize+50, null) ;
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(g.gameMode == g.endlessMode) {
            try {
                if(g.cChecker.checkObject(this, g.object) == 1) {
                    EventThread.resume() ;
                    drawPlayerLose() ;
                }
                else {
                   g2.drawImage(image, worldX, screenY, g.tileSize+50, g.tileSize+50, null) ; 
                }
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
    }
    
    @Override
    public void drawPlayerLose() {
        bangChefCounter++ ;
        try {
            lose = ImageIO.read(new File("./src/main/java/res/playerFall.png")) ;
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        g2.drawImage(lose, worldX, screenY, g.tileSize+50, g.tileSize+50, null) ;
        if(bangChefCounter < 60) {
            bangWhenLose() ;
        }
        else {
            if(bangChefCounter == 200) {
                EventThread.suspend() ;
                g.gameState = g.gameOverState ;
                bangChefCounter = 0 ;
                try {
                    g.playSE(2);
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void bangWhenLose() {
        BufferedImage bang = null ;
        if(bangNum == 1) {
            bang = bang1 ;
        }
        if(bangNum == 2) {
            bang = bang2 ;
        }
        g2.drawImage(bang, worldX-50, screenY-150, g.tileSize+200, g.tileSize+200, null) ;
    }
    
    @Override
    public void drawPlayerWin() {
        bangChefCounter++ ;
        if(bangChefCounter < 120) {
            try {
                stand = ImageIO.read(new File("./src/main/java/res/playerStand.png")) ;
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(bangChefCounter < 60) {
                g2.drawImage(stand, worldX, 140, g.tileSize+50, g.tileSize+50, null) ;
            }
            else {
                if(happyNum == 1) {
                    worldX -= 1 ;
                }
                if(happyNum == 2) {
                    worldX += 1 ;
                }
                g2.drawImage(stand, worldX, 140, g.tileSize+50, g.tileSize+50, null) ;
            }
        }
        else {
            if(bangChefCounter > 120) {
                drawPlayerHappy() ;
            }
            if(bangChefCounter == 150) {
                try {
                    g.playSE(0);
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(bangChefCounter == 220) {
                try {    
                    g.playSE(6);
                    EventThread.suspend() ;
                    g.gameState = g.winState ;
                    bangChefCounter = 0 ;
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    @Override
    public void drawPlayerHappy() {
        try {
            win = ImageIO.read(new File("./src/main/java/res/playerHappy.png")) ;
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        g2.drawImage(win, worldX, 140, g.tileSize+50, g.tileSize+50, null) ;
    }
    
    public void hitObject(int i) {
        if(i == 2) {
            hitCount++ ;
            try {
                if(hitCount == 1) {
                    g.playSE(7) ;
                }
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                }
            hitCount = 0 ;
        }
        else if(i != 999) {
            hitCount++ ;
            String objectName = g.object.get(i).name ;
            switch(objectName) {
                case "Table" : {
                    try {
                        if(hitCount == 1) {
                            g.playSE(1) ;
                        }
                    } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break ;
                }
            }
        }
    }
}