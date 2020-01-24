package com.example.movieapp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.repository.ApiRepository;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel implements CallbackResponse {

    private MutableLiveData<ArrayList<Movies>> popularMovieArrayList;
    CallbackResponse callbackResponse;
    ApiRepository apiRepository;

    public void getfromviewmodel() {
        apiRepository = new ApiRepository(this);
        popularMovieArrayList = new MutableLiveData<ArrayList<Movies>>();
    }

    public LiveData<ArrayList<Movies>> getPopular(){
        return popularMovieArrayList;
    }


    @Override
    public void onResponse(Object o) {

        if(o instanceof ArrayList){
            ArrayList<Movies> movies = (ArrayList<Movies>) o;
            popularMovieArrayList.setValue(movies);
        }
    }
}