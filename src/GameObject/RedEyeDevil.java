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
public class RedEyeDevil extends ParticularObject {

    private Animation forwardAnim, backAnim;
    
    private long startTimeToShoot;
    
    private AudioClip shooting;
    
    public RedEyeDevil(float x, float y, GameWorldState gameWorld) {
        super(x, y, 127, 89, 0, 100, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("redeye");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("redeye");
        forwardAnim.LatTatCaHinh();
        startTimeToShoot = 0;
        setSatThuong(10);
        setThoiGianDeKhongBiThuong(300000000);
        shooting = CacheDataLoader.getInstance().getSound("redeyeshooting");
    }

    @Override
    public void TanCong() {
    
        shooting.play();
        Bullet bullet = new RedEyeBullet(getPosX(), getPosY(), getGameWorld());
        if(getPhuongHuong() == PhuongHuong_Trai) bullet.setTocDoX(-8);
        else bullet.setTocDoX(8);
        bullet.setLoaiNhom(getLoaiNhom());
        getGameWorld().QuanLyVienDan.addObject(bullet);
    
    }

    
    public void Update(){
        super.Update();
        if(System.nanoTime() - startTimeToShoot > 1000*10000000){
            TanCong();
            System.out.println("Red Eye attack");
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
