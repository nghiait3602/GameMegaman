/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObject;

import Effect.Animation;
import Effect.CacheDataLoader;
import State.GameWorldState;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author phamn
 */
public class RobotR extends ParticularObject {

    private Animation forwardAnim, backAnim;
    
    private long startTimeToShoot;
    private float x1, x2, y1, y2;
    
    private AudioClip shooting;
    
    public RobotR(float x, float y, GameWorldState gameWorld) {
        super(x, y, 127, 89, 0, 100, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("robotR");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("robotR");
        forwardAnim.LatTatCaHinh();
        startTimeToShoot = 0;
        setThoiGianDeKhongBiThuong(300000000);
        setSatThuong(10);
        
        x1 = x - 100;
        x2 = x + 100;
        y1 = y - 50;
        y2 = y + 50;
        
        setTocDoX(1);
        setTocDoY(1);
        
        shooting = CacheDataLoader.getInstance().getSound("robotRshooting");
    }

    @Override
    public void TanCong() {  
        
        shooting.play();
        Bullet bullet = new RobotRBullet(getPosX(), getPosY(), getGameWorld());
        
        if(getPhuongHuong()==PhuongHuong_Trai)
            bullet.setTocDoX(5);
        else bullet.setTocDoX(-5);
        bullet.setLoaiNhom(getLoaiNhom());
        getGameWorld().QuanLyVienDan.addObject(bullet);

    }

    
    public void Update(){
        super.Update();
        
        if(getPosX() - getGameWorld().megaMan.getPosX() > 0) setPhuongHuong(ParticularObject.PhuongHuong_Phai);
        else setPhuongHuong(ParticularObject.PhuongHuong_Trai);
        
        if(getPosX() < x1)
            setTocDoX(1);
        else if(getPosX() > x2)
            setTocDoX(-1);
        setPosX(getPosX() + getTocDoX());
        
        if(getPosY() < y1)
            setTocDoY(1);
        else if(getPosY() > y2)
            setTocDoY(-1);
        setPosY(getPosY() + getTocDoY());
        
        if(System.nanoTime() - startTimeToShoot > 1000*10000000*1.5){
            TanCong();
            startTimeToShoot = System.nanoTime();
        }
    }
    
    @Override
    public Rectangle getGioiHanVaChamVoiQuai() {
        Rectangle rect = getGioiHanVaChamVoiBanDo();
        rect.x += 20;
        rect.width -= 40;
        
        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
        if(!DoiTuongNamNgoaiGocNhin()){
            if(getTrangThai() == KhongBiThuong && (System.nanoTime()/10000000)%2!=1){
                // plash...
            }else{
                if(getPhuongHuong() == PhuongHuong_Trai){
                    backAnim.Update(System.nanoTime());
                    backAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }else{
                    forwardAnim.Update(System.nanoTime());
                    forwardAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
        }
        //drawBoundForCollisionWithEnemy(g2);
    }
    
}
