package com.dhadrianwaletv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhadrianwaletv.Activities.VideoPlaylist;
import com.dhadrianwaletv.ModalClasses.YoutubeModal;
import com.dhadrianwaletv.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaylistAdapter extends BaseAdapter {

    private Context context;
    private LinearLayout cardView;
    private ArrayList<YoutubeModal> arrayList = new ArrayList<>();
    public PlaylistAdapter(Context context, ArrayList<YoutubeModal> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View grid;
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(context);
            grid = inflater.inflate(R.layout.grid_layout, null);



        } else {
            grid = (View) convertView;
        }
        YoutubeModal youtubeModal = arrayList.get(i);

        ImageView imageView=(ImageView)grid.findViewById(R.id.img);
        Picasso.with(context)
                .load(youtubeModal.getImg_url())
                .into(imageView);
        // imageView.setImageURI(YoutubeModal.getImg_url());
        TextView tv=(TextView)grid.findViewById(R.id.text);

        tv.setText(youtubeModal.getTitle());
        final String playlist_id = youtubeModal.getVideo_id();
//        Log.e()
        cardView =grid.findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,VideoPlaylist.class);
                intent.putExtra("playlist_id",playlist_id);
                context.startActivity(intent);
            }
        });

        return grid;
    }
}
