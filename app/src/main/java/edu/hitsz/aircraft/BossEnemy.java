package edu.hitsz.aircraft;

import android.media.MediaPlayer;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.basic.RandomNumGenerator;
import edu.hitsz.bullet.BulletFactory;
import edu.hitsz.music.MusicController;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.tool.BaseTool;
import edu.hitsz.tool.BombSupplyFactory;
import edu.hitsz.tool.FireSupplyFactory;
import edu.hitsz.tool.HpSupplyFactory;
import edu.hitsz.tool.ToolFactory;

public class BossEnemy extends EnemyAircraft{
    private final int score=50;
    private final int dropNum=3;
    private final int bombDecreaseHp=100;
    private MediaPlayer player;
    private boolean haveMusic=false;
    public BossEnemy (int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootStrategy, BulletFactory bulletFactory){
        super(locationX,locationY,speedX,speedY,hp,shootStrategy,bulletFactory);
    }


    @Override
    public int getScore() {
        return score;
    }


    @Override
    public List<BaseTool> drop() {
        LinkedList<BaseTool> toolList = new LinkedList<>();
        ToolFactory bombSupplyFactory=new BombSupplyFactory();
        ToolFactory hpSupplyFactory=new HpSupplyFactory();
        ToolFactory fireSupplyFactory=new FireSupplyFactory();
        int y=this.getLocationY();
        int speedX=0;
        int speedY=7;
        BaseTool tool;
        for (int i=0;i<dropNum;i++){
            int x=this.getLocationX()+70*(i-1);
            int decision= RandomNumGenerator.getRandom();
            if (decision<33){
                tool=bombSupplyFactory.createTool(x,y,speedX,speedY);
            }else if(decision<66){
                tool= fireSupplyFactory.createTool(x,y,speedX,speedY);
            }else{
                tool= hpSupplyFactory.createTool(x,y,speedX,speedY);
            }
            toolList.add(tool);
        }
        return toolList;
    }

    @Override
    public void setMediaPlayer(MediaPlayer player, boolean haveMusic) {
        this.player=player;
        this.haveMusic=haveMusic;
        if (haveMusic){
            player.setLooping(true);
            player.start();
        }
    }

    @Override
    public void vanish() {
        if (haveMusic){
            player.stop();
            player.release();
        }
        super.vanish();
    }

    @Override
    public void update() {
        this.decreaseHp(bombDecreaseHp);
    }
}