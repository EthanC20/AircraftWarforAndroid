package edu.hitsz.game;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;

import java.util.HashMap;

import edu.hitsz.ImageManager;

public class MediumGame extends BaseGame{
    private final double bossHpIncreaseRate=0;
    private final double enemyIncreaseRate=0.03;
    private final int sleepTime=15000;
    public MediumGame(Context context, Handler handler, boolean haveMusic) {
        super(context,handler,haveMusic);
        this.backGround = ImageManager.BACKGROUND2_IMAGE;
    }
    @Override
    public int getSleepTime() {
        return sleepTime;
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
    public boolean bossIncrease() {
        return false;
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
