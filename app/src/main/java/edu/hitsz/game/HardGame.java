package edu.hitsz.game;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;

import java.util.HashMap;

import edu.hitsz.ImageManager;

public class HardGame extends BaseGame{
    private final double bossHpIncreaseRate=0.2;
    private final double enemyIncreaseRate=0.06;
    private final int sleepTime=10000;
    public HardGame(Context context, Handler handler, boolean haveMusic) {
        super(context,handler,haveMusic);
        this.backGround = ImageManager.BACKGROUND3_IMAGE;
    }
    @Override
    public int getSleepTime() {
        return sleepTime;
    }

    @Override
    public boolean bossIncrease() {
        return true;
    }

    @Override
    public boolean haveBoss() {
        return true;
    }

    @Override
    public boolean increaseDifficulty() {
        return true;
    }

    @Override
    public double getBossHpIncreaseRate() {
        return bossHpIncreaseRate;
    }

    @Override
    public double getEnemyIncreaseRate() {
        return enemyIncreaseRate;
    }

}
