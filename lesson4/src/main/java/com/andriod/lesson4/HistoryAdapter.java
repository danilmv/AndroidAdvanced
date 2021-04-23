package com.andriod.lesson4;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import androidx.recyclerview.widget.SortedListAdapterCallback;
import androidx.room.Room;

import com.andriod.lesson4.dao.HistoryDao;
import com.andriod.lesson4.database.HistoryDatabase;
import com.andriod.lesson4.model.History;
import com.squareup.picasso.Picasso;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private static SortedList<History> data;
    private static HistoryAdapter instance;

    private static HistoryDatabase db;
    private static HistoryDao dao;

    public HistoryAdapter(Context context) {
        instance = this;
        if (data == null)
            data = new SortedList<History>(History.class, new SortedListCallback(this));

        if (db == null)
            db = Room.databaseBuilder(
                    context,
                    HistoryDatabase.class,
                    "history_database")
                    .allowMainThreadQueries()
                    .build();

        if (dao == null)
            dao = db.getHistoryDao();

        data.addAll(dao.getAllHistory());
    }

    public HistoryDao getHistoryDao() {
        return db.getHistoryDao();
    }

    public static HistoryAdapter getInstance() {
        return instance;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(History history) {
        HistoryAdapter.data.add(history);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textCity;
        TextView textTemperature;
        ImageView imageIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textCity = itemView.findViewById(R.id.historyCity);
            textTemperature = itemView.findViewById(R.id.historyTemperature);
            imageIcon = itemView.findViewById(R.id.historyIcon);
        }

        public void setData(History data) {
            if (data == null) return;

            textCity.setText(data.getCity());
            textTemperature.setText(data.getFormattedTemperature());
            Picasso.get()
                    .load(String.format("http://openweathermap.org/img/w/%s.png", data.getIcon()))
                    .fit()
                    .into(imageIcon);
        }
    }

    static class SortedListCallback extends SortedListAdapterCallback<History> {
        public SortedListCallback(RecyclerView.Adapter adapter) {
            super(adapter);
        }

        @Override
        public int compare(History o1, History o2) {
            return o1.compareTo(o2);
        }

        @Override
        public boolean areContentsTheSame(History oldItem, History newItem) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(History item1, History item2) {
            return item1.compareTo(item2) == 0;
        }

        @Override
        public void onInserted(int position, int count) {
            super.onInserted(position, count);

            dao.insertHistory(data.get(position));
        }
    }
}
