package xyz.teamgravity.ktorclientmultiplatformdemo

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    App(
        api = remember {
            CensorApi(createHttpClient(Darwin.create()))
        }
    )
}