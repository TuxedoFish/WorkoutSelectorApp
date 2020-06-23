package com.example.workoutselector.backend.db.queries;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.workoutselector.backend.db.entities.SettingTable;

import java.util.Date;
import java.util.List;

@Dao
public interface AppDao {

    /* Getters */

    @Query("SELECT * FROM settings")
    List<SettingTable> getSettings();

    /* Updates */
    @Query("UPDATE settings SET activated = :activated WHERE id = :id")
    int updateSetting(int id, boolean activated);

    /* Inserters */

    @Insert
    void insertSettings(SettingTable setting);

    @Insert
    void insertAllSettings(List<SettingTable> settings);

    /* Deleters */
    @Query("DELETE FROM settings")
    void deleteSettings();

    @Query("DELETE FROM settings where id=:id")
    void deleteSettingWithID(int id);
}
