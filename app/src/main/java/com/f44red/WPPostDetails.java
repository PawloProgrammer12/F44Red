package com.f44red;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Post details like title and whole article which is displayed in web page.
 * @author Paweł Turoń
 */

public class WPPostDetails extends DrawerBaseActivity {
    private WebView webView;
    private SwipeRefreshLayout swipe;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.frame_container);
        getLayoutInflater().inflate(R.layout.post_details, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        Intent i = getIntent();
        int position = i.getExtras().getInt("itemPosition");

        Log.e("WpPostDetails", " Title: " + MainActivity.mListPost.get(position).getTitle().getRendered());

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebPage();
            }
        });

        loadWebPage();
    }

    /**
     * Loads web page.
     */

    public void loadWebPage(){
        Intent i = getIntent();
        int position = i.getExtras().getInt("itemPosition");

        webView = (WebView) findViewById(R.id.postWebView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(MainActivity.mListPost.get(position).getLink());
        webView.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "Brak połączenia z Internetem. Spróbuj później", Toast.LENGTH_SHORT).show();
            }

            public void onPageFinished(WebView view, String url){
                //Hide the SwipeReefreshLayout
                swipe.setRefreshing(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle","onStart invoked");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle","onResume invoked");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle","onPause invoked");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle","onStop invoked");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle","onRestart invoked");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle","onDestroy invoked");
    }
}
