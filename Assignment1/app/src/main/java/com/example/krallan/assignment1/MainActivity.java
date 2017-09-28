package com.example.krallan.assignment1;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private Summary summary;
    private Transaction Transaction;
    private Login login;
    private TabLayout tabLayout;
    private int activeTab=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = new Login();
        summary = new Summary();
        Transaction = new Transaction();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("USER", 0);
        boolean isLoggedIn = sharedPreferences.getBoolean("isloggedin", false);
        if(isLoggedIn){
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, summary).commit();
        }else {
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, login).commit();
            tabLayout.setVisibility(View.INVISIBLE);
        }
    }

    public void onTabSelected(TabLayout.Tab tab) {
        Bundle bundle = new Bundle();
        activeTab=tab.getPosition();
        switch(tab.getPosition()){
            case 0:
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, summary).commit();
                break;
            case 1:
                Transaction = new Transaction();
                bundle = new Bundle();
                bundle.putString("type", "Income");
                Transaction.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, Transaction).commit();
                break;
            case 2:
                Transaction = new Transaction();
                bundle = new Bundle();
                bundle.putString("type", "Expense");
                Transaction.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, Transaction).commit();
                break;
        }
    }
    //Adding first & surname to sharedPreferences
    public void login (String firstname, String surname){
        SharedPreferences sharedPreferences = getSharedPreferences("USER", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("firstname", firstname);
        editor.putString("surname", surname);
        editor.putBoolean("isloggedin", true);
        editor.commit();
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, summary).commit();
        tabLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("activeTab", activeTab);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        activeTab = savedInstanceState.getInt("activeTab", 0);
        tabLayout.getTabAt(activeTab).select();
    }

    public void onTabUnselected(TabLayout.Tab tab) {

    }
    
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
