package GameObject;

import Effect.Animation;
import State.GameWorldState;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class ParticularObject extends GameObject {

    public static final int Nhom_DongMinh = 1;
    public static final int Nhom_KeDich = 2;
    
    public static final int PhuongHuong_Trai = 0;
    public static final int PhuongHuong_Phai = 1;

    public static final int ConSong = 0;
    public static final int BiThuong = 1;
    public static final int SapChet = 2;
    public static final int Chet = 3;
    public static final int KhongBiThuong = 4;
    private int state = ConSong;
    
    private float ChieuRong;
    private float ChieuCao;
    private float TapHop;
    private float TocDoX;
    private float TocDoY;
    private int Mau;
    
    private int SatThuong;
    
    private int PhuongHuong;
    
    protected Animation HoatAnhBiThuongPhiaTruoc, HoatAnhBiThuongPhiaSau;
    
    private int LoaiNhom;
    
    private long ThoiGianBatDauKhongBiThuong;
    private long ThoiGianDeKhongBiThuong;

    public ParticularObject(float x, float y, float width, float height, float mass, int blood, GameWorldState gameWorld){

        // posX and posY are the middle coordinate of the object
        super(x, y, gameWorld);
        setChieuRong(width);
        setChieuCao(height);
        setTapHop(mass);
        setMau(blood);
        
        PhuongHuong = PhuongHuong_Phai;

    }
    
    public void setThoiGianDeKhongBiThuong(long time){
        ThoiGianDeKhongBiThuong = time;
    }
    
    public long getThoiGianDeKhongBiThuong(){
        return ThoiGianDeKhongBiThuong;
    }
    
    public void setTrangThai(int state){
        this.state = state;
    }
    
    public int getTrangThai(){
        return state;
    }
    
    public void setSatThuong(int damage){
            this.SatThuong = damage;
    }

    public int getSatThuong(){
            return SatThuong;
    }

    
    public void setLoaiNhom(int team){
        LoaiNhom = team;
    }
    
    public int getLoaiNhom(){
        return LoaiNhom;
    }
    
    public void setTapHop(float mass){
        this.TapHop = mass;
    }

    public float getTapHop(){
            return TapHop;
    }

    public void setTocDoX(float speedX){
        this.TocDoX = speedX;
    }

    public float getTocDoX(){
        return TocDoX;
    }

    public void setTocDoY(float speedY){
        this.TocDoY = speedY;
    }

    public float getTocDoY(){
        return TocDoY;
    }

    public void setMau(int blood){
        if(blood>=0)
                this.Mau = blood;
        else this.Mau = 0;
    }

    public int getMau(){
        return Mau;
    }

    public void setChieuRong(float width){
        this.ChieuRong = width;
    }

    public float getChieuRong(){
        return ChieuRong;
    }

    public void setChieuCao(float height){
        this.ChieuCao = height;
    }

    public float getChieuCao(){
        return ChieuCao;
    }
    
    public void setPhuongHuong(int dir){
        PhuongHuong = dir;
    }
    
    public int getPhuongHuong(){
        return PhuongHuong;
    }
    
    public abstract void TanCong();
    
    
    public boolean DoiTuongNamNgoaiGocNhin(){
        if(getPosX() - getGameWorld().camera.getPosX() > getGameWorld().camera.getWidthView() ||
                getPosX() - getGameWorld().camera.getPosX() < -50
            ||getPosY() - getGameWorld().camera.getPosY() > getGameWorld().camera.getHeightView()
                    ||getPosY() - getGameWorld().camera.getPosY() < -50)
            return true;
        else return false;
    }
    
    public Rectangle getGioiHanVaChamVoiBanDo(){
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (getChieuRong()/2));
        bound.y = (int) (getPosY() - (getChieuCao()/2));
        bound.width = (int) getChieuRong();
        bound.height = (int) getChieuCao();
        return bound;
    }

    public void BiThuong(int AnSatThuong){
        setMau(getMau() - AnSatThuong);
        state = BiThuong;
        hurtingCallback();
    }

    @Override
    public void Update(){
        switch(state){
            case ConSong:
                
                // note: SET DAMAGE FOR OBJECT NO DAMAGE
                ParticularObject object = getGameWorld().QuanLyDoiTuongCuThe.getCollisionWidthEnemyObject(this);
                if(object!=null){
                    
                    
                    if(object.getSatThuong() > 0){

                        // switch state to fey if object die
                        
                        
                        System.out.println("Dinh sat thuong.... tu va cham voi quai........ "+object.getSatThuong());
                        BiThuong(object.getSatThuong());
                    }
                    
                }
                
                
                
                break;
                
            case BiThuong:
                if(HoatAnhBiThuongPhiaSau == null){
                    state = KhongBiThuong;
                    ThoiGianBatDauKhongBiThuong = System.nanoTime();
                    if(getMau() == 0)
                            state = SapChet;
                    
                } else {
                    HoatAnhBiThuongPhiaTruoc.Update(System.nanoTime());
                    if(HoatAnhBiThuongPhiaTruoc.isKhungCuoi()){
                        HoatAnhBiThuongPhiaTruoc.reset();
                        state = KhongBiThuong;
                        if(getMau() == 0)
                            state = SapChet;
                        ThoiGianBatDauKhongBiThuong = System.nanoTime();
                    }
                }
                
                break;
                
            case SapChet:
                
                state = Chet;
                
                break;
            
            case Chet:
                
                
                break;
                
            case KhongBiThuong:
                System.out.println("Trang thai = Khong bi thuong");
                if(System.nanoTime() - ThoiGianBatDauKhongBiThuong > ThoiGianDeKhongBiThuong)
                    state = ConSong;
                break;
        }
        
    }

    public void VeGioiHanVaChamVoiBanDo(Graphics2D g2){
        Rectangle rect = getGioiHanVaChamVoiBanDo();
        g2.setColor(Color.BLUE);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }

    public void VeGioiHanVaChamVoiQuai(Graphics2D g2){
        Rectangle rect = getGioiHanVaChamVoiQuai();
        g2.setColor(Color.RED);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }

    public abstract Rectangle getGioiHanVaChamVoiQuai();

    public abstract void draw(Graphics2D g2);
    
    public void hurtingCallback(){};
	
}
