package kitchensurfer_Object;

import kitchensurfer_MainSystem.Game;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class MainObject {
    public BufferedImage image, image2 ;
    public String name ;
    public int worldX, worldY ;
    public Rectangle solidAreaObject ;
    
    private Game g ;
    
    protected boolean collision = false ;
    protected int spawnCounter = 0 ;
    
    public MainObject(Game g) {
        this.g = g ;
    }
    
    public abstract void draw(Graphics2D g2, Game g) ;
}
