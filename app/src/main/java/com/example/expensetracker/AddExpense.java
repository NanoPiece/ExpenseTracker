package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddExpense extends AppCompatActivity {
    private DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        // add back button to action bar
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        final Button confirmButton = findViewById(R.id.button_confirm);
        Button cancelButton = findViewById(R.id.button_cancel);
        final EditText category = findViewById(R.id.addExpenseCategory);
        final EditText description = findViewById(R.id.addExpenseDescription);
        final EditText amount = findViewById(R.id.addExpenseAmount);
        final EditText date = findViewById(R.id.addExpenseDate);

        // Date variables
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        date.setText(day + "/" + (month + 1) + "/" + year);

        date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // date picker dialog
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddExpense.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryValue = category.getText().toString();
                String descriptionValue = description.getText().toString();
                String amountValue = amount.getText().toString();
                String dateValue = date.getText().toString();
                if (categoryValue.equals("") ||
                        descriptionValue.equals("") ||
                        amountValue.equals("") ||
                        dateValue.equals("")) {
                    Context context = getApplicationContext();
                    CharSequence text = "All fields are mandatory!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    if (categoryValue.equals("")) {
                        category.setHintTextColor(getResources().getColor(R.color.red));
                    }
                    if (descriptionValue.equals("")) {
                        description.setHintTextColor(getResources().getColor(R.color.red));
                    }
                    if (amountValue.equals("")) {
                        amount.setHintTextColor(getResources().getColor(R.color.red));
                    }
                    if (dateValue.equals("")) {
                        date.setHintTextColor(getResources().getColor(R.color.red));
                    }
                } else {
                    String result = categoryValue + "," + descriptionValue + "," + amountValue
                            + "," + dateValue;
                    Intent intent = new Intent();
                    intent.putExtra("New_Expense", result);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

    }
}