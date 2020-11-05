/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.dhadrianwaletv.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.dhadrianwaletv.APIClient;
import com.dhadrianwaletv.R;
import com.dhadrianwaletv.RetrofitAPI;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private String video_id;
    LinearLayout ll_livetv,ll_audio,ll_youtube,ll_facebook,ll_gallery,ll_contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_livetv = findViewById(R.id.ll_livetv);

//       ll_audio = findViewById(R.id.ll_audio);
        ll_youtube = findViewById(R.id.ll_youtube);
        ll_gallery = findViewById(R.id.ll_gallery);
        ll_contact = findViewById(R.id.ll_contact);

        ll_livetv.setOnClickListener(this);
//        ll_audio.setOnClickListener(this);
        ll_youtube.setOnClickListener(this);
        ll_gallery.setOnClickListener(this);
        ll_contact.setOnClickListener(this);

        ll_livetv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
            }
        });
    }

    public void getLiveVideo(){
        RetrofitAPI retrofitAPI;
        retrofitAPI = APIClient.getClient().create(RetrofitAPI.class);
        Call<JsonElement> call = retrofitAPI.getYouTubeLive();

        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMax(100);
        progress.setMessage("Loading Live Video....");
        progress.setCancelable(false);
        progress.show();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                try {

                    Log.e("reponse url",response.raw().toString());
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            JSONObject jsonObject2 = jsonObject1.getJSONObject("id");
                            video_id = jsonObject2.getString("videoId");

                            Intent intent = new Intent(MainActivity.this, LiveVideo.class);
                            intent.putExtra("videoid", video_id);
                            startActivity(intent);
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No Live Video is Available", Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){

                    e.printStackTrace();
                }
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progress.dismiss();
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onKeyDown( int keyCode, KeyEvent event )
    {
        if( keyCode == KeyEvent.KEYCODE_BACK )
        {
            super.onBackPressed();
            return true;
        }
        if( keyCode ==  KeyEvent.KEYCODE_DPAD_CENTER)
        {
            if (event.getAction() == KeyEvent.ACTION_UP){
                Log.e("Center","button clicked");
                Log.e("current focused view",""+getCurrentFocus().getId());
                getWindow().getCurrentFocus();
            }

        }
        if( keyCode ==  KeyEvent.KEYCODE_DPAD_LEFT)
        {

        }
        return super.onKeyDown( keyCode, event );
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.ll_livetv:
                getLiveVideo();
                break;
//            case R.id.ll_audio:
//                startActivity(new Intent(MainActivity.this,AudioActivity.class));
//                break;
            case R.id.ll_youtube:
                startActivity(new Intent(MainActivity.this,YoutubeHome.class));
                break;

            case R.id.ll_gallery:
                startActivity(new Intent(MainActivity.this,GalleryActivity.class));
                break;

            case R.id.ll_contact:
                startActivity(new Intent(MainActivity.this,ContactUsActivity.class));
                break;
        }
    }
}
