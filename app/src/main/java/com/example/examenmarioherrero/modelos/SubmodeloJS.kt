package com.example.examenmarioherrero.modelos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubmodeloJS (
    @SerialName("id")
    val id: String = "",
    @SerialName("nombre")
    val nombre: String,
)