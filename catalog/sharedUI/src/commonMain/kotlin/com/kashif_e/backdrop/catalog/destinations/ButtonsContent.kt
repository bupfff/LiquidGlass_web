package com.kashif_e.backdrop.catalog.destinations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kashif_e.backdrop.catalog.BackdropDemoScaffold
import com.kashif_e.backdrop.catalog.components.LiquidButton

@Composable
fun ButtonsContent(onBack: () -> Unit = {}) {
    BackdropDemoScaffold(onBack = onBack) { backdrop ->
        var lastAction by rememberSaveable {
            mutableStateOf("Tap a button to see it respond.")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16f.dp)
        ) {
            LiquidButton(
                { lastAction = "Transparent button tapped." },
                backdrop
            ) {
                BasicText(
                    "Transparent Liquid Button",
                    style = TextStyle(Color.Black, 15f.sp)
                )
            }
            LiquidButton(
                { lastAction = "Surface button tapped." },
                backdrop,
                surfaceColor = Color.White.copy(0.3f)
            ) {
                BasicText(
                    "Surface Liquid Button",
                    style = TextStyle(Color.Black, 15f.sp)
                )
            }
            LiquidButton(
                { lastAction = "Blue tinted button tapped." },
                backdrop,
                tint = Color(0xFF0088FF)
            ) {
                BasicText(
                    "Tinted Liquid Button",
                    style = TextStyle(Color.White, 15f.sp)
                )
            }
            LiquidButton(
                { lastAction = "Orange tinted button tapped." },
                backdrop,
                tint = Color(0xFFFF8D28)
            ) {
                BasicText(
                    "Tinted Liquid Button",
                    style = TextStyle(Color.White, 15f.sp)
                )
            }
            BasicText(
                lastAction,
                Modifier.fillMaxWidth().padding(top = 8.dp),
                style = TextStyle(Color.White.copy(alpha = 0.8f), 14f.sp)
            )
        }
    }
}
