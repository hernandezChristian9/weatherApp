package com.christian_hernandez.medilink.weatherapplication.Fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
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

import com.christian_hernandez.medilink.weatherapplication.Api.ApiMethods;
import com.christian_hernandez.medilink.weatherapplication.R;

import com.christian_hernandez.medilink.weatherapplication.Model.London;
import com.christian_hernandez.medilink.weatherapplication.Model.Prague;
import com.christian_hernandez.medilink.weatherapplication.Model.San_Francisco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by christian_hernandez on 8/9/2018.
 */

public class MainFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String London_Info = "london_information";

    private LinearLayout LL_1, LL_2, LL_3;
    private TextView tv_london_weather, tv_london_temp, tv_prague_weather, tv_prague_temp, tv_sf_weather, tv_sf_temp;
    private ImageView iv_london_weather, iv_prague_weather, iv_sf_weather;

    private London london_data;
    private Prague prague_model;
    private San_Francisco sanfrancisco_model;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            GetDetails task = new GetDetails();
            task.execute();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
            JSONObject london = new ApiMethods(getContext()).GetLondonWeather();
            try {

                JSONObject data = new JSONObject(london.getString("Data").replaceAll("null","\"\""));

                JSONArray weather = data.getJSONArray("weather");
                JSONObject JSONWeather = weather.getJSONObject(0);
                london_data =  new London();
                london_data.setId(String.valueOf(JSONWeather.get("id")));
                london_data.setMain(String.valueOf(JSONWeather.get("main")));
                london_data.setDescription(String.valueOf(JSONWeather.get("description")));
                london_data.setIcon(String.valueOf(JSONWeather.get("icon")));

                JSONObject main = new JSONObject(data.getString("main"));
                london_data.setTemp(String.valueOf(main.get("temp")));

                JSONObject wind = new JSONObject(data.getString("wind"));
                london_data.setSpeed(String.valueOf(wind.get("speed")));



                JSONObject prague = new ApiMethods(getContext()).GetPragueWeather();

                JSONObject prague_data = new JSONObject(prague.getString("Data").replaceAll("null","\"\""));

                JSONArray prague_weather = prague_data.getJSONArray("weather");
                JSONObject JSONPorgueWeather = prague_weather.getJSONObject(0);
                prague_model =  new Prague();
                prague_model.setId(String.valueOf(JSONPorgueWeather.get("id")));
                prague_model.setMain(String.valueOf(JSONPorgueWeather.get("main")));
                prague_model.setDescription(String.valueOf(JSONPorgueWeather.get("description")));
                prague_model.setIcon(String.valueOf(JSONWeather.get("icon")));

                JSONObject prague_main = new JSONObject(prague_data.getString("main"));
                prague_model.setTemp(String.valueOf(prague_main.get("temp")));

                JSONObject prague_wind = new JSONObject(prague_data.getString("wind"));
                prague_model.setSpeed(String.valueOf(prague_wind.get("speed")));



                JSONObject SanFrancisco = new ApiMethods(getContext()).GetSanFranciscoWeather();

                JSONObject SanFrancisco_data = new JSONObject(SanFrancisco.getString("Data").replaceAll("null","\"\""));

                JSONArray SanFrancisco_weather = SanFrancisco_data.getJSONArray("weather");
                JSONObject JSONSAWeather = SanFrancisco_weather.getJSONObject(0);
                sanfrancisco_model =  new San_Francisco();
                sanfrancisco_model.setId(String.valueOf(JSONSAWeather.get("id")));
                sanfrancisco_model.setMain(String.valueOf(JSONSAWeather.get("main")));
                sanfrancisco_model.setDescription(String.valueOf(JSONSAWeather.get("description")));
                sanfrancisco_model.setIcon(String.valueOf(JSONSAWeather.get("icon")));

                JSONObject sa_main = new JSONObject(SanFrancisco_data.getString("main"));
                sanfrancisco_model.setTemp(String.valueOf(sa_main.get("temp")));

                JSONObject sa_wind = new JSONObject(SanFrancisco_data.getString("wind"));
                sanfrancisco_model.setSpeed(String.valueOf(sa_wind.get("speed")));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv_london_weather.setText(london_data.getDescription());
            tv_london_temp.setText(london_data.getTemp() +"°C");

            tv_prague_temp.setText(prague_model.getDescription());
            tv_prague_temp.setText(prague_model.getTemp() + "°C" );

            tv_sf_weather.setText(sanfrancisco_model.getDescription());
            tv_sf_temp.setText(sanfrancisco_model.getTemp() +"°C");

            progressDialog.dismiss();
        }



    }
}
