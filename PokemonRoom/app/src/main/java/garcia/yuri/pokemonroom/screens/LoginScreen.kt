package garcia.yuri.pokemonroom.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import garcia.yuri.pokemonroom.data.DataStoreManager
import garcia.yuri.pokemonroom.viewModel.AuthViewModel

@Composable
fun LoginScreen(
    //onLogginSuccess: () -> Unit
    viewModel: AuthViewModel
){
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = "Inicio de sesión",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Contraseña") },
            modifier = Modifier
                .fillMaxWidth()
        )



        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                //onLogginSuccess()
                viewModel.login(user, pass)
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text(text = "Ingresar") }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen(viewModel = AuthViewModel(DataStoreManager(LocalContext.current)))
}