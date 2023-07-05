package GameObject;

import State.GameWorldState;
import java.awt.Graphics2D;

public abstract class Bullet extends ParticularObject {

    public Bullet(float x, float y, float width, float height, float mass, int damage, GameWorldState gameWorld) {
            super(x, y, width, height, mass, 1, gameWorld);
            setSatThuong(damage);
    }
    
    public abstract void draw(Graphics2D g2d);

    public void Update(){
        super.Update();
        setPosX(getPosX() + getTocDoX());
        setPosY(getPosY() + getTocDoY());
        ParticularObject object = getGameWorld().QuanLyDoiTuongCuThe.getCollisionWidthEnemyObject(this);
        if(object!=null && object.getTrangThai() == ConSong){
            setMau(0);
            object.BiThuong(getSatThuong());
            System.out.println("Dan gay sat thuong cho quai");
        }
    }
    
}
