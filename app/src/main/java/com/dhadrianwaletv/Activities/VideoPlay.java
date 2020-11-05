package com.dhadrianwaletv.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dhadrianwaletv.R;

import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.PlayerConstants;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

public class VideoPlay extends AppCompatActivity {
//        implements YouTubePlayer.OnInitializedListener {
//    private static final int RECOVERY_REQUEST = 1;
//    private YouTubePlayerView youTubeView;
//    private android.support.v7.widget.Toolbar toolbar;

    //    YouTubePlayer youTubePlayer1;
//    private boolean isFullScreen;
    ProgressBar progressBar;
    String videoId = "", html = "";
    private Context context;
    private com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer youTubePlayer;




    private boolean playing = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_play);
        videoId = getIntent().getStringExtra("videoid");
        initYouTubePlayerView();


//        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
//        WebView webView = (WebView) findViewById(R.id.webview);
//        WebSettings ws = webView.getSettings();
//        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        ws.setPluginState(WebSettings.PluginState.ON);
//        ws.setJavaScriptEnabled(true);
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        webView.reload();
//        videoId = getIntent().getStringExtra("videoid");
//        html = getHTML(videoId);
//
//        webView.loadData(html, "text/html", "UTF-8");
//
//        WebClientClass webViewClient = new WebClientClass(progressBar);
//        webView.setWebViewClient(webViewClient);
//        WebChromeClient webChromeClient = new WebChromeClient();
//        webView.setWebChromeClient(webChromeClient);
//        webView.loadData(html, "text/html", "UTF-8");
    }
    private void initYouTubePlayerView() {
  YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.getPlayerUIController().showFullscreenButton(false);
        //youTubePlayerView.getPlayerUIController().enableLiveVideoUI(true);

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
                + "?&theme=dark&autohide=2&modestbranding=1&rel=0&showinfo=0&autoplay=1\fs=0\" frameborder=\"0\" "
                + "allowfullscreen autobuffer " + "controls onclick=\"this.play()\">\n" + "</iframe>\n";
        return html;
    }

    public class WebClientClass extends WebViewClient {
        ProgressBar ProgressBar = null;

        WebClientClass(ProgressBar progressBar) {
            ProgressBar = progressBar;
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
//
//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            super.onBackPressed();
//            return true;
//        }
//    else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
//            getWindow().getCurrentFocus();
//            WebView webView = (WebView) findViewById(R.id.webview);
//            WebSettings webSettings = webView.getSettings();
//            webSettings.setJavaScriptEnabled(true);
//
////            WebClientClass webViewClient = new WebClientClass(progressBar);
////            webView.setWebViewClient(webViewClient);
////            WebChromeClient webChromeClient = new WebChromeClient();
////            webView.setWebChromeClient(webChromeClient);
//            webView.loadData(html, "text/html", "UTF-8");
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
//        return super.onKeyUp(keyCode, event);
//    }
    public class CustomPlayerUIController extends AbstractYouTubePlayerListener implements YouTubePlayerFullScreenListener {




        CustomPlayerUIController(Context ctx, com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer youTubePlayer1) {

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

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        String s="";
//
//        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
//            s="Landscape";
//
//
//        }else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
//            s="Portrait";
//
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        if(isFullScreen==false){
//            super.onBackPressed();
//            Log.e("isFullScreen","under if condition");
//        }else if(isFullScreen==true){
//            isFullScreen = false;
//            youTubePlayer1.setFullscreen(false);
//            Log.e("isFullScreen","under else-if condition");
//        }else{
//            Log.e("isFullScreen","under else condition");
//        }
//    }

//    @Override
//    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
//        if(!b){
//            this.youTubePlayer1 =youTubePlayer;
//
//            String value=getIntent().getExtras().getString("videoid");
//            youTubePlayer.loadVideo(value);
//            //youTubePlayer.play();
//
//            youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
//                @Override
//                public void onFullscreen(boolean b) {
//                    isFullScreen=b;
//                    Log.e("isFullScreen","fullscreen condition-- "+isFullScreen);
//                    //  youTubePlayer.play();
//                }
//            });
//
//
//        }
//    }

//    @Override
//    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
//
//        if(youTubeInitializationResult.isUserRecoverableError()){
//            youTubeInitializationResult.getErrorDialog(this,RECOVERY_REQUEST).show();
//        }else{
//            String error=String.format(getString(R.string.player_error), youTubeInitializationResult.toString());
//            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == RECOVERY_REQUEST) {
//            // Retry initialization if user performed a recovery action
//            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
//        }
//    }
//
//    private YouTubePlayer.Provider getYouTubePlayerProvider()
//    {
//        return youTubeView;
//    }
//
////    @Override
////    public void onBackPressed(){
////        super.onBackPressed();
////        Intent intent = new Intent(VideoPlay.this,YoutubeHome.class);
////        startActivity(intent);
////        finish();
////    }
