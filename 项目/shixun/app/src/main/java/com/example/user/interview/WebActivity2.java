package com.example.user.interview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class WebActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.webview_layout2);

        WebView webView = findViewById (R.id.webView2);
        webView.loadUrl ("http://software.hebtu.edu.cn/");
    }
}
