package garcia.yuri.pokemonroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import garcia.yuri.pokemonroom.data.DataStoreManager
import garcia.yuri.pokemonroom.navigation.AppNavigation
import garcia.yuri.pokemonroom.ui.theme.PokemonRoomTheme
import garcia.yuri.pokemonroom.viewModel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Instanciar viewModel
        val authViewModel = AuthViewModel(DataStoreManager(this))

        setContent {
            PokemonRoomTheme {
                AppNavigation(authViewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokemonRoomTheme {
        Greeting("Android")
    }
}