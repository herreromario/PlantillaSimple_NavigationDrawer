package com.example.examenmarioherrero.conexion

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.examenmarioherrero.modelos.ModeloBD

@Dao
interface ModeloBDDAO {
    @Query("SELECT * FROM ModeloBD WHERE id = :id")
    suspend fun obtenerPorID (id: Int): ModeloBD

    @Query("SELECT * FROM ModeloBD ORDER BY id ASC")
    suspend fun obtenerTodos(): List<ModeloBD>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(modeloBD: ModeloBD)

    @Update
    suspend fun actualizar(modeloBD: ModeloBD)

    @Delete
    suspend fun eliminar(modeloBD: ModeloBD)
}