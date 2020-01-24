package com.example.movieapp.ui.callbacks;

import com.example.movieapp.ui.model.Movies;

public interface OnGetMovieCallback {

    void onSuccess(Movies movie);

    void onError();

}
