package com.example.exoli.a4inarow.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.exoli.a4inarow.R;
import com.example.exoli.a4inarow.classes.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class GameActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        user = (User) getIntent().getSerializableExtra("R.string.user");

    }
}
