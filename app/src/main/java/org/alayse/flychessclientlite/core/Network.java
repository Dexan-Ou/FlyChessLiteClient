package org.alayse.flychessclientlite.core;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.tencent.mars.sample.wrapper.remote.MarsServiceProxy;
import com.tencent.mars.sample.wrapper.remote.NanoMarsTaskWrapper;

import org.alayse.flychessclientlite.proto.Main;
import org.alayse.flychessclientlite.task.CreateRoomMessageTask;
import org.alayse.flychessclientlite.task.JoinRoomMessageTask;
import org.alayse.flychessclientlite.task.LeftRoomMessageTask;
import org.alayse.flychessclientlite.task.SendActionMessageTask;

import java.util.LinkedList;
import java.util.List;

public class Network {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_SHORT_PORT = 8080;
    public static final int SERVER_LONG_PORT = 8081;
    private static final Network inst = new Network();

    private NanoMarsTaskWrapper<Main.RoomListRequest, Main.RoomListResponse> taskGetRoomList = null;

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
                req.type = Main.RoomListRequest.ALL;
            }

            @Override
            public void onPostDecode(Main.RoomListResponse response) {

            }

            @Override
            public void onTaskEnd(int errType, int errCode) {
                Log.i("Flychess.Network",response.list.length + "");
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

    public void createRoom(final NetworkInterface nif, String userName, String roomName, int playerLimit){
        MarsServiceProxy.send(new CreateRoomMessageTask(userName,roomName,playerLimit)
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
            }));
    }

    public void joinRoom(final NetworkInterface nif, String userName, String roomName){
        final JoinRoomMessageTask joinRoomMessageTask = new JoinRoomMessageTask(userName, roomName);
        MarsServiceProxy.send(joinRoomMessageTask
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
                }));
    }

    public void leftRoom(final NetworkInterface nif, String userName){
        MarsServiceProxy.send(new LeftRoomMessageTask(userName)
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
                }));
    }

    public void sendAction(final NetworkInterface nif, String userName, String content, String roomName){
        MarsServiceProxy.send(new SendActionMessageTask(userName, content, roomName)
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
                }));
    }

}
