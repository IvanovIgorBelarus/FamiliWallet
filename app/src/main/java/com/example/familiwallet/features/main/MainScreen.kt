import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.familiwallet.components.ActionButton
import com.example.familiwallet.components.BottomBar
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.loading.LoadingScreen
import com.example.familiwallet.features.main.MainScreenViewState
import com.example.familiwallet.features.main.MainViewModel
import com.example.familiwallet.navigation.MainScreenNavigation

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigation: NavHostController = rememberNavController()
) {
    val uiState by viewModel.getUiState()

    when (uiState) {
        is UiState.Success -> {
            val viewState = (uiState as UiState.Success<MainScreenViewState>).data
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = { BottomBar(navigation = navigation) },
                floatingActionButton = {
                    ActionButton(navigation = navigation)
                },
                isFloatingActionButtonDocked = true,
                floatingActionButtonPosition = FabPosition.Center
            ) {
                MainScreenNavigation(navigation = navigation)
            }
        }
        is UiState.Error -> {}
        is UiState.Loading -> {
            LoadingScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen()
}



