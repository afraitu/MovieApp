package com.example.movieapp.ui.dbmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.movieapp.ui.App;
import com.example.movieapp.ui.callbacks.CallbackResponse;
import com.example.movieapp.ui.model.Movies;

import java.util.ArrayList;

public class DBManager {

    DBHelper dbHelper;
    Context context;
    CallbackResponse callbackResponse;

    public DBManager(CallbackResponse callbackResponse){
        this.callbackResponse=callbackResponse;
        this.context= App.context;
        dbHelper = new DBHelper(this.context);

    }

    public void addMovies(Movies movie){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_MOVIE_ID, movie.getId());
        values.put(dbHelper.COLUMN_MOVIE_TYPE, movie.getType());
        values.put(dbHelper.COLUMN_MOVIE_TITLE, movie.getTitle());
        values.put(dbHelper.COLUMN_OVERVIEW, movie.getOverview());
        values.put(dbHelper.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(dbHelper.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(dbHelper.COLUMN_AVERAGE_VOTE, movie.getRating());
        values.put(dbHelper.COLUMN_BACKDROP_PATH, movie.getBackdrop());

        long id = db.insert(dbHelper.TABLE_1, null, values);
        callbackResponse.onResponse(id);
    }

    public int isEmpty(){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String count = "SELECT count(*) FROM TABLE_1";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);

        return icount;
    }

    public void addFavorite(Movies movie){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_MOVIEID, movie.getId());
        values.put(dbHelper.COLUMN_FAV_TITLE, movie.getTitle());
        values.put(dbHelper.COLUMN_FAV_USERRATING, movie.getRating());
        values.put(dbHelper.COLUMN_FAV_POSTER_PATH, movie.getPosterPath());
        values.put(dbHelper.COLUMN_FAV_PLOT_SYNOPSIS, movie.getOverview());

        db.insert(dbHelper.TABLE_2, null, values);
        db.close();
//        callbackResponse.onResponse(id);
    }

    public void deleteFavorite(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE_2, dbHelper.COLUMN_MOVIEID+ "=" + id, null);
//        callbackResponse.onResponse(tid);
    }

    public void getAllFavorite(){
        String[] columns = {
                dbHelper.COLUMN_FAV_ID,
                dbHelper.COLUMN_MOVIEID,
                dbHelper.COLUMN_FAV_TITLE,
                dbHelper.COLUMN_FAV_USERRATING,
                dbHelper.COLUMN_FAV_POSTER_PATH,
                dbHelper.COLUMN_FAV_PLOT_SYNOPSIS

        };
        String sortOrder =
                dbHelper.COLUMN_FAV_ID + " ASC";
        ArrayList<Movies> favoriteList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(dbHelper.TABLE_2,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()){
            do {
                Movies movie = new Movies();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_MOVIEID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_FAV_TITLE)));
                movie.setRating((float) Double.parseDouble(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_FAV_USERRATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_FAV_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_FAV_PLOT_SYNOPSIS)));

                favoriteList.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();
//        Log.d("db"," "+favoriteList);
        db.close();

//        callbackResponse.onResponse(favoriteList);
    }

    public Boolean exists(String searchItem) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] projection = {
                dbHelper.COLUMN_FAV_ID,
                dbHelper.COLUMN_MOVIEID,
                dbHelper.COLUMN_FAV_TITLE,
                dbHelper.COLUMN_FAV_USERRATING,
                dbHelper.COLUMN_FAV_PLOT_SYNOPSIS,
                dbHelper.COLUMN_FAV_PLOT_SYNOPSIS

        };
        String selection = dbHelper.COLUMN_FAV_TITLE + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";

        Cursor cursor = db.query(dbHelper.TABLE_2,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();

//        callbackResponse.onResponse(exists);
        return exists;
    }
}
