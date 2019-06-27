package org.alayse.flychessclientlite.task;

import android.os.Handler;
import android.os.Looper;

import com.tencent.mars.sample.wrapper.TaskProperty;
import com.tencent.mars.sample.wrapper.remote.NanoMarsTaskWrapper;

import org.alayse.flychessclientlite.core.Network;
import org.alayse.flychessclientlite.proto.Main;

@TaskProperty(
        host = Network.SERVER_HOST,
        path = "/game/joinroom",
        cmdID = Main.CMD_ID_JOINROOM,
        longChannelSupport = true,
        shortChannelSupport = false
)
public class JoinRoomMessageTask extends NanoMarsTaskWrapper<Main.JoinRoomRequest, Main.MsgResponse> {
    private Runnable callback = null;
    private Runnable onOK = null;
    private Runnable onError = null;
    private String msg = null;
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    public JoinRoomMessageTask(String userName, String roomName){
        super(new Main.JoinRoomRequest(), new Main.MsgResponse());

        request.user = userName;
        request.roomname = roomName;
    }

    public String getMsg(){
        return this.msg;
    }

    @Override
    public void onPreEncode(Main.JoinRoomRequest request) {
    }

    @Override
    public void onPostDecode(Main.MsgResponse response) {
        if (response.retcode == Main.MsgResponse.ERR_OK){
            callback = onOK;
            this.msg = response.errmsg;
        }
        else{
            callback = onError;
        }
    }

    @Override
    public void onTaskEnd(int errType, int errCode) {
        uiHandler.post(callback);
    }


    public JoinRoomMessageTask onOK(Runnable onOK) {
        this.onOK = onOK;
        return this;
    }

    public JoinRoomMessageTask onError(Runnable onError) {
        this.onError = onError;
        return this;
    }
}
