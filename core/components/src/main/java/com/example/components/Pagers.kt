package com.example.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.data.theme.diagramColor1
import com.example.data.theme.mainColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerWithIndicator(
    state: PagerState,
    pageCount: Int,
    pageContent: @Composable (page: Int) -> Unit
) {
    HorizontalPager(
        state = state,
        pageCount = pageCount,
        pageContent = pageContent
    )
    Spacer(modifier = Modifier.size(12.dp))
    Row(
        Modifier
            .height(25.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color = if (state.currentPage == iteration) mainColor else diagramColor1
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(12.dp)
            )
        }
    }
}