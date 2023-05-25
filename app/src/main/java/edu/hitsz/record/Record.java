package edu.hitsz.record;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Record implements Serializable {
    private String name;
    private int score;
    private String date;
    private int mode;
    private final SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd hh:mm");

    public Record(String name, int score, Date date, int mode){
        this.name=name;
        this.score=score;
        this.date=dateFormat.format(date);
        this.mode=mode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    public HashMap<String,String> getMap(){
        HashMap<String,String> map=new HashMap<>();
        map.put("name",name);
        map.put("score",String.valueOf(score));
        map.put("date",date);
        return  map;
    }
}
