package com.example.okhttp.activity;

import static com.example.okhttp.utils.Config.BASE_BACKDROP_PATH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.okhttp.R;
import com.example.okhttp.data.response.Movie;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class Showdetails extends AppCompatActivity {
ImageView roundedImageView,imgQC;
TextView txtTittle, txtId,txtnd;
ImageView imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetails);

        txtTittle = findViewById(R.id.tx_namedetail);
        txtId = findViewById(R.id.tx_iddetail);
        txtnd = findViewById(R.id.tx_noidung);
        roundedImageView = findViewById(R.id.imge_details);
        imgQC = findViewById(R.id.viewpar);
        imgBack = findViewById(R.id.imgback);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Showdetails.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        Movie Mmovie =(Movie) bundle.get("movie");
        txtTittle.setText("" + Mmovie.title);
        txtId.setText("" + Mmovie.date +"");
        txtnd.setText("Nội dung: " +Mmovie.overview);


        Picasso.with(getApplicationContext()).load(BASE_BACKDROP_PATH + Mmovie.backdropPath).into(roundedImageView);
        Picasso.with(getApplicationContext()).load(BASE_BACKDROP_PATH + Mmovie.backdropPath).into(imgQC);

    }
}