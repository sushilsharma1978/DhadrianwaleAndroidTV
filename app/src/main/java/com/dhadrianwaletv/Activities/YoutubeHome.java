package com.dhadrianwaletv.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.dhadrianwaletv.R;

public class YoutubeHome extends AppCompatActivity {
    LinearLayout ll_allVideos,ll_playlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube_screen);

//        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setSubtitleTextColor(Color.WHITE);
        ll_allVideos = findViewById(R.id.ll_allVideos);
        ll_playlist = findViewById(R.id.ll_playlist);

        ll_allVideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YoutubeHome.this, AllVideos.class));
            }
        });

        ll_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YoutubeHome.this, YoutubePlaylist.class));
            }
        });
    }





//    @Override
//    public boolean onKeyUp( int keyCode, KeyEvent event )
//    {
//        if( keyCode == KeyEvent.KEYCODE_BACK )
//        {
//            super.onBackPressed();
//            return true;
//        }
//        if( keyCode == KeyEvent.KEYCODE_DPAD_LEFT )
//        {
//            if(tabLayout.hasFocus()) {
//                Log.e("tab position", tabLayout.getSelectedTabPosition() + "");
//                if (tabLayout.getSelectedTabPosition() == 0) {
//                    TabLayout.Tab tab = tabLayout.getTabAt(0);
//                    tab.select();
//                } else {
//                    TabLayout.Tab tab = tabLayout.getTabAt(1);
//                    tab.select();
//                }
//            }
//            else{
//                Log.e("Tab does not","have focus");
//            }
//            return true;
//        }
//        return super.onKeyUp( keyCode, event );
//    }


//    public boolean isNetworkConnected() {
//        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        Log.e("CM",cm.toString());
//        NetworkInfo ni = cm.getActiveNetworkInfo();
//        if (ni == null && !ni.isConnected()) {
//            Toast.makeText(this, "Please check your internet connection.Intenet is not working.", Toast.LENGTH_LONG).show();
//            return false;
//        } else
//            return true;
//    }



    @Override
    public void onBackPressed(){
       super.onBackPressed();
        Intent intent = new Intent(YoutubeHome.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        super.onBackPressed();
//        Intent intent = new Intent(YoutubeHome.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
//    }
}
