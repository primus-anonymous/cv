package com.neocaptainnemo.cv.ui.compose

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.platform.AmbientContext
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.request.LoadRequest
import com.neocaptainnemo.cv.R

private const val DEFAULT_WIDTH = 1000
private const val DEFAULT_HEIGHT = 300

@Composable
fun UrlImage(
        url: String,
        modifier: Modifier,
) {
    WithConstraints {

        val context: Context = AmbientContext.current

        val asset = remember { mutableStateOf<Bitmap?>(null) }

        val width = if (constraints.hasBoundedWidth) {
            constraints.maxWidth
        } else {
            DEFAULT_WIDTH
        }

        val height = if (constraints.hasBoundedHeight) {
            constraints.maxHeight
        } else {
            DEFAULT_HEIGHT
        }

        onCommit(url) {
            if (url.isEmpty()) {
                return@onCommit
            }
            val imageLoader = Coil.imageLoader(context)
            val request = LoadRequest.Builder(context)
                    .data(url)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .target(
                            onStart = { placeholder ->
                                asset.value = placeholder?.toBitmap(width,
                                                                    height)
                            },
                            onSuccess = { drawable ->
                                asset.value = drawable.toBitmap()
                            },
                            onError = { error ->
                                asset.value = error?.toBitmap(width,
                                                              height)
                            }
                    )
                    .build()

            val requestDisposable = imageLoader.execute(request)

            onDispose {
                requestDisposable.dispose()
            }
        }

        if (asset.value == null) {
            Box(modifier = modifier)
        } else {
            Image(bitmap = asset.value!!.asImageBitmap(),
                  modifier = modifier,
                  contentScale = ContentScale.Crop)
        }
    }
}


