package com.example.movieapp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.repository.ApiRepository;

import java.util.ArrayList;

public class MovieDetailsViewModel extends ViewModel implements CallbackResponse {

    CallbackResponse callbackResponse;
    ApiRepository apiRepository;
    private MutableLiveData<ArrayList<Movies>> movieMutableLiveData;

    public void getMoviesFrmVM(CallbackResponse callbackResponse){
        this.callbackResponse=callbackResponse;
        apiRepository = new ApiRepository(this);
        movieMutableLiveData = new MutableLiveData<>();
    }

    public void addFavorite(Movies movies){
        apiRepository.addFavorite(movies);
    }

    public void deleteFavorite(int id){
        apiRepository.deleteFavorite(id);
    }

    public boolean checkExist(String search){
        return apiRepository.checkExist(search);
    }

    @Override
    public void onResponse(Object o) {

    }
}
