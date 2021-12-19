package com.example.lostandfound.ui.drawer.allPosts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllPostsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllPostsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is fragment 1");
    }

    public LiveData<String> getText() {
        return mText;
    }
}