package com.example.krallan.assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextName;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName = (EditText) findViewById(R.id.editTextName);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view.getId()== R.id.registerButton){
            String name = editTextName.getText().toString();
            if(name.length()> 0){
                SharedPreferences sharedPreferences = getSharedPreferences("USER",0);
                sharedPreferences.edit().putString("USER", name).commit();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }else{

            }
        }
    }
}
