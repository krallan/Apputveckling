package com.example.krallan.assignment2;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;


public class JoinGroup extends DialogFragment implements View.OnClickListener{
    private Spinner spinner;
    private Button buttonJoin;
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
        View rootView = inflater.inflate(R.layout.joingroup_fragment, container, false);
        spinner = rootView.findViewById(R.id.spinnerGroups);
        buttonJoin = rootView.findViewById(R.id.joinGroupButton);
        buttonJoin.setOnClickListener(this);
        mainActivity.getConnection().getGroups();
        return rootView;
    }

    @Override
    public void onClick(View view) {
        String group = spinner.getSelectedItem().toString();
        mainActivity.getConnection().joinGroup(group, name);
        this.dismiss();
    }
    public void updateGroups(final List<String> groups) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, groups);
                spinner.setAdapter(arrayAdapter);
            }
        });

    }
}
