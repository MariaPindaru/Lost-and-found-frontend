package com.example.lostandfound.ui.drawer.allPosts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lostandfound.R;
import com.example.lostandfound.data.model.Post;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AllPostsViewModel extends ViewModel {

    private ArrayList<Post> postArrayList;
    private MutableLiveData<ArrayList<Post>> postArrayLiveData;

    public AllPostsViewModel() {
        postArrayLiveData = new MutableLiveData<>();

        postArrayList = new ArrayList<>();

        Date today = Calendar.getInstance().getTime();

        postArrayList.add(new Post("Post 1", "blablagf", "Brasov, Brasov",
                today, R.drawable.ic_menu_posts));
        postArrayList.add(new Post("Post 1", "blablagf", "Brasov, Brasov",
                today, R.drawable.ic_menu_posts));
        postArrayList.add(new Post("Post 1", "blablagf", "Brasov, Brasov",
                today, R.drawable.ic_menu_posts));
        postArrayList.add(new Post("Post 1", "blablagf", "Brasov, Brasov",
                today, R.drawable.ic_menu_posts));

        postArrayLiveData.setValue(postArrayList);
    }

    public MutableLiveData<ArrayList<Post>> getPosts(){
        return postArrayLiveData;
    }

}