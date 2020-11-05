package com.dhadrianwaletv.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dhadrianwaletv.ModalClasses.PicturesModal;
import com.dhadrianwaletv.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageGridAdapter extends BaseAdapter {

    private Context context;
    private CardView cardView;

    private ArrayList<PicturesModal> arrayList = new ArrayList<>();
    public ImageGridAdapter(Context context, ArrayList<PicturesModal> arrayList){
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
            grid = inflater.inflate(R.layout.image_grid, null);



        } else {
            grid = (View) convertView;
        }


        PicturesModal youtubeModal = arrayList.get(i);
        Log.e("Title in adapter",youtubeModal.getImg_url());

        ImageView imageView=(ImageView)grid.findViewById(R.id.img);
        Picasso.with(context)
                .load(youtubeModal.getImg_url())
                .into(imageView);
       // imageView.setImageURI(YoutubeModal.getImg_url());

//        cardView =(CardView)grid.findViewById(R.id.card_view);
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent= new Intent(context,VideoPlay.class);
//                intent.putExtra("videoid",videoid);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });

        return grid;
    }


}
