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
    private San_Francisco sa_model;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
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

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
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
                //GET DETAILS FOR LONDON WEATHER
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


                //GET DETAILS FOR PRAGUE WEATHER
                JSONObject porgue = new ApiMethods(getContext()).GetPragueWeather();

                JSONObject porgue_data = new JSONObject(porgue.getString("Data").replaceAll("null","\"\""));

                JSONArray porgue_weather = porgue_data.getJSONArray("weather");
                JSONObject JSONPorgueWeather = porgue_weather.getJSONObject(0);
                prague_model =  new Prague();
                prague_model.setId(String.valueOf(JSONPorgueWeather.get("id")));
                prague_model.setMain(String.valueOf(JSONPorgueWeather.get("main")));
                prague_model.setDescription(String.valueOf(JSONPorgueWeather.get("description")));
                prague_model.setIcon(String.valueOf(JSONWeather.get("icon")));

                JSONObject porgue_main = new JSONObject(porgue_data.getString("main"));
                prague_model.setTemp(String.valueOf(porgue_main.get("temp")));

                JSONObject porgue_wind = new JSONObject(porgue_data.getString("wind"));
                prague_model.setSpeed(String.valueOf(porgue_wind.get("speed")));


                //GET DETAILS FOR SF WEATHER
                JSONObject sa = new ApiMethods(getContext()).GetSanFranciscoWeather();

                JSONObject sa_data = new JSONObject(sa.getString("Data").replaceAll("null","\"\""));

                JSONArray sa_weather = sa_data.getJSONArray("weather");
                JSONObject JSONSAWeather = sa_weather.getJSONObject(0);
                sa_model =  new San_Francisco();
                sa_model.setId(String.valueOf(JSONSAWeather.get("id")));
                sa_model.setMain(String.valueOf(JSONSAWeather.get("main")));
                sa_model.setDescription(String.valueOf(JSONSAWeather.get("description")));
                sa_model.setIcon(String.valueOf(JSONSAWeather.get("icon")));

                JSONObject sa_main = new JSONObject(sa_data.getString("main"));
                sa_model.setTemp(String.valueOf(sa_main.get("temp")));

                JSONObject sa_wind = new JSONObject(sa_data.getString("wind"));
                sa_model.setSpeed(String.valueOf(sa_wind.get("speed")));


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

            tv_sf_weather.setText(sa_model.getDescription());
            tv_sf_temp.setText(sa_model.getTemp() +"°C");

            progressDialog.dismiss();
        }



    }
}
