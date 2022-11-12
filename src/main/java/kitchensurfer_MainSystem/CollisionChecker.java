package kitchensurfer_MainSystem ;

//ALL IMPORT
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import kitchensurfer_Entity.Entity;
import kitchensurfer_Object.MainObject;

public class CollisionChecker {
    
    private Game g ;

    //Constructor
    public CollisionChecker(Game g) {
        this.g = g ;
    }
    
    //Method to check the tile that player can't walk
    public void checkTile(Entity entity) {
        
        int entityLeftWorldX = entity.worldX + entity.solidAreaWall.x ;
        int entityRightWorldX = entity.worldX + entity.solidAreaWall.x + entity.solidAreaWall.width ;
        int entityTopWorldY = entity.worldY + entity.solidAreaWall.y ;
        int entityBottomWorldY = entity.worldY + entity.solidAreaWall.y + entity.solidAreaWall.height ;
    
        int entityLeftCol = entityLeftWorldX / g.tileSize ;
        int entityRightCol = entityRightWorldX / g.tileSize ;
        int entityTopRow = entityTopWorldY / g.tileSize ;
        int entityBottomRow = entityBottomWorldY / g.tileSize ;
        
        int tileNum1, tileNum2 ;
        
        switch(entity.direction) {
            case "up" :
                entityTopRow = (entityTopWorldY - entity.speed) / g.tileSize ;
                tileNum1 = g.tile.mapTileNum[entityTopRow][entityLeftCol] ;
                tileNum2 = g.tile.mapTileNum[entityBottomRow][entityLeftCol] ;
                if(g.tile.tile[tileNum1].collision == true || g.tile.tile[tileNum2].collision == true) {
                    entity.collisionOn = true ;
                }
                break ;
            case "left" :
                entityLeftCol = (entityLeftWorldX - entity.speed) / g.tileSize ;
                tileNum1 = g.tile.mapTileNum[entityTopRow][entityLeftCol] ;
                tileNum2 = g.tile.mapTileNum[entityBottomRow][entityLeftCol] ;
                if(g.tile.tile[tileNum1].collision == true || g.tile.tile[tileNum2].collision == true) {
                    entity.collisionOn = true ;
                }
                break ;
            case "right" :
                entityRightCol = (entityRightWorldX + entity.speed) / g.tileSize ;
                tileNum1 = g.tile.mapTileNum[entityTopRow][entityRightCol] ;
                tileNum2 = g.tile.mapTileNum[entityBottomRow][entityRightCol] ;
                if(g.tile.tile[tileNum1].collision == true || g.tile.tile[tileNum2].collision == true) {
                    entity.collisionOn = true ;
                }
                break ;
        }
    }
    
    //Method to check if player hit the object
    public int checkObject(Entity entity, ArrayList<MainObject> object) throws IOException {
        
        int index = 999 ;
        ArrayList<MainObject> cObject = object ;
        for(int i = 0; i < object.size(); i++) {
            if(object.get(i) != null) {
                entity.solidAreaObject.x = entity.worldX + entity.solidAreaObject.x ;
                entity.solidAreaObject.y = entity.worldY+600 + entity.solidAreaObject.y ;

                cObject.get(i).solidAreaObject.x = object.get(i).worldX + object.get(i).solidAreaObject.x ;
                cObject.get(i).solidAreaObject.y = object.get(i).worldY + object.get(i).solidAreaObject.y ;
       
                if(entity.solidAreaObject.intersects(cObject.get(i).solidAreaObject)) {
                    if(object.get(i).name.equals("Table")) {
                        entity.collisionOn = true ;
                        index = 1 ;
                        object.get(i).image = ImageIO.read(new File("./src/main/java/res/tableFall.png")) ;
                    }
                    if(object.get(i).name.equals("Mouse")) {
                        index = 2 ;
                        g.player.hitObject(index) ;
                        if(object.get(i).worldX+30 == 138) {
                            entity.worldX = 258 ;
                        }
                        if(object.get(i).worldX+30 == 308) {
                            entity.worldX = 418 ;
                        }
                        if(object.get(i).worldX+30 == 488) {
                            entity.worldX = 258 ;
                        }
                        object.remove(i) ;
                    }
                }
                entity.solidAreaObject.x = 10 ;
                entity.solidAreaObject.y = 10 ;

                cObject.get(i).solidAreaObject.x = 0 ;
                cObject.get(i).solidAreaObject.y = 0 ;
            }
            else {
                
            }
        }
        return index ;
    }
}
