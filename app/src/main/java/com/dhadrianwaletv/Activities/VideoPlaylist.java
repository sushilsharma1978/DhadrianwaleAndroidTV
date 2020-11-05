package com.dhadrianwaletv.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dhadrianwaletv.APIClient;
import com.dhadrianwaletv.Adapters.Grid_layoutAdapter;
import com.dhadrianwaletv.R;
import com.dhadrianwaletv.RetrofitAPI;
import com.dhadrianwaletv.ModalClasses.YoutubeModal;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlaylist extends AppCompatActivity {
    YoutubeModal youtubeModal;
    private GridView gv;
    private Grid_layoutAdapter adapter;
    private  ProgressDialog progress;
    private ArrayList<YoutubeModal> arrayList = new ArrayList<>();
    public android.support.v7.widget.Toolbar toolbar;
  LinearLayout linearLayout;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_playlist);



        String playListid=getIntent().getExtras().getString("playlist_id");
        Log.e("PlayListId",playListid.toString());

        gv=(GridView)findViewById(R.id.gridview);
        linearLayout=findViewById(R.id.ll_tryagin);
        adapter= new Grid_layoutAdapter(getApplicationContext(),arrayList);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected(getApplicationContext())){
                    getVideos(playListid);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Oops no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        gv.setAdapter(adapter);

        if (isConnected(getApplicationContext())) {
            getVideos(playListid);
        } else{
            Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                gv.onKeyUp(KeyEvent.KEYCODE_DPAD_UP, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP));
                gv.onKeyUp(KeyEvent.KEYCODE_DPAD_RIGHT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
                gv.onKeyUp(KeyEvent.KEYCODE_DPAD_RIGHT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
                return true;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                gv.onKeyUp(KeyEvent.KEYCODE_DPAD_DOWN, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN));
                gv.onKeyUp(KeyEvent.KEYCODE_DPAD_LEFT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT));
                gv.onKeyUp(KeyEvent.KEYCODE_DPAD_LEFT, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT));
                return true;
            case KeyEvent.KEYCODE_BACK:
                super.onBackPressed();
                return true;


        }

        return super.onKeyUp(keyCode, event);
    }

    public void getVideos(String playListid){

        RetrofitAPI retrofitAPI;

        retrofitAPI = APIClient.getClient().create(RetrofitAPI.class);
        Call<JsonElement> call = retrofitAPI.getYouTubePlaylistNext(playListid);

        progress = new ProgressDialog(this);
        progress.setMax(100);
        progress.setMessage("Loading Videos....");
        progress.setCancelable(false);
        //  progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.show();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try{
                    if(response.isSuccessful()) {
                        gv.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                    // Log.e("reponse url",response.raw().toString());
                    JSONObject jsonObject= new JSONObject(response.body().toString());

                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                    Log.e("Array length",jsonArray.length()+"");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                        JSONObject jsonObject4 = jsonObject1.getJSONObject("snippet");

                        if(jsonObject4.has("thumbnails")) {
                            JSONObject jsonObject2 = jsonObject4.getJSONObject("thumbnails");
                            JSONObject jsonObject3 = jsonObject2.getJSONObject("medium");
                            Log.e("Image url", jsonObject3.getString("url"));

                            JSONObject jsonObject5 = jsonObject4.getJSONObject("resourceId");
                            Log.e("VideoId", jsonObject5.getString("videoId"));
                            youtubeModal = new YoutubeModal(jsonObject4.getString("title"), jsonObject3.getString("url"), jsonObject5.getString("videoId"));
                        }else
                        {
                            JSONObject jsonObject5 = jsonObject4.getJSONObject("resourceId");
                            Log.e("VideoId", jsonObject5.getString("videoId"));
                            youtubeModal = new YoutubeModal(jsonObject4.getString("title"),jsonObject4.getString("title"), jsonObject5.getString("videoId"));
                        }
                        arrayList.add(youtubeModal);
                    }
                    adapter.notifyDataSetChanged();
                        progress.dismiss();
                    }else{
                        progress.dismiss();
                        gv.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){

                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progress.dismiss();
                gv.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
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