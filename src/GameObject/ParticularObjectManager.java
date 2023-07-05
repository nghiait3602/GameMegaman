package GameObject;


import State.GameWorldState;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ParticularObjectManager {

    protected List<ParticularObject> particularObjects;

    private GameWorldState gameWorld;
    
    public ParticularObjectManager(GameWorldState gameWorld){
        
        particularObjects = Collections.synchronizedList(new LinkedList<ParticularObject>());
        this.gameWorld = gameWorld;
        
    }
    
    public GameWorldState getGameWorld(){
        return gameWorld;
    }
    
    public void addObject(ParticularObject particularObject){
        
        
        synchronized(particularObjects){
            particularObjects.add(particularObject);
        }
        
    }
    
    public void RemoveObject(ParticularObject particularObject){
        synchronized(particularObjects){
        
            for(int id = 0; id < particularObjects.size(); id++){
                
                ParticularObject object = particularObjects.get(id);
                if(object == particularObject)
                    particularObjects.remove(id);

            }
        }
    }
    
    public ParticularObject getCollisionWidthEnemyObject(ParticularObject object){
        synchronized(particularObjects){
            for(int id = 0; id < particularObjects.size(); id++){
                
                ParticularObject objectInList = particularObjects.get(id);

                if(object.getLoaiNhom() != objectInList.getLoaiNhom() && 
                        object.getGioiHanVaChamVoiQuai().intersects(objectInList.getGioiHanVaChamVoiQuai())){
                    return objectInList;
                }
            }
        }
        return null;
    }
    
    public void UpdateObjects(){
        
        synchronized(particularObjects){
            for(int id = 0; id < particularObjects.size(); id++){
                
                ParticularObject object = particularObjects.get(id);
                
                
                if(!object.DoiTuongNamNgoaiGocNhin()) object.Update();
                
                if(object.getTrangThai() == ParticularObject.Chet){
                    particularObjects.remove(id);
                }
            }
        }

        //System.out.println("Camerawidth  = "+camera.getWidth());
        
    }
    
    public void draw(Graphics2D g2){
        synchronized(particularObjects){
            for(ParticularObject object: particularObjects)
                if(!object.DoiTuongNamNgoaiGocNhin()) object.draw(g2);
        }
    }
	
}
