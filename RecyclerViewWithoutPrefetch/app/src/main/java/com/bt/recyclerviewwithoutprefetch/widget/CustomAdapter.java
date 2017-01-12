package com.bt.recyclerviewwithoutprefetch.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.recyclerviewwithoutprefetch.R;
import com.bt.recyclerviewwithoutprefetch.model.Image;
import com.bumptech.glide.Glide;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Monika on 1/11/2017.
 * Binding view and data
 */

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Image> mImages;
    private LayoutInflater mInflater;
    private Context mContext;

    public CustomAdapter(Context context, ArrayList<Image> images) {
        mImages = images;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = mInflater.inflate(R.layout.recycler_view_item0, parent, false);
                return new ViewHolder0(view);
            case 1:
                view = mInflater.inflate(R.layout.recycler_view_item1, parent, false);
                return new ViewHolder1(view);
            case 2:
                view = mInflater.inflate(R.layout.recycler_view_item2, parent, false);
                return new ViewHolder2(view);
            case 3:
                view = mInflater.inflate(R.layout.recycler_view_item3, parent, false);
                return new ViewHolder3(view);
            case 4:
                view = mInflater.inflate(R.layout.recycler_view_item4, parent, false);
                return new ViewHolder4(view);
            case 5:
                view = mInflater.inflate(R.layout.recycler_view_item5, parent, false);
                return new ViewHolder5(view);
            default: return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        switch (holder.getItemViewType()) {
            case 0:
                try {
                URL url = new URL(mImages.get(position).getImageUrl());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Glide.with(mContext).load(mImages.get(position).getImageUrl()).into(((ViewHolder0)holder).mImage);
                ((ViewHolder0)holder).mDescription.setText(mImages.get(position).getImageDescription());
                break;
            case 1:
                try {
                    URL url = new URL(mImages.get(position).getImageUrl());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                Glide.with(mContext).load(mImages.get(position).getImageUrl()).into(((ViewHolder1)holder).mImage);
                ((ViewHolder1)holder).mDescription.setText(mImages.get(position).getImageDescription());
                break;
            case 2:
                ((ViewHolder2)holder).textView0.setText(mImages.get(position).getImageDescription());
                ((ViewHolder2)holder).textView1.setText(mImages.get(position).getImageDescription());
                break;
            case 3:
                ((ViewHolder3)holder).textView0.setText(mImages.get(position).getImageDescription());
                break;
            case 4:
                Glide.with(mContext).load(mImages.get(position).getImageUrl()).into(((ViewHolder4)holder).mImage);
                break;
            case 5:
                Glide.with(mContext).load(mImages.get(position).getImageUrl()).into(((ViewHolder5)holder).mImage0);
                Glide.with(mContext).load(mImages.get(position).getImageUrl()).into(((ViewHolder5)holder).mImage1);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

     private static class ViewHolder0 extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mDescription;

        private ViewHolder0 (View view) {
            super(view);
            mImage = (ImageView) view.findViewById(R.id.image0);
            mDescription = (TextView) view.findViewById(R.id.description);
        }
    }

    private static class ViewHolder1 extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mDescription;

        private ViewHolder1 (View view) {
            super(view);
            mImage = (ImageView) view.findViewById(R.id.image1);
            mDescription = (TextView) view.findViewById(R.id.description1);
        }
    }

    private static class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView textView0;
        TextView textView1;

        private ViewHolder2 (View view) {
            super(view);
            textView0 = (TextView) view.findViewById(R.id.text02);
            textView1 = (TextView) view.findViewById(R.id.text12);
        }
    }

    private static class ViewHolder3 extends RecyclerView.ViewHolder {
        TextView textView0;

        private ViewHolder3 (View view) {
            super(view);
            textView0 = (TextView) view.findViewById(R.id.text_03);
        }
    }

    private static class ViewHolder4 extends RecyclerView.ViewHolder {
        ImageView mImage;

        private ViewHolder4 (View view) {
            super(view);
            mImage = (ImageView) view.findViewById(R.id.image_04);
        }
    }

    private static class ViewHolder5 extends RecyclerView.ViewHolder {
        ImageView mImage0;
        ImageView mImage1;

        private ViewHolder5 (View view) {
            super(view);
            mImage0 = (ImageView) view.findViewById(R.id.image_05);
            mImage1 = (ImageView) view.findViewById(R.id.image_15);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position % 6;
    }
}
