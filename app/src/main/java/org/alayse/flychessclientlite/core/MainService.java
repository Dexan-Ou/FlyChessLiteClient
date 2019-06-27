package org.alayse.flychessclientlite.core;

import com.tencent.mars.sample.wrapper.remote.PushMessage;
import com.tencent.mars.sample.wrapper.remote.PushMessageHandler;

import java.util.concurrent.LinkedBlockingQueue;

public class MainService implements PushMessageHandler {

    private Thread recvThread;

    private LinkedBlockingQueue<PushMessage> pushMessages = new LinkedBlockingQueue<>();

    private MessageHandler handler = new MessageHandler();

    public MainService() {
        this.start();
    }

    public void start() {
        if (recvThread == null) {
            recvThread = new Thread(pushReceiver, "PUSH-RECEIVER");

            recvThread.start();
        }
    }

    private final Runnable pushReceiver = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    PushMessage pushMessage = pushMessages.take();
                    if (pushMessage != null) {
                        handler.handleRecvMessage(pushMessage);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e1) {
                        //
                    }
                }
            }
        }
    };

    @Override
    public void process(PushMessage message) {
        pushMessages.offer(message);
    }
}
