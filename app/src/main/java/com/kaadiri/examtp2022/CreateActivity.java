package com.kaadiri.examtp2022;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

public class CreateActivity extends AppCompatActivity {

    //Binding attributes
    EditText firstName;
    EditText lastName;
    TextView birthDay;
    TextView imageName;
    byte [] image;
    EditText email;

    //Datepicker attributes
    DatePickerDialog.OnDateSetListener setListener;
    Calendar calendar = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);

    //Helpers
    DatabaseHelper mydb;// = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //Binding between views and java codes
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText)  findViewById(R.id.lastName);
        birthDay = (TextView) findViewById(R.id.birthDay);
        imageName = (TextView) findViewById(R.id.personImage);
        email = (EditText) findViewById(R.id.personEmail);

        //Initialize Helpers
        mydb = new DatabaseHelper(this);
        //Listner on DatepickerDialog
        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int realMonth = i1 + 1;
                birthDay.setText(i2+"/"+realMonth+"/"+i);
            }
        };


    }


    public void onClickBtnCreate(View view) {
        boolean inserted = this.mydb.insert(this.firstName.getText().toString(), this.lastName.getText().toString(),this.birthDay.getText().toString(),
                image, this.email.getText().toString());
        if (inserted){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "the person is inserted", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "the person is not inserted", Toast.LENGTH_SHORT).show();
        }
    }



    public void onClickBtnChooseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //this class is deprecated
        //startActivityForResult(intent,SELECT_PHOTO);
        //so we use this instead
        launchSomeActivity.launch(intent);
    }

    public void onClickBtnChooseDate(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateActivity.this, android.R.style.Theme_Holo_Light_Dialog,
                setListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }

    //More attributes
    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap=null;
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                            //imageView.setImageBitmap(selectedImageBitmap);
                            imageName.setText(selectedImageUri.toString());
                            image = DbBitmapUtility.getBytes(selectedImageBitmap);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        //imageView.setImageBitmap(selectedImageBitmap);
                    }
                }
            });

    public void onClickBtnCancel(View view) {
        /*
        Cursor res = mydb.select();
        Log.d("number of elements", res.getCount() + "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

         */
        CreateActivity.this.finish();
    }
}