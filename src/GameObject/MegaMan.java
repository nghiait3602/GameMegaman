package GameObject;


import Effect.Animation;
import Effect.CacheDataLoader;
import State.GameWorldState;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MegaMan extends Human {

    public static final int RUNSPEED = 3;
    
    private Animation runForwardAnim, runBackAnim, runShootingForwarAnim, runShootingBackAnim;
    private Animation idleForwardAnim, idleBackAnim, idleShootingForwardAnim, idleShootingBackAnim;
    private Animation dickForwardAnim, dickBackAnim;
    private Animation flyForwardAnim, flyBackAnim, flyShootingForwardAnim, flyShootingBackAnim;
    private Animation landingForwardAnim, landingBackAnim;
    
    private Animation climWallForward, climWallBack;
    
    private long lastShootingTime;
    private boolean isShooting = false;
    
    private AudioClip hurtingSound;
    private AudioClip shooting1;
    
    public MegaMan(float x, float y, GameWorldState gameWorld) {
        super(x, y, 70, 90, 0.1f, 100, gameWorld);
        
        shooting1 = CacheDataLoader.getInstance().getSound("bluefireshooting");
        hurtingSound = CacheDataLoader.getInstance().getSound("megamanhurt");
        
        setLoaiNhom(Nhom_DongMinh);

        setThoiGianDeKhongBiThuong(2000*1000000);
        
        runForwardAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim.LatTatCaHinh();
        
        idleForwardAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim = CacheDataLoader.getInstance().getAnimation("idle");
        idleBackAnim.LatTatCaHinh();
        
        dickForwardAnim = CacheDataLoader.getInstance().getAnimation("dick");
        dickBackAnim = CacheDataLoader.getInstance().getAnimation("dick");
        dickBackAnim.LatTatCaHinh();
        
        flyForwardAnim = CacheDataLoader.getInstance().getAnimation("flyingup");
        flyForwardAnim.setLapLai(false);
        flyBackAnim = CacheDataLoader.getInstance().getAnimation("flyingup");
        flyBackAnim.setLapLai(false);
        flyBackAnim.LatTatCaHinh();
        
        landingForwardAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingBackAnim = CacheDataLoader.getInstance().getAnimation("landing");
        landingBackAnim.LatTatCaHinh();
        
        climWallBack = CacheDataLoader.getInstance().getAnimation("clim_wall");
        climWallForward = CacheDataLoader.getInstance().getAnimation("clim_wall");
        climWallForward.LatTatCaHinh();
        
        HoatAnhBiThuongPhiaTruoc = CacheDataLoader.getInstance().getAnimation("hurting");
        HoatAnhBiThuongPhiaSau = CacheDataLoader.getInstance().getAnimation("hurting");
        HoatAnhBiThuongPhiaSau.LatTatCaHinh();
        
        idleShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim = CacheDataLoader.getInstance().getAnimation("idleshoot");
        idleShootingBackAnim.LatTatCaHinh();
        
        runShootingForwarAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
        runShootingBackAnim = CacheDataLoader.getInstance().getAnimation("runshoot");
        runShootingBackAnim.LatTatCaHinh();
        
        flyShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        flyShootingBackAnim = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        flyShootingBackAnim.LatTatCaHinh();
        
    }

    @Override
    public void Update() {

        super.Update();
        
        if(isShooting){
            if(System.nanoTime() - lastShootingTime > 500*1000000){
                isShooting = false;
            }
        }
        
        if(getDapDat()){
            landingBackAnim.Update(System.nanoTime());
            if(landingBackAnim.isKhungCuoi()) {
                setDapDat(false);
                landingBackAnim.reset();
                runForwardAnim.reset();
                runBackAnim.reset();
            }
        }
        
    }

    @Override
    public Rectangle getGioiHanVaChamVoiQuai() {
        // TODO Auto-generated method stub
        Rectangle rect = getGioiHanVaChamVoiBanDo();
        
        if(getNe()){
            rect.x = (int) getPosX() - 22;
            rect.y = (int) getPosY() - 20;
            rect.width = 44;
            rect.height = 65;
        }else{
            rect.x = (int) getPosX() - 22;
            rect.y = (int) getPosY() - 40;
            rect.width = 44;
            rect.height = 80;
        }
        
        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
        
        switch(getTrangThai()){
        
            case ConSong:
            case KhongBiThuong:
                if(getTrangThai() == KhongBiThuong && (System.nanoTime()/10000000)%2!=1)
                {
                    System.out.println("Plash...");
                }else{
                    
                    if(getDapDat()){

                        if(getPhuongHuong() == PhuongHuong_Phai){
                            landingForwardAnim.setKhungHinhHienTai(landingBackAnim.getKhungHinhHienTai());
                            landingForwardAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getGioiHanVaChamVoiBanDo().height/2 - landingForwardAnim.getHinhHienTai().getHeight()/2),
                                    g2);
                        }else{
                            landingBackAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getGioiHanVaChamVoiBanDo().height/2 - landingBackAnim.getHinhHienTai().getHeight()/2),
                                    g2);
                        }

                    }else if(getDangNhay()){

                        if(getPhuongHuong() == PhuongHuong_Phai){
                            flyForwardAnim.Update(System.nanoTime());
                            if(isShooting){
                                flyShootingForwardAnim.setKhungHinhHienTai(flyForwardAnim.getKhungHinhHienTai());
                                flyShootingForwardAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()) + 10, (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                                flyForwardAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }else{
                            flyBackAnim.Update(System.nanoTime());
                            if(isShooting){
                                flyShootingBackAnim.setKhungHinhHienTai(flyBackAnim.getKhungHinhHienTai());
                                flyShootingBackAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()) - 10, (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                            flyBackAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        }

                    }else if(getNe()){

                        if(getPhuongHuong() == PhuongHuong_Phai){
                            dickForwardAnim.Update(System.nanoTime());
                            dickForwardAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getGioiHanVaChamVoiBanDo().height/2 - dickForwardAnim.getHinhHienTai().getHeight()/2),
                                    g2);
                        }else{
                            dickBackAnim.Update(System.nanoTime());
                            dickBackAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), 
                                    (int) getPosY() - (int) getGameWorld().camera.getPosY() + (getGioiHanVaChamVoiBanDo().height/2 - dickBackAnim.getHinhHienTai().getHeight()/2),
                                    g2);
                        }

                    }else{
                        if(getTocDoX() > 0){
                            runForwardAnim.Update(System.nanoTime());
                            if(isShooting){
                                runShootingForwarAnim.setKhungHinhHienTai(runForwardAnim.getKhungHinhHienTai()- 1);
                                runShootingForwarAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                                runForwardAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            if(runForwardAnim.getKhungHinhHienTai()== 1) runForwardAnim.setBoQuaKhungHinh(0);
                        }else if(getTocDoX() < 0){
                            runBackAnim.Update(System.nanoTime());
                            if(isShooting){
                                runShootingBackAnim.setKhungHinhHienTai(runBackAnim.getKhungHinhHienTai() - 1);
                                runShootingBackAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else
                                runBackAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            if(runBackAnim.getKhungHinhHienTai() == 1) runBackAnim.setBoQuaKhungHinh(0);
                        }else{
                            if(getPhuongHuong() == PhuongHuong_Phai){
                                if(isShooting){
                                    idleShootingForwardAnim.Update(System.nanoTime());
                                    idleShootingForwardAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }else{
                                    idleForwardAnim.Update(System.nanoTime());
                                    idleForwardAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            }else{
                                if(isShooting){
                                    idleShootingBackAnim.Update(System.nanoTime());
                                    idleShootingBackAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }else{
                                    idleBackAnim.Update(System.nanoTime());
                                    idleBackAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                                }
                            }
                        }            
                    }
                }
                
                break;
            
            case BiThuong:
                if(getPhuongHuong() == PhuongHuong_Phai){
                    HoatAnhBiThuongPhiaTruoc.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }else{
                    HoatAnhBiThuongPhiaSau.setKhungHinhHienTai(HoatAnhBiThuongPhiaTruoc.getKhungHinhHienTai());
                    HoatAnhBiThuongPhiaSau.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                break;
             
            case SapChet:
                
                break;

        }
        
        //drawBoundForCollisionWithMap(g2);
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void Chay() {
        if(getPhuongHuong() == PhuongHuong_Trai)
            setTocDoX(-3);
        else setTocDoX(3);
    }

    @Override
    public void Nhay() {

        if(!getDangNhay()){
            setDangNhay(true);
            setTocDoY(-5.0f);           
            flyBackAnim.reset();
            flyForwardAnim.reset();
        }
        // for clim wall
        else{
            Rectangle rectRightWall = getGioiHanVaChamVoiBanDo();
            rectRightWall.x += 1;
            Rectangle rectLeftWall = getGioiHanVaChamVoiBanDo();
            rectLeftWall.x -= 1;
            
            if(getGameWorld().BanDoVatLy.haveCollisionWithRightWall(rectRightWall)!=null && getTocDoX() > 0){
                setTocDoY(-5.0f);
                //setSpeedX(-1);
                flyBackAnim.reset();
                flyForwardAnim.reset();
                //setDirection(LEFT_DIR);
            }else if(getGameWorld().BanDoVatLy.haveCollisionWithLeftWall(rectLeftWall)!=null && getTocDoX() < 0){
                setTocDoY(-5.0f);
                //setSpeedX(1);
                flyBackAnim.reset();
                flyForwardAnim.reset();
                //setDirection(RIGHT_DIR);
            }
                
        }
    }

    @Override
    public void Ne() {
        if(!getDangNhay())
            setNe(true);
    }

    @Override
    public void DungDay() {
        setNe(false);
        idleForwardAnim.reset();
        idleBackAnim.reset();
        dickForwardAnim.reset();
        dickBackAnim.reset();
    }

    @Override
    public void DungChay() {
        setTocDoX(0);
        runForwardAnim.reset();
        runBackAnim.reset();
        runForwardAnim.unBoQuaKhungHinh(0);
        runBackAnim.unBoQuaKhungHinh(0);
    }

    @Override
    public void TanCong() {
    
        if(!isShooting && !getNe()){
            
            shooting1.play();
            
            Bullet bullet = new BlueFire(getPosX(), getPosY(), getGameWorld());
            if(getPhuongHuong() == PhuongHuong_Trai) {
                bullet.setTocDoX(-10);
                bullet.setPosX(bullet.getPosX() - 40);
                if(getTocDoX() != 0 && getTocDoY() == 0){
                    bullet.setPosX(bullet.getPosX() - 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            }else {
                bullet.setTocDoX(10);
                bullet.setPosX(bullet.getPosX() + 40);
                if(getTocDoX() != 0 && getTocDoY() == 0){
                    bullet.setPosX(bullet.getPosX() + 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            }
            if(getDangNhay())
                bullet.setPosY(bullet.getPosY() - 20);
            
            bullet.setLoaiNhom(getLoaiNhom());
            getGameWorld().QuanLyVienDan.addObject(bullet);
            
            lastShootingTime = System.nanoTime();
            isShooting = true;
            
        }
    
    }
    @Override
    public void hurtingCallback(){
        System.out.println("Call back hurting");
        hurtingSound.play();
    }

}
