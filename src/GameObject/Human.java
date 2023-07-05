package GameObject;

import State.GameWorldState;
import java.awt.Rectangle;

public abstract class Human extends ParticularObject{

    private boolean isJumping;
    private boolean isDicking;
    
    private boolean isLanding;

    public Human(float x, float y, float width, float height, float mass, int blood, GameWorldState gameWorld) {
        super(x, y, width, height, mass, blood, gameWorld);
        setTrangThai(ConSong);
    }

    public abstract void Chay();
    
    public abstract void Nhay();
    
    public abstract void Ne();
    
    public abstract void DungDay();
    
    public abstract void DungChay();

    public boolean getDangNhay() {
        return isJumping;
    }
    
    public void setDapDat(boolean b){
        isLanding = b;
    }
    
    public boolean getDapDat(){
        return isLanding;
    }
    
    public void setDangNhay(boolean isJumping) {
        this.isJumping = isJumping;
    }

    public boolean getNe() {
        return isDicking;
    }

    public void setNe(boolean isDicking) {
        this.isDicking = isDicking;
    }
    
    @Override
    public void Update(){
        
        super.Update();
        
        if(getTrangThai() == ConSong || getTrangThai() == KhongBiThuong){
        
            if(!isLanding){

                setPosX(getPosX() + getTocDoX());


                if(getPhuongHuong() == PhuongHuong_Trai && 
                        getGameWorld().BanDoVatLy.haveCollisionWithLeftWall(getGioiHanVaChamVoiBanDo())!=null){

                    Rectangle rectLeftWall = getGameWorld().BanDoVatLy.haveCollisionWithLeftWall(getGioiHanVaChamVoiBanDo());
                    setPosX(rectLeftWall.x + rectLeftWall.width + getChieuRong()/2);

                }
                if(getPhuongHuong() == PhuongHuong_Phai && 
                        getGameWorld().BanDoVatLy.haveCollisionWithRightWall(getGioiHanVaChamVoiBanDo())!=null){

                    Rectangle rectRightWall = getGameWorld().BanDoVatLy.haveCollisionWithRightWall(getGioiHanVaChamVoiBanDo());
                    setPosX(rectRightWall.x - getChieuRong()/2);

                }



                /**
                 * Codes below check the posY of megaMan
                 */
                // plus (+2) because we must check below the character when he's speedY = 0

                Rectangle boundForCollisionWithMapFuture = getGioiHanVaChamVoiBanDo();
                boundForCollisionWithMapFuture.y += (getTocDoY()!=0?getTocDoY(): 2);
                Rectangle rectLand = getGameWorld().BanDoVatLy.haveCollisionWithLand(boundForCollisionWithMapFuture);
                
                Rectangle rectTop = getGameWorld().BanDoVatLy.haveCollisionWithTop(boundForCollisionWithMapFuture);
                
                if(rectTop !=null){
                    
                    setTocDoY(0);
                    setPosY(rectTop.y + getGameWorld().BanDoVatLy.getTileSize() + getChieuCao()/2);
                    
                }else if(rectLand != null){
                    setDangNhay(false);
                    if(getTocDoY() > 0) setDapDat(true);
                    setTocDoY(0);
                    setPosY(rectLand.y - getChieuCao()/2 - 1);
                }else{
                    setDangNhay(true);
                    setTocDoY(getTocDoY() + getTapHop());
                    setPosY(getPosY() + getTocDoY());
                }
            }
        }
    }
    
}
