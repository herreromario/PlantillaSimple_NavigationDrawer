package com.example.examenmarioherrero.datos.repositorios

import com.example.examenmarioherrero.conexion.ModeloBDDAO
import com.example.examenmarioherrero.modelos.ModeloBD

interface ModeloBDRepository {

    suspend fun obtenerPorID (id: Int): ModeloBD
    suspend fun obtenerTodos(): List<ModeloBD>
    suspend fun insertar(modeloBD: ModeloBD)
    suspend fun actualizar(modeloBD: ModeloBD)
    suspend fun eliminar(modeloBD: ModeloBD)
}

class ConexionModeloDBRepository(
    private val modeloBDDAO: ModeloBDDAO
): ModeloBDRepository {
    override suspend fun obtenerPorID(id: Int): ModeloBD = modeloBDDAO.obtenerPorID(id)
    override suspend fun obtenerTodos(): List<ModeloBD> = modeloBDDAO.obtenerTodos()
    override suspend fun insertar(modeloBD: ModeloBD) = modeloBDDAO.insertar(modeloBD)
    override suspend fun actualizar(modeloBD: ModeloBD) = modeloBDDAO.actualizar(modeloBD)
    override suspend fun eliminar(modeloBD: ModeloBD) = modeloBDDAO.eliminar(modeloBD)
}


