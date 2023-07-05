package UserInterface;

import State.GameWorldState;
import State.State;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener{

    State TrangThaiGame;

    InputManager QuanLyDauVao;
    
    Thread LuongTroChoi;

    public boolean DangChay = true;

    public GamePanel(){

        //gameState = new MenuState(this);
        TrangThaiGame = new GameWorldState(this);
        
        QuanLyDauVao = new InputManager(TrangThaiGame);

    }

    public void BatDauTroChoi(){
        LuongTroChoi = new Thread(this);
        LuongTroChoi.start();
    }
    int a = 0;
    @Override
    public void run() {

        long ThoiGianTruoc = System.nanoTime();
        long ThoiGianHienTai;
        long ThoiGianNgu;

        long period = 1000000000/80;

        while(DangChay){

            TrangThaiGame.CapNhat();
            TrangThaiGame.KetXuat();
            repaint();

            ThoiGianHienTai = System.nanoTime();
            ThoiGianNgu = period - (ThoiGianHienTai - ThoiGianTruoc);
            try{

                    if(ThoiGianNgu > 0)
                            Thread.sleep(ThoiGianNgu/1000000);
                    else Thread.sleep(period/2000000);

            }catch(Exception e){}

            ThoiGianTruoc = System.nanoTime();
        }

    }

    public void paint(Graphics g){

        g.drawImage(TrangThaiGame.getBufferedImage(), 0, 0, this);

    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        QuanLyDauVao.setNutDaNhan(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        QuanLyDauVao.setNutDaNha(e.getKeyCode());
    }

    public void setState(State state) {
        TrangThaiGame = state;
        QuanLyDauVao.setTrangThaiGame(state);
    }
    
}
