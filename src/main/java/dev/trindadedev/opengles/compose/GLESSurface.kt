package dev.trindadedev.opengles.compose

/*
 * Copyright 2025 Aquiles Trindade (trindadedev).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import dev.trindadedev.opengles.GLESRenderer
import dev.trindadedev.opengles.GLESSurfaceView
import dev.trindadedev.opengles.runOnGLContext
import dev.trindadedev.opengles.unit.Size
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

@Composable
fun GLESSurface(
  modifier: Modifier = Modifier,
  glesSurfaceState: GLESSurfaceState,
  onSurfaceCreated: GLContext.(GL10, EGLConfig) -> Unit,
  onSurfaceChanged: GLContext.(GL10, Size) -> Unit,
  onDrawFrame: GLContext.(GL10) -> Unit
) {
  val androidContext = LocalContext.current
  val factory = rememberGLESSurfaceFactory(
    androidContext = androidContext,
    glesSurfaceState = glesSurfaceState,
    onSurfaceCreated = onSurfaceCreated,
    onSurfaceChanged = onSurfaceChanged,
    onDrawFrame = onDrawFrame
  )
  AndroidView(
    modifier = modifier,
    factory = { factory },
    onRelease = { it.release() }
  )
}

data class GLESSurfaceState(
  var glesSurfaceView: GLESSurfaceView? = null,
  var glesRenderer: GLESRenderer? = null
)

@Composable
fun rememberGLESSurfaceState() = remember { GLESSurfaceState() }

@Composable
private fun rememberGLESSurfaceFactory(
  androidContext: Context,
  glesSurfaceState: GLESSurfaceState,
  onSurfaceCreated: GLContext.(GL10, EGLConfig) -> Unit,
  onSurfaceChanged: GLContext.(GL10, Size) -> Unit,
  onDrawFrame: GLContext.(GL10) -> Unit
) = remember {
  val glesOnRenderer = object : GLESRenderer.OnRenderer {
    override fun onSurfaceCreated(gl: GL10, eglConfig: EGLConfig) {
      runOnGLContext {
        onSurfaceCreated(gl, eglConfig)
      }
    }
    override fun onSurfaceChanged(gl: GL10, size: Size) {
      runOnGLContext {
        onSurfaceChanged(gl, size)
      }
    }
    override fun onDrawFrame(gl: GL10) {
      runOnGLContext {
        onDrawFrame(gl)
      }
    }
  }
  val renderer = GLESRenderer(glesOnRenderer)
  glesSurfaceState.glesRenderer = renderer
  val view = GLESSurfaceView(androidContext)
  glesSurfaceState.glesSurfaceView = view
}