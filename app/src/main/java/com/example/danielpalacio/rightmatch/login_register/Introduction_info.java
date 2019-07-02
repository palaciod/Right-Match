package com.example.danielpalacio.rightmatch.login_register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danielpalacio.rightmatch.R;
import com.example.danielpalacio.rightmatch.firebase_methods.FireBaseMethods;
import com.example.danielpalacio.rightmatch.user_profile.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class Introduction_info extends AppCompatActivity {
    private static final String TAG = "Introduction_info";
    private TextView birthday_picker;
    private EditText first_name;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Button gender_is_woman, gender_is_man,searching_women,searching_men,searching_everyone;
    private String bday;
    private int age;
    private User user_info;

    /*
    --- The private ints below will keep track of the button gender selection ----
     */
    private int myGenderIsMale;
    private int myGenderIsFemale;
    private int theirGenderIsMale;
    private int theirGenderIsFemale;
    private int theirGenderIsAny;
    /*
    ----- Continue Button -----
     */
    private Button _continue;

    /*
    ---- FireBaseMethods Database Private Variables ----
     */
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction_info);
        //mAuth = FirebaseAuth.getInstance(); <------ Used for print_user_information() method to test the contents being pushed to firebase
        age = 0;
        myGenderIsMale = 0;
        myGenderIsFemale = 0;
        theirGenderIsMale = 0;
        theirGenderIsFemale = 0;
        theirGenderIsAny = 0;
        datePicker();
        //fbMethods = new FireBaseMethods(current,Indroduction);

    }


    /*
    --------The datePicker method will handle the birthday date setter--------------
     */
    public void datePicker(){
        birthday_picker = (TextView) findViewById(R.id.bday_intro);
        birthday_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                System.out.println("This is the current month: " +month);

                /*
                -------- The holo light below might change to different theme and the transparency once clicked might change--------------
                 */

                DatePickerDialog dateDialog = new DatePickerDialog(Introduction_info.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dateSetListener,year,month,day);
                dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateDialog.show();

            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                System.out.println("onDateSet: mm/dd/yyy: " + month + "/" + dayOfMonth + "/" + year);

                String date = month + "/" + dayOfMonth + "/" + year;
                bday = date;
                age = get_age(year,month,dayOfMonth);
                System.out.println("My age is: "+age);
                birthday_picker.setText(date);
            }
        };
    }

    public int get_age(int birth_year,int birth_month,int birth_day){
        Calendar cal = Calendar.getInstance();
        int current_year = cal.get(Calendar.YEAR);
        int current_month = cal.get(Calendar.MONTH) + 1;
        int current_day = cal.get(Calendar.DAY_OF_MONTH);
        System.out.println("This is my year: "+current_year+" This is my month: "+current_month+" This is my day: "+current_day);
        System.out.println("This is my year: "+birth_year+" This is my month: "+birth_month+" This is my day: "+birth_day);
        int age = current_year - birth_year;
        int month_value = current_month - birth_month;
        if(month_value<0){
            age--;
        }else if(month_value==0){
            int day_value = current_day - birth_day;
            if(day_value>0){
                age--;
            }
        }

        return age;
    }




    /*
    ----------------- Gender Button Visual Press Effects ------------------
     */

    /*
    -------- The my gender methods is selecting what gender the user is identifying with --------------
     */

    public void my_gender_is_male(View view){
        gender_is_man = (Button) findViewById(R.id.intro_layout_man_gender);
        gender_is_woman = (Button) findViewById(R.id.intro_layout_woman_gender);
        int alpha_female = gender_is_woman.getBackground().getAlpha();
        if(alpha_female<255){
            gender_is_woman.getBackground().setAlpha(255);
            myGenderIsFemale = 0;
        }
        gender_is_man.getBackground().setAlpha(200);
        myGenderIsMale = 1;
        System.out.println("Integer value for male is: "+myGenderIsMale+". Female integer value is: "+ myGenderIsFemale);
    }

    public void my_gender_is_female(View view){
        gender_is_woman = (Button) findViewById(R.id.intro_layout_woman_gender);
        gender_is_man = (Button) findViewById(R.id.intro_layout_man_gender);
        int alpha_male = gender_is_man.getBackground().getAlpha();
        if(alpha_male<255){
            gender_is_man.getBackground().setAlpha(255);
            myGenderIsMale = 0;
        }
        gender_is_woman.getBackground().setAlpha(200);
        myGenderIsFemale = 1;
        System.out.println("Integer value for male is: "+myGenderIsMale+". Female integer value is: "+ myGenderIsFemale);
    }

    /*
    ------------ Below are the methods for choosing what gender you're looking for within the app. -------------------
     */

    public void their_gender_male(View view){
        searching_men = (Button) findViewById(R.id.intro_layout_men_selection);
        searching_women = (Button) findViewById(R.id.intro_layout_women_selection);
        searching_everyone = (Button) findViewById(R.id.intro_layout_everyone_selection);
        int alpha_female = searching_women.getBackground().getAlpha();
        int alpha_everyone = searching_everyone.getBackground().getAlpha();
        if((alpha_female<255)||(alpha_everyone<255)){
            searching_women.getBackground().setAlpha(255);
            searching_everyone.getBackground().setAlpha(255);
            theirGenderIsFemale = 0;
            theirGenderIsAny = 0;
        }
        searching_men.getBackground().setAlpha(200);
        theirGenderIsMale = 1;
        System.out.println("Integer value for male is: "+theirGenderIsMale+". Female integer value is: "+ theirGenderIsFemale+". The everyone integer value is: "+ theirGenderIsAny);
    }

    public void their_gender_female(View view){
        searching_men = (Button) findViewById(R.id.intro_layout_men_selection);
        searching_women = (Button) findViewById(R.id.intro_layout_women_selection);
        searching_everyone = (Button) findViewById(R.id.intro_layout_everyone_selection);
        int alpha_male = searching_men.getBackground().getAlpha();
        int alpha_everyone = searching_everyone.getBackground().getAlpha();
        if((alpha_male<255)||(alpha_everyone<255)){
            searching_men.getBackground().setAlpha(255);
            searching_everyone.getBackground().setAlpha(255);
            theirGenderIsAny = 0;
            theirGenderIsMale = 0;
        }
        searching_women.getBackground().setAlpha(200);
        theirGenderIsFemale = 1;
        System.out.println("Integer value for male is: "+theirGenderIsMale+". Female integer value is: "+ theirGenderIsFemale+". The everyone integer value is: "+ theirGenderIsAny);
    }

    public void their_gender_everyone(View view){
        searching_men = (Button) findViewById(R.id.intro_layout_men_selection);
        searching_women = (Button) findViewById(R.id.intro_layout_women_selection);
        searching_everyone = (Button) findViewById(R.id.intro_layout_everyone_selection);
        int alpha_male = searching_men.getBackground().getAlpha();
        int alpha_female = searching_women.getBackground().getAlpha();
        if((alpha_male<255)||(alpha_female<255)){
            searching_men.getBackground().setAlpha(255);
            searching_women.getBackground().setAlpha(255);
            theirGenderIsMale = 0;
            theirGenderIsFemale = 0;
        }
        searching_everyone.getBackground().setAlpha(200);
        theirGenderIsAny = 1;
        System.out.println("Integer value for male is: "+theirGenderIsMale+". Female integer value is: "+ theirGenderIsFemale+". The everyone integer value is: "+ theirGenderIsAny);
    }

    public String gender_selected(){
        if(myGenderIsMale==1){
            return "male";
        }
        if(myGenderIsFemale==1){
            return "female";
        }
        return null;
    }

    public String gender_preference(){
        if(theirGenderIsMale==1){
            return "male";
        }
        if(theirGenderIsFemale==1){
            return "female";
        }
        if(theirGenderIsAny==1){
            return "any";
        }
        return null;
    }



    /*
    ------------- Storing input information to FireBaseMethods ------------------
     */

    public void continue_button(View view){
        first_name = (EditText) findViewById(R.id.intro_layout_first_name);
        String name  = first_name.getText().toString();
        int my_gender_was_selected = myGenderIsFemale+myGenderIsMale;
        int gender_preference_was_selected = theirGenderIsAny+theirGenderIsFemale+theirGenderIsMale;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    System.out.println("User is signed in and its: "+ user.getUid());
                    System.out.println("User is: "+ user.getEmail());
                }else{
                    System.out.println("Sign in failed.");
                }

            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("On Data Change: Information has been added to FireBase: "+ "---"+dataSnapshot.getValue()+"---");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed to reach data, error: "+databaseError.toException());
            }
        });

        if((!name.isEmpty())&&(!bday.isEmpty())&&(my_gender_was_selected==1)&&(gender_preference_was_selected==1)){
            user_info = new User(name,age,gender_selected(),user.getEmail(),gender_preference(),"0");
            myRef.child("users").child(userID).setValue(user_info);
            Toast.makeText(this,"Updated!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Introduction_info.this,Phone_info.class);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Failed to update. Fill out required fields.",Toast.LENGTH_LONG).show();
        }


    }
    /*
    ---------- The method below was made for testing purposes only ----------
     */
    public void print_user_information(View view){
        first_name = (EditText) findViewById(R.id.intro_layout_first_name);
        String name  = first_name.getText().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        user_info = new User(name,age,gender_selected(),user.getEmail(),gender_preference(),"");
        System.out.println("My name is: "+ user_info.getFirstName() + " My age is: "+ user_info.getAge()+" I identify as a: " + user_info.getSex()+" My email is: "+user_info.getEmail()+" I am looking for: "+user_info.getGenderPreference()+"'s");
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }




}
