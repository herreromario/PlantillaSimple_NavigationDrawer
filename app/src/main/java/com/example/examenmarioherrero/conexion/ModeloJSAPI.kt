package com.example.examenmarioherrero.conexion

import com.example.examenmarioherrero.modelos.ModeloJS
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ModeloJSAPI {
    @GET("elementos")
    suspend fun obtenerTodos(): List<ModeloJS>

    @POST("elementos")
    suspend fun insertar(
        @Body modeloJS: ModeloJS
    ): ModeloJS

    @PUT("elementos/{id}")
    suspend fun actualizar(
        @Path("id") id: String,
        @Body modeloJS: ModeloJS
    ): ModeloJS

    @DELETE("elementos/{id}")
    suspend fun eliminar(
        @Path("id") id: String
    )
}