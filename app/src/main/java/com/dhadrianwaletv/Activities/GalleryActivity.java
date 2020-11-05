package com.dhadrianwaletv.Activities;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.dhadrianwaletv.APIClient;
import com.dhadrianwaletv.Adapters.GalleryAdapter;
import com.dhadrianwaletv.ModalClasses.GalleryModel;
import com.dhadrianwaletv.R;
import com.dhadrianwaletv.RetrofitAPI;
import com.google.gson.JsonElement;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class GalleryActivity extends AppCompatActivity {
    private List<GalleryModel> dataList = new ArrayList<>();
    private GalleryAdapter mAdapter;
    RecyclerView recyclerView;
    GalleryModel galleryModel;
    ProgressBar mProgressBar;
    RetrofitAPI retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#a3cc2e"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#a3cc2e"));
            getWindow().setNavigationBarColor(Color.parseColor("#a3cc2e"));
        }

        mProgressBar = (ProgressBar) findViewById(R.id.google_progress_bishop);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_Bishop);
        ImageView iv_home = findViewById(R.id.home);

        if(isConnected(this)) {
            recyclerView.setVisibility(View.VISIBLE);
            mAdapter = new GalleryAdapter(dataList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration itemDecor = new DividerItemDecoration(this, VERTICAL);
            recyclerView.addItemDecoration(itemDecor);
            recyclerView.setAdapter(mAdapter);
            getData();
        }
        else
        {
            Toast.makeText(this, "Internet Not Available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown( int keyCode, KeyEvent event )
    {
        if( keyCode == KeyEvent.KEYCODE_BACK )
        {
            super.onBackPressed();
            return true;
        }
        return super.onKeyDown( keyCode, event );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    private void getData() {


        retrofitAPI = APIClient.getClientGallery().create(RetrofitAPI.class);
        Call<JsonElement> call = retrofitAPI.getGalleryList();
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressBar.setVisibility(View.INVISIBLE);

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if(jsonObject.has("categories")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("categories");
                            //   Log.e("Array Length",""+jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                //     Log.e("Name ",""+jsonObject1.getString("title"));
                                galleryModel = new GalleryModel(jsonObject1.getString("title")
                                        , jsonObject1.getString("id")
                                );
                                dataList.add(galleryModel);
                                //dbHelper.addOurBishops(galleryModel);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(GalleryActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {

                        e.printStackTrace();

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

                onBackPressed();
            }
        });
    }
}