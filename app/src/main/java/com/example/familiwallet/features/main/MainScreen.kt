import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.familiwallet.components.*
import com.example.familiwallet.core.common.CategoryType
import com.example.familiwallet.core.common.TimeRangeType
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.loading.LoadingScreen
import com.example.familiwallet.features.main.MainScreenViewState
import com.example.familiwallet.features.main.MainViewModel
import com.example.familiwallet.features.main.data.Transaction

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigation: NavHostController = rememberNavController()
) {

    val uiState by viewModel.getUiState()

    var floatingActionState by remember {
        mutableStateOf(FloatingActionState.COLLAPSED)
    }
    when (uiState) {
        is UiState.Success -> {

            val viewState = (uiState as UiState.Success<MainScreenViewState>).data
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                floatingActionButton = {
                    ActionButton(
                        floatingActionState,
                        navigation
                    ) { floatingActionState = it }
                },
                floatingActionButtonPosition = FabPosition.Center
            ) {
                LazyColumn {
                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Заголовок 1",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        CategoryBlock()
                    }

                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Период",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        TimeRangePicker(selectedTimeRange = TimeRangeType.MONTH, onTimeRangeClicked = { viewModel.setUiRangeState(it) })
                    }

                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Доходы",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        CategoryList(list = viewState.incomesList)
                    }

                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Расходы",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        CategoryList(list = viewState.expensesList)
                    }

                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Операции",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        TransactionsList(transactionList = viewState.transactionsList)
                    }

//                    for transactions
//                    items(viewState.incomesList.windowed(1, 1, true)) { list ->
//                        list.forEach { item -> TransactionRow(item) }
//                    }
                }
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



