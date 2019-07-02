package com.example.danielpalacio.rightmatch.home_swipe;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.danielpalacio.rightmatch.user_profile.ProfileSettings;
import com.example.danielpalacio.rightmatch.R;
import com.example.danielpalacio.rightmatch.login_register.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeSwipeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "LoginActivity";
    private ImageButton temp_sign_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_swipe);
        mAuth = FirebaseAuth.getInstance();
        //FireBaseMethods.setUpFireBaseAuth(HomeSwipeActivity.this,HomeSwipeActivity.class);

    }

    public void toProfileSettings(View view){
    System.out.println("Heading to Profile Settings Activity...");
    Intent intent = new Intent(HomeSwipeActivity.this,ProfileSettings.class);
    startActivity(intent);

    }
    public void logout(View view) {
        System.out.println("Signed Out!");
        mAuth.signOut();
        Intent intent = new Intent(HomeSwipeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }



}
