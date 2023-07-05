package GameObject;

import Effect.Animation;
import Effect.CacheDataLoader;
import State.GameWorldState;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BlueFire extends Bullet{
	
    private Animation HoatAnhVienDanPhiaTruoc, HoatAnhVienDanPhiaSau;
    
    public BlueFire(float x, float y, GameWorldState gameWorld) {
        super(x, y, 60, 30, 1.0f, 10, gameWorld);
        HoatAnhVienDanPhiaTruoc = CacheDataLoader.getInstance().getAnimation("bluefire");
        HoatAnhVienDanPhiaSau = CacheDataLoader.getInstance().getAnimation("bluefire");
        HoatAnhVienDanPhiaSau.LatTatCaHinh();
    }

    
    
    @Override
    public Rectangle getGioiHanVaChamVoiQuai() {
        // TODO Auto-generated method stub
        return getGioiHanVaChamVoiBanDo();
    }

    @Override
    public void draw(Graphics2D g2) {
            // TODO Auto-generated method stub
        if(getTocDoX() > 0){
            if(!HoatAnhVienDanPhiaTruoc.isBoQuaKhungHinh(0) && HoatAnhVienDanPhiaTruoc.getKhungHinhHienTai()== 3){
                HoatAnhVienDanPhiaTruoc.setBoQuaKhungHinh(0);
                HoatAnhVienDanPhiaTruoc.setBoQuaKhungHinh(1);
                HoatAnhVienDanPhiaTruoc.setBoQuaKhungHinh(2);
            }
                
            HoatAnhVienDanPhiaTruoc.Update(System.nanoTime());
            HoatAnhVienDanPhiaTruoc.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            if(!HoatAnhVienDanPhiaSau.isBoQuaKhungHinh(0) && HoatAnhVienDanPhiaSau.getKhungHinhHienTai() == 3){
                HoatAnhVienDanPhiaSau.setBoQuaKhungHinh(0);
                HoatAnhVienDanPhiaSau.setBoQuaKhungHinh(1);
                HoatAnhVienDanPhiaSau.setBoQuaKhungHinh(2);
            }
            HoatAnhVienDanPhiaSau.Update(System.nanoTime());
            HoatAnhVienDanPhiaSau.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void Update() {
            // TODO Auto-generated method stub
        if(HoatAnhVienDanPhiaTruoc.isBoQuaKhungHinh(0) || HoatAnhVienDanPhiaSau.isBoQuaKhungHinh(0))
            setPosX(getPosX() + getTocDoX());
        ParticularObject object = getGameWorld().QuanLyDoiTuongCuThe.getCollisionWidthEnemyObject(this);
        if(object!=null && object.getTrangThai() == ConSong){
            setMau(0);
            object.setMau(object.getMau() - getSatThuong());
            object.setTrangThai(BiThuong);
            System.out.println("Thiết lập đạn gây sát thương cho quái");
        }
    }

    @Override
    public void TanCong() {}

}
