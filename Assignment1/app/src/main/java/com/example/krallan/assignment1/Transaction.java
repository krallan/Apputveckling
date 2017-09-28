package com.example.krallan.assignment1;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Transaction extends Fragment implements View.OnClickListener {
    private TextView textViewStartDate;
    private TextView textViewEndDate;
    private DatePickerDialog datePickerDialog;
    private Date startDate;
    private Date endDate;
    private FloatingActionButton fab;
    private Calendar cal = Calendar.getInstance();
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private String type;
    private ListView listView;

    public Transaction() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);
        type = getArguments().getString("type");
        startDate = new Date();
        endDate = new Date();
        startDate.setTime(endDate.getTime() - 2592000000l);

        initComponents(rootView);
        registerListeners();
        listTransactions();



        return rootView;
    }

    public void initComponents(View rootView) {
        textViewStartDate = (TextView) rootView.findViewById(R.id.textViewStartDate);
        textViewStartDate.setText(formatter.format(startDate));
        textViewEndDate = (TextView) rootView.findViewById(R.id.textViewEndDate);
        textViewEndDate.setText(formatter.format(endDate));
        fab = rootView.findViewById(R.id.addTransaction);
        listView = (ListView) rootView.findViewById(R.id.listViewTransactionList);
    }
    public void listTransactions(){
        Database db = new Database(getContext());
        List<TransactionModel> transactions = db.getAllTransactionsByType(type, startDate.getTime(), endDate.getTime());
        TransactionAdapter adapter = new TransactionAdapter(getContext(), transactions);
        listView.setAdapter(adapter);
    }

    public void registerListeners() {
        textViewStartDate.setOnClickListener(this);
        textViewEndDate.setOnClickListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.textViewStartDate) {
            cal.setTime(startDate);
            datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    cal.set(i, i1, i2);
                    startDate.setTime(cal.getTimeInMillis());
                    textViewStartDate.setText(formatter.format(cal.getTime()));
                    listTransactions();
                }
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        } else if (view.getId() == R.id.textViewEndDate) {
            cal.setTime(endDate);
            datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    cal.set(i, i1, i2);
                    endDate.setTime(cal.getTimeInMillis());
                    listTransactions();
                    textViewEndDate.setText(formatter.format(cal.getTime()));
                }
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }else if(view.getId()==R.id.addTransaction){
            Intent intent = new Intent(getContext(),AddTransaction.class);
            intent.putExtra("type", type);
            getActivity().startActivity(intent);
        }

    }
}
