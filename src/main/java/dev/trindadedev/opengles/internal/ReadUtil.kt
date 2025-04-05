
@file:JvmName("ReadUtil")

package dev.trindadedev.opengles.internal

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
import java.io.BufferedReader
import java.io.InputStreamReader

fun Context.readTextFromAndroidAssets(dir: String): String {
  val builder = StringBuilder()
  val reader = BufferedReader(InputStreamReader(assets.open(dir)))
  var line: String? = null
  while (reader.readLine().also { line = it } != null) {
    builder.append(line).append("\n")
  }
  reader.close()
  return builder.toString()
}