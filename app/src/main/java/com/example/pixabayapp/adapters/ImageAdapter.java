package com.example.pixabayapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.pixabayapp.Models.Images;
import com.example.pixabayapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private List<Images> imageList;
    private Context context;
    private LayoutInflater inflater;

    public ImageAdapter(List<Images> imageList, Context context){
        this.imageList = imageList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Images image = (Images) getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_image_layout, null);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        if (!image.getUrl().isEmpty())
        {
            Picasso.with(context).load(image.getUrl()).placeholder(R.drawable.ic_launcher_background).resize(400,400).centerCrop().error(R.drawable.ic_launcher_background).into(imageView);
        }
        return convertView;
    }

    public void setImages(List<Images> data) {
        imageList.addAll(data);
        notifyDataSetChanged();
    }
}
