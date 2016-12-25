package com.muthusoft.edi;

/**
 * Created by safiq on 28/01/16.
 */


import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class ServiceHandler {

    static String response = null;


    HttpURLConnection httpcon;


    public ServiceHandler() {

    }

    /**
     * Making service call
     * @url - url to make request
     * @method - http request method
     * */

    /**
     * Making service call
     *
     * @url - url to make request
     * @method - http request method
     * @params - http request params
     */
    public String makeServiceCall(String url,
                                  String jsonObject) {

        try {

            Log.v("jSon String", url + " - " + jsonObject);

            httpcon = (HttpURLConnection) ((new URL(url).openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            httpcon.setRequestMethod("POST");

            // HTTP connection time out

            httpcon.setRequestProperty("Connection", "keep-alive");
           // httpcon.setRequestProperty("User-Agent", "Android");
            httpcon.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            httpcon.setRequestProperty("Accept","*/*");
            httpcon.setConnectTimeout(100000);
            httpcon.connect();


//Write

            DataOutputStream wr = new DataOutputStream(
                    httpcon.getOutputStream());
            wr.writeBytes(jsonObject);
            wr.flush();
            wr.close();


//Read

            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();

            response = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (httpcon != null) {
                httpcon.disconnect();
            }
        }

        return response;
    }


}

