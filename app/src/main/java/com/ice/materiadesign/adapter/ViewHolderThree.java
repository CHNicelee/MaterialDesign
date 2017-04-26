package com.ice.materiadesign.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ice.materiadesign.R;
import com.ice.materiadesign.model.ModelThree;

/**
 * Created by asd on 1/9/2017.
 */

public class ViewHolderThree extends AbstractViewHolder<ModelThree> {
    TextView tv_info;
    ImageView imageView;
    ImageView imageViewRight;
    public ViewHolderThree(View itemView) {
        super(itemView);
        tv_info = (TextView) itemView.findViewById(R.id.name);
        imageView = (ImageView) itemView.findViewById(R.id.imageLeft);
        imageViewRight = (ImageView) itemView.findViewById(R.id.imageRight);
    }

    @Override
    public void bindViewHolder(ModelThree model) {
        tv_info.setText(model.name);
        imageView.setBackgroundResource(model.ImageIdLeft);
        imageViewRight.setBackgroundResource(model.ImageIdRight);
    }

}
