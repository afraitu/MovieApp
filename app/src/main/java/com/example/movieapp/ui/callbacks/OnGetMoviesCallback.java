package com.example.movieapp.ui.callbacks;

import com.example.movieapp.ui.model.Movies;

import java.util.ArrayList;

public interface OnGetMoviesCallback {

    void onSuccess(int page, ArrayList<Movies> movies);

    void onError();

}
