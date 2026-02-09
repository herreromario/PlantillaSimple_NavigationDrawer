package com.example.examenmarioherrero

import android.app.Application
import com.example.examenmarioherrero.datos.ContenedorExamen
import com.example.examenmarioherrero.datos.DefaultContenedorExamen

class ExamenAplicacion : Application() {
    lateinit var contenedor: ContenedorExamen
    override fun onCreate() {
        super.onCreate()
        contenedor = DefaultContenedorExamen(this)
    }
}