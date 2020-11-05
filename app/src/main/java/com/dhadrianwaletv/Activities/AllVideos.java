package com.dhadrianwaletv.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
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

public class AllVideos extends AppCompatActivity {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    YoutubeModal youtubeModal;
    private GridView gv;
    private Grid_layoutAdapter adapter;
    private ArrayList<YoutubeModal> arrayList = new ArrayList<>();
    private String mParam1;
    private String mParam2;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_all_videos);

        gv=(GridView) findViewById(R.id.gridview);
       linearLayout=findViewById(R.id.ll_tryagin);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected(getApplicationContext())){
                    getVideos();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Oops no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapter= new Grid_layoutAdapter(this,arrayList);
        gv.setAdapter(adapter);

        if (isConnected(this)) {
            getVideos();
        }
        else {
            Toast.makeText(this, "No internet", Toast.LENGTH_SHORT).show();
        }

        gv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.e("keycode ",""+keyCode);
                return false;
            }
        });


        gv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                {
                    if (firstVisibleItem + visibleItemCount >= totalItemCount) {

                        SharedPreferences Pref = getSharedPreferences("MyPref",0);
                        String value=  Pref.getString("pageToken",null);
                        //  Log.e("getpagetoken",value.toString());
                        if(value==null){
//                  Toast.makeText(getContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
                        }else {
                            if (isConnected(getBaseContext())) {
                                getAllVideo(value);
                            }
                        }
                    }
                }
            }

        });
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


        }
        return super.onKeyUp(keyCode, event);
    }


    public void getVideos(){

        final  ProgressDialog progress;
        RetrofitAPI retrofitAPI;

        retrofitAPI = APIClient.getClient().create(RetrofitAPI.class);
        Call<JsonElement> call = retrofitAPI.getYouTubeVideos();

        progress = new ProgressDialog(this);
        progress.setMax(100);
        progress.setMessage("Loading Videos....");
        progress.setCancelable(false);
        progress.show();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {
                    if(response.isSuccessful()) {
                        gv.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                    // Log.e("reponse url",response.raw().toString());
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    if (jsonObject.has("nextPageToken")) {

                        String pagetoken = jsonObject.getString("nextPageToken");
                        Log.e("pagetoken", pagetoken.toString());
                        SharedPreferences sharedPref = getSharedPreferences("MyPref", 0);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("pageToken", pagetoken);
                        editor.commit();
                    }

                    JSONArray jsonArray = jsonObject.getJSONArray("items");

                    Log.e("Array length", jsonArray.length() + "");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonObject5 = jsonObject1.getJSONObject("id");
                        JSONObject jsonObject4 = jsonObject1.getJSONObject("snippet");
                        JSONObject jsonObject2 = jsonObject4.getJSONObject("thumbnails");
                        JSONObject jsonObject3 = jsonObject2.getJSONObject("medium");
                        youtubeModal = new YoutubeModal(jsonObject4.getString("title"), jsonObject3.getString("url"), jsonObject5.getString("videoId"));

                        arrayList.add(youtubeModal);
                    }
                    adapter.notifyDataSetChanged();
                    progress.dismiss();

                    }else{
                        progress.dismiss();
                        gv.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                   } catch (Exception e) {

                    e.printStackTrace();}
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

    public void getAllVideo(String value) {

        RetrofitAPI retrofitAPI;

        retrofitAPI = APIClient.getClient().create(RetrofitAPI.class);
        Call<JsonElement> call = retrofitAPI.getYouTubeVideosNext(value);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try{

                    // Log.e("reponse url",response.raw().toString());
                    JSONObject jsonObject= new JSONObject(response.body().toString());
                    if (jsonObject.has("nextPageToken")){

                        String pagetoken= jsonObject.getString("nextPageToken");
                        Log.e("pagetoken",pagetoken.toString());
                        SharedPreferences sharedPref = getSharedPreferences("MyPref",0);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("pageToken",pagetoken);
                        editor.commit();
                    }

                    JSONArray jsonArray=jsonObject.getJSONArray("items");

                    Log.e("Array length",jsonArray.length()+"");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject jsonObject5 = jsonObject1.getJSONObject("id");
                        Log.e("Video Id",jsonObject5.getString("videoId"));
                        JSONObject jsonObject4 = jsonObject1.getJSONObject("snippet");
                        Log.e("Title is",jsonObject4.getString("title"));
                        JSONObject jsonObject2 = jsonObject4.getJSONObject("thumbnails");
                        JSONObject jsonObject3 = jsonObject2.getJSONObject("medium");
                        Log.e("Image url",jsonObject3.getString("url"));
                        youtubeModal = new YoutubeModal(jsonObject4.getString("title"),jsonObject3.getString("url"),jsonObject5.getString("videoId"));

                        arrayList.add(youtubeModal);
                    }
                    adapter.notifyDataSetChanged();

                }catch (Exception e){

                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

}