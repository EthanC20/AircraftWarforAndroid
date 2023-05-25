package edu.hitsz.game;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;

import java.util.HashMap;

import edu.hitsz.ImageManager;

public class EasyGame extends BaseGame{
    private final double bossHpIncreaseRate = 0;
    private double enemyIncreaseRate = 0;

    public EasyGame(Context context, Handler handler, boolean haveMusic) {
        super(context,handler,haveMusic);
        this.backGround = ImageManager.BACKGROUND1_IMAGE;
    }
    @Override
    public boolean haveBoss() {
        return false;
    }

    @Override
    public boolean increaseDifficulty() {
        return false;
    }

    @Override
    public int getSleepTime() {
        return 10000;
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
