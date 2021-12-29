package com.example.lostandfound.login;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.example.lostandfound.data.model.LoggedInUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        String url =  "http://192.168.1.8:3000/login";//"http://10.0.2.2:3000/login";
        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create("{\"username\":\"" + username +
                        "\", \"password\":\"" + password + "\"}", JSON))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {

                // store token
                String jsonData = response.body().string();
                storeToken(jsonData);

                //store current user
                LoggedInUser user = storeCurrentUser(jsonData);
                LoginRepository.getInstance(this).setLoggedInUser(user);
                return new Result.Success<>(user);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return new Result.Error(new IOException("Error logging in"));
    }

    public Result<LoggedInUser> register(String username, String password) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        OkHttpClient client = new OkHttpClient();

        String url = "http://192.168.1.8:3000/register";//"http://10.0.2.2:3000/register";
        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create("{\"username\":\"" + username +
                        "\", \"password\":\"" + password + "\"}", JSON))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {

                // store token
                String jsonData = response.body().string();
                storeToken(jsonData);

                //store current user
                LoggedInUser user = storeCurrentUser(jsonData);
                LoginRepository.getInstance(this).setLoggedInUser(user);
                return new Result.Success<>(user);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return new Result.Error(new IOException("An error occurred, try again..."));
    }

    public void logout() {
        LoginRepository.getInstance(this).setToken(null);
    }

    private void storeToken(String jsonData) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonData);
        String token = jsonObject.getString("token");
        LoginRepository.getInstance(this).setToken(token);
    }

    private LoggedInUser storeCurrentUser(String jsonData) throws JSONException, IOException {
        JSONObject jsonObject = new JSONObject(jsonData);

        OkHttpClient client = new OkHttpClient();
        String url = "http://192.168.1.8:3000/users/" + jsonObject.getInt("userId");
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();

        Gson gson = new Gson();
        jsonData = response.body().string();
        Type type = new TypeToken<LoggedInUser>() {
        }.getType();

        LoggedInUser user = gson.fromJson(jsonData, type);
        return user;
    }
}