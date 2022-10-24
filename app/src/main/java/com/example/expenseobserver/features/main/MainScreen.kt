import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.components.ActionButton
import com.example.expenseobserver.components.BottomBar
import com.example.expenseobserver.components.TopBar
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.features.transacrionscreen.TransactionDialog
import com.example.expenseobserver.features.transacrionscreen.TransactionViewModel
import com.example.expenseobserver.navigation.MainScreenNavigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalAnimationApi
@Composable
fun MainScreen(
    navController: NavHostController = rememberAnimatedNavController(),
    transactionViewModel: TransactionViewModel = hiltViewModel()
) {
    val forceLoad = remember { mutableStateOf(true) }
    val showDialog = remember { mutableStateOf(false) }
    val transactionData = remember { mutableStateOf(emptyList<UIModel.CategoryModel>()) }

    if (showDialog.value) {
        TransactionDialog(
            data = transactionData.value, dismissDialog = {
                showDialog.value = false
            },
            onButtonClick = { model ->
                transactionViewModel.addTransaction(model) {
                    forceLoad.value = true
                    showDialog.value = false
                }
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
        MainScreenNavigation(
            forceLoad = forceLoad,
            navigation = navController,
            Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 65.dp)
        )
    }
}



