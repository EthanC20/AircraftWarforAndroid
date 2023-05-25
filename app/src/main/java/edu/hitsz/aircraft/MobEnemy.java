package edu.hitsz.aircraft;


import android.media.MediaPlayer;

import java.util.LinkedList;
import java.util.List;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.BulletFactory;
import edu.hitsz.music.MusicController;
import edu.hitsz.strategy.ShootStrategy;
import edu.hitsz.tool.BaseTool;


/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends EnemyAircraft {
    private final int score=10;
    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootStrategy, BulletFactory bulletFactory) {
        super(locationX, locationY, speedX, speedY, hp,shootStrategy,bulletFactory);
    }

    public List<BaseTool> drop(){
        return null;
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
