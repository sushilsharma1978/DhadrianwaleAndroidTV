package com.dhadrianwaletv.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.dhadrianwaletv.APIClient;
import com.dhadrianwaletv.ModalClasses.AlbumModal;
import com.dhadrianwaletv.Adapters.AlbumsAdapter;
import com.dhadrianwaletv.ModalClasses.PicturesModal;
import com.dhadrianwaletv.R;
import com.dhadrianwaletv.RetrofitAPI;
import com.google.gson.JsonElement;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity {

    AlbumModal albumModal;
    ArrayList<AlbumModal> arrayList = new ArrayList<>();
    AlbumsAdapter albumsAdapter;
    ArrayList<PicturesModal> arrayList1 = new ArrayList<>();
    ProgressDialog progress;
    GridView gridView;
    PicturesModal picturesModal;
    public  boolean backclicked=  false;
    LinearLayout linearLayout;


    @Override
    public void onBackPressed() {

    super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        gridView = findViewById(R.id.gridview);
         linearLayout=findViewById(R.id.ll_tryagin);
        albumsAdapter = new AlbumsAdapter(this,arrayList);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected(getApplicationContext())){
                    getAlbums();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Oops no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        gridView.setAdapter(albumsAdapter);

        if(isConnected(this)){
            getAlbums();
        }
        else
        {
            Toast.makeText(this, "Oops no internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                gridView.onKeyDown(KeyEvent.KEYCODE_DPAD_UP   , new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP));
                gridView.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
                gridView.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
                return true;

            case KeyEvent.KEYCODE_DPAD_DOWN:
                gridView.onKeyDown(KeyEvent.KEYCODE_DPAD_DOWN, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN));
                gridView.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT));
                gridView.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT));
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }


    public void getAlbums(){
       // Log.i("time start=======",""+System.currentTimeMillis());
        Long start_time=System.currentTimeMillis();

        RetrofitAPI retrofitAPI;

        retrofitAPI = APIClient.getClientGallery().create(RetrofitAPI.class);

        Call<JsonElement> call = retrofitAPI.getAlbumList(getIntent().getStringExtra("id"),"attachments");

        progress = new ProgressDialog(this);
     //   progress.setMax(100);
        progress.setMessage("Loading Albums....");
        progress.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK)) {

                    backclicked = true;
                     call.cancel();
                    dialog.dismiss();

                    return true;
                }

                return false;
            }
        });
        progress.setCancelable(true);
        progress.show();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                 if(response.isSuccessful()) {
                     gridView.setVisibility(View.VISIBLE);
                     linearLayout.setVisibility(View.GONE);
                     // Log.e("reponse url",response.raw().toString());
                     JSONObject jsonObject = new JSONObject(response.body().toString());

                     JSONArray jsonArray = jsonObject.getJSONArray("posts");

                     Log.e("Array length", jsonArray.length() + "");
                     for (int i = 0; i < jsonArray.length(); i++) {
                         JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                         JSONArray jsonArray1 = jsonObject1.getJSONArray("attachments");
                         //JSONObject jsonObject2 = jsonArray1.getJSONObject(0).getString("url");
                         Log.e("value of i", i + "===" + jsonArray1.length());

                         for (int j = 0; j < jsonArray1.length(); j++) {

                             JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                             JSONObject jsonObject3 = jsonObject2.getJSONObject("images");
                             if (jsonObject3.has("medium_large")) {
                                 JSONObject jsonObject4 = jsonObject3.getJSONObject("medium_large");
                                 picturesModal = new PicturesModal(jsonObject4.getString("url"));
                             }
                             arrayList1.add(picturesModal);
                             // Log.e("Image url",jsonObject4.getString("url")+"");
                         }
                         albumModal = new AlbumModal(jsonObject1.getString("title"), jsonArray1.getJSONObject(0).getString("url"), jsonArray1);
                         //albumModal = new AlbumModal(jsonObject1.getString("title"),jsonArray1.getJSONObject(0).getString("url"),arrayList1);

                         arrayList.add(albumModal);
                     }
                     albumsAdapter.notifyDataSetChanged();
                     progress.dismiss();
                 }else{
                     progress.dismiss();
                     gridView.setVisibility(View.GONE);
                     linearLayout.setVisibility(View.VISIBLE);
                 }
                } catch (Exception e) {

                    e.printStackTrace();
                }
                Long end_time=System.currentTimeMillis();
                Log.i("api taken time=======",""+(end_time-start_time));
            }



            @Override
            public void onFailure(Call<JsonElement> call, final Throwable t) {
            //    Log.i("thorwable====",t.getMessage());
                if(t.getMessage() == "Canceled" || backclicked ==true) {
                    backclicked = false;
                    onBackPressed();
                }
                else {
                    progress.dismiss();
                    gridView.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

}
