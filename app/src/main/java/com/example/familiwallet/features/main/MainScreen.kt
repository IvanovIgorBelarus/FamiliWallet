import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.familiwallet.components.ActionButton
import com.example.familiwallet.components.BottomBar
import com.example.familiwallet.components.TopBar
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.dialog.ShowDialog
import com.example.familiwallet.features.loading.LoadingScreen
import com.example.familiwallet.features.transacrionscreen.TransactionDialog
import com.example.familiwallet.features.transacrionscreen.TransactionViewModel
import com.example.familiwallet.navigation.MainScreenNavigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalAnimationApi
@Composable
fun MainScreen(
    navController: NavHostController = rememberAnimatedNavController(),
    transactionViewModel: TransactionViewModel = hiltViewModel()
) {
    val showDialog = remember { mutableStateOf(false) }
    val transactionData = remember { mutableStateOf(emptyList<UIModel.CategoryModel>()) }
    val transactionState by transactionViewModel.getTransactionState()

    if (showDialog.value) {
        TransactionDialog(
            data = transactionData.value, dismissDialog = {
                showDialog.value = false
            },
            onButtonClick = { model ->
                showDialog.value = false
                transactionViewModel.addTransaction(model)
            }
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navigation = navController) },
        bottomBar = { BottomBar(navigation = navController) },
        floatingActionButton = {
            ActionButton(navigation = navController) {
                transactionViewModel.getCategories { data ->
                    transactionData.value = data
                    showDialog.value = true
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        when (transactionState) {
            is UiState.Success<Unit> -> {
                MainScreenNavigation(
                    navigation = navController,
                    Modifier
                        .fillMaxSize()
                        .padding(0.dp, 0.dp, 0.dp, 65.dp)
                )
            }
            is UiState.Error -> {
                val errorText = (transactionState as UiState.Error).exception.message
                ShowDialog(text = errorText)
            }
            is UiState.Loading -> {
                LoadingScreen()
            }
        }
    }
}



