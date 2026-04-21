package garcia.yuri.pokemonroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import garcia.yuri.pokemonroom.data.DataStoreManager
import garcia.yuri.pokemonroom.data.PokemonDatabase
import garcia.yuri.pokemonroom.data.PokemonRepository
import garcia.yuri.pokemonroom.navigation.AppNavigation
import garcia.yuri.pokemonroom.ui.theme.PokemonRoomTheme
import garcia.yuri.pokemonroom.viewModel.AuthViewModel
import garcia.yuri.pokemonroom.viewModel.PokemonViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Instanciar viewModel
        val authViewModel = AuthViewModel(DataStoreManager(this))
        val database by lazy { PokemonDatabase.getDatabase(this) } //Obtener la base de datos instanciada
        val repository by lazy { PokemonRepository(database.pokemonDao()) } //Instancias el repositorio
        val pokemonViewModel: PokemonViewModel by viewModels { PokemonViewModelFactory(repository) }

        setContent {
            PokemonRoomTheme {
                AppNavigation(authViewModel, pokemonViewModel)
            }
        }
    }

    class PokemonViewModelFactory(private val repository: PokemonRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PokemonViewModel(repository) as T
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