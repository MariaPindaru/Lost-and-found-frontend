package com.example.lostandfound.Register;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lostandfound.R;
import com.example.lostandfound.data.model.User;
import com.example.lostandfound.login.LoginRepository;
import com.example.lostandfound.login.Result;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    RegisterViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void register(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<User> result = loginRepository.register(username, password);

        if (result instanceof Result.Success) {
            User data = ((Result.Success<User>) result).getData();
            registerResult.setValue(new RegisterResult(new RegisterView(data.getUsername())));
        } else {
            registerResult.setValue(new RegisterResult(R.string.login_failed));
        }
    }

    public void registerDataChanged(String username, String password1, String password2, String firstName, String lastName,
                                    String phone, String email) {
        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null, null, null, null, null, null));
        } else if (!isStringValid(password1)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password, null, null, null, null, null));
        } else if (!isStringValid(password2)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_password, null, null, null, null));
        } else if (!password1.equals(password2)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.password_match_error, null, null, null, null));
        } else if (!isStringValid(firstName)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, R.string.required, null, null, null));
        } else if (!isStringValid(lastName)) {
            registerFormState.setValue(new RegisterFormState(null, null, null,  null,R.string.required, null, null));
        } else if (!isStringValid(phone)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, R.string.required, null, R.string.required, null));
        } else if (!isStringValid(email)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, R.string.required, null, null, R.string.required));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isStringValid(String password) {
        if(password == null || password.isEmpty())
            return false;
        return true;
    }
}
