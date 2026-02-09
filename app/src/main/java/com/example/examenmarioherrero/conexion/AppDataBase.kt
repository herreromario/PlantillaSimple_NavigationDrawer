package com.example.examenmarioherrero.conexion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.examenmarioherrero.modelos.ModeloBD

// Base de datos local
@Database(entities = [ModeloBD::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun modeloBDDAO(): ModeloBDDAO
    companion object {
        @Volatile
        private var Instance: AppDataBase? = null

        fun obtenerBaseDatos(context: Context): AppDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDataBase::class.java, "appdatabase")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}