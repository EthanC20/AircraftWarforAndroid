package edu.hitsz.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import edu.hitsz.R;

public class ModeChooseActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static int screenWidth;
    public static int screenHeight;

    private boolean haveMusic;

    private int gameType=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button medium_btn = findViewById(R.id.medium_btn);
        Button easy_btn = findViewById(R.id.easy_btn);
        Button hard_btn = findViewById(R.id.hard_btn);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch musicController_switch=findViewById(R.id.music_controller);


        getScreenHW();

        musicController_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                haveMusic=isChecked;
            }
        });

        Intent intent = new Intent(ModeChooseActivity.this, GameActivity.class);
        medium_btn.setOnClickListener(view -> {
            gameType=GameActivity.MEDIUM_MODE;
            intent.putExtra("gameType",gameType);
            intent.putExtra("haveMusic",haveMusic);
            startActivity(intent);
        });

        easy_btn.setOnClickListener(view -> {
            gameType =GameActivity.EASY_MODE;
            intent.putExtra("gameType",gameType);
            intent.putExtra("haveMusic",haveMusic);
            startActivity(intent);
        });

        hard_btn.setOnClickListener(view -> {
            gameType =GameActivity.HARD_MODE;
            intent.putExtra("gameType",gameType);
            intent.putExtra("haveMusic",haveMusic);
            startActivity(intent);
        });

    }
    public void getScreenHW(){
        //定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //窗口的宽度
        screenWidth= dm.widthPixels;
        //窗口高度
        screenHeight = dm.heightPixels;

        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}