package com.ice.materiadesign;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ice.materiadesign.adapter.CommentAdapter;
import com.ice.materiadesign.adapter.MultiItemCommonAdapter;
import com.ice.materiadesign.adapter.MultiItemTypeSupport;
import com.ice.materiadesign.adapter.MyAdapter;
import com.ice.materiadesign.adapter.TestAdapter;
import com.ice.materiadesign.adapter.ViewHolder;
import com.ice.materiadesign.model.Model;
import com.ice.materiadesign.model.ModelOne;
import com.ice.materiadesign.model.ModelThree;
import com.ice.materiadesign.model.ModelTwo;
import com.ice.materiadesign.util.DecorationListener;
import com.ice.materiadesign.util.FloatingDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rv_main;
    private SideBar sideBar;
    private TextView tv_dialog;
    private CommentAdapter<String> commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //getMemory();
        //initRecyclerView();

        //测试recyclerView展示不同的item
//        initRecyclerViewDifferent();
        //采用通用adapter进行测试
      //  initRv();
        initContactRv();
    }

    private void initRv() {
        MultiItemTypeSupport<Model> m = new MultiItemTypeSupport<Model>() {
            @Override
            public int getLayoutId(int itemType) {
                switch (itemType){
                    case 1:
                        return R.layout.item_1;
                    case 2:
                        return R.layout.item_2;
                    case 3:
                        return R.layout.item_3;
                }
                return  R.layout.item_3;
            }

            @Override
            public int getItemViewType(int position, Model model) {
                if(position<10) return 1;
                    else if(position<20) return 2;
                    else return 3;
            }
        };
        final List<Model> models = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Model model = new Model();
            model.imageId = R.drawable.bestjay;
            model.info = "这是第" +i+"个";
            if(i<10) model.type= 1;
            else if(i<20)  model.type= 2;
            else  model.type= 3;
            models.add(model);
        }

        rv_main = (RecyclerView) findViewById(R.id.rv_main);
        rv_main.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        rv_main.addItemDecoration(new MyItemDecoration(this,LinearLayoutManager.VERTICAL));

        FloatingDecoration.Builder builder = new FloatingDecoration.Builder(this, new DecorationListener() {
            @Override
            public int getType(int position) {
                return models.get(position).type;
            }

            @Override
            public String getTypeText(int position) {
                return "第"+models.get(position).type+"组";
            }
        });
        builder.setTextColor(Color.WHITE)
                .setBackgroundColor(Color.DKGRAY)
                .setHeight(getResources().getDimensionPixelSize(R.dimen.titleHeight));
        rv_main.addItemDecoration(builder.build());
        MultiItemCommonAdapter<Model> adapter = new MultiItemCommonAdapter<Model>(this,models,m){

            @Override
            public void convert(ViewHolder viewHolder, Model data) {
                switch (data.type){
                    case 3: viewHolder.setImageResource(R.id.imageRight,data.imageId);
                    case 2:viewHolder.setImageResource(R.id.imageLeft,data.imageId);
                    case 1:viewHolder.setText(R.id.name,data.info);break;
                }
            }
        };

        rv_main.setAdapter(adapter);
    }

    //初始化联系人列表
    private void initContactRv(){

        tv_dialog = (TextView) findViewById(R.id.dialog);
        sideBar = (SideBar) findViewById(R.id.sideBar);

        sideBar.setTextView(tv_dialog);

        final String[] datas= {"apple","aboard","barbecue","banana","cat","cat","apple","aboard",
                "barbecue","banana","cat","cat","apple","aboard","barbecue","banana","cat","cat",
                "dog","egg","fox","goose","hook","illusion","jog","kitty","orange","pineapple","zero","yellow"};
        final List<String> dataList = new ArrayList();
        Collections.addAll(dataList,datas);
        Collections.sort(dataList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.charAt(0) - o2.charAt(0);
            }
        });
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int index = -1;
                for (int i = 0; i < dataList.size(); i++) {
                    if((dataList.get(i).charAt(0)+"").toUpperCase().equals(s)){
                        index = i;
                        break;
                    }
                }
                if(index!=-1)
                rv_main.scrollToPosition(index);
            }
        });
        rv_main = (RecyclerView) findViewById(R.id.rv_main);
        commentAdapter = new CommentAdapter<String>(this,R.layout.item_1,dataList) {
            @Override
            public void convert(ViewHolder viewHolder, String data) {
                viewHolder.setText(R.id.name,data);
            }
        };
        rv_main.setAdapter(commentAdapter);
        rv_main.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        FloatingDecoration.Builder builder = new FloatingDecoration.Builder(this, new DecorationListener() {
            @Override
            public int getType(int position) {
                return (dataList.get(position).charAt(0)+"").toUpperCase().charAt(0);
            }

            @Override
            public String getTypeText(int position) {
                return (dataList.get(position).charAt(0)+"").toUpperCase();
            }
        });
        builder.setTextColor(Color.WHITE)
                .setBackgroundColor(Color.DKGRAY)
                .setHeight(getResources().getDimensionPixelSize(R.dimen.titleHeight));
        rv_main.addItemDecoration(builder.build());

    }

    /**
     * 使recyclerView展示不同的item布局
     */
    private void initRecyclerViewDifferent() {
        List<ModelOne> list1 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ModelOne m = new ModelOne();
            m.name="这是model1";
            list1.add(m);
        }

        List<ModelTwo> list2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ModelTwo m = new ModelTwo();
            m.name="这是model2";
            m.ImageIdLeft=R.drawable.bestjay;
            list2.add(m);
        }

        List<ModelThree> list3 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ModelThree m = new ModelThree();
            m.ImageIdLeft = R.drawable.bestjay;
            m.ImageIdRight = R.drawable.bestjay;
            m.name="这是model3";
            list3.add(m);
        }


        rv_main = (RecyclerView) findViewById(R.id.rv_main);

        TestAdapter myAdapter = new TestAdapter(this);
       // myAdapter.addData(datas);
        myAdapter.addAllData(list1,list2,list3);
//        rv_main.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = rv_main.getAdapter().getItemViewType(position);
                if(type == Model.TYPE_ONE){
                    return 1;//每个View占用1个横格
                }else
                    return gridLayoutManager.getSpanCount();//每个VIew占用一行
            }
        });
        rv_main.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                int spanSize = layoutParams.getSpanSize();
                int spanIndex = layoutParams.getSpanIndex();
                outRect.top = 20;
                if(spanSize != gridLayoutManager.getSpanCount()){
                    if(spanIndex == 1 || spanIndex == 2){
                        outRect.left = 10;
                    }else {
                        outRect.right = 10;
                    }
                }
            }
        });
        rv_main.setLayoutManager(gridLayoutManager);
        rv_main.setAdapter(myAdapter);
    }

    private void getMemory() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        int memorySize = activityManager.getMemoryClass();
        Log.d("ICE",memorySize+"MB");

        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.d("ICE", "Max memory is " + maxMemory + "KB");
    }

    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        rv_main = (RecyclerView) findViewById(R.id.rv_main);
        List<Integer> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(R.drawable.bestjay);
        }
        rv_main.setLayoutManager(new GridLayoutManager(this,2));
        rv_main.setAdapter(new MyAdapter(getLayoutInflater(),datas,this));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
