package com.example.androiddevchallenge.model

import com.google.gson.annotations.SerializedName

/**
 * The model class represents the data in dogs.json.
 *
 * @author Lin Guo
 * @since 2021/2/26
 */
open class Dog(
    val name: String,
    @SerializedName("avatar_filename")
    val avatarFilename: String,
    val introduction: String) {
    /**
     * Indicates this contact is selected or not.
     */
    var adopted = false
}