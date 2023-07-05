package GameObject;


import Effect.Animation;
import Effect.CacheDataLoader;
import State.GameWorldState;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class RobotRBullet extends Bullet{
	
    private Animation forwardBulletAnim, backBulletAnim;
    
    public RobotRBullet(float x, float y, GameWorldState gameWorld) {
        super(x, y, 60, 30, 1.0f, 10, gameWorld);
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("robotRbullet");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("robotRbullet");
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
