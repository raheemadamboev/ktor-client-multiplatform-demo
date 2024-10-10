package xyz.teamgravity.ktorclientmultiplatformdemo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ktorclientmultiplatformdemo.composeapp.generated.resources.Res
import ktorclientmultiplatformdemo.composeapp.generated.resources.censor
import ktorclientmultiplatformdemo.composeapp.generated.resources.uncensored_text
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    api: CensorApi
) {
    MaterialTheme {
        val scope = rememberCoroutineScope()

        var uncensoredText by rememberSaveable { mutableStateOf("") }
        var censoredText by rememberSaveable { mutableStateOf<String?>(null) }
        var loading by remember { mutableStateOf(false) }
        var error by remember { mutableStateOf<NetworkError?>(null) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            ),
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = uncensoredText,
                onValueChange = { uncensoredText = it },
                placeholder = {
                    Text(
                        text = stringResource(Res.string.uncensored_text)
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = {
                    scope.launch {
                        loading = true
                        censoredText = null
                        error = null

                        api.getCensoredText(uncensoredText).onResult(
                            onSuccess = { text ->
                                censoredText = text
                            },
                            onFailure = { failure ->
                                error = failure
                            }
                        )

                        loading = false
                    }
                }
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 1.dp,
                        modifier = Modifier.size(15.dp)
                    )
                } else {
                    Text(
                        text = stringResource(Res.string.censor)
                    )
                }
            }
            censoredText?.let { text ->
                Text(
                    text = text
                )
            }
            error?.let { error ->
                Text(
                    text = error.name,
                    color = Color.Red
                )
            }
        }
    }
}