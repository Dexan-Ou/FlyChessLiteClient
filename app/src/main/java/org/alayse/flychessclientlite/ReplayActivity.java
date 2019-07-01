package org.alayse.flychessclientlite;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReplayActivity extends AppCompatActivity {
    List<Map<String, String>> replayList;
    SimpleAdapter replayListAdapter;
    private static String REPLAY_ROOT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.replays);
        REPLAY_ROOT = getFilesDir().getAbsolutePath() + "/replay";

        //寻找重播目录下的文件
        File file = new File(REPLAY_ROOT);
        File[] files = file.listFiles();
        if (files == null){
            Toast.makeText(getApplicationContext(), "空目录", Toast.LENGTH_SHORT).show();
        }

        replayList = new ArrayList<>();
        for(int i=0; i<files.length; i++){
            Map<String, String> map = new HashMap<>();
            String[] temp = files[i].getPath().split("/");
            map.put("name", temp[temp.length-1]);
            replayList.add(map);
        }

        final ListView listView = (ListView)findViewById(R.id.Replay_View);
        replayListAdapter = new SimpleAdapter(this,replayList, R.layout.replay, new String[]{"name"}, new int[]{R.id.replay_name});
        listView.setAdapter(replayListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //重播函数
                String replayFile = replayList.get(position).get("name");
                Toast.makeText(getApplicationContext(), replayFile, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("room", "replay");
                intent.putExtra("user", "replay");
                intent.putExtra("isReplay", true);
                intent.putExtra("replayFile", replayFile);
                intent.setClass(ReplayActivity.this, FlyingChessActivity.class);
                startActivity(intent);
            }
        });

        TextView deleteReplay = findViewById(R.id.delete_replay);
        deleteReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(REPLAY_ROOT);
                File[] files = file.listFiles();
                for(int i=0; i<files.length; i++){
                    File to_del = files[i];
                    to_del.delete();
                }
                replayList.clear();
                replayListAdapter.notifyDataSetChanged();
            }
        });
    }
}
