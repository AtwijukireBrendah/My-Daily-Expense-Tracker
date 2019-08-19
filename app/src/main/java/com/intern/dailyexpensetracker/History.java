package com.intern.dailyexpensetracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class History extends Fragment {
    private List<HistoryItem> bookList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HistoryAdapter mAdapter;

    public final static String TABLE="item";
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        //part of sqlite

        dbHelper = new MyDatabaseHelper(getContext());
        database = dbHelper.getWritableDatabase();
        //part of sqlite

        mAdapter = new HistoryAdapter(bookList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        initOrderData();

        return view;
    }
    public Cursor fetch() {
        String countQuery = "SELECT  * FROM " + TABLE;
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE, new String[] {"_id", "item", "category", "amount", "date"},
                null, null, null, null, null);

        //  int count = cursor.getCount();
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    private void initOrderData() {
        Cursor cursor = fetch();
        cursor.moveToFirst();
        for(int i=0; i<cursor.getCount(); i++) {
            HistoryItem itm = new HistoryItem(cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getString(4));
            bookList.add(itm);

            cursor.moveToNext();
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
