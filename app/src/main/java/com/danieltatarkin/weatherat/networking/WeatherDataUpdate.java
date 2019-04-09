package com.danieltatarkin.weatherat.networking;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import android.util.Log;
import android.widget.Toast;

import com.danieltatarkin.weatherat.R;
import com.danieltatarkin.weatherat.receivers.ServiceResultReceiver;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WeatherDataUpdate extends JobIntentService {

    public static final int JOB_ID = 1000;
    public static final int JOB_FAILED_ID = 1002;
    private ResultReceiver resultReceiver;

    public static void enqueueWork(Context context, ServiceResultReceiver serviceResultReceiver, String cityName) {
        Intent intent = new Intent(context, WeatherDataUpdate.class);
        intent.putExtra("receiver", serviceResultReceiver);
        intent.putExtra("city", cityName);
        enqueueWork(context, WeatherDataUpdate.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        try {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            resultReceiver = intent.getParcelableExtra("receiver");
            Log.i("WeatherManager", "Executing work: " + intent);

            String API_KEY = getResources().getString(R.string.OPEN_WEATHER_MAP_API);
            String API_CALL = getResources().getString(R.string.API_CALL);
            String cityName = intent.getStringExtra("city");
            String url = API_CALL + cityName + "&units=metric&appid=" + API_KEY;
            Log.i("WeatherManager", url);

            // New OkHttpClient
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            ConnectivityManager connectivityManager =
                    (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                Response response = client.newCall(request).execute();
                String jsonDataResponse = response.body().string();
                Bundle bundle = new Bundle();
                bundle.putString("jsonData", jsonDataResponse);
                resultReceiver.send(JOB_ID, bundle);
                toast("Updated!");
            } else {
                toast("There is no network available, make sure you have data enabled");
                resultReceiver.send(JOB_FAILED_ID, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("WeatherManager", "Completed weather fetch ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    Handler handler = new Handler();

    // Helper for showing toast
    void toast(final CharSequence text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WeatherDataUpdate.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
