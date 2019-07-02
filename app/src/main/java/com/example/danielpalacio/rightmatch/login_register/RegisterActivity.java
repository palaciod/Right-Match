package com.example.danielpalacio.rightmatch.login_register;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danielpalacio.rightmatch.R;
import com.example.danielpalacio.rightmatch.firebase_methods.FireBaseMethods;
import com.example.danielpalacio.rightmatch.home_swipe.HomeSwipeActivity;
import com.example.danielpalacio.rightmatch.user_profile.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText password;
    private EditText email;
    private Context current;
    private Button signUp;
    private EditText pass_confirm;

    /*
    --- FireBase Variables ---
     */
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        createAccount();

    }





    public void previousActivity(View view) {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }



    public void createAccount(){
        signUp = findViewById(R.id.register_continue);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = (EditText) findViewById(R.id.register_email);
                final String emailString = email.getText().toString();
                password = (EditText) findViewById(R.id.register_password);
                String passwordString = password.getText().toString();
                pass_confirm = (EditText) findViewById(R.id.password_confirmation);
                String second_password = pass_confirm.getText().toString();

                /*
                ------------------ First If statement handles the empty string exception---------------------
                 */
                if(emailString.equals("")|| passwordString.equals("")||second_password.equals("")){
                    System.out.println("Incorrect Input type");
                    Toast.makeText(RegisterActivity.this,"You must fill out all the required fields!",Toast.LENGTH_SHORT).show();
                }
                /*
                -------------------Second If statement handles if the passwords match------------------------
                 */
                if(!passwordString.equals(second_password)){
                    System.out.println("Passwords Do Not Match!");
                    Toast.makeText(RegisterActivity.this,"Passwords do not match!",Toast.LENGTH_SHORT).show();
                }

                /*
                --------------------Finally the else statement handles the create user function and starts a new intent--------------------
                 */

                else{
                    mAuth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(RegisterActivity.this,Introduction_info.class);
                                //user.getEmail(emailString);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"RegisterActivty Started!");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"Left RegisterActivty!");
    }




}
