package com.example.exoli.a4inarow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.exoli.a4inarow.R;
import com.example.exoli.a4inarow.classes.User;

public class GameActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        user = (User) getIntent().getSerializableExtra("R.string.user");

    }
}
