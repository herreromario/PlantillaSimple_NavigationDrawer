package com.example.examenmarioherrero.datos.repositorios

import com.example.examenmarioherrero.conexion.ModeloJSAPI
import com.example.examenmarioherrero.modelos.ModeloJS
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ModeloJSRepository {
    suspend fun obtenerTodos(): List<ModeloJS>
    suspend fun insertar(modeloJS: ModeloJS): ModeloJS
    suspend fun actualizar(id: String, modeloJS: ModeloJS): ModeloJS
    suspend fun eliminar(id: String)
}

class ConexionModeloJSRepository(
    private val modeloJSAPI: ModeloJSAPI
): ModeloJSRepository {
    override suspend fun obtenerTodos(): List<ModeloJS> = modeloJSAPI.obtenerTodos()

    override suspend fun insertar(modeloJS: ModeloJS): ModeloJS = modeloJSAPI.insertar(modeloJS)

    override suspend fun actualizar(id: String, modeloJS: ModeloJS): ModeloJS = modeloJSAPI.actualizar(id, modeloJS)

    override suspend fun eliminar(id: String) = modeloJSAPI.eliminar(id)

}