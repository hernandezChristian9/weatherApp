package com.christian_hernandez.medilink.weatherapplication.Api;

import android.content.Context;

import com.christian_hernandez.medilink.weatherapplication.R;

import org.json.JSONObject;
import java.util.HashMap;

/**
 * Created by christian_hernandez on 8/9/2018.
 */

public class ApiMethods {
    Context mainContext;
    public ApiMethods(Context context) {
        this.mainContext = context;
    }

    public JSONObject GetLondonWeather() {
        String result = (new HttpConnection()).getHttpResponse(mainContext.getString(R.string.url_london));
        String[] split = result.split("\\|");
        HashMap<String, String> hash = new HashMap<>();
        hash.put("ResponseCode", split[0]);
        hash.put("Data", split[1]);
        return new JSONObject(hash);
    }

    public JSONObject GetPragueWeather() {
        String result = (new HttpConnection()).getHttpResponse(mainContext.getString(R.string.url_prague));
        String[] split = result.split("\\|");
        HashMap<String, String> hash = new HashMap<>();
        hash.put("ResponseCode", split[0]);
        hash.put("Data", split[1]);
        return new JSONObject(hash);
    }

    public JSONObject GetSanFranciscoWeather() {
        String result = (new HttpConnection()).getHttpResponse(mainContext.getString(R.string.url_san_francisco));
        String[] split = result.split("\\|");
        HashMap<String, String> hash = new HashMap<>();
        hash.put("ResponseCode", split[0]);
        hash.put("Data", split[1]);
        return new JSONObject(hash);
    }
}
