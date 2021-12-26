package com.example.lostandfound.mainActiviy.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfound.R;
import com.example.lostandfound.data.model.Post;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.Viewholder> {

    private Context context;
    private ArrayList<Post> postArrayList;
    private boolean deleteBtnVisible;

    // Constructor
    public PostAdapter(Context context, ArrayList<Post> postArrayList, boolean deleteBtn) {
        this.context = context;
        this.postArrayList = postArrayList;
        this.deleteBtnVisible = deleteBtn;
    }

    public PostAdapter(Context context, boolean deleteBtn) {
        this.context = context;
        this.postArrayList = new ArrayList<>();
        this.deleteBtnVisible = deleteBtn;
    }

    @NonNull
    @Override
    public PostAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view;
        if(deleteBtnVisible)    {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_deletebtn, parent, false);

        }else{

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        }
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        Post model = postArrayList.get(position);

        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        holder.postTitleTV.setText(model.getTitle());
        holder.postLocationTV.setText(model.getLocation());
        holder.postDateTV.setText(df.format(model.getDate()));
        holder.postImageIV.setImageResource(model.getImage());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return postArrayList.size();
    }

    public void updatePostList(final ArrayList<Post> userArrayList) {
        this.postArrayList.clear();
        this.postArrayList = userArrayList;
        notifyDataSetChanged();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView postImageIV;
        private TextView postTitleTV;
        private TextView postLocationTV;
        private TextView postDateTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            postImageIV = itemView.findViewById(R.id.idPostImage);
            postTitleTV = itemView.findViewById(R.id.idPostTitle);
            postLocationTV = itemView.findViewById(R.id.idPostLocation);
            postDateTV = itemView.findViewById(R.id.idPostDate);
        }
    }
}