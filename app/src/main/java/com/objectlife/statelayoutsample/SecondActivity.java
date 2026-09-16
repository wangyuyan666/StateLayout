package com.objectlife.statelayoutsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.objectlife.statelayout.StateLayout;

public class SecondActivity extends Activity implements View.OnClickListener {

    private StateLayout stateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        stateLayout = findViewById(R.id.sl_layout_state);
        findViewById(R.id.btn_content).setOnClickListener(this);
        findViewById(R.id.btn_empty).setOnClickListener(this);
        findViewById(R.id.btn_error).setOnClickListener(this);
        findViewById(R.id.btn_loading).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_content) {
            stateLayout.setState(StateLayout.VIEW_CONTENT);
        } else if (id == R.id.btn_empty) {
            stateLayout.setState(StateLayout.VIEW_EMPTY);
        } else if (id == R.id.btn_error) {
            stateLayout.setState(StateLayout.VIEW_ERROR);
        } else if (id == R.id.btn_loading) {
            stateLayout.setState(StateLayout.VIEW_LOADING);
        }
    }
}
