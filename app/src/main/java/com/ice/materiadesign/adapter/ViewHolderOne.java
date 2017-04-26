package com.ice.materiadesign.adapter;

import android.view.View;
import android.widget.TextView;

import com.ice.materiadesign.R;
import com.ice.materiadesign.model.ModelOne;

/**
 * Created by asd on 1/9/2017.
 */

public class ViewHolderOne extends AbstractViewHolder<ModelOne> {
    TextView tv_info;
    public ViewHolderOne(View itemView) {
        super(itemView);
        tv_info = (TextView) itemView.findViewById(R.id.name);
    }

    @Override
    public void bindViewHolder(ModelOne model) {
        tv_info.setText(model.name);
    }

}
