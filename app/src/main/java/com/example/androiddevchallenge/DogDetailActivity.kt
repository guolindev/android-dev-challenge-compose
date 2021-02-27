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

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.model.Dog

const val SELECTED_DOG = "selected_dog"
const val SELECTED_POSITION = "selected_position"
const val ADOPTED = "adopted"

class DogDetailActivity : AppCompatActivity() {

    private lateinit var selectedDog: Dog
    private var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dog = intent.getParcelableExtra<Dog>(SELECTED_DOG)
        selectedPosition = intent.getIntExtra(SELECTED_POSITION, 0)
        if (dog == null) {
            Toast.makeText(this, "There must be something wrong.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        selectedDog = dog
        setContent {
            DisplayDogDetail(dog = selectedDog)
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(SELECTED_POSITION, selectedPosition)
        intent.putExtra(ADOPTED, selectedDog.adopted)
        setResult(RESULT_OK, intent)
        finish()
    }

}

var showConfirmDialog by mutableStateOf(false)

@Composable
fun DisplayDogDetail(dog: Dog) {
    val stateDog by remember { mutableStateOf(dog) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        DogAvatar(
            avatar = stateDog.avatarFilename,
            name = stateDog.name
        )
        Spacer(
            modifier = Modifier.requiredHeight(16.dp)
        )
        DogName(
            name = stateDog.name
        )
        Spacer(
            modifier = Modifier.requiredHeight(16.dp)
        )
        DogIntroduction(
            introduction = stateDog.introduction
        )
        Spacer(
            modifier = Modifier.requiredHeight(16.dp)
        )
        AdoptButton(
            adopted = stateDog.adopted
        )
    }
    if (showConfirmDialog) {
        AdoptConfirmDialog(dog = stateDog)
    }
}

@Composable
fun DogAvatar(avatar: String, name: String) {
    val imageIdentity = GlobalApp.context.resources.getIdentifier(
        avatar, "drawable",
        GlobalApp.context.packageName
    )
    val image: Painter = painterResource(imageIdentity)
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = image,
            contentDescription = name,
            modifier = Modifier
                .requiredSize(150.dp)
                .clip(shape = CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DogName(name: String) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DogIntroduction(introduction: String) {
    Text(
        text = introduction,
        fontSize = 18.sp,
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun AdoptButton(adopted: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { showConfirmDialog = true },
            enabled = !adopted
        ) {
            Text(text = if (adopted) "Adopted" else "Adopt")
        }
    }
}

@Composable
fun AdoptConfirmDialog(dog: Dog) {
    AlertDialog(
        onDismissRequest = {
            showConfirmDialog = false
        },
        text = {
            Text(
                text = "Do you want to adopt this lovely dog?",
                style = MaterialTheme.typography.body2
            )
        },
        confirmButton = {
            Button(onClick = {
                showConfirmDialog = false
                dog.adopted = true
            }) {
                Text(
                    text = "Yes"
                )
            }
        },
        dismissButton = {
            Button(onClick = {
                showConfirmDialog = false
            }) {
                Text(
                    text = "No"
                )
            }
        }
    )
}