import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.components.ActionButton
import com.example.components.BottomBar
import com.example.expenseobserver.components.TopBar
import com.example.data.UIModel
import com.example.data.theme.UiState
import com.example.components.LoadingScreen
import com.example.expenseobserver.features.transacrionscreen.TransactionDialog
import com.example.expenseobserver.features.transacrionscreen.TransactionViewModel
import com.example.expenseobserver.navigation.MainScreenNavigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun MainScreen(
    navController: NavHostController = rememberAnimatedNavController(),
    transactionViewModel: TransactionViewModel = hiltViewModel()
) {
    val showDialog = remember { mutableStateOf(false) }
    val categoriesList = remember { mutableStateOf(emptyList<UIModel.CategoryModel>()) }
    val walletsList = remember { mutableStateOf(emptyList<UIModel.WalletModel>()) }
    val uiState by transactionViewModel.getUiState()
    val update = remember { mutableStateOf(false) }

    if (showDialog.value) {
        TransactionDialog(
            categoryData = categoriesList.value, dismissDialog = {
                showDialog.value = false
            },
            walletData = walletsList.value,
            onButtonClick = { model ->
                showDialog.value = false
                transactionViewModel.addTransaction(model) {
                    update.value = !update.value
                }
            }
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navigation = navController) },
        bottomBar = { BottomBar(navigation = navController) },
        floatingActionButton = {
            ActionButton {
                transactionViewModel.getTransactionDialogData { categoriesData, walletsData ->
                    categoriesList.value = categoriesData
                    walletsList.value = walletsData
                    showDialog.value = true
                }
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        MainScreenNavigation(
            update = update,
            navigation = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 65.dp)
        )
    }

    when (uiState) {
        is UiState.Success -> {}
        is UiState.Error -> {}
        is UiState.Loading -> {
            LoadingScreen()
        }
    }

    LaunchedEffect(Unit) {
        transactionViewModel.getData()
    }

}



