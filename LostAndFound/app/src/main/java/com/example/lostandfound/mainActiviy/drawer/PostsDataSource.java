package com.example.lostandfound.mainActiviy.drawer;

import android.os.StrictMode;

import com.example.lostandfound.data.model.User;
import com.example.lostandfound.data.model.Post;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostsDataSource {
    public ArrayList<Post> getUserPosts(User user) {
        ArrayList<Post> posts = new ArrayList<>();

        for (Post post : getPosts()) {
            if (post.getUser_id().equals(user.getUserId())) {
                posts.add(post);
            }
        }

        return posts;
    }


    public ArrayList<Post> getPosts() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        ArrayList<Post> posts = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        String url = "http://10.0.2.2:3000/posts";
        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {

                Gson gson = new Gson();
                String jsonData = response.body().string();
                Type type = new TypeToken<List<Post>>() {
                }.getType();

                posts = gson.fromJson(jsonData, type);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return posts;
    }
}
