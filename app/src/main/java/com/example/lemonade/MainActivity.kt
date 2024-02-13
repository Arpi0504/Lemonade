package com.example.lemonade

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }
    val title = "Lemonade"


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.top),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds // Ensure the image fills the box bounds
                    )
                    Text(
                        text = title,
                        color = Color(red = 100, green = 124, blue = 9),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 120.dp, top = 20.dp),
                        textAlign = TextAlign.Center // Align the text in the center horizontally
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.height(80.dp)
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mybg),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .width(200.dp) // Set the desired width
                        .height(90.dp), // Set the desired height
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (currentStep) {
                        1 -> {
                            LemonTextAndImage(
                                textLabelResourceId = R.string.lemon_select,
                                drawableResourceId = R.drawable.tree, // Change this to the GIF resource ID
                                contentDescriptionResourceId = R.string.lemon_tree_content_description,
                                onImageClick = {
                                    currentStep = 2
                                    squeezeCount = (2..4).random()
                                }
                            )
                        }
                        2 -> {
                            LemonTextAndImage(
                                textLabelResourceId = R.string.lemon_squeeze,
                                drawableResourceId = R.drawable.lemonsqueeze, // Change this to the GIF resource ID
                                contentDescriptionResourceId = R.string.lemon_content_description,
                                onImageClick = {
                                    squeezeCount--
                                    if (squeezeCount == 0) {
                                        currentStep = 3
                                    }
                                }
                            )
                        }
                        3 -> {
                            LemonTextAndImage(
                                textLabelResourceId = R.string.lemon_drink,
                                drawableResourceId = R.drawable.lemonglass, // Change this to the GIF resource ID
                                contentDescriptionResourceId = R.string.lemonade_content_description,
                                onImageClick = {
                                    currentStep = 4
                                }
                            )
                        }
                        4 -> {
                            LemonTextAndImage(
                                textLabelResourceId = R.string.lemon_fill,
                                drawableResourceId = R.drawable.glassfill, // Change this to the GIF resource ID
                                contentDescriptionResourceId = R.string.lemonade_content_description,
                                onImageClick = {
                                    currentStep = 5
                                }
                            )
                        }
                        5 -> {
                            LemonTextAndImage(
                                textLabelResourceId = R.string.lemon_empty_glass,
                                drawableResourceId = R.drawable.emptyglass, // Change this to the GIF resource ID
                                contentDescriptionResourceId = R.string.empty_glass_content_description,
                                onImageClick = {
                                    currentStep = 1
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun LemonTextAndImage(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    onImageClick()
                    // Play the button click sound based on the current step
                    val soundResourceId = when (textLabelResourceId) {
                        R.string.lemon_select -> R.raw.treelemon
                        R.string.lemon_squeeze -> R.raw.squeeze
                        R.string.lemon_drink -> R.raw.liquid
                        R.string.lemon_fill -> R.raw.brokenglass
                        R.string.lemon_empty_glass -> R.raw.wow
                        else -> R.raw.cartoon// You can specify a default sound if needed
                    }
                    val mediaPlayer = MediaPlayer.create(context, soundResourceId)
                    mediaPlayer.start()
                },
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
            ) {
                Image(
                    painter = painterResource(drawableResourceId),
                    contentDescription = stringResource(contentDescriptionResourceId),
                    modifier = Modifier
                        .width(250.dp)
                        .height(300.dp)
                        .background(Color.Yellow)
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_vertical)))
            Text(
                text = stringResource(textLabelResourceId),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}



@Preview
@Composable
fun LemonPreview() {
    AppTheme {
        LemonadeApp()
    }
}
