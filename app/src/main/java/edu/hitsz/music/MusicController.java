package edu.hitsz.music;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

import edu.hitsz.R;

public class MusicController {

    private boolean haveMusic;
    private final AudioAttributes audioAttributes;
    private final SoundPool soundPool;
    private final HashMap<Integer,Integer> soundPoolMap=new HashMap<>();
    private final MediaPlayer bgmPlayer;


    public MusicController(boolean haveMusic, Context context){
        this.haveMusic=haveMusic;

        audioAttributes=new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool=new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(audioAttributes)
                .build();

        soundPoolMap.put(Bgm.BOMB_EXPLOSION,soundPool.load(context,R.raw.bomb_explosion,1));
        soundPoolMap.put(Bgm.GAME_OVER,soundPool.load(context,R.raw.game_over,1));
        soundPoolMap.put(Bgm.BULLET_HIT,soundPool.load(context,R.raw.bullet_hit,1));
        soundPoolMap.put(Bgm.GET_SUPPLY,soundPool.load(context,R.raw.get_supply,1));

        bgmPlayer= MediaPlayer.create(context,R.raw.bgm);
    }

    public void bulletHitBgmDisplay(){
        if (haveMusic){
            Runnable r=()->{
                soundPool.play(soundPoolMap.get(Bgm.BULLET_HIT),1,1,0,0,1);
            };
            new Thread(r).start();
        }
    }

    public void getSupplyBgmDisplay(){
        if (haveMusic){
            Runnable r=()->{
                soundPool.play(soundPoolMap.get(Bgm.GET_SUPPLY),1,1,0,0,1);
            };
            new Thread(r).start();
        }
    }
    public void bombBgmDisplay(){
        if (haveMusic){
            Runnable r=()->{
                soundPool.play(soundPoolMap.get(Bgm.BOMB_EXPLOSION),1,1,0,0,1);
            };
            new Thread(r).start();
        }
    }
    public void gameOverBgmDisplay(){
        if (haveMusic){
            Runnable r=()->{
                soundPool.play(soundPoolMap.get(Bgm.GAME_OVER),1,1,0,0,1);
            };
            new Thread(r).start();
        }
    }
    public void bgmDisplay(){
        if (haveMusic){
            bgmPlayer.setLooping(true);
            bgmPlayer.start();
        }
    }
    public void bgmStop(){
        if (haveMusic){
            bgmPlayer.stop();
            bgmPlayer.release();
        }
    }

}
