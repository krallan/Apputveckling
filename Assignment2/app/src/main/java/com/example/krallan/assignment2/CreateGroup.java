package com.example.krallan.assignment2;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class CreateGroup extends DialogFragment implements View.OnClickListener {
    private EditText editTextName;
    private Button buttonCreateGroup;
    private MainActivity mainActivity;
    private String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,R.style.AppTheme);
        mainActivity = (MainActivity)getActivity();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USER",0);
        name = sharedPreferences.getString("USER","");

    }
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
        String group = editTextName.getText().toString();
        mainActivity.getConnection().joinGroup(group, name);
        this.dismiss();
    }
}
