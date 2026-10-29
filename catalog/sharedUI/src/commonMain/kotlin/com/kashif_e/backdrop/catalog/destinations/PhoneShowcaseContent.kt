package com.kashif_e.backdrop.catalog.destinations

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kashif_e.backdrop.Backdrop
import com.kashif_e.backdrop.catalog.BackdropDemoScaffold
import com.kashif_e.backdrop.catalog.LocalPickBackdropImage
import com.kashif_e.backdrop.catalog.components.LiquidBottomTab
import com.kashif_e.backdrop.catalog.components.LiquidBottomTabs
import com.kashif_e.backdrop.catalog.components.LiquidButton
import com.kashif_e.backdrop.catalog.components.LiquidSlider
import com.kashif_e.backdrop.catalog.components.LiquidToggle
import com.kashif_e.backdrop.drawBackdrop
import com.kashif_e.backdrop.effects.blur
import com.kashif_e.backdrop.effects.lens
import com.kashif_e.backdrop.effects.vibrancy
import kmpliquidglass.catalog.sharedui.generated.resources.Res
import kmpliquidglass.catalog.sharedui.generated.resources.system_home_screen_light
import org.jetbrains.compose.resources.painterResource

private enum class PhoneScreen {
    Lock,
    Home,
    ControlCenter,
    App
}

@Composable
fun PhoneShowcaseContent(onOpenCatalog: () -> Unit) {
    var screen by rememberSaveable { mutableStateOf(PhoneScreen.Lock) }
    var openedApp by rememberSaveable { mutableStateOf("Effects") }
    var selectedDockTab by rememberSaveable { mutableIntStateOf(0) }
    var glassIntensity by rememberSaveable { mutableFloatStateOf(0.7f) }
    var wifiEnabled by rememberSaveable { mutableStateOf(true) }
    var focusEnabled by rememberSaveable { mutableStateOf(false) }
    var brightness by rememberSaveable { mutableFloatStateOf(0.72f) }

    BackdropDemoScaffold(
        initialPainter = painterResource(Res.drawable.system_home_screen_light),
        imagePickerLabel = null
    ) { backdrop ->
        val pickImage = LocalPickBackdropImage.current
        BoxWithConstraints(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .widthIn(max = 430.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(38.dp))
                    .background(Color.Black.copy(alpha = 0.12f))
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.48f),
                        RoundedCornerShape(38.dp)
                    )
                    .padding(horizontal = 22.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StatusBar(
                    backdrop = backdrop,
                    onNotifications = { screen = PhoneScreen.App; openedApp = "Notifications" },
                    onControlCenter = { screen = PhoneScreen.ControlCenter }
                )
                Spacer(Modifier.height(12.dp))

                Box(Modifier.weight(1f).fillMaxWidth()) {
                    when (screen) {
                        PhoneScreen.Lock -> LockScreen(
                            backdrop = backdrop,
                            onUnlock = { screen = PhoneScreen.Home },
                            onCamera = {
                                openedApp = "Camera"
                                screen = PhoneScreen.App
                            },
                            onNotifications = {
                                screen = PhoneScreen.App
                                openedApp = "Notifications"
                            }
                        )

                        PhoneScreen.Home -> HomeScreen(
                            backdrop = backdrop,
                            selectedDockTab = selectedDockTab,
                            onDockTabSelected = { index ->
                                selectedDockTab = index
                                when (index) {
                                    0 -> Unit
                                    1 -> {
                                        openedApp = "Effects"
                                        screen = PhoneScreen.App
                                    }
                                    else -> screen = PhoneScreen.ControlCenter
                                }
                            },
                            onOpenApp = { app ->
                                when (app) {
                                    "Catalog" -> onOpenCatalog()
                                    "Control Center" -> screen = PhoneScreen.ControlCenter
                                    "Lock" -> screen = PhoneScreen.Lock
                                    else -> {
                                        openedApp = app
                                        screen = PhoneScreen.App
                                    }
                                }
                            }
                        )

                        PhoneScreen.ControlCenter -> ControlCenter(
                            backdrop = backdrop,
                            wifiEnabled = wifiEnabled,
                            onWifiChange = { wifiEnabled = it },
                            focusEnabled = focusEnabled,
                            onFocusChange = { focusEnabled = it },
                            brightness = brightness,
                            onBrightnessChange = { brightness = it },
                            onDone = { screen = PhoneScreen.Home }
                        )

                        PhoneScreen.App -> AppScreen(
                            backdrop = backdrop,
                            app = openedApp,
                            glassIntensity = glassIntensity,
                            onGlassIntensityChange = { glassIntensity = it },
                            onBack = { screen = PhoneScreen.Home },
                            onOpenCatalog = onOpenCatalog,
                            onPickImage = pickImage
                        )
                    }
                }

                if (screen != PhoneScreen.Lock) {
                    HomeIndicator(
                        backdrop = backdrop,
                        onClick = {
                            screen = PhoneScreen.Home
                            selectedDockTab = 0
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBar(
    backdrop: Backdrop,
    onNotifications: () -> Unit,
    onControlCenter: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText("9:41", style = TextStyle(Color.White, 14.sp, FontWeight.SemiBold))
        Spacer(Modifier.weight(1f))
        BasicText(
            "Notifications",
            Modifier.clickable(onClick = onNotifications).padding(horizontal = 8.dp),
            style = TextStyle(Color.White.copy(alpha = 0.85f), 11.sp)
        )
        LiquidButton(
            onControlCenter,
            backdrop,
            Modifier.height(30.dp),
            tint = Color.White.copy(alpha = 0.18f)
        ) {
            BasicText("Control", style = TextStyle(Color.White, 11.sp, FontWeight.Medium))
        }
    }
}

@Composable
private fun LockScreen(
    backdrop: Backdrop,
    onUnlock: () -> Unit,
    onCamera: () -> Unit,
    onNotifications: () -> Unit
) {
    val density = LocalDensity.current
    val unlockThreshold = with(density) { 48.dp.toPx() }
    var dragDistance by remember { mutableFloatStateOf(0f) }
    var didUnlock by remember { mutableStateOf(false) }
    val unlockDragState = rememberDraggableState { delta ->
        dragDistance += delta
        if (!didUnlock && dragDistance < -unlockThreshold) {
            didUnlock = true
            onUnlock()
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .draggable(
                state = unlockDragState,
                orientation = Orientation.Vertical,
                onDragStarted = {
                    dragDistance = 0f
                    didUnlock = false
                },
                onDragStopped = { dragDistance = 0f }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(34.dp))
        BasicText(
            "WELCOME TO LIQUID GLASS",
            style = TextStyle(Color.White.copy(alpha = 0.9f), 13.sp, FontWeight.Medium)
        )
        BasicText(
            "9:41",
            Modifier.padding(top = 4.dp),
            style = TextStyle(Color.White, 76.sp, FontWeight.Light)
        )
        Spacer(Modifier.height(20.dp))
        GlassPanel(backdrop, Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                BasicText("GLASS DEMO", style = TextStyle(Color.White.copy(alpha = 0.72f), 11.sp, FontWeight.Bold))
                BasicText("A little more liquid.", style = TextStyle(Color.White, 17.sp, FontWeight.Medium))
                BasicText("Tap around to explore the effects.", style = TextStyle(Color.White.copy(alpha = 0.8f), 13.sp))
            }
        }
        Spacer(Modifier.weight(1f))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundAction(backdrop, "N", onNotifications)
            LiquidButton(
                onUnlock,
                backdrop,
                tint = Color(0xFF1687FF)
            ) {
                BasicText("Swipe up or tap to unlock", style = TextStyle(Color.White, 14.sp, FontWeight.Medium))
            }
            RoundAction(backdrop, "C", onCamera)
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun HomeScreen(
    backdrop: Backdrop,
    selectedDockTab: Int,
    onDockTabSelected: (Int) -> Unit,
    onOpenApp: (String) -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        GlassPanel(backdrop, Modifier.fillMaxWidth()) {
            Row(
                Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    BasicText("CUPERTINO", style = TextStyle(Color.White.copy(alpha = 0.78f), 11.sp, FontWeight.SemiBold))
                    BasicText("72°", style = TextStyle(Color.White, 34.sp, FontWeight.Light))
                    BasicText("Sunny skies", style = TextStyle(Color.White.copy(alpha = 0.84f), 13.sp))
                }
                BasicText("SUN", style = TextStyle(Color.White.copy(alpha = 0.9f), 14.sp, FontWeight.Bold))
            }
        }

        BasicText("YOUR APPS", style = TextStyle(Color.White.copy(alpha = 0.8f), 11.sp, FontWeight.Bold))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AppTile(backdrop, "Effects", "GL", Color(0xFF6D63D8), Modifier.weight(1f)) { onOpenApp("Effects") }
                AppTile(backdrop, "Photos", "PH", Color(0xFFFF806B), Modifier.weight(1f)) { onOpenApp("Photos") }
                AppTile(backdrop, "Music", "MU", Color(0xFFE95183), Modifier.weight(1f)) { onOpenApp("Music") }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AppTile(backdrop, "Control Center", "CC", Color(0xFF398BD8), Modifier.weight(1f)) { onOpenApp("Control Center") }
                AppTile(backdrop, "Catalog", "ALL", Color(0xFF38A57A), Modifier.weight(1f)) { onOpenApp("Catalog") }
                AppTile(backdrop, "Lock", "LK", Color(0xFF505A75), Modifier.weight(1f)) { onOpenApp("Lock") }
            }
        }
        Spacer(Modifier.weight(1f))
        LiquidBottomTabs(
            selectedTabIndex = { selectedDockTab },
            onTabSelected = onDockTabSelected,
            backdrop = backdrop,
            tabsCount = 3,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Home", "Effects", "Control").forEachIndexed { index, label ->
                LiquidBottomTab(onClick = { onDockTabSelected(index) }) {
                    BasicText(label, style = TextStyle(Color.White, 11.sp, FontWeight.Medium))
                }
            }
        }
    }
}

@Composable
private fun ControlCenter(
    backdrop: Backdrop,
    wifiEnabled: Boolean,
    onWifiChange: (Boolean) -> Unit,
    focusEnabled: Boolean,
    onFocusChange: (Boolean) -> Unit,
    brightness: Float,
    onBrightnessChange: (Float) -> Unit,
    onDone: () -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                BasicText("Control Center", style = TextStyle(Color.White, 23.sp, FontWeight.SemiBold))
                BasicText("A familiar place for liquid glass.", style = TextStyle(Color.White.copy(alpha = 0.75f), 12.sp))
            }
            LiquidButton(onDone, backdrop, tint = Color.White.copy(alpha = 0.18f)) {
                BasicText("Done", style = TextStyle(Color.White, 13.sp, FontWeight.Medium))
            }
        }

        GlassPanel(backdrop, Modifier.fillMaxWidth()) {
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                ToggleRow(backdrop, "Wi-Fi", wifiEnabled, onWifiChange)
                ToggleRow(backdrop, "Focus", focusEnabled, onFocusChange)
            }
        }

        GlassPanel(backdrop, Modifier.fillMaxWidth()) {
            Column(
                Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BasicText("DISPLAY", style = TextStyle(Color.White.copy(alpha = 0.74f), 11.sp, FontWeight.Bold))
                BasicText("Brightness  ${(brightness * 100).toInt()}%", style = TextStyle(Color.White, 14.sp, FontWeight.Medium))
                LiquidSlider(
                    value = { brightness },
                    onValueChange = onBrightnessChange,
                    valueRange = 0f..1f,
                    visibilityThreshold = 0.01f,
                    backdrop = backdrop
                )
            }
        }

        GlassPanel(backdrop, Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                BasicText("NOW PLAYING", style = TextStyle(Color.White.copy(alpha = 0.74f), 11.sp, FontWeight.Bold))
                BasicText("Liquid in Motion", style = TextStyle(Color.White, 15.sp, FontWeight.Medium))
                BasicText("Backdrop Demo  •  3:41", style = TextStyle(Color.White.copy(alpha = 0.78f), 12.sp))
            }
        }
        Spacer(Modifier.weight(1f))
        BasicText("Drag the sliders and toggle the controls.", style = TextStyle(Color.White.copy(alpha = 0.8f), 12.sp))
    }
}

@Composable
private fun AppScreen(
    backdrop: Backdrop,
    app: String,
    glassIntensity: Float,
    onGlassIntensityChange: (Float) -> Unit,
    onBack: () -> Unit,
    onOpenCatalog: () -> Unit,
    onPickImage: () -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            LiquidButton(onBack, backdrop, tint = Color.White.copy(alpha = 0.18f)) {
                BasicText("Back", style = TextStyle(Color.White, 13.sp, FontWeight.Medium))
            }
            Spacer(Modifier.weight(1f))
            BasicText(app.uppercase(), style = TextStyle(Color.White.copy(alpha = 0.8f), 11.sp, FontWeight.Bold))
        }

        when (app) {
            "Effects" -> EffectsApp(backdrop, glassIntensity, onGlassIntensityChange, onOpenCatalog)
            "Photos" -> WallpaperApp(backdrop, onPickImage)
            "Camera" -> CameraApp(backdrop, onPickImage)
            "Music" -> SimpleApp(backdrop, "Now Playing", "Liquid in Motion\nBackdrop Demo", "PLAYING")
            "Notifications" -> SimpleApp(backdrop, "Notifications", "You’re all caught up.\nYour glass demo is ready.", "JUST NOW")
            "Lock" -> SimpleApp(backdrop, "Lock Screen", "Head back to the home screen and tap the lock screen shortcut.", "DEVICE")
            else -> SimpleApp(backdrop, app, "Explore the glass surfaces and interactions.", "DEMO")
        }
    }
}

@Composable
private fun CameraApp(backdrop: Backdrop, onPickImage: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        BasicText("Camera", style = TextStyle(Color.White, 24.sp, FontWeight.SemiBold))
        GlassPanel(backdrop, Modifier.fillMaxWidth()) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                BasicText("CAMERA", style = TextStyle(Color.White.copy(alpha = 0.74f), 11.sp, FontWeight.Bold))
                BasicText(
                    "Choose a photo to preview it behind the glass.",
                    style = TextStyle(Color.White, 15.sp, FontWeight.Medium)
                )
            }
        }
        LiquidButton(
            onPickImage,
            backdrop,
            Modifier.fillMaxWidth(),
            tint = Color(0xFF1687FF)
        ) {
            BasicText("Choose a photo", style = TextStyle(Color.White, 14.sp, FontWeight.Medium))
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun WallpaperApp(backdrop: Backdrop, onPickImage: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        BasicText("Make it yours.", style = TextStyle(Color.White, 24.sp, FontWeight.SemiBold))
        GlassPanel(backdrop, Modifier.fillMaxWidth()) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                BasicText("WALLPAPER", style = TextStyle(Color.White.copy(alpha = 0.74f), 11.sp, FontWeight.Bold))
                BasicText(
                    "Choose a photo and see the glass adapt to your colors.",
                    style = TextStyle(Color.White, 15.sp, FontWeight.Medium)
                )
            }
        }
        LiquidButton(
            onPickImage,
            backdrop,
            Modifier.fillMaxWidth(),
            tint = Color(0xFF1687FF)
        ) {
            BasicText("Choose a wallpaper", style = TextStyle(Color.White, 14.sp, FontWeight.Medium))
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun EffectsApp(
    backdrop: Backdrop,
    intensity: Float,
    onIntensityChange: (Float) -> Unit,
    onOpenCatalog: () -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        BasicText("Glass, up close.", style = TextStyle(Color.White, 24.sp, FontWeight.SemiBold))
        BasicText(
            "Tune the lens and watch the wallpaper respond.",
            style = TextStyle(Color.White.copy(alpha = 0.82f), 13.sp)
        )
        GlassPanel(backdrop, Modifier.fillMaxWidth(), intensity) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                BasicText("LIQUID GLASS", style = TextStyle(Color.White.copy(alpha = 0.75f), 11.sp, FontWeight.Bold))
                BasicText("Blur + lens + vibrancy", style = TextStyle(Color.White, 18.sp, FontWeight.Medium))
                BasicText("A live sample over your wallpaper.", style = TextStyle(Color.White.copy(alpha = 0.84f), 12.sp))
            }
        }
        BasicText("GLASS INTENSITY  ${(intensity * 100).toInt()}%", style = TextStyle(Color.White, 12.sp, FontWeight.SemiBold))
        LiquidSlider(
            value = { intensity },
            onValueChange = onIntensityChange,
            valueRange = 0.2f..1f,
            visibilityThreshold = 0.01f,
            backdrop = backdrop
        )
        GlassPanel(backdrop, Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                EffectLegendRow("01", "Blur", "Softens the backdrop")
                EffectLegendRow("02", "Lens", "Bends light through the surface")
                EffectLegendRow("03", "Vibrancy", "Keeps colors alive")
                EffectLegendRow("04", "Highlight", "Responds to touch")
            }
        }
        Spacer(Modifier.weight(1f))
        LiquidButton(
            onOpenCatalog,
            backdrop,
            Modifier.fillMaxWidth(),
            tint = Color(0xFF1687FF)
        ) {
            BasicText("Explore every effect", style = TextStyle(Color.White, 14.sp, FontWeight.Medium))
        }
    }
}

@Composable
private fun SimpleApp(backdrop: Backdrop, title: String, description: String, eyebrow: String) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        BasicText(title, style = TextStyle(Color.White, 24.sp, FontWeight.SemiBold))
        GlassPanel(backdrop, Modifier.fillMaxWidth()) {
            Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                BasicText(eyebrow, style = TextStyle(Color.White.copy(alpha = 0.74f), 11.sp, FontWeight.Bold))
                BasicText(description, style = TextStyle(Color.White, 15.sp, FontWeight.Medium))
            }
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun AppTile(
    backdrop: Backdrop,
    label: String,
    initials: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            Modifier
                .size(58.dp)
                .clip(RoundedCornerShape(17.dp))
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = { RoundedCornerShape(17.dp) },
                    effects = {
                        vibrancy()
                        blur(2.dp.toPx())
                        lens(12.dp.toPx(), 20.dp.toPx())
                    },
                    onDrawSurface = { drawRect(color.copy(alpha = 0.68f)) }
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            BasicText(initials, style = TextStyle(Color.White, 15.sp, FontWeight.Bold))
        }
        BasicText(
            label,
            Modifier.clickable(onClick = onClick),
            style = TextStyle(Color.White, 10.sp, FontWeight.Medium)
        )
    }
}

@Composable
private fun GlassPanel(
    backdrop: Backdrop,
    modifier: Modifier = Modifier,
    intensity: Float = 0.72f,
    content: @Composable () -> Unit
) {
    Box(
        modifier.drawBackdrop(
            backdrop = backdrop,
            shape = { RoundedCornerShape(24.dp) },
            effects = {
                vibrancy()
                blur((8f * intensity).dp.toPx())
                lens((18f * intensity).dp.toPx(), (32f * intensity).dp.toPx())
            },
            onDrawSurface = { drawRect(Color.White.copy(alpha = 0.13f)) }
        )
    ) {
        content()
    }
}

@Composable
private fun ToggleRow(
    backdrop: Backdrop,
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(label, style = TextStyle(Color.White, 14.sp, FontWeight.Medium))
        Spacer(Modifier.weight(1f))
        LiquidToggle(
            selected = { checked },
            onSelect = onCheckedChange,
            backdrop = backdrop
        )
    }
}

@Composable
private fun RoundAction(backdrop: Backdrop, label: String, onClick: () -> Unit) {
    Box(
        Modifier
            .size(48.dp)
            .clip(CircleShape)
            .drawBackdrop(
                backdrop = backdrop,
                shape = { CircleShape },
                effects = {
                    vibrancy()
                    blur(4.dp.toPx())
                    lens(14.dp.toPx(), 22.dp.toPx())
                },
                onDrawSurface = { drawRect(Color.White.copy(alpha = 0.18f)) }
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        BasicText(label, style = TextStyle(Color.White, 13.sp, FontWeight.Bold))
    }
}

@Composable
private fun EffectLegendRow(number: String, title: String, description: String) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(number, style = TextStyle(Color.White.copy(alpha = 0.58f), 10.sp, FontWeight.Bold))
        BasicText(
            title,
            Modifier.padding(start = 10.dp).widthIn(min = 70.dp),
            style = TextStyle(Color.White, 12.sp, FontWeight.SemiBold)
        )
        BasicText(
            description,
            Modifier.weight(1f),
            style = TextStyle(Color.White.copy(alpha = 0.76f), 11.sp)
        )
    }
}

@Composable
private fun HomeIndicator(backdrop: Backdrop, onClick: () -> Unit) {
    Box(
        Modifier
            .padding(top = 12.dp)
            .size(width = 82.dp, height = 24.dp)
            .clickable(onClick = onClick)
            .padding(vertical = 9.5.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.78f))
    )
}
