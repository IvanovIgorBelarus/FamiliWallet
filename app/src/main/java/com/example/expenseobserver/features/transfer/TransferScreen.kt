package com.example.expenseobserver.features.transfer

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.expenseobserver.components.WalletItems
import com.example.expenseobserver.components.WalletTemplate
import com.example.expenseobserver.core.common.ShowScreen
import com.example.expenseobserver.features.transfer.data.TransferScreenViewState
import com.example.expenseobserver.navigation.Screen
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun TransferScreen(
    modifier: Modifier = Modifier,
    navigation: NavHostController,
    transferViewModel: TransferViewModel = hiltViewModel()
) {

    ShowScreen(
        viewModel = transferViewModel,
        onSuccess = {
            UI(
                modifier = modifier,
                viewState = it as TransferScreenViewState,
                navigation = navigation,
                transferViewModel = transferViewModel
            )
        }
    )

    LaunchedEffect(Unit) {
        val id = Screen.TransferScreen.args?.getString("ID", "empty")
        transferViewModel.getTransferInfo(id)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun UI(
    modifier: Modifier,
    viewState: TransferScreenViewState,
    navigation: NavHostController,
    transferViewModel: TransferViewModel
) {

    val selectedWallet = remember { mutableStateOf(viewState.walletsTo.getOrNull(0)) }

    // Создание состояния для индекса текущего элемента
    val currentIndex = remember { mutableStateOf(0) }

    // Создание состояния для позиции скролла
    val scrollState = rememberLazyListState()

    Scaffold(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                WalletTemplate(viewState.walletFrom)
            }
            item {
                LazyRow(
//                    state = scrollState,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)){
                    items(viewState.walletsTo.size){index->
                        val currentItem = viewState.walletsTo[index]
                        WalletTemplate(currentItem)
                    }
                }
            }
        }
//        LaunchedEffect(scrollState) {
//            snapshotFlow { scrollState.firstVisibleItemIndex }
//                .distinctUntilChanged()
//                .collect { index ->
//                    // Определение следующего элемента
//                    val nextIndex = index + 1
//
//                    // Проверка, что следующий элемент существует
//                    if (nextIndex < viewState.walletsTo.size) {
//                        val currentItemPos = scrollState.layoutInfo.visibleItemsInfo[index].offset
//                        val nextItemPos = scrollState.layoutInfo.visibleItemsInfo[nextIndex].offset
//
//                        // Проверка, что текущий элемент находится в середине экрана
//                        if (scrollState.layoutInfo.viewportEndOffset >= nextItemPos) {
//                            // Анимация скролла до следующего элемента
//                            scrollState.scrollToItem(nextIndex)
//                            currentIndex.value = nextIndex
//                        }
//                    }
//                }
//        }
    }
}
