package com.dhadrianwaletv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhadrianwaletv.Activities.VideoPlay;
import com.dhadrianwaletv.ModalClasses.YoutubeModal;
import com.dhadrianwaletv.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MyViewHolder> {

    private List<YoutubeModal> galleryList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView img;
        public LinearLayout card_view;

        public MyViewHolder(View view) {
            super(view);
            card_view = view.findViewById(R.id.card_view);
            text = view.findViewById(R.id.text);
            img = view.findViewById(R.id.img);

        }
    }


    public VideosAdapter(List<YoutubeModal> moviesList, Context context) {
        Log.e("Adapter","videos"+moviesList.size());

        this.context = context;
        this.galleryList = moviesList;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final YoutubeModal galleryModel = galleryList.get(position);
       // Log.e("title in adapter",""+galleryModel.getTitle());

        Log.e("Adapter","videos");
        Picasso.with(context)
                .load(galleryModel.getImg_url())
                .into(holder.img);//        Glide.with(context)
//                .load(imageUrl)
//                .placeholder(R.drawable.sample)
//                .into(holder.iv_bishop);

        holder.text.setText(galleryModel.getTitle());
        final String videoid = galleryModel.getVideo_id();
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,VideoPlay.class);
                intent.putExtra("videoid",videoid);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("size is",""+galleryList.size());
        return galleryList.size();
    }
}