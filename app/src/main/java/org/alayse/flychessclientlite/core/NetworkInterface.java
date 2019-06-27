package org.alayse.flychessclientlite.core;

import org.alayse.flychessclientlite.proto.Main;

import java.util.List;

public abstract class NetworkInterface {
    abstract public void updateRoomList(List<Main.Room> list);
    abstract public void createRoomCallback(boolean ret);
    abstract public void joinRoomCallback(boolean ret, String msg);
    abstract public void leftRoomCallback(boolean ret);
    abstract public void sendActionCallback(boolean ret);
}
