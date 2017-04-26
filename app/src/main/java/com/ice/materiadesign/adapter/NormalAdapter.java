package com.ice.materiadesign.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ice.materiadesign.R;

import java.util.List;

/**
 * Created by asd on 4/9/2017.
 */
public class NormalAdapter extends  RecyclerView.Adapter<NormalAdapter.MyViewHolder>{

    private List<String> data;

    public NormalAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }

        public void setText(String text){
            textView.setText(text);
        }

    }

    public List<String> getData(){
        return data;
    }

    public void onItemDissmiss(int position) {
        //移除数据
        data.remove(position);
        notifyItemRemoved(position);
    }

}
