import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.familiwallet.core.common.CategoryType
import com.example.familiwallet.ui.theme.expensesBackgroundColor
import com.example.familiwallet.ui.theme.expensesColor
import com.example.familiwallet.ui.theme.incomesBackgroundColor
import com.example.familiwallet.ui.theme.incomesColor

@Composable
fun CategoryBlock(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        InfoBox(type = CategoryType.INCOMES, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(8.dp))
        InfoBox(type = CategoryType.EXPENSES, modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CategoryBlockPreview() {
    CategoryBlock()
}

@Composable
fun InfoBox(
    modifier: Modifier = Modifier,
    type: CategoryType = CategoryType.INCOMES
) {
    Box(
        modifier = modifier
            .background(
                color = if (type == CategoryType.INCOMES) incomesBackgroundColor else expensesBackgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .height(150.dp)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (type == CategoryType.INCOMES) "Доходы" else "Расходы",
            color = if (type == CategoryType.INCOMES) incomesColor else expensesColor,
            modifier = Modifier.padding(4.dp),
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun InfoBoxPreview() {
    InfoBox(
        modifier = Modifier.fillMaxWidth(),
        type = CategoryType.INCOMES
    )
}