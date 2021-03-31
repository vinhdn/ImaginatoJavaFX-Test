package com.imaginato.extension

import com.google.gson.Gson

fun <T> String.toObject(clazz: Class<T>): T {
    val gson = Gson()
    return gson.fromJson(this, clazz)
}