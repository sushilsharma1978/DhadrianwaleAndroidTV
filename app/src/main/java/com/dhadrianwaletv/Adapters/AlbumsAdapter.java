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

import com.dhadrianwaletv.Activities.ImageViewActivity;
import com.dhadrianwaletv.ModalClasses.AlbumModal;
import com.dhadrianwaletv.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class AlbumsAdapter extends BaseAdapter {

    private Context context;
    private LinearLayout cardView;

    private ArrayList<AlbumModal> arrayList = new ArrayList<>();
    public AlbumsAdapter(Context context, ArrayList<AlbumModal> arrayList){
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
            grid = inflater.inflate(R.layout.album_grid, null);



        } else {
            grid = (View) convertView;
        }


        final AlbumModal albumModal = arrayList.get(i);

        ImageView imageView=(ImageView)grid.findViewById(R.id.img);
        Picasso.with(context)
                .load(albumModal.getImg_url())
                .into(imageView);
       // imageView.setImageURI(albumModal.getImg_url());
        TextView tv=(TextView)grid.findViewById(R.id.text);

        tv.setText(albumModal.getTitle());
        cardView =grid.findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,ImageViewActivity.class);

                intent.putExtra("imageArray",albumModal.getJsonArray().toString());
                context.startActivity(intent);
            }
        });

//        cardView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                Log.e("On Key","Listener1");
//                if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
//                {
//                    Log.e("On Key","Listener2");
//                    Intent intent = new Intent(context,ImageViewActivity.class);
//
//                    intent.putExtra("imageArray",albumModal.getJsonArray().toString());
//                    context.startActivity(intent);
//                    return true;
//                }
//                return false;
//            }
//        });
        return grid;
    }





}
