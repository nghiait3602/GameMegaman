package Effect;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Viet
 */
public class FrameImage{
    
    private String ten;
    private BufferedImage hinh;
    
    public FrameImage(String name, BufferedImage image){
        this.ten = name;
        this.hinh = image;
    }
    
    public FrameImage(FrameImage khunghinh){
        hinh = new BufferedImage(khunghinh.getChieuRongAnh(), 
                khunghinh.getChieuCaoAnh(), khunghinh.hinh.getType());
        Graphics g = hinh.getGraphics();
        g.drawImage(khunghinh.hinh, 0, 0, null);
        ten = khunghinh.ten;
    }
    
    public void Ve(int x, int y, Graphics2D g2){
        
        g2.drawImage(hinh, x - hinh.getWidth()/2, y - hinh.getHeight()/2, null);
        
    }
    
    public FrameImage(){
        this.ten = null;
        hinh = null;
    }
    
    public int getChieuRongAnh(){
        return hinh.getWidth();
    }

    public int getChieuCaoAnh(){
        return hinh.getHeight();
    }
    
    public void setTen(String name){
        this.ten = name;
    }
    public String getTen(){
        return ten;
    }
    
    public BufferedImage getHinh(){
        return hinh;
    }
    public void setHinh(BufferedImage image){
        this.hinh = image;
    }

}
