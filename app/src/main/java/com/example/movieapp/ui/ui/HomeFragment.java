package com.example.movieapp.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.ui.adapter.MovieAdapter;
import com.example.movieapp.ui.api.ApiClient;
import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.callbacks.OnGetMoviesCallback;
import com.example.movieapp.ui.callbacks.OnMoviesClickCallback;
import com.example.movieapp.ui.model.Movies;
import com.example.movieapp.ui.repository.ApiRepository;
import com.example.movieapp.ui.utils.URLs;
import com.example.movieapp.ui.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView popularRecycler;
    MovieAdapter popularMovieAdapter;
    private ArrayList<Movies> moviesList;
    ApiRepository apiRepository;

    private boolean isFetchingMovies;
    private int currentPage = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        moviesList = new ArrayList<>();
        popularRecycler = (RecyclerView) view.findViewById(R.id.popular_recycler_view);
        popularMovieAdapter = new MovieAdapter(moviesList,callback);

        homeViewModel.getfromviewmodel();
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(this.getActivity());
        popularRecycler.setLayoutManager(layoutManager1);
//        popularRecycler.setAdapter(popularMovieAdapter);
        getPopularMovies(currentPage);
//        popularRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                int totalItemCount = layoutManager1.getItemCount();
//                int visibleItemCount = layoutManager1.getChildCount();
//                int firstVisibleItem = layoutManager1.findFirstVisibleItemPosition();
//
//                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
//                    if (!isFetchingMovies) {
//                        getPopularMovies(currentPage++);
//                    }
//                }
//            }
//        });


        homeViewModel.getPopular().observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> movies) {
                moviesList.clear();
                moviesList.addAll(movies);
                Log.d("popular"," "+movies);
                popularMovieAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void getPopularMovies(int page) {
        Log.d("popular"," "+page);
        isFetchingMovies = true;
        apiRepository.getPopularMovies(page, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, ArrayList<Movies> movies) {
                if (popularMovieAdapter == null) {
                    popularMovieAdapter = new MovieAdapter(movies,callback);
                    popularRecycler.setAdapter(popularMovieAdapter);
                } else {
                    popularMovieAdapter.appendMovies(movies);
                }
                currentPage = page;
                isFetchingMovies = false;
            }

            @Override
            public void onError() {
//                showError();
            }
        });
    }

    OnMoviesClickCallback callback = new OnMoviesClickCallback() {
        @Override
        public void onClick(Movies movie) {
            Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
            intent.putExtra(URLs.MOVIE_ID, movie.getId());
            startActivity(intent);
        }
    };
}