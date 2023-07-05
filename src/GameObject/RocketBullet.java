package GameObject;


import Effect.Animation;
import Effect.CacheDataLoader;
import State.GameWorldState;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class RocketBullet extends Bullet{
	
    private Animation forwardBulletAnimUp, forwardBulletAnimDown, forwardBulletAnim;
    private Animation backBulletAnimUp, backBulletAnimDown, backBulletAnim;

    private long startTimeForChangeSpeedY;
    
    public RocketBullet(float x, float y, GameWorldState gameWorld) {
        
            super(x, y, 30, 30, 1.0f, 10, gameWorld);
            
            backBulletAnimUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
            backBulletAnimDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
            backBulletAnim = CacheDataLoader.getInstance().getAnimation("rocket");
            
            forwardBulletAnimUp = CacheDataLoader.getInstance().getAnimation("rocketUp");
            forwardBulletAnimUp.LatTatCaHinh();
            forwardBulletAnimDown = CacheDataLoader.getInstance().getAnimation("rocketDown");
            forwardBulletAnimDown.LatTatCaHinh();
            forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("rocket");
            forwardBulletAnim.LatTatCaHinh();

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
            if(getTocDoY() > 0){
                forwardBulletAnimDown.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else if(getTocDoY() < 0){
                forwardBulletAnimUp.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else
                forwardBulletAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }else{
            if(getTocDoY() > 0){
                backBulletAnimDown.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else if(getTocDoY() < 0){
                backBulletAnimUp.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
            }else
                backBulletAnim.Ve((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    private void changeSpeedY(){
        if(System.currentTimeMillis() % 3 == 0){
            setTocDoY(getTocDoX());
        }else if(System.currentTimeMillis() % 3 == 1){
            setTocDoY(-getTocDoX());
        }else {
            setTocDoY(0);
            
        }
    }
    
    @Override
    public void Update() {
            // TODO Auto-generated method stub
        super.Update();
        
        if(System.nanoTime() - startTimeForChangeSpeedY > 500*1000000){
            startTimeForChangeSpeedY = System.nanoTime();
            changeSpeedY();
        }
    }

    @Override
    public void TanCong() {}

}
