package com.example.myapplication;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextAge, editTextDateOfBirth;
    private Spinner spinnerCountry;
    private RadioGroup radioGroupGender;
    private CheckBox checkBoxAgree;
    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.xml.activity_main);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAge = findViewById(R.id.editTextAge);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        spinnerCountry = findViewById(R.id.spinnerCountry);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        checkBoxAgree = findViewById(R.id.checkBoxAgree);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set up the spinner with ArrayAdapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);

        // Set click listener for the date of birth field
        editTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        // Set click listener for the submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndSubmitForm();
            }
        });
    }

    // Display DatePickerDialog for selecting the date of birth
    public void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        String dateOfBirth = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        editTextDateOfBirth.setText(dateOfBirth);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    // Validate form inputs and submit
    private void validateAndSubmitForm() {
        // Get user input
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String dateOfBirth = editTextDateOfBirth.getText().toString().trim();
        String selectedCountry = spinnerCountry.getSelectedItem().toString();
        int genderId = radioGroupGender.getCheckedRadioButtonId();
        boolean agreeToTerms = checkBoxAgree.isChecked();

        // Validate input
        if (name.isEmpty() || email.isEmpty() || age.isEmpty() || dateOfBirth.isEmpty() ||
                genderId == -1 || selectedCountry.equals("Select your country") || !agreeToTerms) {
            Toast.makeText(this, "Please fill in all fields and agree to terms", Toast.LENGTH_SHORT).show();
        } else {
            // Display a toast with the form data
            String gender = (genderId == R.id.radioButtonMale) ? "Male" : "Female";
            String message = "Name: " + name + "\nEmail: " + email + "\nAge: " + age +
                    "\nDate of Birth: " + dateOfBirth + "\nCountry: " + selectedCountry +
                    "\nGender: " + gender + "\nAgree to terms: " + agreeToTerms;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}
