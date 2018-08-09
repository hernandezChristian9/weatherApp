package com.christian_hernandez.medilink.weatherapplication.Api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by christian_hernandez on 8/9/2018.
 */

class HttpConnection {

    HttpsURLConnection urlConnection = null;
    URL url = null;
    InputStream inputStream = null;
    OutputStream outputStream = null;
    private String HttpResponse = "";

    public HttpConnection() {

    }

    protected String getHttpResponse(String SERVICE_URL) {
        this.httpsGetMethod(SERVICE_URL);
        return this.HttpResponse;
    }

    private void httpsGetMethod(String SERVICE_URL) {
        int responseCode = 0;
        String fouth_var = "";

        try {
            URL e = new URL(SERVICE_URL);
            HttpsURLConnection conn = (HttpsURLConnection)e.openConnection();
            conn.setReadTimeout(120000);
            conn.setConnectTimeout(120000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.connect();
            responseCode = conn.getResponseCode();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer response = new StringBuffer();

            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }

            this.HttpResponse = responseCode + "|" + response.toString();
        } catch(Exception tenth_var) {
            this.HttpResponse = responseCode + "|" + tenth_var.getMessage();
        }
    }
}
