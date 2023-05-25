package edu.hitsz.record;

import android.content.Context;
import android.icu.util.Output;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


import edu.hitsz.activity.GameActivity;

public class RecordDaoImpl implements RecordDao{
    private List<HashMap<String,String>> easyModeRecords=new LinkedList<>();
    private List<HashMap<String,String>> generalModeRecord=new LinkedList<>();
    private List<HashMap<String,String>> hardModeRecord=new LinkedList<>();
    private int mode;
    private List<HashMap<String,String>> presentRecord;
    private HashMap<Integer,List<HashMap<String,String>>> map=new HashMap<Integer,List<HashMap<String,String>>>();
    private final String file="record.dat";
    private final Context context;
    public RecordDaoImpl(int mode,Context context) throws FileNotFoundException {
        this.mode=mode;
        map.put(GameActivity.EASY_MODE,easyModeRecords);
        map.put(GameActivity.MEDIUM_MODE,generalModeRecord);
        map.put(GameActivity.HARD_MODE,hardModeRecord);
        presentRecord=map.get(mode);
        this.context=context;
    }

    @Override
    public void readRecord() {
        try {
            FileInputStream fis= context.openFileInput(file);
            ObjectInputStream ois=new ObjectInputStream(fis);
            easyModeRecords=(List<HashMap<String, String>>) ois.readObject();
            generalModeRecord=(List<HashMap<String, String>>) ois.readObject();
            hardModeRecord=(List<HashMap<String, String>>) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
//            Log.e("aaaaa", e.getMessage());
//            e.printStackTrace();
//            Log.i("read",e.getMessage());
            writeRecord();
        }

        map.put(GameActivity.EASY_MODE,easyModeRecords);
        map.put(GameActivity.MEDIUM_MODE,generalModeRecord);
        map.put(GameActivity.HARD_MODE,hardModeRecord);
        presentRecord=map.get(mode);
        Log.i("kkkkk","kkk"+map.size());
        Log.i("jifejifj","EEEE"+easyModeRecords.size()+"GGG"+generalModeRecord.size()+"HH"+hardModeRecord.size());
    }


    @Override
    public void writeRecord() {
        try {
            FileOutputStream fos= context.openFileOutput(file,Context.MODE_PRIVATE);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(easyModeRecords);
            oos.writeObject(generalModeRecord);
            oos.writeObject(hardModeRecord);
            Log.i("FFF","EEEE"+easyModeRecords.size()+"GG"+generalModeRecord.size()+"HH"+hardModeRecord.size());
            oos.close();
        }catch (Exception e){
            e.printStackTrace();
            Log.i("TTTT",e.getMessage());
        }
    }

    @Override
    public List<HashMap<String, String>> getRecords() {
        return presentRecord;
    }



    @Override
    public void insertRecord(Record record) {
        HashMap<String,String> newRecordMap=record.getMap();

        for (int i=0;i<presentRecord.size();i++){
            if(Integer.parseInt(Objects.requireNonNull(presentRecord.get(i).get("score")))<record.getScore()){
                newRecordMap.put("rank",String.valueOf(i+1));
                presentRecord.add(i,newRecordMap);
                for(int j=i+1;j<presentRecord.size();j++){
                    presentRecord.get(j).put("rank",String.valueOf(Integer.parseInt(Objects.requireNonNull(presentRecord.get(j).get("rank")))+1));
                }
                writeRecord();
                return;
            }
        }
        if (presentRecord.size()==0||Integer.parseInt(Objects.requireNonNull(presentRecord.get(presentRecord.size() - 1).get("score")))>= record.getScore()){
            newRecordMap.put("rank",String.valueOf(presentRecord.size()+1));
            presentRecord.add(newRecordMap);
        }
        Log.i("KKK","EEEE"+easyModeRecords.size()+"GG"+generalModeRecord.size()+"HH"+hardModeRecord.size());
        writeRecord();
        Log.i("MMM","EEEE"+easyModeRecords.size()+"GG"+generalModeRecord.size()+"HH"+hardModeRecord.size());
    }

    @Override
    public void deleteRecord(int rank) {
        if (rank<=presentRecord.size()&&rank>0){
            presentRecord.remove(rank-1);
            for(int i=rank-1;i<presentRecord.size();i++){
                presentRecord.get(i).put("rank",String.valueOf(Integer.parseInt(Objects.requireNonNull(presentRecord.get(i).get("rank")))-1));
            }
        }
        writeRecord();
    }

}