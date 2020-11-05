package com.dhadrianwaletv.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.dhadrianwaletv.R;

public class AudioActivity extends Activity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_audio);

         // getActionBar().setDisplayHomeAsUpEnabled(true);
        //  https://soundcloud.com/parmeshardwar/

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setWebViewClient(new myWebClient());
        webView.loadUrl("https://soundcloud.com/parmeshardwar/");
        webView.getSettings().setJavaScriptEnabled(true);
    }

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyUp( int keyCode, KeyEvent event )
    {
        if( keyCode == KeyEvent.KEYCODE_BACK )
        {
            super.onBackPressed();
            return true;
        }
        return super.onKeyUp( keyCode, event );
    }
}
