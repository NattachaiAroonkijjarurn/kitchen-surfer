package kitchensurfer_tiles;

import kitchensurfer_MainSystem.Game;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TileManager {
    public Tile[] tile ;
    public int mapTileNum[][] ;
    
    private Game g ;
   
    public TileManager(Game g) {
        this.g = g ;
        
        tile = new Tile[30] ;
        
        getTileImage() ; 
    }
    
    public void setModeTile(int mode) {
        if(g.gameMode == g.normalMode) {
            mapTileNum = new int[g.maxWorldRow][g.maxWorldCol] ;
        }
        if(g.gameMode == g.endlessMode) {
            mapTileNum = new int[g.maxWorldEndlessRow][g.maxWorldCol] ;
        }
    }
    
    public void getTileImage() {
        try {
          
            tile[0] = new Tile() ;
            tile[0].image = ImageIO.read(new File("./src/main/java/tile/woodTile.png")) ;
            
            tile[1] = new Tile() ;
            tile[1].image = ImageIO.read(new File("./src/main/java/tile/woodWallTile.png")) ;
            tile[1].collision = true ;
            
            tile[2] = new Tile() ;
            tile[2].image = ImageIO.read(new File("./src/main/java/tile/woodWallTile2.png")) ;
            tile[2].collision = true ;
            
            //Brick Top
            //BrickTopWallEnd
            tile[3] = new Tile() ;
            tile[3].image = ImageIO.read(new File("./src/main/java/tile/BrickTopWallEnd1.png")) ;
            
            tile[4] = new Tile() ;
            tile[4].image = ImageIO.read(new File("./src/main/java/tile/BrickTopWallEnd2.png")) ;
            
            tile[5] = new Tile() ;
            tile[5].image = ImageIO.read(new File("./src/main/java/tile/BrickTopWallEnd3.png")) ;
            
            tile[6] = new Tile() ;
            tile[6].image = ImageIO.read(new File("./src/main/java/tile/BrickTopWallEnd4.png")) ;
            
            tile[7] = new Tile() ;
            tile[7].image = ImageIO.read(new File("./src/main/java/tile/BrickTopWallEnd5.png")) ;
            
            //Brick Mid
            //BrickMidWallEnd
            tile[8] = new Tile() ;
            tile[8].image = ImageIO.read(new File("./src/main/java/tile/BrickMidWallEnd1.png")) ;
            
            tile[9] = new Tile() ;
            tile[9].image = ImageIO.read(new File("./src/main/java/tile/BrickMidWallEnd2.png")) ;
            
            tile[10] = new Tile() ;
            tile[10].image = ImageIO.read(new File("./src/main/java/tile/BrickMidWallEnd3.png")) ;
            
            tile[11] = new Tile() ;
            tile[11].image = ImageIO.read(new File("./src/main/java/tile/BrickMidWallEnd4.png")) ;
            
            tile[12] = new Tile() ;
            tile[12].image = ImageIO.read(new File("./src/main/java/tile/BrickMidWallEnd5.png")) ;
            
            //Brick Bottom
            //BrickBottomWallEnd
            tile[13] = new Tile() ;
            tile[13].image = ImageIO.read(new File("./src/main/java/tile/BrickBottomWallEnd1.png")) ;
            
            tile[14] = new Tile() ;
            tile[14].image = ImageIO.read(new File("./src/main/java/tile/BrickBottomWallEnd2.png")) ;
            
            tile[15] = new Tile() ;
            tile[15].image = ImageIO.read(new File("./src/main/java/tile/BrickBottomWallEnd3.png")) ;
            
            tile[16] = new Tile() ;
            tile[16].image = ImageIO.read(new File("./src/main/java/tile/BrickBottomWallEnd4.png")) ;
            
            tile[17] = new Tile() ;
            tile[17].image = ImageIO.read(new File("./src/main/java/tile/BrickBottomWallEnd5.png")) ;
            
            //Wood Tile before Door
            //ShadowPath
            tile[18] = new Tile() ;
            tile[18].image = ImageIO.read(new File("./src/main/java/tile/ShadowPath1.png")) ;
            
            tile[19] = new Tile() ;
            tile[19].image = ImageIO.read(new File("./src/main/java/tile/ShadowPath2.png")) ;
            
            tile[20] = new Tile() ;
            tile[20].image = ImageIO.read(new File("./src/main/java/tile/ShadowPath3.png")) ;
            
            tile[21] = new Tile() ;
            tile[21].image = ImageIO.read(new File("./src/main/java/tile/ShadowPath4.png")) ;
            
            tile[22] = new Tile() ;
            tile[22].image = ImageIO.read(new File("./src/main/java/tile/ShadowPath5.png")) ;
            
        }catch(IOException e) {
            e.printStackTrace() ;
        }
            
    }
    
    public void setMap(int mode) {
        if(mode == g.normalMode) {
            loadMap("./src/main/java/map/map.txt") ;
        }
        if(mode == g.endlessMode) {
            loadMap("./src/main/java/map/mapEndless.txt") ;
        }
    }
    
    public void loadMap(String filePath) {
        try {
            File file = new File(filePath) ;
            BufferedReader br = new BufferedReader(new FileReader(file)) ;
            
            int col = 0 ;
            int row = 0 ;
            
            if(g.gameMode == g.normalMode) {
                while(col < g.maxWorldCol && row < g.maxWorldRow) {

                    String line = br.readLine() ;

                    while(col < g.maxWorldCol) {

                        String numbers[] = line.split(" ") ;

                        int num = Integer.parseInt((numbers[col])) ;

                        mapTileNum[row][col] = num ;
                        col++ ;
                    }
                    if(col == g.maxWorldCol) {
                        col = 0 ;
                        row++ ;
                    }
                }
            }
            if(g.gameMode == g.endlessMode) {
                while(col < g.maxWorldCol && row < g.maxWorldEndlessRow) {

                    String line = br.readLine() ;

                    while(col < g.maxWorldCol) {

                        String numbers[] = line.split(" ") ;

                        int num = Integer.parseInt((numbers[col])) ;

                        mapTileNum[row][col] = num ;
                        col++ ;
                    }
                    if(col == g.maxWorldCol) {
                        col = 0 ;
                        row++ ;
                    }
                }
            }
            br.close() ;
        }catch(Exception e) {
            
        }
    }
    
    public void draw(Graphics2D g2) {
        
        int worldCol = 0 ;
        int worldRow = 0 ;
        if(g.gameMode == g.normalMode) {
            while(worldCol < g.maxWorldCol && worldRow < g.maxWorldRow) {

                int tileNum = mapTileNum[worldRow][worldCol] ;

                int worldX = worldCol * g.tileSize ;
                int worldY = worldRow * g.tileSize ;
                int screenX = worldX - g.player.worldX + g.player.screenX ;
                int screenY = worldY - g.player.worldY + g.player.screenY ;

                if(worldX > g.player.worldX - g.player.screenX - 300 &&
                        worldX < g.player.worldX + g.player.screenX + 300 &&
                        worldY > g.player.worldY - g.player.screenY - 300 &&
                        worldY < g.player.worldY + g.player.screenY + 300) {
                        g2.drawImage(tile[tileNum].image, worldX, screenY, g.tileSize, g.tileSize, null) ;
                }

                worldCol++ ;

                if(worldCol == g.maxWorldCol) {
                    worldCol = 0 ;
                    worldRow++ ;
                }
            }
        }
        if(g.gameMode == g.endlessMode) {
            while(worldCol < g.maxWorldCol && worldRow < g.maxWorldEndlessRow) {
                
                int tileNum = 1 ;
                
                if(worldCol == 0) {
                    tileNum = 1 ;
                }
                else if(worldCol == 6) {
                    tileNum = 2 ;
                }
                else {
                    tileNum = 0 ;
                }

                int worldX = worldCol * g.tileSize ;
                int worldY = worldRow * g.tileSize ;
                int screenX = worldX - g.player.worldX + g.player.screenX ;
                int screenY = worldY - g.player.worldY + g.player.screenY ;

                if(worldX > g.player.worldX - g.player.screenX - 300 &&
                        worldX < g.player.worldX + g.player.screenX + 300 &&
                        worldY > g.player.worldY - g.player.screenY - 300 &&
                        worldY < g.player.worldY + g.player.screenY + 300) {
                        g2.drawImage(tile[tileNum].image, worldX, screenY, g.tileSize, g.tileSize, null) ;
                }

                worldCol++ ;
                
                if(worldCol == g.maxWorldCol) {
                    worldCol = 0 ;
                    worldRow++ ;
                }
            }
            
        }
    }
    
}
