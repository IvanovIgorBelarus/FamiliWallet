import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.familiwallet.components.ActionButton
import com.example.familiwallet.components.BottomBar
import com.example.familiwallet.navigation.MainScreenNavigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalAnimationApi
@Composable
fun MainScreen(
    navController: NavHostController = rememberAnimatedNavController()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navigation = navController) },
        floatingActionButton = {
            ActionButton(navigation = navController)
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    ) {
        MainScreenNavigation(navigation = navController)
    }
}



