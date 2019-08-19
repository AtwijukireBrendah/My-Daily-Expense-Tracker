package com.intern.dailyexpensetracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Expense extends Fragment{
    public final static String TABLE="item";
    public final static String TABLETWO="category";
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    String cat;
    EditText item, amount;
    Button savedata;
    Spinner dropdown;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expense, container, false);
        item=(EditText)view.findViewById(R.id.item);
        amount=(EditText)view.findViewById(R.id.amount);
        savedata=(Button)view.findViewById(R.id.save);
        dropdown =(Spinner) view.findViewById(R.id.spinner1);

        //part of sqlite

        dbHelper = new MyDatabaseHelper(getContext());
        database = dbHelper.getWritableDatabase();







       savedata.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               ContentValues values = new ContentValues();

               SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
               String formattedDate = df.format(Calendar.getInstance().getTime());

               values.put("item", item.getText().toString());
               values.put("category", dropdown.getSelectedItem().toString());
               values.put("amount", amount.getText().toString());
               values.put("date", formattedDate);
               database.insert(TABLE, null, values);

               AppCompatActivity activity = (AppCompatActivity) view.getContext();
               Fragment myFragment = new History();
               activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).addToBackStack(null).commit();

           }
       });
        List<String> cats = new ArrayList<String>();

        Cursor cursor = fetch();
        cursor.moveToFirst();
        for(int i=0; i<cursor.getCount(); i++) {
            cats.add(cursor.getString(1));
            cursor.moveToNext();
            //mAdapter.notifyDataSetChanged();
        }



        String[] data = cats.toArray(new String[cats.size()]);

        ArrayAdapter NoCoreAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item, data);

        dropdown.setAdapter(NoCoreAdapter);

        return view;
    }
    public Cursor fetch() {
        String countQuery = "SELECT  * FROM " + TABLETWO;
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = database.query(TABLETWO, new String[] {"_id","category"},
                null, null, null, null, null);

        //  int count = cursor.getCount();
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
