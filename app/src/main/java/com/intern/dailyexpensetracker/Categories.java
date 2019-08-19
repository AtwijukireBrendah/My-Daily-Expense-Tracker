package com.intern.dailyexpensetracker;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Categories extends Fragment {
    public final static String TABLE="category";
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private List<CategoryItem> bookList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CategoryAdapter mAdapter;
    //private RelativeLayout mRelativeLayout;
    private Button mButton;

    private LinearLayout mRelativeLayout;

    String text;
    EditText tv;
    Button btn;
    private PopupWindow mPopupWindow;

    EditText etx;

   // Button but;
    boolean click = true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories, container, false);
        //part of sqlite

        //mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        //mButton = (Button) findViewById(R.id.btn);
        // Get the widgets reference from XML layout
        mRelativeLayout = (LinearLayout) view.findViewById(R.id.rl);



        dbHelper = new MyDatabaseHelper(getContext());
        database = dbHelper.getWritableDatabase();
        //part of sqlite
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //tv=(EditText) view.findViewById(R.id.tv);
        btn=(Button)view.findViewById(R.id.btn);

        mAdapter = new CategoryAdapter(bookList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*text=tv.getText().toString();
                ContentValues values = new ContentValues();

                values.put("category", tv.getText().toString());
                database.insert(TABLE, null, values);

                CategoryItem itm = new CategoryItem("0", tv.getText().toString(), "0","0");
                bookList.add(itm);
                mAdapter.notifyDataSetChanged();
                tv.setText("");*/
// Initialize a new instance of LayoutInflater service
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);

                // Inflate the custom layout/view
                View customView = inflater.inflate(R.layout.custom_layout,null);
                etx=(EditText)customView.findViewById(R.id.etx);
                Button btx=(Button)customView.findViewById(R.id.btn2);
                btx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!etx.getText().toString().matches("")) {
                            ContentValues values = new ContentValues();

                            values.put("category", etx.getText().toString());
                            database.insert(TABLE, null, values);

                            CategoryItem itm = new CategoryItem("0", etx.getText().toString(), "0", "0");
                            bookList.add(itm);
                            mAdapter.notifyDataSetChanged();
                            etx.setText("");
                            mPopupWindow.dismiss();
                        }else{
                            Toast.makeText(getContext(), "Avoid empty Fields", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                mPopupWindow = new PopupWindow(
                        customView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT
                );
                mPopupWindow.setFocusable(true);
                // Set an elevation value for popup window
                // Call requires API level 21
                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }

                // Get a reference for the custom view close button
                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                // Set a click listener for the popup window close button
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Dismiss the popup window
                        mPopupWindow.dismiss();
                    }
                });

                /*
                    public void showAtLocation (View parent, int gravity, int x, int y)
                        Display the content view in a popup window at the specified location. If the
                        popup window cannot fit on screen, it will be clipped.
                        Learn WindowManager.LayoutParams for more information on how gravity and the x
                        and y parameters are related. Specifying a gravity of NO_GRAVITY is similar
                        to specifying Gravity.LEFT | Gravity.TOP.

                    Parameters
                        parent : a parent view to get the getWindowToken() token from
                        gravity : the gravity which controls the placement of the popup window
                        x : the popup's x location offset
                        y : the popup's y location offset
                */
                // Finally, show the popup window at the center location of root relative layout
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);



            }
        });




        initOrderData();

                return view;
    }

    public Cursor fetch() {
        String countQuery = "SELECT  * FROM " + TABLE;
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE, new String[] {"_id", "category"},
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
            CategoryItem itm = new CategoryItem(cursor.getString(0), cursor.getString(1), "0","0");
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
