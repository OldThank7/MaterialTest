package com.example.materialtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private String TAG = "MaterialTest";
    private Toolbar toolbar;
    private DrawerLayout mDrawerLoyout;
    private Fruit[] fruits = {new Fruit("Apple",R.drawable.ic_1)
            ,new Fruit("Banana",R.drawable.ic_2)
            ,new Fruit("0range",R.drawable.ic_3)
            ,new Fruit("Watermelon",R.drawable.ic_4)
            ,new Fruit("Pear",R.drawable.ic_5)
            ,new Fruit("Grape",R.drawable.ic_6)
            ,new Fruit("Pineapple",R.drawable.ic_7)
            ,new Fruit("Strawberry",R.drawable.ic_8)};

    List<Fruit> fruitList = new ArrayList<>();
    FruitAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolber();
        initDrawerLoyout();
        initNav();
        initfab();
        initFruit();
        initSwiperRefresh();
        LogUtil.d(TAG,"初始化操作完成");
    }

    private void initSwiperRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorswipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruit();
            }
        });
    }

    private void refreshFruit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruit();
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruit() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        initFruit_1();
    }

    private void initFruit_1() {
        fruitList.clear();
        for (int i = 0; i < 15;i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    private void initfab() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"数据已删除",Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "数据已恢复", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
    }

    private void initNav() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this, "Call", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_Fraiends:
                        Toast.makeText(MainActivity.this, "Fraiends", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_location:
                        Toast.makeText(MainActivity.this, "Location", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_mail:
                        Toast.makeText(MainActivity.this, "Mail", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.Tasks:
                        Toast.makeText(MainActivity.this, "Tasks", Toast.LENGTH_SHORT).show();
                        break;
                }
                mDrawerLoyout.closeDrawers();
                return true;
            }
        });
    }

    private void initDrawerLoyout() {
        mDrawerLoyout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    private void initToolber() {
        toolbar = (Toolbar) findViewById(R.id.toolber);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolber,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "正在上传", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "正在删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setsting:
                Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLoyout.openDrawer(GravityCompat.START);
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(TAG, "活动由不可见变为可见");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(TAG,"重新启动");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(TAG,"资源释放");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(TAG,"活动完全不可见");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG,"销毁");
    }
}
