package com.example.socialcompass;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button submit = (Button) findViewById(R.id.submitBTN);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmitClicked(view);
            }
        });
    }


    public void onSubmitClicked(View view) {
        Intent intent = new Intent(this, CompassViewActivity.class);
        startActivity(intent);
    }
}