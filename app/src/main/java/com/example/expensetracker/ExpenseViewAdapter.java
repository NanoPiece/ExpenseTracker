package com.example.expensetracker;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseViewAdapter extends RecyclerView.Adapter<ExpenseViewAdapter.ViewHolder> {
    private List<Expense> mExpenses;
    private OnExpenseListener mOnExpenseListener;

    public ExpenseViewAdapter (List<Expense> expenses, OnExpenseListener mOnExpenseListener) {
        this.mExpenses = expenses;
        this.mOnExpenseListener = mOnExpenseListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View expenseView = inflater.inflate(R.layout.expense_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(expenseView, mOnExpenseListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        Expense expense = mExpenses.get(position);
        if (expense.isChecked()) {
            holder.itemView.setBackgroundColor(Color.YELLOW);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }

        // Set item views based on your views and data model
        TextView textView = holder.description;
        textView.setText(expense.getDescription());
        TextView textView1 = holder.category;
        textView1.setText(expense.getCategory());
        TextView textView2 = holder.amount;
        String formattedAmount;
        if (expense.getAmount() >= 1000000) {
            Double simplifiedAmount = expense.getAmount()/1000000;
            formattedAmount = String.format("%.2f", simplifiedAmount);
            formattedAmount += "m";
        } else if (expense.getAmount() >= 1000) {
            Double simplifiedAmount = expense.getAmount()/1000;
            formattedAmount = String.format("%.2f", simplifiedAmount);
            formattedAmount += "k";
        } else {
            formattedAmount = String.format("%.2f", expense.getAmount());
        }
        textView2.setText("£"+formattedAmount);
    }

    @Override
    public int getItemCount() {
        try {
            mExpenses.size();
        } catch (Exception e) {
            return 0;
        }
        return mExpenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {
        public ImageView icon;
        public TextView description;
        public TextView category;
        public TextView amount;
        OnExpenseListener onExpenseListener;

        public ViewHolder(View itemView, OnExpenseListener onExpenseListener) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.expense_icon);
            description = (TextView) itemView.findViewById(R.id.description);
            category = (TextView) itemView.findViewById(R.id.category);
            amount = (TextView) itemView.findViewById(R.id.amount);
            this.onExpenseListener = onExpenseListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onExpenseListener.onExpenseCLick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onExpenseListener.onExpenseLongClick(getAdapterPosition());
            return true;
        }
    }

    // Detect click on individual item of recyclerview
    public interface OnExpenseListener{
        void onExpenseCLick(int position);
        void onExpenseLongClick(int position);
    }

}
