package com.imaginato.extension

import javafx.geometry.Rectangle2D
import javafx.scene.image.Image
import javafx.scene.image.ImageView

fun ImageView.centerCrop(logoImage: Image) {
    image = logoImage
    val newMeasure: Double =
        if (logoImage.width < logoImage.height) logoImage
            .width else logoImage.height
    val x: Double = (logoImage.width - newMeasure) / 2
    val y: Double = (logoImage.height - newMeasure) / 2

    val rect = Rectangle2D(x, y, newMeasure, newMeasure)
    viewport = rect
}