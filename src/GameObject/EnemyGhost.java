/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameObject;

import Effect.Animation;
import Effect.CacheDataLoader;
import static GameObject.ParticularObject.PhuongHuong_Trai;
import static GameObject.ParticularObject.KhongBiThuong;
import State.GameWorldState;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Admin
 */
public class EnemyGhost extends ParticularObject {
    
    private Animation TruocGhost, SauGhost;
    private long ThoiGianBatDauBan;
    private AudioClip DangBan;
    float x1,x2 ;
    
    public EnemyGhost(float x, float y ,GameWorldState gameWorldState){
       super(x, y, 90, 40, 1.0f, 10, gameWorldState);
       TruocGhost = CacheDataLoader.getInstance().getAnimation("ghost");
       SauGhost = CacheDataLoader.getInstance().getAnimation("ghost");
       SauGhost.LatTatCaHinh();
       
        setThoiGianDeKhongBiThuong(300000000);
        setSatThuong(10);
        DangBan = CacheDataLoader.getInstance().getSound("robotRshooting");
        x1 = x - 150;
        x2 = x + 150;
        setTocDoX(1);
    }
 
    @Override
    public void TanCong() {
      
    }

    @Override
    public void Update() {
        super.Update();
        if(getPosX()  > 0) setPhuongHuong(ParticularObject.PhuongHuong_Phai);
        else setPhuongHuong(ParticularObject.PhuongHuong_Trai);
        
          if(getPosX() < x1){
             setTocDoX(1);
             
          }       
          else if(getPosX() > x2){
             setTocDoX(-1);
        }  
        setPosX(getPosX() + getTocDoX());
        
      
    }
    
    @Override
    public Rectangle getGioiHanVaChamVoiQuai() {
        Rectangle rect = getGioiHanVaChamVoiBanDo();
        rect.x += 50;
        rect.width -= 20;
        
        return rect;
    }
    
      @Override
    public void draw(Graphics2D g2) {
        if(!DoiTuongNamNgoaiGocNhin()){
            if(getTrangThai() == KhongBiThuong && (System.nanoTime()/10000000)%2!=1){
                // plash...
            }else{
                if(getTocDoX() > 0){
                   SauGhost.Update(System.nanoTime());
                    SauGhost.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }else{
                    TruocGhost.Update(System.nanoTime());
                    TruocGhost.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
        }
    }
}
