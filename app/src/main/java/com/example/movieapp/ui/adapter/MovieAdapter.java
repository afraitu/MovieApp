package com.example.movieapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp.R;
import com.example.movieapp.ui.callbacks.OnMoviesClickCallback;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.utils.URLs;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MoviesViewHolder> {

    private ArrayList<Movies> movies;
    private OnMoviesClickCallback callback;

    public MovieAdapter(ArrayList<Movies> movies, OnMoviesClickCallback callback) {
        this.movies = movies;
        this.callback = callback;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_movies, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.bind1(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {

        TextView releaseDate;
        TextView title;
        TextView rating;
        TextView genres;
        ImageView poster;
        Movies movie;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            releaseDate = itemView.findViewById(R.id.tv_release_date);
            title = itemView.findViewById(R.id.tv_movie_title);
            rating = itemView.findViewById(R.id.tv_movie_genre);
            genres = itemView.findViewById(R.id.tv_movie_rating);
            poster = itemView.findViewById(R.id.iv_poster);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(movie);
                }
            });

        }

        public void bind1(Movies movie) {
            this.movie = movie;
            releaseDate.setText(movie.getReleaseDate().split("-")[0]);
            title.setText(movie.getTitle());
            rating.setText(String.valueOf(movie.getRating()));
            genres.setText("");
            Glide.with(itemView.getContext())
                    .load(URLs.IMAGE_BASE_URL + movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.drawable.load))
                    .into(poster);

        }

    }

    public void appendMovies(List<Movies> moviesToAppend){
        movies.addAll(moviesToAppend);
        notifyDataSetChanged();
    }

    public void clearMovies() {
        movies.clear();
        notifyDataSetChanged();
    }
}
