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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextView txtUserName;
    private EditText edtUserName;
    private TextView txtEmail;
    private EditText edtEmail;
    private TextView txtPassword;
    private EditText edtPassword;
    private Button btnSubmit;
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private final static int MINIMUM_PASSWORD_LENGTH = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference(getString(R.string.users));

        bindUI();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = edtUserName.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (userName.isEmpty()) {
                    edtUserName.setError(getString(R.string.enter_username_error));
                    return;
                }

                if (email.isEmpty()) {
                    edtEmail.setError(getString(R.string.email_length_error));
                    return;
                }

                if (password.length() < MINIMUM_PASSWORD_LENGTH) {
                    edtUserName.setError(getString(R.string.pass_min_msg));
                    return;
                }

                createUser(userName, email, password);

            }
        });
    }

    private void createUser(final String userName, final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, R.string.login_signup_error + "" + task.getException(),
                                    Toast.LENGTH_LONG).show();
                        } else { // If sign in succeeds, create a new user and add it to the database.


                            Intent intent = new Intent(SignUpActivity.this, GameActivity.class);


                            String userID = db.push().getKey();
                            User theUser = new User(email, userName, userID);
                            String transMail = theUser.getEmail().replace(".", "_");
                            db.child(transMail).setValue(theUser);
                            intent.putExtra(getString(R.string.user), theUser);

                            startActivity(intent);
                            finish();

                        }
                    }
                });
    }

    private void bindUI() {
        txtUserName = (TextView)findViewById(R.id.txt_username_signup);
        edtUserName = (EditText)findViewById(R.id.edt_username_signup);
        txtEmail = (TextView)findViewById(R.id.txt_email_signup);
        edtEmail = (EditText)findViewById(R.id.edt_email_signup);
        txtPassword = (TextView)findViewById(R.id.txt_pass_signup);
        edtPassword =(EditText)findViewById(R.id.edt_pass_signup);
        btnSubmit = (Button)findViewById(R.id.btn_submit_signup);
    }
}
