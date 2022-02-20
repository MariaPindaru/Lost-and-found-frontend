package com.example.lostandfound.Register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lostandfound.R;
import com.example.lostandfound.data.model.UserDetails;
import com.example.lostandfound.databinding.ActivityRegisterBinding;
import com.example.lostandfound.login.LoginRepository;
import com.example.lostandfound.mainActiviy.drawer.LoggedUserMainActivity;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Register");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText1 = binding.password1;
        final EditText passwordEditText2 = binding.password2;
        final EditText firstNameEditText = binding.firstName;
        final EditText lastNameEditText = binding.lastName;
        final EditText phoneEditText = binding.phoneNumber;
        final EditText emailEditText = binding.email;
        final Button registerButton = binding.registerBtn;

        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }

                System.out.println(registerFormState.isDataValid());
                registerButton.setEnabled(registerFormState.isDataValid());

                if (registerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getPassword1Error() != null) {
                    passwordEditText1.setError(getString(registerFormState.getPassword1Error()));
                }
                if (registerFormState.getPassword2Error() != null) {
                    passwordEditText2.setError(getString(registerFormState.getPassword2Error()));
                }
                if (registerFormState.getFirstNameError() != null) {
                    firstNameEditText.setError(getString(registerFormState.getFirstNameError()));
                }
                if (registerFormState.getLastNameError() != null) {
                    lastNameEditText.setError(getString(registerFormState.getLastNameError()));
                }
                if (registerFormState.getPhoneError() != null) {
                    phoneEditText.setError(getString(registerFormState.getPhoneError()));
                }
                if (registerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerFormState.getEmailError()));
                }
            }
        });

        registerViewModel.getRegisterResult().observe(this, new Observer<RegisterResult>() {
            @Override
            public void onChanged(@Nullable RegisterResult registerResult) {
                if (registerResult == null) {
                    return;
                }
                if (registerResult.getError() != null) {
                    //showLoginFailed(registerResult.getError());
                    return;
                }
                if (registerResult.getSuccess() != null) {
                    LoginRepository.getInstance(null).getDataSource().addUserDetails(new UserDetails(
                            LoginRepository.getInstance(null).getUser().getUserId(),
                            firstNameEditText.getText().toString(), lastNameEditText.getText().toString(),
                            phoneEditText.getText().toString(), emailEditText.getText().toString()));

                    updateUiWithUser(registerResult.getSuccess());
                    Intent myIntent = new Intent(RegisterActivity.this, LoggedUserMainActivity.class);
                    RegisterActivity.this.startActivity(myIntent);
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerDataChanged(usernameEditText.getText().toString(),
                        passwordEditText1.getText().toString(), passwordEditText2.getText().toString(),
                        firstNameEditText.getText().toString(), lastNameEditText.getText().toString(),
                        phoneEditText.getText().toString(), emailEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText1.addTextChangedListener(afterTextChangedListener);
        passwordEditText2.addTextChangedListener(afterTextChangedListener);
        firstNameEditText.addTextChangedListener(afterTextChangedListener);
        lastNameEditText.addTextChangedListener(afterTextChangedListener);
        phoneEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);

        emailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerViewModel.register(usernameEditText.getText().toString(),
                            passwordEditText1.getText().toString());
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerViewModel.register(usernameEditText.getText().toString(),
                        passwordEditText1.getText().toString());
            }

        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUiWithUser(RegisterView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }
}