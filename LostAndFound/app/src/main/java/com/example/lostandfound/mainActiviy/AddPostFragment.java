package com.example.lostandfound.mainActiviy;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lostandfound.R;
import com.example.lostandfound.data.model.Post;
import com.example.lostandfound.databinding.FragmentAddPostBinding;
import com.example.lostandfound.mainActiviy.drawer.PostsDataSource;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;


public class AddPostFragment extends Fragment {

    private static final int SELECT_PICTURE = 200;
    private FragmentAddPostBinding binding;
    private Post currentPost = null;

    private SupportMapFragment mapsFragment;
    private GoogleMap mMap;
    private SearchView searchView;

    private Button add;

    private TextView titleTV;
    private TextView descriptionTV;
    private TextView addressTV;

    Spinner spinner;


    public AddPostFragment() {
        // Required empty public constructor
    }

    public static AddPostFragment newInstance(String param1, String param2) {
        AddPostFragment fragment = new AddPostFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        currentPost = new Post();

        spinner = (Spinner) root.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.addOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        addressTV = root.findViewById(R.id.address);
        titleTV = root.findViewById(R.id.title);
        descriptionTV = root.findViewById(R.id.description);

        searchView = root.findViewById(R.id.idSearchView);
        mapsFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        add = root.findViewById(R.id.addPostBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                currentPost.setTitle(titleTV.getText().toString());
                currentPost.setDescription(descriptionTV.getText().toString());
                currentPost.setLocation(addressTV.getText().toString());

                currentPost.setType(spinner.getSelectedItem().toString());

                if(currentPost.getType().toUpperCase(Locale.ROOT).equals("LOST"))
                    currentPost.setPicture("https://upload.wikimedia.org/wikipedia/commons/2/24/Lost_Black_Wikipedia.png");
                else
                    currentPost.setPicture("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTByL_LAWLDPPU2wgtFR7w1jgCN_bjlkhBLUPVPYDJyBQ9Mnuk72ltxE086HKdqibV8kxI&usqp=CAU");

                currentPost.setCurrentDate();

                new PostsDataSource().addPost(currentPost);
            }

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // on below line we are getting the
                // location name from search view.
                String location = searchView.getQuery().toString();

                // below line is to create a list of address
                // where we will store the list of all address.
                List<Address> addressList = null;

                // checking if the entered location is null or not.
                if (location != null || !location.equals("")) {
                    // on below line we are creating and initializing a geo coder.
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        // on below line we are getting location from the
                        // location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // on below line we are getting the location
                    // from our list a first position.

                    if (addressList.isEmpty())
                        return false;

                    Address address = addressList.get(0);

                    // on below line we are creating a variable for our location
                    // where we will add our locations latitude and longitude.
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mapsFragment.getMapAsync(new OnMapReadyCallback() {

                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                            addressTV.setText(address.getLocality() + ", " + address.getCountryName());

                            googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return root;
    }

}