package br.com.fiap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.fiap.screens.LoginScreen
import br.com.fiap.screens.MenuScreen
import br.com.fiap.screens.PedidosScreen
import br.com.fiap.screens.PerfilScreen
import br.com.fiap.ui.theme.NavegacaoTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavegacaoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "login",
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                tween(2000)
                                ) + fadeOut(
                                    animationSpec = tween(2000)
                                )
                        },
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                                tween(2000)
                            )
                        }
                    ) {
                        composable(route = "login") {
                            LoginScreen(navController = navController)
                        }
                        composable(route = "menu") {
                            MenuScreen(navController = navController)
                        }
                        composable(
                            route = "perfil/{nome}/{idade}",
                            arguments = listOf(navArgument(name = "nome") {
                                type = NavType.StringType
                            }, navArgument(name = "idade") {
                                type = NavType.IntType
                            })
                        ) {
                            val nome = it.arguments?.getString("nome")
                            val idade = it.arguments?.getInt("idade")
                            PerfilScreen(
                                navController = navController, nome = nome!!, idade = idade!!
                            )
                        }
                        composable(
                            route = "pedidos?numero={numero}",
                            arguments = listOf(navArgument(name = "numero") {
                                defaultValue = ""
                            })
                        ) {
                            PedidosScreen(
                                navController = navController,
                                numero = it.arguments?.getString("numero")!!
                            )
                        }
                    }
                }
            }
        }
    }
}