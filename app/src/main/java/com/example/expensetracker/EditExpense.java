package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

// Resources used to help build this:
// https://guides.codepath.com/android/Defining-The-ActionBar

public class EditExpense extends AppCompatActivity {
    MenuItem composeButton;
    String[] expense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        final Button confirmButton = findViewById(R.id.button_confirm);
        final Button cancelButton = findViewById(R.id.button_cancel);
        final EditText category = findViewById(R.id.editExpenseCategory);
        final EditText description = findViewById(R.id.editExpenseDescription);
        final EditText amount = findViewById(R.id.editExpenseAmount);
        final TextView header = findViewById(R.id.edit_expense_header);
        final TextView date = findViewById(R.id.editExpenseDate);

        final InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        Intent intent = getIntent();
        expense = intent.getStringArrayExtra("expense");

        final String originalCategory = expense[1];
        final String originalDescription = expense[2];
        final String originalAmount = expense[3];
        final String originalDate = expense[4];

        // Page configuration
        confirmButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);

        category.setText(expense[1]);
        description.setText(expense[2]);
        amount.setText(expense[3]);
        date.setText(expense[4]);
        category.setEnabled(false);
        description.setEnabled(false);
        amount.setEnabled(false);
        date.setEnabled(false);

        // Action Bar - back button configuration
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Edit expense button
        date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog picker;
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(EditExpense.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        // Button actions - confirm
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide keyboard
                // inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                //        InputMethodManager.HIDE_NOT_ALWAYS);

                category.setEnabled(false);
                description.setEnabled(false);
                amount.setEnabled(false);
                date.setEnabled(false);
                composeButton.setVisible(true);
                cancelButton.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);

                Intent resultIntent = new Intent();
                String result = expense[0] + "," +
                                category.getText().toString() + "," +
                                description.getText().toString() + "," +
                                amount.getText().toString() + "," +
                                date.getText().toString();
                resultIntent.putExtra("Updated_Expense", result);
                setResult(MainActivity.RESULT_OK, resultIntent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide keyboard
                // inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                //        InputMethodManager.HIDE_NOT_ALWAYS);

                category.setText(originalCategory);
                description.setText(originalDescription);
                amount.setText(originalAmount);
                date.setText(originalDate);
                category.setEnabled(false);
                description.setEnabled(false);
                amount.setEnabled(false);
                date.setEnabled(false);
                composeButton.setVisible(true);
                cancelButton.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_edit_expense_menu, menu);
        return true;
    }

    // Button for change from read to edit mode
    public void onComposeAction(MenuItem mi) {
        // handle click here
        composeButton = mi;
        final Button confirmButton = findViewById(R.id.button_confirm);
        Button cancelButton = findViewById(R.id.button_cancel);
        final EditText category = findViewById(R.id.editExpenseCategory);
        final EditText description = findViewById(R.id.editExpenseDescription);
        final EditText amount = findViewById(R.id.editExpenseAmount);
        final EditText date = findViewById(R.id.editExpenseDate);

        confirmButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        category.setEnabled(true);
        description.setEnabled(true);
        amount.setEnabled(true);
        date.setEnabled(true);
        mi.setVisible(false);
    }

}