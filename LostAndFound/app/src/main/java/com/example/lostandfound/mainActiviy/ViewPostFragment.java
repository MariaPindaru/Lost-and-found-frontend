package com.example.lostandfound.mainActiviy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lostandfound.R;
import com.example.lostandfound.data.model.Post;
import com.example.lostandfound.databinding.FragmentAllBinding;
import com.example.lostandfound.databinding.FragmentViewPostBinding;
import com.example.lostandfound.login.LoginRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPostFragment extends Fragment {

    private Post currentPost;
    private FragmentViewPostBinding binding;
    private TextView title;
    private TextView description;
    private TextView address;
    private TextView phone;
    private TextView email;
    private TextView date;

    public Post getCurrentPost() {
        return currentPost;
    }

    public void setCurrentPost(Post currentPost) {

        this.currentPost = currentPost;
    }

    public ViewPostFragment() {
        // Required empty public constructor
    }

    public static ViewPostFragment newInstance(String param1, String param2) {
        ViewPostFragment fragment = new ViewPostFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewPostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        address = root.findViewById(R.id.address);
        title = root.findViewById(R.id.title);
        description = root.findViewById(R.id.description);
        phone = root.findViewById(R.id.phone);
        email = root.findViewById(R.id.email);
        date = root.findViewById(R.id.date);

        title.setText(this.currentPost.getTitle());
        description.setText(this.currentPost.getDescription());
        address.setText(this.currentPost.getLocation());
        date.setText(this.currentPost.getDate().toString());

        phone.setText(LoginRepository.getInstance(null).getDetails().getPhone_number());
        email.setText(LoginRepository.getInstance(null).getDetails().getEmail_address());

        return root;
    }
}