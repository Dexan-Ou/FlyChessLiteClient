package org.alayse.flychessclientlite.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.alayse.flychessclientlite.FlyChessApplication;
import org.alayse.flychessclientlite.proto.game.Messagepush;
import org.alayse.flychessclientlite.utils.Constants;

import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ObserveCore extends Observable {
    public static String TAG = ObserveCore.class.getSimpleName();

    private static ObserveCore inst = new ObserveCore();

    private BroadcastReceiver receiver = new RecvMessageReceiver();
    private ConcurrentLinkedDeque<Messagepush.MessagePush> dataArrays = new ConcurrentLinkedDeque<>();

    public static ObserveCore getInstance(){
        return inst;
    }

    public ObserveCore(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.PUSHACTION);
        FlyChessApplication.getContext().registerReceiver(receiver, intentFilter);
    }

    @Override
    public void finalize(){
        FlyChessApplication.getContext().unregisterReceiver(receiver);
    }

    private class RecvMessageReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(Constants.PUSHACTION)){
                String roomName = intent.getStringExtra(Constants.intentMsgRoomName);
                String content = intent.getStringExtra(Constants.intentMsgContent);
                synchronized (this){
                    Messagepush.MessagePush entity = new Messagepush.MessagePush();
                    entity.room = roomName;
                    entity.content = content;
                    dataArrays.add(entity);
                }
                setChanged();
                notifyObservers();
            }
        }
    }
}
