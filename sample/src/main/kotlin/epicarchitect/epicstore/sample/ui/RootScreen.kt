package epicarchitect.epicstore.sample.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import epicarchitect.epicstore.navigation.compose.EpicHavHost
import epicarchitect.epicstore.navigation.compose.LocalNavController
import epicarchitect.epicstore.navigation.compose.epicStoreComposable

@Composable
fun RootScreen() {
    EpicHavHost(startDestination = "todoList") {
        epicStoreComposable("todoList") {
            val navController = LocalNavController.current
            TodoListScreen(
                onAddButtonClick = {
                    navController.navigate("todoCreation")
                },
                onTaskClick = {
                    navController.navigate("todoDetails?id=$it")
                }
            )
        }

        epicStoreComposable(
            route = "todoDetails?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val navController = LocalNavController.current
            val todoId = it.arguments!!.getInt("id")
            TodoDetailsScreen(
                todoId = todoId,
                onEditClick = {
                    navController.navigate("todoUpdating?id=$todoId")
                }
            )
        }

        epicStoreComposable("todoCreation") {
            val navController = LocalNavController.current
            TodoCreationScreen(
                onFinished = navController::popBackStack
            )
        }

        epicStoreComposable(
            route = "todoUpdating?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val navController = LocalNavController.current
            TodoUpdatingScreen(
                todoId = it.arguments!!.getInt("id"),
                onFinished = navController::popBackStack
            )
        }
    }
}