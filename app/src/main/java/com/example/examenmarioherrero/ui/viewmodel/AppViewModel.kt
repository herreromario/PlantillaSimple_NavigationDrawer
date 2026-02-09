package com.example.examenmarioherrero.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.examenmarioherrero.ExamenAplicacion
import com.example.examenmarioherrero.datos.repositorios.ModeloBDRepository
import com.example.examenmarioherrero.datos.repositorios.ModeloJSRepository
import com.example.examenmarioherrero.modelos.ModeloBD
import com.example.examenmarioherrero.modelos.ModeloJS
import kotlinx.coroutines.launch

sealed interface AppUIState {
    data class ObtenerTodosExito(val listaModelosJS: List<ModeloJS>,
                                 val listaModelosBD: List<ModeloBD>) : AppUIState

    data class ObtenerModeloBDExito(val modeloBD: ModeloBD) : AppUIState

    object InsertarExito : AppUIState
    object ActualizarExito : AppUIState
    object EliminarExito : AppUIState
    object Error : AppUIState
    object Cargando : AppUIState
}

class AppViewModel(
    private val modeloJSRepository: ModeloJSRepository,
    private val modeloBDRepository: ModeloBDRepository
) : ViewModel() {

    var appUIState: AppUIState by mutableStateOf(AppUIState.Cargando)
        private set

    var modeloJDPulsado: ModeloJS by mutableStateOf(ModeloJS("", "", emptyList()))

    var modeloBDPulsado: ModeloBD by mutableStateOf(ModeloBD(0, ""))

    init {
        obtenerTodos()
    }

    fun obtenerTodos(){
        viewModelScope.launch {
            appUIState = try {
                val listaModelosJS = modeloJSRepository.obtenerTodos()
                val listaModelosBD = modeloBDRepository.obtenerTodos()
                AppUIState.ObtenerTodosExito(listaModelosJS, listaModelosBD)
            } catch (e: Exception) {
                AppUIState.Error
            }
        }
    }

    fun obtenerModeloBD(id: Int){
        viewModelScope.launch {
            appUIState = try {
                val modeloBD = modeloBDRepository.obtenerPorID(id)
                AppUIState.ObtenerModeloBDExito(modeloBD)
            } catch (e: Exception) {
                AppUIState.Error
            }
        }
    }

    fun insertarModeloJS(modeloJS: ModeloJS) {
        viewModelScope.launch {
            appUIState = try {
                modeloJSRepository.insertar(modeloJS)
                AppUIState.InsertarExito
            } catch (e: Exception) {
                AppUIState.Error
            }
        }
    }

    fun insertarModeloBD(modeloBD: ModeloBD) {
        viewModelScope.launch {
            appUIState = try {
                modeloBDRepository.insertar(modeloBD)
                AppUIState.InsertarExito
            } catch (e: Exception) {
                AppUIState.Error
            }
        }
    }

    fun actualizarModeloJS(id: String, modeloJS: ModeloJS) {
        viewModelScope.launch {
            appUIState = try {
                modeloJSRepository.actualizar(id, modeloJS)
                AppUIState.ActualizarExito
            } catch (e: Exception) {
                AppUIState.Error
            }
        }
    }

    fun actualizarModeloBD(modeloBD: ModeloBD) {
        viewModelScope.launch {
            appUIState = try {
                modeloBDRepository.actualizar(modeloBD)
                AppUIState.ActualizarExito
            } catch (e: Exception) {
                AppUIState.Error
            }
        }
    }

    fun eliminarModeloJS(id: String) {
        viewModelScope.launch {
            appUIState = try {
                modeloJSRepository.eliminar(id)
                AppUIState.EliminarExito
            } catch (e: Exception) {
                AppUIState.Error
            }
        }
    }

    fun eliminarModeloBD(modeloBD: ModeloBD) {
        viewModelScope.launch {
            appUIState = try {
                modeloBDRepository.eliminar(modeloBD)
                AppUIState.EliminarExito
            } catch (e: Exception) {
                AppUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as ExamenAplicacion)
                val modeloJSRepository = aplicacion.contenedor.modeloJSRepository
                val modeloBDRepository = aplicacion.contenedor.modeloBDRepository
                AppViewModel(
                    modeloJSRepository = modeloJSRepository,
                    modeloBDRepository = modeloBDRepository
                )
            }
        }
    }
}