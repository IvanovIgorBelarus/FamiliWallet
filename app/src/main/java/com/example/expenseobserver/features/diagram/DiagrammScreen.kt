package com.example.expenseobserver.features.diagram

import android.content.res.Resources
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.expenseobserver.core.common.CategoryType
import com.example.expenseobserver.core.data.UIModel
import com.example.expenseobserver.features.diagram.data.CategorySumItem
import com.example.expenseobserver.features.diagram.data.DrawItem
import com.example.expenseobserver.features.diagram.data.OverviewItem
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin

@Composable
fun DiagramScreen(
    modifier: Modifier = Modifier,
    transactionsList: List<UIModel.TransactionModel>,
    categoriesList: List<UIModel.CategoryModel>
) {
    if (transactionsList.isNotEmpty() && categoriesList.isNotEmpty()) {
        val expensesSumList = DiagramMapper.mapDiagramItems(transactionsList, categoriesList)
        val expensesSum = floor(DiagramMapper.getSum(expensesSumList) * 100) / 100

        val incomesList = transactionsList.filter { it.type == CategoryType.INCOME.type }
        val incomesSum = floor(incomesList.sumOf { it.value?:0.0 } * 100) / 100
        Box(contentAlignment = Alignment.Center, modifier = modifier) {
            DrawDiagram(modifier, expansesList = expensesSumList, summary = expensesSum)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "+$incomesSum BYN", fontSize = 24.sp, fontWeight = FontWeight.W500, color = Color.DarkGray)
                Text(text = "-$expensesSum BYN", fontSize = 20.sp, fontWeight = FontWeight.W400, color = Color.DarkGray)
            }
        }
    } else {
        Box(contentAlignment = Alignment.Center, modifier = modifier) {
            Text(text = "у вас нет данных", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
        }
    }
}

@Composable
private fun DrawDiagram(
    modifier: Modifier = Modifier,
    expansesList: List<CategorySumItem>,
    summary: Double
) {
    //for diagram get only 7 values!!!
    DrawArc(
        modifier = modifier,
        drawItems = DiagramMapper.mapDrawItems(expansesList, summary),
        sum = summary
    )
}

@Composable
private fun DrawArc(
    modifier: Modifier = Modifier,
    drawItems: List<DrawItem>,
    sum: Double
) {
    val resources = LocalContext.current.resources
    val offsetOverviewList = mutableListOf<OverviewItem>()
    Canvas(modifier = Modifier.fillMaxSize()) {
        //calculate center of diagram circle
        val centerX = (size.width / 2 - drawItems[0].radius)
        val centerY = (size.height / 2 - drawItems[0].radius)


        var angle = -90f
        val radius = drawItems[0].radius + 35f
        drawItems.forEach { drawItem ->
            //draw arc for each value
            drawArc(
                color = drawItem.color,
                startAngle = drawItem.startAngle,
                sweepAngle = drawItem.sweepAngle,
                useCenter = false,
                topLeft = Offset(x = centerX, y = centerY),
                size = Size(drawItem.radius * 2, drawItem.radius * 2),
                style = Stroke(75f)
            )
            //draw margin arcs
            drawArc(
                color = Color.White,
                startAngle = drawItem.startAngle,
                sweepAngle = 1f,
                useCenter = false,
                topLeft = Offset(x = centerX, y = centerY),
                size = Size(drawItem.radius * 2, drawItem.radius * 2),
                style = Stroke(75f)
            )

            //calculate Offset
            angle += drawItem.sweepAngle
            val rad = ((angle - drawItem.sweepAngle / 2) * 2 * Math.PI / 360).toFloat()

            // coordinates of start line
            val x = (radius + 90) * cos(rad) + size.width / 2
            val y = (radius + 90) * sin(rad) + size.height / 2
            val iconOffset = Offset(x-36, y-36)

            // coordinates for drawing lines
            val endLineX = (radius + 90) * cos(rad) + size.width / 2
            val endLineY = (radius + 90) * sin(rad) + size.height / 2
            val textOffset = Offset(endLineX, endLineY-40)

            val drawItemValue = floor(drawItem.value / sum * 10000) / 100
            offsetOverviewList.add(
                OverviewItem(
                    text = "$drawItemValue %",
                    textOffset = textOffset,
                    iconOffset = iconOffset,
                    color = drawItem.color,
                    icon = drawItem.icon
                )
            )
        }
        drawOverviews(resources, offsetOverviewList, this)
    }
}

private fun drawOverviews(
    resources: Resources,
    list: List<OverviewItem>,
    scope: DrawScope
) {
    list.forEach { overview ->
        val colorX = android.graphics.Color.argb(
            overview.color.toArgb().alpha,
            overview.color.toArgb().red,
            overview.color.toArgb().green,
            overview.color.toArgb().blue
        )
        val paint = Paint().apply {
            textAlign = Paint.Align.CENTER
            textSize = 40f
            color = colorX
        }

        val imgPaint = androidx.compose.ui.graphics.Paint().apply {
            colorFilter = ColorFilter.tint(overview.color)
        }
        val drawable = resources.getDrawable(overview.icon).toBitmap(72, 72)
        scope.drawIntoCanvas {
            it.nativeCanvas.drawText(
                overview.text,
                overview.textOffset.x,
                overview.textOffset.y,
                paint
            )
            it.drawImage(
                drawable.asImageBitmap(),
                overview.iconOffset,
                imgPaint
            )
        }
    }
}


