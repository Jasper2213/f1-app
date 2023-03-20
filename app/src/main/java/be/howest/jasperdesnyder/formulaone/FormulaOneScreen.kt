package be.howest.jasperdesnyder.formulaone

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.howest.jasperdesnyder.formulaone.repositories.NavItemsRepo
import be.howest.jasperdesnyder.formulaone.ui.*
import be.howest.jasperdesnyder.formulaone.ui.screens.CalendarScreen
import be.howest.jasperdesnyder.formulaone.ui.screens.MenuScreen
import be.howest.jasperdesnyder.formulaone.ui.screens.PredictionScreen
import be.howest.jasperdesnyder.formulaone.ui.screens.RaceDetailScreen

enum class FormulaOneScreen(@StringRes val title: Int) {
    Start(title = R.string.next_race),
    Calendar(title = R.string.calendar),
    RaceDetail(title = R.string.calendar),
    DriverStandings(title = R.string.driver_standings),
    ConstructorStandings(title = R.string.constructor_standings),
    Menu(title = R.string.menu),
    Predictions(title = R.string.predictions)
}

@Composable
fun FormulaOneApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = FormulaOneScreen.valueOf(
        backStackEntry?.destination?.route ?: FormulaOneScreen.Start.name
    )

    val viewModel: FormulaOneViewModel = viewModel()

    Scaffold(
        topBar = {
            FormulaOneTopBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            BottomNavigation(
                modifier = Modifier.height(60.dp)
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
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = FormulaOneScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = FormulaOneScreen.Start.name) {
                StartScreen()
            }

            composable(route = FormulaOneScreen.Calendar.name) {
                CalendarScreen(
                    viewModel = viewModel,
                    onRaceClicked = {
                        navController.navigate(FormulaOneScreen.RaceDetail.name)
                    }
                )
            }

            composable(route = FormulaOneScreen.RaceDetail.name) {
                RaceDetailScreen(
                    selectedRace = viewModel.getSelectedRace()
                )
            }

            composable(route = FormulaOneScreen.DriverStandings.name) {
                DriverStandingsScreen(
                    onConstructorStandingsClicked = {
                        navController.navigate(FormulaOneScreen.ConstructorStandings.name)
                    }
                )
            }

            composable(route = FormulaOneScreen.ConstructorStandings.name) {
                ConstructorStandingsScreen(
                    onDriverStandingsClicked = {
                        navController.navigate(FormulaOneScreen.DriverStandings.name)
                    }
                )
            }

            composable(route = FormulaOneScreen.Menu.name) {
                MenuScreen()
            }

            composable(route = FormulaOneScreen.Predictions.name) {
                PredictionScreen()
            }
        }
    }
}

@Composable
fun FormulaOneTopBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(currentScreenTitle),
                fontSize = 28.sp
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}