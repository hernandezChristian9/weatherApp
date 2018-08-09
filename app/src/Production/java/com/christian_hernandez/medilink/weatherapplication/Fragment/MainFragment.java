package com.christian_hernandez.medilink.weatherapplication.Fragment;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.christian_hernandez.medilink.weatherapplication.R;

import com.christian_hernandez.medilink.weatherapplication.Api.ApiMethods;
import com.christian_hernandez.medilink.weatherapplication.Api.GlobalVariables;
import com.christian_hernandez.medilink.weatherapplication.Model.London;
import com.christian_hernandez.medilink.weatherapplication.Model.Prague;
import com.christian_hernandez.medilink.weatherapplication.Model.San_Francisco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by christian_hernandez on 8/9/2018.
 */

public class MainFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentManager fragManager;

    private LinearLayout LL_1, LL_2, LL_3;
    private TextView tv_london_weather, tv_london_temp, tv_prague_weather, tv_prague_temp, tv_sf_weather, tv_sf_temp;
    private ImageView iv_london_weather, iv_prague_weather, iv_sf_weather, ic_refresh;

    private London london_model;
    private Prague prague_model;
    private San_Francisco sanfrancisco_model;
    private Bitmap london_icon = null;
    private Bitmap prague_icon = null;
    private Bitmap sanfrancisco_icon = null;

    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        fragManager = getFragmentManager();
        LL_1 = (LinearLayout)view.findViewById(R.id.LL_1);
        LL_2 = (LinearLayout)view.findViewById(R.id.LL_2);
        LL_3 = (LinearLayout)view.findViewById(R.id.LL_3);

        tv_london_weather = (TextView) view.findViewById(R.id.tv_london_weather);
        tv_london_temp = (TextView) view.findViewById(R.id.tv_london_temp);
        tv_prague_weather = (TextView) view.findViewById(R.id.tv_prague_weather);
        tv_prague_temp = (TextView) view.findViewById(R.id.tv_prague_temp);
        tv_sf_weather = (TextView) view.findViewById(R.id.tv_sf_weather);
        tv_sf_temp = (TextView) view.findViewById(R.id.tv_sf_temp);

        iv_london_weather = (ImageView) view.findViewById(R.id.iv_london_weather);
        iv_prague_weather = (ImageView) view.findViewById(R.id.iv_prague_weather);
        iv_sf_weather = (ImageView) view.findViewById(R.id.iv_sf_weather);
        ic_refresh = (ImageView) view.findViewById(R.id.ic_refresh);

        GetDetails task = new GetDetails();
        task.execute();
        LL_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherDetailsFragment weatherDetailsFragment = new WeatherDetailsFragment();
                FragmentTransaction transaction = fragManager.beginTransaction();
                transaction.replace(R.id.container, weatherDetailsFragment, "B");
                transaction.commit();
            }
        });

        LL_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PragueDetailsFragment pragueDetailsFragment = new PragueDetailsFragment();
                FragmentTransaction transaction = fragManager.beginTransaction();
                transaction.replace(R.id.container, pragueDetailsFragment, "C");
                transaction.commit();
            }
        });

        LL_3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SanFranciscoDetailsFragment sanFranciscoDetailsFragment = new SanFranciscoDetailsFragment();
                FragmentTransaction transaction = fragManager.beginTransaction();
                transaction.replace(R.id.container, sanFranciscoDetailsFragment, "D");
                transaction.commit();
            }
        });

        ic_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetDetails task = new GetDetails();
                task.execute();
            }
        });

        return view;
    }

    private class GetDetails extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog = new ProgressDialog(getContext());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Weather App Message");
            progressDialog.setMessage("Gathering Information..");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            InputStream inputStream = null;
            try {
                JSONObject london = new ApiMethods(getContext()).GetLondonWeather();

                JSONObject data = new JSONObject(london.getString("Data").replaceAll("null","\"\""));

                JSONArray weather = data.getJSONArray("weather");
                JSONObject JSONWeather = weather.getJSONObject(0);
                GlobalVariables.london_datalist =  new London();
                GlobalVariables.london_datalist.setId(String.valueOf(JSONWeather.get("id")));
                GlobalVariables.london_datalist.setMain(String.valueOf(JSONWeather.get("main")));
                GlobalVariables.london_datalist.setDescription(String.valueOf(JSONWeather.get("description")));
                GlobalVariables.london_datalist.setIcon(String.valueOf(JSONWeather.get("icon")));

                JSONObject main = new JSONObject(data.getString("main"));
                GlobalVariables.london_datalist.setTemp(String.valueOf(main.get("temp")));

                JSONObject wind = new JSONObject(data.getString("wind"));
                GlobalVariables.london_datalist.setSpeed(String.valueOf(wind.get("speed")));

                String urlOfLondonicon = "http://openweathermap.org/img/w/"+GlobalVariables.london_datalist.getIcon()+".png";
                try {
                    inputStream = new URL(urlOfLondonicon).openStream();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                GlobalVariables.london_datalist.setIcons(BitmapFactory.decodeStream(inputStream));


                JSONObject prague = new ApiMethods(getContext()).GetPragueWeather();

                JSONObject prague_data = new JSONObject(prague.getString("Data").replaceAll("null","\"\""));

                JSONArray prague_weather = prague_data.getJSONArray("weather");
                JSONObject JSONPorgueWeather = prague_weather.getJSONObject(0);
                GlobalVariables.prague_datalist =  new Prague();
                GlobalVariables.prague_datalist.setId(String.valueOf(JSONPorgueWeather.get("id")));
                GlobalVariables.prague_datalist.setMain(String.valueOf(JSONPorgueWeather.get("main")));
                GlobalVariables.prague_datalist.setDescription(String.valueOf(JSONPorgueWeather.get("description")));
                GlobalVariables.prague_datalist.setIcon(String.valueOf(JSONPorgueWeather.get("icon")));

                JSONObject prague_main = new JSONObject(prague_data.getString("main"));
                GlobalVariables.prague_datalist.setTemp(String.valueOf(prague_main.get("temp")));

                JSONObject prague_wind = new JSONObject(prague_data.getString("wind"));
                GlobalVariables.prague_datalist.setSpeed(String.valueOf(prague_wind.get("speed")));

                String urlOfPragueicon = "http://openweathermap.org/img/w/"+GlobalVariables.prague_datalist.getIcon()+".png";
                try {
                    inputStream = new URL(urlOfPragueicon).openStream();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                GlobalVariables.prague_datalist.setIcons(BitmapFactory.decodeStream(inputStream));



                JSONObject SanFrancisco = new ApiMethods(getContext()).GetSanFranciscoWeather();

                JSONObject SanFrancisco_data = new JSONObject(SanFrancisco.getString("Data").replaceAll("null","\"\""));

                JSONArray SanFrancisco_weather = SanFrancisco_data.getJSONArray("weather");
                JSONObject JSONSAWeather = SanFrancisco_weather.getJSONObject(0);
                GlobalVariables.san_francisco_datalist =  new San_Francisco();
                GlobalVariables.san_francisco_datalist.setId(String.valueOf(JSONSAWeather.get("id")));
                GlobalVariables.san_francisco_datalist.setMain(String.valueOf(JSONSAWeather.get("main")));
                GlobalVariables.san_francisco_datalist.setDescription(String.valueOf(JSONSAWeather.get("description")));
                GlobalVariables.san_francisco_datalist.setIcon(String.valueOf(JSONSAWeather.get("icon")));

                JSONObject sa_main = new JSONObject(SanFrancisco_data.getString("main"));
                GlobalVariables.san_francisco_datalist.setTemp(String.valueOf(sa_main.get("temp")));

                JSONObject sa_wind = new JSONObject(SanFrancisco_data.getString("wind"));
                GlobalVariables.san_francisco_datalist.setSpeed(String.valueOf(sa_wind.get("speed")));

                String urlOfSanFranciscoIcon = "http://openweathermap.org/img/w/"+GlobalVariables.san_francisco_datalist.getIcon()+".png";
                try {
                    inputStream = new URL(urlOfSanFranciscoIcon).openStream();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                GlobalVariables.san_francisco_datalist.setIcons(BitmapFactory.decodeStream(inputStream));



            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv_london_weather.setText(GlobalVariables.london_datalist.getMain());
            tv_london_temp.setText(GlobalVariables.london_datalist.getTemp() +"°C");
            iv_london_weather.setImageBitmap(GlobalVariables.london_datalist.getIcons());

            tv_prague_weather.setText(GlobalVariables.prague_datalist.getMain());
            tv_prague_temp.setText(GlobalVariables.prague_datalist.getTemp() + "°C" );
            iv_prague_weather.setImageBitmap(GlobalVariables.prague_datalist.getIcons());

            tv_sf_weather.setText(GlobalVariables.san_francisco_datalist.getMain());
            tv_sf_temp.setText(GlobalVariables.san_francisco_datalist.getTemp() +"°C");
            iv_sf_weather.setImageBitmap(GlobalVariables.san_francisco_datalist.getIcons());

            progressDialog.dismiss();
        }



    }
}
