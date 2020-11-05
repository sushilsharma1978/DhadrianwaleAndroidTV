package com.dhadrianwaletv.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dhadrianwaletv.R;

import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.PlayerConstants;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerFullScreenListener;

import java.util.Timer;
import java.util.TimerTask;


public class LiveVideo extends AppCompatActivity {
    ProgressBar progressBar;
    String videoId = "", html = "";
    WebView webView;
   private boolean select=true;

    private Context context;
    private YouTubePlayer youTubePlayer;




    private boolean playing = true;
   // private final String liveVideoId = "2ccaHpy5Ewo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_live_video);
        videoId = getIntent().getStringExtra("videoid");
        initYouTubePlayerView();


    }
    private void initYouTubePlayerView() {
      YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.getPlayerUIController().showFullscreenButton(false);
        youTubePlayerView.getPlayerUIController().enableLiveVideoUI(true);

        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.initialize(youTubePlayer -> {

            CustomPlayerUIController customPlayerUIController = new CustomPlayerUIController(this ,youTubePlayer);

            youTubePlayer.addListener(customPlayerUIController);

            youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady() {
                    youTubePlayer.loadVideo(videoId,0f);
                }
            });
        }, true);
    }
    public String getHTML(String videoId) {
        String html = "<iframe class=\"youtube-player\" " + "style=\"border: 0; width: 100%; height: 96%;"
                + "padding:0px; margin:0px\" " + "id=\"ytplayer\" type=\"text/html\" "
                + "src=\"http://www.youtube.com/embed/" + videoId
                + "?&theme=dark&autohide=2&modestbranding=1&showinfo=0&rel=0&controls=0&autoplay=1\fs=0\" frameborder=\"0\" "
                + "allowfullscreen autobuffer " + "controls onclick=\"this.play()\">\n" + "</iframe>\n";
        return html;
    }

    public class WebClientClass extends WebViewClient {
        android.widget.ProgressBar ProgressBar = null;

        WebClientClass(ProgressBar progressBar) {
            ProgressBar = progressBar;
        }

        public WebClientClass() {

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            ProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            ProgressBar.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(getHTML(videoId));
            return true;
        }

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
 boolean handler = false;
        int keyCode = event.getKeyCode();
        Log.e("==========", "");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
      startActivity(new Intent(LiveVideo.this, MainActivity.class));
            finish();


          super.onBackPressed();
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            if (event.getAction() == KeyEvent.ACTION_UP){
                handler = true;
                if(playing) youTubePlayer.pause();
                else youTubePlayer.play();

                playing = !playing;
                Log.d("press me","");
            }


           // forceAClick();



        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            handler = true;


        } else if (keyCode == KeyEvent.KEYCODE_BUTTON_A) {
           // forceAClick();
        }


        return handler || super.dispatchKeyEvent(event);
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            super.onBackPressed();
//            return true;
//        }
//        else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
////            getWindow().getCurrentFocus();
////            WebView webView = (WebView) findViewById(R.id.webview);
////            WebSettings webSettings = webView.getSettings();
////            webSettings.setJavaScriptEnabled(true);
////
//////            WebClientClass webViewClient = new WebClientClass(progressBar);
//////            webView.setWebViewClient(webViewClient);
//////            WebChromeClient webChromeClient = new WebChromeClient();
//////            webView.setWebChromeClient(webChromeClient);
////            webView.loadData(html, "text/html", "UTF-8");
//
//            forceAClick();
//
//        } else if (keyCode == KeyEvent.KEYCODE_BUTTON_A) {
//            WebView webView = (WebView) findViewById(R.id.webview);
//            WebSettings webSettings = webView.getSettings();
//            webSettings.setJavaScriptEnabled(true);
//        }
//        else if(keyCode == KeyEvent.KEYCODE_BACK){
//            super.onBackPressed();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private void forceAClick() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Display display = getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        int width = size.x;
                        int height = size.y;


                        // Obtain MotionEvent object
                        long downTime = SystemClock.uptimeMillis();
                        long eventTime = SystemClock.uptimeMillis() + 100;
                        float x = width / 2;
                        float y = height / 2;
                        // List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
                        int metaState = 0;
                        MotionEvent motionEvent = MotionEvent.obtain(
                                downTime,
                                eventTime,
                                MotionEvent.ACTION_DOWN,
                                x,
                                y,
                                metaState
                        );

                        webView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                Log.d("on touch", "touched down");
                                return false;
                            }
                        });

                      webView.dispatchTouchEvent(motionEvent);
                    }
                });
            }
        }, 1);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Display display = getWindowManager().getDefaultDisplay();
                        Point size = new Point();
                        display.getSize(size);
                        int width = size.x;
                        int height = size.y;


                        // Obtain MotionEvent object
                        long downTime = SystemClock.uptimeMillis();
                        long eventTime = SystemClock.uptimeMillis() + 100;
                        float x = width / 2;
                        float y = height / 2;
                        // List of meta states found here: developer.android.com/reference/android/view/KeyEvent.html#getMetaState()
                        int metaState = 0;
                        MotionEvent motionEvent = MotionEvent.obtain(
                                downTime,
                                eventTime,
                                MotionEvent.ACTION_UP,
                                x,
                                y,
                                metaState
                        );

                        webView.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                Log.d("on touch", "touched up");
                                return false;
                            }
                        });

                        webView.dispatchTouchEvent(motionEvent);


                    }
                });
            }
        }, 120);
    }
    public class CustomPlayerUIController extends AbstractYouTubePlayerListener implements YouTubePlayerFullScreenListener {




        CustomPlayerUIController(Context ctx,YouTubePlayer youTubePlayer1) {

           context = ctx;
             youTubePlayer = youTubePlayer1;


          //  initViews();
        }

//        private void initViews() {
//
//
//
//
//
//            playPauseButton.setOnClickListener( (view) -> {
//                if(playing) youTubePlayer.pause();
//                else youTubePlayer.play();
//
//                playing = !playing;
//            });
//
//
//        }

        @Override
        public void onReady() {
           // progressbar.setVisibility(View.GONE);
        }

        @Override
        public void onStateChange(@PlayerConstants.PlayerState.State int state) {
            //if(state == PlayerConstants.PlayerState.PLAYING || state == PlayerConstants.PlayerState.PAUSED || state == PlayerConstants.PlayerState.VIDEO_CUED)
             //   panel.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
           // else
         //   if(state == PlayerConstants.PlayerState.BUFFERING)
              //  panel.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onCurrentSecond(float second) {
           // videoCurrentTimeTextView.setText(second+"");
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onVideoDuration(float duration) {
           // videoDurationTextView.setText(duration+"");
        }

        @Override
        public void onYouTubePlayerEnterFullScreen() {

        }

        @Override
        public void onYouTubePlayerExitFullScreen() {


        }
    }



}

