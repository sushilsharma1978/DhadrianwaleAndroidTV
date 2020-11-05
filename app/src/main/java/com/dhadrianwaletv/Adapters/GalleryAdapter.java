package com.dhadrianwaletv.Adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhadrianwaletv.Activities.AlbumActivity;
import com.dhadrianwaletv.ModalClasses.GalleryModel;
import com.dhadrianwaletv.R;

import java.util.List;


public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private List<GalleryModel> galleryList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_gallery;
        public ImageView iv_bishop;
        public LinearLayout ll_gallery_detail;

        public MyViewHolder(View view) {
            super(view);
            tv_gallery = view.findViewById(R.id.tv_gallery);
            ll_gallery_detail = view.findViewById(R.id.ll_gallery_detail);

        }
    }


    public GalleryAdapter(List<GalleryModel> moviesList, Context context) {
        this.context = context;
        this.galleryList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final GalleryModel galleryModel = galleryList.get(position);
       // Log.e("title in adapter",""+galleryModel.getTitle());
        holder.tv_gallery.setText(galleryModel.getTitle());
//        Glide.with(context)
//                .load(imageUrl)
//                .placeholder(R.drawable.sample)
//                .into(holder.iv_bishop);

        holder.ll_gallery_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AlbumActivity.class);
                intent.putExtra("id",galleryModel.getId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }
}