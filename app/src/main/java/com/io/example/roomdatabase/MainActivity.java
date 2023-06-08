package com.io.example.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    EditText text;
    TextView lab, textView1;
    Button b1;
    String query;
    boolean isClicked = true;
    private Spinner chooseQuerySpinner; //Spinners
    private ArrayAdapter<CharSequence> chooseQueryAdapter; //declare adapters for the spinners
    private String chooseQuery; //vars to hold the values of selected State and District

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();


        //Populate ArrayAdapter using string array and a spinner layout that we will define
        chooseQueryAdapter = ArrayAdapter.createFromResource(this, R.array.array_choose_query, R.layout.spinner_layout);
        // Specify the layout to use when the list of choices appear
//        chooseQueryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseQueryAdapter.setDropDownViewResource(R.layout.spinner_layout);
        //Populate the list of Districts in respect of the State selected
        chooseQuerySpinner.setAdapter(chooseQueryAdapter);
        //When any item of the stateSpinner uis selected
        chooseQuerySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Define City Spinner but we will populate the options through the selected state
                chooseQuery = chooseQuerySpinner.getSelectedItem().toString();
                int parentID = parent.getId();
                if (parentID == R.id.spinner_format) {
                    switch (chooseQuery) {

                        case "Get Single Todo":
                            text.setVisibility(View.VISIBLE);
                            textView1.setVisibility(View.VISIBLE);
                            text.setHint("Enter uid in number");
                            text.setInputType(InputType.TYPE_CLASS_NUMBER);
                            query = "Get Single Todo";

                            break;

                        case "Get All Todos":
                            text.setVisibility(View.GONE);
                            textView1.setVisibility(View.GONE);
                            text.setInputType(InputType.TYPE_CLASS_TEXT);
                            query = "Get All Todos";

                            break;
                        case "Delete A Todo":
                            text.setVisibility(View.VISIBLE);
                            textView1.setVisibility(View.VISIBLE);
                            text.setHint("Enter here uid for delete!");
                            text.setInputType(InputType.TYPE_CLASS_NUMBER);
                            query = "Delete A Todo";

                            break;
                        case "Update A Todo":
                            text.setVisibility(View.VISIBLE);
                            textView1.setVisibility(View.VISIBLE);
                            text.setHint("Enter here uid for update!");
                            text.setInputType(InputType.TYPE_CLASS_NUMBER);
                            query = "Update A Todo";

                            break;

                        case "Insert Multiple Todos":
                            text.setVisibility(View.GONE);
                            textView1.setVisibility(View.GONE);
                            text.setInputType(InputType.TYPE_CLASS_TEXT);
                            query = "Insert Multiple Todos";

                            break;

                        case "Find Completed Todos":
                            text.setVisibility(View.GONE);
                            textView1.setVisibility(View.GONE);
                            text.setInputType(InputType.TYPE_CLASS_TEXT);
                            query = "Find Completed Todos";

                            break;

                        case "Get All Using LiveData Only":
                            text.setVisibility(View.GONE);
                            textView1.setVisibility(View.GONE);
                            text.setInputType(InputType.TYPE_CLASS_TEXT);
                            query = "Get All Using LiveData Only";

                            break;

                        case "Insert Single Todo":

                        default:
                            text.setVisibility(View.VISIBLE);
                            textView1.setVisibility(View.VISIBLE);
                            text.setHint("Enter here!");
                            text.setInputType(InputType.TYPE_CLASS_TEXT);
                            query = "Insert Single Todo";

                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //spinner open downside
        chooseQuerySpinner.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                chooseQuerySpinner.setDropDownVerticalOffset(
                        chooseQuerySpinner.getDropDownVerticalOffset() + chooseQuerySpinner.getHeight());
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    chooseQuerySpinner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    chooseQuerySpinner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        chooseQuerySpinner.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                chooseQuerySpinner.setDropDownVerticalOffset(
                        chooseQuerySpinner.getDropDownVerticalOffset() + chooseQuerySpinner.getHeight() / 6);
                chooseQuerySpinner.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (query) {
                    case "Insert Single Todo":
                        insertSingleTodo(v);
                        break;
                    case "Get Single Todo":
                        getSingleTodo(v);
                        break;
                    case "Get All Todos":
                        getAllTodos(v);
                        break;
                    case "Delete A Todo":
                        deleteATodo(v);
                        break;
                    case "Update A Todo":
                        updateATodo(v);
                        break;
                    case "Insert Multiple Todos":
                        insertMultipleTodos(v);
                        break;
                    case "Find Completed Todos":
                        findCompletedTodos(v);
                        break;
                    case "Get All Using LiveData Only":
                        if (isClicked) { //only one time working
                            isClicked = false;
                            getAllUsingLiveDataOnly(v);
                        }
                        break;
                }
            }
        });
    }

    public void insertSingleTodo(View view) {
        Todo todo = new Todo(text.getText().toString(), true);
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
        insertAsyncTask.execute(todo);

        Log.d(TAG, "run: " + todo.getText());
        lab.setText(todo.getText());
        text.setText("");
    }

    public void getSingleTodo(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Todo todo = TodoRoomDatabase.getInstance(getApplicationContext())
                        .todoDao()
                        .findTodoById((Integer) Integer.parseInt(text.getText().toString()) instanceof Integer ? Integer.parseInt(text.getText().toString()) : 1);


                Log.d(TAG, "run: " + todo.toString());
                lab.setText(todo.getText());
                text.setText("");
            }
        }).start();
    }

    public void getAllTodos(View view) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Todo> todoList = TodoRoomDatabase.getInstance(getApplicationContext())
                        .todoDao()
                        .getAllTodos();

                Log.d(TAG, "run: " + todoList.toString());
                lab.setText("Check in logcat!");
                text.setText("");
            }
        });
        thread.start();
    }

    public void deleteATodo(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Todo todo = TodoRoomDatabase.getInstance(getApplicationContext())
                        .todoDao()
                        .findTodoById((Integer) Integer.parseInt(text.getText().toString()) instanceof Integer ? Integer.parseInt(text.getText().toString()) : null);

                if (todo != null) {

                    Log.d(TAG, "run: " + todo.getUId());
                    Log.d(TAG, "run: " + todo.getText());
                    Log.d(TAG, "run: " + todo.isCompleted());

                    TodoRoomDatabase.getInstance(getApplicationContext())
                            .todoDao()
                            .deleteTodo(todo);
                    Log.d(TAG, "run: todo has been deleted...");
                    lab.setText("Todo has been deleted...");
                    text.setText("");

                } else {
                    Log.d(TAG, "run: todo has not exists...");
                    lab.setText("Todo has not exists...");
                    text.setText("");
                }
            }
        }).start();
    }

    public void updateATodo(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Todo todo = TodoRoomDatabase.getInstance(getApplicationContext())
                        .todoDao()
                        .findTodoById((Integer) Integer.parseInt(text.getText().toString()) instanceof Integer ? Integer.parseInt(text.getText().toString()) : null);


                if (todo != null) {

                    Log.d(TAG, "run: " + todo.getUId());


                    if (todo.isCompleted())
                        todo.setCompleted(false);
                    else
                        todo.setCompleted(true);

                    TodoRoomDatabase.getInstance(getApplicationContext())
                            .todoDao()
                            .updateTodo(todo);

                    Log.d(TAG, "run: todo has been updated...");
                    lab.setText("Todo has been updated...");
                    text.setText("");
                }
            }
        }).start();
    }

    public void insertMultipleTodos(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int rend = new Random().nextInt(50);
                boolean rendB = new Random().nextBoolean();
                List<Todo> todoList = new ArrayList<>();
                todoList.add(new Todo("Make a video on kotlin :" + (rend - 1), !rendB));
                todoList.add(new Todo("Watch black panther : " + (rend - 3), true));
                todoList.add(new Todo("Watch marvel movies : " + (rend - 7), rendB));
                todoList.add(new Todo("Make a beginner video on java : " + (rend + 3), !rendB));

                TodoRoomDatabase.getInstance(getApplicationContext())
                        .todoDao()
                        .insertMultipleTodos(todoList);

                Log.d(TAG, "run: todos has been inserted...");
                lab.setText("Todos has been inserted...");
                text.setText("");

            }
        }).start();
    }

    public void findCompletedTodos(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Todo> todoList = TodoRoomDatabase.getInstance(getApplicationContext())
                        .todoDao()
                        .getAllCompletedTodos();

                Log.i(TAG, "run: " + todoList.toString());
                lab.setText("Check in logcat!");
                text.setText("");
            }
        }).start();
    }

    public void getAllUsingLiveDataOnly(View view) {
        LiveData<List<Todo>> todoList = TodoRoomDatabase.getInstance(getApplicationContext())
                .todoDao()
                .findTodosUsingLiveDataOnly();

        todoList.observe(this, new Observer<List<Todo>>() { // attach to onCreate(); and onStart();
            @Override
            public void onChanged(List<Todo> todoList) {
                Log.i(TAG, "onChanged: " + todoList.toString());
                Log.i(TAG, "onChanged: " + todoList.size());
            }
        });

        lab.setText("Check in logcat!");
        text.setText("");
//        todoList.removeObservers(this); // in onStop(); and onDestroy();
    }


    private void initialize() {
        chooseQuerySpinner = findViewById(R.id.spinner_format);
        textView1 = findViewById(R.id.text_1);
        text = findViewById(R.id.text);
        b1 = findViewById(R.id.b1);
        lab = findViewById(R.id.lab);
    }

    class InsertAsyncTask extends AsyncTask<Todo, Void, Void> {

        @Override
        protected Void doInBackground(Todo... todos) {
            TodoRoomDatabase.getInstance(getApplicationContext())
                    .todoDao()
                    .insertTodo(todos[0]);
            return null;
        }
    }


}
//https://www.youtube.com/playlist?list=PLdHg5T0SNpN3CMNtsd5KGaiBtzhTGIwtC
