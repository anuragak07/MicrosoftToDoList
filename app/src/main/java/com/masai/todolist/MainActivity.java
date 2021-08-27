package com.masai.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity  {
    private TextView mtvTasks;
    private TextView mLogout;
    private TextView mtvImportant;
    private TextView mtvPlanned;
    private TextView mtvMyDay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtvTasks = findViewById(R.id.tvTaskHome);
        mLogout=findViewById(R.id.Anurag);
        mtvImportant=findViewById(R.id.tvImportant);
        mtvPlanned=findViewById(R.id.tvPlanned);
        mtvMyDay=findViewById(R.id.tvMyDay);
        mtvTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Tasks.class);
                startActivity(intent);
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignOutActivity.class);
                startActivity(intent);
            }
        });
        mtvImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ImportantActivity.class);
                startActivity(intent);
            }
        });
        mtvPlanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PlannedActivity.class);
                startActivity(intent);
            }
        });

        mtvMyDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MyDay.class);
                startActivity(intent);
            }
        });



    }
}