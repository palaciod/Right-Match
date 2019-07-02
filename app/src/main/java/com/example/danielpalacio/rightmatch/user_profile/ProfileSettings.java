package com.example.danielpalacio.rightmatch.user_profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.danielpalacio.rightmatch.R;
import com.example.danielpalacio.rightmatch.home_swipe.HomeSwipeActivity;

public class ProfileSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
    }

    public void toProfileSettings(View view){
        System.out.println("Heading towards The HomeSwipe Activity...");
        Intent intent = new Intent(ProfileSettings.this,HomeSwipeActivity.class);
        startActivity(intent);
        finish();
    }
}
