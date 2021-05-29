package com.example.productswithpictures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.productswithpictures.adapter.ItemListAdapter;
import com.example.productswithpictures.data.Item;
import com.example.productswithpictures.data.ItemDb;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemListAdapter adapter;

    ItemDb db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room
                .databaseBuilder(getApplicationContext(), ItemDb.class, "db")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase dbSupport) {
                        Executors.newSingleThreadScheduledExecutor().execute(() -> {
                            db.itemDao().insert(
                                    new Item("Dog", 100, "https://picsum.photos/id/237/1000/"),
                                    new Item("Picture B/W", 1000, "https://picsum.photos/id/151/1000"),
                                    new Item("Laptop", 5000, "https://picsum.photos/id/1/1000"));
                        });
                    }
                })
                .build();

        adapter = new ItemListAdapter();

        recyclerView = findViewById(R.id.products_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        db.itemDao().getAll().observe(this, items -> adapter.submitList(items));
    }
}
