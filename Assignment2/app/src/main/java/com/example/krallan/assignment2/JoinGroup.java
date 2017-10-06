package com.example.krallan.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;


public class JoinGroup extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private Spinner spinner;
    private Button buttonJoin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.joingroup_fragment, container, false);
        spinner = rootView.findViewById(R.id.spinnerGroups);
        buttonJoin = rootView.findViewById(R.id.joinGroupButton);
        spinner.setOnItemSelectedListener(this);
        buttonJoin.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
