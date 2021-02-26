package com.example.androiddevchallenge.logic

import com.example.androiddevchallenge.model.Dog
import com.example.androiddevchallenge.GlobalApp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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