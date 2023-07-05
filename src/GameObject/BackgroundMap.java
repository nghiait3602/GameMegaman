
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObject;

import Effect.CacheDataLoader;
import State.GameWorldState;
import UserInterface.GameFrame;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author phamn
 */
public class BackgroundMap extends GameObject {

    public int[][] BanDo;
    private int KichThuocNgoi;
    
    public BackgroundMap(float x, float y, GameWorldState gameWorld) {
        super(x, y, gameWorld);
        BanDo = CacheDataLoader.getInstance().getBackgroundMap();
        KichThuocNgoi = 30;
    }

    @Override
    public void Update() {}
    
    public void Ve(Graphics2D g2){
        
        Camera camera = getGameWorld().camera;
        
        g2.setColor(Color.RED);
        for(int i = 0;i< BanDo.length;i++)
            for(int j = 0;j<BanDo[0].length;j++)
                if(BanDo[i][j]!=0 && j*KichThuocNgoi - camera.getPosX() > -30 && j*KichThuocNgoi - camera.getPosX() < GameFrame.SCREEN_WIDTH
                        && i*KichThuocNgoi - camera.getPosY() > -30 && i*KichThuocNgoi - camera.getPosY() < GameFrame.SCREEN_HEIGHT){ 
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("tiled"+BanDo[i][j]).getHinh(), (int) getPosX() + j*KichThuocNgoi - (int) camera.getPosX(), 
                        (int) getPosY() + i*KichThuocNgoi - (int) camera.getPosY(), null);
                }
        
    }
    
}
