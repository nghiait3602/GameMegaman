/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;


import State.State;
import java.awt.event.KeyEvent;

/**
 *
 * @author Viet
 */
public class InputManager {
    
    private State TrangThaiGame;
    
    public InputManager(State state){
        this.TrangThaiGame = state;
    }
    
    public void setTrangThaiGame(State state) {
        TrangThaiGame = state;
    }
    
    public void setNutDaNhan(int code){
        TrangThaiGame.setNutDaNhan(code);
    }
    
    public void setNutDaNha(int code){
        TrangThaiGame.setNutDaNha(code);
    }
    
}
