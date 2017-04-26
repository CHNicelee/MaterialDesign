package com.ice.materiadesign.adapter;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.ice.materiadesign.R;
import com.ice.materiadesign.util.BitmapUtil;

import java.util.List;


/**
 * Created by asd on 1/7/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    LayoutInflater inflater;
    List<Integer> imageIDs;
    PopupWindow popupWindow;
    Activity activity;
    private Button btn_delete;

    public MyAdapter(LayoutInflater inflater,List imageIDs,Activity activity){
        this.inflater = inflater;
        this.imageIDs = imageIDs;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(inflater.inflate(R.layout.rv_main_item,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
//        Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromResource(activity.getResources(),imageIDs.get(position),50,50);

        BitmapUtil bitmapUtil = new BitmapUtil(activity);
        bitmapUtil.loadBitmap(imageIDs.get(position),holder.iv_main);
//        holder.iv_main.setImageResource(imageIDs.get(position));
//        holder.iv_main.setImageBitmap(bitmap);
        holder.iv_main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog(holder.iv_main);
                return false;
            }
        });

    }



    @Override
    public int getItemCount() {
        return imageIDs.isEmpty()?0:imageIDs.size();
    }

    private void  showDialog(View view){
        if(null==popupWindow){
            View popView = inflater.inflate(R.layout.delete_item, null);
             btn_delete=(Button) popView.findViewById(R.id.btn_delete);

            popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
        }
        if (popupWindow.isShowing())
            popupWindow.dismiss();

        //第一次显示控件的时候宽高会为0
        int deleteHeight=btn_delete.getHeight()==0?145:btn_delete.getHeight();
        int deleteWidth=btn_delete.getWidth()==0?212:btn_delete.getWidth();

        popupWindow.showAsDropDown(view,(view.getWidth()-deleteWidth)/2,-view.getHeight()-deleteHeight);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_main;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv_main = (ImageView) itemView.findViewById(R.id.iv_main);
        }
    }

}
