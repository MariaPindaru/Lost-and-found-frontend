package com.example.lostandfound.mainActiviy;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lostandfound.R;
import com.example.lostandfound.databinding.FragmentAddPostBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class AddPostFragment extends Fragment {

    private FragmentAddPostBinding binding;

    private SupportMapFragment mapsFragment;
    private GoogleMap mMap;
    private SearchView searchView;
    private TextView addressTV;

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

        searchView = root.findViewById(R.id.idSearchView);
        addressTV = root.findViewById(R.id.address);
        mapsFragment = (SupportMapFragment) getParentFragmentManager().findFragmentById(R.id.map);

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

                    if(addressList.isEmpty())
                        return false;

                    Address address = addressList.get(0);

                    //addressTV.setText(address.getLocality());

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
                            googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        }
                    });
                    // on below line we are adding marker to that position.
                    //mapsFragment.getMap().addMarker(new MarkerOptions().position(latLng).title(location));

                    // below line is to animate camera to that position.
                    // mapsFragment.getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
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