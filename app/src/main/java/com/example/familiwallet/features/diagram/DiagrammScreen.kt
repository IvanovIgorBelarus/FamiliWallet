package com.example.familiwallet.features.diagram

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.familiwallet.core.data.UIModel
import com.example.familiwallet.features.diagram.data.CategorySumItem
import com.example.familiwallet.features.diagram.data.DrawItem
import com.example.familiwallet.features.diagram.data.OverviewItem
import com.example.familiwallet.ui.theme.diagramColor0
import com.example.familiwallet.ui.theme.diagramColor1
import com.example.familiwallet.ui.theme.diagramColor2
import com.example.familiwallet.ui.theme.diagramColor3
import com.example.familiwallet.ui.theme.diagramColor4
import com.example.familiwallet.ui.theme.diagramColor5
import com.example.familiwallet.ui.theme.diagramColor6
import com.example.familiwallet.ui.theme.textUnderLineColor
import kotlin.math.cos
import kotlin.math.sign
import kotlin.math.sin

@Composable
fun DiagramScreen(
    modifier: Modifier = Modifier,
    expansesList: List<UIModel.TransactionModel>,
    categoriesList: List<UIModel.CategoryModel>
) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.padding(50.dp)) {
        DrawDiagram(expansesList = DiagramMapper.mapDiagramItems(expansesList, categoriesList))
        Text(text = "150 BYN", fontSize = 35.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
    }
}

@Composable
private fun DrawDiagram(expansesList: List<CategorySumItem>) {
    //for diagram get only 7 values!!!
    val values = expansesList.sortedByDescending { it.sum }.subList(0, 6)


    var summary = 0.0 //sum of all categories on diagram
    var startAngle = -90f
    var position = 0

    values.forEach { summary += it.sum }

    val items = mutableListOf<DrawItem>()//diagram arc items

    values.forEach { value ->
        val onePart = 360f / summary.toFloat()
        val sweepAngle = value.sum.toFloat() * onePart
        items.add(
            DrawItem(
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                value = value.sum,
                color = colorList[position]
            )
        )
        startAngle += sweepAngle
        position++
    }
    DrawArc(
        drawItems = items
    )
}

@Composable
private fun DrawArc(
    modifier: Modifier = Modifier,
    drawItems: List<DrawItem>
) {
    val offsetOverviewList = mutableListOf<OverviewItem>()
    Canvas(modifier = modifier) {
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
                style = Stroke(50f)
            )
            //draw margin arcs
            drawArc(
                color = Color.White,
                startAngle = drawItem.startAngle,
                sweepAngle = 1f,
                useCenter = false,
                topLeft = Offset(x = centerX, y = centerY),
                size = Size(drawItem.radius * 2, drawItem.radius * 2),
                style = Stroke(100f)
            )

            //calculate Offset
            angle += drawItem.sweepAngle
            val rad = ((angle - drawItem.sweepAngle / 2) * 2 * Math.PI / 360).toFloat()

            // coordinates of circle center
            val x = radius * cos(rad) + size.width / 2
            val y = radius * sin(rad) + size.height / 2

            // coordinates for drawing lines
            val endLineX = x + sign(cos(rad)) * 150
            val endLineOffset = Offset(x = endLineX, y = y)

            offsetOverviewList.add(
                OverviewItem(
                    text = drawItem.value.toString(),
                    offset = endLineOffset,
                    drawItem.color
                )
            )

            //draw overviews lines
//            drawCircle(
//                color = drawItem.color,
//                radius = 10f,
//                center = Offset(x = x, y = y)
//            )

            drawLine(
                color = textUnderLineColor,
                strokeWidth = 2f,
                start = Offset(x = x, y = y),
                end = endLineOffset
            )
        }
        drawOverviews(offsetOverviewList, this)
    }
}

private fun drawOverviews(
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

        scope.drawIntoCanvas {
            it.nativeCanvas.drawText(
                overview.text,
                overview.offset.x,
                overview.offset.y - 5f,
                paint
            )
        }
    }
}

private val colorList = listOf(
    diagramColor0,
    diagramColor1,
    diagramColor2,
    diagramColor3,
    diagramColor4,
    diagramColor5,
    diagramColor6
)

