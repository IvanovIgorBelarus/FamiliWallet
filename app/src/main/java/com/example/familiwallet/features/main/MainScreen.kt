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
import com.example.familiwallet.components.*
import com.example.familiwallet.core.common.CategoryType
import com.example.familiwallet.core.common.TimeRangeType
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.main.MainViewModel
import com.example.familiwallet.features.main.data.Transaction

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {

    val uiState = viewModel.getUiState()
    var floatingActionState by remember {
        mutableStateOf(FloatingActionState.COLLAPSED)
    }
    when (uiState.value) {
        is UiState.Success -> {
            val selectedRange = (uiState.value as UiState.Success<TimeRangeType>).data
            val transactionList: List<Transaction> = when (selectedRange) {
                TimeRangeType.DAY -> transactions.filter { it.type == CategoryType.INCOMES }
                TimeRangeType.WEEK -> transactions.filter { it.type == CategoryType.EXPENSES }
                TimeRangeType.MONTH -> transactions
            }
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                floatingActionButton = {
                    ActionButton(
                        floatingActionState
                    ) { floatingActionState = it }
                },
                floatingActionButtonPosition = FabPosition.Center
            ) {
                LazyColumn() {
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
                            text = "Заголовок 2",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        TimeRangePicker(selectedTimeRange = selectedRange, onTimeRangeClicked = { viewModel.getUiInfo(it) })
                    }

                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Заголовок 3",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }

                    items(transactionList.windowed(1, 1, true)) { list ->
                        list.forEach { item -> TransactionRow(item) }
                    }
                }
            }
        }
        is UiState.Error -> {}
        is UiState.Loading -> {}
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(MainViewModel())
}


