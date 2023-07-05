/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameObject;

import State.GameWorldState;


/**
 *
 * @author Viet
 */
public class Camera extends GameObject {

    private float GocNhinRong;
    private float GocNhinCao;
    
    private boolean isLocked = false;
    
    public Camera(float x, float y, float widthView, float heightView, GameWorldState gameWorld) {
        super(x, y, gameWorld);
        this.GocNhinRong = widthView;
        this.GocNhinCao = heightView;
    }

    public void lock(){
        isLocked = true;
    }
    
    public void unlock(){
        isLocked = false;
    }
    
    @Override
    public void Update() {
    
        // NOTE: WHEN SEE FINAL BOSS, THE CAMERA WON'T CHANGE THE POSITION,
        // AFTER THE TUTORIAL, CAMERA WILL SET THE NEW POS
        
        if(!isLocked){
        
            MegaMan mainCharacter = getGameWorld().megaMan;

            if(mainCharacter.getPosX() - getPosX() > 400) setPosX(mainCharacter.getPosX() - 400);
            if(mainCharacter.getPosX() - getPosX() < 200) setPosX(mainCharacter.getPosX() - 200);

            if(mainCharacter.getPosY() - getPosY() > 400) setPosY(mainCharacter.getPosY() - 400); // bottom
            else if(mainCharacter.getPosY() - getPosY() < 250) setPosY(mainCharacter.getPosY() - 250);// top 
        }
    
    }

    public float getWidthView() {
        return GocNhinRong;
    }

    public void setWidthView(float widthView) {
        this.GocNhinRong = widthView;
    }

    public float getHeightView() {
        return GocNhinCao;
    }

    public void setHeightView(float heightView) {
        this.GocNhinCao = heightView;
    }
    
}
