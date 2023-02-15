package com.antplay.antplayApp.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.antplay.antplayApp.models.OtpRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SendOtpAsyncTask extends AsyncTask<OtpRequest,Void,String> {
public OtpRequest otpRequest;
    public SendOtpAsyncTask(OtpRequest otpRequest) {
        this.otpRequest = otpRequest;
    }

    @Override
    protected String doInBackground(OtpRequest... otpRequests) {
        try {
            // Construct data
            String apiKey = "apikey=" + "NGY0NDY0NTk2NDM3NDM0MzRhNjkzNTU5NjU0OTUwNDY=";
            String message = "&message=" + "4579 is the OTP to verify your signup & login. It will be valid for the next 5 minutes. Please do not share your OTP for security reasons. - AntPlay";
            String sender = "&sender=" + "ANTPLY";
            String numbers = "&numbers=" + "919910985450";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: "+e;
        }

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("SEND_OTP",s);
    }
}
