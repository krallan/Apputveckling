package com.example.krallan.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class CreateGroup extends Fragment implements View.OnClickListener {
    private EditText editTextName;
    private Button buttonCreateGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.creategroup_fragment, container, false);
        editTextName = rootView.findViewById(R.id.editTextName);
        buttonCreateGroup = rootView.findViewById(R.id.createGroupButton);
        buttonCreateGroup.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {

    }
}
