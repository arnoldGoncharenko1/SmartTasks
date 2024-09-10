package com.arnoldgoncharenko.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.houseravenstudios.ui.R

val AmsiPro = FontFamily(
    Font(R.font.amsiproregular),
    Font(R.font.amsiprobold, FontWeight.Bold)
)

val Typography = Typography(
    displayMedium = TextStyle(
        fontFamily = AmsiPro,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp),
    labelSmall = TextStyle(
        fontFamily = AmsiPro,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    ),
)
