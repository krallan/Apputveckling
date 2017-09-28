package com.example.krallan.assignment1;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


public class Summary extends Fragment {
    private TextView income;
    private TextView expense;
    private TextView welcomeMessage;
    private TextView summary;
    private float totalIncome = 0;
    private float totalExpense = 0;

    public Summary() {
    }
    public void initComponents(View v){
        income = (TextView) v.findViewById(R.id.textViewIncomeAmount);
        expense = (TextView) v.findViewById(R.id.textViewExpensesAmount);
        welcomeMessage = (TextView) v.findViewById(R.id.textViewWelcomeMessage);
        summary = (TextView) v.findViewById(R.id.textViewSummaryAmount);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER", 0);
        String firstname = sharedPreferences.getString("firstname", null);
        String surname = sharedPreferences.getString("surname", null);
        welcomeMessage.setText(getString(R.string.welcomeMessage) + " " + firstname + " " + surname);
        totalIncome = getTransactions("Income");
        totalExpense = getTransactions("Expense");
        income.setText(String.valueOf(totalIncome)+ " SEK");
        expense.setText(String.valueOf(totalExpense)+ " SEK");
        summary.setText(String.valueOf(totalIncome - totalExpense)+ " SEK");
    }
    private float getTransactions(String transactionType){
        float totalAmount = 0;
        Database db = new Database(getContext());
       List<TransactionModel> transactions = db.getAllTransactionsByType(transactionType);
        for (TransactionModel transaction : transactions) {
            totalAmount+=transaction.getAmount();
        }
        return totalAmount;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_summary, container, false);
        initComponents(rootView);
        return rootView;
    }

}
