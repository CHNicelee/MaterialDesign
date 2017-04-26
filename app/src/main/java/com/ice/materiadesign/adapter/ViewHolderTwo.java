package com.ice.materiadesign.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ice.materiadesign.R;
import com.ice.materiadesign.model.ModelTwo;

/**
 * Created by asd on 1/9/2017.
 */

public class ViewHolderTwo extends AbstractViewHolder<ModelTwo> {
    TextView tv_info;
    ImageView imageView;
    public ViewHolderTwo(View itemView) {
        super(itemView);
        tv_info = (TextView) itemView.findViewById(R.id.name);
        imageView = (ImageView) itemView.findViewById(R.id.imageLeft);
    }

    @Override
    public void bindViewHolder(ModelTwo model) {
        tv_info.setText(model.name);
        imageView.setBackgroundResource(model.ImageIdLeft);
    }

}
