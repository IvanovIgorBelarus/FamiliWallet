import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.familiwallet.components.TimeRangePicker
import com.example.familiwallet.components.TransactionRow
import com.example.familiwallet.components.transactions
import com.example.familiwallet.core.common.TimeRangeType
import com.example.familiwallet.core.ui.UiState
import com.example.familiwallet.features.main.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val scrollState = rememberScrollState()

    val uiState = viewModel.getUiState()

    when (uiState.value) {
        is UiState.Success -> {
            val selectedRange = (uiState.value as UiState.Success<TimeRangeType>).data
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        CategoryBlock()
                    }
                    item {
                        TimeRangePicker(selectedTimeRange = selectedRange, onTimeRangeClicked = { viewModel.getUiInfo(it) })
                    }

                    items(transactions.windowed(1, 1, true)) { items ->
                        items.forEach { item ->
                            TransactionRow(transaction = item)
                        }
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


