package com.example.lostandfound.Register;

import androidx.annotation.Nullable;

public class RegisterResult {
    @Nullable
    private RegisterView success;
    @Nullable
    private Integer error;

    RegisterResult(@Nullable Integer error) {
        this.error = error;
    }

    RegisterResult(@Nullable RegisterView success) {
        this.success = success;
    }

    @Nullable
    RegisterView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
