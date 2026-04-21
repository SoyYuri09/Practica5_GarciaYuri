package garcia.yuri.pokemonroom.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import garcia.yuri.pokemonroom.viewModel.AuthViewModel

@Composable
fun MainScreen(viewModel: AuthViewModel) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val username by viewModel.username.collectAsState()

    if (isLoggedIn) {
        HomeScreen(username) {
            viewModel.logout()
        }
    } else {
        LoginScreen(viewModel)
    }

}