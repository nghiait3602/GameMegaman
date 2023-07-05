/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package State;

import UserInterface.GamePanel;
import java.awt.image.BufferedImage;

/**
 *
 * @author Viet
 */
public abstract class State {
    
    protected GamePanel gamePanel;
    
    public State(GamePanel gamePanel) {
       this.gamePanel = gamePanel; 
    }
    
    public abstract void CapNhat();
    public abstract void KetXuat();
    public abstract BufferedImage getBufferedImage();
    
    public abstract void setNutDaNhan(int code);
    public abstract void setNutDaNha(int code);
}
