package xyz.teamgravity.ktorclientmultiplatformdemo

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.engine.okhttp.OkHttp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KtorClientMultiplatformDemo",
    ) {
        App(
            api = remember {
                CensorApi(createHttpClient(OkHttp.create()))
            }
        )
    }
}