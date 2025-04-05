
@file:JvmName("ShaderUtil")

package dev.trindadedev.opengles.graphics.shader

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
import dev.trindadedev.opengles.GLContext
import dev.trindadedev.opengles.AndroidGLES
import dev.trindadedev.opengles.internal.readTextFromAndroidAssets
import java.io.File

fun GLContext.loadShader(
  type: Int,
  code: String
): Int {
  val shader = AndroidGLES.glCreateShader(type)
  AndroidGLES.glShaderSource(shader, code)
  AndroidGLES.glCompileShader(shader)
  return shader
}

fun GLContext.compileShaderSource(shaderSource: ShaderSource): Shader {
  return Shader(
    vertexShader = loadShader(
      type = AndroidGLES.GL_VERTEX_SHADER,
      code = shaderSource.vertexShaderCode
    ),
    fragmentShader = loadShader(
      type = AndroidGLES.GL_FRAGMENT_SHADER,
      code = shaderSource.fragmentShaderCode
    )
  )
}

fun GLContext.readShaderSourceFromFile(
  vertexShaderFile: File,
  fragmentShaderFile: File
): ShaderSource {
  if (!vertexShaderFile.extension.equals(".vert")) {
    throw ShaderException("The vertex shader file extension must be .vert")
  }
  if (!fragmentShaderFile.extension.equals(".frag")) {
    throw ShaderException("The fragment shader file extension must be .frag")
  }
  val vertexShaderCode = vertexShaderFile.readText()
  val fragmentShaderCode = fragmentShaderFile.readText()
  if (vertexShaderCode.isEmpty()) {
    throw ShaderException("Empty vertex shader code, or could not be read.")
  }
  if (fragmentShaderCode.isEmpty()) {
    throw ShaderException("Empty fragment shader code, or could not be read.")
  }
  return ShaderSource(
    vertexShaderCode = vertexShaderCode,
    fragmentShaderCode = fragmentShaderCode
  )
}

fun GLContext.readShaderSourceFromAndroidAssets(
  androidContext: Context,
  vertexShaderPath: String,
  fragmentShaderPath: String
): ShaderSource {
  val vertexShaderCode = androidContext.readTextFromAndroidAssets(vertexShaderPath)
  val fragmentShaderCode = androidContext.readTextFromAndroidAssets(fragmentShaderPath)
  if (vertexShaderCode.isEmpty()) {
    throw ShaderException("Empty vertex shader code, or could not be read.")
  }
  if (fragmentShaderCode.isEmpty()) {
    throw ShaderException("Empty fragment shader code, or could not be read.")
  }
  return ShaderSource(
    vertexShaderCode = vertexShaderCode,
    fragmentShaderCode = fragmentShaderCode
  )
}