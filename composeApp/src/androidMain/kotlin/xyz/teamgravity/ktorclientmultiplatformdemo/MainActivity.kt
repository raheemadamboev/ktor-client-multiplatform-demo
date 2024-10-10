package xyz.teamgravity.ktorclientmultiplatformdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.ktor.client.engine.okhttp.OkHttp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = CensorApi(createHttpClient(OkHttp.create()))

        setContent {
            App(
                api = api
            )
        }
    }
}