package com.example.gminemini.assignment2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button dobButton;
    private Button submitButton;
    private EditText name;
    private EditText lastname;
    private EditText age;
    private EditText email;
    private EditText phone;
    private RadioButton fileRadioButton;
    private RadioButton prefRadioButton;
    private EditText dobEditText;

    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();
        Spinner staticSpinner = (Spinner) findViewById(R.id.list);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.namelist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        staticSpinner.setAdapter(adapter);
        setUI();
        setListener();

    }

    private void setUI() {
        dobButton = (Button) findViewById(R.id.setdpk);
        submitButton = (Button) findViewById(R.id.submit);
        dobEditText = (EditText) findViewById(R.id.edit3);
        fileRadioButton = (RadioButton) findViewById(R.id.radio_file);
        prefRadioButton = (RadioButton) findViewById(R.id.radio_pref);

        name = (EditText) findViewById(R.id.edit1);
        lastname = (EditText) findViewById(R.id.edit2);
        age = (EditText) findViewById(R.id.edit3);
        email = (EditText) findViewById(R.id.edit4);
        phone = (EditText) findViewById(R.id.edit5);
    }

    private void setListener() {
        dobButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setdpk: {
                DatePickerDialog datePickerIssue = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel(dobEditText);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerIssue.show();
                break;
            }
            case R.id.submit: {
                if (validate()) {
                    if (fileRadioButton.isChecked())
                        writeFile();

                    if (prefRadioButton.isChecked())
                        perfFile();

                    // TODO next activity
                }
            }

        }
    }

    private void updateLabel(EditText editText) {
        String myFormat = "dd-MM-YYYY";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));
    }

    private void writeFile() {

    }

    private void perfFile() {

    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validate() {

        if (name.length() == 0)
            name.setError("Please enter name");

        if (lastname.length() == 0)
            lastname.setError("Please enter lastname");

        if (age.length() == 0)
            age.setError("Please enter age");

        if (!Character.isUpperCase(name.getText().toString().charAt(0))) {
            name.setError("Please enter first character Upper Case");
        }

        if (!Character.isUpperCase(lastname.getText().toString().charAt(0))) {
            lastname.setError("Please enter first character Upper Case");
        }

        if (email.length() == 0)
            email.setError("Please enter email");
        else if (!isEmailValid(email.getText().toString()))
            email.setError("Email wrong format");

        if (phone.length() == 0)
            phone.setError("Please enter phone");
        else if (phone.length() != 10)
            phone.setError("Phone number wrong format");

        return name.length() != 0 &&
                lastname.length() != 0 &&
                age.length() != 0 &&
                email.length() != 0 &&
                (phone.length() != 0 && phone.length() == 10) &&
                isEmailValid(email.getText().toString()) &&
                Character.isUpperCase(name.getText().toString().charAt(0)) &&
                Character.isUpperCase(lastname.getText().toString().charAt(0));
    }
}
