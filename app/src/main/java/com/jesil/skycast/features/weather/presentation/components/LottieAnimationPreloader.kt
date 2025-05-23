package com.jesil.skycast.features.weather.presentation.components

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun  LottieAnimationPreloader(
    modifier: Modifier = Modifier,
    @RawRes lottieId : Int
) {
    val preLoaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            lottieId
        )
    )

    LottieAnimation(
        composition = preLoaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier
    )
}