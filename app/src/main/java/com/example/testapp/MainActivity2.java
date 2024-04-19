package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.clevertap.android.sdk.*;

public class MainActivity2 extends AppCompatActivity {

    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);

        browser=findViewById(R.id.webView);
        browser.setWebViewClient(new MyBrowser());
        browser.getSettings().setJavaScriptEnabled(true);
        browser.addJavascriptInterface(new CTWebInterface(CleverTapAPI.getDefaultInstance(this)),"my_webview");
        browser.loadUrl("https://nehayadav20.github.io/WebTest/");

    }

    private class MyBrowser extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}