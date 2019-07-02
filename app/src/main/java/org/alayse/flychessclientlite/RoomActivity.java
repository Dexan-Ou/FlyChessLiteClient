package org.alayse.flychessclientlite;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
    private Context mContext;

    private TextView createServer;
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

        mContext = this;

        createServer = (TextView) findViewById(R.id.create_home);
        playerName = MainActivity.getPlayerName();
        roomName = MainActivity.getPlayerName() + "的房间";

        Network.getInstance().hello();
        createServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRoomView(playerName, roomName);
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

    private void createRoomView(final String playerName, String roomName){
        LinearLayout createView = (LinearLayout)getLayoutInflater().inflate(R.layout.dialog_createroom,null);
        final EditText inputRoomName = (EditText)createView.findViewById(R.id.room_room_name);
        inputRoomName.setText(roomName);
        final EditText inputPlayerLimit = (EditText)createView.findViewById(R.id.room_player_limit);
        final EditText inputBotNum = (EditText)createView.findViewById(R.id.room_bot);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        AlertDialog alertDialog = dialogBuilder
                .setTitle(mContext.getResources().getString(R.string.serverView_createRoom))
                .setView(createView)
                .setNegativeButton(mContext.getResources().getString(R.string.dialog_createRoom_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                })
                .setPositiveButton(mContext.getResources().getString(R.string.dialog_createRoom_confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (inputRoomName.getText().toString().equals("") || inputPlayerLimit.getText().toString().equals("") || inputBotNum.getText().toString().equals("")) {
                            Toast.makeText(mContext, "内容不能为空！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int playerLimit, bot;
                        playerLimit = Integer.parseInt(inputPlayerLimit.getText().toString());
                        bot = Integer.parseInt(inputBotNum.getText().toString());
                        if (playerLimit <= 0 || bot < 0 || playerLimit > 4 || bot > playerLimit - 1) {
                            Toast.makeText(mContext, "非法输入！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Network.getInstance().createRoom(networkInterface, playerName,inputRoomName.getText().toString(), playerLimit, bot);
                    }
                })
                .create();
        alertDialog.show();
    }
}