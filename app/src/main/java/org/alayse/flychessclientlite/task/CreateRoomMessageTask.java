package org.alayse.flychessclientlite.task;

import android.os.Handler;
import android.os.Looper;

import com.tencent.mars.sample.wrapper.TaskProperty;
import com.tencent.mars.sample.wrapper.remote.NanoMarsTaskWrapper;

import org.alayse.flychessclientlite.core.Network;
import org.alayse.flychessclientlite.proto.Main;

@TaskProperty(
        host = Network.SERVER_HOST,
        path = "/game/createroom",
        cmdID = Main.CMD_ID_CREATEROOM,
        longChannelSupport = true,
        shortChannelSupport = false
)
public class CreateRoomMessageTask extends NanoMarsTaskWrapper<Main.CreateRoomRequest, Main.MsgResponse> {
    private Runnable callback = null;
    private Runnable onOK = null;
    private Runnable onError = null;
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    public CreateRoomMessageTask(String userName, String roomName, int playerLimit){
        super(new Main.CreateRoomRequest(), new Main.MsgResponse());

        request.user = userName;
        request.roomname = roomName;
        request.playerlimit = playerLimit;
    }

    @Override
    public void onPreEncode(Main.CreateRoomRequest request) {
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


    public CreateRoomMessageTask onOK(Runnable onOK) {
        this.onOK = onOK;
        return this;
    }

    public CreateRoomMessageTask onError(Runnable onError) {
        this.onError = onError;
        return this;
    }
}
