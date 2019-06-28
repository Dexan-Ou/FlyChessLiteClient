package org.alayse.flychessclientlite.task;

import android.os.Handler;
import android.os.Looper;

import com.tencent.mars.sample.wrapper.TaskProperty;
import com.tencent.mars.sample.wrapper.remote.NanoMarsTaskWrapper;

import org.alayse.flychessclientlite.core.Network;
import org.alayse.flychessclientlite.proto.Main;

@TaskProperty(
        host = Network.SERVER_HOST,
        path = "/game/leftroom",
        cmdID = Main.CMD_ID_LEFTROOM,
        longChannelSupport = true,
        shortChannelSupport = false
)
public class LeftRoomMessageTask extends NanoMarsTaskWrapper<Main.JoinRoomRequest, Main.MsgResponse> {
    private Runnable callback = null;
    private Runnable onOK = null;
    private Runnable onError = null;
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    public LeftRoomMessageTask(String access_token, String userName){
        super(new Main.JoinRoomRequest(), new Main.MsgResponse());
        request.accessToken = access_token;
        request.user = userName;
        request.roomname = "LEFT";
    }

    @Override
    public void onPreEncode(Main.JoinRoomRequest request) {
    }

    @Override
    public void onPostDecode(Main.MsgResponse response) {
        if (response.retcode == Main.MsgResponse.ERR_OK){
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


    public LeftRoomMessageTask onOK(Runnable onOK) {
        this.onOK = onOK;
        return this;
    }

    public LeftRoomMessageTask onError(Runnable onError) {
        this.onError = onError;
        return this;
    }
}
