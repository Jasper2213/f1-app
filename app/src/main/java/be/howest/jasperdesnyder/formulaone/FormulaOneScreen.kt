package be.howest.jasperdesnyder.formulaone

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.howest.jasperdesnyder.formulaone.model.FormulaOneUiState
import be.howest.jasperdesnyder.formulaone.repositories.NavItemsRepo
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneApiUiState
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneViewModel
import be.howest.jasperdesnyder.formulaone.ui.screens.*
import java.util.*

enum class FormulaOneScreen(@StringRes val title: Int) {
    Startup(title = R.string.startup),
    Start(title = R.string.next_race),
    Calendar(title = R.string.calendar),
    RaceDetail(title = R.string.calendar),
    DriverStandings(title = R.string.driver_standings),
    ConstructorStandings(title = R.string.constructor_standings),
    Menu(title = R.string.menu),
    Predictions(title = R.string.predictions)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormulaOneApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FormulaOneScreen.valueOf(
        backStackEntry?.destination?.route ?: FormulaOneScreen.Start.name
    )

    val viewModel: FormulaOneViewModel = viewModel()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            if (currentScreen != FormulaOneScreen.Startup) {
                FormulaOneTopBar(
                    currentScreenTitle = currentScreen.title,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                    viewModel = viewModel,
                    uiState = uiState
                )
            }
        },
        bottomBar = {
            if (currentScreen != FormulaOneScreen.Startup) {
                FormulaOneBottomBar(
                    currentScreen = currentScreen,
                    navController = navController
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FormulaOneScreen.Startup.name,
            modifier = modifier
                .padding(innerPadding)
                .background(MaterialTheme.colors.background)
        ) {
            composable(route = FormulaOneScreen.Startup.name) {
                StartupScreen(
                    formulaOneApiUiState = viewModel.formulaOneApiUiState,
                    viewModel = viewModel,
                    onStartupComplete = {
                        if (currentScreen == FormulaOneScreen.Startup)
                            navController.navigate(FormulaOneScreen.Start.name)
                    },
                    onRetryClicked = {
                        viewModel.formulaOneApiUiState = FormulaOneApiUiState.Loading
                        navController.navigate(FormulaOneScreen.Startup.name)
                    },
                    onEmailClicked = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("jasper.desnyder@student.howest.be"))
                        }

                        launcher.launch(Intent.createChooser(intent, "Send email"))
                    }
                )
            }

            composable(route = FormulaOneScreen.Start.name) {
                StartScreen(
                    formulaOneApiUiState = viewModel.formulaOneApiUiState
                )
            }

            composable(route = FormulaOneScreen.Calendar.name) {
                CalendarScreen(
                    viewModel = viewModel,
                    formulaOneApiUiState = viewModel.formulaOneApiUiState,
                    onRaceClicked = {
                        navController.navigate(FormulaOneScreen.RaceDetail.name)
                    }
                )
            }

            composable(route = FormulaOneScreen.RaceDetail.name) {
                RaceDetailScreen(
                    selectedRace = uiState.selectedRace!!
                )
            }

            composable(route = FormulaOneScreen.DriverStandings.name) {
                DriverStandingsScreen(
                    formulaOneApiUiState = viewModel.formulaOneApiUiState,
                    onConstructorStandingsClicked = {
                        navController.navigate(FormulaOneScreen.ConstructorStandings.name)
                    }
                )
            }

            composable(route = FormulaOneScreen.ConstructorStandings.name) {
                ConstructorStandingsScreen(
                    formulaOneApiUiState = viewModel.formulaOneApiUiState,
                    onDriverStandingsClicked = {
                        navController.navigate(FormulaOneScreen.DriverStandings.name)
                    }
                )
            }

            composable(route = FormulaOneScreen.Menu.name) {
                MenuScreen(
                    onNextRaceClicked = {
                        navController.navigate(FormulaOneScreen.Start.name)
                    },
                    onDriversStandingsClicked = {
                        navController.navigate(FormulaOneScreen.DriverStandings.name)
                    },
                    onConstructorsStandingsClicked = {
                        navController.navigate(FormulaOneScreen.ConstructorStandings.name)
                    },
                    onCalendarClicked = {
                        navController.navigate(FormulaOneScreen.Calendar.name)
                    },
                    onPredictionsClicked = {
                        navController.navigate(FormulaOneScreen.Predictions.name)
                    },
                    onWebsiteClicked = {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.formula1.com"))
                        launcher.launch(intent)
                    }
                )
            }

            composable(route = FormulaOneScreen.Predictions.name) {
                PredictionScreen(
                    formulaOneApiUiState = viewModel.formulaOneApiUiState,
                    uiState = uiState,
                    onSubmitClicked = {
                        // TODO: Improve this
                        navController.navigate(FormulaOneScreen.Predictions.name)
                    },
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun FormulaOneTopBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FormulaOneViewModel,
    uiState: FormulaOneUiState
) {
    var expanded by remember { mutableStateOf(false) }
    var isChecked by remember { mutableStateOf(uiState.notificationsEnabled) }

    TopAppBar(
        title = {
            Text(
                text = stringResource(currentScreenTitle),
                fontSize = 28.sp
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        },
        modifier = modifier.background(MaterialTheme.colors.primary),
        actions = {
            if (currentScreenTitle == FormulaOneScreen.Calendar.title) {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "More options",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.padding(8.dp)
                ) {
                    DropdownMenuItem(onClick = {}) {
                        Row {
                            Text(
                                text = "Get notified 30 minutes before race starts?",
                                fontSize = 16.sp
                            )
                            Switch(
                                checked = isChecked,
                                onCheckedChange = {
                                    isChecked = it
                                    viewModel.updateNotificationsEnabled(it)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun FormulaOneBottomBar(
    currentScreen: FormulaOneScreen,
    navController: NavHostController
) {
    BottomNavigation(
        modifier = Modifier
            .height(60.dp)
            .background(MaterialTheme.colors.background)
    ) {
        NavItemsRepo.items.forEach { navItem ->
            BottomNavigationItem(
                selected = currentScreen == navItem.route,
                onClick = {
                    navController.navigate(navItem.route.name)
                },
                icon = {
                    Icon(
                        navItem.icon,
                        contentDescription = navItem.label,
                        modifier = Modifier.size(40.dp)
                    )
                }
            )
        }
    }
}
