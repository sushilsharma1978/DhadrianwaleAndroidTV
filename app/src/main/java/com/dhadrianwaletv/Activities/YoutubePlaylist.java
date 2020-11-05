package com.dhadrianwaletv.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.GridView;
import android.widget.Toast;

import com.dhadrianwaletv.APIClient;
import com.dhadrianwaletv.Adapters.PlaylistAdapter;
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

public class YoutubePlaylist extends AppCompatActivity{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    YoutubeModal youtubeModal;
    private GridView gv;
    private  ProgressDialog progress;
    private PlaylistAdapter playlist_adapter;
    private ArrayList<YoutubeModal> arrayList= new ArrayList<>();
    private String mParam1;
    private String mParam2;
    private GridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_youtubeplaylist);


            gv = (GridView) findViewById(R.id.gridview);
            playlist_adapter = new PlaylistAdapter(this, arrayList);

            gv.setAdapter(playlist_adapter);

        if (isConnected(this) ){
            getPlaylist();
        } else{
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
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

//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                Log.e("Clicked right","right key");
//                gv.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, new KeyEvent(KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_DPAD_LEFT));
//                gv.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, new KeyEvent(KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
//                gv.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, new KeyEvent(KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    public void getPlaylist(){
        RetrofitAPI retrofitAPI;
        retrofitAPI = APIClient.getClient().create(RetrofitAPI.class);
        Call<JsonElement> call = retrofitAPI.getYouTubePlaylist();


        progress = new ProgressDialog(this);
        progress.setMax(100);
        progress.setMessage("Loading Videos....");
        progress.setCancelable(false);
        progress.show();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try{

                    // Log.e("reponse url",response.raw().toString());
                    JSONObject jsonObject= new JSONObject(response.body().toString());

//                    if (jsonObject.has("nextPageToken")){
//
//                        String pagetoken= jsonObject.getString("nextPageToken");
//                        Log.e("pagetoken",pagetoken.toString());
//                        SharedPreferences sharedPref = this.getSharedPreferences("MyPref",0);
//                        SharedPreferences.Editor editor = sharedPref.edit();
//                        editor.putString("pageToken",pagetoken);
//                        editor.commit();
//                    }
                    // Log.e("reponse url",jsonObject.toString());
                    JSONArray jsonArray=jsonObject.getJSONArray("items");
                    Log.e("Array length",jsonArray.length()+"");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        JSONObject jsonObject4 = jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObject2 = jsonObject4.getJSONObject("thumbnails");


                        JSONObject jsonObject3 = jsonObject2.getJSONObject("medium");
                        youtubeModal = new YoutubeModal(jsonObject4.getString("title"),jsonObject3.getString("url"),jsonObject1.getString("id"));

                        arrayList.add(youtubeModal);
                    }
                    playlist_adapter.notifyDataSetChanged();

                }catch (Exception e){

                    e.printStackTrace();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

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
