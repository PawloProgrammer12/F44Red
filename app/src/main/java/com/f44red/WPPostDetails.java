package com.f44red;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.model.Media;
import com.utils.InternetConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Post details like title and whole article which is displayed in web page.
 * @author Paweł Turoń
 */

public class WPPostDetails extends DrawerBaseActivity {
    private WebView webView;
    private TextView postTitle;
    private ImageView postImage;
    private Toolbar postToolbar;

    public static Intent createIntent(Context context, int id, int featuredMedia, String title, String excerpt, String content){
        Intent intent = new Intent(context, WPPostDetails.class);
        intent.putExtra("de.dominikwieners.androidhive.postId", id);
        intent.putExtra("de.dominikwieners.androidhive.featuredMedia",featuredMedia);
        intent.putExtra("de.dominikwieners.androidhive.postExcerpt", excerpt);
        intent.putExtra("de.dominikwieners.androidhive.postTitle", title);
        intent.putExtra("de.dominikwieners.androidhive.postContent",content);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.frame_container);
        getLayoutInflater().inflate(R.layout.post_details, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        // Get Intent
        int id = (int) getIntent().getSerializableExtra("com.f44red.postId");
        int featuredMedia = (int) getIntent().getSerializableExtra("com.f44red.featuredMedia");
        String title = getIntent().getSerializableExtra("com.f44red.postTitle").toString();
        String content = getIntent().getSerializableExtra("com.f44red.postContent").toString().replaceAll("\\\\n", "<br>").replaceAll("\\\\r", "").replaceAll("\\\\", "");

        initToolbar(title, id);
        initPost(title);
        initWebView(content);

        if(InternetConnection.checkInternetConnection(getApplicationContext())){
            RetrofitArrayApi api = WPClient.getApiService();
            Call<Media> call = api.getPostThumbnail(featuredMedia);
            call.enqueue(new Callback<Media>() {
                @Override
                public void onResponse(Call<Media> call, Response<Media> response) {
                    Log.d("ResponseMediaCode", "Status" + response.code());
                    if(response.code() != 404){
                        Media media = response.body();
                        String mediaUrl = media.getGuid().get("rendered").toString().replaceAll("\"", "");

                        Glide.with(getApplicationContext()).load(mediaUrl)
                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(postImage);
                    }else{

                    }
                }

                @Override
                public void onFailure(Call<Media> call, Throwable t) {
                    Log.d("RetrofitResponse", "Error");
                }
            });
        } else {
            Toast.makeText(this, "Brak połączenia z Internetem. Spróbuj póżniej", Toast.LENGTH_SHORT).show();
        }
    }

    private void initPost(String title){
        postImage = (ImageView) findViewById(R.id.post_backdrop);
        postTitle = (TextView) findViewById(R.id.post_title);
        postTitle.setText(title);
        webView = (WebView) findViewById(R.id.postWebView);
    }

    private class MyWebView extends WebViewClient {

        @SuppressLint("NewApi")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }
    }

    //Init Toolbar
    @SuppressLint("NewApi")
    private void initToolbar(String title, int id){

        //Set StatusBarColor transparent
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //Set Toolbar
        postToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.postToolbar);
        setSupportActionBar(postToolbar);
        initCollapsingToolbar(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        postToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initCollapsingToolbar(final String title){
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.post_collapsing_toolbar);
        collapsingToolbar.setTitle("Szczegóły postu");

    }

    private void initWebView(String content){

        //Set Html content
        content = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" +
                "<script src=\"prism.js\"></script>" +
                "<div class=\"content\">" + content+ "</div>";


        Log.d("WebViewContent", content);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webView.loadDataWithBaseURL("file:///android_asset/*",content, "text/html; charset=utf-8", "UTF-8", null);
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
