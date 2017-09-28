package com.example.krallan.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.widget.IconTextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionAdapter extends BaseAdapter {
    private Context context;
    private List<TransactionModel> data;
    private LayoutInflater inflater;

    public TransactionAdapter(Context context, List<TransactionModel> data) {
        this.context = context;
        this.data = data;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public TransactionModel getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;

        if (v == null) {
            v = inflater.inflate(R.layout.show_transactions, viewGroup, false);
        }
        Iconify.with(new FontAwesomeModule());
        TransactionModel transactionModel = getItem(i);

        Date date = new Date(transactionModel.getDate());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        ((TextView) v.findViewById(R.id.textViewDate)).setText(formatter.format(date));
        ((TextView) v.findViewById(R.id.textViewTitle)).setText(transactionModel.getTitle());
        ((TextView) v.findViewById(R.id.textViewAmount)).setText(String.valueOf(transactionModel.getAmount()) + " SEK");
        ((TextView) v.findViewById(R.id.textViewCategory)).setText(transactionModel.getCategory());


        IconTextView viewIcon = v.findViewById(R.id.viewIcon);
        if(transactionModel.getCategory().equals("Salary")) {
            viewIcon.setText("{fa-usd}");
        }else if(transactionModel.getCategory().equals("Other")){
            viewIcon.setText("{fa-folder}");
        }else if(transactionModel.getCategory().equals("Food")){
            viewIcon.setText("{fa-cutlery}");
        }else if(transactionModel.getCategory().equals("Leisure")){
            viewIcon.setText("{fa-spotify}");
        }else if(transactionModel.getCategory().equals("Travel")){
            viewIcon.setText("{fa-suitcase}");
        }else if(transactionModel.getCategory().equals("Accommodation")){
            viewIcon.setText("{fa-home}");
        }
        return v;
    }
}
