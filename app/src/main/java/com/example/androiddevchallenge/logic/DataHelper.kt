/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.logic

import com.example.androiddevchallenge.model.Dog
import com.example.androiddevchallenge.GlobalApp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Helper class to read the data from dogs.json and return the parsed models.
 *
 * @author Lin Guo
 * @since 2021/2/26
 */
class DataHelper {

    /**
     * Read the data from dogs.json and parse it into Dog object list to return.
     */
    suspend fun getDogs(): List<Dog> = coroutineScope {
        var dogs: List<Dog> = ArrayList()
        val job = launch(Dispatchers.IO) {
            try {
                delay(2000) // Make sure we can see the loading progress.
                val assetsManager = GlobalApp.context.assets
                val inputReader = InputStreamReader(assetsManager.open("dogs.json"))
                val jsonString = BufferedReader(inputReader).readText()
                val typeOf = object : TypeToken<List<Dog>>() {}.type
                dogs = Gson().fromJson(jsonString, typeOf)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        job.join()
        dogs
    }

}