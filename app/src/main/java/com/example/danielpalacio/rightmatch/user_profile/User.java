package com.example.danielpalacio.rightmatch.user_profile;

public class User {
    private String first_name;
    private int age;
    private String sex;
    private String email;
    private String genderPreference;
    private String phone_number;

    public User(String firstName,int age_number,String gender,String _email,String _genderPreference,String _number){
        first_name = firstName;
        age = age_number;
        sex = gender;
        email = _email;
        genderPreference = _genderPreference;
        phone_number = _number;
    }

    /*
    --------- Empty constructor to initialize object without arguments in firebase. To prevent a fatal exception. ------
     */

    public User(){
        first_name = "";
        age = 0;
        sex = "";
        email = "";
        genderPreference = "";
        phone_number = "";
    }

    /*
    ---------- Below are my getters for my User Object ---------
     */
    public String getFirstName() {
        return first_name;
    }
    public int getAge(){
        return age;
    }
    public String getSex(){
        return sex;
    }
    public String getEmail(){
        return email;
    }
    public String getGenderPreference(){
        return genderPreference;
    }
    public String getNumber(){
        return phone_number;
    }

    /*
    ------------ Below are my setters for my User object ----------------
     */
    public void setEmail(String _email){
        email = _email;
    }
    public void setFirstName(String _first){
        first_name = _first;
    }
    public void setAge(int _age){
        age = _age;
    }
    public void setSex(String _sex){
        sex = _sex;
    }
    public void setGenderPreference(String sex){
        genderPreference = sex;
    }
    public void setNumber(String number){
        phone_number = number;
    }





}




