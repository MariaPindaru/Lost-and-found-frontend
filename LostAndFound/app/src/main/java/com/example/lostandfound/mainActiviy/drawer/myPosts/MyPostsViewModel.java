package com.example.lostandfound.mainActiviy.drawer.myPosts;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lostandfound.R;
import com.example.lostandfound.data.model.LoggedInUser;
import com.example.lostandfound.data.model.Post;
import com.example.lostandfound.login.LoginRepository;
import com.example.lostandfound.mainActiviy.drawer.PostsDataSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyPostsViewModel extends ViewModel {

    private ArrayList<Post> postArrayList;
    private MutableLiveData<ArrayList<Post>> postArrayLiveData;

    PostsDataSource postsDataSource;

    public MyPostsViewModel() {

        postArrayLiveData = new MutableLiveData<>();

        postArrayList = new ArrayList<>();

        Date today = Calendar.getInstance().getTime();

        postsDataSource = new PostsDataSource();
        LoggedInUser user = LoginRepository.getInstance(null).getUser();
        postArrayList = postsDataSource.getUserPosts(user);

        postArrayLiveData.setValue(postArrayList);
    }

    public MutableLiveData<ArrayList<Post>> getPosts(){
        return postArrayLiveData;
    }
}