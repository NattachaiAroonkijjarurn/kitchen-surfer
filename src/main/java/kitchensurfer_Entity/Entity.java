package kitchensurfer_Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public abstract class Entity extends JPanel {
    public int worldX, worldY ;
    public int speed ;
    
    public String direction ;
    protected BufferedImage up,down,left,right ;
    protected String lane ;
    
    protected int spriteCounter = 0 ;
    protected int spriteNum = 1 ;
    
    public Rectangle solidAreaWall ;
    public Rectangle solidAreaObject ;
    public boolean collisionOn = false ;
    
    public abstract void update() ;
    public abstract void draw(Graphics2D g2) ;
} 
