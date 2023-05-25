package edu.hitsz.aircraft;

import android.media.MediaPlayer;

import java.util.List;

import edu.hitsz.activity.ModeChooseActivity;
import edu.hitsz.bullet.BulletFactory;
import edu.hitsz.music.MusicController;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.tool.BaseTool;

public abstract class EnemyAircraft extends AbstractAircraft implements Observer{
    public EnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootStrategy, BulletFactory bulletFactory){
        super(locationX,locationY,speedX,speedY,hp,shootStrategy,bulletFactory);
    }

    @Override
    public void forward() {
        super.forward();
        if (locationY>= ModeChooseActivity.screenHeight){
            vanish();
        }
    }
    public abstract int getScore();
    public abstract List<BaseTool> drop();
    public abstract void setMediaPlayer(MediaPlayer player, boolean haveMusic);
}

