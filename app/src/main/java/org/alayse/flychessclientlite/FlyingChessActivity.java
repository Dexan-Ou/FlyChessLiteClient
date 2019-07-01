package org.alayse.flychessclientlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.content.*;
import android.util.*;
import org.alayse.flychessclientlite.core.*;

public class FlyingChessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flyingchess);

        Intent intent = getIntent();
        FlyingChessView v = (FlyingChessView)findViewById(R.id.FlyingChessView);
        String room = intent.getStringExtra("room"), user = intent.getStringExtra("user"), replayFile = intent.getStringExtra("replayFile");
        boolean isReplay = intent.getBooleanExtra("isReplay", false);
        int color = intent.getIntExtra("color", 0);
        v.setPlayer(color);
        v.setNextPlayer((color==0)?1:0);
        
        v.setRoom(room);
        v.setUser(user);
        v.setIsReplay(isReplay);
        v.setReplayFile(replayFile);
        Log.e("Size", "" + ObserveCore.getInstance().getSize());
        v.startConnect();
    }
}