package com.intern.dailyexpensetracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.BookViewHolder> {
    private List<HistoryItem> bookList;

    public HistoryAdapter(List<HistoryItem> bookList) {
        this.bookList = bookList;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_item, parent, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        /*holder.name.setText(bookList.get(position).getCustomerName());
        holder.orderid.setText(bookList.get(position).getOrderId());
        holder.total.setText(bookList.get(position).getTotalAmount());
        holder.balance.setText(bookList.get(position).getBalance());*/
       holder.item.setText(bookList.get(position).getExpense());
        holder.categor.setText(bookList.get(position).getCategory());
        holder.amount.setText(bookList.get(position).getAmount());
        holder.date.setText(bookList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView item, categor, amount, date;
        public TextView author;
        TextView disp;

        public BookViewHolder(View view) {
            super(view);
            item=(TextView)view.findViewById(R.id.item);
            categor=(TextView)view.findViewById(R.id.category);
            amount=(TextView)view.findViewById(R.id.amount);
            date=(TextView)view.findViewById(R.id.date);
        }
    }
}