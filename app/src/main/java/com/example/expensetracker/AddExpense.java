package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddExpense extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        // add back button to action bar
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        final Button confirmButton = findViewById(R.id.button_confirm);
        Button cancelButton = findViewById(R.id.button_cancel);
        final EditText category = findViewById(R.id.editTextCategory);
        final EditText description = findViewById(R.id.editTextDescription);
        final EditText amount = findViewById(R.id.editTextAmount);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoryValue = category.getText().toString();
                String descriptionValue = description.getText().toString();
                String amountValue = amount.getText().toString();
                if (categoryValue.equals("") ||
                        descriptionValue.equals("") ||
                        amountValue.equals("")) {
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
                } else {
                    String result = categoryValue + "," + descriptionValue + "," +amountValue;
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