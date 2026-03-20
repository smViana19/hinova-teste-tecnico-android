package br.com.smvtech.testetecnico.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default, // Replace with Inter
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp, // 1.75rem
        lineHeight = 36.sp,
        letterSpacing = (-0.02).em
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default, // Replace with Inter
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp, // 1.125rem
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default, // Replace with Inter
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp, // 0.875rem
        lineHeight = 20.sp,
        letterSpacing = 0.sp
    ),
    labelMedium = TextStyle( // Used for category tags or secondary metadata
        fontFamily = FontFamily.Default, // Replace with Inter
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp, // 0.75rem
        lineHeight = 16.sp,
        letterSpacing = 0.05.em // +0.05em tracking
    )
)