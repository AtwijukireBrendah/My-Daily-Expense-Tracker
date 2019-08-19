package com.intern.dailyexpensetracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.Inflater;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Dashboard extends Fragment {
    CardView addexpense, addcategory, history, graph;
    public final static String TABLE="item";
    public final static String TABLE2="settings";
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    TextView limittext;
    String set_limit;
    int check_limit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard, container, false);
        addexpense=(CardView)view.findViewById(R.id.addexpense);
        addcategory=(CardView)view.findViewById(R.id.addcategory);
        history=(CardView)view.findViewById(R.id.history);
        graph=(CardView)view.findViewById(R.id.graph);
        PieChartView pieChartView = view.findViewById(R.id.chart);
        limittext=(TextView)view.findViewById(R.id.limittext);
        //PieChartView pieChartView = view.findViewById(R.id.chart);
        //part of sqlite

        dbHelper = new MyDatabaseHelper(getContext());
        database = dbHelper.getWritableDatabase();
        //part of sqlite
        Random rnd = new Random();
        //int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        Cursor cursor = fetch();
        cursor.moveToFirst();
        check_limit=0;

        List<SliceValue> pieData = new ArrayList<>();
        for(int i=0; i<cursor.getCount(); i++) {
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            pieData.add(new SliceValue(Integer.parseInt(cursor.getString(3)), color).setLabel(cursor.getString(2)+"\n"+cursor.getString(3)));
            check_limit=check_limit+Integer.parseInt(cursor.getString(3));
            cursor.moveToNext();
        }

        Cursor cursor2 = fetch2();
        cursor2.moveToFirst();

        for(int i=0; i<cursor2.getCount(); i++) {
            set_limit=cursor2.getString(1);

            cursor2.moveToNext();
        }
        if(check_limit>Integer.parseInt(set_limit)){
            limittext.setText("Limit of "+set_limit+" Has been Exceeded");
            limittext.setTextColor(getResources().getColor(R.color.red));
        }else{
            limittext.setText("You are still within the limit");
            limittext.setTextColor(getResources().getColor(R.color.green));
        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        //pieChartData.setHasCenterCircle(true).setCenterText1("Sales in million").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);


        addexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new Expense();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).addToBackStack(null).commit();
            }
        });

        addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new Categories();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).addToBackStack(null).commit();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new History();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).addToBackStack(null).commit();
            }
        });

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new Graph();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }
    public Cursor fetch() {
        String countQuery = "SELECT  * FROM " + TABLE;
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE, new String[] {"_id", "item", "category", "sum(amount)", "date"},
                null, null, "category", null, null);

        //  int count = cursor.getCount();
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor fetch2() {
        String countQuery = "SELECT  * FROM " + TABLE2;
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor2 = database.query(TABLE2, new String[] {"_id", "limits"},
                null, null, null, null, null);

        //  int count = cursor.getCount();
        if (cursor2 != null) {
            cursor2.moveToFirst();
        }

        return cursor2;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
