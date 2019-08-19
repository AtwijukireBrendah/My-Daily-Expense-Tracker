package com.intern.dailyexpensetracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Settings extends Fragment {
    public final static String TABLE="settings";
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    EditText text_tosave;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);
        text_tosave=(EditText)view.findViewById(R.id.limit);

        btn=(Button)view.findViewById(R.id.savebtn);
        //part of sqlite
        dbHelper = new MyDatabaseHelper(getContext());
        database = dbHelper.getWritableDatabase();
        //part of sqlite

        Cursor cursor = fetch();
        cursor.moveToFirst();
        for(int i=0; i<cursor.getCount(); i++) {

            text_tosave.setText(cursor.getString(1));
            cursor.moveToNext();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!text_tosave.getText().toString().matches("")) {
                    ContentValues values = new ContentValues();

                    values.put("limits", text_tosave.getText().toString());
                    database.insert(TABLE, null, values);

                    Toast.makeText(getContext(), "Limit set successfully", Toast.LENGTH_SHORT).show();
                    //text_tosave.setText("");

                }else{
                    Toast.makeText(getContext(), "Avoid empty Fields", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }
    public Cursor fetch() {
        String countQuery = "SELECT  * FROM " + TABLE;
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE, new String[] {"_id", "limits"},
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
