package com.sy.globletake_user.utils;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 */
public class SqlHelper {
    public void insert(SeachHistoryData seachHistoryData){
        seachHistoryData.save();
    }
    public void deleteAll(){
        new Delete().from(SeachHistoryData.class).execute();
    }
    public List<SeachHistoryData> query(){
        List<SeachHistoryData> list=new Select().from(SeachHistoryData.class).execute();
       /* List<String> list1=new ArrayList<>();
        SeachHistoryData seachHistoryData;
        if (list!=null){
            for (int i = 0; i < list.size(); i++) {
                seachHistoryData=list.get(i);
                list1.add(seachHistoryData.getHistory());
            }
        }*/

        return list;
    }
    public void deleteSomeone(SeachHistoryData seachHistoryData){
        seachHistoryData.delete();
    }
}
