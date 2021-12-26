package com.example.lostandfound.mainActiviy.drawer.myPosts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfound.R;
import com.example.lostandfound.databinding.FragmentMypostsBinding;
import com.example.lostandfound.data.model.Post;
import com.example.lostandfound.mainActiviy.drawer.PostAdapter;

import java.util.ArrayList;

public class MyPostsFragment extends Fragment {

    private MyPostsViewModel myPostsViewModel;
    private FragmentMypostsBinding binding;

    private RecyclerView postRV;
    private PostAdapter postAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myPostsViewModel =
                new ViewModelProvider(this).get(MyPostsViewModel.class);

        binding = FragmentMypostsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        postRV = root.findViewById(R.id.idRV);
        postAdapter = new PostAdapter(getContext(), true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        postRV.setLayoutManager(linearLayoutManager);

        postRV.setAdapter(postAdapter);

        Observer<ArrayList<Post>> userListUpdateObserver = new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> userArrayList) {
                postAdapter.updatePostList(userArrayList);
            }
        };

        myPostsViewModel.getPosts().observe(getViewLifecycleOwner(), userListUpdateObserver);

        Spinner spinner = (Spinner) root.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}