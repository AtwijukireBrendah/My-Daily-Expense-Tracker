package com.intern.dailyexpensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.BookViewHolder> {
    private List<CategoryItem> bookList;

    public CategoryAdapter(List<CategoryItem> bookList) {
        this.bookList = bookList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_item, parent, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        /*holder.name.setText(bookList.get(position).getCustomerName());
        holder.orderid.setText(bookList.get(position).getOrderId());
        holder.total.setText(bookList.get(position).getTotalAmount());
        holder.balance.setText(bookList.get(position).getBalance());*/
        holder.disp.setText(bookList.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView name, contact, orderid, date, orders, total, balance;
        public TextView author;
        TextView disp;

        public BookViewHolder(View view) {
            super(view);
            disp=(TextView)view.findViewById(R.id.cate);
        }
    }
}