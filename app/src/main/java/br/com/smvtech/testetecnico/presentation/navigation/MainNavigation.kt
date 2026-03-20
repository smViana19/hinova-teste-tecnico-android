package br.com.smvtech.testetecnico.presentation.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.smvtech.testetecnico.presentation.screens.HomeScreen
import br.com.smvtech.testetecnico.presentation.screens.LoginScreen
import br.com.smvtech.testetecnico.presentation.screens.WorkshopDetailsScreen
import br.com.smvtech.testetecnico.presentation.viewmodel.HomeScreenViewmodel
import br.com.smvtech.testetecnico.presentation.viewmodel.LoginViewmodel

@Composable
fun MainNavigation(startNavigation: String) {
    val navController = rememberNavController()
    val loginViewModel: LoginViewmodel = hiltViewModel()
    val homeViewModel: HomeScreenViewmodel = hiltViewModel()

    Scaffold(content = {
        NavHost(navController = navController, startDestination = startNavigation) {
            composable(route = Screens.LOGIN_SCREEN.name) {
                LoginScreen(navController = navController)
            }
            composable(route = Screens.HOME_SCREEN.name) {
                HomeScreen(navController = navController, viewModel = homeViewModel)
            }
            composable(route = Screens.WORKSHOP_DETAIL_SCREEN.name) {
                WorkshopDetailsScreen(navController = navController)
            }
        }
    })


}