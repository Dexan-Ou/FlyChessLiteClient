package org.alayse.flychessclientlite;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tencent.mars.app.AppLogic;
import com.tencent.mars.sample.wrapper.remote.MarsServiceProxy;
import com.tencent.mars.sample.wrapper.service.DebugMarsServiceProfile;
import com.tencent.mars.sample.wrapper.service.MarsServiceNative;
import com.tencent.mars.sample.wrapper.service.MarsServiceProfile;
import com.tencent.mars.sample.wrapper.service.MarsServiceProfileFactory;
import com.tencent.mars.stn.StnLogic;

import org.alayse.flychessclientlite.core.Network;

import java.util.Random;

public class FlyChessApplication extends Application {

    private static final String TAG = "Flychess.Application";
    private static final String TARGET_HOST = Network.SERVER_HOST;
    private static final int TARGET_SHORT_PORT = Network.SERVER_SHORT_PORT;
    private static final int TARGET_LONG_PORT = Network.SERVER_LONG_PORT;

    private static Context context;

    public static AppLogic.AccountInfo accountInfo = new AppLogic.AccountInfo(
            new Random(System.currentTimeMillis() / 1000).nextInt(), "anonymous");

    public static volatile boolean hasSetUserName = false;

    private static class SampleMarsServiceProfile extends DebugMarsServiceProfile {

        @Override
        public String longLinkHost() {
            return TARGET_HOST;
        }

        @Override
        public int[] longLinkPorts() {
            return new int[]{TARGET_LONG_PORT};
        }

        @Override
        public int shortLinkPort() {
            return TARGET_SHORT_PORT;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        System.loadLibrary("c++_shared");

        StnLogic.setBackupIPs(TARGET_HOST, new String[]{"127.0.0.1"});

        MarsServiceNative.setProfileFactory(new MarsServiceProfileFactory() {
            @Override
            public MarsServiceProfile createMarsServiceProfile() {
                return new SampleMarsServiceProfile();
            }
        });

        // NOTE: MarsServiceProxy is for client/caller
        // Initialize MarsServiceProxy for local client, can be moved to other place
        MarsServiceProxy.init(this, getMainLooper(), null);
        MarsServiceProxy.inst.accountInfo = accountInfo;

        Log.i(TAG, "application started");
    }

    @Override
    public void onTerminate() {
        Log.i(TAG, "application terminated");

        super.onTerminate();

    }

    public static Context getContext() {
        return context;
    }
}
