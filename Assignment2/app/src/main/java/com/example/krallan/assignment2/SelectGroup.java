package com.example.krallan.assignment2;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class SelectGroup extends Fragment implements View.OnClickListener {
    private Button buttonJoinGroup;
    private Button buttonCreateGroup;
    private Button buttonDisconnectGroup;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.selectgroup_fragment, container, false);
        initComponents(rootView);
        return rootView;
    }

    public void initComponents(View rootView){
        buttonJoinGroup = rootView.findViewById(R.id.buttonJoinGroup);
        buttonCreateGroup = rootView.findViewById(R.id.buttonCreateGroup);
       buttonDisconnectGroup = rootView.findViewById(R.id.buttonDisconnect);
        buttonJoinGroup.setOnClickListener(this);
        buttonCreateGroup.setOnClickListener(this);
        buttonDisconnectGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "sfsfsrgr", Toast.LENGTH_SHORT).show();
        if(view.getId()==R.id.buttonJoinGroup){

        }else if(view.getId()==R.id.buttonCreateGroup){

        }else if (view.getId()==R.id.buttonDisconnectGroup){
        }

    }
}
