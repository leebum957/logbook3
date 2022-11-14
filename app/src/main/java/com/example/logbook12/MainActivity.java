package com.example.logbook12;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> imgList = new ArrayList<>();
    ImageView imageView;
    Button next, previous;
    private int index = 0;
    Button add_button;
    private EditText add_Url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DatabaseHelper helper = new DatabaseHelper(this);
        imageView = findViewById(R.id.imageView);
        previous = findViewById(R.id.prevBtn);
        next = findViewById(R.id.nextBtn);



//        loadImage();
        add_Url = findViewById(R.id.urlText);
        add_button = findViewById(R.id.addBtn);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add_Url.getText().toString().length() == 0) {
                    add_Url.setError("Please enter Image URL !!!");
                } else {
                    DatabaseHelper myDB = new DatabaseHelper(MainActivity.this);
                    myDB.addUrl(add_Url.getText().toString().trim());

                    Glide.with(MainActivity.this)
                            .load(imageUrlList())
                            .centerCrop()
                            .into(imageView);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                Cursor cursor = db.readAllData();
                if (index == 0) {
                    cursor.moveToLast();
                    index = cursor.getPosition();
                } else {
                    index--;
                    cursor.moveToPosition(index);
                }
                String url = cursor.getString(1);
                Glide.with(MainActivity.this)
                        .load(url)
                        .centerCrop()
                        .into(imageView);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this);
                Cursor cursor = db.readAllData();
                cursor.moveToLast();
                int last_index = cursor.getPosition();

                if (index == last_index) {
                    cursor.moveToFirst();
                    index = cursor.getPosition();
                } else {
                    index++;
                    cursor.moveToPosition(index);
                }

                String url = cursor.getString(1);
                Glide.with(MainActivity.this)
                        .load(url)
                        .centerCrop()
                        .into(imageView);
            }
        });
    }



    String imageUrlList() {
        DatabaseHelper db = new DatabaseHelper(MainActivity.this);
        Cursor cursor = db.readAllData();
        cursor.moveToLast();
        String url = cursor.getString(1);
        index = cursor.getPosition();
        return url;
    }

    private void loadImage() {
        Glide.with(MainActivity.this)

                .load(imageUrlList())
                .centerCrop()
                .into(imageView);
    }

}