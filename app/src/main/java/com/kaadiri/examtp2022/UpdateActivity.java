package com.kaadiri.examtp2022;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    //Binding attributes
    TextView id;
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
    DatabaseHelper mydb = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //Binding between views and java codes
        id = (TextView) findViewById(R.id.txtId);
        firstName = (EditText) findViewById(R.id.txtFirstName);
        lastName = (EditText)  findViewById(R.id.txtLastName);
        birthDay = (TextView) findViewById(R.id.txtBirthDay);
        imageName = (TextView) findViewById(R.id.txtImage);
        email = (EditText) findViewById(R.id.txtEmail);

        //geting data sent by the intent in the listePage
        Intent intent = getIntent();
        id.setText(intent.getStringExtra("id"));
        firstName.setText(intent.getStringExtra("firstname"));
        lastName.setText(intent.getStringExtra("lastname"));
        birthDay.setText(intent.getStringExtra("birthday"));
        email.setText(intent.getStringExtra("email"));

        //Listner on DatepickerDialog
        setListener = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int realMonth = i1 + 1;
                birthDay.setText(i2+"/"+realMonth+"/"+i);
            }
        };
    }


    public void onClickBtnChooseDate2(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateActivity.this, android.R.style.Theme_Holo_Light_Dialog,
                setListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    public void onClickBtnChooseImage2(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        //this class is deprecated
        //startActivityForResult(intent,SELECT_PHOTO);
        //so we use this instead
        launchSomeActivity.launch(intent);
    }

    public void onClickBtnUpdate(View view) {
        boolean res = mydb.update(Integer.parseInt(id.getText().toString()),
                firstName.getText().toString(),
                lastName.getText().toString(),
                birthDay.getText().toString(),
                image,
                email.getText().toString());
        if (res){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "The Person Is Updated", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "The Person Is Not Updated", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickBtnCancel2(View view) {
        UpdateActivity.this.finish();
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
                            int startOfImageName = selectedImageUri.toString().lastIndexOf("/");
                            imageName.setText(selectedImageUri.toString().substring(startOfImageName) + ".jpg");
                            image = DbBitmapUtility.getBytes(selectedImageBitmap);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        //imageView.setImageBitmap(selectedImageBitmap);
                    }
                }
            });


}