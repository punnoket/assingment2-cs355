package com.example.gminemini.assignment2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.example.gminemini.assignment2.Constant.FILE_NAME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button dobButton;
    private Button submitButton;
    private EditText name;
    private EditText lastname;
    private EditText email;
    private EditText phone;
    private RadioButton fileRadioButton;
    private RadioButton prefRadioButton;
    private EditText dobEditText;
    private Spinner staticSpinner;
    private FileOutputStream outputStream;
    private Calendar calendar;
    private boolean isFromFile;
    private String titleName;

    private SharedPreferences shared_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();
        shared_pref = getSharedPreferences(Constant.PREFS_NAME, MODE_PRIVATE);
        isFromFile = false;
        setUI();
        setListener();

    }

    private void setUI() {
        staticSpinner = (Spinner) findViewById(R.id.list);
        dobButton = (Button) findViewById(R.id.setdpk);
        submitButton = (Button) findViewById(R.id.submit);
        dobEditText = (EditText) findViewById(R.id.edit3);
        fileRadioButton = (RadioButton) findViewById(R.id.radio_file);
        prefRadioButton = (RadioButton) findViewById(R.id.radio_pref);
        name = (EditText) findViewById(R.id.edit1);
        lastname = (EditText) findViewById(R.id.edit2);
        email = (EditText) findViewById(R.id.edit4);
        phone = (EditText) findViewById(R.id.edit5);
        setSpinner();
    }

    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.namelist, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staticSpinner.setAdapter(adapter);
        titleName = staticSpinner.getSelectedItem().toString();
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

                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    intent.putExtra("isFromFile", isFromFile);
                    startActivity(intent);
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
        try {
            String writeString = titleName + name.getText().toString() + "\n" +
                    lastname.getText().toString() + "\n" +
                    getAge() + getResources().getString(R.string.years) + "\n" +
                    email.getText().toString() + "\n" + phone.getText().toString();

            outputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            outputStream.write(writeString.getBytes());
            outputStream.close();
            isFromFile = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void perfFile() {
        SharedPreferences.Editor editor = shared_pref.edit();
        editor.putString("name", titleName + name.getText().toString());
        editor.putString("lastname", lastname.getText().toString());
        editor.putInt("age", getAge());
        editor.putString("email", email.getText().toString());
        editor.putString("phone", phone.getText().toString());
        editor.commit();

    }

    private int getAge() {
        return new Date().getYear() - calendar.getTime().getYear();
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validate() {

        if (name.length() == 0)
            name.setError("Please enter name");

        if (lastname.length() == 0)
            lastname.setError("Please enter lastname");

        if (dobButton.length() == 0)
            dobButton.setError("Please enter date of birth");

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
                dobEditText.length() != 0 &&
                email.length() != 0 &&
                (phone.length() != 0 && phone.length() == 10) &&
                isEmailValid(email.getText().toString()) &&
                Character.isUpperCase(name.getText().toString().charAt(0)) &&
                Character.isUpperCase(lastname.getText().toString().charAt(0));
    }
}
