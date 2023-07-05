package GameObject;

import Effect.Animation;
import Effect.CacheDataLoader;
import State.GameWorldState;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class RedEyeBullet extends Bullet{
	
    private Animation forwardBulletAnim, backBulletAnim;
    
    public RedEyeBullet(float x, float y, GameWorldState gameWorld) {
            super(x, y, 30, 30, 1.0f, 10, gameWorld);
            forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("redeyebullet");
            backBulletAnim = CacheDataLoader.getInstance().getAnimation("redeyebullet");
            backBulletAnim.LatTatCaHinh();
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
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void Update() {
            // TODO Auto-generated method stub
        super.Update();
    }

    @Override
    public void TanCong() {}

}
