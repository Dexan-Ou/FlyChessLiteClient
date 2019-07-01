package org.alayse.flychessclientlite.core;

import android.content.Intent;
import android.util.Log;

import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.tencent.mars.sample.wrapper.remote.PushMessage;

import org.alayse.flychessclientlite.FlyChessApplication;
import org.alayse.flychessclientlite.proto.game.Messagepush;
import org.alayse.flychessclientlite.utils.Constants;

public class MessageHandler {
    public static String TAG = MessageHandler.class.getSimpleName();

    public boolean handleRecvMessage(PushMessage pushMessage) {

        switch (pushMessage.cmdId) {
            case Constants.PUSHCMD: {
                try {
                    Messagepush.MessagePush message = Messagepush.MessagePush.parseFrom(pushMessage.buffer);
                    Intent intent = new Intent();
                    intent.setAction(Constants.PUSHACTION);
                    intent.putExtra(Constants.intentMsgRoomName, message.room);
                    intent.putExtra(Constants.intentMsgContent, message.content);
                    intent.putExtra(Constants.intentMsgNextPlayer, message.nextplayer);
                    FlyChessApplication.getContext().sendBroadcast(intent);
                } catch (InvalidProtocolBufferNanoException e) {
                    Log.e(TAG, "%s", e);
                }
            }
            return true;
            default:
                break;
        }

        return false;
    }
}
