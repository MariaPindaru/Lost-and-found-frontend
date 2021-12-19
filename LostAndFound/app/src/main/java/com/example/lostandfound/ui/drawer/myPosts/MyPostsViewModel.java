package com.example.lostandfound.ui.drawer.myPosts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyPostsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyPostsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is fragment 2");
    }

    public LiveData<String> getText() {
        return mText;
    }
}