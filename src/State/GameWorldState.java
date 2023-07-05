package State;


import Effect.CacheDataLoader;
import Effect.FrameImage;
import GameObject.BackgroundMap;
import GameObject.BulletManager;
import GameObject.Camera;
import GameObject.EnemyGhost;
import GameObject.FinalBoss;
import GameObject.MegaMan;
import GameObject.ParticularObject;
import GameObject.ParticularObjectManager;
import GameObject.PhysicalMap;
import GameObject.RedEyeDevil;
import GameObject.RobotR;
import UserInterface.GameFrame;
import UserInterface.GamePanel;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;


public class GameWorldState extends State {
	
    private BufferedImage bufferedImage;
    private int TinhTrangCuoi;

    public ParticularObjectManager QuanLyDoiTuongCuThe;
    public BulletManager QuanLyVienDan;

    public MegaMan megaMan;
   
    public PhysicalMap BanDoVatLy;
    public BackgroundMap BanDoNen;
    public Camera camera;

    public static final int finalBossX = 3600;
    
    public static final int INIT_GAME = 0;
    public static final int TUTORIAL = 1;
    public static final int GAMEPLAY = 2;
    public static final int GAMEOVER = 3;
    public static final int GAMEWIN = 4;
    public static final int PAUSEGAME = 5;
    
    public static final int INTROGAME = 0;
    public static final int MEETFINALBOSS = 1;
    
    public int openIntroGameY = 0;
    public int state = INIT_GAME;
    public int previousState = state;
    public int tutorialState = INTROGAME;
    
    public int storyTutorial = 0;
    public String[] texts1 = new String[4];

    public String textTutorial;
    public int currentSize = 1;
    
    private boolean finalbossTrigger = true;
    ParticularObject boss;
    
    FrameImage avatar = CacheDataLoader.getInstance().getFrameImage("avatar");
    
    
    private int numberOfLife = 3;
    
    public AudioClip bgMusic;
    
    public GameWorldState(GamePanel gamePanel){
            super(gamePanel);
        
        texts1[0] = "Chúng ta là những siêu anh hùng \n"
                +"và nhiệm vụ của chúng ta là bảo vệ Trái Đất....";
        texts1[1] = "Nơi đây đã bị các quái vật từ ngoài không gian chiếm đóng\n"
                + "và loài người đã sống trong sự sợ hải suốt 10 năm qua....";
        texts1[2] = "Bây giờ là thời gian khởi nghĩa, tiêu diệt chúng \n"
                + "và dành lấy sự tự do....";
        texts1[3] = "      Tiến lênnnn!.....";
        textTutorial = texts1[0];

        
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        megaMan = new MegaMan(400, 400, this);
        BanDoVatLy = new PhysicalMap(0, 0, this);
        BanDoNen = new BackgroundMap(0, 0, this);
        camera = new Camera(0, 50, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);
        QuanLyVienDan = new BulletManager(this);
        
        QuanLyDoiTuongCuThe = new ParticularObjectManager(this);
        QuanLyDoiTuongCuThe.addObject(megaMan);
        
        KhoiTaoQuai();

        bgMusic = CacheDataLoader.getInstance().getSound("bgmusic");
        //bgMusic = null;
    }
    
    private void KhoiTaoQuai(){
        ParticularObject ghost = new EnemyGhost(3000,480, this);
        ghost.setPhuongHuong(ParticularObject.PhuongHuong_Trai);
        ghost.setLoaiNhom(ParticularObject.Nhom_KeDich);
        QuanLyDoiTuongCuThe.addObject(ghost);  
        
        ParticularObject ghost2 = new EnemyGhost(1900,1200, this);
        ghost2.setPhuongHuong(ParticularObject.PhuongHuong_Trai);
        ghost2.setLoaiNhom(ParticularObject.Nhom_KeDich);
        QuanLyDoiTuongCuThe.addObject(ghost2);
        
        ParticularObject redeye = new RedEyeDevil(1250, 410, this);
        redeye.setPhuongHuong(ParticularObject.PhuongHuong_Trai);
        redeye.setLoaiNhom(ParticularObject.Nhom_KeDich);
        QuanLyDoiTuongCuThe.addObject(redeye);  
                
        ParticularObject redeye2 = new RedEyeDevil(2500, 500, this);
        redeye2.setPhuongHuong(ParticularObject.PhuongHuong_Trai);
        redeye2.setLoaiNhom(ParticularObject.Nhom_KeDich);
        QuanLyDoiTuongCuThe.addObject(redeye2);
        
        ParticularObject redeye3 = new RedEyeDevil(3450, 500, this);
        redeye3.setPhuongHuong(ParticularObject.PhuongHuong_Trai);
        redeye3.setLoaiNhom(ParticularObject.Nhom_KeDich);
        QuanLyDoiTuongCuThe.addObject(redeye3);
        
        ParticularObject redeye4 = new RedEyeDevil(500, 1190, this);
        redeye4.setPhuongHuong(ParticularObject.PhuongHuong_Phai);
        redeye4.setLoaiNhom(ParticularObject.Nhom_KeDich);
        QuanLyDoiTuongCuThe.addObject(redeye4);
        
        ParticularObject redeye5 = new RedEyeDevil(1150, 1160, this);
        redeye5.setPhuongHuong(ParticularObject.PhuongHuong_Trai);
        redeye5.setLoaiNhom(ParticularObject.Nhom_KeDich);
        QuanLyDoiTuongCuThe.addObject(redeye5);
        
        ParticularObject robotr1 = new RobotR(1900, 1000, this);
        robotr1.setLoaiNhom(ParticularObject.Nhom_KeDich);
        QuanLyDoiTuongCuThe.addObject(robotr1);
        
        ParticularObject robotr2 = new RobotR(1700, 400, this);
        robotr2.setLoaiNhom(ParticularObject.Nhom_KeDich);
        QuanLyDoiTuongCuThe.addObject(robotr2);
                       
    }

    public void ChuyenTrangThai(int state){
        previousState = this.state;
        this.state = state;
    }
    
    private void TutorialUpdate(){
        switch(tutorialState){
            case INTROGAME:
                
                if(storyTutorial == 0){
                    if(openIntroGameY < 450) {
                        openIntroGameY+=4;
                    }else storyTutorial ++;
                    
                }else{
                
                    if(currentSize < textTutorial.length()) currentSize++;
                }
                break;
            case MEETFINALBOSS:
                if(storyTutorial == 0){
                    if(openIntroGameY >= 450) {
                        openIntroGameY-=1;
                    }
                    if(camera.getPosX() < finalBossX){
                        camera.setPosX(camera.getPosX() + 2);
                    }
                    
                    if(megaMan.getPosX() < finalBossX + 150){
                        megaMan.setPhuongHuong(ParticularObject.PhuongHuong_Phai);
                        megaMan.Chay();
                        megaMan.Update();
                    }else{
                        megaMan.DungChay();
                    }
                    
                    if(openIntroGameY < 450 && camera.getPosX() >= finalBossX && megaMan.getPosX() >= finalBossX + 150){ 
                        camera.lock();
                        storyTutorial++;
                        megaMan.DungChay();
                        BanDoVatLy.phys_map[14][120] = 1;
                        BanDoVatLy.phys_map[15][120] = 1;
                        BanDoVatLy.phys_map[16][120] = 1;
                        BanDoVatLy.phys_map[17][120] = 1;
                        
                        BanDoNen.BanDo[14][120] = 17;
                        BanDoNen.BanDo[15][120] = 17;
                        BanDoNen.BanDo[16][120] = 17;
                        BanDoNen.BanDo[17][120] = 17;
                    }
                    
                }else{
                
                    if(currentSize < textTutorial.length()) currentSize++;
                }
                break;
        }
    }
    
    private void VeChuoi(Graphics2D g2, String text, int x, int y){
        for(String str : text.split("\n"))
            g2.drawString(str, x, y+=g2.getFontMetrics().getHeight());
    }
    
    private void TutorialRender(Graphics2D g2){
        switch(tutorialState){
            case INTROGAME:
                int yMid = GameFrame.SCREEN_HEIGHT/2 - 15;
                int y1 = yMid - GameFrame.SCREEN_HEIGHT/2 - openIntroGameY/2;
                int y2 = yMid + openIntroGameY/2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                
                if(storyTutorial >= 1){
                    g2.drawImage(avatar.getHinh(), 600, 350, null);
                    g2.setColor(Color.BLUE);
                    g2.fillRect(280, 450, 350, 80);
                    g2.setColor(Color.WHITE);
                    String text = textTutorial.substring(0, currentSize - 1);
                    VeChuoi(g2, text, 290, 480);
                }
                
                break;
            case MEETFINALBOSS:
                yMid = GameFrame.SCREEN_HEIGHT/2 - 15;
                y1 = yMid - GameFrame.SCREEN_HEIGHT/2 - openIntroGameY/2;
                y2 = yMid + openIntroGameY/2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                break;
        }
    }
    
    public void CapNhat(){
        
        switch(state){
            case INIT_GAME:
                
                break;
            case TUTORIAL:
                TutorialUpdate();
                
                break;
            case GAMEPLAY:
                QuanLyDoiTuongCuThe.UpdateObjects();
                QuanLyVienDan.UpdateObjects();
        
                BanDoVatLy.Update();
                camera.Update();
                
                
                if(megaMan.getPosX() > finalBossX && finalbossTrigger){
                    finalbossTrigger = false;
                    ChuyenTrangThai(TUTORIAL);
                    tutorialState = MEETFINALBOSS;
                    storyTutorial = 0;
                    openIntroGameY = 550;
                    
                    boss = new FinalBoss(finalBossX + 700, 460, this);
                    boss.setLoaiNhom(ParticularObject.Nhom_KeDich);
                    boss.setPhuongHuong(ParticularObject.PhuongHuong_Trai);
                    QuanLyDoiTuongCuThe.addObject(boss);

                }
                
                if(megaMan.getTrangThai() == ParticularObject.Chet){
                    numberOfLife --;
                    if(numberOfLife >= 0){
                        megaMan.setMau(100);
                        megaMan.setPosY(megaMan.getPosY() - 50);
                        megaMan.setTrangThai(ParticularObject.KhongBiThuong);
                        QuanLyDoiTuongCuThe.addObject(megaMan);
                    }else{
                        ChuyenTrangThai(GAMEOVER);
                        bgMusic.stop();
                    }
                }
                if(!finalbossTrigger && boss.getTrangThai() == ParticularObject.Chet)
                    ChuyenTrangThai(GAMEWIN);
                
                break;
            case GAMEOVER:
                
                break;
            case GAMEWIN:
                
                break;
        }
        

    }

    public void KetXuat(){

        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();

        if(g2!=null){

            // NOTE: two lines below make the error splash white screen....
            // need to remove this line
            //g2.setColor(Color.WHITE);
            //g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
            
            
            //BanDoVatLy.draw(g2);
            
            switch(state){
                case INIT_GAME:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("NHẤN ENTER ĐỂ BẮT ĐẦU", 400, 300);
                    break;
                case PAUSEGAME:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(300, 260, 500, 70);
                    g2.setColor(Color.WHITE);
                    g2.drawString("NHẤN ENTER ĐỂ TIẾP TỤC", 400, 300);
                    break;
                case TUTORIAL:
                    BanDoNen.Ve(g2);
                    if(tutorialState == MEETFINALBOSS){
                        QuanLyDoiTuongCuThe.draw(g2);
                    }
                    TutorialRender(g2);
                    
                    break;
                case GAMEWIN:
                case GAMEPLAY:
                    BanDoNen.Ve(g2);
                    QuanLyDoiTuongCuThe.draw(g2);  
                    QuanLyVienDan.draw(g2);
                    
                    g2.setColor(Color.GRAY);
                    g2.fillRect(19, 59, 102, 22);
                    g2.setColor(Color.red);
                    g2.fillRect(20, 60, megaMan.getMau(), 20);
                    
                    for(int i = 0; i < numberOfLife; i++){
                        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("hearth").getHinh(), 20 + i*40, 18, null);
                    }
                    
                    
                    if(state == GAMEWIN){
                        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("gamewin").getHinh(), 300, 300, null);
                    }
                    
                    break;
                case GAMEOVER:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("THUA!", 450, 300);
                    break;

            }
            

        }

    }

    public BufferedImage getBufferedImage(){
        return bufferedImage;
    }

    @Override
    public void setNutDaNhan(int code) {
       switch(code){
            
            case KeyEvent.VK_DOWN:
                megaMan.Ne();
                break;
                
            case KeyEvent.VK_RIGHT:
                megaMan.setPhuongHuong(megaMan.PhuongHuong_Phai);
                megaMan.Chay();
                break;
                
            case KeyEvent.VK_LEFT:
                megaMan.setPhuongHuong(megaMan.PhuongHuong_Trai);
                megaMan.Chay();
                break;
                
            case KeyEvent.VK_ENTER:
                if(state == GameWorldState.INIT_GAME){
                    if(previousState == GameWorldState.GAMEPLAY)
                        ChuyenTrangThai(GameWorldState.GAMEPLAY);
                    else ChuyenTrangThai(GameWorldState.TUTORIAL);
                    
                    bgMusic.loop();
                }
                if(state == GameWorldState.TUTORIAL && storyTutorial >= 1){
                    if(storyTutorial<=3){
                        storyTutorial ++;
                        currentSize = 1;
                        textTutorial = texts1[storyTutorial-1];
                    }else{
                        ChuyenTrangThai(GameWorldState.GAMEPLAY);
                    }
                    
                    // for meeting boss tutorial
                    if(tutorialState == GameWorldState.MEETFINALBOSS){
                        ChuyenTrangThai(GameWorldState.GAMEPLAY);
                    }
                }
                break;
                
            case KeyEvent.VK_SPACE:
                megaMan.Nhay();
                break;
                
            case KeyEvent.VK_A:
                megaMan.TanCong();
                break;
                
        }}

    @Override
    public void setNutDaNha(int code) {
        switch(code){
            
            case KeyEvent.VK_UP:
                
                break;
                
            case KeyEvent.VK_DOWN:
                megaMan.DungDay();
                break;
                
            case KeyEvent.VK_RIGHT:
                if(megaMan.getTocDoX() > 0)
                    megaMan.DungChay();
                break;
                
            case KeyEvent.VK_LEFT:
                if(megaMan.getTocDoX() < 0)
                    megaMan.DungChay();
                break;
                
            case KeyEvent.VK_ENTER:
                if(state == GAMEOVER || state == GAMEWIN) {
                    gamePanel.setState(new GameWorldState(gamePanel));
                } else if(state == PAUSEGAME) {
                    state = TinhTrangCuoi;
                }
                break;
                
            case KeyEvent.VK_SPACE:
                
                break;
                
            case KeyEvent.VK_A:
                
                break;
            case KeyEvent.VK_ESCAPE:
                TinhTrangCuoi = state;
                state = PAUSEGAME;
                break;
                
        }}
	
}
