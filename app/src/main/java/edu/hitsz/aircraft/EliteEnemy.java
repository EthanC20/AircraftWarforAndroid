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

public class EliteEnemy extends EnemyAircraft {
    private int score=20;
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootStrategy, BulletFactory bulletFactory){
        super(locationX,locationY,speedX,speedY,hp,shootStrategy,bulletFactory);
    }


    public List<BaseTool> drop(){
        List<BaseTool> toolList=new LinkedList<>();
        BaseTool tool;
        int i= RandomNumGenerator.getRandom();
        int x=this.getLocationX();
        int y=this.getLocationY();
        int speedX=0;
        int speedY=this.getSpeedY();
        if(i<10){
            tool=null;
        }else if (i<40) {
            ToolFactory hpSupplyFactory=new HpSupplyFactory();
            tool= hpSupplyFactory.createTool(x, y, speedX, speedY);
        }else if(i<70){
            ToolFactory fireSupplyFactory=new FireSupplyFactory();
            tool= fireSupplyFactory.createTool(x, y, speedX, speedY);
        }else{
            ToolFactory bombSupplyFactory=new BombSupplyFactory();
            tool= bombSupplyFactory.createTool(x, y, speedX, speedY);
        }
        if (tool!=null){
            toolList.add(tool);
        }
        return toolList;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public void update() {
        this.vanish();
    }

    @Override
    public void setMediaPlayer(MediaPlayer player, boolean haveMusic) {

    }
}
