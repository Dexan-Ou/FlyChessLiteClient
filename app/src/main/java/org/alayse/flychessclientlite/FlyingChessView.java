package org.alayse.flychessclientlite;

import android.widget.*;
import android.view.*;
import java.util.*;
import java.lang.*;
import android.content.*;
import android.graphics.*;
import android.util.*;
import android.os.*;
import android.app.*;
import java.io.*;
import java.util.concurrent.locks.ReentrantLock;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import org.alayse.flychessclientlite.core.*;
import org.alayse.flychessclientlite.proto.Main;
import org.alayse.flychessclientlite.proto.game.Messagepush;

class Position{
    private float centerX;
    private float centerY;
    private int chessId;
    private int chessNum;

    Position(float a, float b, int c, int d){
        centerX = a;
        centerY = b;
        chessId = c;
        chessNum = d;
    }
    Position(){
        centerX = 0.0f;
        centerY = 0.0f;
        chessId = 0;
        chessNum = 0;
    }

    public float getX(){
        return centerX;
    }
    public float getY(){
        return centerY;
    }
    public int getChessId(){
        return chessId;
    }
    public int getChessNum(){
        return chessNum;
    }

    public boolean decNum(){
        chessNum--;
        return chessNum > 0;
    }
    public void incNum(){
        chessNum++;
    }

    public boolean equals(Position next){
        return centerX==next.getX() && centerY==next.getY();
    }
    public boolean equalsWithColor(Position next){
        return centerX==next.getX() && centerY==next.getY() && chessId==next.getChessId();
    }
}

public class FlyingChessView extends View implements Observer{
    private static double WIDTH; // 宽
    private static double HEIGHT; // 高
    private static double RECT_WIDTH; // 矩形宽
    private static double RECT_LENGTH; // 矩形长
    private static double RADIUS; // 圆半径
    private static double CENTERX; // 棋盘中点横坐标
    private static double CENTERY; // 棋盘中点纵坐标
    private static double BEGINX; // 棋盘左上角横坐标
    private static double BEGINY; // 棋盘左上角纵坐标
    private static double DICE_BEGINX;
    private static double DICE_BEGINY;
    private static double DICE_LENGTH;
    private static double AI_BEGINX;
    private static double AI_BEGINY;
    private static double BACK_BEGINX;
    private static double BACK_BEGINY;
    private static double BUTTON_WIDTH;
    private static double BUTTON_HEIGHT;
    private static double WAIT_WIDTH;
    private static double WAIT_HEIGHT;
    private static double WAIT_BEGINX;
    private static double WAIT_BEGINY;
    private static String REPLAY_ROOT;
    private final static int POSITION_NUM = 96; // 格子总数
    private final static int PLANE_SUM = 16; // 飞机总数

    private Map<String, Integer> Name2Colors;
    private String[] Id2Colors; // 0-Y, 1-R, 2-B, 3-G
    private Paint MyPaint;
    private ArrayList<Position> Positions;
    private ArrayList<Position> PlanePositions;
    private static int[] AirportIds = {76,77,78,79,81,82,83,84,86,87,88,89,91,92,93,94};
    private Map<String, Integer> Direction2Id;
    private int[] Dice2Image;

    private int NowPlayerId;
    private int NextPlayer;
    private String NowRoom;
    private String NowUser;
    private Position PressPlanePosition;
    private Handler UIHandler;
    private int PlaneNum;
    private int NowDice;
    private ArrayList<Position> Flight;
    private boolean IsAI;
    private boolean AIMessageReceive;
    private volatile static boolean IsPlanePress;
    private volatile static boolean IsDicePress;
    private boolean IsReplay;
    private String ReplayFile;
    private boolean IsWait;
    private boolean IsFinish;

    private static NetworkInterface networkInterface;

    public FlyingChessView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ObserveCore.getInstance().addObserver(this);
        initFlyingChess(context);
    }

    // 初始化棋盘参数
    private void initFlyingChess(Context context){
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double width = dm.widthPixels;
        double height = dm.heightPixels;

        WIDTH = width;
        HEIGHT = height;
        RECT_LENGTH = width/8.5;
        RECT_WIDTH = RECT_LENGTH/2;
        RADIUS = RECT_WIDTH*0.7/2;
        BEGINX = 0;
        BEGINY = (height-width)/3;
        CENTERX = BEGINX+width/2;
        CENTERY = BEGINY+width/2;
        DICE_BEGINY = BEGINY+21*width/20;
        DICE_BEGINX = width*3/7;
        DICE_LENGTH = 130;
        BUTTON_WIDTH = 60;
        BUTTON_HEIGHT = 48;
        AI_BEGINX = width-BUTTON_WIDTH*2-30;
        AI_BEGINY = 20;
        BACK_BEGINX = width-BUTTON_WIDTH;
        BACK_BEGINY = 20;
        WAIT_BEGINX = 0;
        WAIT_BEGINY = CENTERY-100;
        WAIT_WIDTH = WIDTH;
        WAIT_HEIGHT = 200;
        REPLAY_ROOT = context.getFilesDir().getAbsolutePath() + "/replay";

        Name2Colors = new HashMap<String, Integer>();
        Name2Colors.put("Yellow", Color.rgb(215, 215, 45));
        Name2Colors.put("Red", Color.RED);
        Name2Colors.put("Blue", Color.BLUE);
        Name2Colors.put("Green", Color.rgb(50, 215, 35));

        Id2Colors = new String[4];
        Id2Colors[0] = "Yellow";
        Id2Colors[1] = "Blue";
        Id2Colors[2] = "Green";
        Id2Colors[3] = "Red";

        Direction2Id = new HashMap<String, Integer>();
        Direction2Id.put("Left", 0);
        Direction2Id.put("Right", 1);
        Direction2Id.put("Up", 2);
        Direction2Id.put("Down", 3);

        Dice2Image = new int[6];
        Dice2Image[0] = R.drawable.dice1;
        Dice2Image[1] = R.drawable.dice2;
        Dice2Image[2] = R.drawable.dice3;
        Dice2Image[3] = R.drawable.dice4;
        Dice2Image[4] = R.drawable.dice5;
        Dice2Image[5] = R.drawable.dice6;

        Positions = null;
        PlanePositions = new ArrayList<Position>();
        Flight = new ArrayList<Position>();
        PressPlanePosition = null;
        PlaneNum = 0;
        NowDice = 0;
        NextPlayer = 1;
        MyPaint = new Paint();
        IsPlanePress = true;
        IsDicePress = false;
        IsWait = true;
        IsAI = false;
        IsReplay = true;
        IsFinish = false;
        AIMessageReceive = true;

        File replayRoot = new File(REPLAY_ROOT);
        if(!replayRoot.exists()){
            Log.d("CreateDiretory", REPLAY_ROOT);
            replayRoot.mkdir();
        }

        networkInterface = new NetworkInterface(){
            @Override
            public void sendActionCallback(boolean ret) {
                Log.d("Receive", "" + ret);
            }

            public void leftRoomCallback(boolean ret){
                Log.d("Back", "" + ret);
            }
        };
    }

    @Override
	public void update(Observable observable, Object data) {
        if(!IsReplay){
            if(Flight.size() > 0){
                Flight.clear();
            }
            ObserveCore core = (ObserveCore)observable;
            Messagepush.MessagePush message = core.getMessage();
            core.popMessage();
            String content = message.content;
            Log.d("Content", content);
            if(content.equals("game start")){
                IsWait = false;
                FlyingChessView.this.postInvalidate();
            }
            else {
                NextPlayer = message.nextplayer;
                if(content.length()>0){
                    int nowChess;
                    String[] activities = message.content.split(";");
                    Position lastPosition, nextPosition;  
                    for(String activity: activities){
                        String[] temp = activity.substring(1, activity.length()-1).split(",");
                        if(temp.length > 2){
                            nowChess = Integer.parseInt(temp[0]);
                            lastPosition = Positions.get(Integer.parseInt(temp[1]));
                            nextPosition = Positions.get(Integer.parseInt(temp[2]));
                            Flight.add(new Position(lastPosition.getX(), lastPosition.getY(), nowChess, 1));
                            Flight.add(new Position(nextPosition.getX(), nextPosition.getY(), nowChess, 1));
                        }
                    }
                    Log.d("FlightSize", "" + Flight.size()); 
                    updatePlanes(Flight);
                }
                else{
                    IsDicePress = false;
                    PressPlanePosition = null;
                    if(IsAI){
                        AIMessageReceive = true;
                    }
                }
                Log.d("NextPlayer", "" + message.nextplayer);
            }
        }
	}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initCanvas(canvas);
        replay();
    }

    private void sendStatus(Position position){
        final Position pos = position;
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    Network.getInstance().hello();
                    String content = "" + (IsAI?"1,":"0,") + (NowDice+1) + "," + generateMessage(pos);
                    Log.d("Send", content);
                    Network.getInstance().sendAction(networkInterface, NowUser, content, NowRoom);
                }
                catch(Exception e){

                }
            }
        }).start();
    }

    // 点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if(!IsFinish){
            int nowX = (int)event.getX(), nowY = (int)event.getY();
            Map<String, Position> result = hasPlane(nowX, nowY);

            // 如果当前点击位置有飞机
            if(result.get("flag").getChessId() == 1 && (!IsPlanePress) && (!IsAI) && (!IsReplay) && NextPlayer == 1){
                Position position = result.get("position");
                IsPlanePress = true;
                Log.d("PlanePressTest", "" + IsPlanePress);
                PressPlanePosition = position;

                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    sendStatus(position);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    PressPlanePosition = null;
                    this.postInvalidate();
                }
            }

            else if(isDicePosition(nowX, nowY) && (!IsDicePress) && (!IsAI) && (!IsReplay) && NextPlayer == 1){
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    updateDice();
                }
            }

            else if(isBackPosition(nowX, nowY)){
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    createDialog("退出", "是否选择退出游戏");
                }
            }

            else if(isAIPosition(nowX, nowY)){
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    createDialog("托管", "是否" + (IsAI?"取消托管":"选择托管"));
                }
            }
        }
        return true;
    }

    // 设置当前玩家ID 0-黄色; 1-蓝色; 2-绿色; 3-红色
    public void setPlayer(int playerId){
        NowPlayerId = playerId;
    }

    public void setNextPlayer(int nextPlayer){
        NextPlayer = nextPlayer;
    }

    public void setRoom(String room){
        NowRoom = room;
    }

    public void setUser(String user){
        NowUser = user;
    }

    public void setIsReplay(boolean isReplay){
        IsReplay = isReplay;
        if(IsReplay){
            IsWait = false;
        }
    }

    public void setIsWait(boolean isWait){
        IsWait = isWait;
    }

    public void setReplayFile(String replayFile){
        ReplayFile = replayFile;
    }

    public void startConnect(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    if(!IsReplay){
                        while(!IsFinish){
                            Network.getInstance().hello();
                            Thread.sleep(60000);
                        }
                    }
                }
                catch(Exception e){
                    Log.e("Connect Error:", "" + e);
                }
            }
        }).start();
    }

    private void updatePlanes(ArrayList<Position> Flight){
        final ArrayList<Position> tempFlight = Flight;
        UIHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 0x123){
                    // 主线程重新绘制
                    FlyingChessView.this.postInvalidate();
                }
                else if(msg.what == 0x124){
                    IsDicePress = false;
                    if(IsAI){
                        AIMessageReceive = true;
                    }
                }
            }
        };

        Thread t = new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                    Message msg = UIHandler.obtainMessage();
                    msg.what = 0x123;
                    UIHandler.sendMessage(msg);
                    Thread.sleep(250);
                    
                    PressPlanePosition = null;
                    Position nextPosition = null;
                    int lastColor;
                    // 测试动画效果，走20步
                    for(int i=1; i<tempFlight.size(); ++i){
                        nextPosition = tempFlight.get(i-1);
                        int index = findPlanePosition(nextPosition);
                        lastColor = nextPosition.getChessId();
                        if(index >= 0){
                            nextPosition = tempFlight.get(i);
                            if(nextPosition.getChessId() == lastColor && (!PlanePositions.get(index).decNum())){
                                PlanePositions.remove(index);
                            }
                            index = findPlanePosition(nextPosition);
                            if(index >= 0){
                                PlanePositions.get(index).incNum();
                            }
                            else{
                                PlanePositions.add(new Position(nextPosition.getX(), nextPosition.getY(), nextPosition.getChessId(), 1));
                            }
                        }
                        savePlanePositions();

                        msg = UIHandler.obtainMessage();
                        msg.what = 0x123;
                        UIHandler.sendMessage(msg);
                        if(i < tempFlight.size()-1){
                            Thread.sleep(250);
                        }
                    }
                    msg = UIHandler.obtainMessage();
                    msg.what = 0x124;
                    UIHandler.sendMessage(msg);
                }
                catch (Exception ex) { 
                    ex.printStackTrace();  
                }
            }   
        });
        t.start();
    }

    private void savePlanePositions(){
        try{
            File file = new File(REPLAY_ROOT, ReplayFile);
            FileOutputStream os = new FileOutputStream(file, true);
            OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8");
            for(int i=0; i<PlanePositions.size(); ++i){
                Position position = PlanePositions.get(i);
                writer.write("" + position.getX() + "," + position.getY() + "," 
                            + position.getChessId() + "," + position.getChessNum());
                if(i < PlanePositions.size()-1){
                    writer.write(";");
                }
                else{
                    writer.write("\r\n");
                }
            }
            writer.close();
            os.close();
        }
        catch (Exception e) {
            Log.e("Save Flight", "" + e);
        }
    }

    private void updateDice(){
        IsDicePress = true;
        IsPlanePress = true;
        UIHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 0x123){
                    // 主线程重新绘制
                    FlyingChessView.this.postInvalidate();
                }
                else if(msg.what == 0x124){
                    IsPlanePress = false;
                    Log.d("Dice", "" + NowDice);
                }
            }
        };

        Thread t = new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                    Message msg = null;
                    for(int i=0; i<80; ++i){
                        NowDice = (int)(Math.random()*6);
                        msg = UIHandler.obtainMessage();
                        msg.what = 0x123;
                        UIHandler.sendMessage(msg);
                        if(i < 79){
                            Thread.sleep(25);
                        }
                    }
                    msg = UIHandler.obtainMessage();
                    msg.what = 0x124;
                    UIHandler.sendMessage(msg);
                }
                catch (Exception ex) { 
                    ex.printStackTrace();  
                }
            }
        });
        t.start();
    }

    private String generateMessage(Position position){
        for(int i=0; i<Positions.size(); ++i){
            if(Positions.get(i).equals(position)){
                return "" + i;
            }
        }
        return "-1";
    }

    private void replay(){
        if(IsReplay){
            IsReplay = false;
            UIHandler = new Handler(Looper.getMainLooper()){
                @Override
                public void handleMessage(Message msg){
                    if(msg.what == 0x123){
                        // 主线程重新绘制
                        FlyingChessView.this.postInvalidate();
                    }
                }
            };

            new Thread(new Runnable() {
                @Override
                public void run(){
                    try {
                        Message msg = null;
                        File file = new File(REPLAY_ROOT, ReplayFile);
                        FileInputStream is = new FileInputStream(file);
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                        String line = null;
                        while((line = bufferedReader.readLine()) != null){
                            String[] planePositions = line.split(";");
                            PlanePositions.clear();
                            for(String position: planePositions){
                                String[] temp = position.split(",");
                                float x = Float.parseFloat(temp[0]), y = Float.parseFloat(temp[1]);
                                int color = Integer.parseInt(temp[2]), num = Integer.parseInt(temp[3]);
                                PlanePositions.add(new Position(x, y, color, num));
                            }
                            msg = UIHandler.obtainMessage();
                            msg.what = 0x123;
                            UIHandler.sendMessage(msg);
                            Thread.sleep(250);
                        }
                        is.close();
                        bufferedReader.close();
                    } 
                    catch (Exception e) {
                        Log.e("", "" + e);
                    }
                }
            }).start();
        }
    }

    // 初始化canvas
    private void initCanvas(Canvas canvas){
        drawBackground(canvas, MyPaint);
        if(!IsWait){
            if(Positions == null){
                Positions = new ArrayList<Position>();
            }
            MyPaint.setStrokeWidth(2);// 设置画笔粗细
            MyPaint.setAntiAlias(true);// 设置抗锯齿
            drawMainChessBoard(canvas, MyPaint);
            drawAllArrows(canvas, MyPaint);
            drawAllAirports(canvas, MyPaint);
            drawAllRoute(canvas, MyPaint);

            float centerX, centerY;
            for(Position position: PlanePositions){
                drawPlane(position.getX(), position.getY(), position.getChessId(), canvas, MyPaint);
            }
            drawPress(canvas);

            drawDice(canvas, MyPaint, NowDice);
        }
        else{
            showDrawable(canvas, MyPaint, R.drawable.wait_background, (float)WAIT_BEGINX, (float)WAIT_BEGINY, (float)WAIT_WIDTH, (float)WAIT_HEIGHT);
        }
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE? Bitmap.Config.ARGB_8888: Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return bitmap;
    }
    
    private void drawBackground(Canvas canvas, Paint paint){
        showDrawable(canvas, paint, R.drawable.background, 0, 0, (float)WIDTH, (float)HEIGHT);
        showDrawable(canvas, paint, R.drawable.ai, (float)AI_BEGINX, (float)AI_BEGINY, (float)BUTTON_WIDTH, (float)BUTTON_HEIGHT);
        showDrawable(canvas, paint, R.drawable.back, (float)BACK_BEGINX, (float)BACK_BEGINY, (float)BUTTON_WIDTH, (float)BUTTON_HEIGHT);
    }

    private void showDrawable(Canvas canvas, Paint paint, int id, float beginX, float beginY, float newWidth, float newHeight){
        Drawable draw = getResources().getDrawable(id);
        int width = draw.getIntrinsicWidth();
        int height = draw.getIntrinsicHeight();
        Bitmap oldBmp = drawableToBitmap(draw);
        Matrix matrix = new Matrix();
        matrix.postScale(newWidth/width, newHeight/height);
        Bitmap newBmp = Bitmap.createBitmap(oldBmp, 0, 0, width, height, matrix, true);
        canvas.drawBitmap(newBmp, beginX, beginY, paint);
    }

    private void createDialog(String title, String content){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final RelativeLayout nowForm = (RelativeLayout)inflater.inflate(R.layout.dialog, null);
        TextView v = (TextView)nowForm.findViewById(R.id.dialog_title);
        v.setText(title);
        v = (TextView)nowForm.findViewById(R.id.dialog_content);
        v.setText(content);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        final String dialogTitle = title;
        AlertDialog myDialog = dialogBuilder
                            .setView(nowForm)
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    myPositiveOnclick(dialogTitle);
                                }
                            })
                            .create();
        myDialog.show();
        myDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(Color.RED);
        myDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                .setTextColor(Color.RED);
    }

    private void AIRun(){
        final Handler AIHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 0x123){
                    // 主线程重新绘制
                    FlyingChessView.this.postInvalidate();
                }
                else if(msg.what == 0x124){
                    IsPlanePress = false;
                    Log.d("Dice", "" + NowDice);
                }
            }
        };

        new Thread(new Runnable(){
            @Override
            public void run(){
                try{
                    while(IsAI){
                        if(NextPlayer == 1 && AIMessageReceive){
                            IsDicePress = true;
                            IsPlanePress = true;
                            Message msg = null;
                            for(int i=0; i<80; ++i){
                                NowDice = (int)(Math.random()*6);
                                msg = AIHandler.obtainMessage();
                                msg.what = 0x123;
                                AIHandler.sendMessage(msg);
                                if(i < 79){
                                    Thread.sleep(25);
                                }
                            }
                            msg = AIHandler.obtainMessage();
                            msg.what = 0x124;
                            AIHandler.sendMessage(msg);
                            while(IsPlanePress);
                            
                            String content = "" + "1," + (NowDice+1) + ",0";
                            Log.d("Send", content);
                            Network.getInstance().sendAction(networkInterface, NowUser, content, NowRoom);
                            AIMessageReceive = false;
                        }
                    }
                }
                catch(Exception e){

                }
            }
        }).start();
    }

    private void myPositiveOnclick(String title){
        if(title.equals("托管")){
            IsAI = !IsAI;
            AIRun();
        }
        else{
            Network.getInstance().leftRoom(networkInterface, NowUser);
            Activity nowActivity = (Activity)getContext();
            Intent intent = new Intent(nowActivity, RoomActivity.class);
            nowActivity.finish();
            nowActivity.startActivity(intent);
        }
    }

    // 画骰子
    private void drawDice(Canvas canvas, Paint paint, int nowDice){
        if(nowDice >= 0 && nowDice < 6){
            Bitmap b = BitmapFactory.decodeResource(getResources(), Dice2Image[nowDice]);
            float dice_beginx = (float)DICE_BEGINX, dice_beginy = (float)DICE_BEGINY;
            canvas.drawBitmap(b, dice_beginx, dice_beginy, paint);
        }
    }

    // 点击事件（点击合理位置时会画一圈黑色边框）
    private void drawPress(Canvas canvas){
        MyPaint.setColor(Color.BLACK);
        MyPaint.setStrokeWidth(4);
        MyPaint.setStyle(Paint.Style.STROKE);
        float centerX, centerY, radius = (float)RADIUS;
        if(PressPlanePosition != null){
            centerX = PressPlanePosition.getX();
            centerY = PressPlanePosition.getY();
            canvas.drawCircle(centerX, centerY, radius, MyPaint);
        }
    }

    // 画所有路径
    private void drawAllRoute(Canvas canvas, Paint paint){
        drawRoute(Positions.get(17).getX(), Positions.get(17).getY(), "Down", 0, canvas, paint);
        drawRoute(Positions.get(30).getX(), Positions.get(30).getY(), "Left", 1, canvas, paint);
        drawRoute(Positions.get(43).getX(), Positions.get(43).getY(), "Up", 2, canvas, paint);
        drawRoute(Positions.get(4).getX(), Positions.get(4).getY(), "Right", 3, canvas, paint);
    }

    // 画所有箭头
    private void drawAllArrows(Canvas canvas, Paint paint){
        drawArrow((float)CENTERX, (float)CENTERY, 0, "Left", canvas, paint);
        drawArrow((float)CENTERX, (float)CENTERY, 1, "Up", canvas, paint);
        drawArrow((float)CENTERX, (float)CENTERY, 2, "Right", canvas, paint);
        drawArrow((float)CENTERX, (float)CENTERY, 3, "Down", canvas, paint);
    }

    // 画所有机场
    private void drawAllAirports(Canvas canvas, Paint paint){
        float beginX = (float)BEGINX, beginY = (float)BEGINY;
        float length = (float)RECT_LENGTH, radius = (float)RADIUS;
        drawRectBlock(beginX+length, beginY+length, 0, canvas, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Name2Colors.get("Yellow")); 
        canvas.drawCircle(beginX+0.25f*length, beginY+2.25f*length, radius, paint);
        addPosition(beginX+0.25f*length, beginY+2.25f*length, 0);

        drawRectBlock(beginX+7.5f*length, beginY+length, 1, canvas, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Name2Colors.get("Blue")); 
        canvas.drawCircle(beginX+6.25f*length, beginY+0.25f*length, radius, paint);
        addPosition(beginX+6.25f*length, beginY+0.25f*length, 1);

        drawRectBlock(beginX+7.5f*length, beginY+7.5f*length, 2, canvas, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Name2Colors.get("Green")); 
        canvas.drawCircle(beginX+8.25f*length, beginY+6.25f*length, radius, paint);
        addPosition(beginX+8.25f*length, beginY+6.25f*length, 2);

        drawRectBlock(beginX+length, beginY+7.5f*length, 3, canvas, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Name2Colors.get("Red")); 
        canvas.drawCircle(beginX+2.25f*length, beginY+8.25f*length, radius, paint);
        addPosition(beginX+2.25f*length, beginY+8.25f*length, 3);
    }

    // 画主要棋盘
    private void drawMainChessBoard(Canvas canvas, Paint paint){
        int totalId = 0;
        float length = (float)RECT_LENGTH, width = (float)RECT_WIDTH;
        float beginX = (float)BEGINX+length, beginY = (float)BEGINY+3*length;

        float[] result;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "UpLeft", true, false, canvas, paint);
        totalId++;
        result = drawRectLine(beginX, beginY, 2, totalId, Direction2Id.get("Right"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 2;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "UpRight", true, false, canvas, paint);
        totalId++;

        beginX += length; beginY -= length;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "DownLeft", true, false, canvas, paint);
        totalId++;
        result = drawRectLine(beginX, beginY, 2, totalId, Direction2Id.get("Up"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 2;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "UpLeft", true, false, canvas, paint);
        totalId++;

        result = drawRectLine(beginX, beginY, 5, totalId, Direction2Id.get("Right"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 5;

        drawTriangle(beginX, beginY, length, (totalId+3)%4, "UpRight", true, false, canvas, paint);
        totalId++;
        result = drawRectLine(beginX, beginY, 2, totalId, Direction2Id.get("Down"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 2;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "DownRight", true, false, canvas, paint);
        totalId++;

        beginX += length; beginY += length;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "UpLeft", true, false, canvas, paint);
        totalId++;
        result = drawRectLine(beginX, beginY, 2, totalId, Direction2Id.get("Right"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 2;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "UpRight", true, false, canvas, paint);
        totalId++;

        result = drawRectLine(beginX, beginY, 5, totalId, Direction2Id.get("Down"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 5;

        drawTriangle(beginX, beginY, length, (totalId+3)%4, "DownRight", true, false, canvas, paint);
        totalId++;
        result = drawRectLine(beginX, beginY, 2, totalId, Direction2Id.get("Left"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 2;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "DownLeft", true, false, canvas, paint);
        totalId++;

        beginX -= length; beginY += length;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "UpRight", true, false, canvas, paint);
        totalId++;
        result = drawRectLine(beginX, beginY, 2, totalId, Direction2Id.get("Down"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 2;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "DownRight", true, false, canvas, paint);
        totalId++;

        result = drawRectLine(beginX, beginY, 5, totalId, Direction2Id.get("Left"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 5;

        drawTriangle(beginX, beginY, length, (totalId+3)%4, "DownLeft", true, false, canvas, paint);
        totalId++;
        result = drawRectLine(beginX, beginY, 2, totalId, Direction2Id.get("Up"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 2;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "UpLeft", true, false, canvas, paint);
        totalId++;

        beginX -= length; beginY -= length;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "DownRight", true, false, canvas, paint);
        totalId++;
        result = drawRectLine(beginX, beginY, 2, totalId, Direction2Id.get("Left"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 2;
        drawTriangle(beginX, beginY, length, (totalId+3)%4, "DownLeft", true, false, canvas, paint);
        totalId++;

        result = drawRectLine(beginX, beginY, 5, totalId, Direction2Id.get("Up"), canvas, paint);
        beginX = result[0]; beginY = result[1];
        totalId += 5;
    }

    // 画2*2矩形块
    private void drawRectBlock(float centerX, float centerY, int nowColor, Canvas canvas, Paint paint){
        float length = (float)RECT_LENGTH;
        drawRect(centerX-length, centerY-length, centerX, centerY, nowColor, true, PlaneNum<PLANE_SUM, canvas, paint);
        drawRect(centerX+length, centerY-length, centerX, centerY, nowColor, true, PlaneNum<PLANE_SUM, canvas, paint);
        drawRect(centerX-length, centerY+length, centerX, centerY, nowColor, true, PlaneNum<PLANE_SUM, canvas, paint);
        drawRect(centerX+length, centerY+length, centerX, centerY, nowColor, true, PlaneNum<PLANE_SUM, canvas, paint);

        Path borderPath = new Path();
        borderPath.moveTo(centerX-length, centerY-length);
        borderPath.lineTo(centerX+length, centerY-length);
        borderPath.lineTo(centerX+length, centerY+length);
        borderPath.lineTo(centerX-length, centerY+length);
        borderPath.lineTo(centerX-length, centerY-length);
        borderPath.close();

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(borderPath, paint);
    }

    // 画一行矩形
    private float[] drawRectLine(float beginX, float beginY, int numRect, int totalId, 
                                int direction, Canvas canvas, Paint paint){
        float length = (float)RECT_LENGTH, width = (float)RECT_WIDTH;
        float topLeftX, topLeftY;
        float xScale[] = {-width, width, 0, 0}, yScale[] = {0, 0, -width, width};
        float topLeftXScale[] = {width, -width, -length, length}, topLeftYScale[] = {length, -length, width, -width};
        for(int i=0; i<numRect; ++i){
            beginX += xScale[direction];
            beginY += yScale[direction];
            topLeftX = beginX+topLeftXScale[direction];
            topLeftY = beginY+topLeftYScale[direction];
            drawRect(topLeftX, topLeftY, beginX, beginY, (totalId+3)%4, true, false, canvas, paint);
            totalId++;
        }
        
        float result[] = new float[2];
        result[0] = beginX;
        result[1] = beginY;
        return result;
    }

    // 画一个矩形
    private void drawRect(float topLeftX, float topLeftY, float bottomRightX, float bottomRightY,
                        int nowColor, boolean withCircle, boolean withPlane, Canvas canvas, Paint paint){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Name2Colors.get(Id2Colors[nowColor]));

        Path rectPath = new Path();
        rectPath.addRect(topLeftX, topLeftY, bottomRightX, bottomRightY, Path.Direction.CCW);

        // 矩形内部是否画圆
        if(withCircle){
            float circleCenterX = (topLeftX+bottomRightX)/2, circleCenterY = (topLeftY+bottomRightY)/2;
            float height = bottomRightY-topLeftY, width = bottomRightX-topLeftX;
            Path circlePath = new Path();
            circlePath.addCircle(circleCenterX, circleCenterY, (float)RADIUS, Path.Direction.CCW);
            rectPath.op(circlePath, Path.Op.XOR);

            addPosition(circleCenterX, circleCenterY, nowColor);
            if(withPlane){
                PlanePositions.add(new Position(circleCenterX, circleCenterY, nowColor, 1));
                PlaneNum++;
            }
        }
        canvas.drawPath(rectPath, paint);
    }

    // 画等腰直角三角形
    private void drawTriangle(float centerX, float centerY, float length, int nowColor, String mode, 
                            boolean withCircle, boolean withPlane, Canvas canvas, Paint paint){
        float pointX1 = centerX, pointY1 = centerY, pointX2 = centerX, pointY2 = centerY;
        float circleCenterX = centerX, circleCenterY = centerY;
        if(mode == "UpLeft"){
            pointX1 -= length;
            pointY2 -= length;
            circleCenterX -= length/4;
            circleCenterY -= length/4;
        }
        else if(mode == "UpRight"){
            pointX1 += length;
            pointY2 -= length;
            circleCenterX += length/4;
            circleCenterY -= length/4;
        }
        else if(mode == "DownLeft"){
            pointX1 -= length;
            pointY2 += length;
            circleCenterX -= length/4;
            circleCenterY += length/4;
        }
        else if(mode == "DownRight"){
            pointX1 += length;
            pointY2 += length;
            circleCenterX += length/4;
            circleCenterY += length/4;
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Name2Colors.get(Id2Colors[nowColor]));
        
        Path trianglePath = new Path();
        trianglePath.moveTo(centerX, centerY);
        trianglePath.lineTo(pointX1, pointY1);
        trianglePath.lineTo(pointX2, pointY2);
        trianglePath.lineTo(centerX, centerY);
        trianglePath.close();

        if(withCircle){
            Path circlePath = new Path();
            circlePath.addCircle(circleCenterX, circleCenterY, (float)RADIUS, Path.Direction.CCW);
            trianglePath.op(circlePath, Path.Op.XOR);
            addPosition(circleCenterX, circleCenterY, nowColor);
        }
        canvas.drawPath(trianglePath, paint);
    }

    // 画箭头
    private void drawArrow(float centerX, float centerY, int nowColor, String mode, Canvas canvas, Paint paint){
        float trianglePointX1 = centerX, trianglePointY1 = centerY, trianglePointX2 = centerX, trianglePointY2 = centerY;
        float rectTopLeftX = centerX, rectTopLeftY = centerY, rectBottomRightX = centerX, rectBottomRightY = centerY;
        float circleCenterX = centerX, circleCenterY = centerY;
        float length = (float)RECT_LENGTH, triangleLength = length*3/4;
        if(mode == "Left"){
            trianglePointX1 -= triangleLength;
            trianglePointY1 -= triangleLength;
            trianglePointX2 -= triangleLength;
            trianglePointY2 += triangleLength;
            rectBottomRightX -= triangleLength;
            rectBottomRightY += length/4;
            rectTopLeftX = rectBottomRightX-5*length/2;
            rectTopLeftY -= length/4;
            circleCenterX -= 2*triangleLength/3;
        }
        else if(mode == "Right"){
            trianglePointX1 += triangleLength;
            trianglePointY1 -= triangleLength;
            trianglePointX2 += triangleLength;
            trianglePointY2 += triangleLength;
            rectBottomRightX += triangleLength;
            rectBottomRightY += length/4;
            rectTopLeftX = rectBottomRightX+5*length/2;
            rectTopLeftY -= length/4;
            circleCenterX += 2*triangleLength/3;
        }
        else if(mode == "Up"){
            trianglePointX1 -= triangleLength;
            trianglePointY1 -= triangleLength;
            trianglePointX2 += triangleLength;
            trianglePointY2 -= triangleLength;
            rectBottomRightX += length/4;
            rectBottomRightY -= triangleLength;
            rectTopLeftX -= length/4;
            rectTopLeftY = rectBottomRightY-5*length/2;
            circleCenterY -= 2*triangleLength/3;
        }
        else if(mode == "Down"){
            trianglePointX1 -= triangleLength;
            trianglePointY1 += triangleLength;
            trianglePointX2 += triangleLength;
            trianglePointY2 += triangleLength;
            rectBottomRightX += length/4;
            rectBottomRightY += triangleLength;
            rectTopLeftX -= length/4;
            rectTopLeftY = rectBottomRightY+5*length/2;
            circleCenterY += 2*triangleLength/3;
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Name2Colors.get(Id2Colors[nowColor]));

        Path trianglePath = new Path();
        trianglePath.moveTo(centerX, centerY);
        trianglePath.lineTo(trianglePointX1, trianglePointY1);
        trianglePath.lineTo(trianglePointX2, trianglePointY2);
        trianglePath.lineTo(centerX, centerY);
        trianglePath.close();

        Path circlePath = new Path();
        circlePath.addCircle(circleCenterX, circleCenterY, (float)RADIUS, Path.Direction.CCW);
        trianglePath.op(circlePath, Path.Op.XOR);
        float tempX = circleCenterX, tempY = circleCenterY;

        Path rectPath = new Path();
        rectPath.addRect(rectTopLeftX, rectTopLeftY, rectBottomRightX, rectBottomRightY, Path.Direction.CCW);
        trianglePath.op(rectPath, Path.Op.UNION);
        circlePath.reset();
        float step = (float)RECT_WIDTH;
        for(int i=0; i<5; ++i){
            if(mode == "Left"){
                if(i > 0){
                    circleCenterX += step;
                }
                else{
                    circleCenterX = rectTopLeftX+step/2;
                }
                circleCenterY = centerY;
            }
            else if(mode == "Right"){
                if(i > 0){
                    circleCenterX -= step;
                }
                else{
                    circleCenterX = rectTopLeftX-step/2;
                }
                circleCenterY = centerY;
            }
            else if(mode == "Up"){
                circleCenterX = centerX;
                if(i > 0){
                    circleCenterY += step;
                }
                else{
                    circleCenterY = rectTopLeftY+step/2;
                }
            }
            else if(mode == "Down"){
                circleCenterX = centerX;
                if(i > 0){
                    circleCenterY -= step;
                }
                else{
                    circleCenterY = rectTopLeftY-step/2;
                }
            }
            circlePath.addCircle(circleCenterX, circleCenterY, (float)RADIUS, Path.Direction.CCW);
            trianglePath.op(circlePath, Path.Op.XOR);
            circlePath.reset();
            addPosition(circleCenterX, circleCenterY, nowColor);
        }
        canvas.drawPath(trianglePath, paint);
        addPosition(tempX, tempY, nowColor);
    }

    // 得到飞机轮廓
    private Path getPlanePath(float circleCenterX, float circleCenterY){
        float radius = (float)RADIUS;
        float beginX = circleCenterX, beginY = circleCenterY-radius;
        Path planePath = new Path();
        planePath.moveTo(beginX, beginY);
        planePath.lineTo(beginX-radius/6, beginY+radius/4);
        planePath.lineTo(circleCenterX-radius/6, circleCenterY);
        planePath.lineTo(circleCenterX-5*radius/6, circleCenterY+radius/4);
        planePath.lineTo(circleCenterX-radius/6, circleCenterY+radius/4);
        planePath.lineTo(circleCenterX-radius/6, circleCenterY+radius/2);
        planePath.lineTo(circleCenterX-radius/2, circleCenterY+3*radius/4);
        planePath.lineTo(circleCenterX+radius/2, circleCenterY+3*radius/4);
        planePath.lineTo(circleCenterX+radius/6, circleCenterY+radius/2);
        planePath.lineTo(circleCenterX+radius/6, circleCenterY+radius/4);
        planePath.lineTo(circleCenterX+5*radius/6, circleCenterY+radius/4);
        planePath.lineTo(circleCenterX+radius/6, circleCenterY);
        planePath.lineTo(beginX+radius/6, beginY+radius/4);
        planePath.lineTo(beginX, beginY);
        planePath.close();

        return planePath;
    }

    // 画飞机
    private void drawPlane(float circleCenterX, float circleCenterY, int nowColor, Canvas canvas, Paint paint){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        Path planePath = getPlanePath(circleCenterX, circleCenterY);
        canvas.drawPath(planePath, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Name2Colors.get(Id2Colors[nowColor]));
        paint.setStrokeWidth(2);
        planePath = getPlanePath(circleCenterX, circleCenterY);
        canvas.drawPath(planePath, paint);
    }

    // 画飞行路径
    private void drawRoute(float fromX, float fromY, String direction, int nowColor, Canvas canvas, Paint paint){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Name2Colors.get(Id2Colors[nowColor]));
        MyPaint.setStrokeWidth(6);

        float width = (float)RECT_WIDTH;
        float xScale[] = {-width/4, width/4, 0, 0}, yScale[] = {0, 0, -width/4, width/4};
        float triangleAXScale[] = {width/4, -width/4, -width/4, width/4}, triangleAYScale[] = {-width/4, width/4, width/4, -width/4};
        float triangleBXScale[] = {width/4, -width/4, width/4, -width/4}, triangleBYScale[] = {width/4, -width/4, width/4, -width/4};
        int directionId = Direction2Id.get(direction);
        float beginX = fromX+xScale[directionId]*2, beginY = fromY+yScale[directionId]*2;

        int i;
        for(i=0; i<4; ++i){
            canvas.drawPoint(beginX, beginY, paint);
            beginX += xScale[directionId];
            beginY += yScale[directionId];
        }
        canvas.drawPoint(beginX, beginY, paint);

        beginX += 2*xScale[directionId];
        beginY += 2*yScale[directionId];
        Path path = new Path();
        path.moveTo(beginX, beginY);
        path.lineTo(beginX+triangleAXScale[directionId], beginY+triangleAYScale[directionId]);
        path.lineTo(beginX+triangleBXScale[directionId], beginY+triangleBYScale[directionId]);
        path.lineTo(beginX, beginY);
        path.close();
        canvas.drawPath(path, paint);

        for(i=0; i<10; ++i){
            beginX += xScale[directionId];
            beginY += yScale[directionId];
            canvas.drawPoint(beginX, beginY, paint);
        }
        canvas.drawPoint(beginX+xScale[directionId], beginY+yScale[directionId], paint);

        beginX += 2*xScale[directionId];
        beginY += 2*yScale[directionId];
        path.reset();
        path.moveTo(beginX, beginY);
        path.lineTo(beginX+triangleAXScale[directionId], beginY+triangleAYScale[directionId]);
        path.lineTo(beginX+triangleBXScale[directionId], beginY+triangleBYScale[directionId]);
        path.lineTo(beginX, beginY);
        path.close();
        canvas.drawPath(path, paint);
        canvas.drawPoint(beginX+xScale[directionId], beginY+yScale[directionId], paint);

        for(i=0; i<3; ++i){
            beginX += xScale[directionId];
            beginY += yScale[directionId];
            canvas.drawPoint(beginX, beginY, paint);
        }
    }

    // 添加一个新坐标
    private void addPosition(float circleCenterX, float circleCenterY, int nowColor){
        if(Positions.size() < POSITION_NUM){
            Positions.add(new Position(circleCenterX, circleCenterY, nowColor, 1));
        }
    }

    private int findPlanePosition(Position nextPosition){
        int i;
        for(i=0; i<PlanePositions.size(); ++i){
            if(PlanePositions.get(i).equalsWithColor(nextPosition)){
                return i;
            }
        }
        return -1;
    }

    private int isInAirport(Position position){
        int inAirport = 0;
        if(position != null){
            Float centerX, centerY;
            float width = (float)RECT_WIDTH;
            for(int id: AirportIds){
                centerX = Positions.get(id).getX();
                centerY = Positions.get(id).getY();
                if(Math.abs(position.getX()-centerX) < width && Math.abs(position.getY()-centerY) < width){
                    inAirport = 1;
                    break;
                }
            }
        }
        return inAirport;
    }

    private Map<String, Position> hasPlane(int nowX, int nowY){
        Float centerX, centerY;
        float width = (float)RECT_WIDTH, x = (float)nowX, y = (float)nowY;
        Map<String, Position> result = new HashMap<String, Position>();

        int flag = 0, chessId;
        for(Position position: PlanePositions){
            centerX = position.getX();
            centerY = position.getY();
            chessId = position.getChessId();
            if(Math.abs(centerX-x) < width && Math.abs(centerY-y) < width && chessId == NowPlayerId){
                result.put("position", position);
                flag = 1;
                break;
            }
        }

        int inAirport = isInAirport(result.get("position"));
        result.put("flag", new Position(0, 0, flag, inAirport));
        return result;
    }

    private boolean isDicePosition(int nowX, int nowY){
        float halfLength = (float)DICE_LENGTH/2;
        float diceCenterX = (float)DICE_BEGINX+halfLength, diceCenterY = (float)DICE_BEGINY+halfLength;
        float x = (float)nowX, y = (float)nowY;
        return Math.abs(diceCenterX-x) < halfLength && Math.abs(diceCenterY-y) < halfLength;
    }

    private boolean isAIPosition(int nowX, int nowY){
        float halfWidth = (float)BUTTON_WIDTH/2, halfHeight = (float)BUTTON_HEIGHT/2;
        float AICenterX = (float)AI_BEGINX + halfWidth, AICenterY = (float)AI_BEGINY + halfHeight;
        float x = (float)nowX, y = (float)nowY;
        return Math.abs(AICenterX-x) < halfWidth && Math.abs(AICenterY-y) < halfHeight;
    }

    private boolean isBackPosition(int nowX, int nowY){
        float halfWidth = (float)BUTTON_WIDTH/2, halfHeight = (float)BUTTON_HEIGHT/2;
        float backCenterX = (float)BACK_BEGINX + halfWidth, backCenterY = (float)BACK_BEGINY + halfHeight;
        float x = (float)nowX, y = (float)nowY;
        return Math.abs(backCenterX-x) < halfWidth && Math.abs(backCenterY-y) < halfHeight;
    }
}