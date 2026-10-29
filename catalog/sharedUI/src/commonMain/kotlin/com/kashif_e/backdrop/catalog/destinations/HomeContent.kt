package com.kashif_e.backdrop.catalog.destinations

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kashif_e.backdrop.catalog.CatalogDestination

private data class DemoCardData(
    val title: String,
    val description: String,
    val destination: CatalogDestination,
    val number: String
)

@Composable
fun HomeContent(onNavigate: (CatalogDestination) -> Unit) {
    val componentDemos = listOf(
        DemoCardData("All components", "A hands-on collection of buttons, toggles, sliders and tabs.", CatalogDestination.AllComponents, "01"),
        DemoCardData("Liquid buttons", "Explore clear, tinted and surface treatments.", CatalogDestination.Buttons, "02"),
        DemoCardData("Toggle", "A tactile switch with a soft, refractive thumb.", CatalogDestination.Toggle, "03"),
        DemoCardData("Slider", "Scrub the glass control and feel it respond.", CatalogDestination.Slider, "04"),
        DemoCardData("Bottom tabs", "A responsive liquid selection indicator.", CatalogDestination.BottomTabs, "05"),
        DemoCardData("Dialog", "Layer a glass surface over a live backdrop.", CatalogDestination.Dialog, "06")
    )
    val systemDemos = listOf(
        DemoCardData("Control Center", "A familiar system panel with interactive glass.", CatalogDestination.ControlCenter, "07"),
        DemoCardData("Lock Screen", "A full-screen lock experience with SDF effects.", CatalogDestination.LockScreen, "08"),
        DemoCardData("Magnifier", "See the backdrop refracted through a moving lens.", CatalogDestination.Magnifier, "09")
    )
    val effectDemos = listOf(
        DemoCardData("Glass playground", "Adjust blur, refraction, shape and motion.", CatalogDestination.GlassPlayground, "10"),
        DemoCardData("Adaptive luminance", "Watch the glass respond to light and contrast.", CatalogDestination.AdaptiveLuminanceGlass, "11"),
        DemoCardData("Progressive blur", "Blend a gradual blur into the scene.", CatalogDestination.ProgressiveBlur, "12"),
        DemoCardData("Scroll container", "Explore glass over scrolling content.", CatalogDestination.ScrollContainer, "13"),
        DemoCardData("Lazy scroll", "See glass effects over a longer, lazy list.", CatalogDestination.LazyScrollContainer, "14"),
        DemoCardData("Shader playground", "A colorful backdrop for exploring the rendering.", CatalogDestination.Playground, "15")
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF071421),
                        Color(0xFF101B2E),
                        Color(0xFF17162D)
                    )
                )
            )
    ) {
        Box(
            Modifier
                .align(Alignment.TopEnd)
                .padding(top = 80.dp, end = 60.dp)
                .size(280.dp)
                .clip(CircleShape)
                .background(Color(0xFF3388FF).copy(alpha = 0.12f))
        )
        Box(
            Modifier
                .align(Alignment.BottomStart)
                .padding(start = 30.dp, bottom = 40.dp)
                .size(240.dp)
                .clip(CircleShape)
                .background(Color(0xFFB151E8).copy(alpha = 0.12f))
        )

        BoxWithConstraints(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            val wideLayout = maxWidth >= 760.dp
            val cardColumns = when {
                maxWidth >= 900.dp -> 3
                maxWidth >= 560.dp -> 2
                else -> 1
            }
            Column(
                Modifier
                    .widthIn(max = 1080.dp)
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 24.dp, bottom = 56.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        BasicText(
                            "BACKDROP",
                            style = TextStyle(Color.White, 15.sp, FontWeight.Bold)
                        )
                        BasicText(
                            "LIQUID GLASS CATALOG",
                            style = TextStyle(Color.White.copy(alpha = 0.58f), 9.sp, FontWeight.SemiBold)
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    BasicText(
                        "15 INTERACTIVE DEMOS",
                        Modifier
                            .clip(RoundedCornerShape(100.dp))
                            .background(Color.White.copy(alpha = 0.1f))
                            .padding(horizontal = 13.dp, vertical = 9.dp),
                        style = TextStyle(Color.White.copy(alpha = 0.8f), 9.sp, FontWeight.Bold)
                    )
                }

                HeroPanel(
                    wideLayout = wideLayout,
                    onOpenPhone = { onNavigate(CatalogDestination.PhoneDemo) },
                    onExplore = { onNavigate(CatalogDestination.AllComponents) }
                )

                DemoSection(
                    eyebrow = "THE BUILDING BLOCKS",
                    title = "Components",
                    description = "Small, tactile controls built from the same glass effect.",
                    cards = componentDemos,
                    columns = cardColumns,
                    onNavigate = onNavigate
                )
                DemoSection(
                    eyebrow = "FAMILIAR SURFACES",
                    title = "System experiences",
                    description = "Glass in the places people already know.",
                    cards = systemDemos,
                    columns = cardColumns,
                    onNavigate = onNavigate
                )
                DemoSection(
                    eyebrow = "UNDER THE SURFACE",
                    title = "Effects & experiments",
                    description = "Tune, combine and inspect the rendering effects.",
                    cards = effectDemos,
                    columns = cardColumns,
                    onNavigate = onNavigate
                )
                BasicText(
                    "Built with Backdrop • Compose Multiplatform",
                    Modifier.align(Alignment.CenterHorizontally),
                    style = TextStyle(Color.White.copy(alpha = 0.48f), 11.sp)
                )
            }
        }
    }
}

@Composable
private fun HeroPanel(
    wideLayout: Boolean,
    onOpenPhone: () -> Unit,
    onExplore: () -> Unit
) {
    val shape = RoundedCornerShape(32.dp)
    Row(
        Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF174A75).copy(alpha = 0.95f),
                        Color(0xFF43316D).copy(alpha = 0.96f),
                        Color(0xFF232644).copy(alpha = 0.96f)
                    )
                )
            )
            .border(1.dp, Color.White.copy(alpha = 0.16f), shape)
            .padding(if (wideLayout) 34.dp else 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            BasicText(
                "A PLAYGROUND FOR LIGHT",
                style = TextStyle(Color(0xFFB9D9FF), 10.sp, FontWeight.Bold)
            )
            BasicText(
                "Liquid glass,\nin its element.",
                style = TextStyle(Color.White, if (wideLayout) 40.sp else 32.sp, FontWeight.SemiBold)
            )
            BasicText(
                "A hands-on showcase of blur, refraction, vibrancy and the components they make possible.",
                Modifier.widthIn(max = 520.dp),
                style = TextStyle(Color.White.copy(alpha = 0.78f), 14.sp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionPill("Open phone demo", onClick = onOpenPhone, primary = true)
                if (wideLayout) {
                    ActionPill("Browse components", onClick = onExplore)
                }
            }
        }
        if (wideLayout) {
            Column(
                Modifier
                    .padding(start = 22.dp)
                    .size(180.dp)
                    .clip(RoundedCornerShape(34.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF8FD9FF).copy(alpha = 0.38f),
                                Color(0xFF8467FF).copy(alpha = 0.24f),
                                Color(0xFFFF8BC2).copy(alpha = 0.32f)
                            )
                        )
                    )
                    .border(1.dp, Color.White.copy(alpha = 0.32f), RoundedCornerShape(34.dp))
                    .padding(18.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                BasicText("GLASS / 01", style = TextStyle(Color.White.copy(alpha = 0.75f), 10.sp, FontWeight.Bold))
                BasicText("Blur\nLens\nVibrancy", style = TextStyle(Color.White, 22.sp, FontWeight.Medium))
                BasicText("Move through the demos", style = TextStyle(Color.White.copy(alpha = 0.72f), 10.sp))
            }
        }
    }
}

@Composable
private fun DemoSection(
    eyebrow: String,
    title: String,
    description: String,
    cards: List<DemoCardData>,
    columns: Int,
    onNavigate: (CatalogDestination) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            BasicText(eyebrow, style = TextStyle(Color(0xFF91C9FF), 10.sp, FontWeight.Bold))
            BasicText(title, style = TextStyle(Color.White, 24.sp, FontWeight.SemiBold))
            BasicText(description, style = TextStyle(Color.White.copy(alpha = 0.65f), 13.sp))
        }

        cards.chunked(columns).forEach { rowCards ->
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowCards.forEach { card ->
                    DemoCard(
                        card = card,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(card.destination) }
                    )
                }
                repeat(columns - rowCards.size) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun DemoCard(
    card: DemoCardData,
    modifier: Modifier,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(22.dp)
    Column(
        modifier
            .clip(shape)
            .background(Color.White.copy(alpha = 0.065f))
            .border(1.dp, Color.White.copy(alpha = 0.11f), shape)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BasicText(card.number, style = TextStyle(Color(0xFF9ACDFF), 10.sp, FontWeight.Bold))
        BasicText(card.title, style = TextStyle(Color.White, 15.sp, FontWeight.SemiBold))
        BasicText(
            card.description,
            Modifier.heightIn(min = 34.dp),
            style = TextStyle(Color.White.copy(alpha = 0.64f), 11.sp)
        )
    }
}

@Composable
private fun ActionPill(
    label: String,
    onClick: () -> Unit,
    primary: Boolean = false
) {
    val shape = RoundedCornerShape(100.dp)
    Box(
        Modifier
            .clip(shape)
            .background(
                if (primary) Color(0xFF208BFF)
                else Color.White.copy(alpha = 0.11f)
            )
            .border(1.dp, Color.White.copy(alpha = if (primary) 0.25f else 0.14f), shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 11.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicText(label, style = TextStyle(Color.White, 12.sp, FontWeight.SemiBold))
    }
}
