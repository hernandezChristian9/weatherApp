package com.christian_hernandez.medilink.weatherapplication.Fragment;


import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;


import com.christian_hernandez.medilink.weatherapplication.Api.ApiMethods;
import com.christian_hernandez.medilink.weatherapplication.Api.GlobalVariables;
import com.christian_hernandez.medilink.weatherapplication.Model.Prague;
import com.christian_hernandez.medilink.weatherapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by christian_hernandez on 8/9/2018.
 */

public class PragueDetailsFragment extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Prague prague_details;

    TextView tv_current_temperature_field, tv_weather_field, tv_details_field;
    FragmentManager manager;
    ImageView iv_weather_icon;

    private Bitmap prague_icon = null;

    public PragueDetailsFragment() {
        // Required empty public constructor
    }

    public static PragueDetailsFragment newInstance(String param1, String param2) {
        PragueDetailsFragment fragment = new PragueDetailsFragment();
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
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            prague_details = (Prague) bundle.getSerializable("MY_BUNDLE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prague_details, container, false);
        manager = getFragmentManager();

        tv_current_temperature_field = (TextView) view.findViewById(R.id.tv_current_temperature_field);
        tv_weather_field = (TextView) view.findViewById(R.id.tv_weather_field);
        tv_details_field = (TextView) view.findViewById(R.id.tv_details_field);

        iv_weather_icon = (ImageView) view.findViewById(R.id.iv_weather_icon);

        tv_current_temperature_field.setText(GlobalVariables.prague_datalist.getTemp()+"°C");
        tv_weather_field.setText(GlobalVariables.prague_datalist.getMain());
        tv_details_field.setText(GlobalVariables.prague_datalist.getDescription());

        iv_weather_icon.setImageBitmap(GlobalVariables.prague_datalist.getIcons());


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
            InputStream is = null;
            try {
                //GET DETAILS FOR LONDON WEATHER
                JSONObject prague = new ApiMethods(getContext()).GetPragueWeather();

                JSONObject data = new JSONObject(prague.getString("Data").replaceAll("null","\"\""));

                JSONArray weather = data.getJSONArray("weather");
                JSONObject JSONWeather = weather.getJSONObject(0);
                GlobalVariables.prague_datalist = new Prague();
                GlobalVariables.prague_datalist.setId(String.valueOf(JSONWeather.get("id")));
                GlobalVariables.prague_datalist.setMain(String.valueOf(JSONWeather.get("main")));
                GlobalVariables.prague_datalist.setDescription(String.valueOf(JSONWeather.get("description")));
                GlobalVariables.prague_datalist.setIcon(String.valueOf(JSONWeather.get("icon")));

                JSONObject main = new JSONObject(data.getString("main"));
                GlobalVariables.prague_datalist.setTemp(String.valueOf(main.get("temp")));

                JSONObject wind = new JSONObject(data.getString("wind"));
                GlobalVariables.prague_datalist.setSpeed(String.valueOf(wind.get("speed")));

                String urlOfPragueicon = "http://openweathermap.org/img/w/"+GlobalVariables.prague_datalist.getIcon()+".png";
                try {
                    is = new URL(urlOfPragueicon).openStream();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                prague_icon = BitmapFactory.decodeStream(is);
                GlobalVariables.prague_datalist.setIcons(prague_icon);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv_current_temperature_field.setText(GlobalVariables.prague_datalist.getTemp()+"°C");
            tv_weather_field.setText(GlobalVariables.prague_datalist.getMain());
            tv_details_field.setText(GlobalVariables.prague_datalist.getDescription());

            iv_weather_icon.setImageBitmap(GlobalVariables.prague_datalist.getIcons());

            progressDialog.dismiss();
        }



    }
}
