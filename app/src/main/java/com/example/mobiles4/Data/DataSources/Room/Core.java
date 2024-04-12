package com.example.mobiles4.Data.DataSources.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mobiles4.Data.DataSources.Room.DAO.TaskDao;
import com.example.mobiles4.Data.DataSources.Room.Entities.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class Core extends RoomDatabase {
    public abstract TaskDao taskDao();
}
