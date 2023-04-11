package be.howest.jasperdesnyder.formulaone.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.howest.jasperdesnyder.formulaone.R
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneApiUiState
import be.howest.jasperdesnyder.formulaone.ui.FormulaOneViewModel

@Composable
fun StartupScreen(
    formulaOneApiUiState: FormulaOneApiUiState,
    viewModel: FormulaOneViewModel,
    onStartupComplete: () -> Unit,
    onRetryClicked: () -> Unit,
    onEmailClicked: () -> Unit
) {
    viewModel.getSeasonInformation()

    when (formulaOneApiUiState) {
        is FormulaOneApiUiState.Loading -> InitialLoadingScreen()
        is FormulaOneApiUiState.Error -> InitialErrorScreen(onRetryClicked, onEmailClicked)
        is FormulaOneApiUiState.Success -> onStartupComplete()
    }
}

@Composable
fun InitialLoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        val scale = remember { Animatable(0f) }

        // AnimationEffect
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    })
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.scale(scale.value),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colors.primary,
                    blendMode = BlendMode.SrcIn
                )
            )

            Text(
                text = "Loading data...",
                fontSize = 24.sp
            )

            CircularProgressIndicator()
        }
    }
}

@Composable
fun InitialErrorScreen(
    onRetryClicked: () -> Unit,
    onEmailClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val linkColor = if (isSystemInDarkTheme()) Color(0xFF87CEEB) else Color.Blue

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colors.primary,
                blendMode = BlendMode.SrcIn
            )
        )


        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Error loading data",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Please check your internet connection and try again",
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                onClick = onRetryClicked
            ) {
                Text(
                    text = "Retry",
                    fontSize = 18.sp
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "If the problem persists, please contact the developer at",
                textAlign = TextAlign.Center,
            )
            Text(
                text = "jasper.desnyder@student.howest.be",
                modifier = Modifier.clickable(
                    onClick = onEmailClicked
                ),
                textAlign = TextAlign.Center,
                color = linkColor,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}
