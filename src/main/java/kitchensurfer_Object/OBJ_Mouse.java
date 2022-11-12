package kitchensurfer_Object;

import kitchensurfer_MainSystem.Game;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Mouse extends MainObject {
        
    public OBJ_Mouse(Game g, int worldX, int worldY) {
        super(g) ;
        this.worldX = worldX ;
        this.worldY = worldY ;
        name = "Mouse" ;
        collision = false ;
        solidAreaObject = new Rectangle() ;
        solidAreaObject.x = 5 ;
        solidAreaObject.y = 5 ;
        solidAreaObject.width = 84 ;
        solidAreaObject.height = 84 ;
        try {
            image = ImageIO.read(new File("./src/main/java/res/rat1.png")) ;
        }catch(IOException e) {
        }
        try {
            image2 = ImageIO.read(new File("./src/main/java/res/rat2.png")) ;
        }catch(IOException e) {
        }
    }
    
    @Override
    public void draw(Graphics2D g2, Game g) {
        int screenY = worldY - g.player.worldY ;
        if(worldX > g.player.worldX - g.player.screenX - 300 &&
                worldX < g.player.worldX + g.player.screenX + 300 &&
                worldY > g.player.worldY - g.player.screenY - 300 &&
                worldY < g.player.worldY + g.player.screenY + 300) {
                if(g.changeNum == 1) { 
                    g2.drawImage(image, worldX+30, screenY, g.tileSize-30, g.tileSize-30, null) ;
                }
                if(g.changeNum == 2) {
                    g2.drawImage(image2, worldX+30, screenY, g.tileSize-30, g.tileSize-30, null) ;
                }
            } 
    }
}
