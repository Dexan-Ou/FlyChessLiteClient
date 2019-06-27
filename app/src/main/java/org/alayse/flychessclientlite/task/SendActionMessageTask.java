package org.alayse.flychessclientlite.task;

import android.os.Handler;
import android.os.Looper;

import com.tencent.mars.sample.wrapper.TaskProperty;
import com.tencent.mars.sample.wrapper.remote.NanoMarsTaskWrapper;

import org.alayse.flychessclientlite.core.Network;
import org.alayse.flychessclientlite.proto.Main;
import org.alayse.flychessclientlite.proto.game.Game;

@TaskProperty(
        host = Network.SERVER_HOST,
        path = "/game/sendaction",
        cmdID = Main.CMD_ID_SEND_ACTION,
        longChannelSupport = true,
        shortChannelSupport = false
)
public class SendActionMessageTask extends NanoMarsTaskWrapper<Game.SendActionRequest, Game.SendActionResponse> {
    private Runnable callback = null;
    private Runnable onOK = null;
    private Runnable onError = null;
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    public SendActionMessageTask(String userName, String content, String roomName){
        super(new Game.SendActionRequest(), new Game.SendActionResponse());

        request.from = userName;
        request.content = content;
        request.room = roomName;
    }

    @Override
    public void onPreEncode(Game.SendActionRequest request) {
    }

    @Override
    public void onPostDecode(Game.SendActionResponse response) {
        if (response.errCode == Game.SendActionResponse.ERR_OK){
            callback = onOK;
        }
        else{
            callback = onError;
        }
    }

    @Override
    public void onTaskEnd(int errType, int errCode) {
        uiHandler.post(callback);
    }


    public SendActionMessageTask onOK(Runnable onOK) {
        this.onOK = onOK;
        return this;
    }

    public SendActionMessageTask onError(Runnable onError) {
        this.onError = onError;
        return this;
    }
}
