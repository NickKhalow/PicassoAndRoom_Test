package com.example.productswithpictures.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM item")
    LiveData<List<Item>> getAll();

    @Insert
    void insert(Item... item);
}
