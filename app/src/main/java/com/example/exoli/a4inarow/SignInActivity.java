package com.example.exoli.a4inarow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private FirebaseAuth mAuth;
    private TextView txtGameName;
    private TextView txtUserName;
    private EditText edtEmail;
    private TextView txtPassword;
    private EditText edtUserPassword;
    private Button btnSignIn;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        txtGameName = findViewById(R.id.txt_game_name);
        txtUserName = findViewById(R.id.txt_username_signin);
        edtEmail = findViewById(R.id.edt_email);
        txtPassword = findViewById(R.id.txt_pass_signin);
        edtUserPassword = findViewById(R.id.edt_pass);
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);


        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}
