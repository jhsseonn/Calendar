package com.example.ledger2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class Calendar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ImageButton addButton=(ImageButton)findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddList.class);
                startActivity(intent);
                finish();
            }
        });

        ImageView calendar_page=(ImageView)findViewById(R.id.calendar_page);
        ImageView ledger_page=(ImageView)findViewById(R.id.ledger_page);

        calendar_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendar=new Intent(getBaseContext(), Calendar.class);
            }
        });

        ledger_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ledger=new Intent(getBaseContext(), LedgerActivity.class);
            }
        });
    }
}