package Effect;


import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Viet
 */
public class Animation {
    
    private String Ten;
    
    private boolean LapLai;
    
    private ArrayList<FrameImage> KhungHinh;
    private int KhungHinhHienTai;
    
    private ArrayList<Boolean> BoQuaKhungHinh;
    
    private ArrayList<Double> LamChamKhungHinh;
    private long ThoiGianBatDau;

    private boolean VeKhungHinhChuNhat;
    
    public Animation(){
        LamChamKhungHinh = new ArrayList<Double>();
        ThoiGianBatDau = 0;
        KhungHinhHienTai = 0;

        BoQuaKhungHinh = new ArrayList<Boolean>();
        
        KhungHinh = new ArrayList<FrameImage>();
        
        VeKhungHinhChuNhat = false;
        
        LapLai = true;
    }
    
    public Animation(Animation animation){
        
        ThoiGianBatDau = animation.ThoiGianBatDau;
        KhungHinhHienTai = animation.KhungHinhHienTai;
        VeKhungHinhChuNhat = animation.VeKhungHinhChuNhat;
        LapLai = animation.LapLai;
        
        LamChamKhungHinh = new ArrayList<Double>();
        for(Double d : animation.LamChamKhungHinh){
            LamChamKhungHinh.add(d);
        }
        
        BoQuaKhungHinh = new ArrayList<Boolean>();
        for(boolean b : animation.BoQuaKhungHinh){
            BoQuaKhungHinh.add(b);
        }
        
        KhungHinh = new ArrayList<FrameImage>();
        for(FrameImage f : animation.KhungHinh){
            KhungHinh.add(new FrameImage(f));
        }
    }
    
    public void setLapLai(boolean isDaLapLai){
        this.LapLai = isDaLapLai; // Lap lai
    }
    
    public boolean getLapLai(){
        return LapLai;
    }
    
    public boolean isBoQuaKhungHinh(int id){
        return BoQuaKhungHinh.get(id);
    }
    
    public void setBoQuaKhungHinh(int id){
        if(id >= 0 && id < BoQuaKhungHinh.size())
            BoQuaKhungHinh.set(id, true);
    }
    
    public void unBoQuaKhungHinh(int id){
        if(id >= 0 && id < BoQuaKhungHinh.size())
            BoQuaKhungHinh.set(id, false);
    }
    
    public void setTen(String ten){
        this.Ten = ten;
    }
    public String getTen(){
        return Ten;
    }
    
    public void setKhungHinhHienTai(int KhungHinhHienTai){
        if(KhungHinhHienTai >= 0 && KhungHinhHienTai < KhungHinh.size())
            this.KhungHinhHienTai = KhungHinhHienTai;
        else this.KhungHinhHienTai = 0;
    }
    public int getKhungHinhHienTai(){
        return this.KhungHinhHienTai;
    }
    
    public void reset(){
        KhungHinhHienTai = 0;
        ThoiGianBatDau = 0;
    }
    
    public void add(FrameImage khunghinh, double ThoiGianDenKhungHinhTiepTheo){

        BoQuaKhungHinh.add(false);
        KhungHinh.add(khunghinh);
        LamChamKhungHinh.add(new Double(ThoiGianDenKhungHinhTiepTheo));
        
    }
    
    public void setVeKhungHinhChuNhat(boolean b){
        VeKhungHinhChuNhat = b;
    }

    
    public BufferedImage getHinhHienTai(){
        return KhungHinh.get(KhungHinhHienTai).getHinh();
    }
    
    public void Update(long deltaTime){
        
        if(ThoiGianBatDau == 0) ThoiGianBatDau = deltaTime;
        else{
            
            if(deltaTime - ThoiGianBatDau > LamChamKhungHinh.get(KhungHinhHienTai)){
                KhungKeTiep();
                ThoiGianBatDau = deltaTime;
            }
        }
        
    }

    
    public boolean isKhungCuoi(){
        if(KhungHinhHienTai == KhungHinh.size() - 1)
            return true;
        else return false;
    }
    
    private void KhungKeTiep(){
        
        if(KhungHinhHienTai >= KhungHinh.size() - 1){
            
            if(LapLai) KhungHinhHienTai = 0;
        }
        else KhungHinhHienTai++;
        
        if(BoQuaKhungHinh.get(KhungHinhHienTai)) KhungKeTiep();
        
    }
       
    
    public void LatTatCaHinh(){
        
        for(int i = 0;i < KhungHinh.size(); i++){
            
            BufferedImage image = KhungHinh.get(i).getHinh();
            
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(), 0);

            AffineTransformOp op = new AffineTransformOp(tx,
            AffineTransformOp.TYPE_BILINEAR);
            image = op.filter(image, null);
            
            KhungHinh.get(i).setHinh(image);
            
        }
        
    }
    
    public void Ve(int x, int y, Graphics2D g2){
        
        BufferedImage image = getHinhHienTai();
        
        g2.drawImage(image, x - image.getWidth()/2, y - image.getHeight()/2, null);
        if(VeKhungHinhChuNhat)
            g2.drawRect(x - image.getWidth()/2, x - image.getWidth()/2, image.getWidth(), image.getHeight());
        
    }
    
}
