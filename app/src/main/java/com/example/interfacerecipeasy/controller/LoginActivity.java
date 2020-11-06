package com.example.interfacerecipeasy.controller;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.interfacerecipeasy.api.UserRequestListener;
import com.example.interfacerecipeasy.databinding.ActivityLoginBinding;
import com.example.interfacerecipeasy.model.UserService;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonLogin.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view){
                String username = binding.editTextUsername.getText().toString();
                String password = binding.editTextPassword.getText().toString();

                UserService.getInstance().logIn(username, password, new UserRequestListener() {
                    @Override
                    public void onResponse(boolean success) {
                        if(success){
                            loggedIn();
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, LoginActivity.this);


            }
        });

        if(UserService.getInstance().getCurrentUser() != null){
            loggedIn();
        }
        binding.buttonSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void loggedIn(){
        Intent loggedInIntent = new Intent(LoginActivity.this, RecipesActivity.class);
        startActivity(loggedInIntent);

        finishAffinity();
    }
}