package com.ice.materiadesign;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ice.materiadesign.adapter.CommentAdapter;
import com.ice.materiadesign.adapter.ViewHolder;

import java.util.Arrays;

/**
 * Created by asd on 2/16/2017.
 */

public class CoordinatorTest extends AppCompatActivity {

    RecyclerView recyclerView;
    NestedScrollView nestedScrollView;
    FloatingActionButton fab;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_layout);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        Integer[] datas = new Integer[50];
        for (int i = 0; i < datas.length; i++) {
            datas[i] = i;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CommentAdapter<Integer>(this,R.layout.item_1, Arrays.asList(datas)) {
            @Override
            public void convert(ViewHolder viewHolder, Integer data) {
                viewHolder.setText(R.id.name,data+"");
            }
        });


//        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

/*        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(nestedScrollView);
                bottomSheetBehavior.setState(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED
                        ?BottomSheetBehavior.STATE_EXPANDED:BottomSheetBehavior.STATE_COLLAPSED);
            }
        });*/


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(recyclerView,"哎哟 不错哦",Snackbar.LENGTH_SHORT);
                View view = snackbar.getView();//获取Snackbar的view
                if(view!=null){
                    view.setBackgroundColor(getResources().getColor(R.color.pink));//修改view的背景色
                    ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(Color.WHITE);//获取Snackbar的message控件，修改字体颜色
                    ((Button)view.findViewById(R.id.snackbar_action)).setTextColor(Color.GRAY);
                }
                snackbar.setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CoordinatorTest.this, "点击了一下", Toast.LENGTH_SHORT).show();
                    }
                }).show();
           }
        });
    }
}
