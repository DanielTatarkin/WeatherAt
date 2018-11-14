package com.danieltatarkin.weatherat.ui.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danieltatarkin.weatherat.R;
import com.danieltatarkin.weatherat.models.WeatherModel;
import com.danieltatarkin.weatherat.networking.WeatherDataUpdate;
import com.danieltatarkin.weatherat.receivers.ServiceResultReceiver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.danieltatarkin.weatherat.networking.WeatherDataUpdate.JOB_FAILED_ID;
import static com.danieltatarkin.weatherat.networking.WeatherDataUpdate.JOB_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowFragment extends Fragment implements ServiceResultReceiver.Receiver {

    private ServiceResultReceiver serviceResultReceiver;

    private TextView cityTextView;
    private TextView tempTextView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView dateTextView;
    private ImageView weatherIconImageView;
    private TextView conditionTextView;
    private TextView humidityValueTV;
    private TextView precipValueTV;
    private TextView windValueTV;

    public NowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now, container, false);

        cityTextView = view.findViewById(R.id.city_textview);
        dateTextView = view.findViewById(R.id.date_textview);
        tempTextView = view.findViewById(R.id.temperature_textview);
        conditionTextView = view.findViewById(R.id.condition_textview);
        weatherIconImageView = view.findViewById(R.id.weather_imageview);
        humidityValueTV = view.findViewById(R.id.humid_value_textview);
        precipValueTV = view.findViewById(R.id.precip_value_textview);
        windValueTV = view.findViewById(R.id.wind_value_textview);


        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.bringToFront();
        setupReeveivers();
        updateWeather();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateWeather();
            }
        });

        return view;
    }

    private void setupReeveivers() {
        serviceResultReceiver = new ServiceResultReceiver(new Handler());
        serviceResultReceiver.setReceiver(this);
    }

    private void updateWeather() {
        WeatherDataUpdate.enqueueWork(getActivity(), serviceResultReceiver, "Moscow");
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd", Locale.US);
        dateTextView.setText(dateFormat.format(date));
        weatherIconImageView.setImageResource(R.drawable.day_forecast_sun_sunny_weather);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case JOB_ID:
                swipeRefreshLayout.setRefreshing(false);
                updateViews(resultData.getString("jsonData"));
            case JOB_FAILED_ID:
                swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void updateViews(String jsonData) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        WeatherModel model = gson.fromJson(jsonData, WeatherModel.class);

        String currTemp = Long.toString(Math.round(model.getMain().getTemperature()));

        cityTextView.setText(model.getCityName());
        tempTextView.setText(currTemp);
        conditionTextView.setText(model.getCurrWeatherInfo().get(0).getDescription().toUpperCase());
        humidityValueTV.setText(model.getMain().getHumidity()+"%");
        precipValueTV.setText(model.getCurrWeatherInfo().get(0).getCondition());
        windValueTV.setText(model.getWindInfo().getSpeed()+" m/s");
    }
}
