package com.example.lostandfound.login;

import android.os.StrictMode;
import android.view.View;

import com.example.lostandfound.data.model.LoggedInUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        OkHttpClient client = new OkHttpClient();

        String url = "http://10.0.2.2:3000/login";
        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create("{\"username\":\"" + username +
                        "\", \"password\":\"" + password + "\"}", JSON))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                LoggedInUser user = new LoggedInUser("aaa", username);
                return new Result.Success<>(user);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return new Result.Error(new IOException("Error logging in"));
    }


    public void logout() {
        // TODO: revoke authentication
    }
}