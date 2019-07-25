package com.example.supernote.view.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.supernote.R;
import com.example.supernote.model.GroupView;
import com.example.supernote.view.socketIO.SocketIO_Class;
import com.github.nkzawa.socketio.client.Socket;

public class BoundService extends Service implements View.OnTouchListener, View.OnClickListener {
    private int pre_x;
    private int pre_y;
    private float startX;
    private float startY;
    private WindowManager windowManager;
    private WindowManager.LayoutParams iconParams;
    private GroupView iconView;
    private Socket socket;
    private IBinder binder = new BoundExample();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        inIt();
        return super.onStartCommand(intent, flags, startId);
    }

    private void inIt() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        createIconView();
        showIcon();
    }

    private void showIcon() {
        windowManager.addView(iconView,iconParams);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void createIconView() {
        iconView = new GroupView(this);
        View view = View.inflate(this, R.layout.windowmana,iconView);
        TextView iconWinMana = view.findViewById(R.id.iconWM);
        iconWinMana.setOnTouchListener(this);
        iconWinMana.setOnClickListener(this);
        iconParams = new WindowManager.LayoutParams();
        iconParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        iconParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        iconParams.format = PixelFormat.TRANSLUCENT;
        iconParams.gravity = Gravity.CENTER;
        iconParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        iconParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        iconParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        iconParams.flags |= WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
    }

    @Override
    public boolean onUnbind(Intent intent) {

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        socket.disconnect();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pre_x = iconParams.x;
                pre_y = iconParams.y;
                startX = event.getRawX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getRawX() - startX;
                float deltaY = event.getRawY() - startY;
                iconParams.x = (int) (pre_x + deltaX);
                iconParams.y = (int) (pre_y + deltaY);
                windowManager.updateViewLayout(iconView,iconParams);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                Toast.makeText(getApplicationContext(),"OUT "+startX,Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iconWM:
                Toast.makeText(getApplicationContext(), "clickk", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public class BoundExample extends Binder{
        public BoundService getBound(){
            return BoundService.this;
        }
    }
    public void showTime(){
        Toast.makeText(getApplicationContext(),"Tien Luat",Toast.LENGTH_SHORT).show();
    }
    public void SocketConnect(){
        socket = new SocketIO_Class().inIt();
        socket.connect();
    }


}
