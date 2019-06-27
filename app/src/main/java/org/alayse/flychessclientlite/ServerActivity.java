package org.alayse.flychessclientlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.tencent.mars.sample.utils.print.BaseConstants;
import com.tencent.mars.sample.wrapper.remote.MarsServiceProxy;

import org.alayse.flychessclientlite.core.MainService;
import org.alayse.flychessclientlite.core.Network;
import org.alayse.flychessclientlite.core.NetworkInterface;
import org.alayse.flychessclientlite.proto.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerActivity extends AppCompatActivity {
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
        public void createRoomCallback(boolean ret) {

        }

        @Override
        public void joinRoomCallback(boolean ret, String msg) {

        }

        @Override
        public void leftRoomCallback(boolean ret) {

        }

        @Override
        public void sendActionCallback(boolean ret) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        final MainService mainService = new MainService();
        MarsServiceProxy.setOnPushMessageListener(BaseConstants.PUSHMSG_CMDID, mainService);

        roomList = new ArrayList<>();
        ListView listView = (ListView)findViewById(R.id.Server_ListView);
        roomListAdapter = new SimpleAdapter(this,roomList,R.layout.list_item,new String[]{"name","player","playerlimit"},new int[]{R.id.Room_name,R.id.Room_player,R.id.Room_playerlimit});
        listView.setAdapter(roomListAdapter);
        Network.getInstance().scanRoomList(networkInterface);
        Button bScanRoom = (Button)findViewById(R.id.Server_ScanRoom);
        bScanRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Network.getInstance().scanRoomList(networkInterface);
            }
        });
        Button bCreateRoom = (Button)findViewById(R.id.Server_CreateRoom);
        bCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Network.getInstance().createRoom(networkInterface,"test","test",4);
            }
        });

        Network.getInstance().joinRoom(networkInterface,"someone","someroom");
    }
}
