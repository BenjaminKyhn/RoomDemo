package com.example.android.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView outputView;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        Thread thread = new Thread(){
            @Override
            public void run(){
                User u1 = new User();
                u1.firstName = "Benny";
                u1.lastName = "Jensen";

                try {
                    db.userDao().insert(u1);
                }
                catch (Exception e){
                    System.out.println("User already exists");
                }
            }
        };
        thread.start();
    }

    public void click(View view){
        outputView = findViewById(R.id.outputView);

        Thread thread2 = new Thread(){
            @Override
            public void run(){
                try {
                    User u1 = db.userDao().findByName("Benny", "Jensen");
                    outputView.setText("Hello " + u1.firstName + " " + u1.lastName);
                }
                catch (Exception ignored){
                    System.out.println("test");
                }
            }
        };
        thread2.start();
    }
}
