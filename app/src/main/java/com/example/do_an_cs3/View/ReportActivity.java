package com.example.do_an_cs3.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.do_an_cs3.R;

public class ReportActivity extends AppCompatActivity {
    private Button buttoncomback;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        buttoncomback=findViewById(R.id.buttonComback);
        buttoncomback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
