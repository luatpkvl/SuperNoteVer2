package com.example.supernote.view.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.supernote.view.Behavior_scroll;
import com.example.supernote.view.fragment.BookFragment;
import com.example.supernote.view.fragment.FragmentChat;
import com.example.supernote.view.fragment.NotifiFragment;
import com.example.supernote.R;
import com.example.supernote.view.service.BoundService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private RelativeLayout relativeLayout_load;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private BottomNavigationView bottomNavigationView;
    private BoundService.BoundExample binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(MainActivity.this,BoundService.class));
        bindService(new Intent(this, BoundService.class),serviceConnection,BIND_AUTO_CREATE);
        //unbindService(serviceConnection);
        relativeLayout_load = findViewById(R.id.load_fragment);
        drawerLayout = findViewById(R.id.activity_main);
        bottomNavigationView = findViewById(R.id.botnv);
        navigationView = findViewById(R.id.nv);
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_draw,R.string.close_draw);
        toggle.syncState();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new Behavior_scroll());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);
        switch (menuItem.getItemId()){
            case R.id.it_book:
                load_Fragment(new BookFragment());
                break;
            case  R.id.it_nut2:
                load_Fragment(new NotifiFragment());
                break;
            case R.id.it_nut3:
                load_Fragment(new FragmentChat());
                break;
        }
        return true;
    }
    public void load_Fragment(Fragment fm){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.load_fragment,fm)
                .commit();
    }
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
             binder = (BoundService.BoundExample) service;
            // binder.getBound().showTime();
             binder.getBound().SocketConnect();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MainActivity.this,"SERVICEDISCONECT",Toast.LENGTH_SHORT).show();
        }
    };
}
