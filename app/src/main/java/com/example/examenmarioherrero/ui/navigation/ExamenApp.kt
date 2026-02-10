package com.example.examenmarioherrero.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.examenmarioherrero.R
import com.example.examenmarioherrero.modelos.DrawerMenu
import com.example.examenmarioherrero.ui.screens.PantallaInsertar
import com.example.examenmarioherrero.ui.screens.PantallaListar
import com.example.examenmarioherrero.ui.viewmodel.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class Pantallas(@StringRes val titulo: Int) {
    Listar(R.string.listar),
    Insertar(R.string.insertar),
    Detalle(R.string.detalle)
}

val menu = arrayOf(
    DrawerMenu(Icons.AutoMirrored.Filled.List, Pantallas.Listar.titulo, Pantallas.Listar.name),
)

@Composable
fun ExamenApp(
    appViewModel: AppViewModel,
    navController: NavHostController = rememberNavController(),
    coroutine: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) {
    val pilaRetroceso by navController.currentBackStackEntryAsState()

    val pantallaActual = Pantallas.valueOf(
        pilaRetroceso?.destination?.route ?: Pantallas.Listar.name
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { 
            ModalDrawerSheet {
                DrawerContent(
                    menu = menu,
                    pantallaActual = pantallaActual
                ) { ruta ->
                    coroutine.launch {
                        drawerState.close()
                    }

                    navController.navigate(ruta)

                }
            }
        },

        ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    pantallaActual = pantallaActual,
                    puedeNavegarAtras = navController.previousBackStackEntry != null,
                    onNavegarAtras = { navController.navigateUp()},

                    drawerState = drawerState
                )
            },

            floatingActionButton = {
                if (pantallaActual == Pantallas.Listar) {
                    FloatingActionButton(
                        onClick = { navController.navigate(Pantallas.Insertar.name) }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = stringResource(R.string.anadir)
                        )
                    }
                }
            }
        ) { innerPadding ->

            val appUIState = appViewModel.appUIState

            NavHost(
                navController = navController,
                startDestination = Pantallas.Listar.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Pantallas.Listar.name) { PantallaListar() }
                composable(route = Pantallas.Insertar.name) { PantallaInsertar() }
            }

        }
    }
}

@Composable
fun DrawerContent(
    menu: Array<DrawerMenu>,
    pantallaActual: Pantallas,
    onMenuClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        menu.forEach {
            NavigationDrawerItem(
                label = { Text(text = stringResource(id = it.titulo)) },
                icon = { Icon(imageVector = it.icono, contentDescription = null) },
                selected = it.titulo == pantallaActual.titulo,
                onClick = {
                    onMenuClick(it.ruta)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    pantallaActual: Pantallas,
    puedeNavegarAtras: Boolean,
    onNavegarAtras: () -> Unit,
    drawerState: DrawerState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    TopAppBar(
        title = { Text(text = stringResource(id = pantallaActual.titulo)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            when {
                puedeNavegarAtras && (pantallaActual == Pantallas.Insertar ||
                        pantallaActual == Pantallas.Detalle) -> {
                    IconButton(onClick = onNavegarAtras) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.atras)
                        )
                    }
                }

                else -> {
                    IconButton(
                        onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }
                    ) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = stringResource(R.string.atras)
                        )
                    }
                }
            }
        }
    )
}