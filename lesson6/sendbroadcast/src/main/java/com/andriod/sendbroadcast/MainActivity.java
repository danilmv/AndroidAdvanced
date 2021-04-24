package com.andriod.sendbroadcast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String ACTION = "my.testing.broadcast.message";
    private final String MSG_KEY = "MESSAGE";
    public static final int FLAG_RECEIVER_INCLUDE_BACKGROUND = 0x01000000;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        EditText editText = findViewById(R.id.editText);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(ACTION);
            intent.putExtra(MSG_KEY, editText.getText().toString());
            intent.addFlags(FLAG_RECEIVER_INCLUDE_BACKGROUND);
            sendBroadcast(intent);
            Toast.makeText(MainActivity.this, "message sent", Toast.LENGTH_SHORT).show();
        });
    }
}