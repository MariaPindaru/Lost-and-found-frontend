package com.example.lostandfound.mainActiviy.drawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.lostandfound.R;
import com.example.lostandfound.data.model.Post;
import com.example.lostandfound.login.LoginActivity;
import com.example.lostandfound.login.LoginRepository;
import com.example.lostandfound.mainActiviy.AddPostFragment;
import com.example.lostandfound.mainActiviy.ViewPostFragment;
import com.example.lostandfound.mainActiviy.drawer.allPosts.AllPostsFragment;
import com.example.lostandfound.mainActiviy.drawer.myPosts.MyPostsFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Slide;

import com.example.lostandfound.databinding.ActivityLoggedUserMainBinding;

import java.util.ArrayList;
import java.util.List;

public class LoggedUserMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityLoggedUserMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoggedUserMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarLoggedUserMain.toolbar);
        binding.appBarLoggedUserMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(LoggedUserMainActivity.this, R.id.nav_host_fragment_content_logged_user_main);
                navController.navigate(R.id.nav_add_post);
                getSupportActionBar().setTitle("Add post");
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerView = navigationView.getHeaderView(0);
        TextView navName = (TextView) headerView.findViewById(R.id.name);
        navName.setText(LoginRepository.getInstance(null).getDetails().getFirst_name() + " " + LoginRepository.getInstance(null).getDetails().getLast_name());
        TextView navEmail = (TextView) headerView.findViewById(R.id.email);
        navEmail.setText(LoginRepository.getInstance(null).getDetails().getEmail_address());
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_all, R.id.nav_profile, R.id.nav_posts)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_logged_user_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        final Button logout = binding.button;
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
                finish();
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    public void Logout() {
        Intent myIntent = new Intent(LoggedUserMainActivity.this, LoginActivity.class);
        LoggedUserMainActivity.this.startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logged_user_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_logged_user_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
