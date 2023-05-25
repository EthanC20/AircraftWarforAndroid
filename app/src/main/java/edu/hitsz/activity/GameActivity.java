package edu.hitsz.activity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import edu.hitsz.R;
import edu.hitsz.game.BaseGame;
import edu.hitsz.game.EasyGame;
import edu.hitsz.game.HardGame;
import edu.hitsz.game.MediumGame;


public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    private int gameType=0;
    private boolean haveMusic=true;

    public static int EASY_MODE=2;
    public static int MEDIUM_MODE=1;
    public static int HARD_MODE=3;

    private AudioAttributes audioAttributes;
    private SoundPool soundPool;
    private final HashMap<Integer,Integer> soundPoolMap=new HashMap<>();
    MediaPlayer bgmPlayer;
    MediaPlayer bossBgmPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseGame basGameView;
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d(TAG,"handleMessage");
                if (msg.what == 1) {
                    int score=msg.arg1;
                    Toast.makeText(GameActivity.this,"GameOver",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(GameActivity.this,RecordActivity.class);
                    intent.putExtra("gameType",gameType);
                    intent.putExtra("score",score);
                    startActivity(intent);
                }
            }
        };
        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",1);
            haveMusic=getIntent().getBooleanExtra("haveMusic",true);
        }

        if(gameType == MEDIUM_MODE){
            basGameView = new MediumGame(this,handler,haveMusic);

        }else if(gameType == HARD_MODE){
            basGameView = new HardGame(this,handler,haveMusic);
        }else{
            basGameView = new EasyGame(this,handler,haveMusic);
        }
        setContentView(basGameView);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}