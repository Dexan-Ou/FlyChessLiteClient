package org.alayse.flychessclientlite.core;

import org.alayse.flychessclientlite.proto.Main;

import java.util.List;

public abstract class NetworkInterface {
    public void updateRoomList(List<Main.Room> list){}
    public void createRoomCallback(boolean ret){}
    public void joinRoomCallback(boolean ret, String msg){}
    public void leftRoomCallback(boolean ret){}
    public void sendActionCallback(boolean ret){}
    public void helloCallback(String access_token){}
}