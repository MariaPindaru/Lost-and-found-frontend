package com.example.lostandfound.mainActiviy.drawer.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lostandfound.data.model.UserDetails;
import com.example.lostandfound.databinding.FragmentProfileBinding;
import com.example.lostandfound.login.LoginRepository;

public class ProfileFragment extends Fragment {

    private ProfileViewModel homeViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        UserDetails details = LoginRepository.getInstance(null).getDetails();

        final TextView fullName = binding.fullName;
        fullName.setText(details.getFirst_name() + " " + details.getLast_name());

        final EditText firstName = binding.firstName;
        final EditText lastName = binding.lastName;
        final EditText phoneNumber = binding.phoneNumber;
        final EditText emailAddress = binding.emailAddress;

        firstName.setText(details.getFirst_name());
        lastName.setText(details.getLast_name());
        phoneNumber.setText(details.getPhone_number());
        emailAddress.setText(details.getEmail_address());

        final Button saveButton = binding.save;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // TODO: save changes
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}