package com.example.interfacerecipeasy.controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.interfacerecipeasy.api.UserRequestListener;
import com.example.interfacerecipeasy.databinding.ActivitySignupBinding;
import com.example.interfacerecipeasy.model.UserService;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSignUp.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view){
                String password = binding.editTextPassword.getText().toString();
                String passwordConfirmation = binding.editTextConfirmPassword.getText().toString();

                if(password.compareTo(passwordConfirmation) == 0){
                    String username = binding.editTextUsername.getText().toString();
                    UserService.getInstance().signUp(username, password, new UserRequestListener() {
                        @Override
                        public void onResponse(boolean success) {
                            if(success){
                                Intent signedUp = new Intent(SignUpActivity.this, RecipesActivity.class);
                                startActivity(signedUp);

                                finishAffinity();
                            } else{
                                Toast.makeText(SignUpActivity.this, "Invalid infos...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, SignUpActivity.this);

                }else{
                    Toast.makeText(SignUpActivity.this, "Password confirmation doesn't match!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}