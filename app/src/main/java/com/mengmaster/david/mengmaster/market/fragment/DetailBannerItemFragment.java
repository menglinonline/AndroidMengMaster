package com.mengmaster.david.mengmaster.market.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mengmaster.david.mengmaster.market.activity.R;

/**
 * Created by dell on 2016/12/14.
 */
public class DetailBannerItemFragment extends Fragment implements View.OnClickListener {
    private int position;
    private ImageView mImageView;
    private int imageRes;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_banner_item, container, false);
        mImageView = (ImageView) inflate.findViewById(R.id.imageView1);
        mImageView.setImageResource(imageRes);
        inflate.setOnClickListener(this);

        return inflate;
    }

    public void setResId(int imageRes) {
        this.imageRes = imageRes;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View v) {

    }
}
