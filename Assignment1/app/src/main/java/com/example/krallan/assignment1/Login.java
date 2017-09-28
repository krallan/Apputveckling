package com.example.krallan.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Fragment implements View.OnClickListener {

    private EditText firstname;
    private EditText surname;
    private Button loginButton;


    public void initComponents(View v) {
        firstname = (EditText) v.findViewById(R.id.editTextFirstname);
        surname = (EditText) v.findViewById(R.id.editTextSurname);
        loginButton = (Button) v.findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initComponents(rootView);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        String firstname = this.firstname.getText().toString();
        String surname = this.surname.getText().toString();
        MainActivity mainActivity = (MainActivity)getActivity();
        mainActivity.login(firstname, surname);
    }

}


