package com.example.busschedule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**NOTES
 * [BusSchedule::class] is an array literal and can only be used in annotations.
 * Outside annotations you must use arrayOf() or specific functions like intArrayOf()
 * ExportSchema set to false so as not to keep a schema backup history.
 **/
@Database(entities = [BusSchedule::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun busScheduleDao(): BusScheduleDao

    /**NOTES
     * Companion objects are like the static keyword in java
     * Being declared within a class companion objects can be accessed
     * without needing to create an instance of the class.
     **/
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            /**
             * Code wrapped in a synchronized block can only execute on one
             * thread at a time.
             * In this function the database only gets initialized once.
             **/
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addMigrations(DatabaseMigration.MIGRATION_1_2)
                    .createFromAsset("database/bus_schedule.db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{
                        INSTANCE = it
                    }
            }
        }
    }

}


