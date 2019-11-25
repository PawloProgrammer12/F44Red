package com.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import com.f44red.MainActivity;
import com.f44red.RetrofitArrayApi;
import com.model.WPPost;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public abstract class NotificationService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Retrofit retrofitService = new Retrofit.Builder()
                .baseUrl(MainActivity.baseURL)
                .build();

        RetrofitArrayApi service = retrofitService.create(RetrofitArrayApi.class);
        Call<List<WPPost>> call = service.getPostInfo();
        call.enqueue(new Callback<List<WPPost>>() {
            @Override
            public void onResponse(Call<List<WPPost>> call, Response<List<WPPost>> response) {
                System.out.println("Success!");
                response.body().get(0);

                // Restore preferences
                Context ctxt = getApplicationContext();
                SharedPreferences settings = ctxt.getSharedPreferences("myShare", ctxt.MODE_PRIVATE);
                int last_post_id = settings.getInt("last_post_id", 0);

                if ( last_post_id != response.body().get(0).getId() ) {
                    // We need an Editor object to make preference changes.
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("last_post_id", response.body().get(0).getId());

                    // Commit the edits!
                    editor.apply();

                    if (last_post_id != 0) {
                        // Push notification goes here
                        Context context = getApplicationContext();
                        NotificationCompat.Builder mBuilder =
                                new NotificationCompat.Builder(context)
                                        .setContentTitle("Un nouvel article est disponible")
                                        .setContentText(response.body().get(0).getTitle().getRendered());
                        // Sets an ID for the notification
                        int mNotificationId = 001;
                        // Gets an instance of the NotificationManager service
                        NotificationManager mNotifyMgr =
                                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                        // Builds the notification and issues it.
                        mNotifyMgr.notify(mNotificationId, mBuilder.build());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<WPPost>> call, Throwable t) {
                System.out.println("Fail!");
                System.out.println(t.getStackTrace());
            }
        });
        return Service.START_NOT_STICKY;
    }
}
