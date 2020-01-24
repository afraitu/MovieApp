package com.example.movieapp.ui.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_1 = "movies";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MOVIE_TYPE = "type";
    public static final String COLUMN_MOVIE_ID = "movieId";
    public static final String COLUMN_MOVIE_TITLE = "title";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_RELEASE_DATE = "release_date";
    public static final String COLUMN_POSTER_PATH = "poster_path";
    public static final String COLUMN_AVERAGE_VOTE = "vote_average";
    public static final String COLUMN_BACKDROP_PATH = "backdrop_path";



    public static final String TABLE_2 = "favorite";
    public static final String COLUMN_FAV_ID = "_id";
    public static final String COLUMN_MOVIEID = "movieid";
    public static final String COLUMN_FAV_TITLE = "title";
    public static final String COLUMN_FAV_USERRATING = "userrating";
    public static final String COLUMN_FAV_POSTER_PATH = "posterpath";
    public static final String COLUMN_FAV_PLOT_SYNOPSIS = "overview";

    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase db;

    public static final String SQL_CREATE_MOVIES_TABLE =
            "CREATE TABLE " + TABLE_1 + " (" +
                    COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_MOVIE_TYPE + " TEXT NOT NULL, " +
                    COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                    COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                    COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                    COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                    COLUMN_AVERAGE_VOTE + " REAL, " +
                    COLUMN_BACKDROP_PATH + " TEXT " +
                    " );";

    public static final String SQL_CREATE_FAVORITE_TABLE =
            "CREATE TABLE " + TABLE_2 + " (" +
            COLUMN_FAV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_MOVIEID + " INTEGER, " +
            COLUMN_FAV_TITLE + " TEXT NOT NULL, " +
            COLUMN_FAV_USERRATING + " REAL NOT NULL, " +
            COLUMN_FAV_POSTER_PATH + " TEXT NOT NULL, " +
            COLUMN_FAV_PLOT_SYNOPSIS + " TEXT NOT NULL" +
            "); ";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIES_TABLE);
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
        onCreate(db);
    }
}
