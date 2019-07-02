package org.alayse.flychessclientlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.tencent.mars.sample.utils.print.BaseConstants;
import com.tencent.mars.sample.wrapper.remote.MarsServiceProxy;

import org.alayse.flychessclientlite.core.MainService;
import org.alayse.flychessclientlite.core.Network;
import org.alayse.flychessclientlite.core.NetworkInterface;
import org.alayse.flychessclientlite.proto.Main;

import java.util.*;
import java.text.*;
import android.util.*;

public class RoomActivity extends AppCompatActivity {
    private TextView createServer;
    private EditText roomPlayerLimit, roomBot;
    private ImageView reflesh;
    private String playerName, roomName;

    List<Map<String, String>> roomList;
    SimpleAdapter roomListAdapter;

    NetworkInterface networkInterface = new NetworkInterface() {
        @Override
        public void updateRoomList(List<Main.Room> list){
            roomList.clear();
            for (int i = 0; i < list.size(); i++){
                Map<String, String> map = new HashMap<>();
                map.put("name",list.get(i).name);
                map.put("player", list.get(i).player + "");
                map.put("playerlimit", list.get(i).playerlimit + "");
                roomList.add(map);
            }
            roomListAdapter.notifyDataSetChanged();
        }

        @Override
        public void joinRoomCallback(boolean ret, String msg) {
            if (!ret){
                Toast.makeText(getApplicationContext(), "加入房间失败", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.d("Join_Room", "Successful Color=" + msg);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss");
                Intent intent = new Intent();
                intent.putExtra("room", roomName);
                intent.putExtra("user", playerName);
                intent.putExtra("isReplay", false);
                intent.putExtra("color", Integer.parseInt(msg));
                Date date = new Date(System.currentTimeMillis());
                intent.putExtra("replayFile", simpleDateFormat.format(date) + ".txt");
                intent.setClass(RoomActivity.this, FlyingChessActivity.class);
                RoomActivity.this.finish();
                startActivity(intent);
            }
        }

        @Override
        public void createRoomCallback(boolean ret) {
            if(ret){
                Log.d("Create Room", "Successful");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日-HH:mm:ss");
                Intent intent = new Intent();
                intent.putExtra("room", roomName);
                intent.putExtra("user", playerName);
                intent.putExtra("isReplay", false);
                intent.putExtra("color", 0);
                Date date = new Date(System.currentTimeMillis());
                intent.putExtra("replayFile", simpleDateFormat.format(date) + ".txt");
                intent.setClass(RoomActivity.this, FlyingChessActivity.class);
                RoomActivity.this.finish();
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "创建房间失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void leftRoomCallback(boolean ret) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_server_view);
        roomPlayerLimit = findViewById(R.id.room_player_limit);
        roomBot = findViewById(R.id.room_bot);
        createServer = (TextView) findViewById(R.id.create_home);
        playerName = MainActivity.getPlayerName();
        roomName = MainActivity.getPlayerName() + "的房间";

        Network.getInstance().hello();
        createServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int limit, bot;
                limit = Integer.parseInt(roomPlayerLimit.getText().toString());
                bot = Integer.parseInt(roomBot.getText().toString());

                Network.getInstance().createRoom(networkInterface, playerName, roomName, limit);
            }
        });

        reflesh = findViewById(R.id.reflesh);
        reflesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Network.getInstance().scanRoomList(networkInterface);
            }
        });

        roomList = new ArrayList<>();
        ListView listView = (ListView)findViewById(R.id.Server_ListView);
        roomListAdapter = new SimpleAdapter(this, roomList, R.layout.room_list, new String[]{"name", "player", "playerlimit"}, 
                                            new int[]{R.id.Room_name, R.id.Room_player, R.id.Room_playerlimit});
        listView.setAdapter(roomListAdapter);
        listView.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //加入房间函数
                Network.getInstance().joinRoom(networkInterface, playerName, roomList.get(position).get("name"));
            }
        });
        Network.getInstance().scanRoomList(networkInterface);
    }
}