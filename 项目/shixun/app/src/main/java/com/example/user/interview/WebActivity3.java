package com.example.user.interview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebActivity3 extends AppCompatActivity {
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.webview_layout3);
        WebView webView = findViewById (R.id.webView3);
        webView.loadUrl ("http://library.hebtu.edu.cn/");
    }
}
