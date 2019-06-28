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
        path = "/game/hello",
        cmdID = Main.CMD_ID_HELLO,
        longChannelSupport = true,
        shortChannelSupport = false
)
public class HelloMessageTask extends NanoMarsTaskWrapper<Main.HelloRequest, Main.HelloResponse> {
    private Runnable callback = null;
    private String access_token = null;
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    public HelloMessageTask(String access_token){
        super(new Main.HelloRequest(), new Main.HelloResponse());
        request.accessToken = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    @Override
    public void onPreEncode(Main.HelloRequest request) {
    }

    @Override
    public void onPostDecode(Main.HelloResponse response) {
        this.access_token = response.accessToken;
    }

    @Override
    public void onTaskEnd(int errType, int errCode) {
        uiHandler.post(callback);
    }


    public HelloMessageTask setCallback(Runnable callback) {
        this.callback = callback;
        return this;
    }
}
