package dev.trindadedev.opengles

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
import android.opengl.GLSurfaceView as AndroidGLSurfaceView

/**
 * The Main OpenGL Surface View of OpenGL.
 *
 * @property renderer The Renderer of Game.
 * @property context The Android Context.
 */
internal class GLESSurfaceView(
  private val context: Context,
  private val renderer: GLESRenderer
): AndroidGLSurfaceView(context) {

  init {
    setEGLContextClientVersion(2)
    setRenderer(renderer)
  }
}