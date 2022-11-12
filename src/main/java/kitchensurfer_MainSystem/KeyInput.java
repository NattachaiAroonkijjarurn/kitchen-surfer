package kitchensurfer_MainSystem ;

//ALL IMPORT
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class KeyInput implements KeyListener {
    private Game g ;
    public boolean leftPressed, rightPressed ;

    public KeyInput() {
    }
    
    public KeyInput(Game g) {
        this.g = g ;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode() ;
        //TITLE STATE
        if(g.gameState == g.titleState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                try {
                    g.playSE(3) ;
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                }
                g.ui.commandNum-- ;
                if(g.ui.commandNum < 0) {
                    g.ui.commandNum = 1 ;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                try {
                    g.playSE(3) ;
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                }
                g.ui.commandNum++ ;
                if(g.ui.commandNum > 1) {
                    g.ui.commandNum = 0 ;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                try {
                    g.playSE(3) ;
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(g.ui.commandNum == 0) {
                    g.gameState = g.modeState ;
                }
                if(g.ui.commandNum == 1) {
                    System.exit(0) ;
                }
            }
        }
        //MODE STATE
        else if(g.gameState == g.modeState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                try {
                    g.playSE(3) ;
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                }
                g.ui.commandNum-- ;
                if(g.ui.commandNum < 0) {
                    g.ui.commandNum = 1 ;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                try {
                    g.playSE(3) ;
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                }
                g.ui.commandNum++ ;
                if(g.ui.commandNum > 1) {
                    g.ui.commandNum = 0 ;
                }
            }
            if(code == KeyEvent.VK_ENTER) {
                try {
                    g.playSE(3) ;
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(g.ui.commandNum == 0) {
                    g.player.setDefaultValues() ;
                    g.gameMode = g.normalMode ;
                    g.setGameMode(g.normalMode) ;
                    g.tile.setModeTile(g.normalMode) ;
                    g.tile.setMap(g.normalMode) ;
                    g.gameState = g.playState ;
                    g.stopMusic() ; 
                    try {
                        g.playMusic(5) ;
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(g.ui.commandNum == 1) {
                    g.player.setDefaultEndlessValues() ;
                    g.gameMode = g.endlessMode ;
                    g.setGameMode(g.endlessMode) ;
                    g.tile.setModeTile(g.endlessMode) ;
                    g.tile.setMap(g.endlessMode) ;
                    g.gameState = g.playState ;
                    g.stopMusic() ;
                    try {
                        g.playMusic(5) ;
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    g.ui.commandNum = 0 ;
                }
            }
        }
        else {
            if(code == KeyEvent.VK_ESCAPE) {
                try {
                    g.playSE(3) ;
                } catch (UnsupportedAudioFileException | IOException ex) {
                    Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(g.gameState == g.playState) {
                    g.gameState = g.pauseState ;
                }
                else if(g.gameState == g.pauseState) {
                    g.gameState = g.playState ;
                }
            }
            //PLAY STATE
            if(g.gameState == g.playState) {
                if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                    leftPressed = true ;
                }
                else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                    rightPressed = true ;
                }
            }

            //PAUSE STATE
            if(g.gameState == g.pauseState) {
                if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    try {
                        g.playSE(3) ;
                    } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    g.ui.commandNum-- ;
                    if(g.ui.commandNum < 0) {
                        g.ui.commandNum = 1 ;
                    }
                }
                if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    g.ui.commandNum++ ;
                    try {
                        g.playSE(3) ;
                    } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(g.ui.commandNum > 1) {
                        g.ui.commandNum = 0 ;
                    }
                }
                if(code == KeyEvent.VK_ENTER) {
                    try {
                        g.playSE(3) ;
                    } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(g.ui.commandNum == 0) {
                        g.gameState = g.playState ;
                    }
                    if(g.ui.commandNum == 1) {
                        g.gameState = g.titleState ;
                        g.stopMusic() ;
                        try {
                            g.playMusic(4) ;
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        g.ui.commandNum = 0 ;
                    }
                }
            }
            
            //GAMEOVER STATE
            if(g.gameState == g.gameOverState) {
                if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    try {
                        g.playSE(3) ;
                    } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    g.ui.commandNum-- ;
                    if(g.ui.commandNum < 0) {
                        g.ui.commandNum = 1 ;
                    }
                }
                if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    try {
                        g.playSE(3) ;
                    } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    g.ui.commandNum++ ;
                    if(g.ui.commandNum > 1) {
                        g.ui.commandNum = 0 ;
                    }
                }
                if(code == KeyEvent.VK_ENTER) {
                        try {
                        g.playSE(3) ;
                    } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(g.ui.commandNum == 0) {
                        if(g.gameMode == g.normalMode) {
                            g.player.setDefaultValues() ;
                        }
                        if(g.gameMode == g.endlessMode) {
                            g.player.setDefaultValues() ;
                            g.setGameMode(g.endlessMode) ;
                            g.player.worldY = 191700 ;
                        }
                        g.gameState = g.playState ;
                    }
                    if(g.ui.commandNum == 1) {
                        g.gameState = g.titleState ;
                        g.stopMusic() ;
                        try {
                            g.playMusic(4) ;
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        g.ui.commandNum = 0 ;
                        
                    }
                }
            }
            
            //WIN STATE
            if(g.gameState == g.winState) {
                if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    try {
                        g.playSE(3) ;
                    } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    g.ui.commandNum-- ;
                    if(g.ui.commandNum < 0) {
                        g.ui.commandNum = 1 ;
                    }
                }
                if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    try {
                        g.playSE(3) ;
                    } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    g.ui.commandNum++ ;
                    if(g.ui.commandNum > 1) {
                        g.ui.commandNum = 0 ;
                    }
                }
                if(code == KeyEvent.VK_ENTER) {
                    try {
                        g.playSE(3) ;
                    } catch (UnsupportedAudioFileException | IOException ex) {
                        Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(g.ui.commandNum == 0) {
                        g.gameState = g.titleState ;
                        g.stopMusic() ;
                        try {
                            g.playMusic(4) ;
                        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                            Logger.getLogger(KeyInput.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if(g.ui.commandNum == 1) {
                        System.exit(0) ;
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode() ;

        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false ;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = false ;
        }
    }
    
}
