package com.example.lostandfound.mainActiviy;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lostandfound.R;
import com.example.lostandfound.data.model.Post;
import com.example.lostandfound.databinding.FragmentAddPostBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class AddPostFragment extends Fragment {

    private static final int SELECT_PICTURE = 200;
    private FragmentAddPostBinding binding;
    private Post currentPost = null;

    private SupportMapFragment mapsFragment;
    private GoogleMap mMap;
    private SearchView searchView;
    private TextView addressTV;
    private Button upload;
    private ImageView picture;

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

        searchView = root.findViewById(R.id.idSearchView);
        addressTV = root.findViewById(R.id.address);
        mapsFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        upload = root.findViewById(R.id.upload);
        picture = root.findViewById(R.id.image);

        Picasso.get().load(this.currentPost.getPicture()).into(picture);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
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

                        /**
                         * Manipulates the map once available.
                         * This callback is triggered when the map is ready to be used.
                         * This is where we can add markers or lines, add listeners or move the camera.
                         * In this case, we just add a marker near Sydney, Australia.
                         * If Google Play services is not installed on the device, the user will be prompted to
                         * install it inside the SupportMapFragment. This method will only be triggered once the
                         * user has installed Google Play services and returned to the app.
                         */
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

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }
    //startActivityForResult(intent, 1);


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // update the preview image in the layout
                try {
                    Bitmap photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);

                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    System.out.println(selectedImageUri);
                    System.out.println(photo);
                    createDirectoryAndSaveFile(photo, "haha");
                    picture.setImageURI(selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) throws IOException {
        String file_path = "C:\\Users\\maria\\AndroidStudioProjects";
        File dir = new File(file_path);
        if(!dir.exists())
            dir.mkdirs();
//        File file = new File(dir, "aa");
        FileOutputStream fOut = new FileOutputStream(dir);

        imageToSave.compress(Bitmap.CompressFormat.PNG, 85, fOut);
        fOut.flush();
        fOut.close();
    }
}