package com.example.interfacerecipeasy.model;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.interfacerecipeasy.api.MarthaQueue;
import com.example.interfacerecipeasy.api.MarthaRequest;
import com.example.interfacerecipeasy.api.RecipeRequestListener;
import com.example.interfacerecipeasy.api.RecipesFetchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipesService {
    private static RecipesService instance;

    private RecipesService() {

    }

    public static RecipesService getInstance() {
        if (instance == null) {
            instance = new RecipesService();
        }

        return instance;
    }

    public void getAll(int userId, final RecipesFetchListener listener, Context context) {

        JSONObject fetchParams = new JSONObject();
        try {
            fetchParams.put("id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MarthaRequest fetchRequest = new MarthaRequest("select-recipes", fetchParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");
                    ArrayList<Recipe> recipes = new ArrayList<>();

                    for (int i = 0; i<data.length(); i++) {
                        recipes.add(new Recipe(data.getJSONObject(i)));
                    }
                    
                    listener.onResponse(recipes);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onResponse(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(null);
            }
        });

        MarthaQueue.getInstance(context).send(fetchRequest);
    }

    public void add(String category, String name, int hours, int minutes, String description, int userId, final RecipeRequestListener listener, Context context) {

        if (isInvalid(category, name, hours, minutes, description)){
            listener.onResponse(false);
        }else{
            JSONObject recipeParams = new JSONObject();
            try {
                buildRecipeParams(recipeParams, category, name, hours, minutes, description);
                recipeParams.put("user_id",userId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            sendRequest("insert-recipe", recipeParams, listener, context);
        }
    }

    public void edit(final int recipeId, String category, String name, int hours, int minutes, String description, RecipeRequestListener listener, Context context) {
        if (isInvalid(category, name, hours, minutes, description)){
            listener.onResponse(false);
        }else{
            JSONObject editParams = new JSONObject();
            try {
                editParams.put("id", recipeId);
                buildRecipeParams(editParams, category, name, hours, minutes, description);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            sendRequest("update-recipe", editParams, listener, context);
        }
    }

    private void buildRecipeParams(JSONObject params, String category, String name, int hours, int minutes, String description) throws JSONException {
        params.put("category",category);
        params.put("name",name);
        params.put("duration_hours",hours);
        params.put("duration_minutes",minutes);
        params.put("description",description);
    }

    private boolean isInvalid(String category, String name, int hours, int minutes, String description) {
        return category == null || name.isEmpty() || description.isEmpty() || (hours == 0 && minutes == 0);
    }

    public void delete(int recipeId, RecipeRequestListener listener, Context context) {
        JSONObject deleteParams = new JSONObject();
        try {
            deleteParams.put("id", recipeId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sendRequest("delete-recipe", deleteParams, listener, context);
    }

    private void sendRequest(String query, JSONObject params, final RecipeRequestListener listener, Context context) {
        MarthaRequest addRequest = new MarthaRequest(query, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean success = response.getBoolean("success");
                    listener.onResponse(success);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onResponse(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onResponse(false);
            }
        });
        MarthaQueue.getInstance(context).send(addRequest);
    }
}
