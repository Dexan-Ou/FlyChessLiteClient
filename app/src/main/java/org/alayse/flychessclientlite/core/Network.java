package org.alayse.flychessclientlite.core;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tencent.mars.sample.wrapper.remote.MarsServiceProxy;
import com.tencent.mars.sample.wrapper.remote.NanoMarsTaskWrapper;

import org.alayse.flychessclientlite.proto.Main;
import org.alayse.flychessclientlite.task.CreateRoomMessageTask;
import org.alayse.flychessclientlite.task.HelloMessageTask;
import org.alayse.flychessclientlite.task.JoinRoomMessageTask;
import org.alayse.flychessclientlite.task.LeftRoomMessageTask;
import org.alayse.flychessclientlite.task.SendActionMessageTask;

import java.util.LinkedList;
import java.util.List;

public class Network {
    private static final String TAG = "Flychess.Network";

    public static final String SERVER_HOST = "45.77.232.49";
    public static final int SERVER_SHORT_PORT = 8080;
    public static final int SERVER_LONG_PORT = 8081;
    private static final Network inst = new Network();

    private NanoMarsTaskWrapper<Main.RoomListRequest, Main.RoomListResponse> taskGetRoomList = null;
    private CreateRoomMessageTask createRoomMessageTask = null;
    private JoinRoomMessageTask joinRoomMessageTask = null;
    private LeftRoomMessageTask leftRoomMessageTask = null;
    private SendActionMessageTask sendActionMessageTask = null;
    private HelloMessageTask helloMessageTask = null;

    private String access_token = "POI";

    public static Network getInstance(){
        return inst;
    }

    public void scanRoomList(final NetworkInterface nif){
        if (taskGetRoomList != null){
            MarsServiceProxy.cancel(taskGetRoomList);
        }
        taskGetRoomList = new NanoMarsTaskWrapper<Main.RoomListRequest, Main.RoomListResponse>(
                new Main.RoomListRequest(),
                new Main.RoomListResponse()
        ) {

            private List<Main.Room> dataList = new LinkedList<>();

            @Override
            public void onPreEncode(Main.RoomListRequest req) {
                req.type = Main.RoomListRequest.EMPTY;
            }

            @Override
            public void onPostDecode(Main.RoomListResponse response) {

            }

            @Override
            public void onTaskEnd(int errType, int errCode) {
                Log.i("Flychess.Network", response.list.length + "");
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        Log.i("Flychess.Network","RUN");
                        if (response != null) {
                            for (Main.Room room : response.list) {
                                Main.Room poi = new Main.Room();
                                poi.name = room.name;
                                poi.player = room.player;
                                poi.playerlimit = room.playerlimit;
                                dataList.add(poi);
                            }
                        }

                        nif.updateRoomList(dataList);
                    }
                });
            }

        };

        MarsServiceProxy.send(taskGetRoomList.setHttpRequest(SERVER_HOST, "/game/getroomlist"));
    }

    public void createRoom(final NetworkInterface nif, String userName, String roomName, int playerLimit, int botNum){
        Log.i(TAG, "createRoom");
        if (createRoomMessageTask != null)
            MarsServiceProxy.cancel(createRoomMessageTask);
        createRoomMessageTask = new CreateRoomMessageTask(this.access_token, userName, roomName, playerLimit, botNum)
                .onError(new Runnable() {
                    @Override
                    public void run() {
                        nif.createRoomCallback(false);
                    }
                })
                .onOK(new Runnable() {
                    @Override
                    public void run() {
                        nif.createRoomCallback(true);
                    }
                });
        MarsServiceProxy.send(createRoomMessageTask);
    }

    public void joinRoom(final NetworkInterface nif, String userName, String roomName){
        Log.i(TAG, "joinRoom");
        if (joinRoomMessageTask != null)
            MarsServiceProxy.cancel(joinRoomMessageTask);
        joinRoomMessageTask = new JoinRoomMessageTask(this.access_token, userName, roomName)
                .onError(new Runnable() {
                    @Override
                    public void run() {
                        nif.joinRoomCallback(false, null);
                    }
                })
                .onOK(new Runnable() {
                    @Override
                    public void run() {
                        nif.joinRoomCallback(true, joinRoomMessageTask.getMsg());
                    }
                });
        MarsServiceProxy.send(joinRoomMessageTask);
    }

    public void leftRoom(final NetworkInterface nif, String userName){
        Log.i(TAG, "leftRoom");
        if (leftRoomMessageTask != null)
            MarsServiceProxy.cancel(leftRoomMessageTask);
        leftRoomMessageTask = new LeftRoomMessageTask(this.access_token, userName)
                .onError(new Runnable() {
                    @Override
                    public void run() {
                        nif.leftRoomCallback(false);
                    }
                })
                .onOK(new Runnable() {
                    @Override
                    public void run() {
                        nif.leftRoomCallback(true);
                    }
                });
        MarsServiceProxy.send(leftRoomMessageTask);
    }

    public void sendAction(final NetworkInterface nif, String userName, String content, String roomName){
        Log.i(TAG, "sendAction");
        if (sendActionMessageTask != null)
            MarsServiceProxy.cancel(sendActionMessageTask);
        sendActionMessageTask = new SendActionMessageTask(this.access_token, userName, content, roomName)
                .onError(new Runnable() {
                    @Override
                    public void run() {
                        nif.sendActionCallback(false);
                    }
                })
                .onOK(new Runnable() {
                    @Override
                    public void run() {
                        nif.sendActionCallback(true);
                    }
                });
        MarsServiceProxy.send(sendActionMessageTask);
    }

    public void hello(){
        Log.i(TAG, "hello");
        if (helloMessageTask != null)
            MarsServiceProxy.cancel(helloMessageTask);
        final NetworkInterface nif = new NetworkInterface() {
            @Override
            public void helloCallback(String access_token) {
                Network.getInstance().access_token = access_token;
                Log.d(access_token, "Connect Successful");
            }
        };
        helloMessageTask = new HelloMessageTask(this.access_token)
                .setCallback(new Runnable() {
                    @Override
                    public void run() {
                        nif.helloCallback(helloMessageTask.getAccess_token());
                    }
                });
        MarsServiceProxy.send(helloMessageTask);
    }

}