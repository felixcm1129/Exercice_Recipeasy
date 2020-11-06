package com.example.interfacerecipeasy.controller;

import androidx.annotation.LongDef;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.interfacerecipeasy.api.RecipeRequestListener;
import com.example.interfacerecipeasy.databinding.ActivityRecipeBinding;
import com.example.interfacerecipeasy.model.Recipe;
import com.example.interfacerecipeasy.model.RecipesService;
import com.example.interfacerecipeasy.model.UserService;

public class RecipeActivity extends AppCompatActivity {
    private ActivityRecipeBinding binding;
    private int recipeId;
    private int getUserId(){
        return UserService.getInstance().getCurrentUser().getId();
    }

    private String getCategory(){
        String category = null;
        int categoryId = binding.radioGroupCategory.getCheckedRadioButtonId();
        if(categoryId != -1){
            category = findViewById(categoryId).getTag().toString();
        }

        return category;
    }

    private String getName(){
        return binding.editTextName.getText().toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private int getDurationHours(){
        return binding.spinnerTimePicker.getHour();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private int getDurationMinutes(){
        return binding.spinnerTimePicker.getMinute();
    }

    private String getDescription(){
        return binding.multilineDescription.getText().toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.spinnerTimePicker.setIs24HourView(true);
        binding.spinnerTimePicker.setHour(0);
        binding.spinnerTimePicker.setMinute(0);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            Recipe recipe = extras.getParcelable(RecipesActivity.KEY_EDIT_RECIPE_EXTRA);

            recipeId = recipe.getId();

            ((RadioButton)binding.radioGroupCategory.findViewWithTag(recipe.getCategory())).setChecked(true);
            binding.editTextName.setText(recipe.getName());
            binding.spinnerTimePicker.setHour(recipe.getDurationHours());
            binding.spinnerTimePicker.setMinute(recipe.getDurationMinutes());
            binding.multilineDescription.setText(recipe.getDescription());

            binding.buttonSave.setOnClickListener(editClickListener);
            binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RecipesService.getInstance().delete(recipeId, recipeRequestListener, RecipeActivity.this);
                }
            });
        }else{
            binding.buttonDelete.setVisibility(View.GONE);
            binding.buttonSave.setText("Add");

            binding.buttonSave.setOnClickListener(addClickListener);
        }
    }

    private final View.OnClickListener addClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View view) {

            RecipesService.getInstance().add(getCategory(), getName(), getDurationHours(), getDurationMinutes(), getDescription(), getUserId(), recipeRequestListener, RecipeActivity.this);
        }
    };

    private View.OnClickListener editClickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View view) {

            RecipesService.getInstance().edit(recipeId, getCategory(), getName(), getDurationHours(), getDurationMinutes(), getDescription(), recipeRequestListener, RecipeActivity.this);
        }
    };

    private RecipeRequestListener recipeRequestListener = new RecipeRequestListener() {
        @Override
        public void onResponse(boolean success) {
            if(success){
                finishOk();
            }else{
                Toast.makeText(RecipeActivity.this, "Invalid infos...", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void finishOk(){
        setResult(RESULT_OK);
        finish();
    }

}