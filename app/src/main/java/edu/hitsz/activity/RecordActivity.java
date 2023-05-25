package edu.hitsz.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import edu.hitsz.R;
import edu.hitsz.record.Record;
import edu.hitsz.record.RecordDao;
import edu.hitsz.record.RecordDaoImpl;

public class RecordActivity extends AppCompatActivity {
    private static final String TAG = "RecordActivity";
    public static int screenWidth;
    public static int screenHeight;
    private int gameType=0;
    private int score=0;
    private final String file="record.dat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);
        Button return_btn=findViewById(R.id.return_btn);
        TextView modeText=findViewById(R.id.modetext);
        if(getIntent() != null){
            gameType = getIntent().getIntExtra("gameType",-1);
            score=getIntent().getIntExtra("score",0);
        }
        if (gameType==GameActivity.EASY_MODE){
            modeText.setText("难度：简单");
        }else if(gameType==GameActivity.MEDIUM_MODE){
            modeText.setText("难度：中等");
        }else {
            modeText.setText("难度：困难");
        }
        getScreenHW();

        Intent intent = new Intent(RecordActivity.this, ModeChooseActivity.class);


        RecordDao recordImpl = null;
        try {
            recordImpl = new RecordDaoImpl(gameType,this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG,"file not found");
        }
        recordImpl.readRecord();
        List<HashMap<String,String>> data=recordImpl.getRecords();

        RecordDao finalRecordImpl = recordImpl;
        return_btn.setOnClickListener(view -> startActivity(intent));


        SimpleAdapter adapter=new SimpleAdapter(this,data,R.layout.recordlayout,
                new String[]{"rank","name","score","date"},
                new int[]{R.id.ranktext,R.id.nametext,R.id.scoretext,R.id.datetext});

        ListView listView=findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(RecordActivity.this);
            builder.setMessage("确定删除？");
            builder.setTitle("提示");

            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                finalRecordImpl.deleteRecord(i+1);
                adapter.notifyDataSetChanged();
            });

            builder.setNegativeButton("No",null);

            builder.create().show();
            return false;
        });
        AlertDialog.Builder builder=new AlertDialog.Builder(RecordActivity.this);
        EditText editText=new EditText(RecordActivity.this);
        builder.setTitle("你的得分是："+score+"\n请输入你的姓名");
        builder.setView(editText);
        builder.setPositiveButton("确认", (dialogInterface, i) -> {
            String name=editText.getText().toString();
            Record record=new Record(name,score, new Date(),gameType);
            finalRecordImpl.insertRecord(record);
            adapter.notifyDataSetChanged();
        });
        builder.show();

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
