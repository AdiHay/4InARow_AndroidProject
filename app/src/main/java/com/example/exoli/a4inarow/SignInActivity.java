package com.example.exoli.a4inarow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private TextView txtGameName;
    private TextView txtUserName;
    private EditText edtEmail;
    private TextView txtPassword;
    private EditText edtUserPassword;
    private Button btnSignIn;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private Intent intent;
    private final static int MINIMUM_PASSWORD_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        bindUI();

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
    }

    private void bindUI() {
        txtGameName = findViewById(R.id.txt_game_name);
        txtUserName = findViewById(R.id.txt_username_signin);
        edtEmail = findViewById(R.id.edt_email);
        txtPassword = findViewById(R.id.txt_pass_signin);
        edtUserPassword = findViewById(R.id.edt_pass);
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edtEmail.getText().toString();
                final String password = edtUserPassword.getText().toString();

                findUser(email, password);
            }
        });
    }

    private void findUser(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user.
//                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < MINIMUM_PASSWORD_LENGTH) {
                                edtUserPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(SignInActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else { //If sign in succeeds, get the user from the database and put it in Intent for the next activity.

                            String transMail = email.replace(getString(R.string.dot), getString(R.string.underscore));
                            db.child(transMail).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User theUser = dataSnapshot.getValue(User.class);

                                    intent.putExtra(getString(R.string.user), theUser);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}
