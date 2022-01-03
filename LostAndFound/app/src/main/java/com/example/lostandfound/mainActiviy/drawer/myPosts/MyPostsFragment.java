package com.example.lostandfound.mainActiviy.drawer.myPosts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.lostandfound.login.LoginRepository;
import com.example.lostandfound.mainActiviy.drawer.PostsAdapter;
import com.example.lostandfound.mainActiviy.drawer.PostsDataSource;

import java.util.ArrayList;
import java.util.Locale;
import java.util.function.Predicate;

public class MyPostsFragment extends Fragment {

    private MyPostsViewModel myPostsViewModel;
    private FragmentMypostsBinding binding;

    private RecyclerView postRV;
    private PostsAdapter postAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myPostsViewModel =
                new ViewModelProvider(this).get(MyPostsViewModel.class);

        binding = FragmentMypostsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        postRV = root.findViewById(R.id.idRV);
        postAdapter = new PostsAdapter(getContext(), true);

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                ArrayList<Post> posts = new PostsDataSource().getUserPosts(LoginRepository.getInstance(null).getUser());
                Predicate<Post> byType;
                switch (position){
                    case 0:
                        myPostsViewModel.getPosts().setValue(posts);
                        break;

                    case 1:
                        ArrayList<Post> lostList = new ArrayList<Post>();
                        for(Post post : posts)
                            if(post.getType().toUpperCase(Locale.ROOT).equals("LOST")){
                                lostList.add(post);
                            }
                        myPostsViewModel.getPosts().setValue(lostList);
                        break;

                    case 2:
                        ArrayList<Post> foundList = new ArrayList<Post>();

                        for(Post post : posts)
                            if(post.getType().toUpperCase(Locale.ROOT).equals("FOUND")){
                                foundList.add(post);
                            }
                        myPostsViewModel.getPosts().setValue(foundList);
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        myPostsViewModel.getPosts().setValue(new PostsDataSource().getUserPosts(LoginRepository.getInstance(null).getUser()));
        super.onResume();
    }
}