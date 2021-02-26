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
package com.example.androiddevchallenge

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.androiddevchallenge.model.Dog
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.util.Resource

class MainActivity : AppCompatActivity() {

    /**
     * Dogs data read from dogs.json.
     */
    private val dogList = ArrayList<Dog>()

    private val mainViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
//                MyApp()
                SimpleCardComponent()
            }
        }

        mainViewModel.dogsLiveData.observe(this) { resource ->
            when (resource.status) {
                Resource.SUCCESS -> {
                    // Dogs data is ready.
                    resource.data?.let { dogs ->
                        dogList.clear()
                        dogList.addAll(dogs)
                        for (dog in dogs) {
                            println("dog name = " + dog.name)
                        }
                    }
                }
                Resource.LOADING -> {
                }
                Resource.ERROR -> {
                    Toast.makeText(
                        this, "Failed to get dogs data. ${resource.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        // Read and parse the contacts data and get notified by observer when data is ready.
        mainViewModel.readAndParseData()
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        Text(text = "Ready... Set... GO!")
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}

@Composable
fun SimpleCardComponent() {
    Card(
        backgroundColor = Color(0xFFFFA867.toInt()),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
//        Text(
//            text = "Simple Card",
//            textAlign = TextAlign.Center,
//            style = TextStyle(
//                fontSize = 16.sp
//            ),
//            modifier = Modifier.padding(16.dp)
//        )
        val image: Painter = painterResource(R.drawable.ic_launcher_foreground)
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )
    }
}