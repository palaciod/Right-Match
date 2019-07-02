package com.example.danielpalacio.rightmatch.login_register;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danielpalacio.rightmatch.R;
import com.example.danielpalacio.rightmatch.user_profile.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Phone_info extends AppCompatActivity {

    private EditText _phoneNumber;
    private String number;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private String test_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_info);

        arrange_phone_format();
    }

    public void arrange_phone_format(){
        // Organize the EditText _phoneNumber to resemble the hint format.
        _phoneNumber = (EditText) findViewById(R.id.phone_number_info);
        _phoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }


    public void save_phone_number(View view){
        _phoneNumber = (EditText) findViewById(R.id.phone_number_info);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        number = _phoneNumber.getText().toString();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    System.out.println("User is signed in: " + user.getUid());
                    System.out.println("User's email is: "+user.getEmail());
                }else{
                    System.out.println("Sign in failed.");
                }
            }
        };
        if(!number.isEmpty()){
            // Add phone number to the User object and update database info
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   // String number = _phoneNumber.getText().toString();
                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        System.out.println(number);
                        User _user = messageSnapshot.child(userID).getValue(User.class);
                        _user.setNumber(number);
                       // myRef.child("users").child(userID).removeValue(User.class);
                        myRef.child("users").child(userID).setValue(_user);
                        test_num = messageSnapshot.child(userID).getValue(User.class).getNumber();
                        System.out.println("Updated Phone Number!"+"Phone Number is: "+_user.getNumber());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    System.out.println("Failed to reach data, error: "+databaseError.toException());
                }
            });
            System.out.println(test_num);
            Toast.makeText(this,"Updated Phone Number!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Failed to update. Fill in required field.",Toast.LENGTH_LONG).show();
        }


    }
}
