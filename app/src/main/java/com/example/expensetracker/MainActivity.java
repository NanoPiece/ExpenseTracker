package com.example.expensetracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.expensetracker.database.AppDatabase;
import com.example.expensetracker.database.ExpenseDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExpenseViewAdapter.OnExpenseListener {
    private List<Expense> expenses;
    RecyclerView rvExpenses;
    ExpenseViewAdapter adapter;

    // true if the user in selection mode, false otherwise
    private Boolean multiSelect = false;
    // Keeps track of all the selected expenses
    private ArrayList<Expense> selectedExpenses = new ArrayList();
    // Contextual action mode
    private ActionMode mActionMode;
    Menu context_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the recyclerview in activity layout
        rvExpenses = (RecyclerView) findViewById(R.id.rvExpenses);

        // Initialise expenses
        expenses = new ArrayList<>();

        // Adapter
        adapter = new ExpenseViewAdapter(expenses, this);
        rvExpenses.setAdapter(adapter);
        rvExpenses.setLayoutManager(new LinearLayoutManager(this));
        rvExpenses.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //Database variables
        new LoadData(adapter).execute();

        // Add test data


        // Floating action button - add new expense
        FloatingActionButton fab = findViewById(R.id.add_expense_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddExpense.class);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Result
        super.onActivityResult(requestCode, resultCode, data);

        // Result of create new expense item
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String result = data.getStringExtra("New_Expense");
                String[] resultValues = result.split(",");
                // Log.i("Testing", resultValues[2]);
                Expense expense = new Expense(resultValues[0], resultValues[1],
                        Double.parseDouble(resultValues[2]));
                expenses.add(0, expense);
                adapter.notifyItemInserted(0);
                //ensure the recyclerview stays at the top
                rvExpenses.scrollToPosition(0);
            }
        }

        // Result of update existing expense items
        if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                String result = data.getStringExtra("Updated_Expense");
                String[] resultValues = result.split(",");
                Integer position = Integer.parseInt(resultValues[0]);
                Expense item = expenses.get(position);
                item.setCategory(resultValues[1]);
                item.setDescription(resultValues[2]);
                item.setAmount(Double.parseDouble(resultValues[3]));
                adapter.notifyItemChanged(position);
                //ensure the recyclerview stays at the top
                rvExpenses.scrollToPosition(0);
            }
        }
    }

    // Action to be taken when clicking on individual expense item
    @Override
    public void onExpenseCLick(int position) {
        if (multiSelect) {
            selectExpense(position);
        } else {
            Expense expense = expenses.get(position);
            Intent intent = new Intent(this, EditExpense.class);
            String[] expenseDetail = new String[4];
            expenseDetail[0] = Integer.toString(position);
            expenseDetail[1] = expense.getCategory();
            expenseDetail[2] = expense.getDescription();
            expenseDetail[3] = Double.toString(expense.getAmount());
            intent.putExtra("expense", expenseDetail);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    public void onExpenseLongClick(int position) {
        if (mActionMode == null) {
            mActionMode = startActionMode(mActionModeCallback);
        }
        // if multiSelect is false, set it to true and select the item
        if (!multiSelect) {
            // We have started multi selection, so set the flag to true
            multiSelect = true;
            // Hide add new button
            FloatingActionButton fab = findViewById(R.id.add_expense_fab);
            fab.setVisibility(View.GONE);
            // Add it to the list containing all the selected images
            selectExpense(position);
        }
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Select Expense");
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.activity_main_multi_select, menu);
            context_menu = menu;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.miDelete:
                    for (Expense expense : selectedExpenses) {
                        expenses.remove(expense);
                    }
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Expenses deleted",
                            Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            multiSelect = false;
            selectedExpenses.clear();
            for (Expense expense : expenses) {
                expense.setChecked(false);
            }
            adapter.notifyDataSetChanged();
            FloatingActionButton fab = findViewById(R.id.add_expense_fab);
            fab.setVisibility(View.VISIBLE);
        }
    };

    // helper function that adds/removes an item to the list depending on the app's state
    private void selectExpense(int position) {
        // If the "selectedItems" list contains the item, remove it and set it's state to normal
        if (selectedExpenses.contains(expenses.get(position))) {
            selectedExpenses.remove(expenses.get(position));
            expenses.get(position).setChecked(false);
        } else {
            // Else, add it to the list and add a darker shade over the image
            selectedExpenses.add(expenses.get(position));
            expenses.get(position).setChecked(true);
        }
        adapter.notifyDataSetChanged();
    }

    class AddNewExpense extends AsyncTask<Expense, Void, List<Expense>> {
        ExpenseViewAdapter adapter;
        ArrayList<Expense> values = new ArrayList<>();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "expense-database").build();
        final ExpenseDao expenseDao = db.getExpenseDao();

        public AddNewExpense(ExpenseViewAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected List<Expense> doInBackground(Expense... params) {
            List<Expense> result = new ArrayList<>();
            // Update database
            for (Expense i : params) {
                expenseDao.insert(i);
                result.add(0,i);
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Expense> result) {
            for (Expense i : result) {
                expenses.add(0, i);
                adapter.notifyItemInserted(0);
                Log.i("result",expenses.get(0).getDescription());
            }
        }
    }

    class LoadData extends AsyncTask<Void, Void, List<Expense>> {
        ExpenseViewAdapter adapter;
        ArrayList<Expense> values = new ArrayList<>();
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "expense-database").build();
        final ExpenseDao expenseDao = db.getExpenseDao();

        public LoadData(ExpenseViewAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        protected List<Expense> doInBackground(Void... voids) {
            expenses.addAll(expenseDao.getAll());
            if (expenses.size() == 0){
                Expense test1 = new Expense("Test", "Testing", 99.99);
                Expense test2 = new Expense("Test2", "Testing2", 88.99);
                new AddNewExpense(adapter).execute(test1, test2);
            }
            return expenses;
        }

        @Override
        protected void onPostExecute(List<Expense> result) {
            adapter.notifyDataSetChanged();
        }
    }
}