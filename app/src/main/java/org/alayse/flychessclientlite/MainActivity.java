package org.alayse.flychessclientlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.alayse.flychessclientlite.core.ObserveCore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Flychess.Main";

    private static String playerName = null;
    private static Context context;
    private static MainActivity instance;
    private ImageView local, server;
    private ImageView replay;

    private EditText editText;
    private ImageView ok;

    private RelativeLayout inputName;

    private LinearLayout main;

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == local) {
                Toast.makeText(getContext(),"功能尚未开放",Toast.LENGTH_SHORT).show();
            } 
            else if (v == server) {
                startServerGame();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        instance = this;
        super.onCreate(savedInstanceState);
        ObserveCore.getInstance().getSize();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        replay = (ImageView) findViewById(R.id.replay);
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ReplayActivity.class);
                startActivity(intent);
            }
        });

        editText = (EditText) findViewById(R.id.editText);
        editText.requestFocus();
        main = (LinearLayout) findViewById(R.id.main);
        main.setVisibility(View.INVISIBLE);
        inputName = (RelativeLayout) findViewById(R.id.input_name);
        ok = (ImageView) findViewById(R.id.imageView4);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                if (name.equals("")||name.equals(" ")){
                    Toast.makeText(getContext(), "你并没有输入", Toast.LENGTH_SHORT).show();
                }
                else{
                    inputName.setVisibility(View.INVISIBLE);
                    LinearLayout linearLayout = (LinearLayout) inputName.getParent();
                    linearLayout.removeView(inputName);
                    main.setVisibility(View.VISIBLE);
                    playerName = name;
                }
            }
        });
        local = (ImageView) findViewById(R.id.local);
        server = (ImageView) findViewById(R.id.server);

        local.setOnClickListener(listener);
        server.setOnClickListener(listener);
    }

    private void startServerGame() {
        Intent indent = new Intent();
        indent.setClass(this, RoomActivity.class);
        startActivity(indent);
    }

    @Override
    protected void onDestroy() {
        System.out.println("MainActivity destoryed");
        instance = null;
        super.onDestroy();
    }

    public static Context getContext() {
        return context;
    }
    public static String getPlayerName(){
        return playerName;
    }
}   
