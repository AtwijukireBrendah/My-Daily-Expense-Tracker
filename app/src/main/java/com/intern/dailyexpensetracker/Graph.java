package com.intern.dailyexpensetracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Graph extends Fragment {
    public final static String TABLE="item";
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    int check_limit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph, container, false);
        PieChartView pieChartView = view.findViewById(R.id.chart);
        //part of sqlite

        dbHelper = new MyDatabaseHelper(getContext());
        database = dbHelper.getWritableDatabase();
        //part of sqlite
        Random rnd = new Random();

        Cursor cursor = fetch();
        cursor.moveToFirst();
        List<SliceValue> pieData = new ArrayList<>();
        check_limit=0;
        for(int i=0; i<cursor.getCount(); i++) {
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            pieData.add(new SliceValue(Integer.parseInt(cursor.getString(3)), color).setLabel(cursor.getString(2)+" \n"+cursor.getString(3)));
            check_limit=check_limit+Integer.parseInt(cursor.getString(3));
            cursor.moveToNext();
        }

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("Total: UGX."+check_limit).setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);
        return view;
    }
    public Cursor fetch() {
        String countQuery = "SELECT  SUM(amount)as  FROM " + TABLE;
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE, new String[] {"_id", "item", "category", "sum(amount)", "date"},
                null, null, "category", null, null);

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
