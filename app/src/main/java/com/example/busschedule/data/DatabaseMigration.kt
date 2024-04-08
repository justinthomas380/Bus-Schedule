package com.example.busschedule.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class DatabaseMigration {
    companion object{

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create a temporary table with the new schema
                database.execSQL("CREATE TABLE IF NOT EXISTS bus_schedule_new (" +
                        "id INTEGER PRIMARY KEY NOT NULL," +
                        "departure_time TEXT NOT NULL," +
                        "arrival_time TEXT NOT NULL," +
                        "bus_type TEXT)")

                // Copy the data from the old table to the temporary table
                database.execSQL("INSERT INTO bus_schedule_new (id, departure_time, arrival_time) " +
                        "SELECT id, departure_time, arrival_time FROM bus_schedule")

                // Drop the old table
                database.execSQL("DROP TABLE IF EXISTS bus_schedule")

                // Rename the temporary table to the original table name
                database.execSQL("ALTER TABLE bus_schedule_new RENAME TO bus_schedule")
            }
        }
    }

}