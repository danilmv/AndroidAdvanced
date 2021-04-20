package com.andriod.lesson2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int chosen = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_alert_dialog).setOnClickListener(this);
        findViewById(R.id.button_fragment_dialog).setOnClickListener(this);
        findViewById(R.id.button_bottom_dialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_alert_dialog:
                showAlertDialog();
                break;
            case R.id.button_fragment_dialog:
                showFragmentDialog();
                break;
            case R.id.button_bottom_dialog:
                showBottomDialog();
                break;
        }
    }

    private void showAlertDialog() {
        String[] items = getResources().getStringArray(R.array.alert_dialog_lines);

        new AlertDialog.Builder(this)
                .setTitle("Alert Dialog")
                .setIcon(R.drawable.ic_baseline_ring_volume_24)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, which) -> {
                    Toast.makeText(this, String.format("Alert Dialog: Ok, " + (chosen >= 0 ? items[chosen] : "nothing")), Toast.LENGTH_SHORT).show();
                })
                .setSingleChoiceItems(R.array.alert_dialog_lines, chosen, (dialog, which) -> chosen = which)
                .create()
                .show();
    }

    private void showFragmentDialog() {
        new MyFragmentDialog().show(getSupportFragmentManager(), "MyFragmentDialog");
    }

    private void showBottomDialog() {
        new MyBottomDialog().show(getSupportFragmentManager(), "MyBottomDialog");
    }

}