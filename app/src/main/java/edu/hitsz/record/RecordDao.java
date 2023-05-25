package edu.hitsz.record;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface RecordDao {
    void writeRecord();
    void readRecord();
    void deleteRecord(int rank);
    void insertRecord(Record record);
    List<HashMap<String,String>> getRecords();
}
