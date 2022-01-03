package com.example.lostandfound.mainActiviy.drawer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostandfound.R;
import com.example.lostandfound.Register.RegisterActivity;
import com.example.lostandfound.data.model.Post;
import com.example.lostandfound.login.LoginActivity;
import com.example.lostandfound.mainActiviy.ViewPostFragment;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Viewholder> {

    private Context context;
    private ArrayList<Post> postArrayList;
    private boolean deleteBtnVisible;

    // Constructor
    public PostsAdapter(Context context, ArrayList<Post> postArrayList, boolean deleteBtn) {
        this.context = context;
        this.postArrayList = postArrayList;
        this.deleteBtnVisible = deleteBtn;
    }

    public PostsAdapter(Context context, boolean deleteBtn) {
        this.context = context;
        this.postArrayList = new ArrayList<>();
        this.deleteBtnVisible = deleteBtn;
    }

    @NonNull
    @Override
    public PostsAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view;
        if (deleteBtnVisible) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_deletebtn, parent, false);

        } else {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        }
        return new Viewholder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        // to set data to textview and imageview of each card layout
        Post post = postArrayList.get(position);

        String pattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);

        holder.postTitleTV.setText(post.getTitle());
        holder.postLocationTV.setText(post.getLocation());
        holder.postDateTV.setText(df.format(post.getDate()));

        boolean isValid = URLUtil.isValidUrl(post.getPicture());
        if (isValid)
            Picasso.get().load(post.getPicture()).into(holder.postImageIV);
        else {
            //holder.postImageIV.setImageURI(Uri.parse(post.getPicture()));
            String s = post.getPicture();

            try {
                byte[] imageByte = Base64.decode(s, Base64.DEFAULT);
                Bitmap bm = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                holder.postImageIV.setImageBitmap(Bitmap.createScaledBitmap(bm, 50, 50, false));
            }
            catch (Exception e){
                System.out.println(e);
            }
//            byte[] imgbytes = Base64.decode(s, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(imgbytes, 0,
//                    imgbytes.length);
            //holder.postImageIV.setImageBitmap(bitmap);

//            byte[] bytes = post.getPicture().getBytes(StandardCharsets.UTF_8);
//            Bitmap photo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.PNG, 100, bos);
//            byte[] bArray = bos.toString();

        //    System.out.println(bytes.equals(bArray));
//            if (photo != null)
//                holder.postImageIV.setImageBitmap(Bitmap.createScaledBitmap(photo, 200, 200, false));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment_content_logged_user_main);

                Bundle bundle = new Bundle();
                bundle.putSerializable("CurrentPost", postArrayList.get(position)); // Serializable Object
                navController.navigate(R.id.nav_view_post, bundle);

                activity.getSupportActionBar().setTitle("Details");
            }
        });

        if (deleteBtnVisible)
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new PostsDataSource().deletePost(postArrayList.get(position));
                    postArrayList.remove(position);
                    updatePostList(postArrayList);
                }
            });

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
    public static class Viewholder extends RecyclerView.ViewHolder {
        private ImageView postImageIV;
        private TextView postTitleTV;
        private TextView postLocationTV;
        private TextView postDateTV;

        private Button deleteBtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            postImageIV = itemView.findViewById(R.id.idPostImage);
            postTitleTV = itemView.findViewById(R.id.idPostTitle);
            postLocationTV = itemView.findViewById(R.id.idPostLocation);
            postDateTV = itemView.findViewById(R.id.idPostDate);

            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}