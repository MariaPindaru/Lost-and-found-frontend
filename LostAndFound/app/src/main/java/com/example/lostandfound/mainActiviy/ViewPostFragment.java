package com.example.lostandfound.mainActiviy;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lostandfound.R;
import com.example.lostandfound.data.model.Post;
import com.example.lostandfound.databinding.FragmentAllBinding;
import com.example.lostandfound.databinding.FragmentViewPostBinding;
import com.example.lostandfound.login.LoginRepository;
import com.example.lostandfound.mainActiviy.drawer.PostsDataSource;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

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
    private ImageView image;


    private SupportMapFragment mapsFragment;

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
        image = root.findViewById(R.id.image);

        mapsFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        Bundle args = getArguments();
        assert args != null;
        this.currentPost = (Post) args.getSerializable("CurrentPost");

        if (this.currentPost != null) {
            title.setText(this.currentPost.getTitle());
            description.setText(this.currentPost.getDescription());
            address.setText(this.currentPost.getLocation());
            date.setText(this.currentPost.getDate().toString());

            phone.setText(LoginRepository.getInstance(null).getDetails().getPhone_number());
            email.setText(LoginRepository.getInstance(null).getDetails().getEmail_address());

            Picasso.get().load(this.currentPost.getPicture()).into(image);

            String location = this.currentPost.getLocation();

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

                if (!addressList.isEmpty()) {

                    Address address = addressList.get(0);

                    // on below line we are creating a variable for our location
                    // where we will add our locations latitude and longitude.
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mapsFragment.getMapAsync(new OnMapReadyCallback() {

                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                            googleMap.addMarker(new MarkerOptions().position(latLng).title(location));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        }
                    });

                }

            }
        }
        return root;

    }
}