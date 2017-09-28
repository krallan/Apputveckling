package com.example.krallan.assignment1;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class AddTransaction extends AppCompatActivity implements View.OnClickListener {

    private String type = "Income";
    private EditText addTitle;
    private Spinner addCategory;
    private EditText addAmount;
    private TextView addDate;
    private Button addTransaction;
    private Date date = new Date();
    private Calendar cal = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        type = getIntent().getExtras().getString("type");
        db = new Database(this);
        initComponents();
    }
    public void initComponents(){
        addTitle = (EditText) findViewById(R.id.editTextAddTransactionTitle);
        addCategory = (Spinner) findViewById(R.id.spinnerAddTransactionCategory);
        addAmount = (EditText) findViewById(R.id.editTextAddTransactionAmount);
        addDate = (TextView) findViewById(R.id.textViewAddTransactionDate);
        addTransaction = (Button) findViewById(R.id.buttonAddTransaction);

        addDate.setText(dateFormat.format(date));

        addDate.setOnClickListener(this);
        addTransaction.setOnClickListener(this);

        if(type.equals("Income")){
            addTransaction.setText("Add Income");
            String[] categories = getResources().getStringArray(R.array.income_array);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories);
            addCategory.setAdapter(arrayAdapter);
        }else{
            addTransaction.setText("Add Expense");
            String[] categories = getResources().getStringArray(R.array.expenses_array);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories);
            addCategory.setAdapter(arrayAdapter);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.textViewAddTransactionDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    cal.set(i, i1, i2);
                    date.setTime(cal.getTimeInMillis());
                    addDate.setText(dateFormat.format(date));
                }
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }else if(view.getId()==R.id.buttonAddTransaction){
            String title = addTitle.getText().toString();
            String category = addCategory.getSelectedItem().toString();
            float amount = Float.parseFloat(addAmount.getText().toString());
            long date = this.date.getTime();

            TransactionModel transactionModel = new TransactionModel(type, date, title, category, amount);

            if(db.createTransaction(transactionModel)){
                finish();
            }else{
                Toast.makeText(this, "Something's not quite right", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
