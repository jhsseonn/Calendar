package com.example.ledger2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Calendar extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    private RecyclerView scheduleList;
    private CalRecyclerAdapter scheduleAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Schedule> arrayLists;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference_calendar;

    String cDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();

//        scheduleList = findViewById(R.id.recyclerView);
//        scheduleList.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        scheduleList.setLayoutManager(layoutManager);

        //추가 버튼
        ImageButton addButton=(ImageButton)findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {  //추가
                Intent intent = new Intent(getBaseContext(), AddList.class);
                startActivity(intent);
                finish();
            }
        });

        CalendarView calendar=(CalendarView)findViewById(R.id.calendar);
        databaseReference_calendar = database.getReference(mFirebaseUser.getUid()+"/Calendar"); // DB 테이블 연결
        databaseReference_calendar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // firebase database의 data를 받아오는 곳
                arrayLists.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 List 추출
                    Schedule list=snapshot.getValue(Schedule.class);
                    String date = list.getDate();
                    if(cDate.equals(date)){
                        arrayLists.add(list);
                    }else{
                        arrayLists.clear();
                    }
                }

                scheduleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // DB를 가져오던 중 error 발생 시
                Log.d("LedgerActivity", String.valueOf(databaseError.toException()));
            }
        });

        CalendarView myCalendar=(CalendarView)findViewById(R.id.calendar);
        myCalendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                cDate=year+"-"+(month+1)+"-"+dayOfMonth;
                Log.d("C_DATE", cDate);
            }
        });


//        scheduleAdapter = new CalRecyclerAdapter(arrayLists);
//        scheduleList.setAdapter(scheduleAdapter);


        ImageView calendar_page=(ImageView)findViewById(R.id.calendar_page);
        ImageView ledger_page=(ImageView)findViewById(R.id.ledger);
        ImageView my_page = (ImageView)findViewById(R.id.my_page);

        //탭
        calendar_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_one = new Intent(Calendar.this, Calendar.class);
                Calendar.this.startActivity(intent_one);
            }
        });

        ledger_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_two= new Intent(Calendar.this, LedgerActivity.class);
                Calendar.this.startActivity(intent_two);
            }
        });

        my_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_three = new Intent(Calendar.this, MyPage.class);
                Calendar.this.startActivity(intent_three);
            }
        });
    }
}
