package com.example.okhttp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.okhttp.R;
import com.example.okhttp.adapter.SongAdapter;
import com.example.okhttp.data.response.Movie;
import com.example.okhttp.data.response.MovieResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.okhttp.utils.Config.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private SongAdapter adapter;
    public SongAdapter.RecyclerViewListen listen;
    List<Movie> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcv = findViewById(R.id.rcv);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(linearLayoutManager);

        setOnClickListen();
        update();
    }

    private void setOnClickListen() {
        listen = new SongAdapter.RecyclerViewListen() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), Showdetails.class);
                startActivity(intent);
                Bundle bundle = new Bundle();
                bundle.putParcelable("movie", movieList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
    }


    public void update() {
        new OKHTTPReqTask().execute();
    }

    private class OKHTTPReqTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            try {
                Request.Builder builder = new Request.Builder();
                builder.url(BASE_URL_API);
                Request request = builder.build();
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                Log.e("App error", e.toString());
            }

            return "[]";
        }

        @Override
        protected void onPostExecute(String data) {
            Log.e("finish", "good");
            Gson gson = new Gson();
            Type type = new TypeToken<MovieResponse>(){}.getType();
            MovieResponse movieResponse = gson.fromJson(data, type);
            movieList = movieResponse.movies;
            adapter = new SongAdapter(getApplicationContext(), movieResponse.movies, MainActivity.this::update,listen);
            rcv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}