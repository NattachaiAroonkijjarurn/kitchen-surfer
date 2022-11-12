package kitchensurfer_MainSystem ;

//ALL IMPORT
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;

public class UI {
    
    public int commandNum = 0 ;
    public int timeCount ;
    public int second,minute ;
    public int lastMinute, lastSecond ;
    public boolean showTime = false ;
    
    private Game g ;
    private Graphics2D g2 ;
    private Font mineFont ;
    //CHANGE BG
    private int bgCounter = 0 ;
    private int bgNum = 1 ;
    //CHANGE CURSOR
    private int cursorCounter = 0 ;
    private int cursorNum = 1 ;
    //PLAY TIME
    private Timer playTime ;
    private String dSecond, dMinute, dLastSecond, dLastMinute, dBestMinute, dBestSecond ;
    private DecimalFormat dformat = new DecimalFormat("00") ;
    
    
    public UI(Game g) {
        this.g = g ;
        second = 0 ;
        minute = 1 ;
        timeCount = 0 ;
        try {
        mineFont = Font.createFont(Font.TRUETYPE_FONT, new File("./src/main/java/Fonts/Minecraft.ttf")).deriveFont(80f);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        ge.registerFont(mineFont) ;
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
    
    public void startTime() {
        timeCount++ ;
        playTime = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(g.gameMode == g.normalMode) {
                    if(timeCount-66 == 60) {
                        second-- ;
                        timeCount = 0 ;
                    }
                    if(timeCount == 60) {
                        second-- ;
                        timeCount = 0 ;
                    }
                    if(second < 0) {
                        minute-- ;
                        second = 59 ;
                    }
                }
                else if(g.gameMode == g.endlessMode) {
                    try {
                        if(g.cChecker.checkObject(g.player, g.object) != 1) {
                            if(timeCount-65 == 60) {
                                second++ ;
                                timeCount = 0 ;
                            }
                            if(timeCount == 60) {
                                second++ ;
                                timeCount = 0 ;
                            }
                            if(second > 59) {
                                minute++ ;
                                second = 0 ;
                                timeCount = 0 ;
                            }
                            if(minute >= g.bestMinute && second >= g.bestSecond || minute > g.bestMinute) {
                                try {
                                    g.config.saveConfig(minute, second);
                                    g.config.loadConfig();
                                } catch (IOException ex) {
                                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        else {
                            playTime.stop() ;  
                        }
                    } catch (IOException ex) {     
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    public void draw(Graphics2D g2) throws IOException {
        this.g2 = g2 ;
        g2.setFont(mineFont) ;
        g2.setColor(Color.white);
        
        //TITLE STATE
        if(g.gameState == g.titleState) {
            drawTitleScreen() ;
        }
        if(g.gameState == g.modeState) {
            drawModeScreen() ;
        }
        //PLAY STATE
        if(g.gameState == g.playState) {
            startTime() ;
            playTime.start() ;
            drawPlayScreen() ;
        }
        //PAUSE STATE
        if(g.gameState == g.pauseState) {
            drawPauseScreen() ;
        }
        //GAMEOVER STATE
        if(g.gameState == g.gameOverState) {
            if(g.gameMode == g.normalMode) {
                drawGameOverScreen() ;
            }
            if(g.gameMode == g.endlessMode) {
                drawGameOverEndlessScreen() ;
            }
            
        }
        //WIN STATE
        if(g.gameState == g.winState) {
            drawWinScreen() ;
        }
    }
    
    public void update() {
        //COUNT FOR BG TO CHANGE
        bgCounter++ ;
        if(bgCounter > 20) {
            if(bgNum == 1) {
                bgNum = 2 ;
            }
            else if(bgNum == 2) {
                bgNum = 1 ;
            }
            bgCounter = 0 ;
        }
        //COUNT FOR CURSOR TO CHANGE
        cursorCounter++ ;
        if(cursorCounter > 35) {
            if(cursorNum == 1) {
                cursorNum = 2 ;
            }
            else if(cursorNum == 2) {
                cursorNum = 1 ;
            }
            cursorCounter = 0 ;
        }
    }
    
    //TITLE STATE
    public void drawTitleScreen() throws IOException {
        String text, shadow ;
        int x ;
        int y = 0 ;
        BufferedImage logo = ImageIO.read(new File("./src/main/java/res/logo.png")) ;
        BufferedImage bgTitle = ImageIO.read(new File("./src/main/java/res/bgTitle.png")) ;
        BufferedImage bgTitle1 = ImageIO.read(new File("./src/main/java/res/bgTitle1.png")) ;
        
        if(bgNum == 1) {
            g2.drawImage(bgTitle, 0, 0, 672, 768, null) ;
        }
        if(bgNum == 2) {
            g2.drawImage(bgTitle1, 0, 0, 672, 768, null) ;
        }
        
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
        
        text = "PLAY" ;
        shadow = "PLAY" ;
        x = getXforCenteredText(text) ;
        y += g.tileSize*5+10 ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y-10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 0) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 86F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {
                
            }   
        }
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
        text = "QUIT" ;
        shadow = "QUIT" ;
        x = getXforCenteredText(text) ;
        y = 490 + g.tileSize ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y - 10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 1) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 86F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {
                
            }   
        }
        
        g2.drawImage(logo, g.screenWidth/2-200, g.screenHeight/2-340, 420, 235, null) ;
    }
    
    //MODE STATE
    public void drawModeScreen() throws IOException {
        String text, shadow ;
        int x ;
        int y = 0 ;
        BufferedImage logo = ImageIO.read(new File("./src/main/java/res/logo.png")) ;
        BufferedImage bgTitle = ImageIO.read(new File("./src/main/java/res/bgTitle.png")) ;
        BufferedImage bgTitle1 = ImageIO.read(new File("./src/main/java/res/bgTitle1.png")) ;
        
        if(bgNum == 1) {
            g2.drawImage(bgTitle, 0, 0, 672, 768, null) ;
        }
        if(bgNum == 2) {
            g2.drawImage(bgTitle1, 0, 0, 672, 768, null) ;
        }
        
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
        
        text = "NORMAL" ;
        shadow = "NORMAL" ;
        x = getXforCenteredText(text) ;
        y += g.tileSize*5+10 ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y-10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 0) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 86F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {
                
            }   
        }
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
        text = "ENDLESS" ;
        shadow = "ENDLESS" ;
        x = getXforCenteredText(text) ;
        y = 490 + g.tileSize ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y - 10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 1) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 86F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {
                
            }   
        }
        
        g2.drawImage(logo, g.screenWidth/2-200, g.screenHeight/2-340, 420, 235, null) ;
    }
    
    //PLAY STATE
    public void drawPlayScreen() {
        if(g.gameMode == g.normalMode) {
            if(minute == 0 && second == 0) {
            showTime = false ;
        }
            if(showTime == true) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)) ;
                dMinute = dformat.format(minute) ;
                dSecond = dformat.format(second) ;
                String showTimeText = dMinute+":"+dSecond ;
                int x = 480 ;
                int y = 96 ;
                g2.drawString(showTimeText, x, y);
            }
        }
        if(g.gameMode == g.endlessMode) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)) ;
                dMinute = dformat.format(minute) ;
                dSecond = dformat.format(second) ;
                String showTimeText = dMinute+":"+dSecond ;
                int x = 480 ;
                int y = 96 ;
                g2.drawString(showTimeText, x, y);
        }
    }
    
    //PAUSE STATE
    public void drawPauseScreen() throws IOException {
        String text, shadow ;
        int x ;
        int y = 0 ;
        BufferedImage logo = ImageIO.read(new File("./src/main/java/res/logo.png")) ;
        BufferedImage bgTitle = ImageIO.read(new File("./src/main/java/res/bgTitle.png")) ;
        BufferedImage bgTitle1 = ImageIO.read(new File("./src/main/java/res/bgTitle1.png")) ;

        if(bgNum == 1) {
            g2.drawImage(bgTitle, 0, 0, 672, 768, null) ;
        }
        if(bgNum == 2) {
            g2.drawImage(bgTitle1, 0, 0, 672, 768, null) ;
        }

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 86F)) ;
        
        text = "PAUSED" ;
        shadow = "PAUSED" ;
        x = getXforCenteredText(text) ;
        y += g.tileSize+40 ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y-10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)) ;
        text = "RESUME" ;
        shadow = "RESUME" ;
        x = getXforCenteredText(text) ;
        y += g.tileSize*5-10 ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y-10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 0) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {

            }   
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)) ;
        text = "BACK TO TITLE" ;
        shadow = "BACK TO TITLE" ;
        x = getXforCenteredText(text) ;
        y = 576 + g.tileSize ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y - 10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 1) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {

            }   
        }
    }
    
    //GAMEOVER STATE : NORMAL MODE
    public void drawGameOverScreen() throws IOException {
        String text, shadow ;
        int x ;
        int y = 0 ;
        BufferedImage bgGOV1 = ImageIO.read(new File("./src/main/java/res/gameOver1.png")) ;
        BufferedImage bgGOV2 = ImageIO.read(new File("./src/main/java/res/gameOver2.png")) ;
        
        if(bgNum == 1) {
            g2.drawImage(bgGOV1, 0, 0, 672, 768, null) ;
        }
        if(bgNum == 2) {
            g2.drawImage(bgGOV2, 0, 0, 672, 768, null) ;
        }
        
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 86F)) ;
        
        text = "GAME OVER" ;
        shadow = "GAME OVER" ;
        x = getXforCenteredText(text) ;
        y += g.tileSize+40 ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y-10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)) ;
        text = "CONTINUE" ;
        shadow = "CONTINUE" ;
        x = getXforCenteredText(text) ;
        y += g.tileSize*5-10 ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y-10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 0) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {

            }   
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)) ;
        text = "BACK TO TITLE" ;
        shadow = "BACK TO TITLE" ;
        x = getXforCenteredText(text) ;
        y = 576 + g.tileSize ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y - 10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 1) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {

            }   
        }
    }
    
    //GAMEOVER STATE : ENDLESS MODE
    public void drawGameOverEndlessScreen() throws IOException {
        String text, shadow ;
        int x ;
        int y = 0 ;
        BufferedImage bgEndless = ImageIO.read(new File("./src/main/java/res/bgEndless.png")) ;
        
        g2.drawImage(bgEndless, 0, 0, 672, 768, null) ;
        
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 86F)) ;
        
        text = "YOUR TIME" ;
        shadow = "YOUR TIME" ;
        x = getXforCenteredText(text) ;
        y += g.tileSize+40 ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y-10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        
        //Your Time
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 76F)) ;
        dLastMinute = dformat.format(lastMinute) ;
        dLastSecond = dformat.format(lastSecond) ;
        shadow = dLastMinute+" : "+dLastSecond ;
        x = getXforCenteredText(shadow) ;
        y += g.tileSize+70 ;
        if(bgNum == 1) {
            g2.setColor(Color.BLACK);
        }
        if(bgNum == 2) {
            g2.setColor(Color.decode("#ee3d73"));
        }
        g2.drawString(shadow, x, y) ;
        
        //Your Best Time
        g2.setColor(Color.BLACK);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F)) ;
        dBestMinute = dformat.format(g.bestMinute) ;
        dBestSecond = dformat.format(g.bestSecond) ;
        shadow = "Your Best Time : "+dBestMinute+" : "+dBestSecond ;
        x = getXforCenteredText(shadow) ;
        y += g.tileSize-40 ;
        g2.drawString(shadow, x, y) ;

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)) ;
        text = "CONTINUE" ;
        shadow = "CONTINUE" ;
        x = getXforCenteredText(text) ;
        y += g.tileSize*3-20 ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y-10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 0) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {

            }   
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)) ;
        text = "BACK TO TITLE" ;
        shadow = "BACK TO TITLE" ;
        x = getXforCenteredText(text) ;
        y = 596 + g.tileSize ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y - 10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 1) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {

            }   
        }
    }
    
    //WIN STATE
    public void drawWinScreen() throws IOException {
        String text, shadow ;
        int x ;
        int y = 0 ;
 
        BufferedImage bgWin1 = ImageIO.read(new File("./src/main/java/res/win1.png")) ;
        BufferedImage bgWin2 = ImageIO.read(new File("./src/main/java/res/win2.png")) ;
        
        if(bgNum == 1) {
            g2.drawImage(bgWin1, 0, 0, 672, 768, null) ;
        }
        if(bgNum == 2) {
            g2.drawImage(bgWin2, 0, 0, 672, 768, null) ;
        }
        
        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 56F)) ;
        
        text = "CONGRATUATIONS" ;
        shadow = "CONGRATUATIONS" ;
        x = getXforCenteredText(text) ;
        y += g.tileSize+10 ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y-10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)) ;
        text = "BACK TO TITLE" ;
        shadow = "BACK TO TITLE" ;
        x = getXforCenteredText(text) ;
        y += g.tileSize*6-40 ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y-10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 0) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {

            }   
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)) ;
        text = "QUIT" ;
        shadow = "QUIT" ;
        x = getXforCenteredText(text) ;
        y = 610 + g.tileSize ;
        g2.setColor(Color.BLACK);
        g2.drawString(shadow, x, y) ;
        y = y - 10 ;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y) ;
        if(commandNum == 1) {
            if(cursorNum == 1) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64F)) ;
                text = ">" ;
                shadow = ">" ;
                x = x-50 ;
                y = y + 20 ;
                g2.setColor(Color.BLACK);
                g2.drawString(shadow, x, y) ;
                y = y - 10 ;
                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y) ;
            }
            else {

            }   
        }
    }
    
    //METHOD TO FIND THE MIDDLE X OF SCREEN
    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() ;
        int x = g.screenWidth/2 - length/2 ;
        return x ;
    }
}