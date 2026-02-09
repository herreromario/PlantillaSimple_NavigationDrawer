package com.example.examenmarioherrero.datos

import android.content.Context
import com.example.examenmarioherrero.conexion.AppDataBase
import com.example.examenmarioherrero.conexion.ModeloJSAPI
import com.example.examenmarioherrero.datos.repositorios.ConexionModeloDBRepository
import com.example.examenmarioherrero.datos.repositorios.ConexionModeloJSRepository
import com.example.examenmarioherrero.datos.repositorios.ModeloBDRepository
import com.example.examenmarioherrero.datos.repositorios.ModeloJSRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorExamen {
    val modeloJSRepository: ModeloJSRepository
    val modeloBDRepository: ModeloBDRepository
}

class DefaultContenedorExamen(context: Context) : ContenedorExamen {

    // RETROFIT
    private val baseUrl = "http://10.0.2.2:3000"
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()


    private val servicioModeloJSAPI: ModeloJSAPI by lazy {
        retrofit.create(ModeloJSAPI::class.java)
    }

    override val modeloJSRepository: ModeloJSRepository by lazy {
        ConexionModeloJSRepository(servicioModeloJSAPI)
    }

    // BASE DATOS LOCAL

    override val modeloBDRepository: ModeloBDRepository by lazy {
        ConexionModeloDBRepository(AppDataBase.obtenerBaseDatos(context).modeloBDDAO())
    }
}


