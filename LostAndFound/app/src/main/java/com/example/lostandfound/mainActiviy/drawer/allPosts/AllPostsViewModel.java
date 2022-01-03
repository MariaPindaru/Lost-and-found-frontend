package com.example.lostandfound.mainActiviy.drawer.allPosts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lostandfound.R;
import com.example.lostandfound.data.model.Post;
import com.example.lostandfound.mainActiviy.drawer.PostsDataSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AllPostsViewModel extends ViewModel {

    private ArrayList<Post> postArrayList;
    private MutableLiveData<ArrayList<Post>> postArrayLiveData;

    private PostsDataSource postsDataSource;

    public AllPostsViewModel() {
        postArrayLiveData = new MutableLiveData<>();

        postArrayList = new ArrayList<>();

        postsDataSource = new PostsDataSource();
        postArrayList = postsDataSource.getPosts();

        postArrayLiveData.setValue(postArrayList);
    }

    public MutableLiveData<ArrayList<Post>> getPosts(){
        return postArrayLiveData;
    }

}