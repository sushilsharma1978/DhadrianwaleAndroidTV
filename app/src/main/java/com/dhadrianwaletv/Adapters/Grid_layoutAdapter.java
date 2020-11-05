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

import com.dhadrianwaletv.ModalClasses.YoutubeModal;
import com.dhadrianwaletv.R;
import com.dhadrianwaletv.Activities.VideoPlay;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Grid_layoutAdapter extends BaseAdapter {

    private Context context;
    private LinearLayout cardView;

    private ArrayList<YoutubeModal> arrayList = new ArrayList<>();
    public Grid_layoutAdapter(Context context, ArrayList<YoutubeModal> arrayList){
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
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
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
        final String videoid = youtubeModal.getVideo_id();
        cardView =grid.findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context,VideoPlay.class);
                intent.putExtra("videoid",videoid);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return grid;
    }


}
