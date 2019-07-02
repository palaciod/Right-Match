package com.example.danielpalacio.rightmatch.firebase_methods;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danielpalacio.rightmatch.home_swipe.HomeSwipeActivity;
import com.example.danielpalacio.rightmatch.login_register.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FireBaseMethods extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public FireBaseMethods(){
        mAuth = FirebaseAuth.getInstance();
    }


    public boolean getUserSignInStatus(FirebaseUser user){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        if( user != null){
            return true;
        }
        return false;
    }
    public FirebaseAuth.AuthStateListener getmAuthListener(){
        return mAuthListener;
    }

    public void setUpFireBaseAuth(final Context this_activity,final Class next_activity){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (getUserSignInStatus(user)) {
                    // User is signed in
                    System.out.println("User Sign In Status: Signed in!");
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(this_activity,next_activity);
                    startActivity(intent);
                } else {
                    // User is signed out
                    System.out.println("User Sign In Status: Signed out!");
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };
    }


//    public void signIn(EditText email, EditText password, final Context loginActivity) {
//        String emailString = email.getText().toString();
//        String passwordString = password.getText().toString();
//        if(emailString.equals("")|| passwordString.equals("")){
//            System.out.println("Incorrect Input type");
//            Toast.makeText(loginActivity,"You must fill out the specified fields!",Toast.LENGTH_SHORT).show();
//        }else{
//
//            mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful()){
//                        Log.d(TAG,"Signed in with Email: Success!");
//                        Toast.makeText(loginActivity,"Sign In Successful!",Toast.LENGTH_LONG).show();
//                        System.out.println("Sign in was successful!");
//                        Intent intent = new Intent(loginActivity,HomeSwipeActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        Log.d(TAG,"Signed in with Email: Failed!");
//                        Toast.makeText(loginActivity,"Sign In Failed",Toast.LENGTH_LONG).show();
//                        System.out.println("Sign in failed!");
//
//                    }
//                }
//            });
//        }
//
//    }
}
