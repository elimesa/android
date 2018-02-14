package com.emejia.list_topics.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.TextView;

import com.emejia.list_topics.R;
import com.squareup.picasso.Picasso;

public class PictureDetailActivity extends AppCompatActivity {

    private String Desc;
    private String Head ;
    private String Image ;
    private String Url ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);
        showToolbar("", true);
        TextView titleImage = (TextView) findViewById(R.id.titleImage);
        TextView textContentImageDetail = (TextView) findViewById(R.id.textContentImageDetail);
        TextView userNameDetail = (TextView) findViewById(R.id.userNameDetail);
        ImageView imageHeader = (ImageView) findViewById(R.id.imageHeader);


        Bundle datos = this.getIntent().getExtras();
        Desc = datos.getString("Desc");
        Head = datos.getString("Head");
        Image = datos.getString("Image");
        Url = datos.getString("Url");
        Picasso.with(getApplication()).load(Image).into(imageHeader);


        titleImage.setText(Desc);
        textContentImageDetail.setText(Url);
        userNameDetail.setText(Head);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setEnterTransition(new Fade());
        }

    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

    }

}
