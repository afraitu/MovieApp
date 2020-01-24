package com.example.movieapp.ui.repository;

import androidx.annotation.NonNull;

import com.example.movieapp.ui.api.ApiClient;
import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.callbacks.MovieApi;
import com.example.movieapp.ui.callbacks.OnGetMoviesCallback;
import com.example.movieapp.ui.dbmanager.DBManager;
import com.example.movieapp.ui.model.MovieResponse;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.utils.URLs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository implements CallbackResponse {

    CallbackResponse callbackResponse;
    DBManager dbManager;
    MovieApi movieApi;
    private ArrayList<Movies> popularList;
    Movies popular;
    private static ApiClient apiClient;

    public ApiRepository(CallbackResponse callbackResponse) {
        this.callbackResponse = callbackResponse;
        dbManager = new DBManager(this);
        apiClient = ApiClient.getInstance();
    }

    public void getPopularMovies(int page, final OnGetMoviesCallback callback) {

        movieApi.getPopularMovies(URLs.API_KEY, URLs.LANGUAGE, page)
                .enqueue(new Callback<MovieResponse>() {

                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {

                            MovieResponse moviesResponse = response.body();

                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getPage(),moviesResponse.getMovies());
                                callbackResponse.onResponse(moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }

                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        callback.onError();
                    }
                });

    }

    public void addFavorite(Movies movies){
        dbManager.addFavorite(movies);
    }

    public void deleteFavorite(int id){
        dbManager.deleteFavorite(id);
    }

    public boolean checkExist(String search){
        return dbManager.exists(search);
    }





    @Override
    public void onResponse(Object o) {

        if(o instanceof ArrayList){
            callbackResponse.onResponse(popularList);
        }
    }
}
