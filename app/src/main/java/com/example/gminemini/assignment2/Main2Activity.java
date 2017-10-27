package com.example.gminemini.assignment2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.gminemini.assignment2.Constant.PREFS_NAME;

public class Main2Activity extends AppCompatActivity {
    private ImageView imageView;
    private TextView name;
    private TextView lastname;
    private TextView email;
    private TextView phone;
    private TextView age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setUI();
        setData();
    }

    private void setUI() {
        imageView = (ImageView) findViewById(R.id.img);
        name = (TextView) findViewById(R.id.edit_name);
        lastname = (TextView) findViewById(R.id.edit_lastname);
        email = (TextView) findViewById(R.id.edit_email);
        phone = (TextView) findViewById(R.id.edit_Phno);
        age = (TextView) findViewById(R.id.edit_age);
    }

    private void setData() {
        if (getIntent().getBooleanExtra("isFromFile", false))
            setFormFile();
        else
            setFormSharedPreferences();
    }

    private void setFormSharedPreferences() {
        SharedPreferences shared_pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        name.setText(shared_pref.getString("name", "No Pref"));
        lastname.setText(shared_pref.getString("lastname", "No Pref"));
        age.setText(shared_pref.getInt("age", 0) + " " + getResources().getString(R.string.age));
        email.setText(shared_pref.getString("email", "No Pref"));
        phone.setText(shared_pref.getString("phone", "No Pref"));
        setImg(shared_pref.getInt("age", 0));
    }

    private void setFormFile() {

    }

    public void setImg(int age) {
        if (age >= 0 && age <= 15) {
            imageView.setImageResource(R.drawable.baby);
        } else if (age >= 16 && age <= 25) {
            imageView.setImageResource(R.drawable.pun);
        } else if (age >= 26 && age <= 60) {
            imageView.setImageResource(R.drawable.work);
        } else if (age >= 61 && age <= 150) {
            imageView.setImageResource(R.drawable.old);
        }
    }


}
