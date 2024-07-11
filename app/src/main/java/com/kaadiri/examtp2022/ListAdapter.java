package com.kaadiri.examtp2022;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Person> {
    Context context;
    DatabaseHelper mydb;

    public ListAdapter (Context context, ArrayList <Person> personsList){
        super(context, R.layout.listitem, personsList);
        context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Person person = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listitem, parent, false);
        }

        TextView itemID = convertView.findViewById(R.id.itemID);
        ImageView itemImage = convertView.findViewById(R.id.itemImage);
        TextView itemFirstName = convertView.findViewById(R.id.itemFirstName);
        TextView itemLastName = convertView.findViewById(R.id.itemLastName);
        TextView itemBirthDay = convertView.findViewById(R.id.itemBirthDay);
        TextView itemEmail = convertView.findViewById(R.id.itemEmail);
        Button btnItemUpdate = convertView.findViewById(R.id.btnItemUpdate);
        Button btnItemDelete = convertView.findViewById(R.id.btnItemDelete);

        itemID.setText(person.getID().toString());
        DbBitmapUtility dbBitmapUtility =new DbBitmapUtility();
        itemFirstName.setText(person.firstName);
        itemLastName.setText(person.lastName);
        itemBirthDay.setText(person.birthDay);
        itemImage.setImageResource(R.drawable.personicon);
        //itemImage.setImageBitmap(dbBitmapUtility.getImage(person.image));
        itemEmail.setText(person.email);
        btnItemUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdateActivity.class);
                intent.putExtra("id", itemID.getText());
                intent.putExtra("firstname", itemFirstName.getText());
                intent.putExtra("lastname", itemLastName.getText());
                intent.putExtra("birthday", itemBirthDay.getText());
                intent.putExtra("email", itemEmail.getText());
                getContext().startActivity(intent);
            }
        });
        btnItemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("msg", "??????????????????????? you want delete ?????????????????");
                mydb = new DatabaseHelper(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("alert")
                        .setMessage("you are in the point of deleting a person, you want to continue?")
                        .setCancelable(true)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //finish();
                                int resultat = mydb.delete(Integer.parseInt(itemID.getText().toString())); mydb.close();
                                if (resultat != 0){
                                    Toast.makeText(getContext(), "the person is deleted", Toast.LENGTH_SHORT).show();
                                }
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                getContext().startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
            }
        });

        //return super.getView(position, convertView, parent);
        return convertView;
    }
}
