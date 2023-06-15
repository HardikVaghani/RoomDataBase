package com.io.example.roomdatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.io.example.roomdatabase.databinding.ActivityMainBinding;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    ActivityMainBinding binding;
    View root;
    UserViewModel userViewModel;
    String timestampString = null;
    Timestamp timestamp = null;
    Timestamp createdAtTimestamp = null;

    String query;
    boolean isClicked = true;
    private Spinner chooseQuerySpinner; //Spinners
    private ArrayAdapter<CharSequence> chooseQueryAdapter; //declare adapters for the spinners
    private String chooseQuery; //vars to hold the values of selected State and District


    //for insert ui
    EditText insertFirstName, insertLastName, insertDisplayName, insertUserName, insertEmail, insertPhone, insertPassword, insertImage, insertDescription;
    TextView insertDOF;
    RadioGroup insertGender;
    RadioButton radioBtnMale, radioBtnFemale, radioBtnOther;
    public char insert_gender_char = 'm';
    SwitchCompat insertStatus;
    public boolean status = true;
    TextView imageBtn;
    public String imagePath = "";
    Button insertBtn;

    Date dateOfBirth = null;


    //for Detail Ui
    TextView detailFirstName, detailLastName, detailDisplayName, detailUserName, detailEmail, detailPhone, detailPassword, detailDateOfBirth, detailGender, detailImage, detailDescription, detailStatus, detailCreateAt, detailUpdateAt;
    EditText detailUserId;
    Button detailBtn;

    // for Get All User Ui
    RecyclerView getAllUserRecyclerview;
    UserAdapter getAllUserAdapter;
    List<User> users = null;

    // for Update Ui
    Button updateBtnGetUid;
    EditText updateUserId, updateFirstName, updateLastName, updateDisplayName, updateUsername, updateEmail, updatePhone, updatePassword, updateImage, updateDescription;
    TextView updateDOF, updateUserNameTv;
    RadioGroup updateGender;
    RadioButton updateRadioBtnMale, updateRadioBtnFemale, updateRadioBtnOther;
    public char update_gender_char = 'm';
    SwitchCompat updateStatus;
    TextView updateImageBtn;
    public String updateImagePath = "";
    Button updateBtn;
    User updateUser = null;


    // for delete Ui
    EditText deleteUserId;
    Button deleteBtn;


    // for All Active User
    RecyclerView getAllActiveUserRecyclerview;
    UserAdapter getAllActiveUserAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setContentView(R.layout.activity_main);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        initialize();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getTimestamp();
//                    Log.i(TAG, "run: Timestamp: " + timestamp.toString());


                }
            }
        }).start();

        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                Log.i(TAG, "onChanged: " + userList.toString());
                Log.i(TAG, "onChanged: " + userList.size());

                // for all users
                getAllUserAdapter = new UserAdapter(getBaseContext(), userList);
                getAllUserRecyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                getAllUserRecyclerview.setAdapter(getAllUserAdapter);

            }
        });

        // for Active user
        userViewModel.getAllActiveUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                getAllActiveUserAdapter = new UserAdapter(getBaseContext(), users);
                getAllActiveUserRecyclerview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                getAllActiveUserRecyclerview.setAdapter(getAllActiveUserAdapter);
            }
        });


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

                        case "Get Single User":
                            viewsGone();
                            binding.GetSingleUser.setVisibility(View.VISIBLE);

                            break;

                        case "Get All Users":
                            viewsGone();
                            binding.GetAllUsers.setVisibility(View.VISIBLE);

                            break;
                        case "Update A User":
                            viewsGone();
                            binding.UpdateAUser.setVisibility(View.VISIBLE);

                            break;

                        case "Delete A User":
                            viewsGone();
                            binding.DeleteAUser.setVisibility(View.VISIBLE);

                            break;
                        case "Insert Multiple Users":
                            viewsGone();
                            binding.InsertMultipleUsers.setVisibility(View.VISIBLE);

                            break;

                        case "Find Active Users":
                            viewsGone();
                            binding.FindCompletedUsers.setVisibility(View.VISIBLE);

                            break;
                        case "Insert Single User":// this is merge  2 things like this case nad default

                        default:
                            viewsGone();
                            binding.InsertSingleUser.setVisibility(View.VISIBLE);

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

        // insert
        insertDOF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDatePicker();
            }
        });
        insertGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    RadioButton btn = (RadioButton) group.getChildAt(i);

                    if (btn.getId() == checkedId) {
                        // here gender will contain M or F.
                        if (btn.getId() == radioBtnMale.getId()) {
                            insert_gender_char = 'm';
                        } else if (btn.getId() == radioBtnFemale.getId()) {
                            insert_gender_char = 'f';
                        } else {
                            insert_gender_char = 'o';
                        }

                    }

                }

            }
        });
        insertStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                status = isChecked;
                if (isChecked) {
                    insertStatus.setText("Active");
                } else {
                    insertStatus.setText("Inactive");
                }
            }
        });
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(MainActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertSingleUser(v);
            }
        });

        // get single user
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSingleUser(view);
            }
        });

        // update user
        updateDOF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDatePicker();
            }
        });
        updateGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    RadioButton btn = (RadioButton) group.getChildAt(i);

                    if (btn.getId() == checkedId) {
                        // here gender will contain M or F.
                        if (btn.getId() == updateRadioBtnMale.getId()) {
//                        btn.setText("Male");
                            update_gender_char = 'm';
                        } else if (btn.getId() == updateRadioBtnFemale.getId()) {
//                        btn.setText("Female");
                            update_gender_char = 'f';
                        } else {
//                        btn.setText("Other");
                            update_gender_char = 'o';
                        }

                    }

                }

            }
        });
        updateStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                status = isChecked;
                if (isChecked) {
                    updateStatus.setText("Active");
                } else {
                    updateStatus.setText("Inactive");
                }
            }
        });
        updateImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(MainActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        updateBtnGetUid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // for update data set in ui before change
                Log.i(TAG, "onClick" + updateUserId.getText().toString());
                if (updateUserId != null) {
                    final User[] user = {null};

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                user[0] = UserRoomDatabase.getInstance(getApplicationContext())
                                        .userDao()
                                        .findUserById(Integer.parseInt(updateUserId.getText().toString()));

                                Log.i(TAG, "run: " + user[0].toString());
                                updateUser = user[0];

                            } catch (NullPointerException | NumberFormatException npe) {
                                Log.e(TAG, "onChanged: ", npe.fillInStackTrace());
                            }
                        }
                    }).start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setUpdateViews(user[0]);
                        }
                    }, 100);
                }

            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAUser(updateUser);
            }
        });

        // delete user
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAUser(view);
            }
        });
    }

    private void setUpdateViews(User user) {
        try {
            updateFirstName.setText(user.getFirstName());
            updateLastName.setText(user.getLastName());
            updateDisplayName.setText(user.getDisplayName());
            if (user.getUsername().equals("") || user.getUsername() == null) {
                updateUsername.setText(user.getUsername());
                updateUsername.setVisibility(View.VISIBLE);
                updateUserNameTv.setText(user.getUsername());
                updateUserNameTv.setVisibility(View.GONE);
            } else {
                updateUsername.setText(user.getUsername());
                updateUsername.setVisibility(View.GONE);
                updateUserNameTv.setText(user.getUsername());
                updateUserNameTv.setVisibility(View.VISIBLE);
            }
            updateEmail.setText(user.getEmail());
            updatePhone.setText(user.getPhone());
            updatePassword.setText(user.getPassword());

            if (user.getDateOfBirth() == null) {
                updateDOF.setText(null);
                dateOfBirth = null;
            } else {
                dateOfBirth = user.getDateOfBirth();
                updateDOF.setText(dateOfBirth.toString());
            }

            if (user.getGender() == 'm') {
                update_gender_char = 'm';
                updateGender.setActivated(updateRadioBtnMale.isActivated());
            } else if (user.getGender() == 'f') {
                update_gender_char = 'f';
                updateGender.setActivated(updateRadioBtnFemale.isActivated());
            } else {
                update_gender_char = 'o';
                updateGender.setActivated(updateRadioBtnOther.isActivated());
            }

            updateImagePath = user.getImage();
            updateImage.setText(updateImagePath);

            updateDescription.setText(user.getDescription());
            if (user.isStatus()) {
                updateStatus.setChecked(true);
            } else {
                updateStatus.setChecked(false);
            }
            updateStatus.setText(user.isStatus() ? "Active" : "InActive");

        } catch (NullPointerException exception) {
            Log.e(TAG, "setUpdateViews: ", exception.fillInStackTrace());
        }
    }

//    private void setAllUserData() {
//        // get All Users
//        getAllUsers();
//        try {
//            for (User u : users) {
//                Log.i(TAG, "onCreate: "+u.getFirstName());
//            }
//        }catch (Exception e){
//            Log.e(TAG, "onCreate: ",e.fillInStackTrace() );
//        }
//    }

    private void handleDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                dateOfBirth = new Date(year - 1900, month, date);
//                insertDOF.setText(year+"-"+month+"-"+date);
                insertDOF.setText(dateOfBirth.toString());
                updateDOF.setText(dateOfBirth.toString());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = null;
        if (data != null) {
            uri = data.getData();
            imagePath = uri.getPath();
            insertImage.setText(imagePath);

            updateImagePath = uri.getPath();
            updateImage.setText(imagePath);
        }
    }


    private void initialize() {
        try {

            chooseQuerySpinner = binding.spinnerFormat;

            // insert
            insertFirstName = (EditText) findViewById(R.id.user_insert_firstName);
            insertLastName = (EditText) findViewById(R.id.user_insert_lastName);
            insertDisplayName = (EditText) findViewById(R.id.user_insert_displayName);
            insertUserName = (EditText) findViewById(R.id.user_insert_username);
            insertEmail = (EditText) findViewById(R.id.user_insert_email);
            insertPhone = (EditText) findViewById(R.id.user_insert_phone);
            insertPassword = (EditText) findViewById(R.id.user_insert_password);
            insertDOF = (TextView) findViewById(R.id.user_insert_dateOfBirth);
            insertGender = (RadioGroup) findViewById(R.id.user_insert_gender);
            radioBtnMale = (RadioButton) findViewById(R.id.user_insert_radioMale);
            radioBtnFemale = (RadioButton) findViewById(R.id.user_insert_radioFemale);
            radioBtnOther = (RadioButton) findViewById(R.id.user_insert_radioOther);
            insertImage = (EditText) findViewById(R.id.user_insert_image);
            imageBtn = (TextView) findViewById(R.id.user_insert_image_btn);
            insertDescription = (EditText) findViewById(R.id.user_insert_description);
            insertStatus = (SwitchCompat) findViewById(R.id.user_insert_switchCompat_status);
            insertBtn = findViewById(R.id.user_insert_submit);

            // detail
            detailFirstName = (TextView) findViewById(R.id.user_detail_firstName);
            detailLastName = (TextView) findViewById(R.id.user_detail_lastName);
            detailDisplayName = (TextView) findViewById(R.id.user_detail_displayName);
            detailUserName = (TextView) findViewById(R.id.user_detail_username);
            detailEmail = (TextView) findViewById(R.id.user_detail_email);
            detailPhone = (TextView) findViewById(R.id.user_detail_phone);
            detailPassword = (TextView) findViewById(R.id.user_detail_password);
            detailDateOfBirth = (TextView) findViewById(R.id.user_detail_dateOfBirth);
            detailGender = (TextView) findViewById(R.id.user_detail_gender);
            detailImage = (TextView) findViewById(R.id.user_detail_image);
            detailDescription = (TextView) findViewById(R.id.user_detail_description);
            detailStatus = (TextView) findViewById(R.id.user_detail_status);
            detailCreateAt = (TextView) findViewById(R.id.user_detail_createAt);
            detailUpdateAt = (TextView) findViewById(R.id.user_detail_updateAt);
            detailUserId = (EditText) findViewById(R.id.user_detail_user_id);
            detailBtn = (Button) findViewById(R.id.user_detail_submit);

            // all users
            getAllUserRecyclerview = (RecyclerView) findViewById(R.id.user_read_delete_recyclerView);

            // update user
            updateUserId = (EditText) findViewById(R.id.user_update_insert_userId);
            updateBtnGetUid = (Button) findViewById(R.id.user_update_insert_userId_btn);
            updateFirstName = (EditText) findViewById(R.id.user_update_firstName);
            updateLastName = (EditText) findViewById(R.id.user_update_lastName);
            updateDisplayName = (EditText) findViewById(R.id.user_update_displayName);
            updateUsername = (EditText) findViewById(R.id.user_update_userName);
            updateUserNameTv = (TextView) findViewById(R.id.user_update_userName_tv);
            updateEmail = (EditText) findViewById(R.id.user_update_email);
            updatePhone = (EditText) findViewById(R.id.user_update_phone);
            updatePassword = (EditText) findViewById(R.id.user_update_password);
            updateDOF = (TextView) findViewById(R.id.user_update_dateOfBirth);
            updateGender = (RadioGroup) findViewById(R.id.user_update_gender);
            updateRadioBtnMale = (RadioButton) findViewById(R.id.user_update_radioMale);
            updateRadioBtnFemale = (RadioButton) findViewById(R.id.user_update_radioFemale);
            updateRadioBtnOther = (RadioButton) findViewById(R.id.user_update_radioOther);
            updateImage = (EditText) findViewById(R.id.user_update_image);
            updateImageBtn = (TextView) findViewById(R.id.user_update_image_btn);
            updateDescription = (EditText) findViewById(R.id.user_update_description);
            updateStatus = (SwitchCompat) findViewById(R.id.user_update_switchCompat_status);
            updateBtn = findViewById(R.id.user_update_submit);

            // delete user
            deleteUserId = (EditText) findViewById(R.id.user_delete_userId);
            deleteBtn = (Button) findViewById(R.id.user_delete_userId_btn);

            // get All Active User
            getAllActiveUserRecyclerview = (RecyclerView) findViewById(R.id.get_all_active_user_recyclerView);

        } catch (NullPointerException e) {
            Log.e(TAG, "initialize: ", e.fillInStackTrace());
        }
    }

    class InsertAsyncTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            try {
                // first check for user is exists or not
                User user1UserName = UserRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .findUserByUserName(users[0].getUsername());

                User user1Email = UserRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .findUserByEmail(users[0].getEmail());

                User user1Phone = UserRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .findUserByPhone(users[0].getPhone());


                if (user1UserName == null && user1Email == null && user1Phone == null) {
                    UserRoomDatabase.getInstance(getApplicationContext())
                            .userDao()
                            .insertUser(users[0]);

                } else {

                    if (user1UserName == null)
                        Log.e(TAG, "doInBackground: ");
                    else
                        Log.e(TAG, "doInBackground: " + user1UserName.getUsername());

                    if (user1Email == null)
                        Log.e(TAG, "doInBackground: ");
                    else
                        Log.e(TAG, "doInBackground: " + user1Email.getEmail());

                    if (user1Phone == null)
                        Log.e(TAG, "doInBackground: ");
                    else
                        Log.e(TAG, "doInBackground: " + user1Phone.getPhone());

                }

            } catch (SQLiteConstraintException e) {
                Log.e(TAG, "doInBackground: ", e.fillInStackTrace());
            }
            return null;
        }
    }


    private void viewsGone() {
        binding.GetSingleUser.setVisibility(View.GONE);
        binding.GetAllUsers.setVisibility(View.GONE);
        binding.DeleteAUser.setVisibility(View.GONE);
        binding.UpdateAUser.setVisibility(View.GONE);
        binding.InsertMultipleUsers.setVisibility(View.GONE);
        binding.FindCompletedUsers.setVisibility(View.GONE);
        binding.InsertSingleUser.setVisibility(View.GONE);

    }

    private void setInsertTextNull() {
        try {

            //insert all edit text clear
            insertFirstName.setText("");
            insertLastName.setText("");
            insertDisplayName.setText("");
            insertUserName.setText("");
            insertEmail.setText("");
            insertPhone.setText("");
            insertPassword.setText("");
            insertDOF.setText("");
            insertGender.setActivated(true);
//            insertGender.setText("");
            insertImage.setText("");
            insertDescription.setText("");
            insertStatus.setText("");

        } catch (NullPointerException e) {
            Log.e(TAG, "setTextNull: ", e.fillInStackTrace());
        }

    }

    private void getTimestamp() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            timestampString = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        }

        try {
            if (timestampString != null) {
                timestamp = new Timestamp(Objects.requireNonNull(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(timestampString)).getTime());
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressLint("SimpleDateFormat")
    public void insertSingleUser(View view) {

        Log.i(TAG, "insertSingleUser: Date : " + dateOfBirth);
        Log.i(TAG, "insertSingleUser: Gender : " + insert_gender_char);
        Log.e(TAG, "onClick: " + status);
        Log.e(TAG, "onClick: " + imagePath);
        Log.i(TAG, "insertSingleUser: Timestamp: " + timestamp.toString());

        User user = new User(
                insertFirstName.getText().toString(),
                insertLastName.getText().toString(),
                insertDisplayName.getText().toString(),
                insertUserName.getText().toString(),
                insertEmail.getText().toString(),
                insertPhone.getText().toString(),
                insertPassword.getText().toString(),
                dateOfBirth,
                insert_gender_char,
                insertImage.getText().toString(),
                insertDescription.getText().toString(),
                status,
                timestamp,
                timestamp
        );
//                insertGender.getText().toString().charAt(0),

        InsertAsyncTask insertAsyncTask = new InsertAsyncTask();
        insertAsyncTask.execute(user);

//        Log.d(TAG, "run: " + user.getDisplayName());
        setInsertTextNull();
    }

    public void getSingleUser(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                try {

                    user = UserRoomDatabase.getInstance(getApplicationContext())
                            .userDao()
                            .findUserById(Integer.parseInt(detailUserId.getText().toString()));
//                        .findUserById((Integer) Integer.parseInt(text.getText().toString()) instanceof Integer ? Integer.parseInt(text.getText().toString()) : 1);


                    Log.d(TAG, "run: " + user.toString());

                    //set data in ui
                    detailFirstName.setText(user.getFirstName());
                    detailLastName.setText(user.getLastName());
                    detailDisplayName.setText(user.getDisplayName());
                    detailUserName.setText(user.getUsername());
                    detailEmail.setText(user.getEmail());
                    detailPhone.setText(user.getPhone());
                    detailPassword.setText(user.getPassword());
                    detailDateOfBirth.setText(user.getDateOfBirth() != null ? user.getDateOfBirth().toString() : null);
                    if (user.getGender().equals('m')) {
                        detailGender.setText("Male");
                    } else if (user.getGender().equals('f')) {
                        detailGender.setText("Female");
                    } else {
                        detailGender.setText("Other");
                    }
                    detailImage.setText(user.getImage());
                    detailDescription.setText(user.getDescription());
                    detailStatus.setText(user.isStatus() ? "Active" : "InActive");
                    detailCreateAt.setText(user.getCreatedAt().toString());
                    detailUpdateAt.setText(user.getUpdatedAt().toString());

                } catch (Exception e) {
                    Log.e(TAG, "run: ", e.fillInStackTrace());
                    setDetailTextNull();
                } finally {

                }
            }
        }).start();
    }

    private void setDetailTextNull() {
        detailFirstName.setText(null);
        detailLastName.setText(null);
        detailDisplayName.setText(null);
        detailUserName.setText(null);
        detailEmail.setText(null);
        detailPhone.setText(null);
        detailPassword.setText(null);
        detailDateOfBirth.setText(null);
        detailGender.setText(null);
        detailImage.setText(null);
        detailDescription.setText(null);
        detailStatus.setText(null);
        detailCreateAt.setText(null);
        detailUpdateAt.setText(null);
    }

    public void getAllUsers() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> userList = UserRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .getAllUsers();

                Log.d(TAG, "run: " + userList.toString());
                users.clear();
                users.addAll(userList);
            }
        });
        thread.start();
    }

    public void deleteAUser(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = UserRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .findUserById((Integer) Integer.parseInt(deleteUserId.getText().toString()) instanceof Integer ? Integer.parseInt(deleteUserId.getText().toString()) : null);

                if (user != null) {

                    Log.d(TAG, "run: " + user.getId());
                    Log.d(TAG, "run: " + user.getDisplayName());
                    Log.d(TAG, "run: " + user.isStatus());

                    UserRoomDatabase.getInstance(getApplicationContext())
                            .userDao()
                            .deleteUser(user);
                    Log.d(TAG, "run: User has been deleted...");
                    deleteUserId.setText("");
                } else {
                    Log.d(TAG, "run: user has not exists...");
                    deleteUserId.setText("");
                }
            }
        }).start();
    }

    public void updateAUser(User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                User user = UserRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .findUserById((Integer) Integer.parseInt(updateUserId.getText().toString()) instanceof Integer ? Integer.parseInt(updateUserId.getText().toString()) : null);


                if (user != null) {
                    Log.d(TAG, "run: " + user.getId());

                    Log.i(TAG, "updateAUser: Date : " + dateOfBirth);
                    Log.i(TAG, "updateAUser: Gender : " + update_gender_char);
                    Log.e(TAG, "onClick: " + status);
                    Log.e(TAG, "onClick: " + updateImagePath);
                    Log.i(TAG, "updateAUser: Timestamp: " + timestamp.toString());

                    try {

                        user.setFirstName(updateFirstName.getText().toString());
                        user.setLastName(updateLastName.getText().toString());
                        user.setDisplayName(updateDisplayName.getText().toString());
                        user.setUsername(updateUsername.getText().toString());
                        user.setEmail(updateEmail.getText().toString());
                        user.setPhone(updatePhone.getText().toString());
                        user.setPassword(updatePassword.getText().toString());
                        user.setDateOfBirth(dateOfBirth);
                        user.setGender(update_gender_char);
                        user.setImage(updateImagePath);
                        user.setDescription(updateDescription.getText().toString());
                        user.setStatus(status);
                        user.setUpdatedAt(timestamp);

                        UserRoomDatabase.getInstance(getApplicationContext())
                                .userDao()
                                .updateUser(user);

                        Log.d(TAG, "run: user has been updated...");
                    } catch (IllegalArgumentException iae) {
                        Log.e(TAG, "run: ", iae.fillInStackTrace());
                    }
                }
            }
        }).start();
    }

    public void insertMultipleUsers(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int rend = new Random().nextInt(50);
                boolean rendB = new Random().nextBoolean();
                List<User> userList = new ArrayList<>();
//               userList.add(new User("Make a video on kotlin :" + (rend - 1), !rendB));
//               userList.add(new User("Watch black panther : " + (rend - 3), true));
//               userList.add(new User("Watch marvel movies : " + (rend - 7), rendB));
//               userList.add(new User("Make a beginner video on java : " + (rend + 3), !rendB));

                UserRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .insertMultipleUsers(userList);

                Log.d(TAG, "run: users has been inserted...");
                setInsertTextNull();

            }
        }).start();
    }

    public void findCompletedUsers(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> userList = UserRoomDatabase.getInstance(getApplicationContext())
                        .userDao()
                        .getAllCompletedUsers();

                Log.i(TAG, "run: " + userList.toString());
                users.clear();
                users.addAll(userList);
            }
        }).start();
    }
}
