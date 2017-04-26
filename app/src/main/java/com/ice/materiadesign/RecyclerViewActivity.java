package com.ice.materiadesign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.ice.materiadesign.adapter.NormalAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asd on 4/9/2017.
 */

public class RecyclerViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("这是第"+ i + "个");
        }
        NormalAdapter adapter = new NormalAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(new DragItem(adapter));
        touchHelper.attachToRecyclerView(recyclerView);

    }
}
