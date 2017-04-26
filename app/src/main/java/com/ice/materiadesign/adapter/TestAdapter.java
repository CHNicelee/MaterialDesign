package com.ice.materiadesign.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ice.materiadesign.R;
import com.ice.materiadesign.model.Model;
import com.ice.materiadesign.model.ModelOne;
import com.ice.materiadesign.model.ModelThree;
import com.ice.materiadesign.model.ModelTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asd on 1/9/2017.
 */

public class TestAdapter extends RecyclerView.Adapter<AbstractViewHolder> {

    public Context context;
    private LayoutInflater mLayoutInflater;
    public List<Model> datas = new ArrayList<>();
    public TestAdapter(Context context){
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addData(List<Model> datas){
        this.datas = datas;
    }

    //得到item的类型
    @Override
    public int getItemViewType(int position) {
//        return datas.get(position).type;
        return types.get(position);

    }

    @Override
    public AbstractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case Model.TYPE_ONE:
                return new ViewHolderOne(mLayoutInflater.inflate(R.layout.item_1,parent,false));
            case Model.TYPE_TWO:
                return new ViewHolderTwo(mLayoutInflater.inflate(R.layout.item_2,parent,false));
            case Model.TYPE_THREE:
                return new ViewHolderThree(mLayoutInflater.inflate(R.layout.item_3,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(AbstractViewHolder holder, int position) {
        int itemType = types.get(position);
        int realPosition = position - typePosition.get(itemType);
        switch (itemType){
            case Model.TYPE_ONE:
                holder.bindViewHolder(list1.get(realPosition));
                break;
            case Model.TYPE_TWO:
                holder.bindViewHolder(list2.get(realPosition));
                break;
            case Model.TYPE_THREE:
                holder.bindViewHolder(list3.get(realPosition));
                break;

        }
    }


    @Override
    public int getItemCount() {
        return types.size();
    }

    List<ModelOne> list1 = new ArrayList<>();
    List<ModelTwo> list2 = new ArrayList<>();
    List<ModelThree> list3 = new ArrayList<>();

    public void addAllData(List<ModelOne> list1, List<ModelTwo> list2, List<ModelThree> list3) {
        this.list1 = list1;
        this.list2 = list2;
        this.list3 = list3;
        setType(list1,Model.TYPE_ONE);
        setType(list2,Model.TYPE_TWO);
        setType(list3,Model.TYPE_THREE);
    }
    Map<Integer ,Integer> typePosition = new HashMap<>();
    List<Integer> types = new ArrayList<>();
    //为三种数据设置类型
    public void setType(List lists,int type){
        typePosition.put(type,types.size());
        for (int i = 0; i < lists.size(); i++) {
            types.add(type);
        }
    }
}
