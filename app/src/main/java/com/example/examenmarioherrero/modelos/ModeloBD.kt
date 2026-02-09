package com.example.examenmarioherrero.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ModeloBD")
data class ModeloBD(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
)