package com.example.interfacerecipeasy.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interfacerecipeasy.R;
import com.example.interfacerecipeasy.databinding.RecyclerViewRecipeBinding;
import com.example.interfacerecipeasy.model.Recipe;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private final OnRecipeClickListener recipeClickListener;
    private ArrayList<Recipe> recipes;

    public void setRecipes(ArrayList<Recipe> userRecipes) {
        this.recipes = userRecipes;
    }

    public RecipesAdapter(OnRecipeClickListener recipeClickListener) {
        this.recipes = new ArrayList<>();
        this.recipeClickListener = recipeClickListener;
    }

    @NonNull
    @Override
    public RecipesAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewRecipeBinding binding = RecyclerViewRecipeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder{
        private RecyclerViewRecipeBinding binding;

        public RecipeViewHolder(@NonNull RecyclerViewRecipeBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(final Recipe recipe) {

            if(recipe.getCategory().compareTo("breakfast") == 0){
                binding.imageCategory.setImageResource(R.drawable.img_breakfast);
            }else if (recipe.getCategory().compareTo("lunch") == 0) {
                binding.imageCategory.setImageResource(R.drawable.img_lunch);
            }else if(recipe.getCategory().compareTo("dinner") == 0){
                binding.imageCategory.setImageResource(R.drawable.img_dinner);
            }

            binding.textDuration.setText(String.format("%02dh%02d", recipe.getDurationHours(), recipe.getDurationMinutes()));

            binding.textName.setText(recipe.getName());
            binding.textDescription.setText(recipe.getDescription());

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recipeClickListener.onRecipeClicked(recipe);
                }
            });
        }
    }

}
