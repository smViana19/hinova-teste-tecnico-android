package br.com.smvtech.testetecnico.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.smvtech.testetecnico.presentation.screens.HomeScreen
import br.com.smvtech.testetecnico.presentation.screens.LoginScreen
import br.com.smvtech.testetecnico.presentation.screens.WorkshopDetailsScreen
import br.com.smvtech.testetecnico.presentation.viewmodel.HomeScreenViewmodel
import br.com.smvtech.testetecnico.presentation.viewmodel.LoginViewmodel
import br.com.smvtech.testetecnico.presentation.viewmodel.WorkshopDetailsViewmodel

@Composable
fun MainNavigation(startNavigation: String) {
    val navController = rememberNavController()
    val loginViewModel: LoginViewmodel = hiltViewModel()
    val homeViewModel: HomeScreenViewmodel = hiltViewModel()
    val workshopDetailsViewmodel: WorkshopDetailsViewmodel = hiltViewModel()

    Scaffold(content = {
        NavHost(
            navController = navController,
            startDestination = startNavigation,
        ) {
            composable(route = Screens.LOGIN_SCREEN.name) {
                LoginScreen(navController = navController, loginViewModel)
            }
            composable(route = Screens.HOME_SCREEN.name) {
                HomeScreen(navController = navController, viewModel = homeViewModel)
            }
            composable(
                route = "${Screens.WORKSHOP_DETAIL_SCREEN.name}?workshopId={workshopId}",
                arguments = listOf(
                    navArgument("workshopId") {
                        type = NavType.IntType
                    })
            ) { navBackStackEntry ->
                val workshopId = navBackStackEntry.arguments?.getInt("workshopId")
                    ?: throw IllegalStateException("workshopId não encontrado nos argumentos de navegação")
                WorkshopDetailsScreen(
                    navController = navController,
                    viewModel = workshopDetailsViewmodel,
                    workshopId = workshopId
                )
            }
        }
    })


}