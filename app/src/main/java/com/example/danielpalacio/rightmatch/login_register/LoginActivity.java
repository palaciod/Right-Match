package com.example.danielpalacio.rightmatch.login_register;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danielpalacio.rightmatch.firebase_methods.FireBaseMethods;
import com.example.danielpalacio.rightmatch.home_swipe.HomeSwipeActivity;
import com.example.danielpalacio.rightmatch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText email;
    private EditText password;
    private FireBaseMethods fireBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starting.");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
        ----------- Layout container initialization-------------
         */
        email = (EditText) findViewById(R.id.email_edit_text);
        password = (EditText) findViewById(R.id.password_edit_text);
        Button login = (Button) findViewById(R.id.login);


        /*
        ---- FireBaseMethods Methods ----
         */
        mAuth = FirebaseAuth.getInstance();
        /*
        --------- We the lines below do not work, but can be fixed to be more organized. ------------
         */
        fireBase = new FireBaseMethods();

        fireBase.setUpFireBaseAuth(LoginActivity.this,HomeSwipeActivity.class);
        initializeLoginButton();


    }

    public void toRegisterActivity(View view){
        System.out.println("Heading towards Register Activity");
        Intent intent  = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }



     /*
    ----------------------------FireBaseMethods Code-------------------------------
     */

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d(TAG,"Started LoginActivty!");
        //mAuth.addAuthStateListener(fireBase.getmAuthListener());
        if(fireBase.getUserSignInStatus(currentUser)){
            System.out.println(currentUser.getEmail());
            Intent intent = new Intent(LoginActivity.this,HomeSwipeActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"Left LoginActivty!");
    }




    /*
    To make the code below more organized I should put all the firebase methods in one class, since most of the code is repeating itself anyway(CODE SMELL!!!).
    Currently I have firebase methods in loginactivity and homeswipeactivity.
     */

    public void initializeLoginButton(){
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                if(emailString.equals("")|| passwordString.equals("")){
                    System.out.println("Incorrect Input type");
                    Toast.makeText(LoginActivity.this,"You must fill out the specified fields!",Toast.LENGTH_SHORT).show();
                }else{

                    mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG,"Signed in with Email: Success!");
                                Toast.makeText(LoginActivity.this,"Sign In Successful!",Toast.LENGTH_LONG).show();
                                System.out.println("Sign in was successful!");
                                Intent intent = new Intent(LoginActivity.this,HomeSwipeActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Log.d(TAG,"Signed in with Email: Failed!");
                                Toast.makeText(LoginActivity.this,"Sign In Failed",Toast.LENGTH_LONG).show();
                                System.out.println("Sign in failed!");

                            }
                        }
                    });
                }

            }
        });
    }
//









}
