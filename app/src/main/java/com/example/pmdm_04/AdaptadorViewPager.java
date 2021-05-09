package com.example.pmdm_04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.Objects;

class AdaptadorViewPager extends PagerAdapter {
    Context context;
    int[] images;
    LayoutInflater mLayoutInflater;

    public AdaptadorViewPager(Context context, int[] images) {
        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position)
    {
        View itemView = mLayoutInflater.inflate(R.layout.imagen, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewMain);
        imageView.setImageResource(images[position]);
        Objects.requireNonNull(container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((LinearLayout) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }
}
