/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObject;


import Effect.Animation;
import Effect.CacheDataLoader;
import State.GameWorldState;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Hashtable;

/**
 *
 * @author phamn
 */
public class FinalBoss extends ParticularObject {

    private Animation idleforward, idleback;
    private Animation shootingforward, shootingback;
    private Animation slideforward, slideback;
    
    private long startTimeForAttacked;
    
    private Hashtable<String, Long> timeAttack = new Hashtable<String, Long>(); 
    private String[] attackType = new String[4];
    private int attackIndex = 0;
    private long lastAttackTime;
    
    public FinalBoss(float x, float y, GameWorldState gameWorld) {
        super(x, y, 110, 150, 0.1f, 100, gameWorld);
        idleback = CacheDataLoader.getInstance().getAnimation("boss_idle");
        idleforward = CacheDataLoader.getInstance().getAnimation("boss_idle");
        idleforward.LatTatCaHinh();
        
        shootingback = CacheDataLoader.getInstance().getAnimation("boss_shooting");
        shootingforward = CacheDataLoader.getInstance().getAnimation("boss_shooting");
        shootingforward.LatTatCaHinh();
        
        slideback = CacheDataLoader.getInstance().getAnimation("boss_slide");
        slideforward = CacheDataLoader.getInstance().getAnimation("boss_slide");
        slideforward.LatTatCaHinh();
        
        setThoiGianDeKhongBiThuong(500*1000000);
        setSatThuong(10);
        
        attackType[0] = "NONE";
        attackType[1] = "shooting";
        attackType[2] = "NONE";
        attackType[3] = "slide";
        
        timeAttack.put("NONE", new Long(2000));
        timeAttack.put("shooting", new Long(500));
        timeAttack.put("slide", new Long(5000));
        
    }

    public void Update(){
        super.Update();
        
        if(getGameWorld().megaMan.getPosX() > getPosX())
            setPhuongHuong(PhuongHuong_Phai);
        else setPhuongHuong(PhuongHuong_Trai);
        
        if(startTimeForAttacked == 0)
            startTimeForAttacked = System.currentTimeMillis();
        else if(System.currentTimeMillis() - startTimeForAttacked > 300){
            TanCong();
            startTimeForAttacked = System.currentTimeMillis();
        }
        
        if(!attackType[attackIndex].equals("NONE")){
            if(attackType[attackIndex].equals("shooting")){
                
                Bullet bullet = new RocketBullet(getPosX(), getPosY() - 50, getGameWorld());
                if(getPhuongHuong() == PhuongHuong_Trai) bullet.setTocDoX(-4);
                else bullet.setTocDoX(4);
                bullet.setLoaiNhom(getLoaiNhom());
                getGameWorld().QuanLyVienDan.addObject(bullet);
                
            }else if(attackType[attackIndex].equals("slide")){
                
                if(getGameWorld().BanDoVatLy.haveCollisionWithLeftWall(getGioiHanVaChamVoiBanDo())!=null)
                    setTocDoX(5);
                if(getGameWorld().BanDoVatLy.haveCollisionWithRightWall(getGioiHanVaChamVoiBanDo())!=null)
                    setTocDoX(-5);
                
                
                setPosX(getPosX() + getTocDoX());
            }
        }else{
            // stop attack
            setTocDoX(0);
        }
        
    }
    
//    @Override
//    public void Chay() {
//        
//    }
//
//    @Override
//    public void Nhay() {
//        setSpeedY(-5);
//    }
//
//    @Override
//    public void Ne() {
//    
//    
//    }
//
//    @Override
//    public void DungDay() {
//    
//    
//    }
//
//    @Override
//    public void DungChay() {
//    
//    
//    }

    @Override
    public void TanCong() {
    
        // only switch state attack
        
        if(System.currentTimeMillis() - lastAttackTime > timeAttack.get(attackType[attackIndex])){
            lastAttackTime = System.currentTimeMillis();
            
            attackIndex ++;
            if(attackIndex >= attackType.length) attackIndex = 0;
            
            if(attackType[attackIndex].equals("slide")){
                if(getPosX() < getGameWorld().megaMan.getPosX()) setTocDoX(5);
                else setTocDoX(-5);
            }
            
        }
    
    }

    @Override
    public Rectangle getGioiHanVaChamVoiQuai() {
        if(attackType[attackIndex].equals("slide")){
            Rectangle rect = getGioiHanVaChamVoiBanDo();
            rect.y += 100;
            rect.height -= 100;
            return rect;
        }else
            return getGioiHanVaChamVoiBanDo();
    }

    @Override
    public void draw(Graphics2D g2) {
        
        if(getTrangThai() == KhongBiThuong && (System.nanoTime()/10000000)%2!=1)
        {
            System.out.println("Plash...");
        }else{
            
            if(attackType[attackIndex].equals("NONE")){
                if(getPhuongHuong() == PhuongHuong_Phai){
                    idleforward.Update(System.nanoTime());
                    idleforward.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }else{
                    idleback.Update(System.nanoTime());
                    idleback.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
            }else if(attackType[attackIndex].equals("shooting")){
                
                if(getPhuongHuong() == PhuongHuong_Phai){
                    shootingforward.Update(System.nanoTime());
                    shootingforward.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }else{
                    shootingback.Update(System.nanoTime());
                    shootingback.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                
            }else if(attackType[attackIndex].equals("slide")){
                if(getTocDoX() > 0){
                    slideforward.Update(System.nanoTime());
                    slideforward.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + 50, g2);
                }else{
                    slideback.Update(System.nanoTime());
                    slideback.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY() + 50, g2);
                }
            }
        }
       // drawBoundForCollisionWithEnemy(g2);
    }
    
}
