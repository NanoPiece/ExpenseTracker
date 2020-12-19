package com.example.expensetracker;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseViewAdapter extends RecyclerView.Adapter<ExpenseViewAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View expenseView = inflater.inflate(R.layout.expense_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(expenseView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Expense expense = mExpenses.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.description;
        textView.setText(expense.getDescription());
        TextView textView1 = holder.category;
        textView1.setText(expense.getCategory());
        TextView textView2 = holder.amount;
        textView2.setText("£"+expense.getAmount().toString());
    }

    @Override
    public int getItemCount() {
        return mExpenses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView icon;
        public TextView description;
        public TextView category;
        public TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.expense_icon);
            description = (TextView) itemView.findViewById(R.id.description);
            category = (TextView) itemView.findViewById(R.id.category);
            amount = (TextView) itemView.findViewById(R.id.amount);

        }
    }

    private List<Expense> mExpenses;

    public ExpenseViewAdapter(List<Expense> expenses) {
        mExpenses = expenses;
    }
}
