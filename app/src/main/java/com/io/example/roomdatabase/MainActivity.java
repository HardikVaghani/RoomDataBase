package com.io.example.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText t1,t2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BgThread().start();
            }
        });
    }

    private void initialize() {
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        b1 = findViewById(R.id.b1);
    }

    class BgThread extends Thread{
        public void run(){
            super.run();
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "room_db").build();
            UserDao userDao = db.userDao();
            userDao.insertRecord(new User( t1.getText().toString(), t2.getText().toString()));

            t1.setText("");
            t2.setText("");

            Log.i("TAG", "run: Inserted Successfully");

        }
    }
}