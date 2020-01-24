package com.example.movieapp.ui.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp.R;
import com.example.movieapp.ui.api.ApiClient;
import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.callbacks.OnGetMovieCallback;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.utils.URLs;
import com.example.movieapp.ui.viewmodel.MovieDetailsViewModel;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.snackbar.Snackbar;

public class MovieDetailsActivity extends AppCompatActivity implements CallbackResponse {

    private ImageView movieBackdrop;
    private TextView movieTitle;
    private TextView movieOverview;
    private TextView movieReleaseDate;
    private RatingBar movieRating;
    private MaterialFavoriteButton materialFavoriteButton;
    Movies favorite;
    MovieDetailsViewModel movieDetailsViewModel;

    private String title, details, date, poster;
    private float rating;

    private ApiClient apiClient;
    private int movieId;

    CallbackResponse callbackResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        movieId = getIntent().getIntExtra(URLs.MOVIE_ID, movieId);
        apiClient = ApiClient.getInstance();

        initUI();

//        getMovie();
    }

    private void initUI() {
        movieBackdrop = findViewById(R.id.movieDetailsBackdrop);
        movieTitle = findViewById(R.id.movieDetailsTitle);
        movieOverview = findViewById(R.id.movieDetailsOverview);
        movieReleaseDate = findViewById(R.id.movieDetailsReleaseDate);
        movieRating = findViewById(R.id.movieDetailsRating);
        materialFavoriteButton = findViewById(R.id.favorite_button);
    }

//    private void getMovie() {
//        apiClient.getMovie(movieId, new OnGetMovieCallback() {
//            @Override
//            public void onSuccess(Movies movies) {
//                poster = movies.getPosterPath();
//                title = movies.getTitle();
//                details = movies.getOverview();
//                rating = movies.getRating() / 2;
//                date = movies.getReleaseDate();
////                movieReleaseDate.setText(movies.getReleaseDate());
//                movieId = movies.getId();
//                if (!isFinishing()) {
//                    Glide.with(MovieDetailsActivity.this)
//                            .load(URLs.IMAGE_DETAILS_URL + movies.getBackdrop())
//                            .apply(RequestOptions.placeholderOf(R.drawable.load))
//                            .into(movieBackdrop);
//                }
//                movieTitle.setText(title);
//                movieOverview.setText(details);
//                movieRating.setVisibility(View.VISIBLE);
//                movieRating.setRating(rating);
//                movieReleaseDate.setText(date);
//
//                setFavorite();
//            }
//
//            @Override
//            public void onError() {
//                finish();
//            }
//        });
//    }

    public void setFavorite() {
        materialFavoriteButton.setFavorite(false);
        if (movieDetailsViewModel.checkExist(title)) {
            materialFavoriteButton.setFavorite(true);
            Log.d("exist", " " + title);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            movieDetailsViewModel.deleteFavorite(movieId);
                            Snackbar.make(buttonView, "Removed from Favorite",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    });
        } else {
            materialFavoriteButton.setFavorite(false);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            saveFavorite();
                            Snackbar.make(buttonView, "Added to Favorite",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void saveFavorite() {
//        dbManager = new DBManager(MovieActivity.this);
        favorite = new Movies();

//        float rate = favorite.getRating();


        favorite.setId(movieId);
        favorite.setTitle(title);
        favorite.setPosterPath(poster);
        favorite.setRating(rating);
        favorite.setOverview(details);

        movieDetailsViewModel.addFavorite(favorite);
        Log.d("favorite", " " + movieId);
    }

    @Override
    public void onResponse(Object o) {

    }
}
