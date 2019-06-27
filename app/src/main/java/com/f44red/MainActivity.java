package com.f44red;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.adapter.RecyclerViewAdapter;
import com.model.Model;
import com.model.WPPost;
import com.onesignal.OneSignal;
import com.utils.InternetConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Main class of F44 Red application. It contains methods for creating view,
 * loading components and doing main activities like loading news headers with WP REST API and Retrofit.
 * @author Paweł Turoń
 */

public class MainActivity extends DrawerBaseActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Model> list;
    private RecyclerViewAdapter adapter;
    private String baseURL = "http://f44red.com";
    public static List<WPPost> mListPost;
    private SwipeRefreshLayout swipe;
    private long doubleTapClickMilis = 0L;
    private Toast toast;

    /**
     * onCreate method contains components for view.
     * @param savedInstanceState saves current state during using application.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneSignal.startInit(this).init();

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.frame_container);
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        list = new ArrayList<Model>();

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRetrofit();
            }
        });
        // Call Retrofit
        getRetrofit();

        adapter = new RecyclerViewAdapter(list, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * getRetrofit sets connection with F44 Red page and loads news.
     */

    protected void getRetrofit() {
        if (InternetConnection.checkInternetConnection(getApplicationContext())) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitArrayApi service = retrofit.create(RetrofitArrayApi.class);
            Call<List<WPPost>> call = service.getPostInfo();
            call.enqueue(new Callback<List<WPPost>>() {
                @Override
                public void onResponse(Call<List<WPPost>> call, Response<List<WPPost>> response) {
                    Log.e("MainActivity", "Response: " + response.body());
                    mListPost = response.body();
                    swipe.setRefreshing(false);
                    for (int i = 0; i < response.body().size(); i++) {
                        Log.e("Main", "Title: " + response.body().get(i).getTitle().getRendered() +
                                " " + response.body().get(i).getId());

                        String tmpDetails = response.body().get(i).getExcerpt().getRendered().toString();
                        tmpDetails = tmpDetails.replace("<p>", "");
                        tmpDetails = tmpDetails.replace("</p>", "");
                        tmpDetails = tmpDetails.replace("[&hellip;]", "");
                        tmpDetails = tmpDetails.replace("&#8211;", "");
                        tmpDetails = tmpDetails.replace("&#x2122;", "");

                        list.add(new Model(Model.IMAGE_TYPE, response.body().get(i).getTitle().getRendered(),
                                tmpDetails, response.body().get(i).getLinks().getWpFeaturedmedia().get(0).getHref()));
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<WPPost>> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "Brak połączenia z Internetem. Spróbuj póżniej", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(doubleTapClickMilis + 2000L > System.currentTimeMillis()){
                toast.cancel();
                super.onBackPressed();
            } else {
                toast = Toast.makeText(this, "Naciśnij wstecz aby wyjść z aplikacji", Toast.LENGTH_SHORT);
                toast.show();
            }
            doubleTapClickMilis = System.currentTimeMillis();
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

