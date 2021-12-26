package com.example.lostandfound.login;

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

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    Connection connection;
    String res = "";

    public Result<LoggedInUser> login(String username, String password) {
        System.out.println("Reponse: " + performPostCall("http://localhost:3000/users"));
        try {

//            SQLConnection sqlConnection = new SQLConnection();
//            connection = sqlConnection.connect();
//            if (connection != null) {
//                String query = "select * from [user]";
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(query);
//                while (resultSet.next()) {
//                    if (resultSet.getString(2).equals(username) && resultSet.getString(3).equals(password)){
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            "aaa",
                            username);
            return new Result.Success<>(fakeUser);
//                    }
//                }
//            }
        } catch (Exception e) {
            System.out.println(e);
            return new Result.Error(new IOException("Error logging in", e));
        }

        // return new Result.Error(new IOException("Invalid username or password"));
    }

    public String performPostCall(String requestURL) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();

            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public void logout() {
        // TODO: revoke authentication
    }
}